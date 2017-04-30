/**
 * 
 */
package com.ffzx.order.mq.consumer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.bms.api.dto.OrderParam;
import com.ffzx.bms.api.service.OrderProcessManagerApiService;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.mq.AbstractMessageConsumer;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderRecord;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.service.OmsOrderRecordService;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.order.utils.OmsConstant;
import com.ffzx.wms.api.dto.OutboundOrderStatusApiVo;
import com.ffzx.wms.api.dto.Warehouse;

/**
 * @Description: TODO
 * @author zi.luo
 * @email zi.luo@ffzxnet.com
 * @date 2016年6月8日 下午2:50:07
 * @version V1.0
 * 
 */
@Component
public class OutboundOrderStatusReqConsumer extends AbstractMessageConsumer<List<OutboundOrderStatusApiVo>> {

	@Autowired
	private OmsOrderRecordService omsOrderRecordService;

	@Autowired
	private OmsOrderService omsOrderService;

	@Resource(name = "orderProcessManagerApiService")
	private OrderProcessManagerApiService orderProcessManagerApiService;
//	@Autowired
//	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	RabbitTemplate rabbitTemplate;
	/**
	 * 由WMS系统作业状态变更推送给订单系统(拣货完成，装箱中，出库完成)。
	 * ZERO("【", "0"),THREE("】开始拣货", "3"), FOUR("】完成拣货", "4"),FIVE("】开始打包", "5"), SIX("】打包完成", "6"),SEVEN("】正发往【", "7"),LAST("】", "10");
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void onMessage(List<OutboundOrderStatusApiVo> list) {
		logger.info("oms-OutboundOrderStatusReqConsumer==>>onMessage==》》wms推送oms,物流信息消费==》》List<OutboundOrderStatusApiVo> list"+JsonMapper.toJsonString(list));
		OmsOrder order = null;
		ResultDto rto = null;
			try {
				//设置时间，防止程序挂掉，导致其他线程无法获得锁
//				redisTemplate.expire(OmsConstant.QUEUE_WMS_OUTBOUND_ORDER_STATUS, 5L, TimeUnit.MINUTES);
				
				// 当前时间
				Date pushDateEnd = new Date();
				
				//记录订单推送采购时间
				logger.error("接收物流状态，redis锁  enter时间 ===>>>" + DateUtil.formatDateTime(pushDateEnd));
				
				List<OutboundOrderStatusApiVo> listVo = new ArrayList<OutboundOrderStatusApiVo>();// list;
				for (int i = 0; i < list.size(); i++) {
					Map v = (Map) list.get(i);
					OutboundOrderStatusApiVo message = JsonMapper.convertVal(v, OutboundOrderStatusApiVo.class);
					
					logger.info("oms-OutboundOrderStatusReqConsumer==>>onMessage==》》wms推送oms,物流信息消费："+JsonMapper.toJsonString(message));
					if (message != null) {
						Map<String, Object> params = new HashMap<String, Object>();
						Map<String, Long> map = new HashMap<String, Long>();
						params.put("orderNo", message.getSalesOrderNo());
						Page page = new Page();
						List<OmsOrder> orderList = omsOrderService.queryByPage(page, params);
						if (orderList != null && orderList.size() > 0) {
							order = orderList.get(0);
							String curLoisticsStatus = (null==order.getCurLoisticsStatus()?"":order.getCurLoisticsStatus());
							if(message.getStatus().equals(curLoisticsStatus)){
								logger.error("【"+message.getSalesOrderNo()+"】wms重复提交，订单系统跳过该单作业，oms-OutboundOrderStatusReqConsumer==>>message："+JsonMapper.toJsonString(message));
									continue;
							}
							order.setCurLoisticsStatus(message.getStatus().toString());
	
							if (message.getStatus() >= 6) {
								order.setOrderPackStatus("1");
							} else {
								order.setOrderPackStatus("0");
							}
							if (message.getStatus() == 7) { // 如果是已出库，更新订单出库时间
								order.setOutboundTime(new Date());
							}
							int flag = omsOrderService.updateStatus(order);
							if (flag <= 0) {
								throw new Exception("修改订单当前物流状态失败");
							}
							OmsOrderRecord record = new OmsOrderRecord();
							record.setId(UUIDGenerator.getUUID());
							record.setOrderNo(message.getSalesOrderNo());
							record.setOrderId(order.getId());
							record.setDescription(OmsConstant.LogisticsConvert(message.getStatus(), message.getRemarks(), order));
							record.setOutboundStatus(message.getStatus().toString());
							if(message.getStatus()==5){
								record.setCreateDate(DateUtil.getDateAddMillSecond(new Date(),3000));
							}else{
								record.setCreateDate(new Date());
							}
							record.setRecordType("1");
							logger.info("oms-OutboundOrderStatusReqConsumer==>>onMessage==》》【"+message.getSalesOrderNo()+"】物流信息日志新增："+JsonMapper.toJsonString(record));
							omsOrderRecordService.add(record); // 新增订单操作日志
							try{
							if (message.getStatus() == 7) {
								logger.info("【"+message.getSalesOrderNo()+"】oms-OutboundOrderStatusReqConsumer==>>message："+JsonMapper.toJsonString(message));
								List<OmsOrderdetail> detailList = order.getDetailList();
								if (detailList != null && detailList.size() > 0) {
									List<OrderParam> orderParamList = null;
									try {
									orderParamList = initOrderParamInfo(detailList);
									logger.info("【"+order.getOrderNo()+"】出库库存扣：===========orderProcessManagerService.inbound("+JsonMapper.toJsonString(orderParamList)+","+order.getRegionId()+","+order.getOrderNo()+",\"待收货(已出库交接)\"),取消订单操作库存失败");
									rto = orderProcessManagerApiService.inbound(orderParamList,order.getRegionId(),order.getOrderNo());
									if (!rto.getCode().equals(ResultDto.OK_CODE)) {
										logger.error("【"+message.getSalesOrderNo()+"】出库库存扣减失败 ："+rto.getMessage());
										throw new Exception();
									}
									} catch (Exception e) {
										logger.error("stockManagerApiService.Inbound==》》【"+message.getSalesOrderNo()+"】出库库存扣减异常", e);
										throw e;
									}
								} else {
									logger.error("由WMS系统作业状态变更推送给订单系统【"+message.getSalesOrderNo()+"】订单明细为空");
									throw new Exception();
								}
								//订单出库成功通知 为结算系统预留广播
								//sendODORadio(order);
							}
							}catch(Exception e){
								logger.error("【"+message.getSalesOrderNo()+"】wms通知订单物流消息失败",e);
								continue;
							}
						} else {
							throw new Exception();
						}
					} else {
						logger.error("OutboundOrderStatusReqConsumer.onMessage接收数据转化为空 ");
					}
				}
	
			} catch (Exception e) {
				logger.error("InboundPurchaseReqConsumer :" + ToStringBuilder.reflectionToString(list), e);
				throw new ServiceException(e);
			}
		}
	/**
	 * 初始化库存操作传递参数
	 * 
	 * @param orderdetailList
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月6日 上午11:18:50
	 * @version V1.0
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private List<OrderParam> initOrderParamInfo(List<OmsOrderdetail> orderdetailList) throws IllegalAccessException, InvocationTargetException{
		if(null!=orderdetailList&&orderdetailList.size()>0){
			List<OrderParam> orderParamList = new ArrayList<OrderParam>();
			for (OmsOrderdetail omsOrderdetail : orderdetailList) {
				OrderParam op = new OrderParam();

				if(null!=omsOrderdetail.getCommodity()){
					com.ffzx.bms.api.dto.Commodity bms_com = new com.ffzx.bms.api.dto.Commodity();
					BeanUtils.copyProperties(bms_com,omsOrderdetail.getCommodity());
					op.setCommodity(bms_com);
					}
				
				op.setNumber(new Long(omsOrderdetail.getBuyNum()));
				op.setSkuCode(omsOrderdetail.getSkuCode());
				Warehouse warehouse = new Warehouse();
				warehouse.setId(omsOrderdetail.getWarehouseId());
				warehouse.setCode(omsOrderdetail.getWarehouseCode());
				warehouse.setName(omsOrderdetail.getWarehouseName());
				op.setWarehouseObject(warehouse);
				orderParamList.add(op);
			}
			return orderParamList;
		}else{
			return null;
		}
	}
	
	//订单出库成功 发送广播
	private void sendODORadio(OmsOrder omsOrder){
			if(null!=omsOrder){
				try{
					rabbitTemplate.convertAndSend("OMS_ODO_CHANGE_EXCHANGE", "", omsOrder);
					}catch (Exception e) {
						logger.error("【"+omsOrder.getOrderNo()+"】订单出库完成发送广播失败",e);
					}
				}
			}
}

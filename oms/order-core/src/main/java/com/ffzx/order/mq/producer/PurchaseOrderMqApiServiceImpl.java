/**
 * 
 */
package com.ffzx.order.mq.producer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.order.api.dto.Commodity;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.dto.PurchaseOrderDetailVo;
import com.ffzx.order.api.dto.PurchaseOrderVo;
import com.ffzx.order.api.service.PurchaseOrderMqApiService;
import com.ffzx.order.model.PurchasePush;
import com.ffzx.order.service.OmsOrderdetailService;
import com.ffzx.order.service.PurchasePushService;
import com.ffzx.order.utils.OmsConstant;

/**
 * @Description: 发货订单数据推送
 * @author sheng.shan
 * @email  sheng.shan@ffzxnet.com
 * @date 2016年6月7日 上午10:32:24
 * @version V1.0 
 *
 */
@Service("purchaseOrderMqApiService")
public class PurchaseOrderMqApiServiceImpl implements PurchaseOrderMqApiService{

	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(PurchaseOrderMqApiServiceImpl.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private PurchasePushService purchasePushService;
	@Autowired
	private OmsOrderdetailService omsOrderdetailService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void purchaseOrder() {
		
		if(redisTemplate.opsForValue().setIfAbsent(OmsConstant.OMS_PURCHASEORDERMQAPI_PURCHASEORDER, "1")){
			try {
				//设置时间，防止程序挂掉，导致其他线程无法获得锁
				redisTemplate.expire(OmsConstant.OMS_PURCHASEORDERMQAPI_PURCHASEORDER, 5L, TimeUnit.MINUTES);
				
				// 当前时间
				Date pushDateEnd = new Date();
				
				//记录订单推送采购时间
				logger.error("订单数据推送采购，redis锁  enter时间 ===>>>" + DateUtil.formatDateTime(pushDateEnd));
				
				// 获取订单信息
				Map<String,Object> params = new HashMap<String,Object>();
				Date pushDateStart = purchasePushService.selectMaxPushDate(null);
				if(StringUtil.isNotNull(pushDateStart)){
					params.put("pushDateStart", pushDateStart);
				}
				params.put("pushDateEnd", pushDateEnd);
				
				// 按仓库、供应商分组数据
				List<OmsOrderdetail> orderdetailListByParams = omsOrderdetailService.getdetailList(params);
				// 处理数据
				List<OmsOrderdetail> orderdetailListByVendor = setOrderdetailListByVendor(orderdetailListByParams);
				logger.info("订单推送采购---按仓库、供应商分组数据——orderdetailList——"+JsonMapper.toJsonString(orderdetailListByVendor));
				// 按Sku分组数据
				List<OmsOrderdetail> orderdetailListBySku = omsOrderdetailService.getdetailListBySku(params);
				logger.info("订单推送采购---按Sku分组数据——orderdetailListBySku——"+JsonMapper.toJsonString(orderdetailListBySku));

				// 推送消息
//				logger.info("订单推送采购 按供应商分类数据——orderDetails——"+JsonMapper.toJsonString(orderdetails));
				PurchaseOrderVo purchaseOrderVo = new PurchaseOrderVo();
				
				for (OmsOrderdetail omsDetail : orderdetailListByVendor) {
					purchaseOrderVo.setSupplierId(omsDetail.getCommodity()==null?"":omsDetail.getCommodity().getVendorId()); // 供应商Id
					purchaseOrderVo.setWarehouseCode(omsDetail.getWarehouseCode()); // 仓库code
					// 订单编号集合
				    String[] orderNo = omsDetail.getOrderNo().split(",");
				    List<String> orderNos = Arrays.asList(orderNo);
					purchaseOrderVo.setOrderNumbers(orderNos);
					
					List<PurchaseOrderDetailVo> items = new ArrayList<PurchaseOrderDetailVo>();
					PurchaseOrderDetailVo purchaseOrderDetailVo = null;
					for (OmsOrderdetail detail : orderdetailListBySku) {
						if (omsDetail.getWarehouseCode().equals(detail.getWarehouseCode()) 
								&& omsDetail.getCommodity().getVendorId().equals(detail.getCommodity().getVendorId())) {
							purchaseOrderDetailVo = new PurchaseOrderDetailVo();
							purchaseOrderDetailVo.setCode(detail.getSkuCode()); // 商品sku编号
							purchaseOrderDetailVo.setPurchaseQuantity(new BigDecimal(detail.getBuyNum())); // 采购数量
							items.add(purchaseOrderDetailVo);
						}
					}
					purchaseOrderVo.setItems(items); // 商品集合
					// 推送
					logger.info("订单推送采购"+DateUtil.formatDateTime(pushDateEnd)+"——purchaseOrderVo——"+JsonMapper.toJsonString(purchaseOrderVo));
					rabbitTemplate.convertAndSend("QUEUE_OMS_PURCHASE_ORDER", purchaseOrderVo);
				}
				if (CollectionUtils.isNotEmpty(orderdetailListByVendor)) {
					// 新增推送时间记录
					PurchasePush purchasePush = new PurchasePush();
					purchasePush.setId(UUIDGenerator.getUUID());
					purchasePush.setPushDate(pushDateEnd);
					purchasePushService.add(purchasePush);
				} else {
					logger.info("订单推送采购数据为空"+DateUtil.formatDateTime(pushDateEnd));
				}
				
			}catch(Exception e){
				logger.error("订单数据推送采购 : invoke PurchaseOrderMqApiServiceImpl.purchaseOrder error ===>>>",e);
				throw e;
			}finally{
				//释放锁
				redisTemplate.delete(OmsConstant.OMS_PURCHASEORDERMQAPI_PURCHASEORDER);
			}
		}
	}
	
	
	public List<OmsOrderdetail> setOrderdetailListByVendor(List<OmsOrderdetail> orderDetailList){
		Map<String,String> objMap =  new  HashMap<String,String>();
		// 遍历，组合成map集合
 		for (OmsOrderdetail omsOrderdetail : orderDetailList) {
			String key  = omsOrderdetail.getWarehouseCode()+"_"+omsOrderdetail.getCommodity().getVendorId();
			if(objMap.containsKey(key)){
				String orderNoArr = objMap.get(key);
				orderNoArr+= ","+omsOrderdetail.getOrderNo();
				objMap.put(key,orderNoArr);
			}else{
				objMap.put(key,omsOrderdetail.getOrderNo());
			}
		}
 		// 组合按仓库、供应商分组数据
 		List<OmsOrderdetail> orderdetailListByVendor = new ArrayList<OmsOrderdetail>();
 		for (String key : objMap.keySet()) {
 			String warehouseCode = key.substring(0,key.indexOf("_")).trim(); // 仓库code
 			String vendorId = key.substring(key.indexOf("_")+1,key.length()).trim(); // 供应商id
 			String orderNo = objMap.get(key);
 			Commodity commodity = new Commodity();
 			commodity.setVendorId(vendorId); // 设置商品供应商id
 			OmsOrderdetail orderDetail = new OmsOrderdetail();
 			orderDetail.setWarehouseCode(warehouseCode); // 设置商品所在仓库
 			orderDetail.setOrderNo(orderNo); // 设置订单号
 			orderDetail.setCommodity(commodity); // 设置商品
 			orderdetailListByVendor.add(orderDetail);
		}
 		
		return orderdetailListByVendor;
	}
	
}

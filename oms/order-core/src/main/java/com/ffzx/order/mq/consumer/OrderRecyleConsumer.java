package com.ffzx.order.mq.consumer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.basedata.api.dto.Partner;
import com.ffzx.basedata.api.service.PartnerApiService;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.mq.AbstractMessageConsumer;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.order.constant.OrderDetailStatusConstant;
import com.ffzx.order.model.OrderRecycle;
import com.ffzx.order.service.OrderRecycleService;
import com.ffzx.wms.api.dto.OutboundCustomerOrderApiVo;

@Component
public class OrderRecyleConsumer extends AbstractMessageConsumer<List<OutboundCustomerOrderApiVo>>{
	
	protected final Logger logger = LoggerFactory.getLogger(OrderRecyleConsumer.class);
	
	@Autowired
	OrderRecycleService orderRecycleService;
	
	@Autowired
	PartnerApiService partnerApiService;
	
	@Override
	public void onMessage(List<OutboundCustomerOrderApiVo> msgList) {
 		logger.info("接受到MQ消息中队列QUEUE_WMS_OUTBOUND_CUSTOMER_ORDER的数据：" + JsonMapper.toJsonString(msgList));
		
		if (msgList.size() > 0) {
			//处理业务逻辑
			doProcessData(msgList);
			logger.info("接受到MQ消息中队列QUEUE_WMS_OUTBOUND_CUSTOMER_ORDER的数据处理成功");
		}
		
	}
	
	
	private void doProcessData(List<OutboundCustomerOrderApiVo> msgList){
		for (int i = 0; i < msgList.size(); i++) {
			try {
				OutboundCustomerOrderApiVo outboundCustomerOrderApiVo = JsonMapper.convertVal(msgList.get(i),
						OutboundCustomerOrderApiVo.class);
				doProcessLogic(outboundCustomerOrderApiVo);
			} catch (Exception e) {
				logger.error("异常：", e);
			}
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	private void doProcessLogic(OutboundCustomerOrderApiVo outboundCustomerOrderApiVo) throws Exception{
		// 判断该单号是否已插入
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNoSigned", outboundCustomerOrderApiVo.getPrintNo());
		params.put("saleNo", outboundCustomerOrderApiVo.getSalesOrderNo());
		params.put("commodityBarCode", outboundCustomerOrderApiVo.getCommoditySkuBarcode());
		List<OrderRecycle> orderRecyclelist = orderRecycleService.queryByParamsForpeat(params);
		
		if (orderRecyclelist.size() > 0) {
			logger.info("数据重复：" + params);
			return; // 不予许重复插入
		}
		OrderRecycle orderRecycle = covertApiVoToOrderRecycle(outboundCustomerOrderApiVo);
		orderRecycleService.add(orderRecycle);
	}
	
	
	private OrderRecycle covertApiVoToOrderRecycle(OutboundCustomerOrderApiVo outboundCustomerOrderApiVo){
		OrderRecycle orderRecycle = new OrderRecycle();
		orderRecycle.setId(UUIDGenerator.getUUID());
		orderRecycle.setOrderNoSigned(outboundCustomerOrderApiVo.getPrintNo());
		orderRecycle.setBarCodeSigned(outboundCustomerOrderApiVo.getPrintNo());
		orderRecycle.setCommodityBarCode(outboundCustomerOrderApiVo.getCommoditySkuBarcode());
		orderRecycle.setCommodityMoney(outboundCustomerOrderApiVo.getCommodityRetailPrice());
		orderRecycle.setCommodityName(outboundCustomerOrderApiVo.getCommodityName());
		orderRecycle.setCommodityNum(outboundCustomerOrderApiVo.getCommodityQty());  //交易数量
		
		//交易金额=单价*数量
		BigDecimal price = outboundCustomerOrderApiVo.getCommodityRetailPrice()
				.multiply(new BigDecimal(outboundCustomerOrderApiVo.getCommodityQty()));
		orderRecycle.setCommodityPrice(price);    //交易金额
		
		orderRecycle.setWarehouseCalled(outboundCustomerOrderApiVo.getWarehouseName());
		orderRecycle.setDeliveryDate(outboundCustomerOrderApiVo.getOutboundDate());   // 出库时间
		orderRecycle.setFinanceRecyleStatus(OrderDetailStatusConstant.RECOVERED_NOT);
		orderRecycle.setLogisticsRecyleStatus(OrderDetailStatusConstant.RECOVERED_NOT);
		orderRecycle.setPartner(outboundCustomerOrderApiVo.getPartner());
		
		String address = outboundCustomerOrderApiVo.getPartnerProvince() + outboundCustomerOrderApiVo.getPartnerCity()
				+ outboundCustomerOrderApiVo.getPartnerCounty() + outboundCustomerOrderApiVo.getPartnerTown()
				+ outboundCustomerOrderApiVo.getPartnerVillage() + outboundCustomerOrderApiVo.getPartnerTeam();
		orderRecycle.setPartnerAddress(address);
		
		orderRecycle.setPartnerPhone(outboundCustomerOrderApiVo.getPartnerPhone());
		orderRecycle.setSaleNo(outboundCustomerOrderApiVo.getSalesOrderNo());    //销售订单号
		orderRecycle.setSaleNoDelivery(outboundCustomerOrderApiVo.getSourceNo());  //发货订单编号
		//远程调用base-data 获取合伙人工号
		String partnerCode = null;
		
		try {
			long currentTime = System.currentTimeMillis();
			if (StringUtils.isNotBlank(outboundCustomerOrderApiVo.getPartnerId())) {
				ResultDto dto = partnerApiService.getPartnerId(outboundCustomerOrderApiVo.getPartnerId());
				Map<String, Object> dataMap = (Map<String, Object>) dto.getData();
				partnerCode = (dataMap.get("partner") == null ? null : ((Partner) dataMap.get("partner")).getPartnerCode());
			}
			logger.info("远程调用base-data 获取合伙人工号partnerCode=" + partnerCode + ", 耗时："
					+ (System.currentTimeMillis() - currentTime));

		} catch (Exception e) {
			logger.error("远程获取合伙人工号异常", e);
		}
		
		orderRecycle.setPartnerNo(partnerCode);
		return orderRecycle;
	}
	
}

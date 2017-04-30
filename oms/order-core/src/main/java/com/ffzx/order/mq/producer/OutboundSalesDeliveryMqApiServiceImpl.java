/**
 * 
 */
package com.ffzx.order.mq.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.basedata.api.dto.Partner;
import com.ffzx.basedata.api.service.PartnerApiService;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.dto.OutboundSalesDeliveryApiVo;
import com.ffzx.order.api.dto.OutboundSalesDeliveryItemsApiVo;
import com.ffzx.order.api.enums.BuyTypeEnum;
import com.ffzx.order.api.enums.PayTypeEnum;
import com.ffzx.order.api.service.OutboundSalesDeliveryMqApiService;
import com.ffzx.order.service.OmsOrderService;

/**
 * @Description: 发货订单数据推送
 * @author zi.luo
 * @email  zi.luo@ffzxnet.com
 * @date 2016年6月7日 上午10:32:24
 * @version V1.0 
 *
 */
@Service("outboundSalesDeliveryMqApiService")
public class OutboundSalesDeliveryMqApiServiceImpl implements OutboundSalesDeliveryMqApiService{

	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(OutboundSalesDeliveryMqApiServiceImpl.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private PartnerApiService partnerApiService;
	@Autowired
	private OmsOrderService omsOrderService;
	@Override
	public void outboundSalesDelivery(OmsOrder order) {
		OutboundSalesDeliveryApiVo outboundSalesDeliveryApiVo = new OutboundSalesDeliveryApiVo();
		logger.info("【"+order.getOrderNo()+"】订单推送到wms ==============>>订单信息："+JsonMapper.toJsonString(order));
		try {
			outboundSalesDeliveryApiVo.setSalesOrderNo(order.getOrderNo());
			outboundSalesDeliveryApiVo.setSalesOrderNoSub(order.getOrderNo());
			if(order.getBuyType().getValue().equals(BuyTypeEnum.COMMON_BUY.getValue())){
				outboundSalesDeliveryApiVo.setBuyType(0);
			}else if(order.getBuyType().getValue().equals(BuyTypeEnum.NEWUSER_VIP.getValue())){
				outboundSalesDeliveryApiVo.setBuyType(3);
			}
			else if(order.getBuyType().getValue().equals(BuyTypeEnum.PRE_SALE.getValue())){
				outboundSalesDeliveryApiVo.setBuyType(1);
			}
			else if(order.getBuyType().getValue().equals(BuyTypeEnum.PANIC_BUY.getValue())){
				outboundSalesDeliveryApiVo.setBuyType(2);
			}
			else if(order.getBuyType().getValue().equals(BuyTypeEnum.ORDINARY_ACTIVITY.getValue())){
				outboundSalesDeliveryApiVo.setBuyType(4);
			}else if(order.getBuyType().getValue().equals(BuyTypeEnum.WHOLESALE_MANAGER.getValue())){
				outboundSalesDeliveryApiVo.setBuyType(5);
			}
			outboundSalesDeliveryApiVo.setCustomerPhone(order.getConsignPhone());
			outboundSalesDeliveryApiVo.setCustomerName(order.getConsignName());
			outboundSalesDeliveryApiVo.setCustomerId(order.getMemberId());
			outboundSalesDeliveryApiVo.setCustomerProvince(order.getAddressInfo());
			outboundSalesDeliveryApiVo.setPartnerId(order.getPartnerId());
			outboundSalesDeliveryApiVo.setPartnerName(order.getSendPersonName());
			outboundSalesDeliveryApiVo.setPartnerPhone(order.getSendPersonPhone());   //合伙人电话订单字段暂时还没有，之后会加上
			outboundSalesDeliveryApiVo.setPartnerAddressId(this.getPartnerAddressId(order.getOrderNo(),order.getPartnerId()));
			outboundSalesDeliveryApiVo.setOrderAmount(order.getTotalPrice().toString());
			if(order.getFavorablePrice() != null && "".equals(order.getFavorablePrice())){
				outboundSalesDeliveryApiVo.setDiscountAmount(order.getFavorablePrice().toString());
			}else{
				outboundSalesDeliveryApiVo.setDiscountAmount("0");
			}
			outboundSalesDeliveryApiVo.setPayAmount(order.getActualPrice().toString());
			outboundSalesDeliveryApiVo.setFreightAmount(order.getSendCost().toString());
			outboundSalesDeliveryApiVo.setOrderTime(order.getCreateDate());
			if(order.getPayType().getValue().equals(PayTypeEnum.ALIPAYPC.getValue())){
				outboundSalesDeliveryApiVo.setPayType(0);
			}else if(order.getPayType().getValue().equals(PayTypeEnum.ALIPAYSM.getValue())){
				outboundSalesDeliveryApiVo.setPayType(1);
			}else if(order.getPayType().getValue().equals(PayTypeEnum.WXPAY.getValue())){
				outboundSalesDeliveryApiVo.setPayType(2);
			}else if(order.getPayType().getValue().equals(PayTypeEnum.ALIPAYAPP.getValue())){
				outboundSalesDeliveryApiVo.setPayType(3);
			}
			if(StringUtil.isNotNull(order.getIsSupplier())){
				outboundSalesDeliveryApiVo.setInventorySupplier(Integer.parseInt(order.getIsSupplier()));
			}else{
				throw new Exception("库存在供应商字段缺失");
			}
			outboundSalesDeliveryApiVo.setInventoryType(0);
			outboundSalesDeliveryApiVo.setWarehouseCode(order.getWarehouseCode());
			outboundSalesDeliveryApiVo.setStorageId(order.getStorageId());
			List<OutboundSalesDeliveryItemsApiVo> itemsList = new ArrayList<OutboundSalesDeliveryItemsApiVo>();
			List<OmsOrderdetail> virtualItemsList = new  ArrayList<OmsOrderdetail>();//虚拟商品明细
			if(order.getDetailList() != null && order.getDetailList().size() >0){
				List<OmsOrderdetail> detailsList = order.getDetailList();
				for(OmsOrderdetail detail : detailsList){
					if(StringUtil.isNotNull(detail.getVirtualGdId())){
						//虚拟商品处理方式：推送到prms，通知prms进行业务处理
						//virtualItemsList.add(detail);
					}else{	
					//非虚拟商品出库处理方式：推送消息到wms,通知仓库出库
					OutboundSalesDeliveryItemsApiVo outboundSalesDeliveryItemsApiVo = new OutboundSalesDeliveryItemsApiVo();
					outboundSalesDeliveryItemsApiVo.setCommoditySkuCode(detail.getSkuCode());
					outboundSalesDeliveryItemsApiVo.setQty(detail.getBuyNum());
					itemsList.add(outboundSalesDeliveryItemsApiVo);
					}
				}
			}
			if(itemsList.size()>0){
			outboundSalesDeliveryApiVo.setItemsList(itemsList);
			rabbitTemplate.convertAndSend("QUEUE_WMS_OUTBOUND_SALES_DELIVERY", outboundSalesDeliveryApiVo);
			logger.info("订单推送到wms ========================>>"+JsonMapper.toJsonString(outboundSalesDeliveryApiVo));
			}
			if(virtualItemsList.size()>0){
				OmsOrder virtualOmsOrder = (OmsOrder) order.clone();
				virtualOmsOrder.setDetailList(virtualItemsList);
				logger.info("订单推送到prms ========================明细>>"+JsonMapper.toJsonString(virtualItemsList));
				omsOrderService.virtualItemsHandle(virtualOmsOrder);
			}
			
		} catch (Exception e) {
			logger.error("发货订单数据推送 : invoke OutboundSalesDeliveryMqApiServiceImpl.outboundSalesDelivery error ===>>>",e);
			throw new ServiceException(e);
		}
	}
	private String getPartnerAddressId(String orderNo,String partnerId){
		logger.info("【"+orderNo+"】获取合伙人信息partnerId："+partnerId);
		String partnerAddressId = "";
		try{
			ResultDto dto = partnerApiService.getPartnerId(partnerId);
			if(!dto.getCode().equals(ResultDto.OK_CODE )){
				logger.error("【"+orderNo+"】获取合伙人信息异常："+dto.getMessage());
			}
			Map<String, Object> dataMap = (Map<String, Object>) dto.getData();
			Partner partner = null;
			if(StringUtil.isNotNull(dto.getData())){
				partner =(Partner) dataMap.get("partner");
				if(null!=partner.getAddress()){
					partnerAddressId = partner.getAddress().getId();
				}else{
					logger.error("【"+orderNo+"】获取合伙人地址异常："+dto.getMessage());
				}
			}
			}catch(Exception e1){
				logger.error("【"+orderNo+"】获取合伙人地址异常:",e1);
			}
		return partnerAddressId;
	}
}

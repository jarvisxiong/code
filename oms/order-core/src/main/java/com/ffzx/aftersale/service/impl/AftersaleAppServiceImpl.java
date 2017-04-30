package com.ffzx.aftersale.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.aftersale.api.service.exception.CallInterfaceExceptionApi;
import com.ffzx.aftersale.constant.ApiConstants;
import com.ffzx.aftersale.service.AftersaleAppService;
import com.ffzx.basedata.api.service.CodeRuleApiService;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.order.api.dto.AftersaleApply;
import com.ffzx.order.api.dto.AftersaleApplyItem;
import com.ffzx.order.api.dto.AftersalePickup;
import com.ffzx.order.api.dto.AftersaleRefund;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.enums.AfterSaleChangeReasons;
import com.ffzx.order.api.enums.AfterSaleReMoneyReasons;
import com.ffzx.order.api.enums.AfterSaleRefundReasons;
import com.ffzx.order.api.enums.AfterSaleStatusType;
import com.ffzx.order.api.enums.OrderStatusEnum;
import com.ffzx.order.api.service.AftersaleApplyApiService;
import com.ffzx.order.api.service.AftersaleApplyItemApiService;
import com.ffzx.order.api.service.OrderApiService;

public class AftersaleAppServiceImpl implements AftersaleAppService {
	@Autowired
	private AftersaleApplyApiService aftersaleApplyApiService;
	@Autowired
	private CodeRuleApiService codeRuleApiService;
	@Autowired
	private OrderApiService orderApiService;
	@Autowired
	private AftersaleApplyItemApiService aftersaleApplyItemApiService;
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(AftersaleAppServiceImpl.class);
	
	
	
//	@Override
//	@Transactional(rollbackFor=Exception.class)
//	public Map<String, Object> saveAftersaleApply(Map<String, Object> map) {
//		Map<String, Object> dateMap=null;
//		String aftersaleDay=System.getProperty("aftersale.overdue");//具体售后时间
//		AftersaleApply apply=null;
//		OmsOrder order=null;		
//		String orderId=map.get("orderId").toString();
//		String serviceType=map.get("serviceType").toString();
//		String number=map.get("number").toString();
//		String reason=map.get("reason").toString();
//		String userId=map.get("userId").toString();
//		order=getOrderById(orderId);
//		String desc=null;
//		String imgPaths=null;
//		if(map.get("desc")!=null){
//			desc=map.get("desc").toString();
//		}
//		if(map.get("imgPaths")!=null){
//			imgPaths=map.get("imgPaths").toString();
//		}
//		String aftersaleId=null;			
//		
//		//退款没收到货
//		if(serviceType.equals(AfterSaleStatusType.REFUND_TOTAL.getValue())){
//			if(!order.getStatus().equals(OrderStatusEnum.RECEIPT_OF_GOODS.getValue())){
//				dateMap=new HashMap<String, Object>();
//				dateMap.put(AppConstants.RESULT_MSG, AppConstants.AFTERSALE_ORDERERROR);
//				return dateMap;	
//			}else{
//				apply=new AftersaleApply();
//				apply.setApplyPersonAddress(order.getAddressInfo());				
//				aftersaleId=UUIDGenerator.getUUID();
//				apply.setId(aftersaleId);
//				ResultDto aftersaleCode= codeRuleApiService.getCodeRule("order", "order_aftersaleapply_code");
//				apply.setApplyNo(aftersaleCode.getData().toString());
//				List<AftersaleApplyItem> listApplyItem=new ArrayList<AftersaleApplyItem>();
//				List<String> listdetailId=new ArrayList<String>();
//				List<OmsOrderdetail> orderDetailList=getOrderDetailList(number);
//				if(orderDetailList!=null && orderDetailList.size()!=0){
//					for(OmsOrderdetail detail:orderDetailList){								
//						setAftersaleItem(aftersaleId, aftersaleCode, listApplyItem, listdetailId, detail);
//					}
//					setAftersaleApply(serviceType, reason, desc, imgPaths, userId, number, apply, order);
//					Map<String, Object> params=new HashMap<String, Object>();
//					params.put("aftersaleApply", apply);
//					params.put("listApplyItem", listApplyItem);
//					params.put("listdetailId", listdetailId);
//					params.put("orderId", orderId);
//					params.put("orderDetailStatus", AppConstants.STATUS_REFING);
//					params.put("serviceType", serviceType);
//					params.put("orderStatus", AppConstants.STATUS_REFUND_APPLICATION);
//					try {
//						this.aftersaleApplyApiService.saveAftersaleApply(params);
//						dateMap=new HashMap<String, Object>();
//						dateMap.put("afterSalesId", aftersaleId);
//					} catch (Exception e) {
//						logger.error("" , e);
//						throw new CallInterfaceException(AppConstants.RESULT_TYPE_2,"申请售后失败"); 
//					}
//					
//				}
//			}
//		}
//		
//		//退货已收到货或者换货已收到货
//		if(serviceType.equals(AfterSaleStatusType.REFUND_GOODS.getValue()) || serviceType.equals(AfterSaleStatusType.CHANGE_GOODS.getValue())){
//			//交易完成的						
//			Date tranTime=order.getTranTime();//订单交易完成时间
//			if(tranTime!=null){
//				Date aftersaleTime=DateUtil.getNextDay(tranTime, Integer.valueOf(aftersaleDay));//允许申请售后的最后时间
//					//当申请售后的时间已经超过了允许申请售后的时间给予提示
//				if(new Date().getTime()>aftersaleTime.getTime()){
//					dateMap=new HashMap<String, Object>();
//					dateMap.put(AppConstants.RESULT_MSG, AppConstants.AFTERSALE_DELAY);
//					return dateMap;	
//				}
//			}
//			List<AftersaleApplyItem> listApplyItem=new ArrayList<AftersaleApplyItem>();
//			List<String> listdetailId=new ArrayList<String>();
//			String skuId=map.get("skuId").toString();
//			String count=map.get("count").toString();
//			OmsOrderdetail detail=getOrderDetail(number, skuId);
//			if(detail!=null){
//				if(!detail.getOrderDetailStatus().equals(AppConstants.STATUS_NOR)){
//					dateMap=new HashMap<String, Object>();
//					dateMap.put(AppConstants.RESULT_MSG, AppConstants.AFTERSALE_ERROR);
//					return dateMap;	
//				}else{
//					apply=new AftersaleApply();
//					apply.setApplyPersonAddress(order.getAddressInfo());				
//					aftersaleId=UUIDGenerator.getUUID();
//					apply.setId(aftersaleId);
//					ResultDto aftersaleCode= codeRuleApiService.getCodeRule("order", "order_aftersaleapply_code");
//					apply.setApplyNo(aftersaleCode.getData().toString());
//					
//					setAftersaleApply(serviceType, reason, desc, imgPaths, userId, number, apply, order);
//					//售后详情对象
//					setOneAftersaleItem(serviceType, skuId, count, aftersaleId, listApplyItem, listdetailId,detail, aftersaleCode);
//					Map<String, Object> params=new HashMap<String, Object>();
//					params.put("aftersaleApply", apply);
//					params.put("listApplyItem", listApplyItem);
//					params.put("listdetailId", listdetailId);
//					if(serviceType.equals(AfterSaleStatusType.REFUND_GOODS.getValue())){							
//						params.put("orderDetailStatus", AppConstants.STATUS_REFING);
//					}else if(serviceType.equals(AfterSaleStatusType.CHANGE_GOODS.getValue())){
//						//换货已收到货修改订单详情商品状态为换货处理中
//						params.put("orderDetailStatus", AppConstants.STATUS_CHANGING);
//					}	
//					params.put("serviceType", serviceType);
//					try {
//						this.aftersaleApplyApiService.saveAftersaleApply(params);
//						dateMap=new HashMap<String, Object>();
//						dateMap.put("afterSalesId", aftersaleId);
//					} catch (Exception e) {
//						logger.error("" , e);
//						throw new CallInterfaceException(AppConstants.RESULT_TYPE_2,"申请售后失败"); 
//					}
//				}
//			}				
//		}				
//		return dateMap;	
//	}
	
	private void setAftersaleApply(String serviceType, String reason, String desc, String imgPaths, String userId,
			String number, AftersaleApply apply, OmsOrder order) {
		apply.setApplyStatus(Constant.NO);
		apply.setApplyPersonId(userId);
		apply.setOrder(order);
		apply.setOrderNo(number);
		apply.setApplyType(serviceType);
		apply.setReasonSelect(reason);
		apply.setReasonExplain(desc);
		apply.setCreateDate(new Date());
		apply.setCreateBy(order.getCreateBy());
		apply.setApplyPersonName(order.getMemberName());
		apply.setApplyPersonPhone(order.getMemberPhone());				
		apply.setLastUpdateDate(new Date());
		apply.setApplyPicImg(imgPaths);
	}

	private void setOneAftersaleItem(String serviceType, String skuId, String count, String aftersaleId,
			List<AftersaleApplyItem> listApplyItem, List<String> listdetailId, OmsOrderdetail detail,
			ResultDto aftersaleCode) {
		AftersaleApplyItem item=new AftersaleApplyItem();
		item.setId(UUIDGenerator.getUUID());
		item.setCommodityPic(detail.getCommodityImage());
		item.setCommodityName(detail.getCommodityTitle());
		item.setCommodityBarcode(detail.getCommodityBarcode());
		item.setCommoditySpecifications(detail.getCommoditySpecifications());
		item.setCommodityUnit(detail.getCommodityUnit());
		item.setCommodityPrice(detail.getActSalePrice());
		item.setActPayAmount(detail.getActPayAmount());
		if(detail.getBuyType()!=null){
			item.setCommodityBuyType(detail.getBuyType().getValue());
		}
		item.setCommodityBuyNum(detail.getBuyNum());
		item.setReturnNum(Integer.valueOf(count));						
		if(serviceType.equals(AfterSaleStatusType.REFUND_GOODS.getValue())){
			item.setAftersaleStatus(ApiConstants.STATUS_REFING);
		}else if(serviceType.equals(AfterSaleStatusType.CHANGE_GOODS.getValue())){
			item.setAftersaleStatus(ApiConstants.STATUS_CHANGING);
		}					
		item.setApplyId(aftersaleId);
		item.setSkuId(skuId);
		item.setFavouredAmount(detail.getFavouredAmount());
		item.setRedPacketAmount(detail.getRedPacketAmount());
		item.setApplyNo(aftersaleCode.getData().toString());
		item.setCommodityAttributeValues(detail.getCommodityAttributeValues());
		listApplyItem.add(item);
		listdetailId.add(detail.getId());
	}

	private void setAftersaleItem(String aftersaleId, ResultDto aftersaleCode, List<AftersaleApplyItem> listApplyItem,
			List<String> listdetailId, OmsOrderdetail detail) {
		AftersaleApplyItem item=new AftersaleApplyItem();
		item.setId(UUIDGenerator.getUUID());
		item.setCommodityPic(detail.getCommodityImage());
		item.setCommodityName(detail.getCommodityTitle());
		item.setCommoditySpecifications(detail.getCommoditySpecifications());
		item.setCommodityUnit(detail.getCommodityUnit());
		item.setCommodityPrice(detail.getActSalePrice());
		item.setActPayAmount(detail.getActPayAmount());
		item.setCommodityBarcode(detail.getCommodityBarcode());
		item.setBuyGifts(detail.getBuyGifts());
		item.setMainCommodityId(detail.getOrderdetailId());
		if(detail.getBuyType()!=null){
			item.setCommodityBuyType(detail.getBuyType().getValue());
		}
		item.setCommodityBuyNum(detail.getBuyNum());
		item.setReturnNum(detail.getBuyNum());
		item.setAftersaleStatus(ApiConstants.STATUS_REFING);
		item.setApplyId(aftersaleId);
		item.setApplyNo(aftersaleCode.getData().toString());
		item.setSkuId(detail.getSkuId());
		item.setFavouredAmount(detail.getFavouredAmount());
		item.setCommodityAttributeValues(detail.getCommodityAttributeValues());
		listApplyItem.add(item);			
		listdetailId.add(detail.getId());
	}
	
	/**
	 * Add by Qin.Huang 2016-09-19
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> saveAftersaleApplyNew(Map<String, Object> map) {
		Map<String, Object> dateMap=null;
		String aftersaleDay=System.getProperty("aftersale.overdue");//具体售后时间
		AftersaleApply apply=null;
		OmsOrder order=null;		
		String orderId=map.get("orderId").toString();
		String serviceType=map.get("serviceType").toString();
		String number=map.get("number").toString();
		String reason=map.get("reason").toString();
		String userId=map.get("userId").toString();
		order=getOrderById(orderId);
		String desc=null;
		String imgPaths=null;
		if(map.get("desc")!=null){
			desc=map.get("desc").toString();
		}
		if(map.containsKey("imgPaths")&&map.get("imgPaths")!=null){
			imgPaths=map.get("imgPaths").toString();
		}
		String aftersaleId=null;			
		
		//退款没收到货
		if(serviceType.equals(AfterSaleStatusType.REFUND_TOTAL.getValue())){
			if(!order.getStatus().equals(OrderStatusEnum.RECEIPT_OF_GOODS.getValue())){
				dateMap=new HashMap<String, Object>();
				dateMap.put(ApiConstants.RESULT_MSG, ApiConstants.AFTERSALE_ORDERERROR);
				return dateMap;	
			}else{
				apply=new AftersaleApply();
				apply.setApplyPersonAddress(order.getAddressInfo());				
				aftersaleId=UUIDGenerator.getUUID();
				apply.setId(aftersaleId);
				ResultDto aftersaleCode= codeRuleApiService.getCodeRule("order", "order_aftersaleapply_code");
				apply.setApplyNo(aftersaleCode.getData().toString());
				List<AftersaleApplyItem> listApplyItem=new ArrayList<AftersaleApplyItem>();
				List<String> listdetailId=new ArrayList<String>();
				List<OmsOrderdetail> orderDetailList=getOrderDetailList(number);
				if(orderDetailList!=null && orderDetailList.size()!=0){
					for(OmsOrderdetail detail:orderDetailList){								
						setAftersaleItemNew(aftersaleId, aftersaleCode, listApplyItem, listdetailId, detail);
					}
					setAftersaleApply(serviceType, reason, desc, imgPaths, userId, number, apply, order);
					Map<String, Object> params=new HashMap<String, Object>();
					params.put("aftersaleApply", apply);
					params.put("listApplyItem", listApplyItem);
					params.put("listdetailId", listdetailId);
					params.put("orderId", orderId);
					params.put("orderDetailStatus", ApiConstants.STATUS_REFING);
					params.put("serviceType", serviceType);
					params.put("orderStatus", ApiConstants.STATUS_REFUND_APPLICATION);
					try {
						this.aftersaleApplyApiService.saveAftersaleApply(params);
						dateMap=new HashMap<String, Object>();
						dateMap.put("afterSalesId", aftersaleId);
					} catch (Exception e) {
						logger.error("" , e);
						throw new CallInterfaceExceptionApi(ApiConstants.RESULT_TYPE_2,"申请售后失败"); 
					}
					
				}
			}
		}
		
		//退货已收到货或者换货已收到货
		if(serviceType.equals(AfterSaleStatusType.REFUND_GOODS.getValue()) || serviceType.equals(AfterSaleStatusType.CHANGE_GOODS.getValue())){
			//交易完成的						
			Date tranTime=order.getTranTime();//订单交易完成时间
			if(tranTime!=null){
				Date aftersaleTime=DateUtil.getNextDay(tranTime, Integer.valueOf(aftersaleDay));//允许申请售后的最后时间
					//当申请售后的时间已经超过了允许申请售后的时间给予提示
				if(new Date().getTime()>aftersaleTime.getTime()){
					dateMap=new HashMap<String, Object>();
					dateMap.put(ApiConstants.RESULT_MSG, ApiConstants.AFTERSALE_DELAY);
					return dateMap;	
				}
			}
			List<AftersaleApplyItem> listApplyItem=new ArrayList<AftersaleApplyItem>();
			List<String> listdetailId=new ArrayList<String>();
			String skuId=map.get("skuId").toString();
			String count=map.get("count").toString();
			OmsOrderdetail detail=getOrderDetail(number, skuId);
			if(detail!=null){
				if(!detail.getOrderDetailStatus().equals(ApiConstants.STATUS_NOR)){
					dateMap=new HashMap<String, Object>();
					dateMap.put(ApiConstants.RESULT_MSG, ApiConstants.AFTERSALE_ERROR);
					return dateMap;	
				}else{
					apply=new AftersaleApply();
					apply.setApplyPersonAddress(order.getAddressInfo());				
					aftersaleId=UUIDGenerator.getUUID();
					apply.setId(aftersaleId);
					ResultDto aftersaleCode= codeRuleApiService.getCodeRule("order", "order_aftersaleapply_code");
					apply.setApplyNo(aftersaleCode.getData().toString());
					
					setAftersaleApply(serviceType, reason, desc, imgPaths, userId, number, apply, order);
					//售后详情对象
					//根据主商品的订单明细ID查赠品明细
					List<OmsOrderdetail> giftOrderdetailList = null;
					if(detail!=null && "1".equals(detail.getBuyGifts())){
						giftOrderdetailList = findGifts(detail.getId());
						//计算退、换赠品的数量
						for (OmsOrderdetail omsOrderdetail : giftOrderdetailList) {
							int giftNum = getReturnGiftNum(omsOrderdetail.getSingleGiftNum(), omsOrderdetail.getTriggerNum(), omsOrderdetail.getBuyNum(), Integer.valueOf(count));
							omsOrderdetail.setGiftNum(giftNum);						
						}
					}
					
					setOneAftersaleItemNew(serviceType, skuId, count, aftersaleId, listApplyItem, listdetailId,detail, aftersaleCode,giftOrderdetailList);
					Map<String, Object> params=new HashMap<String, Object>();
					params.put("aftersaleApply", apply);
					params.put("listApplyItem", listApplyItem);
					params.put("listdetailId", listdetailId);
					if(serviceType.equals(AfterSaleStatusType.REFUND_GOODS.getValue())){							
						params.put("orderDetailStatus", ApiConstants.STATUS_REFING);
					}else if(serviceType.equals(AfterSaleStatusType.CHANGE_GOODS.getValue())){
						//换货已收到货修改订单详情商品状态为换货处理中
						params.put("orderDetailStatus", ApiConstants.STATUS_CHANGING);
					}	
					params.put("serviceType", serviceType);
					try {
						this.aftersaleApplyApiService.saveAftersaleApply(params);
						dateMap=new HashMap<String, Object>();
						dateMap.put("afterSalesId", aftersaleId);
					} catch (Exception e) {
						logger.error("" , e);
						throw new CallInterfaceExceptionApi(ApiConstants.RESULT_TYPE_2,"申请售后失败"); 
					}
				}
			}				
		}				
		return dateMap;	
	}

	/***
	 * 
	 * @param applyParamsList
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年1月3日 上午11:40:26
	 * @version V1.0
	 * @return 
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> batchAftersaleApply(List<Map<String, Object>> applyParamsList) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<>();
			List<String> afterSalesIdList = new ArrayList<>();
			for (Map<String, Object> map : applyParamsList) {
				String orderNo =  (String) map.get("number");
				String orderId=map.get("orderId").toString();
				logger.info("批量申请售后【"+orderNo+"】");
				map.put("orderStatus", ApiConstants.STATUS_REFUND_APPLICATION);		
				ResultDto dto= this.aftersaleApplyApiService.saveAftersaleApply(map);
				if(!Constant.NO.equals(dto.getCode())){
					resultMap.put("orderNo", orderNo);
					resultMap.put("orderId", orderId);
					return resultMap;
				}else{
					AftersaleApply a=(AftersaleApply) map.get("aftersaleApply");
					afterSalesIdList.add(a.getId());
				}
			}
			resultMap.put("afterSalesIdList", afterSalesIdList);
			return resultMap;
	}	
	
	private void setAftersaleItemNew(String aftersaleId, ResultDto aftersaleCode, List<AftersaleApplyItem> listApplyItem,
			List<String> listdetailId, OmsOrderdetail detail) {
		AftersaleApplyItem item=new AftersaleApplyItem();
		item.setId(UUIDGenerator.getUUID());
		item.setCommodityPic(detail.getCommodityImage());
		item.setCommodityName(detail.getCommodityTitle());
		item.setCommoditySpecifications(detail.getCommoditySpecifications());
		item.setCommodityUnit(detail.getCommodityUnit());
		item.setCommodityPrice(detail.getActSalePrice());
		item.setActPayAmount(detail.getActPayAmount());
		if("2".equals(detail.getBuyGifts())){
			item.setCommodityPrice(new BigDecimal(0D));
			item.setActPayAmount(new BigDecimal(0D));
		}
		item.setCommodityBarcode(detail.getCommodityBarcode());
		if(detail.getBuyType()!=null){
			item.setCommodityBuyType(detail.getBuyType().getValue());
		}
		item.setCommodityBuyNum(detail.getBuyNum());
		item.setReturnNum(detail.getBuyNum());
		item.setAftersaleStatus(ApiConstants.STATUS_REFING);
		item.setApplyId(aftersaleId);
		item.setApplyNo(aftersaleCode.getData().toString());
		item.setSkuId(detail.getSkuId());
		item.setFavouredAmount(detail.getFavouredAmount());
		//红包总金额
		item.setRedPacketAmount(detail.getRedPacketAmount());
		item.setCommodityAttributeValues(detail.getCommodityAttributeValues());
		//Add by Qin.Huang 2016-09-19
		item.setMainCommodityId(detail.getId());
		item.setBuyGifts(detail.getBuyGifts());
		listApplyItem.add(item);			
		listdetailId.add(detail.getId());
	}
	
	private void setOneAftersaleItemNew(String serviceType, String skuId, String count, String aftersaleId,
			List<AftersaleApplyItem> listApplyItem, List<String> listdetailId, OmsOrderdetail detail,
			ResultDto aftersaleCode,List<OmsOrderdetail> giftOrderdetailList) {
		AftersaleApplyItem item=new AftersaleApplyItem();
		item.setId(UUIDGenerator.getUUID());
		item.setCommodityPic(detail.getCommodityImage());
		item.setCommodityName(detail.getCommodityTitle());
		item.setCommodityBarcode(detail.getCommodityBarcode());
		item.setCommoditySpecifications(detail.getCommoditySpecifications());
		item.setCommodityUnit(detail.getCommodityUnit());
		item.setCommodityPrice(detail.getActSalePrice());
		item.setActPayAmount(detail.getActPayAmount());
		if(detail.getBuyType()!=null){
			item.setCommodityBuyType(detail.getBuyType().getValue());
		}
		item.setCommodityBuyNum(detail.getBuyNum());
		item.setReturnNum(Integer.valueOf(count));						
		if(serviceType.equals(AfterSaleStatusType.REFUND_GOODS.getValue())){
			item.setAftersaleStatus(ApiConstants.STATUS_REFING);
		}else if(serviceType.equals(AfterSaleStatusType.CHANGE_GOODS.getValue())){
			item.setAftersaleStatus(ApiConstants.STATUS_CHANGING);
		}					
		item.setApplyId(aftersaleId);
		item.setSkuId(skuId);
		item.setFavouredAmount(detail.getFavouredAmount());
		item.setRedPacketAmount(detail.getRedPacketAmount());
		item.setApplyNo(aftersaleCode.getData().toString());
		item.setCommodityAttributeValues(detail.getCommodityAttributeValues());
		//Add by Qin.Huang 2016-09-19
		item.setBuyGifts(detail.getBuyGifts());
		
		listApplyItem.add(item);
		listdetailId.add(detail.getId());
		//Add by Qin.Huang 2016-09-19
		if(null != giftOrderdetailList){
			for (OmsOrderdetail giftOrderdetail : giftOrderdetailList) {
				AftersaleApplyItem giftItem=new AftersaleApplyItem();
				giftItem.setId(UUIDGenerator.getUUID());
				giftItem.setCommodityPic(giftOrderdetail.getCommodityImage());
				giftItem.setCommodityName(giftOrderdetail.getCommodityTitle());
				giftItem.setCommodityBarcode(giftOrderdetail.getCommodityBarcode());
				giftItem.setCommoditySpecifications(giftOrderdetail.getCommoditySpecifications());
				giftItem.setCommodityUnit(giftOrderdetail.getCommodityUnit());
				giftItem.setCommodityPrice(giftOrderdetail.getActSalePrice());
				if(giftOrderdetail.getBuyGifts()!=null && "2".equals(giftOrderdetail.getBuyGifts())){
					giftItem.setCommodityPrice(new BigDecimal(0D));
				}
				giftItem.setActPayAmount(giftOrderdetail.getActPayAmount());
				if(giftOrderdetail.getBuyType()!=null){
					giftItem.setCommodityBuyType(giftOrderdetail.getBuyType().getValue());
				}
				giftItem.setCommodityBuyNum(giftOrderdetail.getBuyNum());
				giftItem.setReturnNum(giftOrderdetail.getGiftNum());						
				if(serviceType.equals(AfterSaleStatusType.REFUND_GOODS.getValue())){
					giftItem.setAftersaleStatus(ApiConstants.STATUS_REFING);
				}else if(serviceType.equals(AfterSaleStatusType.CHANGE_GOODS.getValue())){
					giftItem.setAftersaleStatus(ApiConstants.STATUS_CHANGING);
				}					
				giftItem.setApplyId(aftersaleId);
				giftItem.setSkuId(giftOrderdetail.getSkuId());
				giftItem.setFavouredAmount(giftOrderdetail.getFavouredAmount());
				giftItem.setRedPacketAmount(giftOrderdetail.getRedPacketAmount());
				giftItem.setApplyNo(aftersaleCode.getData().toString());
				giftItem.setCommodityAttributeValues(giftOrderdetail.getCommodityAttributeValues());
				
				giftItem.setBuyGifts(giftOrderdetail.getBuyGifts());
				giftItem.setMainCommodityId(detail.getId());
				listdetailId.add(giftOrderdetail.getId());
				
				listApplyItem.add(giftItem);
			}
		}
	}
	/**
	 * 
	* @Title: findGifts 
	* @Description: 根据主商品的订单明细ID查赠品明细
	* @param mainCommodityId
	* @return
	* @throws ServiceException
	 */
	public List<OmsOrderdetail> findGifts(String mainCommodityId)throws ServiceException{
		ResultDto result = null;
		try {
			 result = orderApiService.findGifts(mainCommodityId);
		} catch (Exception e) {
			logger.error("根据主商品的订单明细ID查赠品明细: " , e);
			throw new CallInterfaceExceptionApi(ApiConstants.RESULT_TYPE_0,e.getMessage());
		}
		if(result.getCode().equals(ResultDto.OK_CODE) ){
			return (List<OmsOrderdetail>) result.getData();
		}else{
			logger.error("根据主商品的订单明细ID查赠品明细: " , result.getMessage());
			throw new CallInterfaceExceptionApi(ApiConstants.RESULT_TYPE_2,result.getMessage());
		}
	}
	/**
	 * 
	 * 雷------2016年9月14日
	 * 
	 * @Title: getReturnGiftNum
	 * @Description: 计算退、换赠品的数量
	 * @param  singleGiftNum
	 * @param  triggerNum
	 * @param  buyNum
	 * @param  mainReturnNum
	 * @return int 返回类型
	 * @throws
	 */
	public int getReturnGiftNum(Integer singleGiftNum, Integer triggerNum, Integer buyNum, Integer mainReturnNum) throws ServiceException {
		int giftNum = (int) (Math.ceil((double) mainReturnNum / triggerNum) * singleGiftNum);
		if (buyNum < giftNum)
			return giftNum = buyNum;
		return giftNum;
	}
	/**
	 * 
	 * 雷------2016年10月31日
	 * @Title: getOrderById
	 * @Description: 迁移，根据订单id查订单
	 * @param @param orderId
	 * @param @return
	 * @param @throws ServiceException    设定文件
	 * @return OmsOrder    返回类型
	 * @throws
	 */
	public OmsOrder getOrderById(String orderId)throws ServiceException{
		logger.debug("OrderApiConsumer-getOrderById=》订单接口 - BEGIN");
		OmsOrder order=null;
		ResultDto result = null;
		try {
			result=this.orderApiService.findOrderById(orderId);
			order=(OmsOrder) result.getData();
		} catch (Exception e) {
			logger.error("OrderApiConsumer-getOrderById=》", e);
			throw new CallInterfaceExceptionApi(ApiConstants.RESULT_TYPE_0,"系统异常");
		}
		return order;
	}
	/**
	 * 
	 * 雷------2016年10月31日
	 * @Title: getOrderDetailList
	 * @Description: 迁移，根据订单号查询子订单集合
	 * @param @param orderNo
	 * @param @return
	 * @param @throws ServiceException    设定文件
	 * @return List<OmsOrderdetail>    返回类型
	 * @throws
	 */
	public List<OmsOrderdetail> getOrderDetailList(String orderNo)throws ServiceException{
		logger.debug("OrderApiConsumer-getOrderDetailList=》订单接口 - BEGIN");
		ResultDto result = null;
		List<OmsOrderdetail> detailList=null;
		try {
			result=this.orderApiService.indentSearchOne(orderNo);
			OmsOrder order=(OmsOrder) result.getData();
			detailList= order.getDetailList();
		} catch (Exception e) {
			logger.error("OrderApiConsumer-getOrderDetailList=》", e);
			throw new CallInterfaceExceptionApi(ApiConstants.RESULT_TYPE_0,"系统异常");
		}
		return detailList;
		
	}
	/**
	 * 
	 * 雷------2016年10月31日
	 * @Title: getOrderDetail
	 * @Description: 迁移，根据订单号/skuid查询子订单集合
	 * @param @param orderNo
	 * @param @param skuId
	 * @param @return
	 * @param @throws ServiceException    设定文件
	 * @return OmsOrderdetail    返回类型
	 * @throws
	 */
	public OmsOrderdetail getOrderDetail(String orderNo,String skuId)throws ServiceException{
		logger.debug("OrderApiConsumer-getOrderDetailList=》订单接口 - BEGIN");
		ResultDto result = null;
		OmsOrderdetail detail=null;
		try {
			result=this.orderApiService.findOmsOrderDetail(orderNo, skuId);
			detail=  (OmsOrderdetail) result.getData();
		} catch (Exception e) {
			logger.error("OrderApiConsumer-getOrderDetail=》", e);
			throw new CallInterfaceExceptionApi(ApiConstants.RESULT_TYPE_0,"系统异常");
		}
		return detail;
	}
	/**
	 * 
	 * 雷------2016年10月31日
	 * @Title: findAftersaleApplyPageNew
	 * @Description: 迁移，获取售后单列表
	 * @param @param apply
	 * @param @return
	 * @param @throws ServiceException    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	public Map<String, Object> findAftersaleApplyPageNew(AftersaleApply apply)throws ServiceException{
		ResultDto result = null;
		Map<String, Object> params=null;
		try {
			if(StringUtil.isNotNull(apply)){
				params=new HashMap<String, Object>();					
				result=this.aftersaleApplyApiService.findAftersaleApply(apply);
				params=(Map<String, Object>) result.getData();
				Page page=(Page) params.get("page");
				List<Map<String, Object>> listMap=new ArrayList<Map<String, Object>>();
				List<AftersaleApply> applyList=(List<AftersaleApply>) params.get("applyList");
				if(StringUtil.isNotNull(applyList)){
					for(AftersaleApply app:applyList){
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("orderId", app.getOrder().getId());
						map.put("orderNumber", app.getOrderNo());
						map.put("time", DateUtil.formatDateTime(app.getOrder().getCreateDate()));
						map.put("status", app.getOrder().getStatus());
						map.put("sendTime", DateUtil.formatDateTime(app.getOrder().getSendCommodityTime()));
						map.put("type", app.getOrder().getBuyType());
						double totalPrice=0.0;
						double sendCost=0.0;
						if(StringUtil.isNotNull(app.getOrder().getTotalPrice())){
							 totalPrice=app.getOrder().getActualPrice().doubleValue();//订单实际支付金额
						}	
						if(StringUtil.isNotNull(app.getOrder().getSendCost())){
							sendCost=app.getOrder().getSendCost().doubleValue();
						}
						map.put("totalPrice", totalPrice+sendCost);
						map.put("uid", app.getOrder().getMemberId());
						map.put("isInvoice", app.getOrder().getIsInvoice());
						map.put("afterSalesId", app.getId());
						map.put("afterSalesType", app.getApplyType());
						AftersaleApplyItem item=new AftersaleApplyItem();
						item.setApplyNo(app.getApplyNo());							
						result=this.aftersaleApplyItemApiService.findAftersaleApplyItemList(null, null, null, item);
						List<AftersaleApplyItem> aftersaleApplyItemList=(List<AftersaleApplyItem>)result.getData();
						if(StringUtil.isNotNull(aftersaleApplyItemList)){
							List<Map<String, Object>> goods=new ArrayList<Map<String, Object>>();
							for(AftersaleApplyItem detail:aftersaleApplyItemList){
								Map<String, Object> goodsMap=new HashMap<String, Object>();
								goodsMap.put("skuId", detail.getSkuId());
								goodsMap.put("imgPath", System.getProperty("image.web.server") +detail.getCommodityPic());
								goodsMap.put("title1", detail.getCommodityName());
								goodsMap.put("count", detail.getReturnNum());
								goodsMap.put("discountPrice", detail.getCommodityPrice());
								goodsMap.put("goodsStatus", detail.getAftersaleStatus());
								goodsMap.put("afterSalesId", app.getId());
								goodsMap.put("afterSalesType", app.getApplyType());
								goodsMap.put("goodsAttr", detail.getCommodityAttributeValues());
								
								goodsMap.put("giveType", detail.getBuyGifts());
								if("2".equals(detail.getBuyGifts())){
									goodsMap.put("mainCommodityId", detail.getMainCommodityId());
								}else{
									goodsMap.put("mainCommodityId", "");
								}
								goods.add(goodsMap);
							}
							map.put("goods", goods);
						}
						listMap.add(map);
					}
					params=new HashMap<String, Object>();
					params.put("items", listMap);
					params.put("page", page);
				}
			}
		} catch (Exception e) {
			logger.error("获取售后单列表 " , e);
			throw new CallInterfaceExceptionApi(ApiConstants.RESULT_TYPE_2,result.getMessage());
		}
		return params;
	}
	public Map<String, Object> findAftersaleApplyDetailNew(AftersaleApply apply)throws ServiceException{
		ResultDto result = null;
		//Map<String, Object> params=null;
		Map<String, Object> applyMap=new HashMap<String, Object>();
		try {
			//if(StringUtil.isNotNull(apply)){
			//	params=new HashMap<String, Object>();
			//	result=this.aftersaleApplyApiService.findAftersaleApplyDetail(apply);
			//	params=(Map<String, Object>) result.getData();					
			//}					
			if(StringUtil.isNotNull(apply)){
				String skuId=apply.getSkuId();
				if(StringUtil.isNotNull(apply.getId())){
					
					apply=this.aftersaleApplyApiService.findAftersaleApplyById(apply.getId());
				}
				double refundAmount=0.0;
				int count=0;					
				applyMap.put("serviceType", apply.getApplyType());
				applyMap.put("serviceTypeContent", getExamType(apply.getApplyType()));
				applyMap.put("orderAmount", apply.getOrder().getTotalPrice());
				applyMap.put("reason", apply.getReasonSelect());
				applyMap.put("reasonContent", getReasonName(apply.getApplyType(), apply.getReasonSelect()));
				applyMap.put("desc", apply.getReasonExplain());
				applyMap.put("orderNo", apply.getOrderNo());
				applyMap.put("applyDate", DateUtil.formatDateTime(apply.getCreateDate()));	
				// 根据IOS要求要加上orderid,雷---2016/08/05
				applyMap.put("newOrderId", apply.getOrderId());	
				applyMap.put("orderAmount","");//app开发说这个字段没有用到
				//换货 时
				if(apply.getApplyType().equals(AfterSaleStatusType.CHANGE_GOODS.getValue())){
					// apply.getOrderNo()查出的是原交易订单，查询原交易订单的支付金额,雷---2016/08/05
					//重置为换货的订单
					applyMap.put("newOrderNo", apply.getExchangeOrderNo());
				}else{
					applyMap.put("newOrderNo", "");
				}
				//仓库审核状态获取
				String pickuNo=apply.getPickupNo();
				if(StringUtil.isNotNull(pickuNo)){
					//AftersalePickup pickup=this.aftersalePickupService.getAftersalPickupByNo(pickuNo);
					
					List<AftersalePickup> pickups=this.aftersaleApplyApiService.getAftersalPickupByPickNo(pickuNo);
					AftersalePickup pickup=null;
					if(pickups!=null && pickups.size()>0)
						pickup=pickups.get(0);
					if(StringUtil.isNotNull(pickup)){
						String pickupStatus=pickup.getPickupStatus();//取货单状态
						if(pickupStatus.equals(ApiConstants.YES)){
							if(apply.getApplyType().equals("2")){
								applyMap.put("checkStatusTitle","总仓已经审核通过，我们将尽快为您发货，请耐心等候。");
							}else{
								applyMap.put("checkStatusTitle","总仓已经审核通过，我们将尽快为您退款，请耐心等候。");
							}
						}else{
							applyMap.put("checkStatusTitle","您的申请正在处理中，请耐心等候");
						}
					}
				}else{
					applyMap.put("checkStatusTitle","您的申请正在处理中，请耐心等候");
				}
				//退款时间获取
				String refundNo=apply.getRefundNo();
				if(StringUtil.isNotNull(refundNo)){
					
					AftersaleRefund refund=this.aftersaleApplyApiService.findRefundInfo(null, refundNo);
					if(StringUtil.isNotNull(refund)){
						String refundStatus=refund.getRefundStatus();//退款单状态
						if(refundStatus.equals(ApiConstants.REFUND_PAY)){
							applyMap.put("refundDate",DateUtil.formatDateTime(refund.getPayDate()));
						}
					}
				}else{
					applyMap.put("refundDate","");
				}
				AftersaleApplyItem item=new AftersaleApplyItem();
				//退款（没收到货）
				item.setApplyId(apply.getId());
				if(!apply.getApplyType().equals(ApiConstants.REFUND_TOTAL)){
					item.setSkuId(skuId);					
				}
				DecimalFormat df = new DecimalFormat("0.00");//格式化小数
				
				result = this.aftersaleApplyItemApiService.findAftersaleApplyItemList(null, null,null, item);
				List<AftersaleApplyItem> aftersaleApplyItemList=(List<AftersaleApplyItem>)result.getData();
				if(StringUtil.isNotNull(aftersaleApplyItemList)){									
					List<Map<String, Object>> goodslist=new ArrayList<Map<String, Object>>();
					for(AftersaleApplyItem ai:aftersaleApplyItemList){			
						Map<String, Object> goodsMap=new HashMap<String, Object>();
						//售后申请状态
						if(apply.getApplyType().equals("2")&&ai.getAftersaleStatus().equals(ApiConstants.STATUS_CHANGESUC)){
							applyMap.put("checkStatusTitle","感谢您在大麦场购物，欢迎您再次光临！");
						}
						applyMap.put("checkStatus", ai.getAftersaleStatus());	
						int buyNum=ai.getCommodityBuyNum();//购买数量
						int retNum=ai.getReturnNum();//售后数量
						//如果购买数量和售后数量相同说明是待收货按订单退
						if(buyNum==retNum){
							refundAmount+=ai.getActPayAmount().doubleValue();
							count+=ai.getCommodityBuyNum();
						}else{
							//否则是售后数量乘以单个商品的实际支付金额							
							refundAmount+= (ai.getActPayAmount().doubleValue()/buyNum)*retNum;	
							count+=ai.getReturnNum();
						}
						goodsMap.put("title1", ai.getCommodityName());
						goodsMap.put("goodsAttr", ai.getCommodityAttributeValues());
						goodsMap.put("imgPath", ai.getCommodityPic()==null?"":System.getProperty("image.web.server") +ai.getCommodityPic());
						goodsMap.put("count", ai.getReturnNum());
						goodsMap.put("price", ai.getCommodityPrice());
						goodsMap.put("giveType", ai.getBuyGifts());
						if("2".equals(ai.getBuyGifts())){
							goodsMap.put("mainCommodityId", ai.getMainCommodityId());
						}else{
							goodsMap.put("mainCommodityId", "");
						}
						goodslist.add(goodsMap);
					}
					applyMap.put("refundAmount",df.format(refundAmount) );
					applyMap.put("goodsCount", count);
					applyMap.put("goodslist", goodslist);
				}
			}
			
		} catch (Exception e) {
			logger.error("获取售后详情" , e);
			throw new CallInterfaceExceptionApi(ApiConstants.RESULT_TYPE_2,result.getMessage());
		}
		return applyMap;
	}
	public String getExamType(String value) {
		for (AfterSaleStatusType examType : AfterSaleStatusType.values()) {
			if (value.equals(examType.getValue())) {
				return examType.getName();
			}
		}
		return null;
	}
	public String getReasonName(String type,String value){
		 String reasonName="商品质量问题";		 
		 if(StringUtils.isNotEmpty(type)&&StringUtils.isNotEmpty(value)){
			 switch (type) {
				case "0":		
					 for(AfterSaleRefundReasons rea:AfterSaleRefundReasons.values()){
						 if(value.equals(rea.getValue())){
							 reasonName= rea.getName();
						 }
					 }
					break;
				case "1":		
					 for(AfterSaleReMoneyReasons rea:AfterSaleReMoneyReasons.values()){
						 if(value.equals(rea.getValue())){
							 reasonName= rea.getName();
						 }
					 }
					break;
				case "2":		
					 for(AfterSaleChangeReasons rea:AfterSaleChangeReasons.values()){
						 if(value.equals(rea.getValue())){
							 reasonName= rea.getName();
						 }
					 }
					break;

				default:
					break;
				}		
		 }		
			return reasonName;
	 }

}

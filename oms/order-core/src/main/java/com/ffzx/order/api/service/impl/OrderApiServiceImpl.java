package com.ffzx.order.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.member.api.dto.Member;
import com.ffzx.member.api.service.MemberApiService;
import com.ffzx.order.api.dto.Commodity;
import com.ffzx.order.api.dto.CommoditySku;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderRecord;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.enums.BuyTypeEnum;
import com.ffzx.order.api.enums.OrderOperationRecordEnum;
import com.ffzx.order.api.enums.OrderTypeEnum;
import com.ffzx.order.api.enums.PayTypeEnum;
import com.ffzx.order.api.service.OrderApiService;
import com.ffzx.order.api.vo.OmsOrderParamVo;
import com.ffzx.order.api.vo.OmsOrderVo;
import com.ffzx.order.api.vo.OrderBiVo;
import com.ffzx.order.constant.OrderStatusConstant;
import com.ffzx.order.service.CommodityService;
import com.ffzx.order.service.CommoditySkuService;
import com.ffzx.order.service.OmsOrderFormService;
import com.ffzx.order.service.OmsOrderRecordService;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.order.service.OmsOrderdetailService;
import com.ffzx.order.utils.pingxx.Pay;
import com.ffzx.promotion.api.service.CouponReceiveApiService;
import com.pingplusplus.model.Charge;

/**
 * 订单dubbo实现
 * 
 * @className OrderApiServiceImpl.java
 * @author shifeng.tang
 * @date 2016年3月28日 下午5:58:02
 * @version 1.0
 */
@Service("orderApiService")
public class OrderApiServiceImpl implements OrderApiService {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(OrderApiServiceImpl.class);
	@Resource
	private RedisUtil redisUtil;
	@Autowired
	private OmsOrderService omsOrderService;// 订单接口服务
	
	@Autowired
	private OmsOrderdetailService omsOrderdetailService;// 订单明细接口服务
	@Autowired
	private OmsOrderRecordService omsOrderRecordService; // 获取订单操作记录接口
	@Autowired
	private MemberApiService memberApiService;// 会员地址接口调用
	@Autowired
	private CommodityService commodityService;// 商品
	@Autowired
	private CommoditySkuService commoditySkuService;// 商品sku
	
	@Autowired
	private CouponReceiveApiService couponReceiveApiService;// 优惠券接口

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private OmsOrderFormService omsOrderFormService;
	private void writeOpt(OmsOrder order) throws Exception {
		if (StringUtil.isNotNull(order.getCouponId())) {
			ResultDto couponReceiveResultDtoData = couponReceiveApiService.updateReceiveState(order.getCouponId(), "1",order.getMemberId());// 将该优惠券设置为已使用
			if (!couponReceiveResultDtoData.getCode().equals(ResultDto.OK_CODE)) {
				throw new ServiceException(couponReceiveResultDtoData.getMessage());
			}

		}
		/**
		 * 优惠券领取更新状态
		 * 
		 * @param id
		 * @param state
		 *            使用状态(0未使用，1使用).
		 * @return
		 */

	}

	private CommoditySku getCommoditySkuById(String skuId) {
		return commoditySkuService.getCommoditySkuByIdFromRedis(skuId);
	}

	private Commodity getCommodityByCode(String code) {
		return commodityService.getCommodityByCodeFromRedis(code);
	}

	/***
	 * 构建订单明细实体
	 * 
	 * @param orderNo
	 *            关联订单编号
	 * @param commodityNo
	 *            关联商品编号
	 * @param commodityTitle
	 *            商品标题
	 * @param CommodityImage
	 *            商品图片
	 * @param barcode
	 *            商品条形码
	 * @param specifications
	 *            商品规格
	 * @param unit
	 *            商品单位
	 * @param CommodityPrice
	 *            单价 (普通商品取优惠价、活动商品取活动商品的优惠价)
	 * @param actPayAmount
	 *            实际支付金额:单价*数量-按比例使用的优惠券金额
	 * @param buyNum
	 *            购买数量
	 * @param readySendTime
	 *            发货日期(如果是预售购买,则有默认的预计发货日期)
	 * @param activityCommodityItemId
	 *            关联活动详情ID
	 * @param memberId
	 *            下单会员ID
	 * @return
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-9 下午03:55:50
	 * @version V1.0
	 */
	@SuppressWarnings("unused")
	private OmsOrderdetail buildOrderDetail(String orderNo, String commodityNo, String commodityTitle, String CommodityImage, String barcode, String specifications, String unit, BigDecimal CommodityPrice, BigDecimal actPayAmount, int buyNum, Date readySendTime, String activityCommodityItemId, String memberId, Date createDate) {
		OmsOrderdetail omsOrderdetail = new OmsOrderdetail();
		omsOrderdetail.setId(UUIDGenerator.getUUID());
		omsOrderdetail.setOrderNo(orderNo);
		omsOrderdetail.setCommodityNo(commodityNo);
		omsOrderdetail.setCommodityTitle(commodityTitle);
		omsOrderdetail.setCommodityImage(CommodityImage);
		omsOrderdetail.setCommodityBarcode(barcode);
		omsOrderdetail.setCommoditySpecifications(specifications);
		omsOrderdetail.setCommodityUnit(unit);
		omsOrderdetail.setCommodityPrice(CommodityPrice);
		omsOrderdetail.setActPayAmount(actPayAmount);
		omsOrderdetail.setBuyNum(buyNum);
		omsOrderdetail.setReadySendTime(readySendTime);
		omsOrderdetail.setActivityCommodityItemId(activityCommodityItemId);
		omsOrderdetail.setMemberId(memberId);
		omsOrderdetail.setCreateDate(createDate);
		return omsOrderdetail;
	}

	@Override
	public ResultDto findOrderListByMember(String memberId, String status, int page, int pageSize, int waterline) throws ServiceException {
		StringBuilder logBuilder = new StringBuilder("invoke orderApiServiceImpl.findOrderListByMember  == >>>\r\n");
		logBuilder.append("params{memberId:").append(memberId).append(",status:").append(status).append(",page:").append(page).append(",pageSize:").append(pageSize).append("}");
		logger.info(logBuilder.toString());
		ResultDto rsDto = null;
		Page pageObj = new Page(page, pageSize, waterline);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("unDeletedByUser", "0");
		if (StringUtil.isNotNull(status)) {
			if(status.indexOf(",")>0){
				params.put("statusStr", status);
			}else{
				params.put("status", status);
			}
		}
		params.put("delFlag", Constant.DELTE_FLAG_NO);
		params.put("orderBy", "t.create_date desc");
		List<OmsOrder> orderList = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			orderList = omsOrderService.queryByPage(pageObj, params);
			pageObj.setRecords(orderList);
			rsDto.setData(pageObj);
		} catch (ServiceException e) {
			logger.error("ServiceException >>> OrderApiServiceImpl findOrderListByMember=》订单-列表", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:服务器忙");
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Exception >>> OrderApiServiceImpl  findOrderListByMember=》订单-列表", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:服务器忙");
			throw new ServiceException(e);
		}
		return rsDto;
	}


	/***
	 * 
	 * @param params
	 * @param page
	 * @param pageSize
	 * @param waterline
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年1月5日 上午9:52:19
	 * @version V1.0
	 * @return 
	 */
	@Override
	public ResultDto getOrderByPage(Map<String, Object> params, int page, int pageSize, int waterline)
			throws ServiceException {
		ResultDto rsDto = null;
		Page pageObj = new Page(page,pageSize,waterline);
		if(null==params){
			params = new HashMap<>();	
		}
		//if(params.containsKey("orderBy")){
			params.put("orderBy", "t.create_date desc");
		//}
			//只能是普通订单 排除换货重发的订单
		params.put("orderType", OrderTypeEnum.COMMON_ORDER.getValue());
		//排除掉超过交易完成超过15天的订单
		params.put("endDateCode", Constant.NO);
		params.put("commodityTypeCode", Constant.NO);
		params.put("delFlag", Constant.DELTE_FLAG_NO);
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			List<OmsOrder> orderList = omsOrderService.queryByPage(pageObj, params);
			
			pageObj.setRecords(orderList);
			rsDto.setData(pageObj);
		} catch (Exception e) {
			logger.error("getOrderByPage订单查询异常",e);
		}
		return rsDto;
	}
	
	@Override
	public ResultDto signOrder(String orderNo) throws ServiceException {
		StringBuilder logBuilder = new StringBuilder("invoke orderApiServiceImpl.signOrder == >>>\r\n");
		logBuilder.append("params{orderNo:").append(orderNo).append("}");
		logger.info(logBuilder.toString());
		// TODO 额外的接口调用控制逻辑保留
		return operateOrder(orderNo, OmsOrder.SIGN);
	}

	@Override
	public ResultDto backOrder(String orderNo) throws ServiceException {
		StringBuilder logBuilder = new StringBuilder("invoke orderApiServiceImpl.backOrder  == >>>\r\n");
		logBuilder.append("params{orderNo:").append(orderNo).append("}");
		logger.info(logBuilder.toString());
		//
		return operateOrder(orderNo, OmsOrder.BACK);
	}

	@Override
	public ResultDto returnBackOrder(String orderNo) throws ServiceException {
		StringBuilder logBuilder = new StringBuilder("invoke orderApiServiceImpl.returnBackOrder  == >>>\r\n");
		logBuilder.append("params{orderNo:").append(orderNo).append("}");
		logger.info(logBuilder.toString());
		// TODO 额外的接口调用控制逻辑保留
		return operateOrder(orderNo, OmsOrder.RETURN_BACK);
	}

	@Override
	public ResultDto deleteOrder(String orderNo) throws ServiceException {
		StringBuilder logBuilder = new StringBuilder("invoke orderApiServiceImpl.deleteOrder  == >>>\r\n");
		logBuilder.append("params{orderNo:").append(orderNo).append("}");
		logger.info(logBuilder.toString());
		// TODO 额外的接口调用控制逻辑保留
		return operateOrder(orderNo, OmsOrder.DELETE);
	}

	@Override
	public ResultDto cancelOrder(String orderNo) throws ServiceException {
		StringBuilder logBuilder = new StringBuilder("invoke orderApiServiceImpl.cancelOrder  == >>>\r\n");
		logBuilder.append("params{orderNo:").append(orderNo).append("}");
		logger.info(logBuilder.toString());
		// TODO 额外的接口调用控制逻辑保留
		return operateOrder(orderNo, OmsOrder.CANCEL);
	}



	private ResultDto operateOrder(String orderNo, String operateType) {
		try {
			if(operateType.equals("DELETE")){
				omsOrderService.operateOrderByType(orderNo, operateType);
			}else{
				omsOrderService.operateOrderByType(orderNo, OrderOperationRecordEnum.MANUAL_CANCEL.getValue());
			}
		} catch (Exception e) {
			logger.error("invoke orderApiServiceImpl.operateOrder error === >>> orderNo:" + orderNo + ",operateType:" + operateType, e);
			//return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, e.getMessage());
			throw new ServiceException(e);
		}
		return new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
	}

	@Override
	public ResultDto pingxxPay(String orderNo, String payType, String value) throws ServiceException {
		// TODO Auto-generated method stub
		Map<String,Object> _params =  new HashMap<String,Object>();
    	_params.put("orderNo",orderNo);
		OmsOrder order = null;
		try {
			order = omsOrderService.getOrderByWebhooks(_params);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error("invoke orderApiServiceImpl.pingxxPay error === omsOrderService.getOrderByWebhooks  >>> orderNo:" + orderNo , e1);
			return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "网络超时");
		}
		if (order == null) {
			return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "订单不存在");
		}
		if (order.getPayTime() != null) {
			return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "该订单已完成支付，请勿重新支付");
		}
		if (OrderStatusConstant.ORDER_CANCELED.equals(order.getStatus())) {
			return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "该订单已取消，不能支付");
		}
		if (!"0".equals(order.getStatus())) {
			return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "该订单已完成支付，请勿重新支付");
		}
		// 根据订单id 查询订单明细
		Map<String, Object> odParams = new HashMap<String, Object>();
		odParams.put("orderNo", order.getOrderNo());
		List<OmsOrderdetail> odList;
		try {
			odList = this.omsOrderdetailService.selectJoinSku(odParams);
		} catch (Exception e) {
			//return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "该订单已完成支付，请勿重新支付");
			throw new ServiceException(e);
		}
		if (odList == null || odList.size() == 0) {
			return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "该订单无明细数据异常， 无法完成支付");
		}
		try {
		ResultDto resultDto  = memberApiService.getByIdMember(order.getMemberId());
		Member member  =  (Member) resultDto.getData();
		for (OmsOrderdetail omsOrderdetail : odList){
			if(omsOrderdetail.getBuyType().equals(BuyTypeEnum.NEWUSER_VIP)&&member.getIsNew().equals(Constant.NO)){
				return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "该订单存在新用户指定商品，您不是新用户，无法完成支付");
			}
		}
		} catch (Exception e) {
			logger.info("==========支付接口 查询会员信息失败============",e);
		}
		/*
		 * for (OmsOrderdetail omsOrderdetail : odList) { if(omsOrderdetail == null ||
		 * omsOrderdetail.getCommoditySku()==null||omsOrderdetail.getCommoditySku().getActFlag().equals(Constant.ACT_FLAG_YES)||omsOrderdetail.getCommoditySku().getDelFlag().equals(Constant.DELTE_FLAG_YES)){ return new ResultDto(ResultDto.BUSINESS_ERROR_CODE,"抱歉，商品【" +
		 * omsOrderdetail.getCommodityTitle() +"】已下架，该订单无法完成支付"); } }
		 */
		//如果当前日期在支付过期日期之后
		if(new Date().compareTo(order.getOverTime())>0){
			logger.info("==========【"+order.getOrderNo()+"】过期支付============");
			return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "该订单支付过期， 无法完成支付");
		}
		
		// 获取支付凭证
		// 计算超时时间。。。当前时间+（系统删除订单时间）
		String time_expire;// 超时时间...
		time_expire = DateUtil.date2UnixTimeStamp(order.getOverTime());
		String oldChargeId = StringUtil.isNotNull(order.getChargeId()) ? order.getChargeId() : "";
		String oldPayType =  StringUtil.isNotNull(order.getPayType()) ? order.getPayType().getValue():"";
		Map<String, Object> resultMap = Pay.createCharge(order, payType, time_expire,value);
		ResultDto result = new ResultDto(ResultDto.OK_CODE, "success");
		Charge charge = (Charge) resultMap.get("charge");
		JSONObject jsonObj = new JSONObject();
		JSONObject chargeObj = (JSONObject) JSONObject.toJSON(charge);
		jsonObj.put("charge", chargeObj);
		result.setData(jsonObj);
		order =  (OmsOrder)resultMap.get("order");
		if (!oldChargeId.equals(order.getChargeId())||!oldPayType.equals(order.getPayType().getValue())) {
			updateOrderChargeId(order);
		}
		return result;
	}

	// 更新订单chargeId
	@Transactional(rollbackFor=Exception.class)
	private void updateOrderChargeId(OmsOrder order) {
		if (order != null) {
			OmsOrder data = new OmsOrder();
			data.setId(order.getId());
			data.setChargeId(order.getChargeId());
			data.setPayType(order.getPayType());
			this.omsOrderService.modifyById(data);
		}
	}

	@Override
	public ResultDto selectBuyCount(Map<String, Object> params) throws ServiceException {
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			Integer i = omsOrderdetailService.selectBuyCount(params);
			rsDto.setData(i);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-selectBuyCount-Exception=》机构dubbo调用-selectBuyCount", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto getOrderRecord(String orderId, String recordType) throws ServiceException {
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderId", orderId);
			params.put("recordType", recordType);
			List<OmsOrderRecord> list = omsOrderRecordService.findByPage(null, Constant.ORDER_BY_FIELD_CREATE+" desc"+",outbound_status desc",null, params);
			rsDto.setData(list);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-getOrderRecord-Exception=》机构dubbo调用-getOrderRecord", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto findOrderById(String id) throws ServiceException {
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			OmsOrder order = this.omsOrderService.findById(id);
			rsDto.setData(order);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-findOrderById-Exception=》机构dubbo调用-findOrderById", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto indentSearchOne(String orderNumber) throws ServiceException {
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderNo", orderNumber);
			OmsOrder data = omsOrderService.getOrderByKey(params);
			if (data != null) {
				// 获取订单明细
				Map<String, Object> odParams = new HashMap<String, Object>();
				odParams.put("orderId", data.getId());
				List<OmsOrderdetail> odList = omsOrderdetailService.findByBiz(odParams);
				params.put("recordType", "1");
				List<OmsOrderRecord> orderRecordList = omsOrderRecordService.findByPage(null,Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY, params);
				params.clear();
				params.put("orderNo", orderNumber);
				if (odList != null) {
					data.setDetailList(odList);

				}
				if (orderRecordList != null) {
					data.setOrderRecordList(orderRecordList);
				}
			}
			rsDto.setData(data);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-indentSearchOne-Exception=》机构dubbo调用-indentSearchOne", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto upDateStatus(String orderId, String status) throws ServiceException {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			OmsOrder order = new OmsOrder();
			order.setId(orderId);
			order.setStatus(status);
			this.omsOrderService.modifyById(order);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-upDateStatus-Exception=》", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto findOmsOrderDetail(String orderNo, String skuId) throws ServiceException {
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			// 获取订单明细
			Map<String, Object> odParams = new HashMap<String, Object>();
			odParams.put("orderNo", orderNo);
			odParams.put("skuId", skuId);
			OmsOrderdetail detail = this.omsOrderdetailService.getOrderdetailByCode(odParams);
			if (StringUtil.isNotNull(detail)) {
				rsDto.setData(detail);
			}
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-findOmsOrderDetail-Exception=》机构dubbo调用-findOmsOrderDetail", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;

	}

	@Override
	public ResultDto getUserOrderData(String uid, String month) throws ServiceException {
		ResultDto rsDto = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", uid);
			params.put("statusStr", "('1','4')"); 
			params.put("delFlag", "0");
			Double historyMoney = omsOrderService.getTotalMoney(params);
			params.put("createDateStart", month + "-01 00:00:00");
			params.put("createDateEnd", month + "-31 23:59:59");
			Double monthMoney = omsOrderService.getTotalMoney(params);
			List<OmsOrder> orderList = omsOrderService.findByPage(null, null, null, params);
			resultMap.put("totalMonth", monthMoney);
			resultMap.put("totalSum", historyMoney);
			resultMap.put("orderList", orderList);
			rsDto.setData(resultMap);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-getUserOrderData-Exception=》", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto getOrderStatusCountByUser(String memberId) throws ServiceException {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", memberId);
			params.put("delFlag", "0");
			Map<String, Object> resultMap = this.omsOrderService.getStatusCount(params);
			Map<String, Object> afterSaleResultMap = this.omsOrderService.getAfterSaleCount(params);
			resultMap.put("refundCount", afterSaleResultMap.containsKey("refundCount")?afterSaleResultMap.get("refundCount"):0);
			rsDto.setData(resultMap);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-getOrderStatusCountByUser-Exception=》", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto upDateomsOrderdetaiStatus(List<String> omsOrderdetailId, String status) throws ServiceException {
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			for (String detailId : omsOrderdetailId) {
				OmsOrderdetail orderdetail = new OmsOrderdetail();
				orderdetail.setId(detailId);
				orderdetail.setOrderDetailStatus(status);
				this.omsOrderdetailService.modifyById(orderdetail);
			}
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-upDateomsOrderdetaiStatus-Exception=》", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto buildOrder(OmsOrder omsOrder) throws ServiceException {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
		try {
			logger.info("==========>>>进入orderApiService持久化订单中心<<<========");
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			this.omsOrderService.buildOrder(omsOrder);
			rsDto.setData(omsOrder);
			String msg = "下单成功";
			logger.info("==========>>>开始生成订单操作记录<<<========");
			this.saveOmsOrderRecord(omsOrder, msg, "0");
			logger.info("==========>>>订单操作记录生成完毕<<<========");
			rsDto.setMessage(msg);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-buildOrder-Exception=》", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, e.getMessage());
			throw new ServiceException(e);
		}
		// 保存订单数据
		return rsDto;
	}

	 /*
     * 订单操作类型 0:订单操作,1：物流状态,2:订单分配错误异常
     */
	private void saveOmsOrderRecord(OmsOrder omsOrder,String msg,String type)throws ServiceException{
		OmsOrderRecord omsOrderRecord = new OmsOrderRecord();
		Date now = omsOrder.getCreateDate();
		omsOrderRecord.setId(UUIDGenerator.getUUID());
		omsOrderRecord.setOrderId(omsOrder.getId());
		omsOrderRecord.setOrderNo(omsOrder.getOrderNo());
		omsOrderRecord.setOprId(omsOrder.getMemberId());
		omsOrderRecord.setOprName(omsOrder.getMemberName() == null ? null : omsOrder.getMemberPhone());
		omsOrderRecord.setCreateDate(now);
		omsOrderRecord.setLastUpdateDate(now);
		omsOrderRecord.setDescription(msg);
		omsOrderRecord.setRecordType(type);
		omsOrderRecordService.add(omsOrderRecord);
	}

	@Override
	public ResultDto selectOrderDetailCount(Map<String, Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			Integer buyCount = omsOrderdetailService.selectBuyCount(params);
			rsDto.setData(buyCount);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-selectOrderDetailCount-Exception=》", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto getOrderCommoditySkuById(String skuId) throws ServiceException {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			CommoditySku commoditySku = commoditySkuService.getCommoditySkuByIdFromRedis(skuId);
			String commodityCode = commoditySku.getCommodityCode();
			Commodity commodity = commodityService.getCommodityByCodeFromRedis(commodityCode);
			commoditySku.setCommodity(commodity);
			rsDto.setData(commoditySku);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-getOrderCommoditySkuById-Exception=》", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto getLatestOrderuser(String commodityId,String activityStartTime,int page, int pageSize) throws ServiceException {
		ResultDto rsDto = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Page pageObj = new Page(page, pageSize);
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			List<OmsOrder> orderUserList = omsOrderService.findLatestOrderuser(pageObj, commodityId,activityStartTime);
			// if(orderUserList !=null && orderUserList.size()>=1){
			// }
			resultMap.put("items", orderUserList);
			resultMap.put("page", pageObj.getPageCount());
			resultMap.put("pageSize", pageObj.getPageSize());
			resultMap.put("recordCount", pageObj.getTotalCount());

			rsDto.setData(resultMap);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-getLatestOrderuser-Exception=》", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}

		return rsDto;
	}

	@Override
	public ResultDto getLatestOrderByACId(String commodityId,String acitivityCommodityId,int page, int pageSize) throws ServiceException {
		ResultDto rsDto = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Page pageObj = new Page(page, pageSize);
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			List<OmsOrder> orderUserList = omsOrderService.findLatestOrderByACId(pageObj, commodityId,acitivityCommodityId);
			// if(orderUserList !=null && orderUserList.size()>=1){
			// }
			resultMap.put("items", orderUserList);
			resultMap.put("page", pageObj.getPageCount());
			resultMap.put("pageSize", pageObj.getPageSize());
			resultMap.put("recordCount", pageObj.getTotalCount());

			rsDto.setData(resultMap);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-getLatestOrderuser-Exception=》", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}

		return rsDto;
	}
	
	@Override
	public ResultDto upDateStatusByType(String orderId, String type) throws ServiceException {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");

			OmsOrder order = this.omsOrderService.findById(orderId);
			if (type.equals("DELETE")) {// 删除操作
				this.omsOrderService.operateOrderByType(order.getOrderNo(), OmsOrder.DELETE);
			} else {// 取消订单 业务逻辑赞不知道怎么实现
				this.omsOrderService.operateOrderByType(order.getOrderNo(), OrderOperationRecordEnum.MANUAL_CANCEL.getValue());
			}
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-upDateStatusByType-Exception=》", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto confirmOrder(String uid, String oid) {
		try {
			omsOrderService.confirmOrder(uid, oid);
			sendOrder24Radio(oid);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl.confirmOrder error reason by=》 ", e);
			//return new ResultDto(ResultDto.ERROR_CODE, e.getMessage());
			throw new ServiceException(e);
		}
		return new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
	}
	//交易完成的订单 发送广播
		private void sendOrder24Radio(String orderId){
			OmsOrder omsOrder = null;
			try{
				omsOrder = this.omsOrderService.findOrderInfoById(orderId);
				rabbitTemplate.convertAndSend("OMS_ORDER24_CHANGE_EXCHANGE", "", omsOrder);
				}catch (Exception e) {
					logger.error("订单交易完成发送广播失败",e);
					logger.error("【"+(omsOrder!=null?omsOrder.getOrderNo():orderId)+"】订单交易完成发送广播失败",e);
				}
		}
	@Override
	public ResultDto findOrderInfoToSendApp(String id) {
		try {
			OmsOrder order = omsOrderService.findOrderInfoToSendApp(id);
			if (null == order) {
				return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "未找到该数据对应订单详细信息！");
			}
			ResultDto finalResult = new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
			finalResult.setData(order);
			return finalResult;
		} catch (Exception e) {
			logger.error("omsOrderService.findOrderInfoToSendApp error reason by=》 ", e);
			//return new ResultDto(ResultDto.ERROR_CODE, e.getMessage());
			throw new ServiceException(e);
		}
	}
	@Override
	public ResultDto findOrderInfoByNo(String orderNo) {
		try {
			OmsOrder order = omsOrderService.findOrderInfo(orderNo);
			if (null == order) {
				return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "未找到该数据对应订单详细信息！");
			}
			ResultDto finalResult = new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
			finalResult.setData(order);
			return finalResult;
		} catch (Exception e) {
			logger.error("omsOrderService.findOrderInfoToSendApp error reason by=》 ", e);
			//return new ResultDto(ResultDto.ERROR_CODE, e.getMessage());
			throw new ServiceException(e);
		}
	}
	
	@Override
	public ResultDto allocationError(String orderId) {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			OmsOrder order = new OmsOrder();
			order.setId(orderId);
			order.setAllocationError(Constant.YES);// 订单分配错误标示
			this.omsOrderService.modifyById(order);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-allocationError-Exception=》", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto confirmReceipt(String billId, String oid, String personId, String keyValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page queryDistributionPage(OmsOrderVo vo, Page page) throws Exception {
			Map<String, Object> params = new HashMap<String, Object>();
			if (vo != null) {
				
				//确认送达开始时间
				if (!StringUtil.isEmpty(vo.getDeliveryStartDate())) {
					params.put("deliveryStartDate", vo.getDeliveryStartDate());
				}
				//确认送达结束时间
				if (!StringUtil.isEmpty(vo.getDeliveryEndDate())) {
					params.put("deliveryEndDate", vo.getDeliveryEndDate());
				}
				
				if (null != vo.getOrderNoList() && vo.getOrderNoList().size() > 0) {
					params.put("orderNoList", vo.getOrderNoList());// 根据订单编号list查询
				}
				if (!StringUtil.isEmpty(vo.getKeyWords())) {
					logger.info(" get search key:"+vo.getKeyWords());
					params.put("keyWords", vo.getKeyWords());
				}
				if (!StringUtil.isEmpty(vo.getPartnerId())) {
					params.put("partnerId", vo.getPartnerId());
				}
				if (null != vo.getTodayUnDist()) {
					params.put("todayUnDist", vo.getTodayUnDist());
				}
				if (null != vo.getMissingDist()) {
					params.put("missingDist", vo.getMissingDist());
				}
				if(null != vo.getStatusList() && vo.getStatusList().size()>0){
					params.put("statusList", vo.getStatusList());
				}
				if(StringUtils.isNotEmpty(vo.getMemberID()))
					params.put("memberID", vo.getMemberID());
				if(StringUtils.isNotEmpty(vo.getOrderState()))
					params.put("orderState", vo.getOrderState());
				params.put("outOrderStatus", vo.getOutOrderStatus());
			}
			List<OmsOrderVo> rlist = this.omsOrderService.queryDistributionPage(page, params);
			if(rlist != null && rlist.size() > 0){
				//获取对应的最新物流状态
				List<String>  orderNoList = getOrderNoList(rlist);
				List<OmsOrderVo> dList = this.omsOrderRecordService.findLogisticsStatusByOrderNos(orderNoList);
				for (OmsOrderVo r : rlist) {
					for (OmsOrderVo d : dList) {
						if(r!=null && r.getOrderNo()!=null && d!=null && d.getOrderNo()!=null && r.getOrderNo().equals(d.getOrderNo())){
							r.setLogisticsStatus(d.getLogisticsStatus());
						}
					}
				}
			}			
			page.setRecords(rlist);
		
		return page;
	}
	

	/**
	 * @author 雷
	 * @date 2016年6月15日
	 * 统计会员各订单状态的支付总金额
	 * (non-Javadoc)
	 * @see com.ffzx.order.api.service.OrderApiService#getSumMemberPay(com.ffzx.order.api.vo.OmsOrderVo)
	 */
	@Override
	public OmsOrderVo getSumMemberPay(OmsOrderVo vo) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (vo != null) {
			if(StringUtils.isNotEmpty(vo.getMemberID()))
				params.put("memberID", vo.getMemberID());
			if(StringUtils.isNotEmpty(vo.getOrderState()))
				params.put("orderState", vo.getOrderState());
		}
		return this.omsOrderService.getSumMemberPay(params);
	}	
	@Override
	public ResultDto updateDistributionDate(String orderNos) {
		try {
			omsOrderService.updateDistributionDate(orderNos);
			return new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl.getSumMemberPay -Exception=》", e);
			//return new ResultDto(ResultDto.ERROR_CODE, e.getMessage());
			throw new ServiceException(e);
		}
	}
	@Override
	public OmsOrderVo queryEachDistCount(OmsOrderVo vo) throws ServiceException {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("partnerId", vo.getPartnerId());//合伙人ID
		params.put("todayUnDist", vo.getTodayUnDist());
		params.put("missingDist", vo.getMissingDist());
        OmsOrderVo res=null;
		try {
			res = this.omsOrderService.queryEachDistCount(params);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-queryEachDistCount-Exception=》", e);
			throw new ServiceException(e);
		}
		return res;
	}

	@Override
	public ResultDto findSaleOrderBi(String partnerId) {
		try {
			List<OrderBiVo> biList = omsOrderService.findSaleOrderBi(partnerId);
			ResultDto result = new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
			result.setData(biList);
			return result;
		} catch (Exception e) {
			logger.error("omsOrderService.findSaleOrderBi",e);
			//return new ResultDto(ResultDto.ERROR_CODE, e.getMessage());
			throw new ServiceException(e);
		}
	}

	/***
	 * 根据换货订单编号和skuId修改老订单商品状态
	 */
	@Override
	public ResultDto updateOrderDetailStatus(String exChangeorderNo, String skuId) {
		
		try {
			Map<String,Object> res = omsOrderService.updateOrderDetailStatus(exChangeorderNo, skuId);
			ResultDto result = new ResultDto(ResultDto.OK_CODE, res.get("code").toString());
			result.setData(res.get("date"));
			result.setMessage(res.get("message").toString());
			return result;
		} catch (Exception e) {
			logger.error("根据换货订单编号和skuId修改老订单商品状态",e);
			//return new ResultDto(ResultDto.ERROR_CODE, e.getMessage());
			throw new ServiceException(e);
		}
	}

	@Override
	public ResultDto buyGoods(int sysType, String memberId, String couponId, String addressId, int isInvoice,
			String desc, String cityId, String goodsList) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}







	/* (non-Javadoc)
	 * @see com.ffzx.order.api.service.OrderApiService#findLogisticsStatusByOrderNos(java.util.List)
	 */
	@Override
	public ResultDto findLogisticsStatusByOrderNos(List<String> orderNoList) throws ServiceException {
		try {
			List<OmsOrderVo> omsOrderVoList = omsOrderRecordService.findLogisticsStatusByOrderNos(orderNoList);
			ResultDto result = new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
			result.setData(omsOrderVoList);
			return result;
		} catch (Exception e) {
			logger.error("获取订单最新物流状态：",e);
			throw new ServiceException(e);
		}
	}
	
	private List<String> getOrderNoList(List<OmsOrderVo> omsOrderList){
		List<String>  orderNoList = new ArrayList<String>();
		for (OmsOrderVo omsOrderVo : omsOrderList) {
			if(omsOrderVo != null){
				orderNoList.add(omsOrderVo.getOrderNo());
			}			
		}
		return orderNoList;
	}

	/**
	 * 雷-----2016年9月19日
	 * (非 Javadoc)
	 * <p>Title: findGifts</p>
	 * <p>Description: 根据主商品的订单明细ID查赠品明细</p>
	 * @param mainCommodityId
	 * @return
	 * @throws ServiceException
	 * @see com.ffzx.order.api.service.OrderApiService#findGifts(java.lang.String)
	 */
	@Override
	public ResultDto findGifts(String mainCommodityId) throws ServiceException {
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			List<OmsOrderdetail> orderdetails = omsOrderdetailService.getdetailByIDList(mainCommodityId);
			rsDto.setData(orderdetails);
		} catch (Exception e) {
			logger.error("OrderApiServiceImpl-findGifts-Exception=》机构dubbo调用-findGifts", e);
			throw new ServiceException(e);
		}
		return rsDto;
		
	}

	@Override
	public ResultDto findOrderBuyGive(Map<String, Object> params) {
		ResultDto resDto=null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			List<OmsOrder> list=this.omsOrderService.findByBiz(params);
			resDto.setData(list);
		} catch (Exception e) {
			logger.error("", e);
			throw new ServiceException(e);
		}
		//add by ying.cai  加个return。。。
		return resDto;
	}

	/***
	 * 
	 * @param orderNo
	 * @param charge_id
	 * @param paidTime
	 * @param payType
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月18日 下午3:50:38
	 * @version V1.0
	 * @return 
	 */
	@Override
	public ResultDto paySuccess(String orderNo, String charge_id, Date paidTime, PayTypeEnum payType)
			throws ServiceException {
		StringBuilder paramBuilder = new StringBuilder("OrderApiServiceImpl.paySuccess(\"");
		paramBuilder.append(orderNo).append(",").append(charge_id).append(",").append(paidTime).append(",").append(payType.getValue()).append("\")");
		logger.info("【"+orderNo+"】"+paramBuilder.toString());
		try {
			this.omsOrderService.orderPaySuccess(orderNo, charge_id,null, paidTime, payType);
		} catch (Exception e) {
			logger.error("【"+orderNo+"】支付异常，调用dubbo接口 OrderApiServiceImpl.paySuccess()", e);
			throw new ServiceException(e);
		}
		logger.info("【"+orderNo+"】paySuccess调用成功！");
		return new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
	}

	/***
	 * 
	 * @param orderNo
	 * @param time_succeed
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月18日 下午4:12:40
	 * @version V1.0
	 * @return 
	 */
	@Override
	public ResultDto refundSuccess(String orderNo, Date time_succeed) {
		StringBuilder paramBuilder = new StringBuilder("OrderApiServiceImpl.refundSuccess(\"");
		paramBuilder.append(orderNo).append(",").append(time_succeed).append("\")");
		logger.info("【"+orderNo+"】"+paramBuilder.toString());
		try {
			this.omsOrderService.orderRefundSuccess(orderNo, time_succeed);
		} catch (Exception e) {
			logger.error("【"+orderNo+"】退款异常，调用dubbo接口 OrderApiServiceImpl.paySuccess()", e);
			throw new ServiceException(e);
		}
		return new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
	}

	/***
	 * 
	 * @param orderNo
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月18日 下午4:21:43
	 * @version V1.0
	 * @return 
	 */
	@Override
	public ResultDto findOrderByNo(String orderNo) throws ServiceException {
		ResultDto resultDto =  null;
		try {
		Map<String,Object> params =  new HashMap<String,Object>();
        params.put("orderNo",orderNo);
		OmsOrder order =  omsOrderService.getOrderByWebhooks(params);
		if(null!=order){
			resultDto =  new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
			resultDto.setData(order);
		}else{
			logger.error("【"+orderNo+"】查无此单,OrderApiServiceImpl.findOrderByNo()");
			resultDto =  new ResultDto(ResultDto.ERROR_CODE, "查无此单");
		}
		} catch (Exception e) {
			logger.error("【"+orderNo+"】获取订单信息异常， OrderApiServiceImpl.findOrderByNo()", e);
			throw new ServiceException(e);
		}
		return resultDto;
	}

	/***
	 * 
	 * @param sysType
	 * @param uId
	 * @param couponId
	 * @param addressId
	 * @param isInvoice
	 * @param goodsListStr
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月27日 上午9:39:10
	 * @version V1.0
	 * @return 
	 */
	@Override
	public ResultDto orderForm(String sysType, String uId, String couponId, String addressId, String isInvoice,
			String goodsListStr) {
		ResultDto resultDto =  null;
		try {
			OmsOrder omsOrder = omsOrderFormService.placeAnOrder(sysType, uId, couponId, addressId, isInvoice, goodsListStr);
			resultDto =  new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
			resultDto.setData(omsOrder);
		} catch (ServiceException e) {
			logger.error("===OrderApiServiceImpl.orderForm()===成单[业务]异常",e);
			if(e.getCode()==1){
				resultDto =  new ResultDto(ResultDto.ERROR_CODE,e.getMessage());
			}else{
				throw e;
			}
		}catch (Exception e) {
			logger.error("===OrderApiServiceImpl.orderForm()===成单异常",e);
			throw e;
		}
		return resultDto;
	}
	/**
	 * 雷-----2016年11月21日 (非 Javadoc)
	 * <p>
	 * Title: findOrderInfo
	 * </p>
	 * <p>
	 * Description:根据订单号，查询订单与子订单信息
	 * </p>
	 * 
	 * @param orderNo
	 * @return
	 * @see com.ffzx.order.api.service.OrderApiService#findOrderInfo(java.lang.String)
	 */
	@Override
	public OmsOrder findOrderInfo(String orderNo) {
		return omsOrderService.findOrderInfo(orderNo);
	}
	@Override
	public List<OmsOrder> queryOrderData(OmsOrderParamVo vo) {
		Map<String,Object> params=new HashMap<>();
		if(StringUtils.isNotEmpty(vo.getOrderNo())){
			params.put("orderNo", vo.getOrderNo());
		}
		if(StringUtils.isNotEmpty(vo.getAfterSaleNo())){
			params.put("afterSaleNo", vo.getAfterSaleNo());
		}
		List<OmsOrder> list=this.omsOrderService.findByBiz(params);
		if(null != list && list.size()>0){
			List<String> orderNos=new ArrayList<>();
			for(OmsOrder o:list){
				orderNos.add(o.getOrderNo());
			}
			params.clear();
			params.put("orderNos", orderNos);
			List<OmsOrderdetail> dlist=this.omsOrderdetailService.findByBiz(params);
			for(OmsOrder o:list){
				List<OmsOrderdetail> delist=new ArrayList<>();
				for(OmsOrderdetail de:dlist){
					if(o.getOrderNo().equals(de.getOrderNo())){
						delist.add(de);
					}
				}
				o.setDetailList(delist);
			}
		}
		return list;
	}

	/***
	 * 
	 * @param orderNo
	 * @param handleResult
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年11月4日 下午3:28:04
	 * @version V1.0
	 * @return 
	 */
	@Override
	public ResultDto payHandle(String orderNo, String handleResult) throws ServiceException {
		StringBuilder paramBuilder = new StringBuilder("OrderApiServiceImpl.refundSuccess(\"");
		paramBuilder.append(orderNo).append(",").append(handleResult).append("\")");
		logger.info("【"+orderNo+"】"+paramBuilder.toString());
		try {
			this.omsOrderService.payHandle(orderNo, handleResult);;
		} catch (Exception e) {
			logger.error("调用dubbo接口 【"+orderNo+"】支付处理异常", e);
			throw new ServiceException(e);
		}
		return new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
	}

}

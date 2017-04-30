package com.ffzx.order.service.inbuond.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.basedata.api.service.CodeRuleApiService;
import com.ffzx.bms.api.dto.OrderParam;
import com.ffzx.bms.api.service.OrderProcessManagerApiService;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.CodeGenerator;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.order.api.dto.AftersalePickup;
import com.ffzx.order.api.dto.AftersaleRefund;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderRecord;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.service.OutboundSalesDeliveryMqApiService;
import com.ffzx.order.common.AftersaleAuxiliary;
import com.ffzx.order.constant.OrderDetailStatusConstant;
import com.ffzx.order.mq.producer.AftersaleToMemberOtherMq;
import com.ffzx.order.service.AftersaleApplyItemService;
import com.ffzx.order.service.AftersaleApplyService;
import com.ffzx.order.service.AftersalePickupService;
import com.ffzx.order.service.AftersaleRefundService;
import com.ffzx.order.service.OmsOrderRecordService;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.order.service.OmsOrderdetailService;
import com.ffzx.order.service.inbuond.InboundAftersaleQualityService;

@Service("inboundAftersaleQualityService")
public class InboundAftersaleQualityServiceImpl implements InboundAftersaleQualityService {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(InboundAftersaleQualityServiceImpl.class);
	@Autowired
	private AftersalePickupService aftersalePickupService;
	@Autowired
	private CodeRuleApiService codeRuleApiService;
	@Autowired
	private AftersaleRefundService aftersaleRefundService;
	@Autowired
	private AftersaleApplyService aftersaleApplyService;
	@Autowired
	private OmsOrderRecordService omsOrderRecordService;
	@Autowired
	private OmsOrderService omsOrderService;
	@Autowired
	private OmsOrderdetailService omsOrderdetailService;
	@Autowired
	private OutboundSalesDeliveryMqApiService OutboundSalesDeliveryMqApiService;
	@Autowired
	private AftersaleApplyItemService aftersaleApplyItemService;
	@Resource(name = "orderProcessManagerApiService")
	private OrderProcessManagerApiService orderProcessManagerApiService;
	@Autowired
	private AftersaleToMemberOtherMq aftersaleToMemberOtherMq;

	@Transactional(rollbackFor = Exception.class)
	public void updatePickupStatus(SysUser user, List<AftersalePickup> pickups) throws ServiceException {
		try {
			AftersalePickup pickup = pickups.get(0);
			ResultDto stockManagerResultDto = null;
			String pickupNo = pickup.getPickupNo();
			String pickupType = pickup.getAftersaleApply().getApplyType();
			Map<String, Object> params = new HashMap<>();
			params.put("pickupNo", pickupNo);
			params.put("pickupStatus", OrderDetailStatusConstant.APPROVE_SUC);// 审核通过
			params.put("lastUpdateBy.id", user.getId());
			params.put("lastUpdateDate", new Date());
			int result = this.aftersalePickupService.updatePickStatus(params);
			if (result > 0) {
				logger.info("thunder-质检审核进入消息3————————" + pickupNo);
				/**
				 * 换货类型的处理
				 */
				if (OrderDetailStatusConstant.CHANGE_GOODS.equals(pickupType)) {
					OmsOrder order = this.omsOrderService.findOrderInfo(pickup.getOrderNo());
					List<OmsOrderdetail> orderDetails = this.omsOrderdetailService.getOrderDetailListByApplyId(pickup.getAftersaleApply().getId());
					/**
					 * 2016-08-04雷添加预占库存
					 */
					List<OrderParam> list = AftersaleAuxiliary.preholdingStore(order, orderDetails);
					order.setOrderNo("HH" + this.getOrderNO());
					// 扣减库存
					try {
						stockManagerResultDto = orderProcessManagerApiService.pendingExchangePayment(list, order.getRegionId(), order.getOrderNo());
					} catch (Exception e) {
						logger.error("invoke InboundAftersaleQualityServiceImpl===>>updatePickupStatus====>> orderProcessManagerApiService.pendingExchangePayment===>>调用异常", e);
						throw new ServiceException("【" + order.getOrderNo() + "】======原订单：" + pickup.getOrderNo() + "=====orderProcessManagerApiService.pendingExchangePayment(" + order.getCountyStoreCode() + "," + order.getRegionId() + ")库存更新异常");
					}
					aftersaleToMemberOtherMq.changeSaleBroadcast(pickup, order, orderDetails, user);
					// 预占库存成功，将生成换货订单
					if (stockManagerResultDto.getCode().equals(ResultDto.OK_CODE)) {
						// 生成换货订单，并推送wms
						saveChangeOrder(user, order, orderDetails, pickup);
					} else {
						logger.info("invoke InboundAftersaleQualityServiceImpl.updatePickupStatus === >>>\r\n params:" + order.getOrderNo() + "=== >>>扣减库存失败");
						throw new ServiceException(stockManagerResultDto.getMessage());
					}
				} else {
					logger.info("thunder-质检审核进入退款————————" + pickup.getApplyNo() + "," + pickup.getPickupNo());
					// 生成退款单
					saveaftersalerefund(user, pickup);
					/**
					 * 退货广播
					 */
					aftersaleToMemberOtherMq.returnGoods(getSourceOrder(pickup.getApplyNo()), pickup, user);
				}
			}
		} catch (Exception e) {
			logger.error("生成换货订单/退款单；换货订单并推送订单信息给WMS", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 
	 * 雷------2016年9月19日
	 * 
	 * @Title: saveChangeOrder
	 * @Description: 生成换货订单，并维护售后申请表
	 * @param @param user
	 * @param @param order
	 * @param @param orderDetails
	 * @param @param pickup 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@Transactional(rollbackFor = Exception.class)
	private void saveChangeOrder(SysUser user, OmsOrder order, List<OmsOrderdetail> orderDetails, AftersalePickup pickup) throws ServiceException {
		if (StringUtil.isNotNull(order)) {
			/**
			 * 订单id
			 */
			String orderId = UUIDGenerator.getUUID();
			List<OmsOrderdetail> details = AftersaleAuxiliary.combinationChangeOrderDetail(order, orderId, orderDetails);
			if (null != details && details.size() > 0)
				for (OmsOrderdetail omsOrderdetail : details) {
					this.omsOrderdetailService.add(omsOrderdetail);
				}
			int pickcode = this.omsOrderService.add(AftersaleAuxiliary.combinationChangeOrder(user, order, orderId,details));
			/**
			 * 维护售后申请表换货单号
			 */
			if (pickcode > 0) {
				Map<String, Object> params = new HashMap<>();
				params.put("exchangeOrderNo", order.getOrderNo());
				params.put("applyNo", pickup.getApplyNo());
				params.put("lastUpdateBy.id", user.getId());
				params.put("lastUpdateDate", new Date());
				params.put("storageApproveDate", new Date());
				this.aftersaleApplyService.updatePickupNoOrRefundNo(params);
				// 生成订单操作记录
				saveOrderRecord(user, pickup.getOrderNo());
			}
			// 推送wms
			order.setDetailList(details);
			logger.info("换货订单【" + order.getOrderNo() + "】===》推送订单信息到wms ============== >" + JsonMapper.toJsonString(order));
			OutboundSalesDeliveryMqApiService.outboundSalesDelivery(order);
		}
	}

	/**
	 * 
	 * 雷------2016年9月19日
	 * 
	 * @Title: saveaftersalerefund
	 * @Description: 生成退款单
	 * @param @param user
	 * @param @param pickup 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void saveaftersalerefund(SysUser user, AftersalePickup pickup) throws ServiceException {
		logger.info("thunder-质检审核进入消息5————————" + pickup.getApplyNo() + "," + pickup.getPickupNo());
		ResultDto refundCode = codeRuleApiService.getCodeRule("order", "order_aftersalerefund_code");
		String refundNo = refundCode.getData().toString();
		AftersaleRefund refund = new AftersaleRefund();
		refund.setApplyNo(pickup.getApplyNo());
		refund.setAftersaleApply(pickup.getAftersaleApply());
		refund.setOrderNo(pickup.getOrderNo());
		refund.setCreateBy(user);
		refund.setCreateDate(new Date());
		refund.setLastUpdateBy(user);
		refund.setLastUpdateDate(new Date());
		refund.setRefundNo(refundNo);
		refund.setId(UUIDGenerator.getUUID());
		refund.setRefundStatus(OrderDetailStatusConstant.REFUND_APPROVE_NO);
		int pickcode = this.aftersaleRefundService.add(refund);
		// 维护售后申请表取货单号
		if (pickcode > 0) {
			Map<String, Object> params = new HashMap<>();
			params.put("refundNo", refundNo);
			params.put("applyNo", pickup.getApplyNo());
			params.put("lastUpdateBy.id", user.getId());
			params.put("lastUpdateDate", new Date());
			params.put("storageApproveDate", new Date());
			this.aftersaleApplyService.updatePickupNoOrRefundNo(params);
			// 生成订单操作记录
			saveOrderRecord(user, pickup.getOrderNo());
		}
	}

	private void saveOrderRecord(SysUser user, String orderNo) {
		OmsOrderRecord record = new OmsOrderRecord();
		record.setOrderNo(orderNo);
		record.setOprId(user.getId());
		record.setOprName(user.getName());
		record.setDescription("售后取货单：审核通过");
		record.setId(UUIDGenerator.getUUID());
		record.setRecordType(OrderDetailStatusConstant.NO);
		record.setCreateDate(new Date());
		try {
			this.omsOrderRecordService.add(record);
		} catch (ServiceException e) {
			logger.error("InboundAftersaleQualityServiceImpl.saveOrderRecord() Exception=》订单系统-售后管理-获取售后申请单-时间转换", e);
			throw new ServiceException(e);
		}
	}

	@Autowired
	CodeGenerator codeGenerator;

	// 后去商品编码
	private String getOrderNO() {
		logger.info("OmsOrderServiceImpl.getOrderNO ===>>>begin");
		String orderNo = null;
		try {
			orderNo = codeGenerator.generate("O2O_", "TIMESTAMP", 3);
			if (orderNo != null) {
				orderNo = orderNo.substring(4);
			}
		} catch (Exception e) {
			logger.info("codeGenerator.generate===>>生成订单号异常>>", e);
			try {
				ResultDto orderCodeDubboResult = codeRuleApiService.getCodeRule("order", "OMS_ORDER_NO");
				if (!orderCodeDubboResult.getCode().equals(ResultDto.OK_CODE)) {// 若订单号生成异常
																				// 此处可临时生成单号
					logger.info("调用codeRuleApiService.getCodeRule===>>生成订单号异常>>" + orderCodeDubboResult.getMessage());
				}
				if (StringUtil.isNotNull(orderCodeDubboResult.getData())) {
					orderNo = orderCodeDubboResult.getData().toString();
				}
			} catch (Exception e1) {
				logger.info("codeGenerator.generate===>>生成订单号异常>>", e1);
			}
		}
		return orderNo;
	}

	/**
	 * 
	 * 雷------2016年11月3日
	 * 
	 * @Title: getSourceOrder
	 * @Description: 根据售后申请单号，查询源订单售后信息
	 * @param @param applyNo
	 * @param @return 设定文件
	 * @return OmsOrder 返回类型
	 * @throws
	 */
	public OmsOrder getSourceOrder(String applyNo) {
		return this.aftersaleApplyService.getSourceOrder(applyNo);
	}
}

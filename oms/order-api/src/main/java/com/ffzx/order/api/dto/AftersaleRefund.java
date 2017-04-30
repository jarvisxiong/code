package com.ffzx.order.api.dto;

import java.util.Date;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * @Description: 退款单
 * @author qh.xu
 * @email qianghui.xu@ffzxnet.com
 * @date 2016年5月13日 下午2:14:57
 * @version V1.0
 * 
 */
public class AftersaleRefund extends DataEntity<AftersaleRefund> {

	private static final long serialVersionUID = 1L;

	/**
	 * 退款单号.
	 */
	private String refundNo;

	/**
	 * 售后申请单号.
	 */
	private String applyNo;

	/**
	 * 关联订单号.
	 */
	private String orderNo;

	/**
	 * 关联售后取货单单号. 冗余字段（用作查询）
	 */
	private String pickupNo;

	/**
	 * 关联换货订单号.
	 */
	private String exchangeOrderNo;

	/**
	 * 退款单状态.0：未审核，1：审核通过，其它：已退款
	 */
	private String refundStatus;

	/**
	 * 审核时间
	 */
	private Date approveDate;

	/**
	 * 付款时间（退款时间）
	 */
	private Date payDate;
	/**
	 * 2016-11-15，雷---审核人姓名.
	 */
	private String auditingName;
	/**
	 * 审核人ID
	 */
	private String auditingId;
	/**
	 * 审核时间
	 */
	private Date auditingTime;

	/*
	 * 售后申请单
	 */
	private AftersaleApply aftersaleApply;

	public AftersaleApply getAftersaleApply() {
		return aftersaleApply;
	}

	public void setAftersaleApply(AftersaleApply aftersaleApply) {
		this.aftersaleApply = aftersaleApply;
	}

	// 冗余字段
	private String applyPersonPhone; // 手机号
	// 申请人
	private String applyPersonName;
	// 售后申请单,服务类型.
	private String applyType;
	// 申请开始时间
	private String applyStartDate;
	// 申请结束时间
	private String applyEndDate;
	// 仓库审核开始时间
	private String storageApproveStartDate;
	// 仓库审核结束时间
	private String storageApproveEndDate;
	// 审核开始时间
	private String serviceApproveStartDate;
	// 审核结束时间
	private String serviceApproveEndDate;

	public String getApplyPersonPhone() {
		return applyPersonPhone;
	}

	public void setApplyPersonPhone(String applyPersonPhone) {
		this.applyPersonPhone = applyPersonPhone;
	}

	public String getStorageApproveStartDate() {
		return storageApproveStartDate;
	}

	public void setStorageApproveStartDate(String storageApproveStartDate) {
		this.storageApproveStartDate = storageApproveStartDate;
	}

	public String getStorageApproveEndDate() {
		return storageApproveEndDate;
	}

	public void setStorageApproveEndDate(String storageApproveEndDate) {
		this.storageApproveEndDate = storageApproveEndDate;
	}

	public String getServiceApproveStartDate() {
		return serviceApproveStartDate;
	}

	public void setServiceApproveStartDate(String serviceApproveStartDate) {
		this.serviceApproveStartDate = serviceApproveStartDate;
	}

	public String getServiceApproveEndDate() {
		return serviceApproveEndDate;
	}

	public void setServiceApproveEndDate(String serviceApproveEndDate) {
		this.serviceApproveEndDate = serviceApproveEndDate;
	}

	public String getApplyPersonName() {
		return applyPersonName;
	}

	public void setApplyPersonName(String applyPersonName) {
		this.applyPersonName = applyPersonName;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getApplyStartDate() {
		return applyStartDate;
	}

	public void setApplyStartDate(String applyStartDate) {
		this.applyStartDate = applyStartDate;
	}

	public String getApplyEndDate() {
		return applyEndDate;
	}

	public void setApplyEndDate(String applyEndDate) {
		this.applyEndDate = applyEndDate;
	}

	/**
	 * 
	 * {@linkplain #refundNo}
	 * 
	 * @return the value of aftersale_refund.refund_no
	 */
	public String getRefundNo() {
		return refundNo;
	}

	/**
	 * {@linkplain #refundNo}
	 * 
	 * @param refundNo
	 *            the value for aftersale_refund.refund_no
	 */
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo == null ? null : refundNo.trim();
	}

	/**
	 * 
	 * {@linkplain #applyNo}
	 * 
	 * @return the value of aftersale_refund.apply_no
	 */
	public String getApplyNo() {
		return applyNo;
	}

	/**
	 * {@linkplain #applyNo}
	 * 
	 * @param applyNo
	 *            the value for aftersale_refund.apply_no
	 */
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo == null ? null : applyNo.trim();
	}

	/**
	 * 
	 * {@linkplain #orderNo}
	 * 
	 * @return the value of aftersale_refund.order_no
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * {@linkplain #orderNo}
	 * 
	 * @param orderNo
	 *            the value for aftersale_refund.order_no
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	/**
	 * 
	 * {@linkplain #pickupNo}
	 * 
	 * @return the value of aftersale_refund.pickup_no
	 */
	public String getPickupNo() {
		return pickupNo;
	}

	/**
	 * {@linkplain #pickupNo}
	 * 
	 * @param pickupNo
	 *            the value for aftersale_refund.pickup_no
	 */
	public void setPickupNo(String pickupNo) {
		this.pickupNo = pickupNo == null ? null : pickupNo.trim();
	}

	/**
	 * 
	 * {@linkplain #exchangeOrderNo}
	 * 
	 * @return the value of aftersale_refund.exchange_order_no
	 */
	public String getExchangeOrderNo() {
		return exchangeOrderNo;
	}

	/**
	 * {@linkplain #exchangeOrderNo}
	 * 
	 * @param exchangeOrderNo
	 *            the value for aftersale_refund.exchange_order_no
	 */
	public void setExchangeOrderNo(String exchangeOrderNo) {
		this.exchangeOrderNo = exchangeOrderNo == null ? null : exchangeOrderNo.trim();
	}

	/**
	 * 
	 * {@linkplain #refundStatus}
	 * 
	 * @return the value of aftersale_refund.refund_status
	 */
	public String getRefundStatus() {
		return refundStatus;
	}

	/**
	 * {@linkplain #refundStatus}
	 * 
	 * @param refundStatus
	 *            the value for aftersale_refund.refund_status
	 */
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus == null ? null : refundStatus.trim();
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	/**
	 * 雷----2016年11月15日
	 * 
	 * @return auditingName
	 */
	public String getAuditingName() {
		return auditingName;
	}

	/**
	 * 雷-------2016年11月15日
	 * 
	 * @param auditingName
	 *            要设置的 auditingName
	 */
	public void setAuditingName(String auditingName) {
		this.auditingName = auditingName;
	}

	/**
	 * 雷----2016年11月15日
	 * 
	 * @return auditingId
	 */
	public String getAuditingId() {
		return auditingId;
	}

	/**
	 * 雷-------2016年11月15日
	 * 
	 * @param auditingId
	 *            要设置的 auditingId
	 */
	public void setAuditingId(String auditingId) {
		this.auditingId = auditingId;
	}

	/**
	 * 雷----2016年11月15日
	 * 
	 * @return auditingTime
	 */
	public Date getAuditingTime() {
		return auditingTime;
	}

	/**
	 * 雷-------2016年11月15日
	 * 
	 * @param auditingTime
	 *            要设置的 auditingTime
	 */
	public void setAuditingTime(Date auditingTime) {
		this.auditingTime = auditingTime;
	}

}
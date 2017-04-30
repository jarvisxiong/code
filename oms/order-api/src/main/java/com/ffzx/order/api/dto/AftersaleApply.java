package com.ffzx.order.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * @Description: 售后申请单
 * @author qh.xu
 * @email qianghui.xu@ffzxnet.com
 * @date 2016年5月11日 上午11:52:45
 * @version V1.0
 * 
 */
public class AftersaleApply extends DataEntity<AftersaleApply> {

	private static final long serialVersionUID = 1L;

	/**
	 * 售后申请单号.
	 */
	private String applyNo;

	/**
	 * 关联订单编号.
	 */
	private String orderNo;

	/**
	 * 关联售后取货单号.
	 */
	private String pickupNo;

	/**
	 * 关联退款单号.
	 */
	private String refundNo;

	/**
	 * 关联换货订单号.
	 */
	private String exchangeOrderNo;

	/**
	 * 售后申请单审核状态.0 未审核，1已通过，2已驳回
	 */
	private String applyStatus;

	/**
	 * 售后申请单,服务类型.0:退款(没收到货),1：退货(已收到货)，2：换货(已收到货)
	 */
	private String applyType;

	/**
	 * 客服审核时间.
	 */
	private Date serviceApproveDate;

	/**
	 * 客服审核描述.
	 */
	private String serviceApproveDesc;

	/**
	 * 仓储审核时间.
	 */
	private Date storageApproveDate;

	/**
	 * 仓储审核描述.
	 */
	private String storageApproveDesc;

	/**
	 * 申请人ID.
	 */
	private String applyPersonId;

	/**
	 * 申请人姓名.
	 */
	private String applyPersonName;

	/**
	 * 申请人手机号.
	 */
	private String applyPersonPhone;

	/**
	 * 会员下单选择的收货地址.
	 */
	private String applyPersonAddress;

	/**
	 * 售后原因选择.
	 */
	private String reasonSelect;

	/**
	 * 售后原因说明.
	 */
	private String reasonExplain;

	/**
	 * 售后申请图片
	 */
	private String applyPicImg;

	/***
	 * 关联订单
	 */
	private OmsOrder order;
	/**
	 * 查询辅助，商品条形码
	 */
	private String commodityBarcode;

	private List<AftersaleApplyItem> aftersaleApplyItems;
	// 冗余
	private String orderId;
	private int page;// 页数
	private int pageSize;// 每页显示数量
	private String skuId;
	/**
	 * 雷----2016-07-28，添加售后取货单的审核状态
	 */
	private String pickupStatus;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getApplyPicImg() {
		return applyPicImg;
	}

	public void setApplyPicImg(String applyPicImg) {
		this.applyPicImg = applyPicImg;
	}

	public OmsOrder getOrder() {
		return order;
	}

	public void setOrder(OmsOrder order) {
		this.order = order;
	}

	/*
	 * 冗余
	 */
	// 审核开始时间
	private String serviceApproveStartDate;
	// 审核结束时间
	private String serviceApproveEndDate;
	// 仓库审核开始时间
	private String storageApproveStartDate;
	// 仓库审核结束时间
	private String storageApproveEndDate;
	// 申请开始时间
	private String applyStartDate;
	// 申请结束时间
	private String applyEndDate;

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
	 * {@linkplain #applyNo}
	 * 
	 * @return the value of aftersale_apply.apply_no
	 */
	public String getApplyNo() {
		return applyNo;
	}

	/**
	 * {@linkplain #applyNo}
	 * 
	 * @param applyNo
	 *            the value for aftersale_apply.apply_no
	 */
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo == null ? null : applyNo.trim();
	}

	/**
	 * 
	 * {@linkplain #orderNo}
	 * 
	 * @return the value of aftersale_apply.order_no
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * {@linkplain #orderNo}
	 * 
	 * @param orderNo
	 *            the value for aftersale_apply.order_no
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	/**
	 * 
	 * {@linkplain #pickupNo}
	 * 
	 * @return the value of aftersale_apply.pickup_no
	 */
	public String getPickupNo() {
		return pickupNo;
	}

	/**
	 * {@linkplain #pickupNo}
	 * 
	 * @param pickupNo
	 *            the value for aftersale_apply.pickup_no
	 */
	public void setPickupNo(String pickupNo) {
		this.pickupNo = pickupNo == null ? null : pickupNo.trim();
	}

	/**
	 * 
	 * {@linkplain #refundNo}
	 * 
	 * @return the value of aftersale_apply.refund_no
	 */
	public String getRefundNo() {
		return refundNo;
	}

	/**
	 * {@linkplain #refundNo}
	 * 
	 * @param refundNo
	 *            the value for aftersale_apply.refund_no
	 */
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo == null ? null : refundNo.trim();
	}

	/**
	 * 
	 * {@linkplain #exchangeOrderNo}
	 * 
	 * @return the value of aftersale_apply.exchange_order_no
	 */
	public String getExchangeOrderNo() {
		return exchangeOrderNo;
	}

	/**
	 * {@linkplain #exchangeOrderNo}
	 * 
	 * @param exchangeOrderNo
	 *            the value for aftersale_apply.exchange_order_no
	 */
	public void setExchangeOrderNo(String exchangeOrderNo) {
		this.exchangeOrderNo = exchangeOrderNo == null ? null : exchangeOrderNo.trim();
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	/**
	 * 
	 * {@linkplain #applyType}
	 * 
	 * @return the value of aftersale_apply.apply_type
	 */
	public String getApplyType() {
		return applyType;
	}

	/**
	 * {@linkplain #applyType}
	 * 
	 * @param applyType
	 *            the value for aftersale_apply.apply_type
	 */
	public void setApplyType(String applyType) {
		this.applyType = applyType == null ? null : applyType.trim();
	}

	/**
	 * 
	 * {@linkplain #serviceApproveDate}
	 * 
	 * @return the value of aftersale_apply.service_approve_date
	 */
	public Date getServiceApproveDate() {
		return serviceApproveDate;
	}

	/**
	 * {@linkplain #serviceApproveDate}
	 * 
	 * @param serviceApproveDate
	 *            the value for aftersale_apply.service_approve_date
	 */
	public void setServiceApproveDate(Date serviceApproveDate) {
		this.serviceApproveDate = serviceApproveDate;
	}

	/**
	 * 
	 * {@linkplain #serviceApproveDesc}
	 * 
	 * @return the value of aftersale_apply.service_approve_desc
	 */
	public String getServiceApproveDesc() {
		return serviceApproveDesc;
	}

	/**
	 * {@linkplain #serviceApproveDesc}
	 * 
	 * @param serviceApproveDesc
	 *            the value for aftersale_apply.service_approve_desc
	 */
	public void setServiceApproveDesc(String serviceApproveDesc) {
		this.serviceApproveDesc = serviceApproveDesc == null ? null : serviceApproveDesc.trim();
	}

	/**
	 * 
	 * {@linkplain #storageApproveDate}
	 * 
	 * @return the value of aftersale_apply.storage_approve_date
	 */
	public Date getStorageApproveDate() {
		return storageApproveDate;
	}

	/**
	 * {@linkplain #storageApproveDate}
	 * 
	 * @param storageApproveDate
	 *            the value for aftersale_apply.storage_approve_date
	 */
	public void setStorageApproveDate(Date storageApproveDate) {
		this.storageApproveDate = storageApproveDate;
	}

	/**
	 * 
	 * {@linkplain #storageApproveDesc}
	 * 
	 * @return the value of aftersale_apply.storage_approve_desc
	 */
	public String getStorageApproveDesc() {
		return storageApproveDesc;
	}

	/**
	 * {@linkplain #storageApproveDesc}
	 * 
	 * @param storageApproveDesc
	 *            the value for aftersale_apply.storage_approve_desc
	 */
	public void setStorageApproveDesc(String storageApproveDesc) {
		this.storageApproveDesc = storageApproveDesc == null ? null : storageApproveDesc.trim();
	}

	/**
	 * 
	 * {@linkplain #applyPersonId}
	 * 
	 * @return the value of aftersale_apply.apply_person_id
	 */
	public String getApplyPersonId() {
		return applyPersonId;
	}

	/**
	 * {@linkplain #applyPersonId}
	 * 
	 * @param applyPersonId
	 *            the value for aftersale_apply.apply_person_id
	 */
	public void setApplyPersonId(String applyPersonId) {
		this.applyPersonId = applyPersonId == null ? null : applyPersonId.trim();
	}

	/**
	 * 
	 * {@linkplain #applyPersonName}
	 * 
	 * @return the value of aftersale_apply.apply_person_name
	 */
	public String getApplyPersonName() {
		return applyPersonName;
	}

	/**
	 * {@linkplain #applyPersonName}
	 * 
	 * @param applyPersonName
	 *            the value for aftersale_apply.apply_person_name
	 */
	public void setApplyPersonName(String applyPersonName) {
		this.applyPersonName = applyPersonName == null ? null : applyPersonName.trim();
	}

	/**
	 * 
	 * {@linkplain #applyPersonPhone}
	 * 
	 * @return the value of aftersale_apply.apply_person_phone
	 */
	public String getApplyPersonPhone() {
		return applyPersonPhone;
	}

	/**
	 * {@linkplain #applyPersonPhone}
	 * 
	 * @param applyPersonPhone
	 *            the value for aftersale_apply.apply_person_phone
	 */
	public void setApplyPersonPhone(String applyPersonPhone) {
		this.applyPersonPhone = applyPersonPhone == null ? null : applyPersonPhone.trim();
	}

	/**
	 * 
	 * {@linkplain #applyPersonAddress}
	 * 
	 * @return the value of aftersale_apply.apply_person_address
	 */
	public String getApplyPersonAddress() {
		return applyPersonAddress;
	}

	/**
	 * {@linkplain #applyPersonAddress}
	 * 
	 * @param applyPersonAddress
	 *            the value for aftersale_apply.apply_person_address
	 */
	public void setApplyPersonAddress(String applyPersonAddress) {
		this.applyPersonAddress = applyPersonAddress == null ? null : applyPersonAddress.trim();
	}

	/**
	 * 
	 * {@linkplain #reasonSelect}
	 * 
	 * @return the value of aftersale_apply.reason_select
	 */
	public String getReasonSelect() {
		return reasonSelect;
	}

	/**
	 * {@linkplain #reasonSelect}
	 * 
	 * @param reasonSelect
	 *            the value for aftersale_apply.reason_select
	 */
	public void setReasonSelect(String reasonSelect) {
		this.reasonSelect = reasonSelect == null ? null : reasonSelect.trim();
	}

	/**
	 * 
	 * {@linkplain #reasonExplain}
	 * 
	 * @return the value of aftersale_apply.reason_explain
	 */
	public String getReasonExplain() {
		return reasonExplain;
	}

	/**
	 * {@linkplain #reasonExplain}
	 * 
	 * @param reasonExplain
	 *            the value for aftersale_apply.reason_explain
	 */
	public void setReasonExplain(String reasonExplain) {
		this.reasonExplain = reasonExplain == null ? null : reasonExplain.trim();
	}

	public List<AftersaleApplyItem> getAftersaleApplyItems() {
		return aftersaleApplyItems;
	}

	public void setAftersaleApplyItems(List<AftersaleApplyItem> aftersaleApplyItems) {
		this.aftersaleApplyItems = aftersaleApplyItems;
	}

	/**
	 * 雷----2016年7月28日
	 * 
	 * @return pickupStatus
	 */
	public String getPickupStatus() {
		return pickupStatus;
	}

	/**
	 * 雷-------2016年7月28日
	 * 
	 * @param pickupStatus
	 *            要设置的 pickupStatus
	 */
	public void setPickupStatus(String pickupStatus) {
		this.pickupStatus = pickupStatus;
	}

	/**
	 * 雷----2016年12月7日
	 * 
	 * @return commodityBarcode
	 */
	public String getCommodityBarcode() {
		return commodityBarcode;
	}

	/**
	 * 雷-------2016年12月7日
	 * 
	 * @param commodityBarcode
	 *            要设置的 commodityBarcode
	 */
	public void setCommodityBarcode(String commodityBarcode) {
		this.commodityBarcode = commodityBarcode;
	}

}
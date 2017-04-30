package com.ffzx.order.api.dto;

import java.util.Date;
import java.util.List;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

public class InboundAftersaleReqApiVo extends DataEntity<InboundAftersaleReqApiVo> {
	private static final long serialVersionUID = 1L;

	private String warehouseCode;// 仓库id
	private String warehouseName;// 仓库名
	private String no;// 取货单
	private String orderId;// 订单id
	private String orderNo;// 订单号
	private String contactId;// 联系人ID
	private String contactName;// 联系人姓名
	private String contactPhone;// 联系人电话
	private String contactEmail;// 联系人邮箱
	private String contactAddress;// 联系人地址
	private String userId;// 退货用户id
	private String userName;// 退货人名称
	private String userPhone;// 退货人电话
	private String userAddress;// 退货人地址
	private Date planArrivalDate;// 预计到货时间
	private List<InboundAftersaleReqItemsApiVo> itemsList;// 商品集合
	/**
	 * 客服审核描述.
	 */
	private String serviceApproveDesc;
	/**
	 * 售后申请单,服务类型.0:退款(没收到货),1：退货(已收到货)，2：换货(已收到货)
	 */
	private String applyType;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public Date getPlanArrivalDate() {
		return planArrivalDate;
	}

	public void setPlanArrivalDate(Date planArrivalDate) {
		this.planArrivalDate = planArrivalDate;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public List<InboundAftersaleReqItemsApiVo> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<InboundAftersaleReqItemsApiVo> itemsList) {
		this.itemsList = itemsList;
	}

	/**
	 * 雷----2016年7月26日
	 * 
	 * @return serviceApproveDesc
	 */
	public String getServiceApproveDesc() {
		return serviceApproveDesc;
	}

	/**
	 * 雷-------2016年7月26日
	 * 
	 * @param serviceApproveDesc
	 *            要设置的 serviceApproveDesc
	 */
	public void setServiceApproveDesc(String serviceApproveDesc) {
		this.serviceApproveDesc = serviceApproveDesc;
	}

	/**
	 * 雷----2016年8月8日
	 * 
	 * @return applyType
	 */
	public String getApplyType() {
		return applyType;
	}

	/**
	 * 雷-------2016年8月8日
	 * 
	 * @param applyType
	 *            要设置的 applyType
	 */
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

}

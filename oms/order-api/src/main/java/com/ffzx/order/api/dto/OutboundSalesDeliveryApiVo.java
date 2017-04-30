/**
 * 
 */
package com.ffzx.order.api.dto;

import java.util.Date;
import java.util.List;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * @Description: TODO
 * @author zi.luo
 * @email zi.luo@ffzxnet.com
 * @date 2016年6月7日 上午10:17:12
 * @version V1.0
 *
 */
public class OutboundSalesDeliveryApiVo extends DataEntity<OutboundSalesDeliveryApiVo> {
	private static final long serialVersionUID = 1L;

	private String salesOrderNo; // 销售订单号
	private String salesOrderNoSub; // 销售订子单号
	private Integer buyType; // 购买类型(0普通,1预售,2抢购,3新手专享,4普通活动,5批发活动)
	private String customerPhone; // 收货人电话
	private String customerName; // 收货人名称
	private String customerId; // 收货人Id
	private String customerProvince; // 收货人地址
	private String partnerId; // 合伙人ID
	private String partnerName; // 合伙人名称
	private String partnerPhone; // 合伙人电话
	private String partnerAddressId; // 合伙人地址最下级ID（例如：村id）
	private String orderAmount; // 订单金额
	private String discountAmount; // 优惠金额
	private String payAmount; // 支付金额
	private String freightAmount; // 运费
	private Integer payType; // 支付方式0支付宝PC,1支付宝扫码,2微信支付,3支付宝APP
	private Integer inventoryType; // 库存限制类型0:良品1不良品
	private Integer inventorySupplier; // 库存=0库存在供应商，1库存在大麦场
	private Date orderTime; // 订单时间
	private String warehouseCode; // 仓库编码
	private String storageId; // 仓库id

	private List<OutboundSalesDeliveryItemsApiVo> itemsList; // 商品

	public String getSalesOrderNo() {
		return salesOrderNo;
	}

	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}

	public String getSalesOrderNoSub() {
		return salesOrderNoSub;
	}

	public void setSalesOrderNoSub(String salesOrderNoSub) {
		this.salesOrderNoSub = salesOrderNoSub;
	}

	public Integer getBuyType() {
		return buyType;
	}

	public void setBuyType(Integer buyType) {
		this.buyType = buyType;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerProvince() {
		return customerProvince;
	}

	public void setCustomerProvince(String customerProvince) {
		this.customerProvince = customerProvince;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerPhone() {
		return partnerPhone;
	}

	public void setPartnerPhone(String partnerPhone) {
		this.partnerPhone = partnerPhone;
	}

	public String getPartnerAddressId() {
		return partnerAddressId;
	}

	public void setPartnerAddressId(String partnerAddressId) {
		this.partnerAddressId = partnerAddressId;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getFreightAmount() {
		return freightAmount;
	}

	public void setFreightAmount(String freightAmount) {
		this.freightAmount = freightAmount;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(Integer inventoryType) {
		this.inventoryType = inventoryType;
	}

	public Integer getInventorySupplier() {
		return inventorySupplier;
	}

	public void setInventorySupplier(Integer inventorySupplier) {
		this.inventorySupplier = inventorySupplier;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public List<OutboundSalesDeliveryItemsApiVo> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<OutboundSalesDeliveryItemsApiVo> itemsList) {
		this.itemsList = itemsList;
	}

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

}

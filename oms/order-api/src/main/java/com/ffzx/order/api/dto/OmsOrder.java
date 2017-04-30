package com.ffzx.order.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ffzx.commerce.framework.annotation.Comment;
import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.enums.BuyTypeEnum;
import com.ffzx.order.api.enums.OrderOperationRecordEnum;
import com.ffzx.order.api.enums.OrderTypeEnum;
import com.ffzx.order.api.enums.PayTypeEnum;
import com.ffzx.basedata.api.dto.Warehouse;

/**
 * @Description: 订单
 * @author qh.xu
 * @email qianghui.xu@ffzxnet.com
 * @date 2016年5月10日 下午2:03:34
 * @version V1.0 edit: 2016-5-11 ying.cai 实现Cloneable接口
 */
public class OmsOrder extends DataEntity<OmsOrder> implements Cloneable {

	private static final long serialVersionUID = 1L;
	/**
	 * 对订单的操作 SIGN:签收 ; BACK:退单; RETURN_BACK:撤销退单 ; CANCEL:取消订单 ; DELETE:删除订单
	 * */
	public static final String SIGN = "SIGN";
	public static final String BACK = "BACK";
	public static final String RETURN_BACK = "RETURN_BACK";
	public static final String CANCEL = "CANCEL";
	public static final String DELETE = "DELETE";
	public static final String PAYSUCCESS = "PAYSUCCESS";

	/**
	 * 订单编号,该订单唯一标示.
	 */
	private String orderNo;

	/**
	 * 对应支付网关订单编号.
	 */
	private String payOrderNo;

	/**
	 * 订单配送仓库编码
	 */
	private String warehouseCode;

	/**
	 * 订单状态. 0：待付款,6:支付处理中,1：待收货，2：退款申请中，3：交易关闭(说明有售后)，4：交易完成，5：订单已取消
	 */
	private String status;

	/**
	 * 支付类型.
	 */
	private PayTypeEnum payType;

	/**
	 * 订单购买类型.
	 */
	private BuyTypeEnum buyType;

	/**
	 * 订单类型.
	 */
	private OrderTypeEnum orderType;

	/**
	 * 下单会员ID.
	 */
	private String memberId;
	/**
	 * 下单会员账号.
	 */
	private String memberAccount;
	/**
	 * 下单会员姓名.
	 */
	private String memberName;

	/**
	 * 下单会员电话.
	 */
	private String memberPhone;

	/**
	 * 购买数量.
	 */
	private Integer buyCount;

	/**
	 * 关联合伙人ID.
	 */
	private String partnerId;

	/**
	 * 订单总金额.
	 */
	private BigDecimal totalPrice;

	/**
	 * 运费.
	 */
	private BigDecimal sendCost;

	/**
	 * 优惠金额.
	 */
	private BigDecimal favorablePrice;

	/**
	 * 订单支付金额.
	 */
	private BigDecimal actualPrice;

	/**
	 * 订单支付时间.
	 */
	private Date payTime;

	/**
	 * 退单时间.
	 */
	private Date chargeBackTime;

	/**
	 * 订单完成时间.
	 */
	private Date finishTime;

	/**
	 * 发货时间.
	 */
	private Date sendCommodityTime;

	/**
	 * 预计发货时间.
	 */
	private Date preSendCommodityTime;

	/**
	 * 最后打印时间.
	 */
	private Date lastPrintTime;

	/**
	 * 打印次数.
	 */
	private Integer printCount;

	/**
	 * 绑定仓库ID.
	 */
	private String storageId;

	/**
	 * 配送员（合伙人）.
	 */
	private String sendPerson;

	/**
	 * 配送员名称（合伙人）.
	 */
	private String sendPersonName;

	/**
	 * 配送员电话合伙人）.
	 */
	private String sendPersonPhone;

	/**
	 * 配送员服务点（配送地址即合伙人地址）.
	 */
	private String servicePoint;

	/**
	 * 库存在供应商：0否，1是
	 */
	private String isSupplier;

	/**
	 * 退单操作人.
	 */
	private String chargeBackOpr;

	/**
	 * 订单所属省.
	 */
	private String province;

	/**
	 * 订单所属市.
	 */
	private String city;

	/**
	 * 订单所属区/县.
	 */
	private String area;

	/**
	 * 详细收货地址.
	 */
	private String addressInfo;

	/**
	 * 收货人电话.
	 */
	private String consignPhone;

	/**
	 * 收货人姓名.
	 */
	private String consignName;

	/**
	 * 支付ID.
	 */
	private String chargeId;

	/**
	 * 是否需要发票1:是,0:否.
	 */
	private String isInvoice;

	/**
	 * 发票描述.
	 */
	private String invoiceDesc;

	/**
	 * 对应优惠券ID,分配给用户所对应的中间表Id.
	 */
	private String couponId;

	/**
	 * 对应优惠券名称.
	 */
	private String couponName;

	/**
	 * 优惠券金额.
	 */
	private BigDecimal couponAmount;

	/**
	 * 订单来源0:android,1:ios,2:pc.
	 */
	private String orderSource;

	/**
	 * 当前物流状态. 4拣货完成,5装箱中，6已装箱,7已出库
	 */
	private String curLoisticsStatus;

	/**
	 * 订单分配错误：1:是,0:否.
	 */
	private String allocationError;

	/**
	 * 订单调拨状态. 0：配送APP确认收货 1：配送APP确认送达
	 */
	private String outOrderStatus;

	/**
	 * 关联活动编号.
	 */
	private String activityNo;

	/**
	 * 冗余属性 关联订单明细
	 */
	private List<OmsOrderdetail> detailList;

	private List<OmsOrderRecord> orderRecordList;

	/**
	 * 地区Id(选择地址的最低级地址id).
	 */
	private String regionId;

	/**
	 * 地区名称(所有选择地址名称组合).
	 */
	private String regionName;

	/**
	 * 详细地址.
	 */
	private String detailedAddress;

	/**
	 * 订单是否装箱复核（0：未装箱，1：已装箱）
	 */
	private String orderPackStatus;

	/**
	 * 合伙人编号
	 */
	private String partnerCode;
	/**
	 * 仓库名称
	 */
	private String storageName;

	/***
	 * 订单操作原因记录 add by ying.cai
	 */
	private OrderOperationRecordEnum operationRecord;

	/***
	 * 订单操作时间 add by ying.cai
	 */
	private Date operationDate;
	/**
	 * 自动关闭订单时间
	 */
	private Date overTime;

	private Date distributionDate;// 配送单确认收货时间

	private Date tranTime;// 交易完成时间

	private String couponReceiveId;// 领取ID

	private String orderStatusStr;
	/**
	 * 合伙人对应的县仓库ID
	 */
	private String countyStoreId;
	/**
	 * 合伙人对应的县仓库code
	 */
	private String countyStoreCode;
	/**
	 * 合伙人对应的县仓库名称
	 */
	private String countyStoreName;
	/**
	 * 用户删除订单标识（0：正常，1：删除）
	 */
	private String delForUser;

	/**
	 * 区域仓库
	 */
	private Warehouse areaWarehouse;
	/**
	 * 中央仓库
	 */
	private Warehouse centerWarehouse;
	/**
	 * 2016-09-21-雷 补货日期
	 */
	private Date supplyDate;
	/**
	 * 是否补货：0：不补货，1：补货
	 */
	private String supplyFlag;

	/**
	 * 红包总金额：多个时叠加
	 */
	private BigDecimal totalRedAmount;

	
	@Comment("服务站id")
	private String serviceStationId;
	
	@Comment("服务站code")
	private String serviceStationCode;
	
	@Comment("服务站名称")
	private String serviceStationName;
	/**
	 * 
	 * 合伙人下单 1：是
	 */
	private String isPartnerOrder;
	/**
	 * 是否差价结算：1是 ，其他否
	 */
	private String settlementFlag;
	
	/**
	 * 支付扩展信息
	 */
	private String payExtra;
	/**
	 * 冗余字段
	 * 红包使用ids：多个时用;隔开 
	 */
	private String redPacketUseIds;
	
	public String getOrderStatusStr() {
		if ("0".equals(status)) {
			orderStatusStr = "待付款";
		} else if ("1".equals(status)) {
			orderStatusStr = "待收货";
		} else if ("2".equals(status)) {
			orderStatusStr = "退款申请中";
		} else if ("3".equals(status)) {
			orderStatusStr = "交易关闭";
		} else if ("4".equals(status)) {
			orderStatusStr = "交易完成";
		} else if ("5".equals(status)) {
			orderStatusStr = "订单已取消";
		} else if ("6".equals(status)) {
			orderStatusStr = "支付处理中";
		}
		return orderStatusStr;
	}

	public void setOrderStatusStr(String orderStatusStr) {
		this.orderStatusStr = orderStatusStr;
	}

	public String getCouponReceiveId() {
		return couponReceiveId;
	}

	public void setCouponReceiveId(String couponReceiveId) {
		this.couponReceiveId = couponReceiveId;
	}

	/**
	 * 出库时间时间.
	 */
	private Date outboundTime;

	public Date getTranTime() {
		return tranTime;
	}

	public void setTranTime(Date tranTime) {
		this.tranTime = tranTime;
	}

	public Date getDistributionDate() {
		return distributionDate;
	}

	public void setDistributionDate(Date distributionDate) {
		this.distributionDate = distributionDate;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public String getOrderPackStatus() {
		return orderPackStatus;
	}

	public void setOrderPackStatus(String orderPackStatus) {
		this.orderPackStatus = orderPackStatus;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	/**
	 * 
	 * {@linkplain #orderNo}
	 * 
	 * @return the value of oms_order.order_no
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * {@linkplain #orderNo}
	 * 
	 * @param orderNo
	 *            the value for oms_order.order_no
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	/**
	 * 
	 * {@linkplain #payOrderNo}
	 * 
	 * @return the value of oms_order.pay_order_no
	 */
	public String getPayOrderNo() {
		return payOrderNo;
	}

	/**
	 * {@linkplain #payOrderNo}
	 * 
	 * @param payOrderNo
	 *            the value for oms_order.pay_order_no
	 */
	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo == null ? null : payOrderNo.trim();
	}

	/**
	 * 
	 * {@linkplain #memberId}
	 * 
	 * @return the value of oms_order.member_id
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * {@linkplain #memberId}
	 * 
	 * @param memberId
	 *            the value for oms_order.member_id
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId == null ? null : memberId.trim();
	}

	/**
	 * 
	 * {@linkplain #memberName}
	 * 
	 * @return the value of oms_order.member_name
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * {@linkplain #memberName}
	 * 
	 * @param memberName
	 *            the value for oms_order.member_name
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName == null ? null : memberName.trim();
	}

	/**
	 * 
	 * {@linkplain #memberPhone}
	 * 
	 * @return the value of oms_order.member_phone
	 */
	public String getMemberPhone() {
		return memberPhone;
	}

	/**
	 * {@linkplain #memberPhone}
	 * 
	 * @param memberPhone
	 *            the value for oms_order.member_phone
	 */
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone == null ? null : memberPhone.trim();
	}

	/**
	 * 
	 * {@linkplain #buyCount}
	 * 
	 * @return the value of oms_order.buy_count
	 */
	public Integer getBuyCount() {
		return buyCount;
	}

	/**
	 * {@linkplain #buyCount}
	 * 
	 * @param buyCount
	 *            the value for oms_order.buy_count
	 */
	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	/**
	 * 
	 * {@linkplain #partnerId}
	 * 
	 * @return the value of oms_order.partner_id
	 */
	public String getPartnerId() {
		return partnerId;
	}

	/**
	 * {@linkplain #partnerId}
	 * 
	 * @param partnerId
	 *            the value for oms_order.partner_id
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId == null ? null : partnerId.trim();
	}

	/**
	 * 
	 * {@linkplain #totalPrice}
	 * 
	 * @return the value of oms_order.total_price
	 */
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	/**
	 * {@linkplain #totalPrice}
	 * 
	 * @param totalPrice
	 *            the value for oms_order.total_price
	 */
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * 
	 * {@linkplain #sendCost}
	 * 
	 * @return the value of oms_order.send_cost
	 */
	public BigDecimal getSendCost() {
		return sendCost;
	}

	/**
	 * {@linkplain #sendCost}
	 * 
	 * @param sendCost
	 *            the value for oms_order.send_cost
	 */
	public void setSendCost(BigDecimal sendCost) {
		this.sendCost = sendCost;
	}

	/**
	 * 
	 * {@linkplain #favorablePrice}
	 * 
	 * @return the value of oms_order.favorable_price
	 */
	public BigDecimal getFavorablePrice() {
		return favorablePrice;
	}

	/**
	 * {@linkplain #favorablePrice}
	 * 
	 * @param favorablePrice
	 *            the value for oms_order.favorable_price
	 */
	public void setFavorablePrice(BigDecimal favorablePrice) {
		this.favorablePrice = favorablePrice;
	}

	/**
	 * 
	 * {@linkplain #actualPrice}
	 * 
	 * @return the value of oms_order.actual_price
	 */
	public BigDecimal getActualPrice() {
		return actualPrice;
	}

	/**
	 * {@linkplain #actualPrice}
	 * 
	 * @param actualPrice
	 *            the value for oms_order.actual_price
	 */
	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}

	/**
	 * 
	 * {@linkplain #payTime}
	 * 
	 * @return the value of oms_order.pay_time
	 */
	public Date getPayTime() {
		return payTime;
	}

	/**
	 * {@linkplain #payTime}
	 * 
	 * @param payTime
	 *            the value for oms_order.pay_time
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	/**
	 * 
	 * {@linkplain #chargeBackTime}
	 * 
	 * @return the value of oms_order.charge_back_time
	 */
	public Date getChargeBackTime() {
		return chargeBackTime;
	}

	/**
	 * {@linkplain #chargeBackTime}
	 * 
	 * @param chargeBackTime
	 *            the value for oms_order.charge_back_time
	 */
	public void setChargeBackTime(Date chargeBackTime) {
		this.chargeBackTime = chargeBackTime;
	}

	/**
	 * 
	 * {@linkplain #finishTime}
	 * 
	 * @return the value of oms_order.finish_time
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * {@linkplain #finishTime}
	 * 
	 * @param finishTime
	 *            the value for oms_order.finish_time
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * 
	 * {@linkplain #sendCommodityTime}
	 * 
	 * @return the value of oms_order.send_Commodity_time
	 */
	public Date getSendCommodityTime() {
		return sendCommodityTime;
	}

	/**
	 * {@linkplain #sendCommodityTime}
	 * 
	 * @param sendCommodityTime
	 *            the value for oms_order.send_Commodity_time
	 */
	public void setSendCommodityTime(Date sendCommodityTime) {
		this.sendCommodityTime = sendCommodityTime;
	}

	/**
	 * 
	 * {@linkplain #preSendCommodityTime}
	 * 
	 * @return the value of oms_order.pre_send_Commodity_time
	 */
	public Date getPreSendCommodityTime() {
		return preSendCommodityTime;
	}

	/**
	 * {@linkplain #preSendCommodityTime}
	 * 
	 * @param preSendCommodityTime
	 *            the value for oms_order.pre_send_Commodity_time
	 */
	public void setPreSendCommodityTime(Date preSendCommodityTime) {
		this.preSendCommodityTime = preSendCommodityTime;
	}

	/**
	 * 
	 * {@linkplain #lastPrintTime}
	 * 
	 * @return the value of oms_order.last_print_time
	 */
	public Date getLastPrintTime() {
		return lastPrintTime;
	}

	/**
	 * {@linkplain #lastPrintTime}
	 * 
	 * @param lastPrintTime
	 *            the value for oms_order.last_print_time
	 */
	public void setLastPrintTime(Date lastPrintTime) {
		this.lastPrintTime = lastPrintTime;
	}

	/**
	 * 
	 * {@linkplain #printCount}
	 * 
	 * @return the value of oms_order.print_count
	 */
	public Integer getPrintCount() {
		return printCount;
	}

	/**
	 * {@linkplain #printCount}
	 * 
	 * @param printCount
	 *            the value for oms_order.print_count
	 */
	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}

	/**
	 * 
	 * {@linkplain #storageId}
	 * 
	 * @return the value of oms_order.storage_id
	 */
	public String getStorageId() {
		return storageId;
	}

	/**
	 * {@linkplain #storageId}
	 * 
	 * @param storageId
	 *            the value for oms_order.storage_id
	 */
	public void setStorageId(String storageId) {
		this.storageId = storageId == null ? null : storageId.trim();
	}

	/**
	 * 
	 * {@linkplain #sendPerson}
	 * 
	 * @return the value of oms_order.send_person
	 */
	public String getSendPerson() {
		return sendPerson;
	}

	/**
	 * {@linkplain #sendPerson}
	 * 
	 * @param sendPerson
	 *            the value for oms_order.send_person
	 */
	public void setSendPerson(String sendPerson) {
		this.sendPerson = sendPerson == null ? null : sendPerson.trim();
	}

	/**
	 * 
	 * {@linkplain #chargeBackOpr}
	 * 
	 * @return the value of oms_order.charge_back_opr
	 */
	public String getChargeBackOpr() {
		return chargeBackOpr;
	}

	/**
	 * {@linkplain #chargeBackOpr}
	 * 
	 * @param chargeBackOpr
	 *            the value for oms_order.charge_back_opr
	 */
	public void setChargeBackOpr(String chargeBackOpr) {
		this.chargeBackOpr = chargeBackOpr == null ? null : chargeBackOpr.trim();
	}

	/**
	 * 
	 * {@linkplain #province}
	 * 
	 * @return the value of oms_order.province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * {@linkplain #province}
	 * 
	 * @param province
	 *            the value for oms_order.province
	 */
	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	/**
	 * 
	 * {@linkplain #city}
	 * 
	 * @return the value of oms_order.city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * {@linkplain #city}
	 * 
	 * @param city
	 *            the value for oms_order.city
	 */
	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	/**
	 * 
	 * {@linkplain #area}
	 * 
	 * @return the value of oms_order.area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * {@linkplain #area}
	 * 
	 * @param area
	 *            the value for oms_order.area
	 */
	public void setArea(String area) {
		this.area = area == null ? null : area.trim();
	}

	/**
	 * 
	 * {@linkplain #addressInfo}
	 * 
	 * @return the value of oms_order.address_info
	 */
	public String getAddressInfo() {
		return addressInfo;
	}

	/**
	 * {@linkplain #addressInfo}
	 * 
	 * @param addressInfo
	 *            the value for oms_order.address_info
	 */
	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo == null ? null : addressInfo.trim();
	}

	/**
	 * 
	 * {@linkplain #consignPhone}
	 * 
	 * @return the value of oms_order.consign_phone
	 */
	public String getConsignPhone() {
		return consignPhone;
	}

	/**
	 * {@linkplain #consignPhone}
	 * 
	 * @param consignPhone
	 *            the value for oms_order.consign_phone
	 */
	public void setConsignPhone(String consignPhone) {
		this.consignPhone = consignPhone == null ? null : consignPhone.trim();
	}

	/**
	 * 
	 * {@linkplain #consignName}
	 * 
	 * @return the value of oms_order.consign_name
	 */
	public String getConsignName() {
		return consignName;
	}

	/**
	 * {@linkplain #consignName}
	 * 
	 * @param consignName
	 *            the value for oms_order.consign_name
	 */
	public void setConsignName(String consignName) {
		this.consignName = consignName == null ? null : consignName.trim();
	}

	/**
	 * 
	 * {@linkplain #chargeId}
	 * 
	 * @return the value of oms_order.charge_id
	 */
	public String getChargeId() {
		return chargeId;
	}

	/**
	 * {@linkplain #chargeId}
	 * 
	 * @param chargeId
	 *            the value for oms_order.charge_id
	 */
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId == null ? null : chargeId.trim();
	}

	/**
	 * 
	 * {@linkplain #isInvoice}
	 * 
	 * @return the value of oms_order.is_invoice
	 */
	public String getIsInvoice() {
		return isInvoice;
	}

	/**
	 * {@linkplain #isInvoice}
	 * 
	 * @param isInvoice
	 *            the value for oms_order.is_invoice
	 */
	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice == null ? null : isInvoice.trim();
	}

	/**
	 * 
	 * {@linkplain #invoiceDesc}
	 * 
	 * @return the value of oms_order.invoice_desc
	 */
	public String getInvoiceDesc() {
		return invoiceDesc;
	}

	/**
	 * {@linkplain #invoiceDesc}
	 * 
	 * @param invoiceDesc
	 *            the value for oms_order.invoice_desc
	 */
	public void setInvoiceDesc(String invoiceDesc) {
		this.invoiceDesc = invoiceDesc == null ? null : invoiceDesc.trim();
	}

	/**
	 * 
	 * {@linkplain #couponId}
	 * 
	 * @return the value of oms_order.coupon_id
	 */
	public String getCouponId() {
		return couponId;
	}

	/**
	 * {@linkplain #couponId}
	 * 
	 * @param couponId
	 *            the value for oms_order.coupon_id
	 */
	public void setCouponId(String couponId) {
		this.couponId = couponId == null ? null : couponId.trim();
	}

	/**
	 * 
	 * {@linkplain #couponName}
	 * 
	 * @return the value of oms_order.coupon_name
	 */
	public String getCouponName() {
		return couponName;
	}

	/**
	 * {@linkplain #couponName}
	 * 
	 * @param couponName
	 *            the value for oms_order.coupon_name
	 */
	public void setCouponName(String couponName) {
		this.couponName = couponName == null ? null : couponName.trim();
	}

	/**
	 * 
	 * {@linkplain #couponAmount}
	 * 
	 * @return the value of oms_order.coupon_amount
	 */
	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	/**
	 * {@linkplain #couponAmount}
	 * 
	 * @param couponAmount
	 *            the value for oms_order.coupon_amount
	 */
	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	/**
	 * 
	 * {@linkplain #orderSource}
	 * 
	 * @return the value of oms_order.order_source
	 */
	public String getOrderSource() {
		return orderSource;
	}

	/**
	 * {@linkplain #orderSource}
	 * 
	 * @param orderSource
	 *            the value for oms_order.order_source
	 */
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource == null ? null : orderSource.trim();
	}

	/**
	 * 
	 * {@linkplain #curLoisticsStatus}
	 * 
	 * @return the value of oms_order.cur_loistics_status
	 */
	public String getCurLoisticsStatus() {
		return curLoisticsStatus;
	}

	/**
	 * {@linkplain #curLoisticsStatus}
	 * 
	 * @param curLoisticsStatus
	 *            the value for oms_order.cur_loistics_status
	 */
	public void setCurLoisticsStatus(String curLoisticsStatus) {
		this.curLoisticsStatus = curLoisticsStatus == null ? null : curLoisticsStatus.trim();
	}

	/**
	 * 
	 * {@linkplain #outOrderStatus}
	 * 
	 * @return the value of oms_order.out_order_status
	 */
	public String getOutOrderStatus() {
		return outOrderStatus;
	}

	/**
	 * {@linkplain #outOrderStatus}
	 * 
	 * @param outOrderStatus
	 *            the value for oms_order.out_order_status
	 */
	public void setOutOrderStatus(String outOrderStatus) {
		this.outOrderStatus = outOrderStatus == null ? null : outOrderStatus.trim();
	}

	/**
	 * 
	 * {@linkplain #activityNo}
	 * 
	 * @return the value of oms_order.activity_no
	 */
	public String getActivityNo() {
		return activityNo;
	}

	/**
	 * {@linkplain #activityNo}
	 * 
	 * @param activityNo
	 *            the value for oms_order.activity_no
	 */
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo == null ? null : activityNo.trim();
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public PayTypeEnum getPayType() {
		return payType;
	}

	public void setPayType(PayTypeEnum payType) {
		this.payType = payType;
	}

	public BuyTypeEnum getBuyType() {
		return buyType;
	}

	public void setBuyType(BuyTypeEnum buyType) {
		this.buyType = buyType;
	}

	public OrderTypeEnum getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderTypeEnum orderType) {
		this.orderType = orderType;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public List<OmsOrderdetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<OmsOrderdetail> detailList) {
		this.detailList = detailList;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getSendPersonName() {
		return sendPersonName;
	}

	public void setSendPersonName(String sendPersonName) {
		this.sendPersonName = sendPersonName;
	}

	public String getServicePoint() {
		return servicePoint;
	}

	public void setServicePoint(String servicePoint) {
		this.servicePoint = servicePoint;
	}

	/**
	 * 相关枚举取值方法
	 */
	public String getStatusName() {
		if (StringUtil.isNotNull(this.getStatus())) {
			switch (this.getStatus()) {
			case "0":
				return "待付款";
			case "1":
				return "待收货";
			case "2":
				return "退款申请中";
			case "3":
				return "交易关闭";
			case "4":
				return "交易完成";
			case "5":
				return "订单已取消";
			case "6":
				return "支付处理中";
			default:
				return "其他";
			}
		} else {
			return null;
		}
	}

	public String getPayTypeName() {
		if (StringUtil.isNotNull(this.getPayType())) {
			return this.getPayType().getName();
		} else {
			return null;
		}
	}

	public String getBuyTypeName() {
		if (StringUtil.isNotNull(this.getBuyType())) {
			return this.getBuyType().getName();
		} else {
			return null;
		}
	}

	public String getOrderTypeName() {
		if (StringUtil.isNotNull(this.getOrderType())) {
			return this.getOrderType().getName();
		} else {
			return null;
		}
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getDetailedAddress() {
		return detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

	public List<OmsOrderRecord> getOrderRecordList() {
		return orderRecordList;
	}

	public void setOrderRecordList(List<OmsOrderRecord> orderRecordList) {
		this.orderRecordList = orderRecordList;
	}

	public String getAllocationError() {
		return allocationError;
	}

	public void setAllocationError(String allocationError) {
		this.allocationError = allocationError;
	}

	public OrderOperationRecordEnum getOperationRecord() {
		return operationRecord;
	}

	public void setOperationRecord(OrderOperationRecordEnum operationRecord) {
		this.operationRecord = operationRecord;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public String getSendPersonPhone() {
		return sendPersonPhone;
	}

	public void setSendPersonPhone(String sendPersonPhone) {
		this.sendPersonPhone = sendPersonPhone;
	}

	public String getIsSupplier() {
		return isSupplier;
	}

	public void setIsSupplier(String isSupplier) {
		this.isSupplier = isSupplier;
	}

	public Date getOutboundTime() {
		return outboundTime;
	}

	public void setOutboundTime(Date outboundTime) {
		this.outboundTime = outboundTime;
	}

	/**
	 * 雷----2016年8月1日
	 * 
	 * @return countyStoreId
	 */
	public String getCountyStoreId() {
		return countyStoreId;
	}

	/**
	 * 雷-------2016年8月1日
	 * 
	 * @param countyStoreId
	 *            要设置的 countyStoreId
	 */
	public void setCountyStoreId(String countyStoreId) {
		this.countyStoreId = countyStoreId;
	}

	/**
	 * 雷----2016年8月1日
	 * 
	 * @return countyStoreCode
	 */
	public String getCountyStoreCode() {
		return countyStoreCode;
	}

	/**
	 * 雷-------2016年8月1日
	 * 
	 * @param countyStoreCode
	 *            要设置的 countyStoreCode
	 */
	public void setCountyStoreCode(String countyStoreCode) {
		this.countyStoreCode = countyStoreCode;
	}

	/**
	 * 雷----2016年8月1日
	 * 
	 * @return countyStoreName
	 */
	public String getCountyStoreName() {
		return countyStoreName;
	}

	/**
	 * 雷-------2016年8月1日
	 * 
	 * @param countyStoreName
	 *            要设置的 countyStoreName
	 */
	public void setCountyStoreName(String countyStoreName) {
		this.countyStoreName = countyStoreName;
	}

	/**
	 * 雷----2016年8月3日
	 * 
	 * @return delForUser
	 */
	public String getDelForUser() {
		return delForUser;
	}

	/**
	 * 雷-------2016年8月3日
	 * 
	 * @param delForUser
	 *            要设置的 delForUser
	 */
	public void setDelForUser(String delForUser) {
		this.delForUser = delForUser;
	}

	/**
	 * @return the areaWarehouse
	 */
	public Warehouse getAreaWarehouse() {
		return areaWarehouse;
	}

	/**
	 * @param areaWarehouse the areaWarehouse to set
	 */
	public void setAreaWarehouse(Warehouse areaWarehouse) {
		this.areaWarehouse = areaWarehouse;
	}

	/**
	 * @return the centerWarehouse
	 */
	public Warehouse getCenterWarehouse() {
		return centerWarehouse;
	}

	/**
	 * @param centerWarehouse the centerWarehouse to set
	 */
	public void setCenterWarehouse(Warehouse centerWarehouse) {
		this.centerWarehouse = centerWarehouse;
	}
	
	/**
	 * 雷----2016年9月21日
	 * 
	 * @return supplyDate
	 */
	public Date getSupplyDate() {
		return supplyDate;
	}

	/**
	 * 雷-------2016年9月21日
	 * 
	 * @param supplyDate
	 *            要设置的 supplyDate
	 */
	public void setSupplyDate(Date supplyDate) {
		this.supplyDate = supplyDate;
	}

	/**
	 * 雷----2016年9月21日
	 * 
	 * @return supplyFlag
	 */
	public String getSupplyFlag() {
		return supplyFlag;
	}

	/**
	 * 雷-------2016年9月21日
	 * 
	 * @param supplyFlag
	 *            要设置的 supplyFlag
	 */
	public void setSupplyFlag(String supplyFlag) {
		this.supplyFlag = supplyFlag;
	}

	/**
	 * @return the totalRedAmount
	 */
	public BigDecimal getTotalRedAmount() {
		return totalRedAmount;
	}

	/**
	 * @param totalRedAmount the totalRedAmount to set
	 */
	public void setTotalRedAmount(BigDecimal totalRedAmount) {
		this.totalRedAmount = totalRedAmount;
	}

	/**
	 * @return the redPacketUseIds
	 */
	public String getRedPacketUseIds() {
		return redPacketUseIds;
	}

	/**
	 * @param redPacketUseIds the redPacketUseIds to set
	 */
	public void setRedPacketUseIds(String redPacketUseIds) {
		this.redPacketUseIds = redPacketUseIds;
	}

	/**
	 * @return the serviceStationId
	 */
	public String getServiceStationId() {
		return serviceStationId;
	}

	/**
	 * @param serviceStationId the serviceStationId to set
	 */
	public void setServiceStationId(String serviceStationId) {
		this.serviceStationId = serviceStationId;
	}

	/**
	 * @return the serviceStationCode
	 */
	public String getServiceStationCode() {
		return serviceStationCode;
	}

	/**
	 * @param serviceStationCode the serviceStationCode to set
	 */
	public void setServiceStationCode(String serviceStationCode) {
		this.serviceStationCode = serviceStationCode;
	}

	/**
	 * @return the serviceStationName
	 */
	public String getServiceStationName() {
		return serviceStationName;
	}

	/**
	 * @param serviceStationName the serviceStationName to set
	 */
	public void setServiceStationName(String serviceStationName) {
		this.serviceStationName = serviceStationName;
	}

	/**
	 * @return the settlementFlag
	 */
	public String getSettlementFlag() {
		return settlementFlag;
	}

	/**
	 * @param settlementFlag the settlementFlag to set
	 */
	public void setSettlementFlag(String settlementFlag) {
		this.settlementFlag = settlementFlag;
	}

	/**
	 * @return the isPartnerOrder
	 */
	public String getIsPartnerOrder() {
		return isPartnerOrder;
	}

	/**
	 * @param isPartnerOrder the isPartnerOrder to set
	 */
	public void setIsPartnerOrder(String isPartnerOrder) {
		this.isPartnerOrder = isPartnerOrder;
	}

	/**
	 * @return the payExtra
	 */
	public String getPayExtra() {
		return payExtra;
	}

	/**
	 * @param payExtra the payExtra to set
	 */
	public void setPayExtra(String payExtra) {
		this.payExtra = payExtra;
	}
	
	
	
}
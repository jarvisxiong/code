/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年3月23日 上午11:53:44
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package com.ffzx.order.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ffzx.commerce.framework.annotation.Comment;
import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.commerce.framework.utils.DateUtil;

/***
 * 差价结算明细表
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年3月23日 上午11:53:44
 * @version V1.0
 */
public class PriceSettlementDetail extends DataEntity<PriceSettlementDetail> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1063891740702616360L;
	
	@Comment("结算编号")
	private String psNo;
	
	@Comment("订单编号")
	private String orderNo;

	@Comment("商品图片路径")
	private String commodityImage;
	
	@Comment("商品标题")
	private String commodityTitle;
	
	@Comment("skuId")
	private String skuId;

	@Comment("skuCode")
	private String skuCode;
	
	@Comment("虚拟商品关联id")
	private String virtualGdId;
	
	@Comment("商品单位")
	private String commodityUnit;
	
	@Comment("销售价")
	private BigDecimal actSalePrice;
	@Comment("销售总价")
	private BigDecimal saleAmount;

	@Comment("批发售价")
	private BigDecimal pifaPrice;
	
	@Comment("实际支付金额")
	private BigDecimal actPayAmount;
	
	@Comment("购买数量")
	private Integer buyNum;
	/**
	 * 明细差额.
	 * 实际销售价-批发价
	 * balance=(actSalePrice-prfaPrice)*buyNum
	 */
	@Comment("差额")
	private BigDecimal balance;
	
	@Comment("下单会员ID")
	private String memberId;
	
	@Comment("下单会员账号")
	private String memberAccount;

	@Comment("下单会员姓名")
	private String memberName;
	
	@Comment("关联合伙人ID")
	private String partnerId;
	
	@Comment("关联合伙人编码")
	private String partnerCode;
	
	@Comment("关联合伙人名称")
	private String partnerName;
	
	@Comment("关联合伙人手机号")
	private String partnerPhone;
	
	@Comment("服务站id")
	private String serviceStationId;
	
	@Comment("服务站code")
	private String serviceStationCode;
	
	@Comment("服务站名称")
	private String serviceStationName;
    
	@Comment("导入编码")
	private String serialCode;
	@Comment("关联订单明细id")
	private String omsOrderDetailId;
	@Comment("退货数量")
	private Integer refundNum;
	@Comment("下单时间")
	private Date orderTime;
	private PriceSettlement priceSettlement;
	/**
	 * 冗余字段 differencePrice= actSalePrice-pifaPrice
	 */
	private BigDecimal differencePrice;
	
	/**
	 * 冗余字段
	 */
	private String orderDateStr;
	/**
	 * @return the psNo
	 */
	public String getPsNo() {
		return psNo;
	}

	/**
	 * @param psNo the psNo to set
	 */
	public void setPsNo(String psNo) {
		this.psNo = psNo;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the commodityImage
	 */
	public String getCommodityImage() {
		return commodityImage;
	}

	/**
	 * @param commodityImage the commodityImage to set
	 */
	public void setCommodityImage(String commodityImage) {
		this.commodityImage = commodityImage;
	}

	/**
	 * @return the commodityTitle
	 */
	public String getCommodityTitle() {
		return commodityTitle;
	}

	/**
	 * @param commodityTitle the commodityTitle to set
	 */
	public void setCommodityTitle(String commodityTitle) {
		this.commodityTitle = commodityTitle;
	}

	/**
	 * @return the skuId
	 */
	public String getSkuId() {
		return skuId;
	}

	/**
	 * @param skuId the skuId to set
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	/**
	 * @return the skuCode
	 */
	public String getSkuCode() {
		return skuCode;
	}

	/**
	 * @param skuCode the skuCode to set
	 */
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	/**
	 * @return the virtualGdId
	 */
	public String getVirtualGdId() {
		return virtualGdId;
	}

	/**
	 * @param virtualGdId the virtualGdId to set
	 */
	public void setVirtualGdId(String virtualGdId) {
		this.virtualGdId = virtualGdId;
	}

	/**
	 * @return the commodityUnit
	 */
	public String getCommodityUnit() {
		return commodityUnit;
	}

	/**
	 * @param commodityUnit the commodityUnit to set
	 */
	public void setCommodityUnit(String commodityUnit) {
		this.commodityUnit = commodityUnit;
	}

	/**
	 * @return the actSalePrice
	 */
	public BigDecimal getActSalePrice() {
		return actSalePrice;
	}

	/**
	 * @param actSalePrice the actSalePrice to set
	 */
	public void setActSalePrice(BigDecimal actSalePrice) {
		this.actSalePrice = actSalePrice;
	}

	/**
	 * @return the pifaPrice
	 */
	public BigDecimal getPifaPrice() {
		return pifaPrice;
	}

	/**
	 * @param pifaPrice the pifaPrice to set
	 */
	public void setPifaPrice(BigDecimal pifaPrice) {
		this.pifaPrice = pifaPrice;
	}

	/**
	 * @return the actPayAmount
	 */
	public BigDecimal getActPayAmount() {
		return actPayAmount;
	}

	/**
	 * @param actPayAmount the actPayAmount to set
	 */
	public void setActPayAmount(BigDecimal actPayAmount) {
		this.actPayAmount = actPayAmount;
	}

	/**
	 * @return the buyNum
	 */
	public Integer getBuyNum() {
		return buyNum;
	}

	/**
	 * @param buyNum the buyNum to set
	 */
	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	/**
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the memberAccount
	 */
	public String getMemberAccount() {
		return memberAccount;
	}

	/**
	 * @param memberAccount the memberAccount to set
	 */
	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the partnerId
	 */
	public String getPartnerId() {
		return partnerId;
	}

	/**
	 * @param partnerId the partnerId to set
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * @return the partnerCode
	 */
	public String getPartnerCode() {
		return partnerCode;
	}

	/**
	 * @param partnerCode the partnerCode to set
	 */
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	/**
	 * @return the partnerName
	 */
	public String getPartnerName() {
		return partnerName;
	}

	/**
	 * @param partnerName the partnerName to set
	 */
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
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
	 * @return the serialCode
	 */
	public String getSerialCode() {
		return serialCode;
	}

	/**
	 * @param serialCode the serialCode to set
	 */
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	/**
	 * @return the omsOrderDetailId
	 */
	public String getOmsOrderDetailId() {
		return omsOrderDetailId;
	}

	/**
	 * @param omsOrderDetailId the omsOrderDetailId to set
	 */
	public void setOmsOrderDetailId(String omsOrderDetailId) {
		this.omsOrderDetailId = omsOrderDetailId;
	}

	/**
	 * @return the priceSettlement
	 */
	public PriceSettlement getPriceSettlement() {
		return priceSettlement;
	}

	/**
	 * @param priceSettlement the priceSettlement to set
	 */
	public void setPriceSettlement(PriceSettlement priceSettlement) {
		this.priceSettlement = priceSettlement;
	}

	/**
	 * @return the saleAmount
	 */
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	/**
	 * @param saleAmount the saleAmount to set
	 */
	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	/**
	 * @return the partnerPhone
	 */
	public String getPartnerPhone() {
		return partnerPhone;
	}

	/**
	 * @param partnerPhone the partnerPhone to set
	 */
	public void setPartnerPhone(String partnerPhone) {
		this.partnerPhone = partnerPhone;
	}

	/**
	 * @return the refundNum
	 */
	public Integer getRefundNum() {
		return refundNum;
	}

	/**
	 * @param refundNum the refundNum to set
	 */
	public void setRefundNum(Integer refundNum) {
		this.refundNum = refundNum;
	}

	/**
	 * @return the orderTime
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime the orderTime to set
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * @return the differencePrice
	 */
	public BigDecimal getDifferencePrice() {
		return this.actSalePrice.subtract(this.pifaPrice==null?new BigDecimal("0"):this.pifaPrice);
	}

	/**
	 * @param differencePrice the differencePrice to set
	 */
	public void setDifferencePrice(BigDecimal differencePrice) {
		this.differencePrice = differencePrice;
	}

	/**
	 * @return the orderDateStr
	 */
	public String getOrderDateStr() {
		return DateUtil.formatDateTime(this.orderTime);
	}

	/**
	 * @param orderDateStr the orderDateStr to set
	 */
	public void setOrderDateStr(String orderDateStr) {
		this.orderDateStr = orderDateStr;
	}
	
	
}

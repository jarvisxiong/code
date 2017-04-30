package com.ffzx.order.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

import java.math.BigDecimal;

/**
 * @Description: 售后申请单明细
 * @author qh.xu
 * @email qianghui.xu@ffzxnet.com
 * @date 2016年5月13日 下午2:13:51
 * @version V1.0
 * 
 */
public class AftersaleApplyItem extends DataEntity<AftersaleApplyItem> {

	private static final long serialVersionUID = 1L;

	/**
	 * 关联商品图片路径.
	 */
	private String commodityPic;

	/**
	 * 关联商品名称.
	 */
	private String commodityName;

	/**
	 * 商品条形码.
	 */
	private String commodityBarcode;

	/**
	 * 商品规格.
	 */
	private String commoditySpecifications;

	/**
	 * 商品单位.
	 */
	private String commodityUnit;

	/**
	 * 支付单价.
	 */
	private BigDecimal commodityPrice;

	/**
	 * 实付金额.
	 */
	private BigDecimal actPayAmount;

	/**
	 * 商品购买类型.
	 */
	private String commodityBuyType;

	/**
	 * 商品购买数量.
	 */
	private Integer commodityBuyNum;

	/**
	 * 商品退货数量.
	 */
	private Integer returnNum;

	/**
	 * 售后状态.
	 */
	private String aftersaleStatus;

	/**
	 * 关联售后申请单ID.
	 */
	private String applyId;

	/**
	 * 售后申请单编号.
	 */
	private String applyNo;
	/***
	 * 商品skuid
	 */
	private String skuId;

	/***
	 * 商品优惠金额
	 */
	private BigDecimal favouredAmount;
	/**
	 * 红包金额
	 */
	private BigDecimal redPacketAmount;

	/****
	 * 商品sku属性
	 */
	private String commodityAttributeValues;

	/**
	 * 雷----2016-09-14 若此条明细是买赠 赠品明细，则该字段存储主商品明细id
	 */
	private String mainCommodityId;

	/**
	 * 【买赠】明细标示，1：【买赠】主商品明细，2：【买赠】赠品明细，空标示普通明细
	 */
	private String buyGifts;

	public String getCommodityAttributeValues() {
		return commodityAttributeValues;
	}

	public void setCommodityAttributeValues(String commodityAttributeValues) {
		this.commodityAttributeValues = commodityAttributeValues;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public BigDecimal getFavouredAmount() {
		return favouredAmount;
	}

	public void setFavouredAmount(BigDecimal favouredAmount) {
		this.favouredAmount = favouredAmount;
	}

	/**
	 * 
	 * {@linkplain #commodityPic}
	 * 
	 * @return the value of aftersale_apply_item.commodity_pic
	 */
	public String getCommodityPic() {
		return commodityPic;
	}

	/**
	 * {@linkplain #commodityPic}
	 * 
	 * @param commodityPic
	 *            the value for aftersale_apply_item.commodity_pic
	 */
	public void setCommodityPic(String commodityPic) {
		this.commodityPic = commodityPic == null ? null : commodityPic.trim();
	}

	/**
	 * 
	 * {@linkplain #commodityName}
	 * 
	 * @return the value of aftersale_apply_item.commodity_name
	 */
	public String getCommodityName() {
		return commodityName;
	}

	/**
	 * {@linkplain #commodityName}
	 * 
	 * @param commodityName
	 *            the value for aftersale_apply_item.commodity_name
	 */
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName == null ? null : commodityName.trim();
	}

	/**
	 * 
	 * {@linkplain #commodityBarcode}
	 * 
	 * @return the value of aftersale_apply_item.commodity_barcode
	 */
	public String getCommodityBarcode() {
		return commodityBarcode;
	}

	/**
	 * {@linkplain #commodityBarcode}
	 * 
	 * @param commodityBarcode
	 *            the value for aftersale_apply_item.commodity_barcode
	 */
	public void setCommodityBarcode(String commodityBarcode) {
		this.commodityBarcode = commodityBarcode == null ? null : commodityBarcode.trim();
	}

	/**
	 * 
	 * {@linkplain #commoditySpecifications}
	 * 
	 * @return the value of aftersale_apply_item.commodity_specifications
	 */
	public String getCommoditySpecifications() {
		return commoditySpecifications;
	}

	/**
	 * {@linkplain #commoditySpecifications}
	 * 
	 * @param commoditySpecifications
	 *            the value for aftersale_apply_item.commodity_specifications
	 */
	public void setCommoditySpecifications(String commoditySpecifications) {
		this.commoditySpecifications = commoditySpecifications == null ? null : commoditySpecifications.trim();
	}

	/**
	 * 
	 * {@linkplain #commodityUnit}
	 * 
	 * @return the value of aftersale_apply_item.commodity_unit
	 */
	public String getCommodityUnit() {
		return commodityUnit;
	}

	/**
	 * {@linkplain #commodityUnit}
	 * 
	 * @param commodityUnit
	 *            the value for aftersale_apply_item.commodity_unit
	 */
	public void setCommodityUnit(String commodityUnit) {
		this.commodityUnit = commodityUnit == null ? null : commodityUnit.trim();
	}

	/**
	 * 
	 * {@linkplain #commodityPrice}
	 * 
	 * @return the value of aftersale_apply_item.commodity_price
	 */
	public BigDecimal getCommodityPrice() {
		return commodityPrice;
	}

	/**
	 * {@linkplain #commodityPrice}
	 * 
	 * @param commodityPrice
	 *            the value for aftersale_apply_item.commodity_price
	 */
	public void setCommodityPrice(BigDecimal commodityPrice) {
		this.commodityPrice = commodityPrice;
	}

	/**
	 * 
	 * {@linkplain #actPayAmount}
	 * 
	 * @return the value of aftersale_apply_item.act_pay_amount
	 */
	public BigDecimal getActPayAmount() {
		return actPayAmount;
	}

	/**
	 * {@linkplain #actPayAmount}
	 * 
	 * @param actPayAmount
	 *            the value for aftersale_apply_item.act_pay_amount
	 */
	public void setActPayAmount(BigDecimal actPayAmount) {
		this.actPayAmount = actPayAmount;
	}

	/**
	 * 
	 * {@linkplain #commodityBuyType}
	 * 
	 * @return the value of aftersale_apply_item.commodity_buy_type
	 */
	public String getCommodityBuyType() {
		return commodityBuyType;
	}

	/**
	 * {@linkplain #commodityBuyType}
	 * 
	 * @param commodityBuyType
	 *            the value for aftersale_apply_item.commodity_buy_type
	 */
	public void setCommodityBuyType(String commodityBuyType) {
		this.commodityBuyType = commodityBuyType == null ? null : commodityBuyType.trim();
	}

	/**
	 * 
	 * {@linkplain #commodityBuyNum}
	 * 
	 * @return the value of aftersale_apply_item.commodity_buy_num
	 */
	public Integer getCommodityBuyNum() {
		return commodityBuyNum;
	}

	/**
	 * {@linkplain #commodityBuyNum}
	 * 
	 * @param commodityBuyNum
	 *            the value for aftersale_apply_item.commodity_buy_num
	 */
	public void setCommodityBuyNum(Integer commodityBuyNum) {
		this.commodityBuyNum = commodityBuyNum;
	}

	/**
	 * 
	 * {@linkplain #returnNum}
	 * 
	 * @return the value of aftersale_apply_item.return_num
	 */
	public Integer getReturnNum() {
		return returnNum;
	}

	/**
	 * {@linkplain #returnNum}
	 * 
	 * @param returnNum
	 *            the value for aftersale_apply_item.return_num
	 */
	public void setReturnNum(Integer returnNum) {
		this.returnNum = returnNum;
	}

	/**
	 * 
	 * {@linkplain #aftersaleStatus}
	 * 
	 * @return the value of aftersale_apply_item.aftersale_status
	 */
	public String getAftersaleStatus() {
		return aftersaleStatus;
	}

	/**
	 * {@linkplain #aftersaleStatus}
	 * 
	 * @param aftersaleStatus
	 *            the value for aftersale_apply_item.aftersale_status
	 */
	public void setAftersaleStatus(String aftersaleStatus) {
		this.aftersaleStatus = aftersaleStatus == null ? null : aftersaleStatus.trim();
	}

	/**
	 * 
	 * {@linkplain #applyId}
	 * 
	 * @return the value of aftersale_apply_item.apply_id
	 */
	public String getApplyId() {
		return applyId;
	}

	/**
	 * {@linkplain #applyId}
	 * 
	 * @param applyId
	 *            the value for aftersale_apply_item.apply_id
	 */
	public void setApplyId(String applyId) {
		this.applyId = applyId == null ? null : applyId.trim();
	}

	/**
	 * 
	 * {@linkplain #applyNo}
	 * 
	 * @return the value of aftersale_apply_item.apply_no
	 */
	public String getApplyNo() {
		return applyNo;
	}

	/**
	 * {@linkplain #applyNo}
	 * 
	 * @param applyNo
	 *            the value for aftersale_apply_item.apply_no
	 */
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo == null ? null : applyNo.trim();
	}

	/**
	 * 雷----2016年9月14日
	 * 
	 * @return mainCommodityId
	 */
	public String getMainCommodityId() {
		return mainCommodityId;
	}

	/**
	 * 雷-------2016年9月14日
	 * 
	 * @param mainCommodityId
	 *            要设置的 mainCommodityId
	 */
	public void setMainCommodityId(String mainCommodityId) {
		this.mainCommodityId = mainCommodityId;
	}

	/**
	 * 雷----2016年9月14日
	 * 
	 * @return buyGifts 【买赠】明细标示，1：【买赠】主商品明细，2：【买赠】赠品明细
	 */
	public String getBuyGifts() {
		return buyGifts;
	}

	/**
	 * 雷-------2016年9月14日
	 * 
	 * @param buyGifts
	 *            要设置的 buyGifts 【买赠】明细标示，1：【买赠】主商品明细，2：【买赠】赠品明细
	 */
	public void setBuyGifts(String buyGifts) {
		this.buyGifts = buyGifts;
	}

	/**
	 * 雷----2016年11月23日
	 * 
	 * @return redPacketAmount
	 */
	public BigDecimal getRedPacketAmount() {
		return redPacketAmount;
	}

	/**
	 * 雷-------2016年11月23日
	 * 
	 * @param redPacketAmount
	 *            要设置的 redPacketAmount
	 */
	public void setRedPacketAmount(BigDecimal redPacketAmount) {
		this.redPacketAmount = redPacketAmount;
	}

}
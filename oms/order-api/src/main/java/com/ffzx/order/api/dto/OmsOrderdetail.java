package com.ffzx.order.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.dto.Commodity;
import com.ffzx.order.api.dto.CommoditySku;
import com.ffzx.order.api.enums.BuyTypeEnum;

/**
 * 
 * @author ffzx
 * @date 2016-05-10 14:27:13
 * @version 1.0.0
 * @copyright www.ffzxnet.com edit: 2016-5-11 ying.cai 实现Cloneable接口
 */
public class OmsOrderdetail extends DataEntity<OmsOrderdetail> implements Cloneable {

	private static final long serialVersionUID = 1L;

	/**
	 * 关联订单ID.
	 */
	private String orderId;

	/**
	 * 关联订单编号.
	 */
	private String orderNo;

	/**
	 * 商品编号 code.
	 */
	private String commodityNo;

	/**
	 * skuId.
	 */
	private String skuId;

	/**
	 * skuCode.
	 */
	private String skuCode;
	/**
	 * 商品标题.
	 */
	private String commodityTitle;

	/**
	 * 商品图片路径.
	 */
	private String commodityImage;

	/**
	 * sku商品条形码 ，订单明细修改条形码设置为sku条形码 modify by hyl 2016.8.8.
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
	 * 商品单价.
	 */
	private BigDecimal commodityPrice;
	/**
	 * 实际售价.
	 */
	private BigDecimal actSalePrice;

	/**
	 * 批发售价.
	 */
	private BigDecimal pifaPrice;
	
	/**
	 * 明细差额.
	 * 实际销售价-批发价
	 * balance=(actSalePrice-prfaPrice)*buyNum
	 */
	private BigDecimal balance;
	/**
	 * 优惠金额.
	 */
	private BigDecimal favouredAmount;

	/**
	 * 实际支付金额.
	 */
	private BigDecimal actPayAmount;

	/**
	 * 购买数量.
	 */
	private Integer buyNum;

	/**
	 * 发货日期.
	 */
	private Date readySendTime;

	/**
	 * 关联活动详情ID.
	 */
	private String activityCommodityItemId;
	/**
	 * 买赠商品详情id
	 */
	private String giftCommodityItemId;
	/**
	 * 关联活动ID.
	 */
	private String activityId;

	/**
	 * 下单会员ID.
	 */
	private String memberId;

	/**
	 * 标示是否有效(1:有效,0:无效).
	 */
	private String isEffective;

	/*****
	 * 订单详情商品状态 0：正常，1：退款处理中，2：换货处理中，3：退款成功，4：换货成功
	 */
	private String orderDetailStatus;

	/**
	 * sku属性组合.
	 */
	private String commodityAttributeValues;
	/**
	 * 配送仓库Id
	 */
	private String warehouseId;

	/**
	 * 配送仓库编码
	 */
	private String warehouseCode;
	/**
	 * 配送仓库Id
	 */
	private String warehouseName;

	/**
	 * 冗余字段 不做持久化处理 商品sku 实体.
	 */
	private CommoditySku commoditySku;

	/**
	 * 冗余字段 不做持久化处理 商品 实体.
	 */
	private Commodity commodity;

	/**
	 * 冗余字段 不做持久化处理 订单 实体.
	 */
	private OmsOrder order;

	/**
	 * 冗余字段 购买类型 下单时使用 .
	 */
	private BuyTypeEnum buyType;

	/**
	 * 推送采购消息（1：推送 0：未推送）
	 */
	private String pushRemind;

	/***
	 * 冗余字段 不做持久化处理 同一批发商品下sku购买总量 当购买类型为批发【
	 * WHOLESALE_MANAGER】时，由客户端传此参数获取某商品购买sku数量的总和 在计算商品批发价格时候使用
	 */
	private int wholeSaleCount;

	/** 冗余字段，值如：颜色,尺码,配置 ying.cai */
	private String skuNames;

	/**
	 * 冗余字段，值如：红色,中码,低配 ； 配合这两个字段，最终就可以构建如这样的显示数据 ： 颜色:红色 尺码:中码 配置:低配 ying.cai
	 */
	private String skuValues;

	/**
	 * 冗余字段 标示：若果是买赠关系，若是主品设置为主品标示，若是赠品则设置为关联的主品标示
	 */
	private String label;

	/**
	 * 冗余字段 赠送商品分组明细
	 */
	private List<OmsOrderdetail> giftList;
	/**
	 * 若此条明细是买赠 赠品明细，则该字段存储主商品明细id
	 */
	private String orderdetailId;

	/**
	 * 满赠条件数量 ，便于售后处理
	 */
	private Integer triggerNum;

	/**
	 * 赠品单次增量，便于售后处理
	 */
	private Integer singleGiftNum;

	/**
	 * 若此条明细是买赠 主商品明细，存储赠品数量，便于售后处理 【废弃】
	 */
	private Integer giftNum;

	/**
	 * 是否指定仓库，0：收货地址对应的中央仓，1：收货地址对应的县仓
	 */
	private String warehouseAppoint;

	/**
	 * 【买赠】明细标示，1：【买赠】主商品明细，2：【买赠】赠品明细，空标示普通明细
	 */
	private String buyGifts;

	/**
	 * 促销方式：1：【买赠】
	 */
	private String promotions;

	/**
	 * 红包ids：多个时用;隔开
	 */
	private String redPacketIds;
	/**
	 * 红包使用ids：多个时用;隔开
	 */
	private String redPacketUseIds;
	/**
	 * 红包名称：多个时用;隔开
	 */
	private String redPacketNames;
	/**
	 * 红包金额：多个时叠加
	 */
	private BigDecimal redPacketAmount;
	/**
	 * 1:虚拟商品
	 */
	private String commodityType; 
	/**
	 * 虚拟商品关联id
	 */
	private String virtualGdId;
	/**
	 * 虚拟商品关联code
	 */
	private String virtualGdCode;
	/**
	 * 虚拟商品关联name
	 */
	private String virtualGdName;
	/**
	 * 用户于售后计算优惠金额，红包金额的，辅助属性
	 */
	private Integer return_num;

	private  String settlementFlag;
	public OmsOrder getOrder() {
		return order;
	}

	public void setOrder(OmsOrder order) {
		this.order = order;
	}

	public String getOrderDetailStatus() {
		return orderDetailStatus;
	}

	public void setOrderDetailStatus(String orderDetailStatus) {
		this.orderDetailStatus = orderDetailStatus;
	}

	public BigDecimal getActSalePrice() {
		return actSalePrice;
	}

	public void setActSalePrice(BigDecimal actSalePrice) {
		this.actSalePrice = actSalePrice;
	}

	/**
	 * 
	 * {@linkplain #orderId}
	 * 
	 * @return the value of oms_orderdetail.order_id
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * {@linkplain #orderId}
	 * 
	 * @param orderId
	 *            the value for oms_orderdetail.order_id
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	/**
	 * 
	 * {@linkplain #orderNo}
	 * 
	 * @return the value of oms_orderdetail.order_no
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * {@linkplain #orderNo}
	 * 
	 * @param orderNo
	 *            the value for oms_orderdetail.order_no
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	/**
	 * 
	 * {@linkplain #CommodityNo}
	 * 
	 * @return the value of oms_orderdetail.Commodity_no
	 */

	/**
	 * 
	 * {@linkplain #actPayAmount}
	 * 
	 * @return the value of oms_orderdetail.act_pay_amount
	 */
	public BigDecimal getActPayAmount() {
		return actPayAmount;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public String getCommodityTitle() {
		return commodityTitle;
	}

	public void setCommodityTitle(String commodityTitle) {
		this.commodityTitle = commodityTitle;
	}

	public String getCommodityImage() {
		return commodityImage;
	}

	public void setCommodityImage(String commodityImage) {
		this.commodityImage = commodityImage;
	}

	public String getCommodityBarcode() {
		return commodityBarcode;
	}

	public void setCommodityBarcode(String commodityBarcode) {
		this.commodityBarcode = commodityBarcode;
	}

	public String getCommoditySpecifications() {
		return commoditySpecifications;
	}

	public void setCommoditySpecifications(String commoditySpecifications) {
		this.commoditySpecifications = commoditySpecifications;
	}

	public String getCommodityUnit() {
		return commodityUnit;
	}

	public void setCommodityUnit(String commodityUnit) {
		this.commodityUnit = commodityUnit;
	}

	public BigDecimal getCommodityPrice() {
		return commodityPrice;
	}

	public void setCommodityPrice(BigDecimal commodityPrice) {
		this.commodityPrice = commodityPrice;
	}

	/**
	 * {@linkplain #actPayAmount}
	 * 
	 * @param actPayAmount
	 *            the value for oms_orderdetail.act_pay_amount
	 */
	public void setActPayAmount(BigDecimal actPayAmount) {
		this.actPayAmount = actPayAmount;
	}

	/**
	 * 
	 * {@linkplain #buyNum}
	 * 
	 * @return the value of oms_orderdetail.buy_num
	 */
	public Integer getBuyNum() {
		return buyNum;
	}

	/**
	 * {@linkplain #buyNum}
	 * 
	 * @param buyNum
	 *            the value for oms_orderdetail.buy_num
	 */
	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	/**
	 * 
	 * {@linkplain #readySendTime}
	 * 
	 * @return the value of oms_orderdetail.ready_send_time
	 */
	public Date getReadySendTime() {
		return readySendTime;
	}

	/**
	 * {@linkplain #readySendTime}
	 * 
	 * @param readySendTime
	 *            the value for oms_orderdetail.ready_send_time
	 */
	public void setReadySendTime(Date readySendTime) {
		this.readySendTime = readySendTime;
	}

	public String getActivityCommodityItemId() {
		return activityCommodityItemId;
	}

	public void setActivityCommodityItemId(String activityCommodityItemId) {
		this.activityCommodityItemId = activityCommodityItemId;
	}

	/**
	 * 
	 * {@linkplain #memberId}
	 * 
	 * @return the value of oms_orderdetail.member_id
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * {@linkplain #memberId}
	 * 
	 * @param memberId
	 *            the value for oms_orderdetail.member_id
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId == null ? null : memberId.trim();
	}

	/**
	 * 
	 * {@linkplain #isEffective}
	 * 
	 * @return the value of oms_orderdetail.is_effective
	 */
	public String getIsEffective() {
		return isEffective;
	}

	/**
	 * {@linkplain #isEffective}
	 * 
	 * @param isEffective
	 *            the value for oms_orderdetail.is_effective
	 */
	public void setIsEffective(String isEffective) {
		this.isEffective = isEffective == null ? null : isEffective.trim();
	}

	public String getCommodityAttributeValues() {
		return commodityAttributeValues;
	}

	public void setCommodityAttributeValues(String commodityAttributeValues) {
		this.commodityAttributeValues = commodityAttributeValues;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public CommoditySku getCommoditySku() {
		return commoditySku;
	}

	public void setCommoditySku(CommoditySku commoditySku) {
		this.commoditySku = commoditySku;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	public BuyTypeEnum getBuyType() {
		return buyType;
	}

	public void setBuyType(BuyTypeEnum buyType) {
		this.buyType = buyType;
	}

	public String getBuyTypeName() {
		if (StringUtil.isNotNull(this.getBuyType())) {
			return this.getBuyType().getName();
		} else {
			return null;
		}
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public BigDecimal getFavouredAmount() {
		return favouredAmount;
	}

	public void setFavouredAmount(BigDecimal favouredAmount) {
		this.favouredAmount = favouredAmount;
	}

	public int getWholeSaleCount() {
		return wholeSaleCount;
	}

	public void setWholeSaleCount(int wholeSaleCount) {
		this.wholeSaleCount = wholeSaleCount;
	}

	public String getPushRemind() {
		return pushRemind;
	}

	public void setPushRemind(String pushRemind) {
		this.pushRemind = pushRemind;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
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

	public String getSkuNames() {
		return skuNames;
	}

	public void setSkuNames(String skuNames) {
		this.skuNames = skuNames;
	}

	public String getSkuValues() {
		return skuValues;
	}

	public void setSkuValues(String skuValues) {
		this.skuValues = skuValues;
	}

	/**
	 * @return the orderdetailId
	 */
	public String getOrderdetailId() {
		return orderdetailId;
	}

	/**
	 * @param orderdetailId
	 *            the orderdetailId to set
	 */
	public void setOrderdetailId(String orderdetailId) {
		this.orderdetailId = orderdetailId;
	}

	/**
	 * @return the giftNum
	 */
	public Integer getGiftNum() {
		return giftNum;
	}

	/**
	 * @param giftNum
	 *            the giftNum to set
	 */
	public void setGiftNum(Integer giftNum) {
		this.giftNum = giftNum;
	}

	/**
	 * @return the warehouseAppoint
	 */
	public String getWarehouseAppoint() {
		return warehouseAppoint;
	}

	/**
	 * @param warehouseAppoint
	 *            the warehouseAppoint to set
	 */
	public void setWarehouseAppoint(String warehouseAppoint) {
		this.warehouseAppoint = warehouseAppoint;
	}

	/**
	 * @return the buyGifts
	 */
	public String getBuyGifts() {
		return buyGifts;
	}

	/**
	 * @param buyGifts
	 *            the buyGifts to set
	 */
	public void setBuyGifts(String buyGifts) {
		this.buyGifts = buyGifts;
	}

	/**
	 * @return the triggerNum
	 */
	public Integer getTriggerNum() {
		return triggerNum;
	}

	/**
	 * @param triggerNum
	 *            the triggerNum to set
	 */
	public void setTriggerNum(Integer triggerNum) {
		this.triggerNum = triggerNum;
	}

	/**
	 * @return the promotions
	 */
	public String getPromotions() {
		return promotions;
	}

	/**
	 * @param promotions
	 *            the promotions to set
	 */
	public void setPromotions(String promotions) {
		this.promotions = promotions;
	}

	/**
	 * @return the singleGiftNum
	 */
	public Integer getSingleGiftNum() {
		return singleGiftNum;
	}

	/**
	 * @param singleGiftNum
	 *            the singleGiftNum to set
	 */
	public void setSingleGiftNum(Integer singleGiftNum) {
		this.singleGiftNum = singleGiftNum;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the giftList
	 */
	public List<OmsOrderdetail> getGiftList() {
		return giftList;
	}

	/**
	 * @param giftList
	 *            the giftList to set
	 */
	public void setGiftList(List<OmsOrderdetail> giftList) {
		this.giftList = giftList;
	}

	/**
	 * @return the giftCommodityItemId
	 */
	public String getGiftCommodityItemId() {
		return giftCommodityItemId;
	}

	/**
	 * @param giftCommodityItemId
	 *            the giftCommodityItemId to set
	 */
	public void setGiftCommodityItemId(String giftCommodityItemId) {
		this.giftCommodityItemId = giftCommodityItemId;
	}

	/**
	 * @return the redPacketIds
	 */
	public String getRedPacketIds() {
		return redPacketIds;
	}

	/**
	 * @param redPacketIds
	 *            the redPacketIds to set
	 */
	public void setRedPacketIds(String redPacketIds) {
		this.redPacketIds = redPacketIds;
	}

	/**
	 * @return the redPacketUseIds
	 */
	public String getRedPacketUseIds() {
		return redPacketUseIds;
	}

	/**
	 * @param redPacketUseIds
	 *            the redPacketUseIds to set
	 */
	public void setRedPacketUseIds(String redPacketUseIds) {
		this.redPacketUseIds = redPacketUseIds;
	}

	/**
	 * @return the redPacketNames
	 */
	public String getRedPacketNames() {
		return redPacketNames;
	}

	/**
	 * @param redPacketNames
	 *            the redPacketNames to set
	 */
	public void setRedPacketNames(String redPacketNames) {
		this.redPacketNames = redPacketNames;
	}

	/**
	 * @return the redPacketAmount
	 */
	public BigDecimal getRedPacketAmount() {
		return redPacketAmount;
	}

	/**
	 * @param redPacketAmount
	 *            the redPacketAmount to set
	 */
	public void setRedPacketAmount(BigDecimal redPacketAmount) {
		this.redPacketAmount = redPacketAmount;
	}

	/**
	 * @return the virtualGdId
	 */
	public String getVirtualGdId() {
		return virtualGdId;
	}

	/**
	 * @param virtualGdId
	 *            the virtualGdId to set
	 */
	public void setVirtualGdId(String virtualGdId) {
		this.virtualGdId = virtualGdId;
	}

	/**
	 * @return the virtualGdCode
	 */
	public String getVirtualGdCode() {
		return virtualGdCode;
	}

	/**
	 * @param virtualGdCode
	 *            the virtualGdCode to set
	 */
	public void setVirtualGdCode(String virtualGdCode) {
		this.virtualGdCode = virtualGdCode;
	}

	/**
	 * @return the virtualGdName
	 */
	public String getVirtualGdName() {
		return virtualGdName;
	}

	/**
	 * @param virtualGdName
	 *            the virtualGdName to set
	 */
	public void setVirtualGdName(String virtualGdName) {
		this.virtualGdName = virtualGdName;
	}

	/**
	 * 雷----2016年12月6日
	 * 
	 * @return return_num
	 */
	public Integer getReturn_num() {
		return return_num;
	}

	/**
	 * 雷-------2016年12月6日
	 * 
	 * @param return_num
	 *            要设置的 return_num
	 */
	public void setReturn_num(Integer return_num) {
		this.return_num = return_num;
	}

	/**
	 * @return the commodityType
	 */
	public String getCommodityType() {
		return commodityType;
	}

	/**
	 * @param commodityType the commodityType to set
	 */
	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
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
	
}
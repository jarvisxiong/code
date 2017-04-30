package com.ffzx.promotion.model;

/**
 * @Description: 抢购活动列表 App用
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年8月22日 上午11:17:22
 * @version V1.0 
 *
 */
public class ActivitySaleApp{
	/**
	 * 商品名称
	 */
	private String goodsId;
	
	/**
	 * 活动商品id activityCommodityId
	 */
	private String activityId;
	/**
	 * 活动商品图片
	 */
	private String activityImgPath;
	/**
	 * 原价=零售价
	 */
	private String price;
	/**
	 * 最低价格
	 */
	private String discountPrice;

	/**
	 * 是否以发送提醒
	 */
	private String isRemind;
	/**
	 * 已提醒/关注人数
	 */
	private String remindCount;
	/**
     * 商品计量单位名称.
     */
	private String unit;
	/**
	 * 活动开始时间
	 */
	private String activityTime;
	/**
	 * 活动结束时间
	 */
	private String activityEndTime;
	/**
	 * id限购量
	 */
	private String idLimit;
	/**
	 * 总限购量，有加增量值
	 */
	private String limit;
	/**
	 * 用户购买数量+增量值
	 */
	private String presaleCount;
	/**
	 * 是否存在辅助属性,1是
	 */
	private String existExtra;
	/**
	 * 如果existExtra为1,那么priceAmong肯定存在(前提,不能在一个商品已经处于预售抢购活动中的时候,还去设置这个商品的辅助属性!!!),
	 * 商品优惠价
	 */
	private String priceAmong;
	/**
	 * 是否可购买
	 */
	private String isAllowFlag;
	/**
	 * 活动标题
	 */
	private String activityTitle;
	/**
	 * 是否开抢
	 */
	private String isPANICBUY;
	
	/**
	 * 增量值，缓存查数据继续返回
	 * @return
	 */
	private String saleIncrease;
	/**
	 * 商品编码
	 * @return
	 */
	private String commodityNo;
	/**
	 * 类目id
	 */
	private String categoryId;
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 活动类型type
	 * @return
	 */
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCommodityNo() {
		return commodityNo;
	}
	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}
	public String getSaleIncrease() {
		return saleIncrease;
	}
	public void setSaleIncrease(String saleIncrease) {
		this.saleIncrease = saleIncrease;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getActivityImgPath() {
		return activityImgPath;
	}
	public void setActivityImgPath(String activityImgPath) {
		this.activityImgPath = activityImgPath;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}
	public String getIsRemind() {
		return isRemind;
	}
	public void setIsRemind(String isRemind) {
		this.isRemind = isRemind;
	}
	public String getRemindCount() {
		return remindCount;
	}
	public void setRemindCount(String remindCount) {
		this.remindCount = remindCount;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getActivityTime() {
		return activityTime;
	}
	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}
	public String getActivityEndTime() {
		return activityEndTime;
	}
	public void setActivityEndTime(String activityEndTime) {
		this.activityEndTime = activityEndTime;
	}
	public String getIdLimit() {
		return idLimit;
	}
	public void setIdLimit(String idLimit) {
		this.idLimit = idLimit;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getPresaleCount() {
		return presaleCount;
	}
	public void setPresaleCount(String presaleCount) {
		this.presaleCount = presaleCount;
	}
	public String getExistExtra() {
		return existExtra;
	}
	public void setExistExtra(String existExtra) {
		this.existExtra = existExtra;
	}
	public String getPriceAmong() {
		return priceAmong;
	}
	public void setPriceAmong(String priceAmong) {
		this.priceAmong = priceAmong;
	}
	public String getIsAllowFlag() {
		return isAllowFlag;
	}
	public void setIsAllowFlag(String isAllowFlag) {
		this.isAllowFlag = isAllowFlag;
	}
	public String getActivityTitle() {
		return activityTitle;
	}
	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}
	public String getIsPANICBUY() {
		return isPANICBUY;
	}
	public void setIsPANICBUY(String isPANICBUY) {
		this.isPANICBUY = isPANICBUY;
	}
	
}

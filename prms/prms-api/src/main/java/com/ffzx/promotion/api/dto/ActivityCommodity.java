package com.ffzx.promotion.api.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.ffzx.commerce.framework.common.persistence.BaseEntity;
import com.ffzx.promotion.api.dto.constant.Constant;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;

/**
 * 
 * @Description: 活动商品管理
 * @author yuzhao.xu
 * @email yuzhao.xu@ffzxnet.com
 * @date 2016年5月3日 下午6:15:37
 * @version V1.0
 * 
 */
public class ActivityCommodity extends BaseEntity<ActivityCommodity> {

	private static final long serialVersionUID = 1L;

	public ActivityCommodity() {

	}

	public ActivityCommodity(String id) {
		this.id = id;
	}

	/**
	 * 关联活动编号.
	 */
	private String activityNo;

	//
	private String activityStatus;

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	/**
	 * 图片路径.
	 */
	private String picPath;

	/**
	 * 活动类型(抢购，预售，普通，新用户，批发）.
	 */
	private ActivityTypeEnum activityType;

	/**
	 * 商品编号.
	 */
	private String commodityNo;

	/**
	 * 关联商品ID.
	 */
	private String commodityId;

	/**
	 * 活动标题.
	 */
	private String activityTitle;

	/**
	 * 商品条形码.
	 */
	private String commodityBarcode;

	/**
	 * 活动价格显示(包含区间价格).
	 */
	private String showPrice;

	/**
	 * 开始时间.
	 */
	private Date startDate;

	/**
	 * 结束时间.
	 */
	private Date endDate;

	/**
	 * 置顶序号.
	 */
	private Integer sortTopNo;

	/**
	 * 排序号.
	 */
	private Integer sortNo;

	/**
	 * 是否推荐商品 1:是,0:不是.
	 */
	private String isRecommend;

	/**
	 * 推荐商品排序.
	 */
	private Integer recommendSort;

	/**
	 * 单个用户限购数量.
	 */
	private Integer idLimitCount;

	/**
	 * 总限购数量.
	 */
	private Integer limitCount;

	/**
	 * 销量增量值.
	 */
	private Integer saleIncrease;

	/**
	 * 是否启用特殊数量值 1:启用,0:不启用.
	 */
	private String enableSpecialCount;

	/**
	 * 特殊数量值.
	 */
	private Integer specialCount;

	/**
	 * 活动ID.
	 */
	private ActivityManager activity;

	/**
	 * 发货时间.
	 */
	private Date deliverDate;
	/**
	 * APP是否显示限定数量，0否，1是.
	 */
	private String islimit;

	/**
	 * 购买数量选择-批发使用
	 */
	private String buyCount;

	/**
	 * 是否显示最新下单的用户.
	 */
	private String isNeworder;
	/**
	 * 关注人数.
	 */
	private Integer followCount;

	/**
	 * 是否以发送提醒
	 */
	private String isRemind;
	/**
	 * 是否抢完 (0未抢完 3抢完)-2016-6-11新增
	 */
	private String activityCommodityStatus;

    /**
     * 最大价格.
     */
    private BigDecimal maxPrice;

    /**
     * 最小价格.
     */
    private BigDecimal minPrice;
    
    /*
     * 预售标签ID
     */
    private ActivityPreSaleTag preSaleTag;

	// 冗余字段
	private String storageNum;
	
	//编辑换了商品的ID
	private String newCommodityId;

	/*
	 * 要更新限购量的时候 老限购量
	 */
	private String oldlimitCount;
	
	/*
	 * 是否启用特殊增量值
	 */
	private String enableSpecialCountStr;


	


	public String getEnableSpecialCountStr() {
		return enableSpecialCountStr;
	}

	public void setEnableSpecialCountStr(String enableSpecialCountStr) {
		this.enableSpecialCountStr = enableSpecialCountStr;
	}

	public ActivityPreSaleTag getPreSaleTag() {
		return preSaleTag;
	}

	public void setPreSaleTag(ActivityPreSaleTag preSaleTag) {
		this.preSaleTag = preSaleTag;
	}

	public String getOldlimitCount() {
		return oldlimitCount;
	}

	public void setOldlimitCount(String oldlimitCount) {
		this.oldlimitCount = oldlimitCount;
	}

	public String getNewCommodityId() {
		return newCommodityId;
	}

	public void setNewCommodityId(String newCommodityId) {
		this.newCommodityId = newCommodityId;
	}

	/**
	 * 雷-----2016年6月23日
	 * @return the maxPrice
	 */
	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	/**
	 * 雷-------2016年6月23日
	 * @param maxPrice the maxPrice to set
	 */
	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public String getStorageNum() {
		return storageNum;
	}

	public void setStorageNum(String storageNum) {
		this.storageNum = storageNum;
	}

	public String getIsRemind() {
		return isRemind;
	}

	public void setIsRemind(String isRemind) {
		this.isRemind = isRemind;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public String getIsNeworder() {
		return isNeworder;
	}

	public void setIsNeworder(String isNeworder) {
		this.isNeworder = isNeworder;
	}

	public String getIslimit() {
		return islimit;
	}

	public void setIslimit(String islimit) {
		this.islimit = islimit;
	}

	public String getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(String buyCount) {
		this.buyCount = buyCount;
	}

	// 冗余
	/****
	 * 商品活动状态
	 */
	private String commodityStatus;

	/****
	 * 商品活动状态(返回字符串) 活动商品状态(0 即将开始 1 进行中 2 已结束)
	 */
	private String commodityStatusStr;

	public String getCommodityStatusStr() {
		return commodityStatusStr;
	}

	public void setCommodityStatusStr(String commodityStatusStr) {
		this.commodityStatusStr = commodityStatusStr;
	}

	// 开始时间字符串
	private String startDateStr;
	// 结束时间字符串
	private String endDateStr;
	// 发货时间字符串
	private String deliverDateStr;
	// 商品sku
	private String activityitemDate;
	private String activityId;
	// 商品对象
	private Commodity commodity;
	
	// 冗余字段(skuId)
	private String skuId;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityitemDate() {
		return activityitemDate;
	}

	public void setActivityitemDate(String activityitemDate) {
		this.activityitemDate = activityitemDate;
	}

	public String getDeliverDateStr() {
		return deliverDateStr;
	}

	public void setDeliverDateStr(String deliverDateStr) {
		this.deliverDateStr = deliverDateStr;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getCommodityStatus() {

		if (this.startDate != null && this.endDate != null) {
			if (Constant.STATUS_EMPTY.equals(this.activityCommodityStatus) && Long.valueOf(DateUtil.getTime()) < this.endDate.getTime()) {
				 commodityStatus = "3";// 已抢完
			}else if (Long.valueOf(DateUtil.getTime()) > this.endDate.getTime()) {
				 commodityStatus = Constant.STATUS_OVERDUR;// 已结束
			}  else if (!"3".equals(this.activityCommodityStatus) && Long.valueOf(DateUtil.getTime()) > this.startDate.getTime() && Long.valueOf(DateUtil.getTime()) < this.endDate.getTime()) {
				commodityStatus = Constant.STATUS_EFFECT;// 进行中
			} else if (!"3".equals(this.activityCommodityStatus) && Long.valueOf(DateUtil.getTime()) < this.startDate.getTime()) {
				commodityStatus = Constant.STATUS_UNEFFECT;// 即将开始
			} else {
				commodityStatus = "-1";// 全部
			}

			if (commodityStatus == "0") {
				commodityStatusStr = "即将开始";
			} else if (commodityStatus == "1") {
				commodityStatusStr = "进行中";
			} else if (commodityStatus == "2") {
				commodityStatusStr = "已结束";
			}else if (commodityStatus == "3") {
				commodityStatusStr = "已抢完";
			} else {
				commodityStatusStr = "";
			}
		}
		return commodityStatus;
	}

	public void setCommodityStatus(String commodityStatus) {
		this.commodityStatus = commodityStatus;
	}

	/**
	 * 
	 * {@linkplain #activityNo}
	 * 
	 * @return the value of activity_commdity.activity_no
	 */
	public String getActivityNo() {
		return activityNo;
	}

	/**
	 * {@linkplain #activityNo}
	 * 
	 * @param activityNo
	 *            the value for activity_commdity.activity_no
	 */
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo == null ? null : activityNo.trim();
	}

	/**
	 * 
	 * {@linkplain #picPath}
	 * 
	 * @return the value of activity_commdity.pic_path
	 */
	public String getPicPath() {
		return picPath;
	}

	/**
	 * {@linkplain #picPath}
	 * 
	 * @param picPath
	 *            the value for activity_commdity.pic_path
	 */
	public void setPicPath(String picPath) {
		this.picPath = picPath == null ? null : picPath.trim();
	}

	public ActivityTypeEnum getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityTypeEnum activityType) {
		this.activityType = activityType;
	}

	/**
	 * 
	 * {@linkplain #commodityNo}
	 * 
	 * @return the value of activity_commodity.commodity_no
	 */
	public String getCommodityNo() {
		return commodityNo;
	}

	/**
	 * {@linkplain #commodityNo}
	 * 
	 * @param commodityNo
	 *            the value for activity_commodity.commodity_no
	 */
	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo == null ? null : commodityNo.trim();
	}

	/**
	 * 
	 * {@linkplain #commodityId}
	 * 
	 * @return the value of activity_commodity.commodity_id
	 */
	public String getCommodityId() {
		return commodityId;
	}

	/**
	 * {@linkplain #commodityId}
	 * 
	 * @param commodityId
	 *            the value for activity_commodity.commodity_id
	 */
	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId == null ? null : commodityId.trim();
	}

	/**
	 * 
	 * {@linkplain #activityTitle}
	 * 
	 * @return the value of activity_commdity.activity_title
	 */
	public String getActivityTitle() {
		return activityTitle;
	}

	/**
	 * {@linkplain #activityTitle}
	 * 
	 * @param activityTitle
	 *            the value for activity_commdity.activity_title
	 */
	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle == null ? null : activityTitle.trim();
	}

	/**
	 * 
	 * {@linkplain #commodityBarcode}
	 * 
	 * @return the value of activity_commdity.commdity_barcode
	 */
	public String getCommodityBarcode() {
		return commodityBarcode;
	}

	/**
	 * {@linkplain #commodityBarcode}
	 * 
	 * @param commdityBarcode
	 *            the value for activity_commdity.commdity_barcode
	 */
	public void setCommodityBarcode(String commodityBarcode) {
		this.commodityBarcode = commodityBarcode == null ? null : commodityBarcode.trim();
	}

	/**
	 * 
	 * {@linkplain #showPrice}
	 * 
	 * @return the value of activity_commdity.show_price
	 */
	public String getShowPrice() {
		return showPrice;
	}

	/**
	 * {@linkplain #showPrice}
	 * 
	 * @param showPrice
	 *            the value for activity_commdity.show_price
	 */
	public void setShowPrice(String showPrice) {
		this.showPrice = showPrice == null ? null : showPrice.trim();
	}

	/**
	 * 
	 * {@linkplain #startDate}
	 * 
	 * @return the value of activity_commdity.start_date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * {@linkplain #startDate}
	 * 
	 * @param startDate
	 *            the value for activity_commdity.start_date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 
	 * {@linkplain #endDate}
	 * 
	 * @return the value of activity_commdity.end_date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * {@linkplain #endDate}
	 * 
	 * @param endDate
	 *            the value for activity_commdity.end_date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 
	 * {@linkplain #sortTopNo}
	 * 
	 * @return the value of activity_commdity.sort_top_no
	 */
	public Integer getSortTopNo() {
		return sortTopNo;
	}

	/**
	 * {@linkplain #sortTopNo}
	 * 
	 * @param sortTopNo
	 *            the value for activity_commdity.sort_top_no
	 */
	public void setSortTopNo(Integer sortTopNo) {
		this.sortTopNo = sortTopNo;
	}

	/**
	 * 
	 * {@linkplain #sortNo}
	 * 
	 * @return the value of activity_commdity.sort_no
	 */
	public Integer getSortNo() {
		return sortNo;
	}

	/**
	 * {@linkplain #sortNo}
	 * 
	 * @param sortNo
	 *            the value for activity_commdity.sort_no
	 */
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	/**
	 * 
	 * {@linkplain #isRecommend}
	 * 
	 * @return the value of activity_commdity.is_recommend
	 */
	public String getIsRecommend() {
		return isRecommend;
	}

	/**
	 * {@linkplain #isRecommend}
	 * 
	 * @param isRecommend
	 *            the value for activity_commdity.is_recommend
	 */
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend == null ? null : isRecommend.trim();
	}

	/**
	 * 
	 * {@linkplain #recommendSort}
	 * 
	 * @return the value of activity_commdity.recommend_sort
	 */
	public Integer getRecommendSort() {
		return recommendSort;
	}

	/**
	 * {@linkplain #recommendSort}
	 * 
	 * @param recommendSort
	 *            the value for activity_commdity.recommend_sort
	 */
	public void setRecommendSort(Integer recommendSort) {
		this.recommendSort = recommendSort;
	}

	/**
	 * 
	 * {@linkplain #limitCount}
	 * 
	 * @return the value of activity_commdity.limit_count
	 */
	public Integer getIdLimitCount() {
		return idLimitCount;
	}

	/**
	 * {@linkplain #limitCount}
	 * 
	 * @param limitCount
	 *            the value for activity_commdity.limit_count
	 */
	public void setIdLimitCount(Integer idLimitCount) {
		this.idLimitCount = idLimitCount;
	}

	/**
	 * 
	 * {@linkplain #limitCount}
	 * 
	 * @return the value of activity_commdity.limit_count
	 */
	public Integer getLimitCount() {
		return limitCount;
	}

	/**
	 * {@linkplain #limitCount}
	 * 
	 * @param limitCount
	 *            the value for activity_commdity.limit_count
	 */
	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}

	/**
	 * 
	 * {@linkplain #saleIncrease}
	 * 
	 * @return the value of activity_commdity.sale_increase
	 */
	public Integer getSaleIncrease() {
		return saleIncrease;
	}

	/**
	 * {@linkplain #saleIncrease}
	 * 
	 * @param saleIncrease
	 *            the value for activity_commdity.sale_increase
	 */
	public void setSaleIncrease(Integer saleIncrease) {
		this.saleIncrease = saleIncrease;
	}

	/**
	 * 
	 * {@linkplain #enableSpecialCount}
	 * 
	 * @return the value of activity_commdity.enable_special_count
	 */
	public String getEnableSpecialCount() {
		return enableSpecialCount;
	}

	/**
	 * {@linkplain #enableSpecialCount}
	 * 
	 * @param enableSpecialCount
	 *            the value for activity_commdity.enable_special_count
	 */
	public void setEnableSpecialCount(String enableSpecialCount) {
		this.enableSpecialCount = enableSpecialCount == null ? null : enableSpecialCount.trim();
	}

	/**
	 * 
	 * {@linkplain #specialCount}
	 * 
	 * @return the value of activity_commdity.special_count
	 */
	public Integer getSpecialCount() {
		return specialCount;
	}

	/**
	 * {@linkplain #specialCount}
	 * 
	 * @param specialCount
	 *            the value for activity_commdity.special_count
	 */
	public void setSpecialCount(Integer specialCount) {
		this.specialCount = specialCount;
	}

	public ActivityManager getActivity() {
		return activity;
	}

	public void setActivity(ActivityManager activity) {
		this.activity = activity;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	/**
	 * 雷-----2016年6月11日
	 * 
	 * @return the activityCommodityStatus
	 */
	public String getActivityCommodityStatus() {
		return activityCommodityStatus;
	}

	/**
	 * 雷-------2016年6月11日
	 * 
	 * @param activityCommodityStatus
	 *            the activityCommodityStatus to set
	 */
	public void setActivityCommodityStatus(String activityCommodityStatus) {
		this.activityCommodityStatus = activityCommodityStatus;
	}

}
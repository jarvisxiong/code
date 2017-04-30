package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.BaseEntity;

/**
 * 
 * @ClassName: ActivityCommodityVo
 * @Description: 广告查询商品的详情
 * @author 雷
 * @date 2016年6月23日
 * 
 */
public class ActivityCommodityVo extends BaseEntity<ActivityCommodityVo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 雷-------2016年6月23日
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public ActivityCommodityVo() {
		super();
	}

	private String id;
	/**
	 * 商品标题
	 */
	private String commodityTitle;
	/**
	 * 商品条形码.
	 */
	private String commodityBarcode;

	/**
	 * 活动价格显示(包含区间价格).
	 */
	private String showPrice;
	/**
	 * 发布状态1:已发布,0:未发布.
	 */
	private String releaseStatus;
	/**
	 * 活动ID
	 */
	private String activityId;

	/**
	 * 雷-----2016年6月23日
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 雷-------2016年6月23日
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 雷-----2016年6月23日
	 * 
	 * @return the commodityTitle
	 */
	public String getCommodityTitle() {
		return commodityTitle;
	}

	/**
	 * 雷-------2016年6月23日
	 * 
	 * @param commodityTitle
	 *            the commodityTitle to set
	 */
	public void setCommodityTitle(String commodityTitle) {
		this.commodityTitle = commodityTitle;
	}

	/**
	 * 雷-----2016年6月23日
	 * 
	 * @return the commodityBarcode
	 */
	public String getCommodityBarcode() {
		return commodityBarcode;
	}

	/**
	 * 雷-------2016年6月23日
	 * 
	 * @param commodityBarcode
	 *            the commodityBarcode to set
	 */
	public void setCommodityBarcode(String commodityBarcode) {
		this.commodityBarcode = commodityBarcode;
	}

	/**
	 * 雷-----2016年6月23日
	 * 
	 * @return the showPrice
	 */
	public String getShowPrice() {
		return showPrice;
	}

	/**
	 * 雷-------2016年6月23日
	 * 
	 * @param showPrice
	 *            the showPrice to set
	 */
	public void setShowPrice(String showPrice) {
		this.showPrice = showPrice;
	}

	/**
	 * 雷-----2016年6月23日
	 * 
	 * @return the releaseStatus
	 */
	public String getReleaseStatus() {
		return releaseStatus;
	}

	/**
	 * 雷-------2016年6月23日
	 * 
	 * @param releaseStatus
	 *            the releaseStatus to set
	 */
	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	/**
	 * 雷-----2016年6月23日
	 * 
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
	}

	/**
	 * 雷-------2016年6月23日
	 * 
	 * @param activityId
	 *            the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

}
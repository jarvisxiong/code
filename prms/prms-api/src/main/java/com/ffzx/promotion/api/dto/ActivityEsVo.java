package com.ffzx.promotion.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/***
 * exchange:amq.activity.topic
 * queue:QUEUE_ELASTICSEARCH_COMMODITY_ADD
 * 搜索引擎dto(添加或修改活动信息)
 * @author ying.cai
 * @date 2016年9月9日 下午2:59:50
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public class ActivityEsVo implements Serializable{

	private static final long serialVersionUID = 6628619676631525702L;

	/**商品id*/
	private String goodsId;
	
	/**标题*/
	private String title1 ;
	
	/**图片路径*/
	private String imgPath; 
	
	/**商品优惠价*/
	private String discountPrice; 
	
	 /**
     * 最大价格.
     */
    private BigDecimal maxPrice;

    /**
     * 最小价格.
     */
    private BigDecimal minPrice;
	
	/**是否专享价格*/
	private Integer isVip; 
	
	/**商品类型*/
	private String type; 
	
	/**活动id*/
	private String activityId;
	
	/**活动开始时间*/
	private Date startDate;

	/**活动结束时间*/
	private Date endDate;
	
	private String releaseStatus ; //冗余字段，对应活动管理数据是否已启用  “1”为已启用
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDiscountPrice() {
		return discountPrice;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}


	
}

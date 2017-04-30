package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;

import java.math.BigDecimal;

/**
 * 
 * @Description: 活动商品管理sku
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月3日 下午6:15:37
 * @version V1.0 
 *
 */
public class ActivityCommoditySku extends DataEntity<ActivityCommoditySku> {

    private static final long serialVersionUID = 1L;

    /**
     * 关联活动编号.
     */
    private String activityNo;

    /**
     * 商品编号
     */
    private String commodityNo;
    
    /**
     * 活动价格.
     */
    private BigDecimal activityPrice;

    /**
     * 商品sku表ID.
     */
    private String commoditySkuId;

    /**
     * 商品编号.
     */
    private String commoditySkuNo;

    /**
     * 商品条形码.
     */
    private String commoditySkuBarcode;

    /**
     * 商品标题.
     */
    private String commoditySkuTitle;

    /**
     * 开始区间数(批发管理用).
     */
    private Integer selectionStart;

    /**
     * 结束区间数(批发管理用).
     */
    private Integer selectionEnd;

    /**
     * 属性组合(针对有属性商品).
     */
    private String attrGroup;

    /**
     * 活动类型(抢购，预售，普通，新用户，批发）.
     */
    private ActivityTypeEnum activityType;

    /**
     * 限购数量(预售抢购用).
     */
    private Integer limitCount;

    /**
     * 关联商品设置表ID.
     */
    private ActivityCommodity activityCommodity;

    /**
     * 关联活动ID.
     */
    private ActivityManager activity;
    /**
     * 优惠价.
     */
    private BigDecimal commoditySkuPrice;

    public BigDecimal getCommoditySkuPrice() {
		return commoditySkuPrice;
	}

	public void setCommoditySkuPrice(BigDecimal commoditySkuPrice) {
		this.commoditySkuPrice = commoditySkuPrice;
	}

	/**
     * 
     * {@linkplain #activityNo}
     *
     * @return the value of activity_commdity_item.activity_no
     */
    public String getActivityNo() {
        return activityNo;
    }

    /**
     * {@linkplain #activityNo}
     * @param activityNo the value for activity_commdity_item.activity_no
     */
    public void setActivityNo(String activityNo) {
        this.activityNo = activityNo == null ? null : activityNo.trim();
    }

    /**
     * 
     * {@linkplain #activityPrice}
     *
     * @return the value of activity_commdity_item.activity_price
     */
    public BigDecimal getActivityPrice() {
        return activityPrice;
    }

    /**
     * {@linkplain #activityPrice}
     * @param activityPrice the value for activity_commdity_item.activity_price
     */
    public void setActivityPrice(BigDecimal activityPrice) {
        this.activityPrice = activityPrice;
    }

    /**
     * 
     * {@linkplain #commdityId}
     *
     * @return the value of activity_commdity_item.commdity_id
     */
    public String getCommoditySkuId() {
        return commoditySkuId;
    }

    /**
     * {@linkplain #commdityId}
     * @param commdityId the value for activity_commdity_item.commdity_id
     */
    public void setCommoditySkuId(String commoditySkuId) {
        this.commoditySkuId = commoditySkuId == null ? null : commoditySkuId.trim();
    }

    /**
     * 
     * {@linkplain #commdityNo}
     *
     * @return the value of activity_commdity_item.commdity_no
     */
    public String getCommoditySkuNo() {
        return commoditySkuNo;
    }

    /**
     * {@linkplain #commdityNo}
     * @param commdityNo the value for activity_commdity_item.commdity_no
     */
    public void setCommoditySkuNo(String commoditySkuNo) {
        this.commoditySkuNo = commoditySkuNo == null ? null : commoditySkuNo.trim();
    }

    /**
     * 
     * {@linkplain #commdityBarcode}
     *
     * @return the value of activity_commdity_item.commdity_barcode
     */
    public String getCommoditySkuBarcode() {
        return commoditySkuBarcode;
    }

    /**
     * {@linkplain #commdityBarcode}
     * @param commdityBarcode the value for activity_commdity_item.commdity_barcode
     */
    public void setCommoditySkuBarcode(String commoditySkuBarcode) {
        this.commoditySkuBarcode = commoditySkuBarcode == null ? null : commoditySkuBarcode.trim();
    }

    /**
     * 
     * {@linkplain #commdityTitle}
     *
     * @return the value of activity_commdity_item.commdity_title
     */
    public String getCommoditySkuTitle() {
        return commoditySkuTitle;
    }

    /**
     * {@linkplain #commdityTitle}
     * @param commdityTitle the value for activity_commdity_item.commdity_title
     */
    public void setCommoditySkuTitle(String commoditySkuTitle) {
        this.commoditySkuTitle =  commoditySkuTitle == null ? null : commoditySkuTitle.trim();
    }

    /**
     * 
     * {@linkplain #selectionStart}
     *
     * @return the value of activity_commdity_item.selection_start
     */
    public Integer getSelectionStart() {
        return selectionStart;
    }

    /**
     * {@linkplain #selectionStart}
     * @param selectionStart the value for activity_commdity_item.selection_start
     */
    public void setSelectionStart(Integer selectionStart) {
        this.selectionStart = selectionStart;
    }

    /**
     * 
     * {@linkplain #selectionEnd}
     *
     * @return the value of activity_commdity_item.selection_end
     */
    public Integer getSelectionEnd() {
        return selectionEnd;
    }

    /**
     * {@linkplain #selectionEnd}
     * @param selectionEnd the value for activity_commdity_item.selection_end
     */
    public void setSelectionEnd(Integer selectionEnd) {
        this.selectionEnd = selectionEnd;
    }

    /**
     * 
     * {@linkplain #attrGroup}
     *
     * @return the value of activity_commdity_item.attr_group
     */
    public String getAttrGroup() {
        return attrGroup;
    }

    /**
     * {@linkplain #attrGroup}
     * @param attrGroup the value for activity_commdity_item.attr_group
     */
    public void setAttrGroup(String attrGroup) {
        this.attrGroup = attrGroup == null ? null : attrGroup.trim();
    }

    public ActivityTypeEnum getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityTypeEnum activityType) {
		this.activityType = activityType;
	}

	/**
     * 
     * {@linkplain #limitCount}
     *
     * @return the value of activity_commdity_item.limit_count
     */
    public Integer getLimitCount() {
        return limitCount;
    }

    /**
     * {@linkplain #limitCount}
     * @param limitCount the value for activity_commdity_item.limit_count
     */
    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

	public ActivityCommodity getActivityCommodity() {
		return activityCommodity;
	}

	public void setActivityCommodity(ActivityCommodity activityCommodity) {
		this.activityCommodity = activityCommodity;
	}

	public ActivityManager getActivity() {
		return activity;
	}

	public void setActivity(ActivityManager activity) {
		this.activity = activity;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

}
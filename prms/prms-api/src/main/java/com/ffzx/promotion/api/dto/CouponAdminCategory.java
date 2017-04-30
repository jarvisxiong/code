package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
* @Description: 类目表和商品表和优惠券管理中间表
* @author yuzhao.xu
* @email  yuzhao.xu@ffzxnet.com
* @date 2016年5月3日 下午6:15:37
* @version V1.0 
*
*/
public class CouponAdminCategory extends DataEntity<CouponAdminCategory> {

    private static final long serialVersionUID = 1L;

    /**
     * 类目表id.
     */
    private String categoryId;

    /**
     * 优惠券管理id.
     */
    private CouponAdmin couponAdmin;

    /**
     * 商品表id.
     */
    private String commodityId;

    /**
     * 2是商品，3是类目.
     * COMM_FIXED="2";COMM_CATEGORY="3";
     */
    private String type;
    /**
     * 指定普通活动活动.
     */
    private String activityManagerId;

    public String getActivityManagerId() {
		return activityManagerId;
	}

	public void setActivityManagerId(String activityManagerId) {
		this.activityManagerId = activityManagerId;
	}

	/**
     * 
     * {@linkplain #categoryId}
     *
     * @return the value of coupon_admin_category.category_id
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * {@linkplain #categoryId}
     * @param categoryId the value for coupon_admin_category.category_id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

	/**
     * 
     * {@linkplain #commodityId}
     *
     * @return the value of coupon_admin_category.commodity_id
     */
    public String getCommodityId() {
        return commodityId;
    }

    /**
     * {@linkplain #commodityId}
     * @param commodityId the value for coupon_admin_category.commodity_id
     */
    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId == null ? null : commodityId.trim();
    }

    /**
     * 
     * {@linkplain #type}
     *
     * @return the value of coupon_admin_category.type
     */
    public String getType() {
        return type;
    }

    /**
     * {@linkplain #type}
     * @param type the value for coupon_admin_category.type
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

	public CouponAdmin getCouponAdmin() {
		return couponAdmin;
	}

	public void setCouponAdmin(CouponAdmin couponAdmin) {
		this.couponAdmin = couponAdmin;
	}
    
}
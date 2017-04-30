package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * 
 * @author ffzx
 * @date 2016-09-12 11:25:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class GiftCoupon extends DataEntity<GiftCoupon> {

    private static final long serialVersionUID = 1L;

    /**
     * 优惠券编码.
     */
    private String couponCode;

    /**
     * 优惠券名称.
     */
    private String couponName;

    /**
     * 优惠券面值.
     */
    private String couponFace;

    /**
     * 优惠券消费限制.
     */
    private String couponLimit;

    /**
     * 优惠券有效期.
     */
    private String couponNum;

    /**
     * 买赠管理ID.
     */
    private String giveId;

    /**
     * 买赠主商品ID.
     */
    private String commodityId;

    /**
     * 优惠券ID.
     */
    private String couponId;

    /**
     * 
     * {@linkplain #couponCode}
     *
     * @return the value of gift_coupon.coupon_code
     */
    public String getCouponCode() {
        return couponCode;
    }

    /**
     * {@linkplain #couponCode}
     * @param couponCode the value for gift_coupon.coupon_code
     */
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode == null ? null : couponCode.trim();
    }

    /**
     * 
     * {@linkplain #couponName}
     *
     * @return the value of gift_coupon.coupon_name
     */
    public String getCouponName() {
        return couponName;
    }

    /**
     * {@linkplain #couponName}
     * @param couponName the value for gift_coupon.coupon_name
     */
    public void setCouponName(String couponName) {
        this.couponName = couponName == null ? null : couponName.trim();
    }

    /**
     * 
     * {@linkplain #couponFace}
     *
     * @return the value of gift_coupon.coupon_face
     */
    public String getCouponFace() {
        return couponFace;
    }

    /**
     * {@linkplain #couponFace}
     * @param couponFace the value for gift_coupon.coupon_face
     */
    public void setCouponFace(String couponFace) {
        this.couponFace = couponFace == null ? null : couponFace.trim();
    }

    /**
     * 
     * {@linkplain #couponLimit}
     *
     * @return the value of gift_coupon.coupon_limit
     */
    public String getCouponLimit() {
        return couponLimit;
    }

    /**
     * {@linkplain #couponLimit}
     * @param couponLimit the value for gift_coupon.coupon_limit
     */
    public void setCouponLimit(String couponLimit) {
        this.couponLimit = couponLimit == null ? null : couponLimit.trim();
    }

    /**
     * 
     * {@linkplain #couponNum}
     *
     * @return the value of gift_coupon.coupon_num
     */
    public String getCouponNum() {
        return couponNum;
    }

    /**
     * {@linkplain #couponNum}
     * @param couponNum the value for gift_coupon.coupon_num
     */
    public void setCouponNum(String couponNum) {
        this.couponNum = couponNum == null ? null : couponNum.trim();
    }

    /**
     * 
     * {@linkplain #giveId}
     *
     * @return the value of gift_coupon.give_id
     */
    public String getGiveId() {
        return giveId;
    }

    /**
     * {@linkplain #giveId}
     * @param giveId the value for gift_coupon.give_id
     */
    public void setGiveId(String giveId) {
        this.giveId = giveId == null ? null : giveId.trim();
    }

    /**
     * 
     * {@linkplain #commodityId}
     *
     * @return the value of gift_coupon.commodity_id
     */
    public String getCommodityId() {
        return commodityId;
    }

    /**
     * {@linkplain #commodityId}
     * @param commodityId the value for gift_coupon.commodity_id
     */
    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId == null ? null : commodityId.trim();
    }

    /**
     * 
     * {@linkplain #couponId}
     *
     * @return the value of gift_coupon.coupon_id
     */
    public String getCouponId() {
        return couponId;
    }

    /**
     * {@linkplain #couponId}
     * @param couponId the value for gift_coupon.coupon_id
     */
    public void setCouponId(String couponId) {
        this.couponId = couponId == null ? null : couponId.trim();
    }
}
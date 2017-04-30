package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author ffzx
 * @date 2016-09-22 15:11:01
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class GiveOrderCoupon extends DataEntity<GiveOrderCoupon> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号.
     */
    private String orderNo;

    /**
     * 订单ID.
     */
    private String orderId;

    /**
     * 买赠活动ID.
     */
    private String giveId;

    /**
     * 优惠券ID.
     */
    private String couponId;

    /**
     * 买赠管理优惠券ID.
     */
    private String giftCouponId;


    /**
     * 优惠券结束时间.
     */
    private Date enddate;

    /**
     * 有效期（天数）.
     */
    private String limitDay;
    
    /***
     * 冗余字段，订单明细中的购买数量
     */
    private int buyNum;
    
    /***
     * 通过buyNum与买赠活动的触发数量计算出的单次需要赠送的数量
     */
    private String sendNum; 
    
    /***
     * 发放时间
     */
    private Date sendDate;
    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of give_order_coupon.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * {@linkplain #orderNo}
     * @param orderNo the value for give_order_coupon.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * 
     * {@linkplain #orderId}
     *
     * @return the value of give_order_coupon.order_id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * {@linkplain #orderId}
     * @param orderId the value for give_order_coupon.order_id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    



	public String getGiveId() {
		return giveId;
	}

	public void setGiveId(String giveId) {
		this.giveId = giveId;
	}

	/**
     * 
     * {@linkplain #couponId}
     *
     * @return the value of give_order_coupon.coupon_id
     */
    public String getCouponId() {
        return couponId;
    }

    /**
     * {@linkplain #couponId}
     * @param couponId the value for give_order_coupon.coupon_id
     */
    public void setCouponId(String couponId) {
        this.couponId = couponId == null ? null : couponId.trim();
    }

    /**
     * 
     * {@linkplain #giftCouponId}
     *
     * @return the value of give_order_coupon.gift_coupon_id
     */
    public String getGiftCouponId() {
        return giftCouponId;
    }

    /**
     * {@linkplain #giftCouponId}
     * @param giftCouponId the value for give_order_coupon.gift_coupon_id
     */
    public void setGiftCouponId(String giftCouponId) {
        this.giftCouponId = giftCouponId == null ? null : giftCouponId.trim();
    }



    /**
     * 
     * {@linkplain #enddate}
     *
     * @return the value of give_order_coupon.endDate
     */
    public Date getEnddate() {
        return enddate;
    }

    /**
     * {@linkplain #enddate}
     * @param enddate the value for give_order_coupon.endDate
     */
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    /**
     * 
     * {@linkplain #limitDay}
     *
     * @return the value of give_order_coupon.limit_day
     */
    public String getLimitDay() {
        return limitDay;
    }

    /**
     * {@linkplain #limitDay}
     * @param limitDay the value for give_order_coupon.limit_day
     */
    public void setLimitDay(String limitDay) {
        this.limitDay = limitDay == null ? null : limitDay.trim();
    }

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public String getSendNum() {
		return sendNum;
	}

	public void setSendNum(String sendNum) {
		this.sendNum = sendNum;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Override
	public String toString() {
		return "GiveOrderCoupon [orderNo=" + orderNo + ", orderId=" + orderId + ", giveId=" + giveId + ", couponId="
				+ couponId + ", giftCouponId=" + giftCouponId + ", enddate=" + enddate + ", limitDay=" + limitDay
				+ ", buyNum=" + buyNum + ", sendNum=" + sendNum + ", sendDate=" + sendDate + "]";
	}
    
	
    
}
package com.ffzx.order.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;


 /**
 * @Description: 售后取货单
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年5月13日 下午2:14:17
 * @version V1.0 
 *
 */
public class AftersalePickup extends DataEntity<AftersalePickup> {

    private static final long serialVersionUID = 1L;

    /**
     * 取货单号.
     */
    private String pickupNo;

    /**
     * 关联订单号.
     */
    private String orderNo;

    /**
     * 关联售后申请单号.
     */
    private String applyNo;

    /**
     * 关联退款单号.
     */
    private String refundNo;

    /**
     * 关联换货订单号.
     */
    private String exchangeOrderNo;

    /**
     * 售后取货单状态.（0未审核，1：通过审核，2：删除）
     */
    private String pickupStatus;
    
    /*
     * 售后申请单
     */
    private AftersaleApply aftersaleApply;
    
    //冗余
    //取货单类型 0退款 1换货
    private String pickupType;
    //质检数量
    private int pickupCount;
    //商品编码
    private String commodityNo;
    
    
    
    public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public int getPickupCount() {
		return pickupCount;
	}

	public void setPickupCount(int pickupCount) {
		this.pickupCount = pickupCount;
	}

	public String getPickupType() {
		return pickupType;
	}

	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}

	public AftersaleApply getAftersaleApply() {
		return aftersaleApply;
	}

	public void setAftersaleApply(AftersaleApply aftersaleApply) {
		this.aftersaleApply = aftersaleApply;
	}

	/**
     * 
     * {@linkplain #pickupNo}
     *
     * @return the value of aftersale_pickup.pickup_no
     */
    public String getPickupNo() {
        return pickupNo;
    }

    /**
     * {@linkplain #pickupNo}
     * @param pickupNo the value for aftersale_pickup.pickup_no
     */
    public void setPickupNo(String pickupNo) {
        this.pickupNo = pickupNo == null ? null : pickupNo.trim();
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of aftersale_pickup.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * {@linkplain #orderNo}
     * @param orderNo the value for aftersale_pickup.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * 
     * {@linkplain #applyNo}
     *
     * @return the value of aftersale_pickup.apply_no
     */
    public String getApplyNo() {
        return applyNo;
    }

    /**
     * {@linkplain #applyNo}
     * @param applyNo the value for aftersale_pickup.apply_no
     */
    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo == null ? null : applyNo.trim();
    }

    /**
     * 
     * {@linkplain #refundNo}
     *
     * @return the value of aftersale_pickup.refund_no
     */
    public String getRefundNo() {
        return refundNo;
    }

    /**
     * {@linkplain #refundNo}
     * @param refundNo the value for aftersale_pickup.refund_no
     */
    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo == null ? null : refundNo.trim();
    }

    /**
     * 
     * {@linkplain #exchangeOrderNo}
     *
     * @return the value of aftersale_pickup.exchange_order_no
     */
    public String getExchangeOrderNo() {
        return exchangeOrderNo;
    }

    /**
     * {@linkplain #exchangeOrderNo}
     * @param exchangeOrderNo the value for aftersale_pickup.exchange_order_no
     */
    public void setExchangeOrderNo(String exchangeOrderNo) {
        this.exchangeOrderNo = exchangeOrderNo == null ? null : exchangeOrderNo.trim();
    }

    /**
     * 
     * {@linkplain #pickupStatus}
     *
     * @return the value of aftersale_pickup.pickup_status
     */
    public String getPickupStatus() {
        return pickupStatus;
    }

    /**
     * {@linkplain #pickupStatus}
     * @param pickupStatus the value for aftersale_pickup.pickup_status
     */
    public void setPickupStatus(String pickupStatus) {
        this.pickupStatus = pickupStatus == null ? null : pickupStatus.trim();
    }
}
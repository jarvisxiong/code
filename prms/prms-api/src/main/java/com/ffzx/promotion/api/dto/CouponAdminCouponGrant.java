package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
* @Description: 优惠价发放和优惠券管理中间表
* @author yuzhao.xu
* @email  yuzhao.xu@ffzxnet.com
* @date 2016年5月3日 下午6:15:37
* @version V1.0 
*
*/
public class CouponAdminCouponGrant extends DataEntity<CouponAdminCouponGrant> {

    private static final long serialVersionUID = 1L;

    /**
     * 优惠券管理id.
     */
    private CouponAdmin couponAdmin;

    /**
     * 优惠券发放id.
     */
    private CouponGrant couponGrant;
    
    /**
     * 领取数量
     */
    private Integer receiveNum;
    
    /**
     * 是否领取
     */
    private String isReceive;
    
    /**
     * 发放总量
     */
    private Integer grantNum;
    

    
    

	public String getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(String isReceive) {
		this.isReceive = isReceive;
	}

	public Integer getGrantNum() {
		return grantNum;
	}

	public void setGrantNum(Integer grantNum) {
		this.grantNum = grantNum;
	}

	public CouponAdmin getCouponAdmin() {
		return couponAdmin;
	}

	public void setCouponAdmin(CouponAdmin couponAdmin) {
		this.couponAdmin = couponAdmin;
	}

	public CouponGrant getCouponGrant() {
		return couponGrant;
	}

	public void setCouponGrant(CouponGrant couponGrant) {
		this.couponGrant = couponGrant;
	}

	public Integer getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(Integer receiveNum) {
		this.receiveNum = receiveNum;
	}

   
}
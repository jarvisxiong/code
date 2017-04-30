package com.ffzx.promotion.vo;

import java.io.Serializable;

public class CouponExportVo implements Serializable{

	private static final long serialVersionUID = 4977157834637991808L;
	
	private String giveId;//买赠活动的主键id
	
	private String couponCode ; //优惠券编码
	
	private String couponName ; //优惠券名称
	
	private String couponFace ; //优惠券面值
	
	private String couponLimit ; //优惠券消费限制
	
	private String couponNum ; //优惠券有效期
	
	public String getGiveId() {
		return giveId;
	}

	public void setGiveId(String giveId) {
		this.giveId = giveId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponFace() {
		return couponFace;
	}

	public void setCouponFace(String couponFace) {
		this.couponFace = couponFace;
	}

	public String getCouponLimit() {
		if(this.couponLimit!=null){
			return this.couponLimit.equals("-1.00")?"无限制":"满"+this.couponLimit+"元可使用";
		}
		return couponLimit;
	}

	public void setCouponLimit(String couponLimit) {
		this.couponLimit = couponLimit;
	}

	public String getCouponNum() {
		return couponNum;
	}

	public void setCouponNum(String couponNum) {
		this.couponNum = couponNum;
	}
	
	
}

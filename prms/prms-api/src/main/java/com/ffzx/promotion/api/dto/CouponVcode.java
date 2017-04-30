package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.commerce.framework.constant.Constant;


 /**
 * @Description: TODO
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年7月26日 下午6:12:31
 * @version V1.0 
 *
 */
public class CouponVcode extends DataEntity<CouponVcode> {

    public CouponVcode(String id,String vcode, String couponGrantId) {
		super();
		this.id=id;
		this.vcode = vcode;
		this.couponGrantId = couponGrantId;
	}

	public CouponVcode() {
		super();
	}

	public CouponVcode(String couponGrantId) {
		super();
		this.couponGrantId = couponGrantId;
	}

	private static final long serialVersionUID = 1L;
	
    /**
     * 优惠码.
     */
    private String vcode;

    /**
     * 状态（1领取，0未领取).
     */
    private String start;
    
    /**
     * 状态str
     */

    private String startStr;

    /**
     * 优惠券发放id.
     */
    private String couponGrantId;

    /**
     * 
     * {@linkplain #vcode}
     *
     * @return the value of coupon_vcode.vcode
     */
    public String getVcode() {
        return vcode;
    }

    /**
     * {@linkplain #vcode}
     * @param vcode the value for coupon_vcode.vcode
     */
    public void setVcode(String vcode) {
        this.vcode = vcode == null ? null : vcode.trim();
    }

    public String getStartStr() {
    	if(start.equals(Constant.YES)){
    		return "已领取";
    	}
		return "未领取";
	}

	public void setStartStr(String startStr) {
		this.startStr = startStr;
	}

	/**
     * 
     * {@linkplain #start}
     *
     * @return the value of coupon_vcode.start
     */
    public String getStart() {
        return start;
    }

    /**
     * {@linkplain #start}
     * @param start the value for coupon_vcode.start
     */
    public void setStart(String start) {
        this.start = start == null ? null : start.trim();
    }

    /**
     * 
     * {@linkplain #couponGrantId}
     *
     * @return the value of coupon_vcode.coupon_grant_id
     */
    public String getCouponGrantId() {
        return couponGrantId;
    }

    /**
     * {@linkplain #couponGrantId}
     * @param couponGrantId the value for coupon_vcode.coupon_grant_id
     */
    public void setCouponGrantId(String couponGrantId) {
        this.couponGrantId = couponGrantId == null ? null : couponGrantId.trim();
    }
}
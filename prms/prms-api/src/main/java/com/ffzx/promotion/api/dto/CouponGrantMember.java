package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
* @Description: 优惠券发放和会员中间表（指定用户）
* @author yuzhao.xu
* @email  yuzhao.xu@ffzxnet.com
* @date 2016年5月3日 下午6:15:37
* @version V1.0 
*
*/
public class CouponGrantMember extends DataEntity<CouponGrantMember> {

    private static final long serialVersionUID = 1L;

    /**
     * 优惠券发放id.
     */
    private CouponGrant couponGrant;

    /**
     * 会员id.
     */
    private String memberId;

    /**
     * 
     * {@linkplain #memberId}
     *
     * @return the value of coupon_grant_member.member_id
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * {@linkplain #memberId}
     * @param memberId the value for coupon_grant_member.member_id
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

	public CouponGrant getCouponGrant() {
		return couponGrant;
	}

	public void setCouponGrant(CouponGrant couponGrant) {
		this.couponGrant = couponGrant;
	}
    
    
}
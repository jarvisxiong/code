package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * 
 * @author ffzx
 * @date 2016-11-07 09:42:57
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class UserLable extends DataEntity<UserLable> {

    public UserLable() {
		super();
		// TODO Auto-generated constructor stub
	}


	public UserLable(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public UserLable(String lableId, String lablename, String lablenumber) {

		super();
		this.lableId = lableId;
		this.lablename = lablename;
		this.lablenumber = lablenumber;
	}

	private static final long serialVersionUID = 1L;

    /**
     * 标签id.
     */
    private String lableId;

    /**
     * 红包发放id.
     */
    private String redpackageGrantId;

    /**
     * 优惠券发放id.
     */
    private String couponGrantId;
    
    //冗余字段===================
    /**
     * 标签名称
     */
    private String lablename;

    /**
     * 便签编码
     */
    private String lablenumber;
    public String getLablename() {
		return lablename;
	}

	public void setLablename(String lablename) {
		this.lablename = lablename;
	}

	public String getLablenumber() {
		return lablenumber;
	}

	public void setLablenumber(String lablenumber) {
		this.lablenumber = lablenumber;
	}

	/**
     * 
     * {@linkplain #lableId}
     *
     * @return the value of user_lable.lable_id
     */
    public String getLableId() {
        return lableId;
    }

    /**
     * {@linkplain #lableId}
     * @param lableId the value for user_lable.lable_id
     */
    public void setLableId(String lableId) {
        this.lableId = lableId == null ? null : lableId.trim();
    }

    /**
     * 
     * {@linkplain #redpackageGrantId}
     *
     * @return the value of user_lable.redpackage_grant_id
     */
    public String getRedpackageGrantId() {
        return redpackageGrantId;
    }

    /**
     * {@linkplain #redpackageGrantId}
     * @param redpackageGrantId the value for user_lable.redpackage_grant_id
     */
    public void setRedpackageGrantId(String redpackageGrantId) {
        this.redpackageGrantId = redpackageGrantId == null ? null : redpackageGrantId.trim();
    }

    /**
     * 
     * {@linkplain #couponGrantId}
     *
     * @return the value of user_lable.coupon_grant_id
     */
    public String getCouponGrantId() {
        return couponGrantId;
    }

    /**
     * {@linkplain #couponGrantId}
     * @param couponGrantId the value for user_lable.coupon_grant_id
     */
    public void setCouponGrantId(String couponGrantId) {
        this.couponGrantId = couponGrantId == null ? null : couponGrantId.trim();
    }
}
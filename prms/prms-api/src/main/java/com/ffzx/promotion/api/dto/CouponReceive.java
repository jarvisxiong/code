package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.util.Date;
import java.util.List;

/**
* @Description: 优惠券领取
* @author yuzhao.xu
* @email  yuzhao.xu@ffzxnet.com
* @date 2016年5月3日 下午6:15:37
* @version V1.0 
*
*/
public class CouponReceive extends DataEntity<CouponReceive> implements Cloneable{

    private static final long serialVersionUID = 1L;

    /**
     * 会员id.
     */
    private String memberId;

    /**
     * 昵称.
     */
    private String nickName;

    /**
     * 会员手机号.
     */
    private String phone;

    /**
     * 领取时间.
     */
    private Date receiveDate;
    /**
     * .过期时间
     */
    private Date overDate;


    /**
     * 发放优惠码.
     */
    private String grantVcode;
    /**
     * 使用状态(0未使用，1使用).
     * 0 NO  1 YES
     */
    private String useState;
    private String userStateStr;   //导出表格使用冗余字段
    /**
     * 是否已提醒（0：未提醒，1：已提醒）
     */
    private String isRemind;
    
    /**
     * 是否领取（1领取，0未领取）.
     */
    private String isReceive;
    

	public String getGrantVcode() {
		return grantVcode;
	}

	public void setGrantVcode(String grantVcode) {
		this.grantVcode = grantVcode;
	}

	public Date getOverDate() {
		return overDate;
	}

	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}

	public String getUserStateStr() {
    	if(useState==null || "0".equals(useState)){
    		return "未使用";
    	}
    	else{
    		return "已使用";
    	}
	}

	public void setUserStateStr(String userStateStr) {
		this.userStateStr = userStateStr;
	}

	/**
     * 使用时间.
     */
    private Date useDate;

    /**
     * 优惠券管理id.
     */
    private CouponAdmin couponAdmin;

    /**
     * 订单编码（用户购买优惠券）.
     */
    private String orderNo;
    /**
     * 优惠券发放id.
     */
    private CouponGrant couponGrant;

    private String beginReceiveDateStr;
    
    private String endReceiveDateStr;
    
    /******
     * 冗余
     */
    private String categoryNames;
    private String goodsNames;
	private String grantModeReceiveStr;
	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getGoodsNames() {
		return goodsNames;
	}

	public void setGoodsNames(String goodsNames) {
		this.goodsNames = goodsNames;
	}

	public String getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(String isReceive) {
		this.isReceive = isReceive;
	}

	public String getGrantModeReceiveStr() {
		if(couponGrant!=null && couponGrant.getGrantModeStr()!=null){
			return couponGrant.getGrantModeStr();
		}
		return "新用户专享";
	}

	public void setGrantModeReceiveStr(String grantModeReceiveStr) {
		this.grantModeReceiveStr = grantModeReceiveStr;
	}

	/**
     * 
     * {@linkplain #memberId}
     *
     * @return the value of coupon_receive.member_id
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * {@linkplain #memberId}
     * @param memberId the value for coupon_receive.member_id
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * 
     * {@linkplain #nickName}
     *
     * @return the value of coupon_receive.nick_name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * {@linkplain #nickName}
     * @param nickName the value for coupon_receive.nick_name
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * 
     * {@linkplain #phone}
     *
     * @return the value of coupon_receive.phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * {@linkplain #phone}
     * @param phone the value for coupon_receive.phone
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 
     * {@linkplain #receiveDate}
     *
     * @return the value of coupon_receive.receive_date
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * {@linkplain #receiveDate}
     * @param receiveDate the value for coupon_receive.receive_date
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * 
     * {@linkplain #useState}
     *
     * @return the value of coupon_receive.use_state
     */
    public String getUseState() {
        return useState;
    }

    /**
     * {@linkplain #useState}
     * @param useState the value for coupon_receive.use_state
     */
    public void setUseState(String useState) {
        this.useState = useState == null ? null : useState.trim();
    }

    /**
     * 
     * {@linkplain #useDate}
     *
     * @return the value of coupon_receive.use_date
     */
    public Date getUseDate() {
        return useDate;
    }

    /**
     * {@linkplain #useDate}
     * @param useDate the value for coupon_receive.use_date
     */
    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

	public String getIsRemind() {
		return isRemind;
	}

	public void setIsRemind(String isRemind) {
		this.isRemind = isRemind;
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

	public String getBeginReceiveDateStr() {
		return beginReceiveDateStr;
	}

	public void setBeginReceiveDateStr(String beginReceiveDateStr) {
		this.beginReceiveDateStr = beginReceiveDateStr;
	}

	public String getEndReceiveDateStr() {
		return endReceiveDateStr;
	}

	public void setEndReceiveDateStr(String endReceiveDateStr) {
		this.endReceiveDateStr = endReceiveDateStr;
	}
  
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
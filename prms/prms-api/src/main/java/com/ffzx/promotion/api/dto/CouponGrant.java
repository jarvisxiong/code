package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.promotion.api.dto.constant.Constant;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
* @Description: 优惠券发放
* @author yuzhao.xu
* @email  yuzhao.xu@ffzxnet.com
* @date 2016年5月3日 下午6:15:37
* @version V1.0 
*
*/
public class CouponGrant extends DataEntity<CouponGrant> {

    private static final long serialVersionUID = 1L;

    public CouponGrant() {
		super();
	}

	/**
     * 编码.
     */
    private String number;

    public CouponGrant(String grantMode) {
		super();
		this.grantMode = grantMode;
	}

	/**
     * 发放名称.
     */
    private String name;

    /**
     * 发放时间.
     */
    private Date grantDate;

    /**
     * 
     * 发放方式(0系统推送,1用户领取,2指定用户，3优惠码,4消息推送，5天降
     * GRANT_SYS="0";GRANT_USERREC="1";GRANT_FIXEDUSER="2";
     */
    private String grantMode;

    /**
     * 发放类型(0普通，1注册，2分享).3优惠码
     * GRANT_COMM="0";GRANT_REG="1";GRANT_SHARE="2";
     */
    private String grantType;

    /**
     * 领取状态(0未开始,1领取中,2领取完成,3已结束).
     * REC_UNSTART="0";REC_ING="1";REC_SUCCESS="2";REC_END="3";
     */
    private String receiveState;

    /**
     * 领取限制(-1)无限制.
     */
    private Integer receiveLimit;

    /**
     * 发放张数.
     */
    private Integer grantNum;

    /**
     * 剩余量.
     */
    private Integer surplusNum;

    /**
     *   总量.不常用.几乎不用此字段
     */
    private Integer zongnum;

    /**
     * 领取量.
     */
    private Integer receiveNum;

    /**
     * 链接.
     * 0 NO 不推广 1 YES推广
     */
    private String url;

    /**
     * 状态，0不暂停，1暂停领取.
     * 0 NO 1 YES
     */
    private Integer state;

    /**
     * 是否已发放（0未发放,1发放)
     * 0 NO 1 YES 2进行中
     */
    private String isGrant;/**
     * .发放结束时间 
     */
    private Date grantEndDate;
    /**
     * 0所有用户，1指定用户，2新用户.  3指定用户标签
     */
    private String userType;
    /**
     * 冗余字段，发放开始结束时间
     */
    private String beginGrantDateStr;
    private String endGrantDateStr;
    private String grantDateStr;
    private String grantModeStr;
    private String grantEndDateStr;
	private List<UserLable> userLables;//用户标签

    private String userlableList;
    
    public List<UserLable> getUserLables() {
		return userLables;
	}

	public void setUserLables(List<UserLable> userLables) {
		this.userLables = userLables;
	}

	public String getUserlableList() {
		return userlableList;
	}

	public void setUserlableList(String userlableList) {
		this.userlableList = userlableList;
	}

	public String getGrantEndDateStr() {
		return grantEndDateStr;
	}

	public void setGrantEndDateStr(String grantEndDateStr) {
		this.grantEndDateStr = grantEndDateStr;
	}

	public String getGrantModeStr() {
    	if(grantMode == null || "0".equals(grantMode)){
    		return "系统推送";
    	}else if("1".equals(grantMode)){
    		return "用户领取";
    	}else if("2".equals(grantMode)){
    		return "指定用户";
    	}else if("4".equals(grantMode)){
    		return "消息优惠券推送";
    	}else if("5".equals(grantMode)){
    		return "天降优惠券推送";
    	}
		return grantModeStr;
	}

	public void setGrantModeStr(String grantModeStr) {
		this.grantModeStr = grantModeStr;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getGrantDateStr() {
		return grantDateStr;
	}

	public Date getGrantEndDate() {
		return grantEndDate;
	}

	public void setGrantEndDate(Date grantEndDate) {
		this.grantEndDate = grantEndDate;
	}

	public void setGrantDateStr(String grantDateStr) {
		this.grantDateStr = grantDateStr;
	}

	public String getEndGrantDateStr() {
		return endGrantDateStr;
	}

	public String getIsGrant() {
		if(!StringUtil.isEmpty(grantMode) &&  grantDate!=null && grantEndDate!=null  ){//如果是用户领取,根据时间判断
	    	if(PrmsConstant.CouponUserReceive.equals(grantMode) 
	    			|| PrmsConstant.CouponFallingMovement.equals(grantMode)){//如果是用户领取,根据时间判断
				if(DateUtil.diffDateTime(grantDate,new Date())<0){//发放
		    			if(grantEndDate!=null && DateUtil.diffDateTime(grantEndDate,new Date())<0){//已结束
		        			return Constant.YES;
		    			}else{
		    				return  Constant.STATUS_OVERDUR;//进行中
		    			}
		    		}
		    		else{
		    			return Constant.NO;
		    		}
	    	}else if(PrmsConstant.CouponMessageMovement.equals(grantMode)){
	    		//如果是消息或者天降
	    		if(DateUtil.diffDateTime(grantDate,new Date())<0){//发放
	    			if(grantEndDate!=null && DateUtil.diffDateTime(grantEndDate,new Date())<0){//已结束
	        			return Constant.YES;
	    			}
	    			
	    		}
	    		return isGrant;
	    		
	    	}
    	}
		return isGrant;
	}

	public void setIsGrant(String isGrant) {
		this.isGrant = isGrant;
	}

	public void setEndGrantDateStr(String endGrantDateStr) {
		this.endGrantDateStr = endGrantDateStr;
	}

	public String getBeginGrantDateStr() {
		return beginGrantDateStr;
	}

	public void setBeginGrantDateStr(String beginGrantDateStr) {
		this.beginGrantDateStr = beginGrantDateStr;
	}
	
	public Integer getSurplusNum() {
		return surplusNum;
	}

	public void setSurplusNum(Integer surplusNum) {
		this.surplusNum = surplusNum;
	}

	public Integer getZongnum() {
		return zongnum;
	}

	public void setZongnum(Integer zongnum) {
		this.zongnum = zongnum;
	}

	public Integer getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(Integer receiveNum) {
		this.receiveNum = receiveNum;
	}


	/**
     * 
     * {@linkplain #number}
     *
     * @return the value of coupon_grant.number
     */
    public String getNumber() {
        return number;
    }

    /**
     * {@linkplain #number}
     * @param number the value for coupon_grant.number
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of coupon_grant.name
     */
    public String getName() {
        return name;
    }

    /**
     * {@linkplain #name}
     * @param name the value for coupon_grant.name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * {@linkplain #grantDate}
     *
     * @return the value of coupon_grant.grant_date
     */
    public Date getGrantDate() {
        return grantDate;
    }

    /**
     * {@linkplain #grantDate}
     * @param grantDate the value for coupon_grant.grant_date
     */
    public void setGrantDate(Date grantDate) {
        this.grantDate = grantDate;
    }

    /**
     * 
     * {@linkplain #grantMode}
     *
     * @return the value of coupon_grant.grant_mode
     */
    public String getGrantMode() {
        return grantMode;
    }

    /**
     * {@linkplain #grantMode}
     * @param grantMode the value for coupon_grant.grant_mode
     */
    public void setGrantMode(String grantMode) {
        this.grantMode = grantMode == null ? null : grantMode.trim();
    }

    /**
     * 
     * {@linkplain #grantType}
     *
     * @return the value of coupon_grant.grant_type
     */
    public String getGrantType() {
    	
        return grantType;
    }

    /**
     * {@linkplain #grantType}
     * @param grantType the value for coupon_grant.grant_type
     */
    public void setGrantType(String grantType) {
        this.grantType = grantType == null ? null : grantType.trim();
    }

    /**
     * 
     * {@linkplain #receiveState}
     *
     * @return the value of coupon_grant.receive_state
     */
    public String getReceiveState() {
    	if(grantDate != null){
    		if(grantDate.getTime() <= new Date().getTime()){
        		return "1";
        	}else if(grantDate.getTime() > new Date().getTime()){
        		return "0";
        	}
    	}
        return receiveState;
    }

    /**
     * {@linkplain #receiveState}
     * @param receiveState the value for coupon_grant.receive_state
     */
    public void setReceiveState(String receiveState) {
        this.receiveState = receiveState == null ? null : receiveState.trim();
    }

    /**
     * 
     * {@linkplain #receiveLimit}
     *
     * @return the value of coupon_grant.receive_limit
     */
    public Integer getReceiveLimit() {
        return receiveLimit;
    }

    /**
     * {@linkplain #receiveLimit}
     * @param receiveLimit the value for coupon_grant.receive_limit
     */
    public void setReceiveLimit(Integer receiveLimit) {
        this.receiveLimit = receiveLimit;
    }

    /**
     * 
     * {@linkplain #grantNum}
     *
     * @return the value of coupon_grant.grant_num
     */
    public Integer getGrantNum() {
        return grantNum;
    }

    /**
     * {@linkplain #grantNum}
     * @param grantNum the value for coupon_grant.grant_num
     */
    public void setGrantNum(Integer grantNum) {
        this.grantNum = grantNum;
    }

    /**
     * 
     * {@linkplain #url}
     *
     * @return the value of coupon_grant.url
     */
    public String getUrl() {
        return url;
    }

    /**
     * {@linkplain #url}
     * @param url the value for coupon_grant.url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 
     * {@linkplain #state}
     *
     * @return the value of coupon_grant.state
     */
    public Integer getState() {
        return state;
    }

    /**
     * {@linkplain #state}
     * @param state the value for coupon_grant.state
     */
    public void setState(Integer state) {
        this.state = state;
    }
}
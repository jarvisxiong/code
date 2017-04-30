package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.promotion.api.dto.constant.Constant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
* @Description: 优惠券管理
* @author yuzhao.xu
* @email  yuzhao.xu@ffzxnet.com
* @date 2016年5月3日 下午6:15:37
* @version V1.0 
*
*/
public class CouponAdmin extends DataEntity<CouponAdmin> {

    private static final long serialVersionUID = 1L;

    /**
     * 编码.
     */
    private String number;

    /**
     * 面值.（小）
     */
    @NotNull
    private BigDecimal faceValue;
    
   
    /**
     * 优惠券名称.
     */
    @NotNull
    private String name;

    /**
     * 消费限制(-1)无限制.有限制就是保存 满多少元
     * 
     */
    private BigDecimal consumptionLimit;

    /**
     * 有效期状态(1:指定有效期，0:自定义有效期:DATE_FIXED="1";DATE_CUSTOM="0";).
     */
    @NotNull
    private String effectiveDateState;

    /**
     * 有效期开始时间.
     */
    private Date effectiveDateStart;

    /**
     * 有效期结束时间.
     */
    private Date effectiveDateEnd;

    /**
     * 优惠券状态0未生效，1已生效，2已过期
     * （STATUS_UNEFFECT="0";STATUS_EFFECT="1";STATUS_OVERDUR="2";）.
     */
    private String couponState;

    /**
     * 有效天数.
     */
    private BigDecimal effectiveDateNum;

    /**
     * 选择商品(0全部商品，2指定商品，3指定商品类目.
     * COMM_ALL="0";COMM_FIXED="2";COMM_CATEGORY="3";
     */
    @NotNull
    private String goodsSelect;

    /**
     * 其他限制(0无限制 NO）1有限制 YES.
     */
    private String otherLimit;
    
    /********
     * 剩余量
     */
    public BigDecimal surplus;
    
    /*****
     * 备注
     */
    public String remarks;
    
    /*****
     * 发放id
     */
    public String couponGrantId;

    //冗余字段
    /****
     * 面值(大)
     */
    private BigDecimal maxFaceValue;
    private String effectiveDateStartStr;
    private String effectiveDateEndStr;
    private String effectiveDateStr;//有效时间
    private String goodsId;
    private String categoryId;
    private String limitValue;
    private String effectiveStr;   //导出表格使用冗余字段，有效期
    
    // 创建时间冗余字段 -- 页面查询条件
    private String createDateStart; // 创建开始时间
    private String createDateEnd; // 创建结束时间

    private String activityManageIds;//活动管理id集合
    private List<String> list;//商品集合或者类目集合id
    
    private String delFlagTemp;//冗余字段，用于“优惠券管理”界面查询字段（启用状态，不需要默认值，但delFlag有默认值“0”）
    
    public String getActivityManageIds() {
		return activityManageIds;
	}
	public void setActivityManageIds(String activityManageIds) {
		this.activityManageIds = activityManageIds;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public String getEffectiveDateStr() {
		return effectiveDateStr;
	}
	public void setEffectiveDateStr(String effectiveDateStr) {
		this.effectiveDateStr = effectiveDateStr;
	}
	public String getEffectiveStr() {
    	if(effectiveDateState == null){
    		return "";
    	}else if("0".equals(effectiveDateState)){
    		return effectiveDateNum.intValue()+"天";
    	}else if("1".equals(effectiveDateState)){
    		return DateUtil.formatDateTime(effectiveDateStart) +"到"+DateUtil.formatDateTime(effectiveDateEnd);
    	}
		return effectiveStr;
	}
	public void setEffectiveStr(String effectiveStr) {
		this.effectiveStr = effectiveStr;
	}
	public String getLimitValue() {
		return limitValue;
	}
	public void setLimitValue(String limitValue) {
		this.limitValue = limitValue;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getEffectiveDateStartStr() {
		return effectiveDateStartStr;
	}
	public void setEffectiveDateStartStr(String effectiveDateStartStr) {
		this.effectiveDateStartStr = effectiveDateStartStr;
	}
	public String getEffectiveDateEndStr() {
		return effectiveDateEndStr;
	}
	public void setEffectiveDateEndStr(String effectiveDateEndStr) {
		this.effectiveDateEndStr = effectiveDateEndStr;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public BigDecimal getMaxFaceValue() {
		return maxFaceValue;
	}
	public void setMaxFaceValue(BigDecimal maxFaceValue) {
		this.maxFaceValue = maxFaceValue;
	}
	public String getCouponGrantId() {
		return couponGrantId;
	}
	public void setCouponGrantId(String couponGrantId) {
		this.couponGrantId = couponGrantId;
	}
	/**
     * 
     * {@linkplain #number}
     *
     * @return the value of coupon_admin.surplus
     */
    public BigDecimal getSurplus() {
		return surplus;
	}
    /**
     * {@linkplain #number}
     * @param number the value for coupon_admin.surplus
     */
	public void setSurplus(BigDecimal surplus) {
		this.surplus = surplus;
	}

	/**
     * 
     * {@linkplain #number}
     *
     * @return the value of coupon_admin.number
     */
    public String getNumber() {
        return number;
    }

    /**
     * {@linkplain #number}
     * @param number the value for coupon_admin.number
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * 
     * {@linkplain #faceValue}
     *
     * @return the value of coupon_admin.face_value
     */
    public BigDecimal getFaceValue() {
        return faceValue;
    }

    /**
     * {@linkplain #faceValue}
     * @param faceValue the value for coupon_admin.face_value
     */
    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of coupon_admin.name
     */
    public String getName() {
        return name;
    }

    /**
     * {@linkplain #name}
     * @param name the value for coupon_admin.name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * {@linkplain #consumptionLimit}
     *
     * @return the value of coupon_admin.consumption_limit
     */
    public BigDecimal getConsumptionLimit() {
        return consumptionLimit;
    }

    /**
     * {@linkplain #consumptionLimit}
     * @param consumptionLimit the value for coupon_admin.consumption_limit
     */
    public void setConsumptionLimit(BigDecimal consumptionLimit) {
        this.consumptionLimit = consumptionLimit;
    }

    /**
     * 
     * {@linkplain #effectiveDateState}
     *
     * @return the value of coupon_admin.effective_date_state
     */
    public String getEffectiveDateState() {
        return effectiveDateState;
    }

    /**
     * {@linkplain #effectiveDateState}
     * @param effectiveDateState the value for coupon_admin.effective_date_state
     */
    public void setEffectiveDateState(String effectiveDateState) {
        this.effectiveDateState = effectiveDateState == null ? null : effectiveDateState.trim();
    }

    /**
     * 
     * {@linkplain #effectiveDateStart}
     *
     * @return the value of coupon_admin.effective_date_start
     */
    public Date getEffectiveDateStart() {
        return effectiveDateStart;
    }

    /**
     * {@linkplain #effectiveDateStart}
     * @param effectiveDateStart the value for coupon_admin.effective_date_start
     */
    public void setEffectiveDateStart(Date effectiveDateStart) {
        this.effectiveDateStart = effectiveDateStart;
    }

    /**
     * 
     * {@linkplain #effectiveDateEnd}
     *
     * @return the value of coupon_admin.effective_date_end
     */
    public Date getEffectiveDateEnd() {
        return effectiveDateEnd;
    }

    /**
     * {@linkplain #effectiveDateEnd}
     * @param effectiveDateEnd the value for coupon_admin.effective_date_end
     */
    public void setEffectiveDateEnd(Date effectiveDateEnd) {
        this.effectiveDateEnd = effectiveDateEnd;
    }

    /**
     * 
     * {@linkplain #couponState}
     *
     * @return the value of coupon_admin.coupon_state
     */
    public String getCouponState() {
    	if(this.effectiveDateEnd!=null && this.effectiveDateStart!=null){
    		if(Long.valueOf(DateUtil.getTime())>this.effectiveDateEnd.getTime()){
        		couponState=Constant.STATUS_OVERDUR;//已过期
        	}else if(Long.valueOf(DateUtil.getTime())>this.effectiveDateStart.getTime() && Long.valueOf(DateUtil.getTime())<this.effectiveDateEnd.getTime()){
        		couponState=Constant.STATUS_EFFECT;//已生效
        	}else if(Long.valueOf(DateUtil.getTime())<this.effectiveDateStart.getTime()){
        		couponState=Constant.STATUS_UNEFFECT;//未生效
        	}
    	}  	
        return couponState;
    }

    /**
     * {@linkplain #couponState}
     * @param couponState the value for coupon_admin.coupon_state
     */
    public void setCouponState(String couponState) {
        this.couponState=couponState;
    }

    /**
     * 
     * {@linkplain #effectiveDateNum}
     *
     * @return the value of coupon_admin.effective_date_num
     */
    public BigDecimal getEffectiveDateNum() {
        return effectiveDateNum;
    }

    /**
     * {@linkplain #effectiveDateNum}
     * @param effectiveDateNum the value for coupon_admin.effective_date_num
     */
    public void setEffectiveDateNum(BigDecimal effectiveDateNum) {
        this.effectiveDateNum = effectiveDateNum;
    }

    /**
     * 
     * {@linkplain #goodsSelect}
     *
     * @return the value of coupon_admin.goods_select
     */
    public String getGoodsSelect() {
        return goodsSelect;
    }

    /**
     * {@linkplain #goodsSelect}
     * @param goodsSelect the value for coupon_admin.goods_select
     */
    public void setGoodsSelect(String goodsSelect) {
        this.goodsSelect = goodsSelect == null ? null : goodsSelect.trim();
    }

    /**
     * 
     * {@linkplain #otherLimit}
     *
     * @return the value of coupon_admin.other_limit
     */
    public String getOtherLimit() {
        return otherLimit;
    }

    /**
     * {@linkplain #otherLimit}
     * @param otherLimit the value for coupon_admin.other_limit
     */
    public void setOtherLimit(String otherLimit) {
        this.otherLimit = otherLimit == null ? null : otherLimit.trim();
    }
	public String getDelFlagTemp() {
		return delFlagTemp;
	}
	public void setDelFlagTemp(String delFlagTemp) {
		this.delFlagTemp = delFlagTemp;
	}
	public String getCreateDateStart() {
		return createDateStart;
	}
	public void setCreateDateStart(String createDateStart) {
		this.createDateStart = createDateStart;
	}
	
	
	public String getCreateDateEnd() {
		return createDateEnd;
	}
	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}
}
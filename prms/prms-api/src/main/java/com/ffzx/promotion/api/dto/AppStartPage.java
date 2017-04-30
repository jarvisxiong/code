package com.ffzx.promotion.api.dto;

import java.util.Date;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.promotion.api.enums.AdvertTypeEnum;

public class AppStartPage extends DataEntity<AppStartPage> {

	/** 
	* 
	*/
	private static final long serialVersionUID = -5499655929586197245L;

	// app终端(Android、IOS)
	private String terminal;
	
	// 生效时间
	private Date effectiveDate;
	
	// 过期时间
	private Date expiredDate;
	
	// 广告类型
	private String advertType;
	
	// 活动id（广告类型是--抢购详情、预售详情时 存放 活动id）
	private String activityId;
	
	// 活动id/活动商品id(特殊情况：广告类型是--抢购详情、预售详情时    存放 活动商品id，活动id有值存放；其他详情时  存放；列表 存放 活动id)
	private String objId;
	
	// 关联数据名称
	private String objName;
	
	// 链接地址（广告类型是--web链接 存放地址）
	private String url;
	
	// 图片1
	private String imgPath1;
	
	// 图片2
	private String imgPath2;
	
	// 图片3
	private String imgPath3;
	
	// 图片4（仅IOS使用）
	private String imgPath4;
	
	// 操作人
	private String operator;
	
	// 冗余字段
	private String status; // 状态 （未生效：0，已生效：1，已过期：2）
	private String effectiveStartDate; // 生效开始时间
	private String effectiveEndDate; // 生效结束时间
	private String expiredStartDate; // 过去开始时间
	private Date expiredEndDate; // 过去结束时间
	

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getAdvertType() {
		return advertType;
	}

	public void setAdvertType(String advertType) {
		this.advertType = advertType;
	}
	// 广告类型枚举
	public String getAdvertTypeStr() {
		return advertType==null?"":AdvertTypeEnum.valueOf(advertType).getName();
	}
	
	
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgPath1() {
		return imgPath1;
	}

	public void setImgPath1(String imgPath1) {
		this.imgPath1 = imgPath1;
	}

	public String getImgPath2() {
		return imgPath2;
	}

	public void setImgPath2(String imgPath2) {
		this.imgPath2 = imgPath2;
	}

	public String getImgPath3() {
		return imgPath3;
	}

	public void setImgPath3(String imgPath3) {
		this.imgPath3 = imgPath3;
	}

	public String getImgPath4() {
		return imgPath4;
	}

	public void setImgPath4(String imgPath4) {
		this.imgPath4 = imgPath4;
	}

	public String getStatus() {
		if(this.effectiveDate!=null && this.expiredDate!=null){
    		if(Long.valueOf(DateUtil.getTime())>this.expiredDate.getTime()){
    			status = Constant.STATUS_OVERDUR; // 已过期
    		}else if(Long.valueOf(DateUtil.getTime())>this.effectiveDate.getTime() && Long.valueOf(DateUtil.getTime())<this.expiredDate.getTime()){
    			status=Constant.STATUS_EFFECT;// 已生效
        	}else if(Long.valueOf(DateUtil.getTime())<this.effectiveDate.getTime()){
        		status=Constant.STATUS_UNEFFECT;// 未生效
        	}
    	}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(String effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public String getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(String effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public String getExpiredStartDate() {
		return expiredStartDate;
	}

	public void setExpiredStartDate(String expiredStartDate) {
		this.expiredStartDate = expiredStartDate;
	}

	public Date getExpiredEndDate() {
		return expiredEndDate;
	}

	public void setExpiredEndDate(Date expiredEndDate) {
		this.expiredEndDate = expiredEndDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}

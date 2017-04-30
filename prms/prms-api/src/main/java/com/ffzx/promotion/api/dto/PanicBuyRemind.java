package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

public class PanicBuyRemind extends DataEntity<PanicBuyRemind> {

	private static final long serialVersionUID = 1L;

	
	/**
	 * 活动Id
	 */
	private String activityId;
	
	/**
	 * 商品Id
	 */
	private String commodityId;
	
	/**
	 * 会员Id
	 */
	private String memberId;
	
	/**
	 * 是否提醒(0：取消提醒  1：加入提醒)
	 */
	private String isRemind;
	
	/**
	 * 极光推送(0未提醒，1提醒)
	 */
	private String isJiguangRemind;
	
	/**
	 * 极光定时推送id
	 */
	private String scheduleId;
	
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getIsRemind() {
		return isRemind;
	}

	public void setIsRemind(String isRemind) {
		this.isRemind = isRemind;
	}

	public String getIsJiguangRemind() {
		return isJiguangRemind;
	}

	public void setIsJiguangRemind(String isJiguangRemind) {
		this.isJiguangRemind = isJiguangRemind;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	
}

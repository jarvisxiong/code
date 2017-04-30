package com.ffzx.promotion.model;

/**
 * @Description: 抢购活动类目列表 App用
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年8月22日 上午11:17:22
 * @version V1.0 
 *
 */
public class ActivitySaleAppCategory{



	
	/**
	 * @param startDate
	 * @param states
	 * @param isCheck
	 * @param activityId
	 */
	 
	public ActivitySaleAppCategory(String startDate, String states, String isCheck, String activityId) {
		super();
		this.startDate = startDate;
		this.states = states;
		this.isCheck = isCheck;
		this.activityId = activityId;
	}




	/**
	 */
	 
	public ActivitySaleAppCategory() {
		super();
	}
/**
     * 开始时间.备用查数据
     */
    private String startDate;
   
    
	/**
     * 状态，已结束，抢购中，即将开始
     */
    private String states;
    
    /**
     * 是否选中,0否，1是
     */
    private String isCheck;
    
    /**
     * activityId 活动id,查数据
     */
    private String activityId;
    
    
    
	public String getIsCheck() {
		return isCheck;
	}




	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}

    /**
     * 时间显示
     */
    private String minuteSecond;

	public String getMinuteSecond() {
		return minuteSecond;
	}




	public void setMinuteSecond(String minuteSecond) {
		this.minuteSecond = minuteSecond;
	}




	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
    
}

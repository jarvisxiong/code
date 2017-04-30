package com.ffzx.promotion.model;


import java.util.Date;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;



 /**
 * @Description: 抢购活动类目列表 App列表外围
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年8月24日 上午11:17:22
 * @version V1.0 
 *
 */
public class ActivitySaleAppDate{
	
	 
	/**
	 * @param startDateMillis
	 * @param endDateMillis
	 * @param minuteSecond
	 * @param prompt
	 * @param rangeDate
	 * @param rangeDateString
	 */
	 
	public ActivitySaleAppDate(String startDateMillis, String endDateMillis, String prompt,
			String rangeDate, String rangeDateString) {
		super();
		this.startDateMillis = startDateMillis;
		this.endDateMillis = endDateMillis;
		this.prompt = prompt;
		this.rangeDate = rangeDate;
		this.rangeDateString = rangeDateString;
	}
	
	/**
	 */
	 
	public ActivitySaleAppDate() {
		super();
	}
	/**
     * 开始时间毫秒数
     */
    private String startDateMillis;

    /**
     * 结束时间毫秒数
     */
    private String endDateMillis;

    /**
     * 提示
     * @return
     */
    private String prompt;
    /**
     * 距离时间，返回秒数
     * @return
     */
    private String rangeDate;
    /**
     * 距结束，距开始
     * @return
     */
    private String rangeDateString;
    /**
     * 活动状态  1即将开始 2进行中 3已结束
     * @return
     */
    private String status;
    
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartDateMillis() {
		return startDateMillis;
	}
	public void setStartDateMillis(String startDateMillis) {
		this.startDateMillis = startDateMillis;
	}
	public String getEndDateMillis() {
		return endDateMillis;
	}
	public void setEndDateMillis(String endDateMillis) {
		this.endDateMillis = endDateMillis;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public String getRangeDate() {
		return rangeDate;
	}
	public void setRangeDate(String rangeDate) {
		this.rangeDate = rangeDate;
	}
	public String getRangeDateString() {
		return rangeDateString;
	}
	public void setRangeDateString(String rangeDateString) {
		this.rangeDateString = rangeDateString;
	}

    
}

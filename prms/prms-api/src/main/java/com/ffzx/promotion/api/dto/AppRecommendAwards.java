package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

public class AppRecommendAwards extends DataEntity<AppRecommendAwards> {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = 2294530168969193024L;

	/**
	 * APP推荐有奖状态(1:已发布,0:未发布)
	 */
	private String recommendStatus;
	
	/**
	 * APP推荐有奖提示语
	 */
	private String recommendTitle;
	
	/**
	 * 引导分享页面X取值
	 */
	private String pageXValue;
	
	/**
	 * 引导分享页面规则
	 */
	private String pageRule;
	
	/**
	 * 引导分享页面Y取值
	 */
	private String pageYValue;
	
	/**
	 * 分享过程标题
	 */
	private String shareTitle;
	
	/**
	 * 分享过程描述
	 */
	private String shareDescription ;
	
	/**
	 * 分享后页面标题
	 */
	private String shareafterTitle;
	
	/**
	 * 分享后页面X取值
	 */
	private String shareafterXValue;
	
	/**
	 * 分享后页面规则
	 */
	private String shareafterRule;
	
	/**
	 * 新用户注册红包发放编码
	 */
	private String newuserGrantcode;
	
	/**
	 * 老用户反推荐红包发放编码
	 */
	private String olduserGrantcode;

	public String getRecommendStatus() {
		return recommendStatus;
	}

	public void setRecommendStatus(String recommendStatus) {
		this.recommendStatus = recommendStatus;
	}

	public String getRecommendTitle() {
		return recommendTitle;
	}

	public void setRecommendTitle(String recommendTitle) {
		this.recommendTitle = recommendTitle;
	}

	public String getPageXValue() {
		return pageXValue;
	}

	public void setPageXValue(String pageXValue) {
		this.pageXValue = pageXValue;
	}

	public String getPageRule() {
		return pageRule;
	}

	public void setPageRule(String pageRule) {
		this.pageRule = pageRule;
	}

	public String getPageYValue() {
		return pageYValue;
	}

	public void setPageYValue(String pageYValue) {
		this.pageYValue = pageYValue;
	}

	public String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public String getShareDescription() {
		return shareDescription;
	}

	public void setShareDescription(String shareDescription) {
		this.shareDescription = shareDescription;
	}

	public String getShareafterTitle() {
		return shareafterTitle;
	}

	public void setShareafterTitle(String shareafterTitle) {
		this.shareafterTitle = shareafterTitle;
	}

	public String getShareafterXValue() {
		return shareafterXValue;
	}

	public void setShareafterXValue(String shareafterXValue) {
		this.shareafterXValue = shareafterXValue;
	}

	public String getShareafterRule() {
		return shareafterRule;
	}

	public void setShareafterRule(String shareafterRule) {
		this.shareafterRule = shareafterRule;
	}

	public String getNewuserGrantcode() {
		return newuserGrantcode;
	}

	public void setNewuserGrantcode(String newuserGrantcode) {
		this.newuserGrantcode = newuserGrantcode;
	}

	public String getOlduserGrantcode() {
		return olduserGrantcode;
	}

	public void setOlduserGrantcode(String olduserGrantcode) {
		this.olduserGrantcode = olduserGrantcode;
	}
	
	
}

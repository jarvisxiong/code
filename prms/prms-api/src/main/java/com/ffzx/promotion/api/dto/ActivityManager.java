package com.ffzx.promotion.api.dto;


import java.util.Date;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;

 /**
 * @Description: 活动管理
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年6月6日 下午1:37:02
 * @version V1.0 
 *
 */
public class ActivityManager extends DataEntity<ActivityManager>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 976112678373036555L;
	 /**
     * 活动类型(抢购，预售，普通，新用户，批发）.
     */
    private ActivityTypeEnum activityType;

    /**
     * 活动编号.
     */
    private String activityNo;

    /**
     * 发布状态1:已发布,0:未发布.
     */
    private String releaseStatus;

    /**
     * 活动状态（即将开始0，进行中1，已结束2）.
     */
    private String activityStatus;

    /**
     * 活动标题.
     */
    private String title;

    /**
     * 开始时间.
     */
    private Date startDate;

    /**
     * 结束时间.
     */
    private Date endDate;

    /**
     * 操作人.
     */
    private String operator;

    /**
     * 是否显示倒计时 1:显示,0:不显示.
     */
    private String showCountDown;

    /**
     * 活动图片路径.
     */
    private String picPath;
    /**
     * .置顶序号
     */
    private Integer topSore;
    /**
     * 是否app推荐（0：否  1：是）
     */
    private String appRecommend;
    /**
     * 排除id集合
     */
    private String activityIds;

    /**
     * 查询id集合
     */
    private String activityIsIds;
    public String getActivityIsIds() {
		return activityIsIds;
	}

	public void setActivityIsIds(String activityIsIds) {
		this.activityIsIds = activityIsIds;
	}

	public String getActivityIds() {
		return activityIds;
	}

	public void setActivityIds(String activityIds) {
		this.activityIds = activityIds;
	}

	//冗余
    private String [] status;
    
    private ActivityCommodity activityCommodity;
    
    private ActivityCommoditySku activityCommoditySku;
    
    // 活动状态(冗余字段--APP大麦场活动banner接口用)
    private String actStatus;

    //冗余字段  app排序抢购，进行，即将开始自动
    private String sortdate;
    
    public String getSortdate() {
		return sortdate;
	}

	public void setSortdate(String sortdate) {
		this.sortdate = sortdate;
	}

	public String getActStatus() {
		return actStatus;
	}

	public void setActStatus(String actStatus) {
		this.actStatus = actStatus;
	}

	public ActivityCommodity getActivityCommodity() {
		return activityCommodity;
	}

	public void setActivityCommodity(ActivityCommodity activityCommodity) {
		this.activityCommodity = activityCommodity;
	}

	public ActivityCommoditySku getActivityCommoditySku() {
		return activityCommoditySku;
	}

	public void setActivityCommoditySku(ActivityCommoditySku activityCommoditySku) {
		this.activityCommoditySku = activityCommoditySku;
	}

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

	public Integer getTopSore() {
		return topSore;
	}

	public void setTopSore(Integer topSore) {
		this.topSore = topSore;
	}

	public ActivityTypeEnum getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityTypeEnum activityType) {
		this.activityType = activityType;
	}

	/**
     * 
     * {@linkplain #activityNo}
     *
     * @return the value of activity_manager.activity_no
     */
    public String getActivityNo() {
        return activityNo;
    }

    /**
     * {@linkplain #activityNo}
     * @param activityNo the value for activity_manager.activity_no
     */
    public void setActivityNo(String activityNo) {
        this.activityNo = activityNo == null ? null : activityNo.trim();
    }

    /**
     * 
     * {@linkplain #releaseStatus}
     *
     * @return the value of activity_manager.release_status
     */
    public String getReleaseStatus() {
        return releaseStatus;
    }

    /**
     * {@linkplain #releaseStatus}
     * @param releaseStatus the value for activity_manager.release_status
     */
    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus == null ? null : releaseStatus.trim();
    }

    /**
     * 
     * {@linkplain #activityStatus}
     *
     * @return the value of activity_manager.activity_status
     */
    public String getActivityStatus() {
    	if(this.startDate!=null && this.endDate!=null){
    		if(Long.valueOf(DateUtil.getTime())>this.endDate.getTime()){
    			activityStatus = Constant.STATUS_OVERDUR; //
    		}else if(Long.valueOf(DateUtil.getTime())>=this.startDate.getTime() && Long.valueOf(DateUtil.getTime())<=this.endDate.getTime()){
    			activityStatus=Constant.STATUS_EFFECT;//进行中
        	}else if(Long.valueOf(DateUtil.getTime())<this.startDate.getTime()){
        		activityStatus=Constant.STATUS_UNEFFECT;//即将开始
        	}
    	}
    	
        return activityStatus;
    }

    /**
     * {@linkplain #activityStatus}
     * @param activityStatus the value for activity_manager.activity_status
     */
    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus == null ? null : activityStatus.trim();
    }

    /**
     * 
     * {@linkplain #title}
     *
     * @return the value of activity_manager.title
     */
    public String getTitle() {
        return title;
    }

    /**
     * {@linkplain #title}
     * @param title the value for activity_manager.title
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 
     * {@linkplain #startDate}
     *
     * @return the value of activity_manager.start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * {@linkplain #startDate}
     * @param startDate the value for activity_manager.start_date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     *
     * @return the value of activity_manager.end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * {@linkplain #endDate}
     * @param endDate the value for activity_manager.end_date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 
     * {@linkplain #operator}
     *
     * @return the value of activity_manager.operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * {@linkplain #operator}
     * @param operator the value for activity_manager.operator
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * 
     * {@linkplain #showCountDown}
     *
     * @return the value of activity_manager.show_count_down
     */
    public String getShowCountDown() {
        return showCountDown;
    }

    /**
     * {@linkplain #showCountDown}
     * @param showCountDown the value for activity_manager.show_count_down
     */
    public void setShowCountDown(String showCountDown) {
        this.showCountDown = showCountDown == null ? null : showCountDown.trim();
    }

    /**
     * 
     * {@linkplain #picPath}
     *
     * @return the value of activity_manager.pic_path
     */
    public String getPicPath() {
        return picPath;
    }

    /**
     * {@linkplain #picPath}
     * @param picPath the value for activity_manager.pic_path
     */
    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }

	public String getAppRecommend() {
		return appRecommend;
	}

	public void setAppRecommend(String appRecommend) {
		this.appRecommend = appRecommend;
	}
    
}

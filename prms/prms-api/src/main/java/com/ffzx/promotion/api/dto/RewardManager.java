package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author ffzx
 * @date 2016-10-24 17:25:15
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class RewardManager extends DataEntity<RewardManager> {

    private static final long serialVersionUID = 1L;

    /**
     * 标题.
     */
    private String title;

    /**
     * 免费抽奖编号.
     */
    private String rewardNo;

    /**
     * 抽奖期号.
     */
    private String dateNo;

    /**
     * 发布状态（0：未发布 1： 已发布）.
     */
    private String sendStaus;

    /**
     * 0：即将开始 1 进行中 2 已结束/等待开奖中 3 已开奖）.
     */
    private String rewardStatus;

    /**
     * 开始时间.
     */
    private Date startDate;

    /**
     * 结束时间.
     */
    private Date endDate;

    /**
     * 开奖时间.
     */
    private Date rewardDate;

    /**
     * 操作人名字.
     */
    private String createName;

    /**
     * 市场价.
     */
    private BigDecimal price;

    /**
     * 列表展示图.
     */
    private String showImage;

    /**
     * 详情展示图.（以逗号 , 分割）
     */
    private String detailImage;
    /*
     *冗余字段 
     */
    
    //参与人次
    private Integer takeCount;
    
    //是否可以抽奖 1 是 0 否
    private String isDraw; 
    
    //是否分享过   0 否 1  是
    private String isShare;
    
    private Integer drawCount;//剩余抽奖次数
    
    private String luckNo;//幸运号
    private String luckName;//中奖人
    private List<String> drawNo;//抽奖码
    
    /*
     * 详情图片分割
     */
    private List<String> detailImageStr;
    
    /**
     * 开始时间冗余
     */
    private String startDateStartStr;
    private String startDateEndStr;
    
    /**
     * 结束时间冗余
     */
    private String endDateStartStr;
    private String endDateEndStr;
    
    /**
     * 开奖时间冗余
     */
    private String rewardDateStartStr;
    private String rewardDateEndStr;
    
    
    
    
    
    public Integer getDrawCount() {
		return drawCount;
	}

	public void setDrawCount(Integer drawCount) {
		this.drawCount = drawCount;
	}

	public List<String> getDrawNo() {
		return drawNo;
	}

	public void setDrawNo(List<String> drawNo) {
		this.drawNo = drawNo;
	}

	public String getLuckNo() {
		return luckNo;
	}

	public void setLuckNo(String luckNo) {
		this.luckNo = luckNo;
	}

	public String getLuckName() {
		return luckName;
	}

	public void setLuckName(String luckName) {
		this.luckName = luckName;
	}

	public String getIsDraw() {
		return isDraw;
	}

	public void setIsDraw(String isDraw) {
		this.isDraw = isDraw;
	}

	public String getIsShare() {
		return isShare;
	}

	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}

	public Integer getTakeCount() {
		return takeCount;
	}

	public void setTakeCount(Integer takeCount) {
		this.takeCount = takeCount;
	}

	public List<String> getDetailImageStr() {
		return detailImageStr;
	}

	public void setDetailImageStr(List<String> detailImageStr) {
		this.detailImageStr = detailImageStr;
	}

	public String getStartDateStartStr() {
		return startDateStartStr;
	}

	public void setStartDateStartStr(String startDateStartStr) {
		this.startDateStartStr = startDateStartStr;
	}

	public String getStartDateEndStr() {
		return startDateEndStr;
	}

	public void setStartDateEndStr(String startDateEndStr) {
		this.startDateEndStr = startDateEndStr;
	}

	public String getEndDateStartStr() {
		return endDateStartStr;
	}

	public void setEndDateStartStr(String endDateStartStr) {
		this.endDateStartStr = endDateStartStr;
	}

	public String getEndDateEndStr() {
		return endDateEndStr;
	}

	public void setEndDateEndStr(String endDateEndStr) {
		this.endDateEndStr = endDateEndStr;
	}

	public String getRewardDateStartStr() {
		return rewardDateStartStr;
	}

	public void setRewardDateStartStr(String rewardDateStartStr) {
		this.rewardDateStartStr = rewardDateStartStr;
	}

	public String getRewardDateEndStr() {
		return rewardDateEndStr;
	}

	public void setRewardDateEndStr(String rewardDateEndStr) {
		this.rewardDateEndStr = rewardDateEndStr;
	}

    /**
     * 
     * {@linkplain #title}
     *
     * @return the value of reward_manager.title
     */
    public String getTitle() {
        return title;
    }

    /**
     * {@linkplain #title}
     * @param title the value for reward_manager.title
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 
     * {@linkplain #rewardNo}
     *
     * @return the value of reward_manager.reward_no
     */
    public String getRewardNo() {
        return rewardNo;
    }

    /**
     * {@linkplain #rewardNo}
     * @param rewardNo the value for reward_manager.reward_no
     */
    public void setRewardNo(String rewardNo) {
        this.rewardNo = rewardNo == null ? null : rewardNo.trim();
    }

    /**
     * 
     * {@linkplain #dateNo}
     *
     * @return the value of reward_manager.date_no
     */
    public String getDateNo() {
        return dateNo;
    }

    /**
     * {@linkplain #dateNo}
     * @param dateNo the value for reward_manager.date_no
     */
    public void setDateNo(String dateNo) {
        this.dateNo = dateNo == null ? null : dateNo.trim();
    }

    /**
     * 
     * {@linkplain #sendStaus}
     *
     * @return the value of reward_manager.send_staus
     */
    public String getSendStaus() {
        return sendStaus;
    }

    /**
     * {@linkplain #sendStaus}
     * @param sendStaus the value for reward_manager.send_staus
     */
    public void setSendStaus(String sendStaus) {
        this.sendStaus = sendStaus == null ? null : sendStaus.trim();
    }

    /**
     * 
     * {@linkplain #rewardStatus}
     *
     * @return the value of reward_manager.reward_status
     */
    public String getRewardStatus() {
        return rewardStatus;
    }

    /**
     * {@linkplain #rewardStatus}
     * @param rewardStatus the value for reward_manager.reward_status
     */
    public void setRewardStatus(String rewardStatus) {
        this.rewardStatus = rewardStatus == null ? null : rewardStatus.trim();
    }

    /**
     * 
     * {@linkplain #startDate}
     *
     * @return the value of reward_manager.start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * {@linkplain #startDate}
     * @param startDate the value for reward_manager.start_date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     *
     * @return the value of reward_manager.end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * {@linkplain #endDate}
     * @param endDate the value for reward_manager.end_date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 
     * {@linkplain #rewardDate}
     *
     * @return the value of reward_manager.reward_date
     */
    public Date getRewardDate() {
        return rewardDate;
    }

    /**
     * {@linkplain #rewardDate}
     * @param rewardDate the value for reward_manager.reward_date
     */
    public void setRewardDate(Date rewardDate) {
        this.rewardDate = rewardDate;
    }

    /**
     * 
     * {@linkplain #createName}
     *
     * @return the value of reward_manager.create_name
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * {@linkplain #createName}
     * @param createName the value for reward_manager.create_name
     */
    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    /**
     * 
     * {@linkplain #price}
     *
     * @return the value of reward_manager.price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * {@linkplain #price}
     * @param price the value for reward_manager.price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 
     * {@linkplain #showImage}
     *
     * @return the value of reward_manager.show_image
     */
    public String getShowImage() {
        return showImage;
    }

    /**
     * {@linkplain #showImage}
     * @param showImage the value for reward_manager.show_image
     */
    public void setShowImage(String showImage) {
        this.showImage = showImage == null ? null : showImage.trim();
    }

    /**
     * 
     * {@linkplain #detailImage}
     *
     * @return the value of reward_manager.detail_image
     */
    public String getDetailImage() {
        return detailImage;
    }

    /**
     * {@linkplain #detailImage}
     * @param detailImage the value for reward_manager.detail_image
     */
    public void setDetailImage(String detailImage) {
        this.detailImage = detailImage == null ? null : detailImage.trim();
    }
}
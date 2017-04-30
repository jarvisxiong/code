package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * 
 * @author ffzx
 * @date 2016-10-25 17:37:18
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class JoinRecord extends DataEntity<JoinRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 参与人手机号.
     */
    private String phone;

    /**
     * 头像.
     */
    private String headImage;

    /**
     * 奖券/抽奖码.
     */
    private String ticket;

    /**
     * 抽奖管理编号.
     */
    private String rewardNo;

    /**
     * 外键ID（免费抽奖ID）.
     */
    private String rewardId;

    /**
     * 分享的用户ID.
     */
    private String memberId;

    /**
     * 0 未中奖 1 中奖.
     */
    private String isBond;
    
    /*
     * 冗余
     */
    private String sumTime;//时分秒毫秒组合值
    
    

    public String getSumTime() {
		return sumTime;
	}

	public void setSumTime(String sumTime) {
		this.sumTime = sumTime;
	}

	/**
     * 
     * {@linkplain #phone}
     *
     * @return the value of join_record.phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * {@linkplain #phone}
     * @param phone the value for join_record.phone
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 
     * {@linkplain #headImage}
     *
     * @return the value of join_record.head_image
     */
    public String getHeadImage() {
        return headImage;
    }

    /**
     * {@linkplain #headImage}
     * @param headImage the value for join_record.head_image
     */
    public void setHeadImage(String headImage) {
        this.headImage = headImage == null ? null : headImage.trim();
    }

    /**
     * 
     * {@linkplain #ticket}
     *
     * @return the value of join_record.ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * {@linkplain #ticket}
     * @param ticket the value for join_record.ticket
     */
    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

    /**
     * 
     * {@linkplain #rewardNo}
     *
     * @return the value of join_record.reward_no
     */
    public String getRewardNo() {
        return rewardNo;
    }

    /**
     * {@linkplain #rewardNo}
     * @param rewardNo the value for join_record.reward_no
     */
    public void setRewardNo(String rewardNo) {
        this.rewardNo = rewardNo == null ? null : rewardNo.trim();
    }

    /**
     * 
     * {@linkplain #rewardId}
     *
     * @return the value of join_record.reward_id
     */
    public String getRewardId() {
        return rewardId;
    }

    /**
     * {@linkplain #rewardId}
     * @param rewardId the value for join_record.reward_id
     */
    public void setRewardId(String rewardId) {
        this.rewardId = rewardId == null ? null : rewardId.trim();
    }

    /**
     * 
     * {@linkplain #memberId}
     *
     * @return the value of join_record.member_id
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * {@linkplain #memberId}
     * @param memberId the value for join_record.member_id
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * 
     * {@linkplain #isBond}
     *
     * @return the value of join_record.is_bond
     */
    public String getIsBond() {
        return isBond;
    }

    /**
     * {@linkplain #isBond}
     * @param isBond the value for join_record.is_bond
     */
    public void setIsBond(String isBond) {
        this.isBond = isBond == null ? null : isBond.trim();
    }
}
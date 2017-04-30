package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * 
 * @author ffzx
 * @date 2016-10-24 11:24:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class RewardLuckNo extends DataEntity<RewardLuckNo> {

    private static final long serialVersionUID = 1L;

    /**
     * 幸运号.
     */
    private String luckNo;

    /**
     * 外键ID（抽奖管理ID）.
     */
    private String rewardId;

    /**
     * 参与人次.
     */
    private Integer takeCount;

    /**
     * 数值A.
     */
    private String oneValue;

    /**
     * 数值B.
     */
    private String twoValue;

    /**
     * 操作人名字.
     */
    private String createName;
    
    /**
     * 免费抽奖
     */
    private RewardManager reward;
    
    /**
     * 抽奖记录
     */
    private JoinRecord record;
    
    

	public JoinRecord getRecord() {
		return record;
	}

	public void setRecord(JoinRecord record) {
		this.record = record;
	}

	public RewardManager getReward() {
		return reward;
	}

	public void setReward(RewardManager reward) {
		this.reward = reward;
	}

	/**
     * 
     * {@linkplain #luckNo}
     *
     * @return the value of reward_luck_no.luck_no
     */
    public String getLuckNo() {
        return luckNo;
    }

    /**
     * {@linkplain #luckNo}
     * @param luckNo the value for reward_luck_no.luck_no
     */
    public void setLuckNo(String luckNo) {
        this.luckNo = luckNo == null ? null : luckNo.trim();
    }

    /**
     * 
     * {@linkplain #rewardId}
     *
     * @return the value of reward_luck_no.reward_id
     */
    public String getRewardId() {
        return rewardId;
    }

    /**
     * {@linkplain #rewardId}
     * @param rewardId the value for reward_luck_no.reward_id
     */
    public void setRewardId(String rewardId) {
        this.rewardId = rewardId == null ? null : rewardId.trim();
    }

    /**
     * 
     * {@linkplain #takeCount}
     *
     * @return the value of reward_luck_no.take_count
     */
    public Integer getTakeCount() {
        return takeCount;
    }

    /**
     * {@linkplain #takeCount}
     * @param takeCount the value for reward_luck_no.take_count
     */
    public void setTakeCount(Integer takeCount) {
        this.takeCount = takeCount;
    }

    /**
     * 
     * {@linkplain #oneValue}
     *
     * @return the value of reward_luck_no.one_value
     */
    public String getOneValue() {
        return oneValue;
    }

    /**
     * {@linkplain #oneValue}
     * @param oneValue the value for reward_luck_no.one_value
     */
    public void setOneValue(String oneValue) {
        this.oneValue = oneValue == null ? null : oneValue.trim();
    }

    /**
     * 
     * {@linkplain #twoValue}
     *
     * @return the value of reward_luck_no.two_value
     */
    public String getTwoValue() {
        return twoValue;
    }

    /**
     * {@linkplain #twoValue}
     * @param twoValue the value for reward_luck_no.two_value
     */
    public void setTwoValue(String twoValue) {
        this.twoValue = twoValue == null ? null : twoValue.trim();
    }

    /**
     * 
     * {@linkplain #createName}
     *
     * @return the value of reward_luck_no.create_name
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * {@linkplain #createName}
     * @param createName the value for reward_luck_no.create_name
     */
    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }
}
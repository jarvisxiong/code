package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.util.Date;

/**
 * 
 * @author ffzx
 * @date 2016-10-25 10:24:34
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class ShareDraw extends DataEntity<ShareDraw> {

    private static final long serialVersionUID = 1L;

    /**
     * 剩余可抽奖次数.
     */
    private Integer count;

    /**
     * 是否分享（0：否 1：是）.
     */
    private String isShare;

    /**
     * 免费抽奖编号.
     */
    private String rewardNo;

    /**
     * 分享之前是否抽过奖（0 ：否 1：是）.
     */
    private String isDraw;

    /**
     * 更新时间.
     */
    private Date updateDate;

    /**
     * 外键ID.
     */
    private String rewardId;

    /**
     * 分享的用户ID.
     */
    private String memberId;

    /**
     * 
     * {@linkplain #count}
     *
     * @return the value of share_draw.count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * {@linkplain #count}
     * @param count the value for share_draw.count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 
     * {@linkplain #isShare}
     *
     * @return the value of share_draw.is_share
     */
    public String getIsShare() {
        return isShare;
    }

    /**
     * {@linkplain #isShare}
     * @param isShare the value for share_draw.is_share
     */
    public void setIsShare(String isShare) {
        this.isShare = isShare == null ? null : isShare.trim();
    }

    /**
     * 
     * {@linkplain #rewardNo}
     *
     * @return the value of share_draw.reward_no
     */
    public String getRewardNo() {
        return rewardNo;
    }

    /**
     * {@linkplain #rewardNo}
     * @param rewardNo the value for share_draw.reward_no
     */
    public void setRewardNo(String rewardNo) {
        this.rewardNo = rewardNo == null ? null : rewardNo.trim();
    }

    /**
     * 
     * {@linkplain #isDraw}
     *
     * @return the value of share_draw.is_draw
     */
    public String getIsDraw() {
        return isDraw;
    }

    /**
     * {@linkplain #isDraw}
     * @param isDraw the value for share_draw.is_draw
     */
    public void setIsDraw(String isDraw) {
        this.isDraw = isDraw == null ? null : isDraw.trim();
    }

    /**
     * 
     * {@linkplain #updateDate}
     *
     * @return the value of share_draw.update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * {@linkplain #updateDate}
     * @param updateDate the value for share_draw.update_date
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 
     * {@linkplain #rewardId}
     *
     * @return the value of share_draw.reward_id
     */
    public String getRewardId() {
        return rewardId;
    }

    /**
     * {@linkplain #rewardId}
     * @param rewardId the value for share_draw.reward_id
     */
    public void setRewardId(String rewardId) {
        this.rewardId = rewardId == null ? null : rewardId.trim();
    }

    /**
     * 
     * {@linkplain #memberId}
     *
     * @return the value of share_draw.member_id
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * {@linkplain #memberId}
     * @param memberId the value for share_draw.member_id
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }
}
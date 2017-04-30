package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.util.Date;

/**
 * 
 * @author ffzx
 * @date 2016-10-24 11:24:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class ShareOrder extends DataEntity<ShareOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 抽奖期号.
     */
    private String rewardDateNo;

    /**
     * 中奖人.
     */
    private String luckName;

    /**
     * 领奖地址.
     */
    private String receiveAddress;

    /**
     * 操作人名字.
     */
    private String createName;

    /**
     * 操作时间.
     */
    private Date updateDate;

    /**
     * 外键ID 抽奖管理ID.
     */
    private String rewardId;

    /**
     * 晒单图片.
     */
    private String showImage;

    /**
     * 
     * {@linkplain #rewardDateNo}
     *
     * @return the value of share_order.reward_date_no
     */
    public String getRewardDateNo() {
        return rewardDateNo;
    }

    /**
     * {@linkplain #rewardDateNo}
     * @param rewardDateNo the value for share_order.reward_date_no
     */
    public void setRewardDateNo(String rewardDateNo) {
        this.rewardDateNo = rewardDateNo == null ? null : rewardDateNo.trim();
    }

    /**
     * 
     * {@linkplain #luckName}
     *
     * @return the value of share_order.luck_name
     */
    public String getLuckName() {
        return luckName;
    }

    /**
     * {@linkplain #luckName}
     * @param luckName the value for share_order.luck_name
     */
    public void setLuckName(String luckName) {
        this.luckName = luckName == null ? null : luckName.trim();
    }

    /**
     * 
     * {@linkplain #receiveAddress}
     *
     * @return the value of share_order.receive_address
     */
    public String getReceiveAddress() {
        return receiveAddress;
    }

    /**
     * {@linkplain #receiveAddress}
     * @param receiveAddress the value for share_order.receive_address
     */
    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress == null ? null : receiveAddress.trim();
    }

    /**
     * 
     * {@linkplain #createName}
     *
     * @return the value of share_order.create_name
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * {@linkplain #createName}
     * @param createName the value for share_order.create_name
     */
    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    /**
     * 
     * {@linkplain #updateDate}
     *
     * @return the value of share_order.update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * {@linkplain #updateDate}
     * @param updateDate the value for share_order.update_date
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 
     * {@linkplain #rewardId}
     *
     * @return the value of share_order.reward_id
     */
    public String getRewardId() {
        return rewardId;
    }

    /**
     * {@linkplain #rewardId}
     * @param rewardId the value for share_order.reward_id
     */
    public void setRewardId(String rewardId) {
        this.rewardId = rewardId == null ? null : rewardId.trim();
    }

    /**
     * 
     * {@linkplain #showImage}
     *
     * @return the value of share_order.show_image
     */
    public String getShowImage() {
        return showImage;
    }

    /**
     * {@linkplain #showImage}
     * @param showImage the value for share_order.show_image
     */
    public void setShowImage(String showImage) {
        this.showImage = showImage == null ? null : showImage.trim();
    }
}
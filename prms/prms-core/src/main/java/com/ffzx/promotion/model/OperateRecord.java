package com.ffzx.promotion.model;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.util.Date;

/**
 * 
 * @author ffzx
 * @date 2016-11-01 17:16:02
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class OperateRecord extends DataEntity<OperateRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 操作内容.
     */
    private String content;

    /**
     * 操作时间.
     */
    private Date recordDate;

    /**
     * 操作人.
     */
    private String recordUser;

    /**
     * 外键ID.
     */
    private String rewardId;

    /**
     * 
     * {@linkplain #content}
     *
     * @return the value of operate_record.content
     */
    public String getContent() {
        return content;
    }

    /**
     * {@linkplain #content}
     * @param content the value for operate_record.content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 
     * {@linkplain #recordDate}
     *
     * @return the value of operate_record.record_date
     */
    public Date getRecordDate() {
        return recordDate;
    }

    /**
     * {@linkplain #recordDate}
     * @param recordDate the value for operate_record.record_date
     */
    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    /**
     * 
     * {@linkplain #recordUser}
     *
     * @return the value of operate_record.record_user
     */
    public String getRecordUser() {
        return recordUser;
    }

    /**
     * {@linkplain #recordUser}
     * @param recordUser the value for operate_record.record_user
     */
    public void setRecordUser(String recordUser) {
        this.recordUser = recordUser == null ? null : recordUser.trim();
    }

    /**
     * 
     * {@linkplain #rewardId}
     *
     * @return the value of operate_record.reward_id
     */
    public String getRewardId() {
        return rewardId;
    }

    /**
     * {@linkplain #rewardId}
     * @param rewardId the value for operate_record.reward_id
     */
    public void setRewardId(String rewardId) {
        this.rewardId = rewardId == null ? null : rewardId.trim();
    }
}
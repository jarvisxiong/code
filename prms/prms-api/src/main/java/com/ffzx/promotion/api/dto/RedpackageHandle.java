package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.util.Date;

/**
 * 
 * @author ffzx
 * @date 2016-11-07 09:41:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class RedpackageHandle extends DataEntity<RedpackageHandle> {

    private static final long serialVersionUID = 1L;

    /**
     * 处理时间.
     */
    private Date handleDate;

    /**
     * 处理信息.
     */
    private String handleMessage;

    /**
     * 操作人.
     */
    private String handleName;

    /**
     * 红包策略id.
     */
    private String redpackageId;

    /**
     * 红包发放id.
     */
    private String redpackageGrantId;

    /**
     * 
     * {@linkplain #handleDate}
     *
     * @return the value of redpackage_handle.handle_date
     */
    public Date getHandleDate() {
        return handleDate;
    }

    /**
     * {@linkplain #handleDate}
     * @param handleDate the value for redpackage_handle.handle_date
     */
    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    /**
     * 
     * {@linkplain #handleMessage}
     *
     * @return the value of redpackage_handle.handle_message
     */
    public String getHandleMessage() {
        return handleMessage;
    }

    /**
     * {@linkplain #handleMessage}
     * @param handleMessage the value for redpackage_handle.handle_message
     */
    public void setHandleMessage(String handleMessage) {
        this.handleMessage = handleMessage == null ? null : handleMessage.trim();
    }

    /**
     * 
     * {@linkplain #handleName}
     *
     * @return the value of redpackage_handle.handle_name
     */
    public String getHandleName() {
        return handleName;
    }

    /**
     * {@linkplain #handleName}
     * @param handleName the value for redpackage_handle.handle_name
     */
    public void setHandleName(String handleName) {
        this.handleName = handleName == null ? null : handleName.trim();
    }

    /**
     * 
     * {@linkplain #redpackageId}
     *
     * @return the value of redpackage_handle.redpackage_id
     */
    public String getRedpackageId() {
        return redpackageId;
    }

    /**
     * {@linkplain #redpackageId}
     * @param redpackageId the value for redpackage_handle.redpackage_id
     */
    public void setRedpackageId(String redpackageId) {
        this.redpackageId = redpackageId == null ? null : redpackageId.trim();
    }

    /**
     * 
     * {@linkplain #redpackageGrantId}
     *
     * @return the value of redpackage_handle.redpackage_grant_id
     */
    public String getRedpackageGrantId() {
        return redpackageGrantId;
    }

    /**
     * {@linkplain #redpackageGrantId}
     * @param redpackageGrantId the value for redpackage_handle.redpackage_grant_id
     */
    public void setRedpackageGrantId(String redpackageGrantId) {
        this.redpackageGrantId = redpackageGrantId == null ? null : redpackageGrantId.trim();
    }
}
package com.ffzx.order.model;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * 
 * @author ffzx
 * @date 2016-05-18 19:48:18
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class AftersaleRecord extends DataEntity<AftersaleRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 操作记录描述.
     */
    private String description;

    /**
     * 创建人ID.
     */
    private String oprId;

    /**
     * 创建人姓名.
     */
    private String oprName;

    /**
     * 关联售后申请单编号.
     */
    private String applyNo;

    /**
     * 关联售后申请单ID.
     */
    private String applyId;

    /**
     * 
     * {@linkplain #description}
     *
     * @return the value of aftersale_record.description
     */
    public String getDescription() {
        return description;
    }

    /**
     * {@linkplain #description}
     * @param description the value for aftersale_record.description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 
     * {@linkplain #oprId}
     *
     * @return the value of aftersale_record.opr_id
     */
    public String getOprId() {
        return oprId;
    }

    /**
     * {@linkplain #oprId}
     * @param oprId the value for aftersale_record.opr_id
     */
    public void setOprId(String oprId) {
        this.oprId = oprId == null ? null : oprId.trim();
    }

    /**
     * 
     * {@linkplain #oprName}
     *
     * @return the value of aftersale_record.opr_name
     */
    public String getOprName() {
        return oprName;
    }

    /**
     * {@linkplain #oprName}
     * @param oprName the value for aftersale_record.opr_name
     */
    public void setOprName(String oprName) {
        this.oprName = oprName == null ? null : oprName.trim();
    }

    /**
     * 
     * {@linkplain #applyNo}
     *
     * @return the value of aftersale_record.apply_no
     */
    public String getApplyNo() {
        return applyNo;
    }

    /**
     * {@linkplain #applyNo}
     * @param applyNo the value for aftersale_record.apply_no
     */
    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo == null ? null : applyNo.trim();
    }

    /**
     * 
     * {@linkplain #applyId}
     *
     * @return the value of aftersale_record.apply_id
     */
    public String getApplyId() {
        return applyId;
    }

    /**
     * {@linkplain #applyId}
     * @param applyId the value for aftersale_record.apply_id
     */
    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }
}
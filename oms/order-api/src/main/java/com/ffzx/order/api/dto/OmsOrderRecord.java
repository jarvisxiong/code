package com.ffzx.order.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * 
 * @author ffzx
 * @date 2016-05-10 14:04:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class OmsOrderRecord extends DataEntity<OmsOrderRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 操作详情.
     */
    private String description;

    /**
     * 操作人ID.
     */
    private String oprId;

    /**
     * 操作人姓名.
     */
    private String oprName;

    /**
     * 关联订单ID.
     */
    private String orderId;

    /**
     * 关联订单编号.
     */
    private String orderNo;
    
    /*
     * 订单操作类型 0:订单操作 1：物流状态2:订单分配错误异常
     */
    private String recordType;
    
    private String outboundStatus;
    

    public String getOutboundStatus() {
		return outboundStatus;
	}

	public void setOutboundStatus(String outboundStatus) {
		this.outboundStatus = outboundStatus;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	/**
     * 
     * {@linkplain #description}
     *
     * @return the value of oms_order_record.description
     */
    public String getDescription() {
        return description;
    }

    /**
     * {@linkplain #description}
     * @param description the value for oms_order_record.description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 
     * {@linkplain #oprId}
     *
     * @return the value of oms_order_record.opr_id
     */
    public String getOprId() {
        return oprId;
    }

    /**
     * {@linkplain #oprId}
     * @param oprId the value for oms_order_record.opr_id
     */
    public void setOprId(String oprId) {
        this.oprId = oprId == null ? null : oprId.trim();
    }

    /**
     * 
     * {@linkplain #oprName}
     *
     * @return the value of oms_order_record.opr_name
     */
    public String getOprName() {
        return oprName;
    }

    /**
     * {@linkplain #oprName}
     * @param oprName the value for oms_order_record.opr_name
     */
    public void setOprName(String oprName) {
        this.oprName = oprName == null ? null : oprName.trim();
    }

    /**
     * 
     * {@linkplain #orderId}
     *
     * @return the value of oms_order_record.order_id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * {@linkplain #orderId}
     * @param orderId the value for oms_order_record.order_id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of oms_order_record.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * {@linkplain #orderNo}
     * @param orderNo the value for oms_order_record.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
}
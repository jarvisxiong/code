package com.ffzx.order.model;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.order.api.enums.PayTypeEnum;

import java.math.BigDecimal;
import java.util.Date;


 /**
 * @Description: 支付记录表
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年5月10日 下午2:06:30
 * @version V1.0 
 *
 */
public class PaymentRecord extends DataEntity<PaymentRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单支付编号.
     */
    private String payOrderNo;

    /**
     * 支付金额.
     */
    private BigDecimal amount;

    /**
     * 支付状态1:支付成功,0:支付未成功.
     */
    private String payStatus;

    /**
     * 查询第三方支付 该订单是否支付成功 次数.
     */
    private Integer checkCount;

    /**
     * 支付方式.
     */
    private PayTypeEnum payType;

    /**
     * 支付渠道.
     */
    private String payChannel;

    /**
     * 更新时间.
     */
    private Date updateDate;

    /**
     * 
     * {@linkplain #payOrderNo}
     *
     * @return the value of payment_record.pay_order_no
     */
    public String getPayOrderNo() {
        return payOrderNo;
    }

    /**
     * {@linkplain #payOrderNo}
     * @param payOrderNo the value for payment_record.pay_order_no
     */
    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo == null ? null : payOrderNo.trim();
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of payment_record.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * {@linkplain #amount}
     * @param amount the value for payment_record.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #payStatus}
     *
     * @return the value of payment_record.pay_status
     */
    public String getPayStatus() {
        return payStatus;
    }

    /**
     * {@linkplain #payStatus}
     * @param payStatus the value for payment_record.pay_status
     */
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }

    /**
     * 
     * {@linkplain #checkCount}
     *
     * @return the value of payment_record.check_count
     */
    public Integer getCheckCount() {
        return checkCount;
    }

    /**
     * {@linkplain #checkCount}
     * @param checkCount the value for payment_record.check_count
     */
    public void setCheckCount(Integer checkCount) {
        this.checkCount = checkCount;
    }

    public PayTypeEnum getPayType() {
		return payType;
	}

	public void setPayType(PayTypeEnum payType) {
		this.payType = payType;
	}

	/**
     * 
     * {@linkplain #payChannel}
     *
     * @return the value of payment_record.pay_channel
     */
    public String getPayChannel() {
        return payChannel;
    }

    /**
     * {@linkplain #payChannel}
     * @param payChannel the value for payment_record.pay_channel
     */
    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel == null ? null : payChannel.trim();
    }

    /**
     * 
     * {@linkplain #updateDate}
     *
     * @return the value of payment_record.update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * {@linkplain #updateDate}
     * @param updateDate the value for payment_record.update_date
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
package com.ffzx.order.api.dto;

import java.math.BigDecimal;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * 
 * @author ffzx
 * @date 2016-06-03 14:33:38
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class MemberSummary extends DataEntity<MemberSummary> {

    private static final long serialVersionUID = 1L;
    
    /**
     * 用户id
     */
    private String memberId;
    
    /**
     * 总支付金额
     */
    private BigDecimal totalPrice;

    /**
     * 总订单数量.
     */
    private Integer totalOrderCount;
    
    /**
     * 支付年.
     */
    private String payYear;
    /**
     * 支付月.
     */
    private String payMonth;
    
    /**
     * 支付日.
     */
    private String payDay;

	/**
	 * @return the totalPrice
	 */
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the totalOrderCount
	 */
	public Integer getTotalOrderCount() {
		return totalOrderCount;
	}

	/**
	 * @param totalOrderCount the totalOrderCount to set
	 */
	public void setTotalOrderCount(Integer totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}

	/**
	 * @return the payYear
	 */
	public String getPayYear() {
		return payYear;
	}

	/**
	 * @param payYear the payYear to set
	 */
	public void setPayYear(String payYear) {
		this.payYear = payYear;
	}

	/**
	 * @return the payMonth
	 */
	public String getPayMonth() {
		return payMonth;
	}

	/**
	 * @param payMonth the payMonth to set
	 */
	public void setPayMonth(String payMonth) {
		this.payMonth = payMonth;
	}

	/**
	 * @return the payDay
	 */
	public String getPayDay() {
		return payDay;
	}

	/**
	 * @param payDay the payDay to set
	 */
	public void setPayDay(String payDay) {
		this.payDay = payDay;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	
}
/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年1月4日 上午9:45:36
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package com.ffzx.order.api.dto;

import java.math.BigDecimal;

import com.ffzx.commerce.framework.annotation.Comment;
import com.ffzx.commerce.framework.common.persistence.DataEntity;

/***
 * 收银记录
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年1月4日 上午9:45:36
 * @version V1.0
 */
public class CollectionRecord extends DataEntity<CollectionRecord> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -202486090323286965L;
	
	@Comment("流水编号")
	private String runningCode;
	
	@Comment("服务站编号") 
	private String servicesNo;

	@Comment("服务站名称")
	
	private String servicesName;
	
	@Comment("合伙人Id")
	private String partnerId;
	
	@Comment("合伙人姓名")
	private String partnerName;
	
	@Comment("合伙人编号")
	private String partnerCode;
	
	@Comment("合伙人编号")
	private String partnerPhone;
	
	@Comment("应收金额")
	private BigDecimal receivable;
	
	@Comment("实收收金额")
	private BigDecimal actualFines;
	
	@Comment("找零")
	private BigDecimal paidChange;
	
	@Comment("标记：0未退回，1已退回")
	private  String tab="0";

	@Comment("备注")
	private String remark;

	/**
	 * @return the runningCode
	 */
	public String getRunningCode() {
		return runningCode;
	}

	/**
	 * @param runningCode the runningCode to set
	 */
	public void setRunningCode(String runningCode) {
		this.runningCode = runningCode;
	}

	/**
	 * @return the servicesNo
	 */
	public String getServicesNo() {
		return servicesNo;
	}

	/**
	 * @param servicesNo the servicesNo to set
	 */
	public void setServicesNo(String servicesNo) {
		this.servicesNo = servicesNo;
	}

	/**
	 * @return the servicesName
	 */
	public String getServicesName() {
		return servicesName;
	}

	/**
	 * @param servicesName the servicesName to set
	 */
	public void setServicesName(String servicesName) {
		this.servicesName = servicesName;
	}

	/**
	 * @return the partnerId
	 */
	public String getPartnerId() {
		return partnerId;
	}

	/**
	 * @param partnerId the partnerId to set
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * @return the partnerName
	 */
	public String getPartnerName() {
		return partnerName;
	}

	/**
	 * @param partnerName the partnerName to set
	 */
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	/**
	 * @return the partnerCode
	 */
	public String getPartnerCode() {
		return partnerCode;
	}

	/**
	 * @param partnerCode the partnerCode to set
	 */
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	/**
	 * @return the partnerPhone
	 */
	public String getPartnerPhone() {
		return partnerPhone;
	}

	/**
	 * @param partnerPhone the partnerPhone to set
	 */
	public void setPartnerPhone(String partnerPhone) {
		this.partnerPhone = partnerPhone;
	}

	/**
	 * @return the receivable
	 */
	public BigDecimal getReceivable() {
		return receivable;
	}

	/**
	 * @param receivable the receivable to set
	 */
	public void setReceivable(BigDecimal receivable) {
		this.receivable = receivable;
	}

	/**
	 * @return the actualFines
	 */
	public BigDecimal getActualFines() {
		return actualFines;
	}

	/**
	 * @param actualFines the actualFines to set
	 */
	public void setActualFines(BigDecimal actualFines) {
		this.actualFines = actualFines;
	}

	/**
	 * @return the paidChange
	 */
	public BigDecimal getPaidChange() {
		return paidChange;
	}

	/**
	 * @param paidChange the paidChange to set
	 */
	public void setPaidChange(BigDecimal paidChange) {
		this.paidChange = paidChange;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the tab
	 */
	public String getTab() {
		return tab;
	}

	/**
	 * @param tab the tab to set
	 */
	public void setTab(String tab) {
		this.tab = tab;
	}
	
	
}

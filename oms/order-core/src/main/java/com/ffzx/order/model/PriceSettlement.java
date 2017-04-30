/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年3月23日 下午3:55:14
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package com.ffzx.order.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ffzx.commerce.framework.annotation.Comment;
import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.StringUtil;

/***
 * 差价结算表
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年3月23日 下午3:55:14
 * @version V1.0
 */
public class PriceSettlement extends DataEntity<PriceSettlement> {
	
	private static final long serialVersionUID = 1063891740702616360L;

	@Comment("结算编号")
	private String psNo;
	
	@Comment("结算总数量")
	private Integer totalCount;

	/**
	 * 总销售金额.
	 * 实际销售金额（实际销售单价X购买数量）总和
	 */
	@Comment("总销售金额")
	private BigDecimal totaSaleAmount;
	
	/**
	 * 总差额.
	 * balance=sum((actSalePrice-pifaPrice)*buyNum)
	 */
	@Comment("差额")
	private BigDecimal totalBalance;

	@Comment("关联合伙人ID")
	private String partnerId;
	
	@Comment("关联合伙人编码")
	private String partnerCode;
	
	@Comment("关联合伙人名称")
	private String partnerName;
	/**
	 *  0:待审核
	 *  1:待结算
	 *  2:已结算
	 */
	@Comment("结算状态")
	private String status;
	
	@Comment("结算时间")
	private Date cutOffTime;
	
	@Comment("服务站id")
	private String serviceStationId;
	
	@Comment("服务站code")
	private String serviceStationCode;
	
	@Comment("服务站名称")
	private String serviceStationName;
	
	@Comment("打印日期")
	private Date printDate;
	@Comment("打印次数")
	private int printTimes; 
	@Comment("关联合伙人手机号")
	private String partnerPhone;
	/**
	 * 冗余字段
	 */
	private String printDateStr;
	/**
	 * 冗余字段
	 */
	private List<PriceSettlementDetail> detailList;
	/**
	 * @return the psNo
	 */
	public String getPsNo() {
		return psNo;
	}

	/**
	 * @param psNo the psNo to set
	 */
	public void setPsNo(String psNo) {
		this.psNo = psNo;
	}

	/**
	 * @return the totalCount
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the totaSaleAmount
	 */
	public BigDecimal getTotaSaleAmount() {
		return totaSaleAmount;
	}

	/**
	 * @param totaSaleAmount the totaSaleAmount to set
	 */
	public void setTotaSaleAmount(BigDecimal totaSaleAmount) {
		this.totaSaleAmount = totaSaleAmount;
	}

	/**
	 * @return the totalBalance
	 */
	public BigDecimal getTotalBalance() {
		return totalBalance;
	}

	/**
	 * @param totalBalance the totalBalance to set
	 */
	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the cutOffTime
	 */
	public Date getCutOffTime() {
		return cutOffTime;
	}

	/**
	 * @param cutOffTime the cutOffTime to set
	 */
	public void setCutOffTime(Date cutOffTime) {
		this.cutOffTime = cutOffTime;
	}

	/**
	 * @return the serviceStationId
	 */
	public String getServiceStationId() {
		return serviceStationId;
	}

	/**
	 * @param serviceStationId the serviceStationId to set
	 */
	public void setServiceStationId(String serviceStationId) {
		this.serviceStationId = serviceStationId;
	}

	/**
	 * @return the serviceStationCode
	 */
	public String getServiceStationCode() {
		return serviceStationCode;
	}

	/**
	 * @param serviceStationCode the serviceStationCode to set
	 */
	public void setServiceStationCode(String serviceStationCode) {
		this.serviceStationCode = serviceStationCode;
	}

	/**
	 * @return the serviceStationName
	 */
	public String getServiceStationName() {
		return serviceStationName;
	}

	/**
	 * @param serviceStationName the serviceStationName to set
	 */
	public void setServiceStationName(String serviceStationName) {
		this.serviceStationName = serviceStationName;
	}

	/**
	 * @return the detailList
	 */
	public List<PriceSettlementDetail> getDetailList() {
		return detailList;
	}

	/**
	 * @param detailList the detailList to set
	 */
	public void setDetailList(List<PriceSettlementDetail> detailList) {
		this.detailList = detailList;
	}
	
	
	/**
	 * @return the printDate
	 */
	public Date getPrintDate() {
		return printDate;
	}

	/**
	 * @param printDate the printDate to set
	 */
	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
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
	 * @return the printTimes
	 */
	public int getPrintTimes() {
		return printTimes;
	}

	/**
	 * @param printTimes the printTimes to set
	 */
	public void setPrintTimes(int printTimes) {
		this.printTimes = printTimes;
	}

	public String getStatusName() {
		if (StringUtil.isNotNull(this.getStatus())) {
			switch (this.getStatus()) {
			case "0":
				return "待审核";
			case "1":
				return "待结算";
			case "2":
				return "已结算";
			default:
				return "其他";
			}
		} else {
			return null;
		}
	}

	/**
	 * @return the printDateStr
	 */
	public String getPrintDateStr() {
		return DateUtil.formatDateTime(new Date());
	}

	/**
	 * @param printDateStr the printDateStr to set
	 */
	public void setPrintDateStr(String printDateStr) {
		this.printDateStr = printDateStr;
	}
	
	
}

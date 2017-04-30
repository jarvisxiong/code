/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年3月24日 下午9:07:54
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package com.ffzx.order.model;

import java.util.Date;

/***
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年3月24日 下午9:07:54
 * @version V1.0
 */
public class PriceSettlementDeatilQuery extends PriceSettlementDetail {

		
	/**
	 * 
	 */
	private static final long serialVersionUID = 8873016814300533630L;

	private String createDateStart;
	
	private String createDateEnd;
	
	private String cutOffTimeStart;
	
	private String cutOffTimeEnd;
	
	private String serviceStationNameLike;	
	
	private String partnerNameLike;

	private String psNoLike;
	
	private String partnerCodeLike;
	
	
	
	/**
	 * @return the createDateStart
	 */
	public String getCreateDateStart() {
		return createDateStart;
	}

	/**
	 * @param createDateStart the createDateStart to set
	 */
	public void setCreateDateStart(String createDateStart) {
		this.createDateStart = createDateStart;
	}

	/**
	 * @return the createDateEnd
	 */
	public String getCreateDateEnd() {
		return createDateEnd;
	}

	/**
	 * @param createDateEnd the createDateEnd to set
	 */
	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	/**
	 * @return the cutOffTimeStart
	 */
	public String getCutOffTimeStart() {
		return cutOffTimeStart;
	}

	/**
	 * @param cutOffTimeStart the cutOffTimeStart to set
	 */
	public void setCutOffTimeStart(String cutOffTimeStart) {
		this.cutOffTimeStart = cutOffTimeStart;
	}

	/**
	 * @return the cutOffTimeEnd
	 */
	public String getCutOffTimeEnd() {
		return cutOffTimeEnd;
	}

	/**
	 * @param cutOffTimeEnd the cutOffTimeEnd to set
	 */
	public void setCutOffTimeEnd(String cutOffTimeEnd) {
		this.cutOffTimeEnd = cutOffTimeEnd;
	}

	/**
	 * @return the serviceStationNameLike
	 */
	public String getServiceStationNameLike() {
		return serviceStationNameLike;
	}

	/**
	 * @param serviceStationNameLike the serviceStationNameLike to set
	 */
	public void setServiceStationNameLike(String serviceStationNameLike) {
		this.serviceStationNameLike = serviceStationNameLike;
	}

	/**
	 * @return the partnerNameLike
	 */
	public String getPartnerNameLike() {
		return partnerNameLike;
	}

	/**
	 * @param partnerNameLike the partnerNameLike to set
	 */
	public void setPartnerNameLike(String partnerNameLike) {
		this.partnerNameLike = partnerNameLike;
	}

	/**
	 * @return the psNoLike
	 */
	public String getPsNoLike() {
		return psNoLike;
	}

	/**
	 * @param psNoLike the psNoLike to set
	 */
	public void setPsNoLike(String psNoLike) {
		this.psNoLike = psNoLike;
	}

	/**
	 * @return the partnerCodeLike
	 */
	public String getPartnerCodeLike() {
		return partnerCodeLike;
	}

	/**
	 * @param partnerCodeLike the partnerCodeLike to set
	 */
	public void setPartnerCodeLike(String partnerCodeLike) {
		this.partnerCodeLike = partnerCodeLike;
	}
	
	
}

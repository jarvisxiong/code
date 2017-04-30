package com.ffzx.permission.model;

import java.io.Serializable;

/**
 * SysUserAddress
 * 
 * @author CMM
 *
 * 2016年9月26日 下午5:03:41
 */
public class SysUserAddress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String userId;
	
	private String addressId;
	
	private String addressCode;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the addressId
	 */
	public String getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return the addressCode
	 */
	public String getAddressCode() {
		return addressCode;
	}

	/**
	 * @param addressCode the addressCode to set
	 */
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

}

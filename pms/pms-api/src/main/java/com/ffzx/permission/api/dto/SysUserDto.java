package com.ffzx.permission.api.dto;

import java.io.Serializable;

/**
 * SysUserDto
 * 
 * @author CMM
 *
 * 2016年8月29日 下午5:41:22
 */
public class SysUserDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	private String oldWorkNo;		// 工号
	
	private String workNo;		// 工号
	
	private String name;	// 姓名
	
	private String mobile;	// 手机
	
	private String officeId;  //归属部门
	
	private String companyId;  //归属公司
	
	private String email;	// 邮箱
	
	private String phone;	// 电话

	/**
	 * @return the workNo
	 */
	public String getWorkNo() {
		return workNo;
	}

	/**
	 * @param workNo the workNo to set
	 */
	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the officeId
	 */
	public String getOfficeId() {
		return officeId;
	}

	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the oldWorkNo
	 */
	public String getOldWorkNo() {
		return oldWorkNo;
	}

	/**
	 * @param oldWorkNo the oldWorkNo to set
	 */
	public void setOldWorkNo(String oldWorkNo) {
		this.oldWorkNo = oldWorkNo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

}

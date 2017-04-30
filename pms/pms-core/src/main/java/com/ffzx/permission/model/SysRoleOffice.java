package com.ffzx.permission.model;

import java.io.Serializable;

/**
 * SysRoleOffice
 * 
 * @author CMM
 *
 * 2016年3月5日 上午10:49:52
 */
public class SysRoleOffice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ID
	 */
	private String id;
	
	/**
     * 角色编号.
     */
    private String roleId;

    /**
     * 机构编号.
     */
    private String officeId;
    
    public SysRoleOffice(){}
    public SysRoleOffice(String roleId, String officeId){
    	this.roleId = roleId;
    	this.officeId = officeId;
    }
    public SysRoleOffice(String id, String roleId, String officeId){
    	this.id = id;
    	this.roleId = roleId;
    	this.officeId = officeId;
    }

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
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

}

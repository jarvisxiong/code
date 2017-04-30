package com.ffzx.permission.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.ffzx.commerce.framework.common.persistence.TreeEntity;

/**
 * 用户组
 * 
 * @author liujunjun
 * @date 2016年3月5日 上午10:12:55
 * @version 1.0
 */
public class SysUserGroup extends TreeEntity<SysUserGroup> {

    private static final long serialVersionUID = 1L;
    
    /**
     * 编码.
     */
	@Length(min=1, max=2000)
    private String code;

    /**
     * 可以放到数据字典管理,存入编码.
     */
    private String type;
    
    /**
     * 子用户组
     */
	private List<SysUserGroup> sub;
	
	/**
	 * 角色ID
	 */
	private String roleId;

	public SysUserGroup(){
		super();
	}
	
	public SysUserGroup(String id){
		super(id);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public SysUserGroup getParent() {
		return parent;
	}

	@Override
	public void setParent(SysUserGroup parent) {
		this.parent = parent;
		
	}

	public List<SysUserGroup> getSub() {
		if(sub == null){
			sub = new ArrayList<SysUserGroup>();
		}
		return sub;
	}

	public void setSub(List<SysUserGroup> sub) {
		this.sub = sub;
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
    
}
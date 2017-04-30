package com.ffzx.permission.model;

import java.io.Serializable;

/**
 * 
 * @author Generator
 * @date 2016-03-05 10:09:55
 * @version 1.0.0
 * @copyright facegarden.com
 */
public class SysUserGroupRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一序列号.
     */
    private String id;

    /**
     * 用户组id.
     */
    private String userGroupId;

    /**
     * 角色id.
     */
    private String roleId;
    
    public SysUserGroupRole(){}
    public SysUserGroupRole(String id, String userGroupId, String roleId){
    	this.id = id;
    	this.userGroupId = userGroupId;
    	this.roleId = roleId;
    }

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of sys_user_group_role.id
     */
    public String getId() {
        return id;
    }

    /**
     * {@linkplain #id}
     * @param id the value for sys_user_group_role.id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 
     * {@linkplain #userGroupId}
     *
     * @return the value of sys_user_group_role.user_group_id
     */
    public String getUserGroupId() {
        return userGroupId;
    }

    /**
     * {@linkplain #userGroupId}
     * @param userGroupId the value for sys_user_group_role.user_group_id
     */
    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId == null ? null : userGroupId.trim();
    }

    /**
     * 
     * {@linkplain #roleId}
     *
     * @return the value of sys_user_group_role.role_id
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * {@linkplain #roleId}
     * @param roleId the value for sys_user_group_role.role_id
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}
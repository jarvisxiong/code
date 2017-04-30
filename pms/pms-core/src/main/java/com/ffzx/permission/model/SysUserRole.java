package com.ffzx.permission.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * .
     */
    private String id;

    /**
     * 用户编号.
     */
    private String userId;

    /**
     * 角色编号.
     */
    private String roleId;
    
    public SysUserRole(){}
    public SysUserRole(String id, String userId, String roleId){
    	this.id = id;
    	this.userId = userId;
    	this.roleId = roleId;
    }

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of t_user_role.id
     */
    public String getId() {
        return id;
    }

    /**
     * {@linkplain #id}
     * @param id the value for t_user_role.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #userId}
     *
     * @return the value of t_user_role.user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * {@linkplain #userId}
     * @param userId the value for t_user_role.user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * {@linkplain #roleId}
     *
     * @return the value of t_user_role.role_id
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * {@linkplain #roleId}
     * @param roleId the value for t_user_role.role_id
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
package com.ffzx.permission.model;

import java.io.Serializable;

/**
 * 
 * @author Generator
 * @date 2016-03-05 10:09:55
 * @version 1.0.0
 * @copyright facegarden.com
 */
public class SysUserGroupUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一序列号.
     */
    private String id;

    /**
     * 用户id.
     */
    private String userId;

    /**
     * 用户组id.
     */
    private String userGroupId;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of sys_user_group_user.id
     */
    public String getId() {
        return id;
    }

    /**
     * {@linkplain #id}
     * @param id the value for sys_user_group_user.id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 
     * {@linkplain #userId}
     *
     * @return the value of sys_user_group_user.user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * {@linkplain #userId}
     * @param userId the value for sys_user_group_user.user_id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 
     * {@linkplain #userGroupId}
     *
     * @return the value of sys_user_group_user.user_group_id
     */
    public String getUserGroupId() {
        return userGroupId;
    }

    /**
     * {@linkplain #userGroupId}
     * @param userGroupId the value for sys_user_group_user.user_group_id
     */
    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId == null ? null : userGroupId.trim();
    }
}
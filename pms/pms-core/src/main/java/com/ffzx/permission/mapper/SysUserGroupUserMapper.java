package com.ffzx.permission.mapper;

import java.util.List;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.permission.model.SysUserGroupUser;

/**
 * sys_user_group_user数据库操作接口
 * 
 * @author Generator
 * @date 2016-03-05 10:09:55
 * @version 1.0.0
 * @copyright facegarden.com
 */
@MyBatisDao
public interface SysUserGroupUserMapper extends CrudMapper {
	/**
	 * 删除用户组关联的用户
	 * @param id 用户组ID
	 */
	public void deleteUserGroupAllControlUserList(String id);

	/**
	 * 插入用户组关联的用户
	 * @param userRoleList
	 */
	public void insertUserGroupControlUser(List<SysUserGroupUser> userGroupUserList);	

	/**
	 * 删除用户组关联的用户
	 * @param UserId 用户ID
	 */
	public void deleteUserGroupAllControlUserListByUserId(String userId);
}
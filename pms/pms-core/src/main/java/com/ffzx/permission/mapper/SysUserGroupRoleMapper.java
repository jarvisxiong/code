package com.ffzx.permission.mapper;

import java.util.List;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.permission.model.SysUserGroupRole;

/**
 * sys_user_group_role数据库操作接口
 * 
 * @author Generator
 * @date 2016-03-05 10:09:55
 * @version 1.0.0
 * @copyright facegarden.com
 */
@MyBatisDao
public interface SysUserGroupRoleMapper extends CrudMapper {

	/**
	 * 删除用户组关联的角色
	 * @param userId
	 */
	public void deleteUserGroupAllControlRoleList(String id);

	/**
	 * 插入用户组关联的角色
	 * @param userRoleList
	 */
	public void insertUserGroupControlRole(List<SysUserGroupRole> userRoleList);
}
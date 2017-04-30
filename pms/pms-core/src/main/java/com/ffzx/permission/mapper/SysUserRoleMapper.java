package com.ffzx.permission.mapper;

import java.util.List;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.permission.model.SysUserRole;

/**
 * t_user_role数据库操作接口
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
@MyBatisDao
public interface SysUserRoleMapper extends CrudMapper {

	/**
	 * 删除用户关联的角色
	 * @param userId
	 */
	public void deleteUserAllControlRoleList(String userId);

	/**
	 * 插入用户关联的角色
	 * @param userRoleList
	 */
	public void insertUserControlRole(List<SysUserRole> userRoleList);
}
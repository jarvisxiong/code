package com.ffzx.permission.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.permission.model.SysUserGroup;

/**
 * sys_user_group数据库操作接口
 * 
 * @author Generator
 * @date 2016-03-05 10:09:55
 * @version 1.0.0
 * @copyright facegarden.com
 */
public interface SysUserGroupService extends BaseCrudService {

	/**
	 * 保存用户组
	 * @param userGroup
	 * @param userId
	 * @param roleIds   分配的角色  允许为null
	 * @param userIds   分配的用户  允许为null
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode save(SysUserGroup userGroup, String userId, String roleIds, String userIds) throws ServiceException;

	/**
	 * 获取所有机构
	 */
	public List<Object> getUserGroupList(Map<String, Object> params) throws ServiceException;

	/**
	 * 机构treetable返回
	 * 
	 * @param id
	 */
	public List<Object> getOfficeTreeTable(Map<String, Object> params, String id) throws ServiceException;
	

	/**
	 * 删除用户组
	 * @param userGroup
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode delete(SysUserGroup userGroup, String userId) throws ServiceException;	
	
	/**
	 * 根据角色查询用户组
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<SysUserGroup> getUserGroupListByRole(String roleId) throws ServiceException;
	
	/**
	 * 查询用户组，并标识用户关联
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public List<SysUserGroup> selectAllUserByUserGroupId(String userId) throws ServiceException;
	
	/**
	 * 根据角色查询用户组(所有的用户组)
	 */
	public List<Object> getAllUserGroupListByRole(String roleId) throws ServiceException;
}
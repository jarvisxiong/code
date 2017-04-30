package com.ffzx.permission.service;

import java.util.List;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.commerce.framework.system.entity.SysRole;
import com.ffzx.commerce.framework.system.entity.SysUser;

/**
 * sys_role数据库操作接口
 * 
 * @author Generator
 * @date 2016-03-03 18:00:10
 * @version 1.0.0
 * @copyright facegarden.com
 */
public interface SysRoleService extends BaseCrudService {
	
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param sysRole
	 * @return
	 * @throws ServiceException
	 */
	public List<SysRole> findList(Page page, String orderByField, String orderBy, SysRole sysRole) throws ServiceException;
	
	/**
	 * 编辑或新增角色
	 * 
	 * @param sysRole
	 * @param roleMenuIds
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode save(SysRole sysRole, String roleMenuIds, String roleOfficeIds) throws ServiceException;
	
	/**
	 * 逻辑删除
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode delete(SysRole sysRole) throws ServiceException;
	
	/**
	 * 获取用户关联的所有角色
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public List<SysRole> selectAllRoleByUserId(String userId) throws ServiceException;
	
	/**
	 * 删除角色用户
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode deleteUser(String roleId, String userId) throws ServiceException;
	
	/**
	 * 保存角色用户
	 * @param roleId
	 * @param userIds
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode saveUser(String roleId, String[] userIds) throws ServiceException;

	/**
	 * 获取所有角色,标识用户组关联
	 * 
	 * @param UserGroupId
	 * @return
	 * @throws ServiceException
	 */
	public List<SysRole> selectAllRoleByUserGroupId(String UserGroupId) throws ServiceException;
	
	/**
	 * 删除角色用户组
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode deleteUserGroup(String roleId, String userGroupId) throws ServiceException;
	
	/**
	 * 保存角色用户组
	 * @param roleId
	 * @param userIds
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode saveUserGroup(String roleId, String userGroupIds) throws ServiceException;
	
	/**
	 * 获取用户关联的所有角色(包括用组中的角色)
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public List<SysRole> selectAllRole(String userId) throws ServiceException;
	
	/**
	 * 查询所有机构树形 并通过RoleId标识已赋予的机构
	 * 
	 * @param roleId
	 * @return
	 * @throws ServiceException
	 */
	public List<Object> selectAllOfficeByRoleId(String roleId) throws ServiceException;

}
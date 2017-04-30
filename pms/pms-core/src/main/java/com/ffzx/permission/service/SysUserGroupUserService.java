package com.ffzx.permission.service;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * sys_user_group_user数据库操作接口
 * 
 * @author Generator
 * @date 2016-03-05 10:09:55
 * @version 1.0.0
 * @copyright facegarden.com
 */
public interface SysUserGroupUserService extends BaseCrudService {

	/**
	 * 为用户组分配用户
	 * @param id
	 * @param roleIdStr
	 * @throws ServiceException
	 */
	public void authorizationUserGroupUser(String id, String roleIdStr) throws ServiceException;
	
	/**
	 * 为用户分配用户组
	 * @param id
	 * @param roleIdStr
	 * @throws ServiceException
	 */
	public void authorizationUserGroupUserByUserId(String id, String roleIdStr) throws ServiceException;
	
	/**
	 * 通过userIdStr新增用户与用户组关系
	 * @param id
	 * @param userIdStr
	 * @throws ServiceException
	 */
	public void assignUserAdd(String id, String userIdStr) throws ServiceException;
	/**
	 * 通过userIdStr删除用户与用户组关系
	 * @param id
	 * @param userIdStr
	 * @throws ServiceException
	 */
	public void assignUserRemove(String id, String userIdStr) throws ServiceException;
}
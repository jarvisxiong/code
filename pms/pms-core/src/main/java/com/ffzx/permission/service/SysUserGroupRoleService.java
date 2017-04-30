package com.ffzx.permission.service;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * sys_user_group_role数据库操作接口
 * 
 * @author Generator
 * @date 2016-03-05 10:09:55
 * @version 1.0.0
 * @copyright facegarden.com
 */
public interface SysUserGroupRoleService extends BaseCrudService {

	/**
	 * 分配用户角色
	 * @param userId
	 * @param roleIdStr
	 */
	void authorizationUserGroupRole(String userId,  String roleIdStr) throws ServiceException;
}
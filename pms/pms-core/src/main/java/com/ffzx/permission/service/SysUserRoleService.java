package com.ffzx.permission.service;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * t_user_role数据库操作接口
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
public interface SysUserRoleService extends BaseCrudService {
	
	/**
	 * 分配用户角色
	 * @param id
	 * @param String roleIdStr
	 */
	void authorizationUserRole(String id, String roleIdStr) throws ServiceException;
}
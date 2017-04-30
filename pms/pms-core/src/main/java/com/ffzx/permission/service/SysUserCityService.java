package com.ffzx.permission.service;

import java.util.List;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * t_user_city数据库操作接口
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
public interface SysUserCityService extends BaseCrudService {

	/**
	 * 分配用户城市
	 * @param userId
	 * @param cityList
	 */
	void authorizationUserCity(String userId, List<String> cityIds) throws ServiceException;
}
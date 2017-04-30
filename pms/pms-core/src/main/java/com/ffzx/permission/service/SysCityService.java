package com.ffzx.permission.service;

import java.util.List;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.permission.model.SysCity;

/**
 * t_city数据库操作接口
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
public interface SysCityService extends BaseCrudService {

	/**
	 * 根据用户id查询所有的城市
	 * @param userId
	 * @return
	 */
	List<SysCity> selectAllCityByUserId (String userId) throws ServiceException;

}
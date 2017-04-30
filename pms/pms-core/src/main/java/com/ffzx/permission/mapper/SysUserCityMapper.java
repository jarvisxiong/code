package com.ffzx.permission.mapper;

import java.util.List;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.permission.model.SysUserCity;

/**
 * t_user_city数据库操作接口
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
@MyBatisDao
public interface SysUserCityMapper extends CrudMapper {
	/**
	 * 删除用户关联的城市
	 * 
	 * @param userId
	 */
	public void deleteUserAllControlCityList(String userId);

	/**
	 * 插入用户关联的城市
	 * 
	 * @param userRoleList
	 */
	public void insertUserControlCity(List<SysUserCity> userCities);
}
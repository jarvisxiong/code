package com.ffzx.permission.mapper;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.permission.model.SysCity;

/**
 * t_city数据库操作接口
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
@MyBatisDao
public interface SysCityMapper extends CrudMapper {

	public List<SysCity> selectAllCityByUserId(Map<String, Object> params);
}
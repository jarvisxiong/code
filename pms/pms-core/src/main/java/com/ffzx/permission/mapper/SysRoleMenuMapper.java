package com.ffzx.permission.mapper;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;

/**
 * t_role_menu数据库操作接口
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
@MyBatisDao
public interface SysRoleMenuMapper extends CrudMapper {

	 public int deleteByPrimaryKey(Integer id);
}
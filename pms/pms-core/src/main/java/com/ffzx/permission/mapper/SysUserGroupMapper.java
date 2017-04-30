package com.ffzx.permission.mapper;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.permission.model.SysUserGroup;

/**
 * sys_user_group数据库操作接口
 * 
 * @author Generator
 * @date 2016-03-05 10:09:55
 * @version 1.0.0
 * @copyright facegarden.com
 */
@MyBatisDao
public interface SysUserGroupMapper extends CrudMapper {

	  /**
	   * 通过父id查询子集
	   * @param menu
	   * @return
	   */
	 public List<SysUserGroup> getByParentIdsLike(SysUserGroup userGroup)throws ServiceException;
	 
	/**
	 * 根据角色查询用户组
	 * 
	 * @param map
	 * @return
	 */
	 public List<SysUserGroup> getUserGroupListByRole(Map<String, Object> map);
 
	/**
	 * 获取所有用户组，标识用户关联
	 * 
	 * @param params
	 * @return
	 */
	public List<SysUserGroup> selectAllUserGroupByUserId(Map<String, Object> params);
	 
	/**
	 * 根据角色查询用户组(所有用户组)
	 * 
	 * @param map
	 * @return
	 */
	 public List<SysUserGroup> getAllUserGroupListByRole(Map<String, Object> map);
}
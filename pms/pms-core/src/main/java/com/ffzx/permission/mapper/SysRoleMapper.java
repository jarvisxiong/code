package com.ffzx.permission.mapper;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.system.entity.SysOffice;
import com.ffzx.commerce.framework.system.entity.SysRole;
import com.ffzx.permission.model.SysRoleMenu;
import com.ffzx.permission.model.SysRoleOffice;
import com.ffzx.permission.model.SysUserGroupRole;
import com.ffzx.permission.model.SysUserRole;

/**
 * sys_role数据库操作接口
 * 
 * @author Generator
 * @date 2016-03-03 20:50:36
 * @version 1.0.0
 * @copyright facegarden.com
 */
@MyBatisDao
public interface SysRoleMapper extends CrudMapper {
	
	/**
	 * 根据角色ID，删除对应的角色菜单
	 */
	int delAllRoleMenu(String roleId);
	
	/**
	 * 根据角色ID，删除对应的角色机构
	 * @param roleId
	 * @return
	 */
	int delAllRoleOffice(String roleId);
	
	/**
	 * 根据角色ID，删除对应的角色用户
	 */
	int delAllRoleUser(String roleId);
	
	/**
	 * 根据角色ID，删除对应的角色用户组
	 */
	int delAllRoleUserGroup(String roleId);
	
	/**
	 * 批量添加角色菜单
	 * 
	 * @param roleMenuList
	 * @return
	 */
	int batchInsertRoleMenu(List<SysRoleMenu> roleMenuList);
	
	/**
	 * 批量添加角色机构
	 * 
	 * @param roleOfficeList
	 * @return
	 */
	int batchInsertRoleOffice(List<SysRoleOffice> roleOfficeList);
	
	/**
	 * 批量添加角色用户
	 * 
	 * @param roleUserList
	 * @return
	 */
	int batchInsertRoleUser(List<SysUserRole> roleUserList);
	
	/**
	 * 批量添加角色用户组
	 * 
	 * @param roleUserList
	 * @return
	 */
	int batchInsertRoleUserGroup(List<SysUserGroupRole> roleUserList);
	
	/**
	 * 获取用户关联的所有角色
	 * 
	 * @param params
	 * @return
	 */
	List<SysRole> selectAllRoleByUserId(Map<String, Object> params);
	
	/**
	 * 删除角色用户
	 * 
	 * @param roleId
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	int deleteRoleUser(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 获取所有角色，标识用户组关联
	 * 
	 * @param params
	 * @return
	 */
	public List<SysRole> selectAllRoleByUserGroupId(Map<String, Object> params);
	
	/**
	 * 删除角色用户组
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	int deleteRoleUserGroup(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 获取用户关联的所有角色(包括用户组中的角色)
	 * 
	 * @param params
	 * @return
	 */
	List<SysRole> selectAllRole(Map<String, Object> params);
	
	/**
	 * 获取角色所有机构
	 * 
	 * @param params
	 * @return
	 */
	List<SysOffice> selectAllOfficeByRoleId(Map<String, Object> params);

}
package com.ffzx.permission.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.commerce.framework.system.entity.SysMenu;
import com.ffzx.commerce.framework.vo.SysMenuVo;

/**
 * t_menu数据库操作接口
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
public interface SysMenuService extends BaseCrudService {


	/**
	 * 通过UserID获取用户菜单
	 * 
	 * @param userId
	 * @return List<SysMenu> 树形
	 */
	public List<SysMenuVo> getMenuByUserId(String userId) throws ServiceException;
	
	/**
	 * 通过UserID获取用户权限
	 * 
	 * @param userId
	 * @return List<SysMenu>
	 */
	public List<String> getPermissionByUserId(String userId) throws ServiceException;

	/**
	 * 保存
	 * 
	 * @param sysMenu
	 *            菜单
	 * @param userId
	 *            操作用户ID
	 * @return
	 * @throws Exception
	 */
	public ServiceCode save(SysMenu sysMenu) throws Exception;

	/**
	 * 删除
	 * 
	 * @param sysMenu
	 *            菜单
	 * @param userId
	 *            操作用户ID
	 * @return
	 * @throws Exception
	 */
	public ServiceCode delete(SysMenu sysMenu) throws Exception;

	/**
	 * 查询所有权限用户树形 并通过RoleId标识已赋予的菜单
	 * 
	 * @param roleId
	 * @return
	 * @throws ServiceException
	 */
	public List<Object> selectAllMenuByRoleId(String roleId) throws ServiceException;
	
	/**
	 * 查询所有权限用户树形 并通过UserId标识已赋予的菜单
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public List<Object> selectAllMenuByUserId(String userId) throws ServiceException;

	/**
	 * 根据菜单id查询子集
	 * 
	 * @param menuId
	 * @return
	 */
	public List<SysMenu> selectSubMenuList(String menuId);

	/**
	 * 查询所有菜单主要用于树形显示List<Object>
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<Object> getMenuList(Map<String, Object> params) throws ServiceException;

	/**
	 * 查询菜单用于页面treetable显示
	 * 
	 * @param id
	 *            菜单id，treetable显示数据为菜单id下的所有数据
	 * @return
	 * @throws ServiceException
	 */
	public List<Object> getMenuTreeTable(Map<String, Object> params, String id) throws ServiceException;
}
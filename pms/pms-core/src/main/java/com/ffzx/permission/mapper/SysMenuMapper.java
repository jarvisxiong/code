package com.ffzx.permission.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.system.entity.SysMenu;

/**
 * t_menu数据库操作接口
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
@MyBatisDao
public interface SysMenuMapper extends CrudMapper {

	public int deleteByPrimaryKey(Integer id);
	
	/**
	 * 根据用户查询菜单
	 * 
	 * @param map
	 * @return
	 */
	List<SysMenu> selectMenuByUserId(@Param("params") Map<String, Object> map);
	
	
	/**
	 * 根据用户获取所有权限
	 * @param map
	 * @return
	 */
	List<String> selectPermissionByUserId(@Param("params") Map<String, Object> map);
	
	/**
	 * 获取所有权限
	 * @param map
	 * @return
	 */
	List<String> selectPermissionAll(@Param("params") Map<String, Object> map);

	/**
	 * 根据菜单id查询子集
	 * 
	 * @param menuId
	 * @return
	 */
	List<SysMenu> selectSubMenuList(String menuId);

	/**
	 * 根据菜单id删除角色菜单
	 * 
	 * @param menuId
	 * @return
	 */
	int deleteAllRoleMenu(String menuId);

	/**
	 * 根据菜单ID删除菜单功能
	 * 
	 * @param menuId
	 * @return
	 */
	int deleteAllMenuAction(String menuId);

	/**
	 * 根据菜单Id删除菜单角色功能
	 * 
	 * @param menuId
	 * @return
	 */
	int deleteAllMenuRoleAction(String menuId);

	/**
	 * 查询父级菜单
	 * 
	 * @param menuId
	 * @return
	 */
	SysMenu selectParentMenu(String menuId);

	/**
	 * 菜单排序
	 * 
	 * @param menu
	 * @return
	 */
	int updateMenuOrder(SysMenu menu);

	/**
	 * 判断同等级是否有相同的菜单了
	 * 
	 * @param menu
	 * @param parentId
	 * @return
	 */
	int isHasMenu(SysMenu menu);

	/**
	 * 查询所有权限 并通过RoleId标识已赋予的菜单
	 * 
	 * @param map
	 */
	List<SysMenu> selectAllMenuByRoleId(Map<String, Object> map);
	
	/**
	 * 查询所有权限 并通过UserId标识已赋予的菜单
	 * 
	 * @param map
	 */
	List<SysMenu> selectAllMenuByUserId(Map<String, Object> map);

	/**
	 * 通过父id查询子集
	 * 
	 * @param menu
	 * @return
	 */
	List<SysMenu> getByParentIdsLike(SysMenu menu) throws ServiceException;

	/**
	 * 更改删除状态为删除
	 * @param menu
	 * @return
	 * @throws ServiceException
	 */
	int updateDelFlag(SysMenu menu) throws ServiceException;

}
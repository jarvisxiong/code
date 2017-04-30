package com.ffzx.permission.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.basedata.api.dto.SubSystemConfig;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.constant.RedisPrefix;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.system.entity.SysMenu;
import com.ffzx.commerce.framework.utils.BeanMapUtils;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commerce.framework.vo.SysMenuVo;
import com.ffzx.permission.api.service.consumer.SubSystemConfigApiConsumer;
import com.ffzx.permission.common.PmsServiceResultCode;
import com.ffzx.permission.mapper.SysMenuMapper;
import com.ffzx.permission.service.SysMenuService;

/**
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends BaseCrudServiceImpl implements SysMenuService {

	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Resource
	private RedisUtil redisUtil;

	@Autowired
	public SubSystemConfigApiConsumer subSystemConfigApiConsumer;

	@Override
	public CrudMapper init() {
		return sysMenuMapper;
	}

	/**
	 * 通过UserID获取用户菜单
	 * 
	 * @param userId
	 * @return List<SysMenu> 树形
	 */
	@SuppressWarnings("unchecked")
	public List<SysMenuVo> getMenuByUserId(String userId) throws ServiceException {
		List<SysMenu> menuList = null;
		List<SysMenuVo> resultList = null;

		// 从缓存中获取菜单
		resultList = (List<SysMenuVo>) redisUtil.get(RedisPrefix.SYSTEM_LOGIN + RedisPrefix.USER_MENU + userId);
		if (null != resultList) {
			return resultList;
		}
		
		// 缓存中无数据时，从数据库中查询菜单
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(Constant.MENU_ISSHOW, Constant.MENU_SHOW);
		params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);

		if (Constant.ADMIN_ID.equals(userId)) {
			menuList = this.findByBiz(params);
		} else {
			params.put("userId", userId);
			menuList = sysMenuMapper.selectMenuByUserId(params);
		}
		// 获得子系统并拼接链接
		List<SubSystemConfig> subSystemList = subSystemConfigApiConsumer.getSubSystemConfig(null);
		Map<String, Object> subSystemMap = new HashMap<String, Object>();
		for (SubSystemConfig subSystemConfig : subSystemList) {
			subSystemMap.put(subSystemConfig.getSubSystemCode(), subSystemConfig);
		}
		for (SysMenu menu : menuList) {
			if (subSystemMap.containsKey(menu.getSubSystemCode()) && StringUtil.isNotNull(menu.getHref())) {
				SubSystemConfig subSystem = (SubSystemConfig) subSystemMap.get(menu.getSubSystemCode());
				String url = "http://" + subSystem.getSubSystemDomain() + ":" + subSystem.getSubSystemPort()
						+ subSystem.getSubSystemBasePath() + menu.getHref();
				menu.setUrl(url);
			}
		}
		resultList = menuListToTree(menuList, "0");

		// 把菜单放入缓存
		if (null != resultList) {
			redisUtil.set(RedisPrefix.SYSTEM_LOGIN + RedisPrefix.USER_MENU + userId, resultList);
		}
		return resultList;
	}

	/**
	 * 通过UserID获取用户权限
	 * 
	 * @param userId
	 * @return List<SysMenu>
	 */
	public List<String> getPermissionByUserId(String userId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(Constant.MENU_ISSHOW, Constant.MENU_HIDE);
		params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
		if (Constant.ADMIN_ID.equals(userId)) {
			return sysMenuMapper.selectPermissionAll(params);
		} else {
			params.put("userId", userId);
			return sysMenuMapper.selectPermissionByUserId(params);
		}
	}

	/**
	 * 添加/修改
	 */
	@Transactional(rollbackFor = Exception.class)
	public ServiceCode save(SysMenu sysMenu) throws Exception {
		// 判断菜单是否重复
		if (isHasMenu(sysMenu) > 0) {
			return PmsServiceResultCode.PMS_MENU_10051;
		}
		int result = 0;
		SysMenu parentMenu = this.findById(sysMenu.getParent().getId());
		String parentIds = "";
		if (parentMenu == null) {
			parentIds = "0,";
		} else {
			parentIds = parentMenu.getParentIds() + sysMenu.getParent().getId() + ",";
		}
		sysMenu.setParentIds(parentIds);
		if (StringUtil.isNotNull(sysMenu.getId())) {// 修改
			SysMenu oldMenu = this.findById(sysMenu.getId());// 旧的数据
			result = sysMenuMapper.updateByPrimaryKeySelective(sysMenu);// 更新数据
			// 维护所有子集的ids
			List<SysMenu> list = getMenuListByParent(sysMenu);
			sysMenu.setParentIds(parentIds);
			if (list != null) {
				for (SysMenu e : list) {
					e.setParentIds(e.getParentIds().replace(oldMenu.getParentIds(), sysMenu.getParentIds()));
					sysMenuMapper.updateByPrimaryKeySelective(e);
				}
			}
		} else { // 添加
			sysMenu.setId(UUIDGenerator.getUUID());
			result = sysMenuMapper.insertSelective(sysMenu);
		}
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	/**
	 * 父id获取所有子集
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	private List<SysMenu> getMenuListByParent(SysMenu menu) throws ServiceException {
		menu.setParentIds("%," + menu.getId() + ",%");
		List<SysMenu> list = sysMenuMapper.getByParentIdsLike(menu);
		return list;
	}

	/**
	 * 删除
	 */
	@Transactional
	public ServiceCode delete(SysMenu sysMenu) throws Exception {
		if (getMenuListByParent(sysMenu).size() > 0) {
			return PmsServiceResultCode.PMS_MENU_10052;
		}
		SysMenu delMenu = new SysMenu(sysMenu.getId());
		delMenu.setDelFlag(Constant.DELTE_FLAG_YES);
		int result = sysMenuMapper.updateByPrimaryKeySelective(delMenu);// 更新数据
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	/**
	 * 判断该菜单在同一父级上是否存在
	 * 
	 * @param tmenu
	 * @return
	 */
	public int isHasMenu(SysMenu tmenu) {
		int result = this.sysMenuMapper.isHasMenu(tmenu);
		return result;
	}

	/**
	 * 查询所有权限 并通过RoleId标识已赋予的菜单
	 * 
	 * @param roleId
	 * @return
	 * @throws ServiceException
	 */
	public List<Object> selectAllMenuByRoleId(String roleId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
		List<SysMenu> tMenu = sysMenuMapper.selectAllMenuByRoleId(params);
		List<Object> listResult = new ArrayList<Object>();
		listResult = sysMenuToMap(tMenu, listResult);
		return listResult;
	}
	
	/**
	 * 查询所有权限 并通过UserId标识已赋予的菜单
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<Object> selectAllMenuByUserId(String userId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
		List<SysMenu> tMenu = sysMenuMapper.selectAllMenuByUserId(params);
		List<Object> listResult = new ArrayList<Object>();
		listResult = sysMenuToUserMap(tMenu, listResult);
		return listResult;
	}

	/**
	 * 根据菜单id查询子集
	 * 
	 * @param menuId
	 * @return
	 */
	public List<SysMenu> selectSubMenuList(String menuId) {
		return this.sysMenuMapper.selectSubMenuList(menuId);
	}

	/**
	 * 获取所有菜单主要用于树形显示分配权限List<Object>
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<Object> getMenuList(Map<String, Object> params) throws ServiceException {
		if (!StringUtil.isNotNull(params)) {
			params = new HashMap<String, Object>();
		}
		params.put(Constant.ORDER_BY_FIELD_PARAMS, Constant.ORDER_BY_FIELD);
		params.put(Constant.ORDER_BY_PARAMS, Constant.ORDER_BY);
		params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
		List<SysMenu> tMenu = this.findByBiz(params);
		List<Object> listResult = new ArrayList<Object>();
		listResult = sysMenuToMap(tMenu, listResult);
		return listResult;
	}

	/**
	 * 查询菜单用于页面treetable显示
	 * 
	 * @param id
	 *            菜单id，treetable显示数据为菜单id下的所有数据
	 * @return
	 * @throws ServiceException
	 */
	public List<Object> getMenuTreeTable(Map<String, Object> params, String id) throws ServiceException {
		if (!StringUtil.isNotNull(params)) {
			params = new HashMap<String, Object>();
		}
		params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
		List<SysMenu> list = new ArrayList<SysMenu>();
		List<SysMenu> listTree = new ArrayList<SysMenu>();
		List<Object> treeTable = new ArrayList<Object>();
		if (id != null) {
			SysMenu menu = this.findById(id);// 旧的数据
			list.add(menu);
			if (params.containsKey(Constant.DELTE_FLAG)) {
				menu.setDelFlag((String) params.get(Constant.DELTE_FLAG));
			} else {
				menu.setDelFlag(null);
			}
			list.addAll(getMenuListByParent(menu));
			listTree = listToTree(list, menu.getParent().getId());
		} else {
			list = this.findByBiz(params);
			listTree = listToTree(list, "0");
		}
		treeTable = sysMenuToMap(listTree, treeTable);
		return treeTable;
	}

	/**
	 * 列表转树形
	 * 
	 * @param list
	 * @param menuId
	 */
	public List<SysMenu> listToTree(List<SysMenu> list, String id) {
		List<SysMenu> rootTrees = new ArrayList<SysMenu>();
		for (SysMenu object1 : list) {
			if (object1.getParentId().equals(id)) {
				object1.getParent().setId("0");
				rootTrees.add(object1);
			}
			for (SysMenu object2 : list) {
				if (object2.getParentId().equals(object1.getId())) {
					if (object1.getSub().size() == 0) {
						List<SysMenu> myChildrens = new ArrayList<SysMenu>();
						myChildrens.add(object2);
						object1.setSub(myChildrens);
					} else {
						object1.getSub().add(object2);
					}
				}
			}
		}
		return rootTrees;
	}
	private List<SysMenuVo> menuListToTree(List<SysMenu> list, String id) {
		if(null == id || null == list){
			return null;
		}
		
		List<SysMenuVo> rootTrees = new ArrayList<SysMenuVo>();
		//一级菜单
		for (SysMenu menu : list) {
			if (id.equals(menu.getParentId())) {
				SysMenuVo menuVo = new SysMenuVo();
				menuVo.setId(menu.getId());
				menuVo.setParentId("0");
				menuVo.setName(menu.getName());
				menuVo.setHref(menu.getHref());
				menuVo.setUrl(menu.getUrl());
				rootTrees.add(menuVo);
			}
		}
		
		List<SysMenuVo> menuVoList2 = new ArrayList<SysMenuVo>();
		//二级菜单
		for (SysMenuVo menuVo1 : rootTrees) {
			for (SysMenu menu : list) {
				if (menuVo1.getId().equals(menu.getParentId())) {
					SysMenuVo menuVo2 = new SysMenuVo();
					menuVo2.setId(menu.getId());
					menuVo2.setParentId(menu.getParentId());
					menuVo2.setName(menu.getName());
					menuVo2.setHref(menu.getHref());
					menuVo2.setUrl(menu.getUrl());
					menuVo1.getSub().add(menuVo2);
					
					menuVoList2.add(menuVo2);
				}
			}
		}
		
		List<SysMenuVo> menuVoList3 = new ArrayList<SysMenuVo>();
		//三级菜单
		for (SysMenuVo menuVo2 : menuVoList2) {
			for (SysMenu menu : list) {
				if (menuVo2.getId().equals(menu.getParentId())) {
					SysMenuVo menuVo3 = new SysMenuVo();
					menuVo3.setId(menu.getId());
					menuVo3.setParentId(menu.getParentId());
					menuVo3.setName(menu.getName());
					menuVo3.setHref(menu.getHref());
					menuVo3.setUrl(menu.getUrl());
					menuVo2.getSub().add(menuVo3);
					
					menuVoList3.add(menuVo3);
				}
			}
		}
		
		//四级菜单
		for (SysMenuVo menuVo3 : menuVoList3) {
			for (SysMenu menu : list) {
				if (menuVo3.getId().equals(menu.getParentId())) {
					SysMenuVo menuVo4 = new SysMenuVo();
					menuVo4.setId(menu.getId());
					menuVo4.setParentId(menu.getParentId());
					menuVo4.setName(menu.getName());
					menuVo4.setHref(menu.getHref());
					menuVo4.setUrl(menu.getUrl());
					menuVo3.getSub().add(menuVo4);
				}
			}
		}
		return rootTrees;
	}

	/**
	 * list<SysMenu>格式转变成List<map>格式
	 * 
	 * @param subMenu
	 *            list
	 * @param listResult
	 * @return
	 */
	public List<Object> sysMenuToMap(List<SysMenu> subMenu, List<Object> listResult) {
		for (SysMenu menu : subMenu) {
			Map<String, Object> row = BeanMapUtils.toMap(menu);
			row.put("pId", menu.getParentId());
			row.put("open", true);
			if (menu.getRoleId() != null) {
				row.put("checked", true);
			}
			listResult.add(row);
			if (menu.getSub().size() > 0) {
				sysMenuToMap(menu.getSub(), listResult);
			}
		}
		return listResult;
	}
	public List<Object> sysMenuToUserMap(List<SysMenu> subMenu, List<Object> listResult) {
		for (SysMenu menu : subMenu) {
			Map<String, Object> row = BeanMapUtils.toMap(menu);
			row.put("pId", menu.getParentId());
			row.put("open", true);
			if (menu.getUserId() != null) {
				row.put("checked", true);
			}
			listResult.add(row);
			if (menu.getSub().size() > 0) {
				sysMenuToUserMap(menu.getSub(), listResult);
			}
		}
		return listResult;
	}
	
}
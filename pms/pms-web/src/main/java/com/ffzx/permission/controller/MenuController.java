/**
 * 
 */
package com.ffzx.permission.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.commerce.framework.annotation.Permission;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.system.entity.SysMenu;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.permission.api.service.consumer.SubSystemConfigApiConsumer;
import com.ffzx.permission.service.SysMenuService;

/**
 * 菜单权限的控制器层
 * 
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	public SysMenuService sysMenuService;
	
	@Autowired
	public SubSystemConfigApiConsumer subSystemConfigApiConsumer;

	/**
	 * 根据用户 获取已分配的菜单
	 * @return
	 */
	@RequestMapping(value = "/queryAllMenuByUserId", method = RequestMethod.POST)
	public void getAllMenuList(HttpServletResponse response) {
		logger.debug("MenuController-getAllMenuList=》菜单权限-已分配的菜单");
		try {
			// 获得当前用户信息
			SysUser currentLoginUser = RedisWebUtils.getLoginUser();
			this.responseWrite(response, this.getJsonByObject(sysMenuService.getMenuByUserId(currentLoginUser.getId())));
		} catch (ServiceException e) {
			logger.error("MenuController-getAllMenuList-ServiceException=》菜单权限-已分配的菜单-ServiceException", e);
		} catch (Exception e) {
			logger.error("MenuController-getAllMenuList-Exception=》菜单权限-已分配的菜单-Exception", e);
		}
	}

	/**
	 * 查看所有的菜单权限
	 * 
	 * @return
	 */
	@RequestMapping(value = "list")
	@Permission(privilege = {"pms:menu:view","pms:menu:edit"})
	public String getMenuList(ModelMap map) {
		logger.debug("MenuController-getMenuList=》菜单权限-查看所有的菜单权限");
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			/*SysUser sysUser = WebUtils.getLoginUser(this.getRequest());
			if(!sysUser.isAdmin()){
				params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
			}*/
			//掩藏的不显示
			String tree_data = "[]";
			params.put(Constant.MENU_ISSHOW, Constant.MENU_SHOW);
			List<Object> listResult = sysMenuService.getMenuList(params);
			tree_data = this.getJsonByObject(listResult);
			map.put("result", tree_data);
		} catch (ServiceException e) {
			logger.error("MenuController-getMenuList-ServiceException=》菜单权限-查看所有的菜单权限-ServiceException", e);
		} catch (Exception e) {
			logger.error("MenuController-getMenuList-Exception=》菜单权限-查看所有的菜单权限-Exception", e);
		}
		return "menu/menu_list";
	}
	
	@RequestMapping(value = "tableList")
	@Permission(privilege = {"pms:menu:view","pms:menu:edit"})
	public String tableList(String id, ModelMap map) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			// 显示所有
			List<Object> treeTable = sysMenuService.getMenuTreeTable(params,id);
			map.put("treeTable", treeTable);
		} catch (ServiceException e) {
			logger.error("MenuController-getMenuList-ServiceException=》菜单权限-查看所有的菜单权限-ServiceException", e);
		} catch (Exception e) {
			logger.error("MenuController-getMenuList-Exception=》菜单权限-查看所有的菜单权限-Exception", e);
		}
		return "menu/menu_table_list";
	}
	

	/**
	 * 查看所有的菜单权限
	 * 
	 * @return
	 */
	@RequestMapping(value = "ajaxgetMenuList")
	@ResponseBody
	public void ajaxgetMenuList(HttpServletResponse response) {
		logger.debug("MenuController-ajaxgetMenuList=》菜单权限-查看所有的菜单权限");
		try {
			// 查询所有且不分页
			Map<String, Object> params = new HashMap<String, Object>();
			List<Object> listResult = sysMenuService.getMenuList(params);
			this.getJsonByObject(listResult);
			this.responseWrite(response, this.getJsonByObject(listResult));
		} catch (ServiceException e) {
			logger.error("MenuController-ajaxgetMenuList-ServiceException=》菜单权限-查看所有的菜单权限-ServiceException", e);
		} catch (Exception e) {
			logger.error("MenuController-ajaxgetMenuList-ServiceException=》菜单权限-查看所有的菜单权限-ServiceException", e);
		}
	}

	/**
	 * 加载编辑页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "form")
	@Permission(privilege = {"pms:menu:view"})
	public String form(String id, String parentId, ModelMap map) {
		try {
			SysMenu tMenu = new SysMenu();
			if (StringUtil.isNotNull(id)) {
				tMenu.setId(id);
				tMenu = sysMenuService.findById(tMenu);
				map.put("menu", tMenu);
				parentId = tMenu.getParentId();
			}
			// 父级
			SysMenu tParentMenu = new SysMenu();
			if (StringUtil.isNotNull(parentId) && !"0".equals(parentId)) {
				tParentMenu.setId(parentId);
				tParentMenu = sysMenuService.findById(tParentMenu);
			} else {
				tParentMenu.setId("0");
				tParentMenu.setName("根菜单");
			}
			map.put("pMenu", tParentMenu);
			map.put("subSystemConfig",subSystemConfigApiConsumer.getSubSystemConfig(null));
		} catch (ServiceException e) {
			logger.error("MenuController-menuEditView-ServiceException=》功能管理-加载编辑页面-ServiceException", e);
		} catch (Exception e) {
			logger.error("MenuController-menuEditView-ServiceException=》功能管理-加载编辑页面-ServiceException", e);
		}
		return "menu/menu_form";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	@Permission(privilege = {"pms:menu:edit"})
	public ResultVo save(@Valid SysMenu sysMenu, BindingResult result) {
		ResultVo resultVo = new ResultVo();
		if (result.hasErrors()) {
			resultVo.setStatus(Constant.RESULT_VALID);
			resultVo.setInfoMap(this.getValidErrors(result.getFieldErrors()));
			return resultVo;
		}
		try {
			SysUser user = RedisWebUtils.getLoginUser();
			if (StringUtil.isNotNull(sysMenu.getId())) {// 修改
				sysMenu.setLastUpdateBy(user);
				sysMenu.setLastUpdateDate(new Date());
			}else{
				sysMenu.setCreateBy(user);
				sysMenu.setCreateDate(new Date());
			}
			ServiceCode serviceCode = sysMenuService.save(sysMenu);
			resultVo.setResult(serviceCode, "/menu/tableList.do");
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("MenuController.menuEdit() ServiceException=》系统管理-菜单管理", e);
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("MenuController.menuEdit() Exception=》系统管理-菜单管理", e);
		}
		if (resultVo.isHasException()) {
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}

	/**
	 * 删除
	 * 
	 * @param session
	 * @param menuId
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	@Permission(privilege = {"pms:menu:delete"})
	public ResultVo delete(HttpSession session, SysMenu sysMenu) {
		ResultVo resultVo = new ResultVo();
		try {
			SysUser user = RedisWebUtils.getLoginUser();
			sysMenu.setCreateBy(user);
			sysMenu.setCreateDate(new Date());
			ServiceCode serviceCode = sysMenuService.delete(sysMenu);
			resultVo.setResult(serviceCode, "/menu/tableList.do");
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("MenuController.menuEdit() ServiceException=》系统管理-菜单管理", e);
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("MenuController.menuEdit() Exception=》系统管理-菜单管理", e);
		}
		if (resultVo.isHasException()) {
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}
}

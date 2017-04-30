/**
 * 
 */
package com.ffzx.permission.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.system.entity.SysMenu;
import com.ffzx.permission.service.SysMenuService;

/**
 * 菜单权限的控制器层
 * 
 * @author longcui
 *
 */
@Controller
@RequestMapping("/systemSet")
public class SystemSetController extends BaseController {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(SystemSetController.class);

	@Autowired
	public SysMenuService sysMenuService;
	
	/**
	 * 查看所有的菜单权限
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSystemSetList", method = RequestMethod.GET)
	public ModelAndView getMenuList() {
		logger.debug("SystemSetController-getSystemSetList=》菜单权限-查看所有的菜单权限");
		String tree_data = "[]";
		try {
			// 查询所有且不分页
			Map<String, Object> params = new HashMap<String, Object>();
			List<SysMenu> tMenu = sysMenuService.findByBiz(params);

			List<Object> listResult = new ArrayList<Object>();

			if (tMenu != null) {
				for (SysMenu menu : tMenu) {
					Map<String, Object> row = new HashMap<String, Object>();
					row.put("id", menu.getId());
					row.put("name", menu.getName());
					row.put("pId", menu.getParentId());
					row.put("url", menu.getHref());
					row.put("open", true);
					listResult.add(row);
				}
			}
			tree_data = this.getJsonByObject(listResult);

		} catch (ServiceException e) {
			logger.error("SystemSetController-getMenuList-ServiceException=》系统设置-查看所有的菜单权限-ServiceException", e);
		} catch (Exception e) {
			logger.error("SystemSetController-getMenuList-Exception=》系统设置-查看所有的菜单权限-Exception", e);
		}
		ModelAndView mav = new ModelAndView("systemSet/systemSet_list");
		mav.addObject("result", tree_data);
		return mav;
	}

}

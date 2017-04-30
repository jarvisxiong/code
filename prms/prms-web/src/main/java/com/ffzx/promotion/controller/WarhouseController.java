package com.ffzx.promotion.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ffzx.commerce.framework.annotation.Permission;
import com.ffzx.commerce.framework.controller.BaseController;

/**
 * @author chenjia
 * 	/warehouse/list.do
 * pms:warehouse:view
 */
@Controller
@RequestMapping("/warehouse")
public class WarhouseController extends BaseController {

	// 记录日志
	protected final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${cas.server.url}")
	private String coreValue;
	
	@Value("${generator.model}")
	private String webValue;
	
	/**
	 * 列表
	 * @return
	 */
	@Permission(privilege = {"pms:warehouse:view"})
	@RequestMapping(value = "list")
	public String index(ModelMap map) {
		map.put("test", "test str " + coreValue + "web:  " + webValue);
		logger.info("the name is {}", "ok");
		return "warehouse/warehouse_list";
	}
	


}

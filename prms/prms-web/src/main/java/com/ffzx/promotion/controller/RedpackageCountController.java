package com.ffzx.promotion.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.promotion.api.dto.Redpackage;
import com.ffzx.promotion.service.RedpackageCountService;



 /**
 * @Description: 红包统计
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年11月21日 上午9:25:31
 * @version V1.0 
 *
 */
@Controller
@RequestMapping("/redpackageCount")
public class RedpackageCountController extends BaseController{
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(RedpackageCountController.class);

	@Autowired
	private RedpackageCountService redpackageCountService;
	
	@RequestMapping(value = "datacount")
	public String datacount(String redpackageId, ModelMap map){
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("redpackageId", redpackageId);
		map.put("redpackageCount", redpackageCountService.findByBiz(param));
		return "redpackage/redpackage_datacount";
	}
	
}

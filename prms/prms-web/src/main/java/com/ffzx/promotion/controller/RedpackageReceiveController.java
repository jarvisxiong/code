package com.ffzx.promotion.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.commodity.api.dto.SaleAttribute;
import com.ffzx.commodity.api.dto.SaleTemplate;
import com.ffzx.promotion.api.dto.Redpackage;
import com.ffzx.promotion.api.dto.RedpackageReceive;
import com.ffzx.promotion.service.RedpackageReceiveService;
import com.ffzx.promotion.service.RedpackageService;

import net.sf.json.JSONArray;



 /**
 * @Description: 红包统计
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年11月21日 上午9:25:43
 * @version V1.0 
 *
 */
@Controller
@RequestMapping("/redpackageReceive")
public class RedpackageReceiveController extends BaseController{
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(RedpackageReceiveController.class);

	@Autowired
	private RedpackageReceiveService redpackageReceiveService;
	
	/**
	 * 列表
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(RedpackageReceive redpackageReceive, ModelMap map){
		map.put("redpackageReceive", redpackageReceive);
		return "redpackage/redpackageReceive_list";
	}

	
	/**
	 * 异步加载列表
	 * @return
	 */
	@RequestMapping(value = "list2")
	@ResponseBody
	public ResultVo list2(RedpackageReceive redpackageReceive, ModelMap map){
		Page pageObj = this.getPageObj();
		ResultVo resultVo = new ResultVo();
		List<Redpackage> redpackages=null;
		try {
			redpackageReceive.setIsReceive(Constant.YES);
			redpackages = redpackageReceiveService.findList(pageObj, redpackageReceive);
			resultVo.setRecordsTotal(pageObj.getTotalCount());
		    resultVo.setInfoData(redpackages);
//			map.put("saleTemplateList", SaleTemplateList);
			map.put("pageObj", pageObj);
		} catch (ServiceException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}
		return resultVo;
	}
	

}

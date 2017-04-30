package com.ffzx.portal.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.portal.model.PageTab;
import com.ffzx.portal.service.PageTabService;

/**
 * 
 * @author ying.cai
 * @date 2017年4月12日下午1:48:28
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 */
@Controller
@RequestMapping("/pageTab")
public class PageTabController extends BaseController{
	private final static Logger logger = LoggerFactory.getLogger(PageTabController.class);
	
	@Autowired
	private PageTabService pageTabService;
	
	@RequestMapping(value = "list")
	public String getMenuList(ModelMap map) {
		return "pageTab/pageTab_list";
	}
	
	@ResponseBody
	@RequestMapping(value = "listData")
	public ResultVo listData(ModelMap map,PageTab pageTab) {
		Page pageObj = this.getPageObj();
		
		Map<String,Object> params = new HashMap<>();
		params.put("pageTabType", pageTab.getPageTabType()==null?null:pageTab.getPageTabType().toString());
		params.put("number", pageTab.getNumber());
		params.put("name", pageTab.getName());
		params.put("key", pageTab.getKey());
		List<PageTab> pageTabList = pageTabService.findByPage(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, params);
			
	    ResultVo resultVo = new ResultVo();
	    resultVo.setRecordsTotal(pageObj.getTotalCount());
	    resultVo.setInfoData(pageTabList);
		
		return resultVo;
	}
	
	@RequestMapping(value = "form")
	public String form(String id, ModelMap map){
		PageTab pageTab = null;
		if(StringUtils.isNotBlank(id)){
			pageTab = pageTabService.findById(id);
		}
		map.addAttribute("pageTab", pageTab);
		return "pageTab/pageTab_form";
	}
	
	@RequestMapping("saveDate")
	@ResponseBody
	public ResultVo saveDate(PageTab pageTab) {
		ResultVo result = new ResultVo();
		result.setStatus(Constant.RESULT_SCS);
		result.setInfoStr(Constant.RESULT_SCS_MSG);
		
		if( !validData(pageTab) ){
			result.setHasException(true);
			result.setStatus(Constant.RESULT_EXC);
			result.setInfoStr("编码已经存在！");
			return result;
		}
		
		if( StringUtils.isEmpty(pageTab.getId())){
			pageTab.setId(UUIDGenerator.getUUID());
			pageTab.setCreateDate(new Date());
			pageTabService.add(pageTab);
		}else {
			pageTabService.modifyById(pageTab);
		}
		
		
		return result;
	}
	
	private boolean validData(PageTab pageTab){
		Map<String,Object> params = new HashMap<>();
		params.put("number", pageTab.getNumber());
		List<PageTab> pts = pageTabService.findByBiz(params);
		if( pts==null||pts.size()==0 ){
			return true;
		}
		PageTab pt = pts.get(0);
		return pt.getId().equals(pageTab.getId());
		
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ResultVo delete(String id) {
		ResultVo result = new ResultVo();
		result.setStatus(Constant.RESULT_SCS);
		result.setInfoStr(Constant.RESULT_SCS_MSG);
		try{
			pageTabService.deleteById(id);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			result.setStatus(Constant.RESULT_ERR);
			result.setInfoStr(Constant.RESULT_ERR_MSG);
		}
		return result;
	}
	
	
}

package com.ffzx.portal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.portal.model.Catelog;
import com.ffzx.portal.model.CatelogData;
import com.ffzx.portal.service.CatelogDataService;
import com.ffzx.portal.service.CatelogService;


/**
 * 
 * @author ying.cai
 * @date 2017年4月13日下午3:27:49
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 */
@Controller
@RequestMapping("/catelogData")
public class CatelogDataController extends BaseController {
	
	@Autowired
	private CatelogService catelogService;
	@Autowired
	private CatelogDataService catelogDataService;
	
	
	@RequestMapping("list")
	public String list(ModelMap map) {
		List<Object> listData = catelogService.findByBiz(null); 
		String jsonStr = JSON.toJSONStringWithDateFormat(listData, Constant.DEFAULT_DATA_FORMAT);
		map.put("result", jsonStr);
		return "catelogData/catelogData_list";
	}
	
	@RequestMapping("tableListView")
	public String tableListView(String catelogId,ModelMap map){
		catelogId = catelogId==null?getDefaultId():catelogId;
		map.put("catelogId", catelogId);
		return "catelogData/catelogData_table_list";
	}
	
	@RequestMapping("listData")
	@ResponseBody
	public ResultVo listData(String catelogId,String catelogIdTwo,String title,String isLaunch){
		catelogId = StringUtil.isNotNull(catelogId)?catelogId:catelogIdTwo;
		catelogId=catelogId==null?getDefaultId():catelogId;
		Page pageObj = this.getPageObj();
		ResultVo resultVo = new ResultVo();
		resultVo.setRecordsTotal(0);
		resultVo.setInfoData(new ArrayList<>());
		
		if(StringUtil.isNotNull(catelogId)){
			Map<String,Object> params = new HashMap<>();
			params.put("catelogId", catelogId );
			params.put("title", title);
			params.put("isLaunch", isLaunch);
			
			List<CatelogData> catelogDataList = catelogDataService.findByPage(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, params);
			resultVo.setRecordsTotal(pageObj.getTotalCount());
		    resultVo.setInfoData(catelogDataList);
		}
		return resultVo;
	}
	
	
	private String getDefaultId(){
		List<Catelog> listData = catelogService.findByBiz(null); 
		if(listData!=null && listData.size()>0){
			return listData.get(0).getId();
		}
		return null;
	}
	
	@RequestMapping("form")
	public String form(String id,String catelogId, ModelMap map){
		CatelogData catelogData = null;
		if(StringUtil.isNotNull(id)){
			catelogData = catelogDataService.findById(id);
		}
		map.addAttribute("catelogData", catelogData);
		map.put("catelogId", catelogId);
		map.put("image_path", System.getProperty("image.web.server"));
		return "catelogData/catelogData_form";
	}
	
	
	@RequestMapping("saveData")
	@ResponseBody
	public ResultVo saveData(CatelogData catelogData) {
		ResultVo result = new ResultVo();
		result.setStatus(Constant.RESULT_SCS);
		result.setInfoStr(Constant.RESULT_SCS_MSG);
		if( StringUtils.isEmpty(catelogData.getId())){
			catelogData.setId(UUIDGenerator.getUUID());
			catelogData.setCreateTime(new Date());
			catelogData.setCreateDate(new Date());
			Catelog cl = new Catelog();
			cl.setId(catelogData.getCatelogId());
			catelogData.setCatelog(cl);
			catelogDataService.add(catelogData);
		}else {
			catelogDataService.modifyById(catelogData);
		}
		return result;
	}
	
	@RequestMapping("release")
	@ResponseBody
	public ResultVo release(String type,String...id) {
		String ids[] = id;
		ResultVo result = new ResultVo();
		result.setStatus(Constant.RESULT_SCS);
		result.setInfoStr(Constant.RESULT_SCS_MSG);
		
		for (int i = 0; i < ids.length; i++) {
			CatelogData cld = catelogDataService.findById(ids[i]);
			if(cld==null){
				continue;
			}
			//RELEASE代表发布
			cld.setIsLaunch("RELEASE".equals(type)?1:0);
			catelogDataService.modifyById(cld);
		}
		return result;
	} 
	
	
	@RequestMapping("top")
	@ResponseBody
	public ResultVo top(String type,String id) {
		ResultVo result = new ResultVo();
		result.setStatus(Constant.RESULT_SCS);
		result.setInfoStr(Constant.RESULT_SCS_MSG);
		CatelogData cld = catelogDataService.findById(id);
		cld.setIsTop("TOP".equals(type)?1:0);
		catelogDataService.updateTop(cld);
		return result;
	} 
	
	@RequestMapping("/delete")
	@ResponseBody
	public ResultVo delete(String id) {
		ResultVo result = new ResultVo();
		result.setStatus(Constant.RESULT_SCS);
		result.setInfoStr(Constant.RESULT_SCS_MSG);
		try{
			catelogDataService.deleteById(id);
		}catch(Exception e){
			result.setStatus(Constant.RESULT_ERR);
			result.setInfoStr(Constant.RESULT_ERR_MSG);
		}
		return result;
	}
}

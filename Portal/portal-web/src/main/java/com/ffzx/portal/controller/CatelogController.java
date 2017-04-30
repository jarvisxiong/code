package com.ffzx.portal.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.portal.enums.CatelogDataType;
import com.ffzx.portal.enums.CatelogSortType;
import com.ffzx.portal.model.Catelog;
import com.ffzx.portal.model.DataAttachment;
import com.ffzx.portal.model.PageTab;
import com.ffzx.portal.service.CatelogService;
import com.ffzx.portal.service.DataAttachmentService;
import com.ffzx.portal.service.PageTabService;

/**
 * 栏目共之气
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月13日 上午10:12:39
 * @version V1.0
 */
@Controller
@RequestMapping("/catelog")
public class CatelogController extends BaseController{
	private final static Logger logger = LoggerFactory.getLogger(CatelogController.class);
	@Autowired
	private CatelogService catelogService;
	@Autowired
	private PageTabService pageTabService;
	@Autowired
	private DataAttachmentService dataAttachmentService;
	@RequestMapping(value = "list")
	public String list(ModelMap map) {
		List<Object> listData = catelogService.findByBiz(null); 
		map.put("result", this.getJsonByObject(listData));
		return "catelog/catelog_list";
	}
	@RequestMapping(value = "tableList")
	public String tableList(String id,ModelMap map) {
		map.put("pId",id);
		return "catelog/catelog_table_list";
	}
	
	@RequestMapping(value = "/pagetab_selector")
	public String pagetab_selector() {
		return "catelog/pagetab_selector";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pagetab_selector_listdata")
	@ResponseBody
	public ResultVo pagetab_selector_listdata(ModelMap modelMap, HttpServletRequest request, String keyword) {
		
		Page pageObj = this.getPageObj();
	    Map<String, Object> params = new HashMap<String, Object>();
		
		if(StringUtil.isNotNull(keyword)) {
			keyword = keyword.trim();
			params.put("name", keyword);
		}
		List<PageTab> pageTabList = pageTabService.findByPage(pageObj,"sort", "asc", params);
		ResultVo resultVo = new ResultVo();
	    resultVo.setRecordsTotal(pageObj.getTotalCount());
	    resultVo.setInfoData(pageTabList);
		return resultVo;
	}
	@ResponseBody
	@RequestMapping(value = "listData")
	public ResultVo listData(ModelMap map,Catelog catelog) {
		Page pageObj = this.getPageObj();
		List<Catelog> catelogList = catelogService.findByPage(pageObj, "sort", "asc", getQueryParam(catelog));
	    ResultVo resultVo = new ResultVo();
	    resultVo.setRecordsTotal(pageObj.getTotalCount());
	    resultVo.setInfoData(catelogList);
		return resultVo;
	}
	
	@ResponseBody
	@RequestMapping(value = "ajaxTreeData")
	public ResultVo ajaxTreeData(ModelMap map) {
		List<Object> listData = catelogService.findByBiz(null); 
	    ResultVo resultVo = new ResultVo();
	    resultVo.setInfoData(this.getJsonByObject(listData));
		return resultVo;
	}
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public ResultVo treeData(ModelMap map,Catelog catelog) {
		Page pageObj = this.getPageObj();
		List<Catelog> catelogList = catelogService.findByPage(pageObj, "sort", "asc", getQueryParam(catelog));
	    ResultVo resultVo = new ResultVo();
	    resultVo.setRecordsTotal(pageObj.getTotalCount());
	    resultVo.setInfoData(catelogList);
		return resultVo;
	}
	
	@RequestMapping(value = "treeDataList")
	@ResponseBody
	public void treeDataList(Catelog catelog, String parent, HttpServletResponse response) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if (StringUtil.isNotNull(parent)) {
				params.put("parentId", parent);
			}
			List<Object> listResult = catelogService.findByBiz(params);;
			this.responseWrite(response, this.getJsonByObject(listResult));
		} catch (ServiceException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	private  Map<String,Object> getQueryParam(Catelog catelog){
		Map<String,Object> params = new HashMap<>();
		if(catelog.getParent()!=null&&StringUtil.isNotNull(catelog.getParent().getId())){
			params.put("parentidsAlllike",catelog.getParent().getId());
		}
		if(StringUtil.isNotNull(catelog.getNameOrNumber())){
			params.put("nameOrNumber", catelog.getNameOrNumber());
		}
		if(StringUtil.isNotNull(catelog.getIsEnable())){
			params.put("isEnable", catelog.getIsEnable());
		}	
		return params;
	}
	@RequestMapping(value = "form")
	public String form(String id, ModelMap map){
		Catelog catelog = null;
		if(StringUtils.isNotBlank(id)){
			catelog = catelogService.findById(id);
		}
		map.put("catelogDataTypeList", CatelogDataType.toList());
		map.put("catelogSortTypeList", CatelogSortType.toList());
		map.put("data", catelog);
		if(null!=catelog){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("object", catelog.getId());
		List<DataAttachment> imgDataList = dataAttachmentService.findByBiz(params);
		map.put("imgDataList", imgDataList);
		map.put("imgRoot", System.getProperty("image.web.server"));
		}
		return "catelog/catelog_form";
	}
	
	@RequestMapping("saveData")
	@ResponseBody
	public ResultVo saveDate(Catelog catelog) {
		ResultVo result = new ResultVo();
		
		
		if( !validData(catelog) ){
			result.setHasException(true);
			result.setStatus(Constant.RESULT_EXC);
			result.setInfoStr("编码已经存在！");
			return result;
		}
		catelog.setCreateDate(new Date());
		catelogService.saveData(catelog);	
		result.setStatus(Constant.RESULT_SCS);
		result.setInfoStr(Constant.RESULT_SCS_MSG);
		//result.setUrl(getBasePath() + "/catelog/list.do");
		return result;
	}
	
	private boolean validData(Catelog catelog){
		Map<String,Object> params = new HashMap<>();
		params.put("number", catelog.getNumber());
		List<Catelog> pts = catelogService.findByBiz(params);
		if( pts==null||pts.size()==0 ){
			return true;
		}
		Catelog pt = pts.get(0);
		return pt.getId().equals(catelog.getId());
		
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ResultVo delete(String id) {
		ResultVo result = new ResultVo();
		result.setStatus(Constant.RESULT_SCS);
		result.setInfoStr(Constant.RESULT_SCS_MSG);
		try{
			Catelog data  = catelogService.findById(id);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("parentidsrlike", data.getParentIds()+data.getId());
			List<Catelog> list = catelogService.findByBiz(param);
			if(null!=list&&list.size()>0){
				throw new ServiceException("存在子节点，不能删除");
			}
			catelogService.deleteById(id);
		}catch(ServiceException se){
			logger.error(se.getMessage(),se);
			result.setStatus(Constant.RESULT_ERR);
			result.setInfoStr(se.getMessage());
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			result.setStatus(Constant.RESULT_ERR);
			result.setInfoStr(Constant.RESULT_ERR_MSG);
		}
		return result;
	}
	
	
	@RequestMapping("/isEnabled")
	@ResponseBody
	public ResultVo isEnabled(Catelog data) {
		ResultVo result = new ResultVo();
		result.setStatus(Constant.RESULT_SCS);
		result.setInfoStr(Constant.RESULT_SCS_MSG);
		try{			
			catelogService.modifyById(data);
		}catch(ServiceException se){
			logger.error(se.getMessage(),se);
			result.setStatus(Constant.RESULT_ERR);
			result.setInfoStr(se.getMessage());
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			result.setStatus(Constant.RESULT_ERR);
			result.setInfoStr(Constant.RESULT_ERR_MSG);
		}
		return result;
	}
	
}

package com.ffzx.promotion.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CancellationException;

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
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.promotion.api.dto.Redpackage;
import com.ffzx.promotion.exception.BussinessException;
import com.ffzx.promotion.service.RedpackageService;



 /**
 * @Description: 红包创建
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年11月21日 上午9:25:16
 * @version V1.0 
 *
 */
@Controller
@RequestMapping("/redpackage")
public class RedpackageController extends BaseController{
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(RedpackageController.class);

	@Autowired
	private RedpackageService redpackageService;
	
	/**
	 * 列表
	 * @return
	 */
	@RequestMapping(value = "list")
	@Permission(privilege={"prms:redpackage:list","prms:redpackage:edit","prms:redpackage:view",
			"prms:redpackage:add","prms:redpackagegrant:list","prms:redpackage:delete","prms:redpackagegrant:isEnabled"})
	public String list(Redpackage redpackage, ModelMap map){
		map.put("redpackage", redpackage);
		return "redpackage/redpackage_list";
	}
	/**
	 * 弹窗列表
	 * @return
	 */
	@RequestMapping(value = "dialoglist")
	public String dialoglist(Redpackage redpackage, ModelMap map){
		map.put("redpackage", redpackage);
		
		return "redpackage/redpackage_dialoglist";
	}
	
	/**
	 * 异步加载列表
	 * @return
	 */
	@RequestMapping(value = "list2")
	@ResponseBody
	@Permission(privilege={"prms:redpackage:list","prms:redpackage:edit","prms:redpackage:view",
			"prms:redpackage:add","prms:redpackagegrant:list","prms:redpackage:delete","prms:redpackagegrant:isEnabled"})
	public ResultVo list2(Redpackage redpackage, ModelMap map){
		Page pageObj = this.getPageObj();
		ResultVo resultVo = new ResultVo();
		List<Redpackage> redpackages=null;
		try {
			redpackages = redpackageService.findList(pageObj, redpackage);
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
	

	/**
	 * 加载添加页面
	 */
	@RequestMapping(value = "form")
	@Permission(privilege={"prms:redpackage:add","prms:redpackage:edit"})
	public String form(String id, ModelMap map,String viewType){
		try {
			Redpackage redpackage;
			if(StringUtil.isNotNull(id)){
				redpackage=redpackageService.findById(id);
			}else{
				 redpackage=new Redpackage();
			}
			map.addAttribute("redpackage",redpackage);
			map.put("viewType", '0');
		} catch (ServiceException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}
		return "redpackage/redpackage_form";
	}
	
	/**
	 * 查看页面
	 */
	@RequestMapping(value = "allform")
	public String allform(String id, ModelMap map){
		try {
			if(StringUtil.isNotNull(id)){
				map.put("viewType", '1');
				map.put("id", id);
			}
		} catch (ServiceException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}
		return "redpackage/redpackage_allform";
	}

	
	/**
	 * 查看页面
	 */
	@RequestMapping(value = "detail")
	public String detail(String id, ModelMap map){
		try {
			if(StringUtil.isNotNull(id)){
				map.put("redpackage", redpackageService.findBydetail(id));
				map.put("viewType", '1');
			}
		} catch (ServiceException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}
		return "redpackage/redpackage_detail";
	}
	

	/**
	 * 修改禁用状态
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	@Permission(privilege={"prms:redpackage:delete"})
	public ResultVo delete(Redpackage redpackage,HttpSession session){
		ResultVo resultVo = new ResultVo();
		try {
			redpackage.setDelFlag(Constant.YES);
			resultVo.setResult(redpackageService.deleteRedpackage(redpackage),null);//"/saleTemplate/list.do"			
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("", e);
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("", e);
		}
		if(resultVo.isHasException()){
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}	
	
	/**
	 * 修改禁用状态
	 */
	@RequestMapping(value = "updateState")
	@ResponseBody
	@Permission(privilege={"prms:redpackage:isEnabled"})
	public ResultVo updateState(Redpackage redpackage,HttpSession session){
		ResultVo resultVo = new ResultVo();
		String error=Constant.RESULT_EXC_MSG;
		try {
			resultVo.setResult(redpackageService.updateState(redpackage),null);//"/saleTemplate/list.do"			
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("", e);
		}catch (BussinessException e) {
			// TODO: handle exception
			error=e.getErrorMsg();
		}
		catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("", e);
		}
		if(resultVo.isHasException()){
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(error);
			return resultVo;
		}
		return resultVo;
	}	
	
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "save",method=RequestMethod.POST)
	@Permission(privilege={"prms:redpackage:add","prms:redpackage:edit"})
	@ResponseBody
	public ResultVo save(@Valid Redpackage redpackage,BindingResult result,ModelMap map){
		ResultVo resultVo = new ResultVo();
		
		try {
			SysUser sysUser = RedisWebUtils.getLoginUser();
			redpackage.setLastUpdateBy(sysUser);
			redpackage.setLastUpdateName(sysUser.getName());
			redpackage.setLastUpdateDate(new Date());
			if(!StringUtil.isNotNull(redpackage.getId())){
				redpackage.setCreateBy(sysUser);
				redpackage.setCreateDate(new Date());
				redpackage.setCreateName(sysUser.getName());
			}
			ServiceCode serviceCode = redpackageService.saveOrUpdate(redpackage);
			
			resultVo.setResult(serviceCode,"/redpackage/list.do");			
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("", e);
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("", e);
		}
		if(resultVo.isHasException()){ //异常返还
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}
}

package com.ffzx.permission.help.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ffzx.commerce.framework.annotation.Permission;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.FastJsonUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.commerce.framework.vo.Single2ResultVo;
import com.ffzx.permission.model.TestPage;
import com.ffzx.permission.service.TestPageService;

/**
 * PageDemo
 * 
 * @author CMM
 *
 * 2016年2月18日 下午3:39:37
 */
@Controller
@RequestMapping("/help/test")
public class PageDemo extends BaseController {
	
	private final static Logger logger = LoggerFactory.getLogger(PageDemo.class);
	
	@Autowired
	public TestPageService testPageService;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listDemo")
	//@Permission(privilege = {"pms:demo:view", "pms:demo:edit", "pms:demo:delete"})
	public String list(ModelMap map){
		return "help/demo_list";
	}
	
	@RequestMapping(value = "/queryData")
	@ResponseBody
	public ResultVo queryData(TestPage testPage){
		
	    List<TestPage> testList = null;
	    Page pageObj = this.getPageObj();
		
		//查询数据
	    testList = testPageService.findList(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, testPage);
		
		//设置页面显示
	    ResultVo resultVo = new ResultVo();
	    resultVo.setRecordsTotal(pageObj.getTotalCount());
	    resultVo.setInfoData(testList);
	    
		return resultVo;
	}
	@RequestMapping(value = "/formDealWith")
	@ResponseBody
	public Single2ResultVo formDealWith(String id){

		TestPage testPage = null;
		//查询编辑数据
		if(StringUtil.isNotEmpty(id)){
			testPage = testPageService.findById(id);
		}
		//返回结果对象 
		Single2ResultVo single2ResultVo = new Single2ResultVo();
		single2ResultVo.setDataObject(testPage);
		return single2ResultVo;

	}
	@RequestMapping(value = "/form")
	public String form(String id,ModelMap map){
		map.put("id", id);
		return "help/demo_form";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	//@Permission(privilege = "pms:demo:edit")
	public Single2ResultVo save(@Valid TestPage testPage, BindingResult result) {//说明：在紧跟@Valid参数添加BindingResult参数
		
		//返回结果对象
		Single2ResultVo single2ResultVo = new Single2ResultVo();
		
		if(result.hasErrors()) {//验证错误  
			
			//验证失败详细说明
			//验证失败统一说明
			single2ResultVo.setStatus(Constant.RESULT_VALID_STR);
			single2ResultVo.setInfoStr(result.getFieldError().getDefaultMessage());
			
			logger.info("验证出错了。");
			return single2ResultVo;
        } 
		
		//执行更新
		testPage.setCreateBy(RedisWebUtils.getLoginUser());
		testPage.setLastUpdateBy(RedisWebUtils.getLoginUser());
		ServiceCode serviceCode = null;
		serviceCode = testPageService.save(testPage);
		single2ResultVo.setResult(serviceCode, "/help/test/listDemo.do");
		return single2ResultVo;
	}
	
	@RequestMapping("/delete")
	@ResponseBody	
	//@Permission(privilege = "pms:demo:delete")
	public Single2ResultVo delete(String id) {
		//返回结果对象
		Single2ResultVo resultVo = new Single2ResultVo();
		int resultInt = 0;
		
		//执行操作
		resultInt = testPageService.deleteById(id);
		
		//判断执行结课
		if(resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
			resultVo.setUrl("/help/test/listDemo.do");
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
		}
		
		return resultVo;
	}
	
	@RequestMapping("/deletes")
	@ResponseBody
	//@Permission(privilege = "pms:demo:delete")
	public Single2ResultVo deletes(String ids, RedirectAttributes redirectAttributes) {
		//返回结果对象
		Single2ResultVo single2ResultVo = new Single2ResultVo();
		boolean errorResult = false;
		
		String[] idArray = ids.split(",");
		for(String id : idArray){
			testPageService.deleteById(id);
		}
		
		//判断执行结课
		if(errorResult) {
			single2ResultVo.setStatus(Constant.RESULT_ERR);
			single2ResultVo.setInfoStr(Constant.RESULT_ERR_MSG);
		}else{
			single2ResultVo.setStatus(Constant.RESULT_SCS);
			single2ResultVo.setInfoStr(Constant.RESULT_DEL_MSG);
			single2ResultVo.setUrl("/help/test/listDemo.do");
		}
		
		return single2ResultVo;
	}
	
	/**
	 * 获取当前用户的权限，主要用于调试
	 * @return
	 */
	@RequestMapping("/personPermission")
	@ResponseBody
	public Set<String> personPermission() {
		Set<String> personPermission = RedisWebUtils.getPermission();
		
		return personPermission;
	}
	
	/**
	 * 获取当前用户的权限，主要用于调试
	 * @return
	 */
	@RequestMapping("/permissionMap")
	@ResponseBody
	public Map<String, Object> permissionMap() {
		SysUser sysUser = RedisWebUtils.getLoginUser();
		Map<String, Object> sysUserMap = new HashMap<String, Object>();
		sysUserMap.put("vendorId", sysUser.getVendorId());
		sysUserMap.put("vendorCode", sysUser.getVendorCode());
		
		sysUserMap.put("allOfficeFlag", sysUser.getAllOfficeFlag());
		sysUserMap.put("officeList", sysUser.getOfficeList());
		
		sysUserMap.put("allAddressFlag", sysUser.getAllAddressFlag());
		sysUserMap.put("addressIdList", sysUser.getAddressIdList());
		sysUserMap.put("addressCodeList", sysUser.getAddressCodeList());
		
		sysUserMap.put("allWarehouseFlag", sysUser.getAllWarehouseFlag());
		sysUserMap.put("warehouseList", sysUser.getWarehouseList());
		
		return sysUserMap;
	}
	
	/**
	 * 测试没有权限的页面
	 * @return
	 */
	@RequestMapping("/demoPermission")
	@ResponseBody
	@Permission(privilege = "pms:demo:delete22")
	public Set<String> deomPermission() {
		Set<String> personPermission = RedisWebUtils.getPermission();
		
		return personPermission;
	}


	@RequestMapping("/popup")
	public String popup() {
		
		return "help/popup";
	}
	

	@RequestMapping("/popupFrom")
	public String popupFrom() {
		
		return "help/popup_form";
	}
	
}

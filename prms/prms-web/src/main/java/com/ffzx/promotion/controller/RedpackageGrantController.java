package com.ffzx.promotion.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;

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
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.member.api.dto.MemberLabel;
import com.ffzx.member.api.service.MemberLabelApiService;
import com.ffzx.promotion.api.dto.Redpackage;
import com.ffzx.promotion.api.dto.RedpackageGrant;
import com.ffzx.promotion.api.service.consumer.MemberApiConsumer;
import com.ffzx.promotion.api.service.consumer.MemberLableApiConsumer;
import com.ffzx.promotion.exception.BussinessException;
import com.ffzx.promotion.service.RedpackageGrantService;
import com.ffzx.promotion.service.RedpackageService;



 /**
 * @Description: 红包发放
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年11月21日 上午9:24:51
 * @version V1.0 
 *
 */
@Controller
@RequestMapping("/redpackageGrant")
public class RedpackageGrantController extends BaseController{
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(RedpackageGrantController.class);

	@Autowired
	private RedpackageGrantService redpackageGrantService;

	@Autowired
	private MemberLableApiConsumer memberLableApiConsumer;
	/**
	 * 列表
	 * @return
	 */
	@RequestMapping(value = "list")
	@Permission(privilege={"prms:redpackagegrant:edit","prms:redpackagegrant:add","prms:redpackagegrant:list",
			"prms:redpackagegrant:isEnabled","prms:redpackagegrant:delete"})
	public String list(RedpackageGrant redpackageGrant, ModelMap map){
		map.put("redpackageGrant", redpackageGrant);
		return "redpackage/redpackageGrant_list";
	}
	/**
	 * 会员列表
	 * @param member
	 * @param map
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @return
	 */
	@RequestMapping(value = "/memberlabel")
	public String memberlabel(MemberLabel memberLabel,ModelMap map, HttpServletResponse response) {
		
		return "redpackage/redpackageGrant_memberlabel";
	}
	/**
	 * 会员列表
	 * @param member
	 * @param map
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @return
	 */
	@RequestMapping(value = "/memberlabel2")
	@ResponseBody
	public ResultVo memberlabel2(MemberLabel memberLabel,ModelMap map, HttpServletResponse response) {
		Page pageObj = this.getPageObj();
		ResultVo resultVo = new ResultVo();
		try {
			//查询正常用户
			memberLabel.setLabelStatus(Constant.YES);
			List<Object> list= memberLableApiConsumer.selectLabelByParams
					(memberLabel, pageObj, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY);
			resultVo.setRecordsTotal(pageObj.getTotalCount());
		    resultVo.setInfoData(list);
		} catch (ServiceException e) {
			logger.error(" ", e);
			throw e;
		} catch (Exception e) {
			logger.error(" ", e);
		}
		return resultVo;
	}
	
	/**
	 * 异步加载列表
	 * @return
	 */
	@RequestMapping(value = "list2")
	@ResponseBody
	@Permission(privilege={"prms:redpackagegrant:edit","prms:redpackagegrant:add","prms:redpackagegrant:list",
			"prms:redpackagegrant:isEnabled","prms:redpackagegrant:delete"})
	public ResultVo list2(RedpackageGrant redpackageGrant, ModelMap map){
		Page pageObj = this.getPageObj();
		ResultVo resultVo = new ResultVo();
		List<RedpackageGrant> redpackageGrants=null;
		try {
			redpackageGrants = redpackageGrantService.findList(pageObj, redpackageGrant);
			resultVo.setRecordsTotal(pageObj.getTotalCount());
		    resultVo.setInfoData(redpackageGrants);
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
	@Permission(privilege={"prms:redpackagegrant:edit","prms:redpackagegrant:add"})
	public String form(String id, ModelMap map,String viewType){
		try {
			RedpackageGrant redpackageGrant;
			if(StringUtil.isNotNull(id)){
				redpackageGrant=redpackageGrantService.findBydetail(id, false);
			}else{
				 redpackageGrant=new RedpackageGrant();
			}
			map.addAttribute("redpackageGrant",redpackageGrant);
			map.put("viewType", '0');
		} catch (ServiceException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}
		return "redpackage/redpackageGrant_form";
	}
	

	
	/**
	 * 查看页面
	 */
	@RequestMapping(value = "detail")
	public String detail(String id, ModelMap map){
		try {
			if(StringUtil.isNotNull(id)){
				map.put("redpackageGrant", redpackageGrantService.findBydetail(id,true));
				map.put("viewType", '1');
			}
		} catch (ServiceException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}
		return "redpackage/redpackageGrant_detail";
	}
	

	/**
	 * 修改禁用状态
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	@Permission(privilege={"prms:redpackagegrant:delete"})
	public ResultVo delete(RedpackageGrant redpackageGrant,HttpSession session){
		ResultVo resultVo = new ResultVo();
		try {
			redpackageGrant.setDelFlag(Constant.YES);
			resultVo.setResult(redpackageGrantService.deleteRedpackage(redpackageGrant),null);//"/saleTemplate/list.do"			
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("", e);
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("", e);
		}
		if(resultVo.isHasException() && !resultVo.getStatus().equals(Constant.RESULT_SCS)){
			resultVo.setHasException(true);
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
	@Permission(privilege={"prms:redpackagegrant:isEnabled"})
	public ResultVo updateState(RedpackageGrant redpackageGrant,HttpSession session){
		ResultVo resultVo = new ResultVo();
		String error=Constant.RESULT_EXC_MSG;
		try {
			resultVo.setResult(redpackageGrantService.updateState(redpackageGrant),null);//"/saleTemplate/list.do"			
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
	@Permission(privilege={"prms:redpackagegrant:edit","prms:redpackagegrant:add"})
	@ResponseBody
	public ResultVo save(@Valid RedpackageGrant redpackageGrant,BindingResult result,ModelMap map){
		ResultVo resultVo = new ResultVo();

		String error=Constant.RESULT_EXC_MSG;
		try {
			SysUser sysUser = RedisWebUtils.getLoginUser();
			redpackageGrant.setLastUpdateBy(sysUser);
			redpackageGrant.setLastUpdateName(sysUser.getName());
			redpackageGrant.setLastUpdateDate(new Date());
			if(!StringUtil.isNotNull(redpackageGrant.getId())){
				redpackageGrant.setCreateBy(sysUser);
				redpackageGrant.setCreateDate(new Date());
				redpackageGrant.setCreateName(sysUser.getName());
			}
			ServiceCode serviceCode = redpackageGrantService.saveOrUpdate(redpackageGrant);
			
			resultVo.setResult(serviceCode,"/redpackageGrant/list.do");			
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("", e);
		} catch (BussinessException e) {
			// TODO: handle exception
			error=e.getErrorMsg();
			resultVo.setHasException(true);
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
}

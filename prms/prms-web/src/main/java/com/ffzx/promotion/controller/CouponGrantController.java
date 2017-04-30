package com.ffzx.promotion.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
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
import com.ffzx.commerce.framework.utils.ExcelReader;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UploadFileUtils;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.member.api.dto.MemberLabel;
import com.ffzx.promotion.api.dto.AppRecommendAwards;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.service.consumer.DictApiConsumer;
import com.ffzx.promotion.api.service.consumer.MemberApiConsumer;
import com.ffzx.promotion.api.service.consumer.MemberLableApiConsumer;
import com.ffzx.promotion.constant.CouponConstant;
import com.ffzx.promotion.service.AppRecommendAwardsService;
import com.ffzx.promotion.service.CouponAdminCouponGrantService;
import com.ffzx.promotion.service.CouponAdminService;
import com.ffzx.promotion.service.CouponGrantMemberService;
import com.ffzx.promotion.service.CouponGrantService;

/**
 * 优惠券发放控制器
 * 
 * @className CouponGrantController.java
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月4日 下午5:36:20
 * @version 1.0
 */
@Controller
@RequestMapping("/couponGrant")
public class CouponGrantController extends BaseController {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(CouponGrantController.class);

	private String couponLinkUrl;
	@Autowired
	private CouponGrantService couponGrantService;
	@Autowired
	private CouponGrantMemberService couponGrantMemberService;
	@Autowired
	private MemberApiConsumer memberApiConsumer;
	@Autowired
	private AppRecommendAwardsService appRecommendAwardsService;
	@Autowired
	private CouponAdminService couponAdminService;
	
	@Autowired
	private CouponAdminCouponGrantService couponAdminCouponGrantService;
	@Autowired
	private MemberLableApiConsumer memberLableApiConsumer;
	
	@Autowired
	private DictApiConsumer dictApiConsumer;
	
	/**
	 * 优惠券发放列表
	 * @param member
	 * @param map
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @return
	 */
	@RequestMapping(value = "/list")
	@Permission(privilege = {"prms:grant:edit","prms:grant:add","prms:grant:stop","prms:grant:delete","prms:grant:details","prms:grant:vcode"})
	public String couponGrantList(CouponGrant couponGrant,ModelMap map) {
		Page pageObj = this.getPageObj();
		List<CouponGrant> couponGrants=null;
		try {
			couponGrants=couponGrantService.findList(pageObj, "create_date",  Constant.ORDER_BY, couponGrant);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			logger.error("CouponGrantController-couponGrantList-Exception=》促销系统-优惠券发放列表-Exception", e);
			throw e;
			
		}
//		couponLinkUrl = dictApiConsumer.getCouponLink();
		map.put("couponGrantList", couponGrants);
//		map.put("couponLinkUrl", couponLinkUrl);
		map.put("pageObj",pageObj);
		return "coupon/coupon_grant_list";
	}
	
	/**
	 * 优惠券发放删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody	
	@Permission(privilege = "prms:grant:delete")
	public ResultVo delete(String id) {
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		int resultInt = 0;
		//执行操作
		try {
			if(id != null && !"".equals(id)){
				resultInt = couponGrantService.delete(id);
			}
		} catch (Exception e) {
			logger.error("couponGrant.delete() Exception=》优惠券管理-删除优惠券", e);
			throw new ServiceException(e);
		}
		
		//判断执行结课
		if(resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
			resultVo.setResult(ServiceResultCode.SUCCESS,"/couponGrant/list.do");
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
			resultVo.setResult(ServiceResultCode.FAIL,"/couponGrant/list.do");
		}
		return resultVo;
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
		
		return "coupon/coupon_grant_memberlabel";
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
	 * 暂停领取
	 * @param id
	 * @return
	 */
	@RequestMapping("/stop")
	@Permission(privilege = "prms:grant:stop")
	@ResponseBody	
	public ResultVo stop(String id) {
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		int resultInt = 0;
		CouponGrant couponGrant = new CouponGrant();
		couponGrant.setId(id);
		couponGrant.setReceiveState(Constant.REC_END);
		//执行操作
		try {
			resultInt = couponGrantService.modifyById(couponGrant);
		} catch (Exception e) {
			logger.error("CouponGrant.stop() Exception=》优惠券管理-暂停领取", e);
			throw new ServiceException(e);
		}
		
		//判断执行结课
		if(resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
			resultVo.setUrl("/couponGrant/list.do");
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
		}
		
		return resultVo;
	}
	/**
	 * 会员列表
	 * @param member
	 * @param map
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @return
	 */
	@RequestMapping(value = "/memberlist")
	@Permission(privilege = {"prms:grant:edit","prms:grant:add"})
	public String memberlist(String nickName,String phone,String noidList,ModelMap map, HttpServletResponse response) {
		try {
			Page page = this.getPageObj();
			Map<String, Object> memberParams=new HashMap<String, Object>();
			if(StringUtil.isNotNull(phone))
				memberParams.put("phone", phone);
			if(StringUtil.isNotNull(nickName))
				memberParams.put("nickName", nickName);
			if(StringUtil.isNotNull(noidList))
				memberParams.put("noidList", noidList.split(","));
			//查询正常用户
			memberParams.put("isDisable", Constant.NO);
			List<Object> list= memberApiConsumer.getAllMember(memberParams, page, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY);
			if(list!=null && list.size()>0){
				page=(Page) list.get(list.size()-1);
				list.remove(list.size()-1);
			}
			if(page == null){
				page = this.getPageObj();
			}
			map.put("list", list);
			map.put("pageObj", page);
			map.put("phone", phone);
			map.put("nickName", nickName);
			map.put("noidList",noidList);
		} catch (ServiceException e) {
			logger.error("CouponGrantController-memberlist-ServiceException=》优惠券管理-dubbo会员list接口-ServiceException", e);
			throw e;
		} catch (Exception e) {
			logger.error("CouponGrantController-memberlist-Exception=》机构管理-查看所有机构-dubbo会员list接口", e);
			throw new ServiceException(e);
		}
		return "coupon/coupon_grant_member_list";
	}

	/**
	 *  
	 * 优惠券列表
	 * @param phoneOrNick
	 * @param noidList
	 * @param map
	 * @param response
	 * @author luozi
	 * @return
	 */
	@RequestMapping(value = "/couponAdminList")
	@Permission(privilege = {"prms:grant:edit","prms:grant:add"})
	public String couponAdminList(String couponName,String grantDate,String noidList,
			String number,
			ModelMap map, HttpServletResponse response,
			String isDate) {
		try {
			Page page = this.getPageObj();
			Map<String, Object> couponParams=new HashMap<String, Object>();
			if(StringUtil.isNotNull(couponName))
				couponParams.put("couponName", couponName);
			if(StringUtil.isNotNull(grantDate)){
				couponParams.put("date", grantDate);
			}else if(!StringUtil.isEmpty(isDate) && isDate.equals(Constant.YES)){//是否获取服务器当前时间
				couponParams.put("date", new Date());
			}else if(!StringUtil.isEmpty(number)){
				couponParams.put("number", number);
			}
			
			if(StringUtil.isNotNull(noidList))
				couponParams.put("noidList", noidList.split(","));
			List<CouponAdmin> list= couponAdminService.getCouponAdminList(page, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY,couponParams);

			map.put("list", list);
			map.put("pageObj", page);
			map.put("couponName", couponName);
			map.put("number", number);
			map.put("grantDate", grantDate);
			map.put("noidList",noidList);
		} catch (ServiceException e) {
			logger.error("CouponGrantController-couponAdminList-ServiceException=》优惠券管理-dubbo会员list接口-ServiceException", e);
			throw e;
		} catch (Exception e) {
			logger.error("CouponGrantController-couponAdminList-Exception=》机构管理-查看所有机构-dubbo会员list接口", e);
			throw new ServiceException(e);
		}
		return "coupon/coupon_grant_admin_list";
	}

	/**
	 *  
	 * 优惠券买赠列表
	 * @param phoneOrNick
	 * @param noidList
	 * @param map
	 * @param response
	 * @author luozi
	 * @return
	 */
	@RequestMapping(value = "/couponAdminMzList")
	public String couponAdminMzList(String couponName,String grantDate,String noidList,
			String number,
			ModelMap map, HttpServletResponse response,
			String isDate) {
		try {
			Page page = this.getPageObj();
			Map<String, Object> couponParams=new HashMap<String, Object>();
			if(StringUtil.isNotEmpty(couponName))
				couponParams.put("couponName", couponName);
			if(StringUtil.isNotEmpty(grantDate)){
				couponParams.put("date", grantDate);
			}
			if(!StringUtil.isEmpty(isDate) && isDate.equals(Constant.YES)){//是否获取服务器当前时间
				couponParams.put("date", new Date());
			}
			if(StringUtils.isNotEmpty(number)){
				couponParams.put("number", number);
			}
			
			if(StringUtil.isNotNull(noidList))
				couponParams.put("noidList", noidList.split(","));
			List<CouponAdmin> list= couponAdminService.getCouponAdminList(page, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY,couponParams);

			map.put("list", list);
			map.put("pageObj", page);
			map.put("couponName", couponName);
			map.put("number", number);
			map.put("grantDate", grantDate);
			map.put("isDate", isDate);
			map.put("noidList",noidList);
		} catch (ServiceException e) {
			logger.error("CouponGrantController-couponAdminList-ServiceException=》优惠券管理-dubbo会员list接口-ServiceException", e);
			throw e;
		} catch (Exception e) {
			logger.error("CouponGrantController-couponAdminList-Exception=》机构管理-查看所有机构-dubbo会员list接口", e);
			throw new ServiceException(e);
		}
		return "coupon/coupon_grant_adminmz_list";
	}

	/*********
	 * 基本信息
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "viewDetail")
	@Permission(privilege = {"prms:grant:details"})
	public String memberDetail(String id, ModelMap map){
		CouponGrant couponGrant=null;
		try {

			List<String> listuser=couponGrantMemberService.selectMemberid(id);
			if(listuser!=null){
				map.addAttribute("idList", listuser.toString().substring(1, listuser.toString().length()-1).replaceAll(" ", "")+",");
				Map<String, Object> memberParams=new HashMap<String, Object>();
					memberParams.put("idList", listuser);
				Page page = this.getPageObj();
				page.setPageSize(1000);
				List<Object> list= memberApiConsumer.getAllMember(memberParams, page, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY);
				list.remove(list.size()-1);
				map.put("userList", list);

			}
			
			couponGrant = this.couponGrantService.findBydetail(id);
			List<String> couponList=couponAdminCouponGrantService.selectCouponAdminId(id);
			if(couponList!=null){
				Map<String, Object> couponParams=new HashMap<String, Object>();
				couponParams.put("idList", couponList);
				Page page = this.getPageObj();
				page.setPageSize(1000);
				List<CouponAdmin> list= couponAdminService.getCouponAdminList(page, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY,couponParams);
				map.put("couponList", list);
			}
		} catch (ServiceException e) {
			logger.error("couponGrantController-Exception=》优惠券管理-优惠券发放基本信息-Exception", e);
			throw e;
		}
		map.put("couponGrant", couponGrant);
		return "coupon/coupon_grant_detail";
	}
	/**
	 * 添加
	 */
	@Permission(privilege = {"prms:grant:add"})
	@RequestMapping(value = "save",method=RequestMethod.POST)@ResponseBody
	public ResultVo save(@Valid CouponGrant couponGrant,BindingResult result, HttpSession session,String userList,String couponAdminList){


		ResultVo resultVo = new ResultVo();
		if(result.hasErrors()){		//验证失败说明
			resultVo.setStatus(Constant.RESULT_VALID);
			resultVo.setInfoMap(this.getValidErrors(result.getFieldErrors()));
			return resultVo;
		}
		
		try {

			if(StringUtil.isNotNull(couponGrant.getId())){		//修改

				SysUser updateBy = RedisWebUtils.getLoginUser();
				couponGrant.setLastUpdateBy(updateBy);
				couponGrant.setLastUpdateDate(new Date());
			}else{
				//设置新增人信息
				SysUser createdBy = RedisWebUtils.getLoginUser();
				couponGrant.setCreateBy(createdBy);
				couponGrant.setCreateDate(new Date());
			}
			ServiceCode serviceCode = couponGrantService.saveOrUpdateCoupon(couponGrant,userList,couponAdminList);
			resultVo.setResult(serviceCode,"/couponGrant/list.do");
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("CouponGrantController-couponGrantList-Exception=》促销系统-优惠券新增-Exception", e);
			throw e;
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("CouponGrantController-couponGrantList-Exception=》促销系统-优惠券新增-Exceptio", e);
			throw new ServiceException(e);
		}
		if(resultVo.isHasException()){ //异常返还
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}
	

	
	@RequestMapping(value = "importView")
	public String importView(HttpServletRequest request,String id,String type){
		
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		return "coupon/coupon_importview";
	}
	@RequestMapping(value = "importExcel")
	public  String importExcel(HttpServletRequest request, ModelMap map,String id,String type){

		
		List<String> list;
		try {
			list = UploadFileUtils.uploadFiles(request);
			if(list !=null && list.size()>0){
				for (String path:list) {
					List<String[]> listRow = ExcelReader.getExcelData(path);
					Map<String, Object> param=couponGrantService.importExcel(listRow);
					map.put(CouponConstant.date, param.get(CouponConstant.date));
					map.put(CouponConstant.errorVaildate, param.get(CouponConstant.errorVaildate));
					map.put(CouponConstant.message, param.get(CouponConstant.message));
					
				}
			}

		} catch (IllegalStateException e) {
			logger.error("", e);
			throw e;
			
		}catch(Exception e){
			logger.error(" ", e);
			throw new RuntimeException(e);
			
		}
		return "coupon/coupon_importmsg";
	}
	@RequestMapping(value = "codeform")
	public String codeform(String id, ModelMap map,String viewstatus){

		try {
			if(StringUtil.isNotNull(id)){
				List<String> listuser=couponGrantMemberService.selectMemberid(id);
				List<String> couponList=couponAdminCouponGrantService.selectCouponAdminId(id);
				if(listuser!=null){
					map.addAttribute("idList", listuser.toString().substring(1, listuser.toString().length()-1).replaceAll(" ", "")+",");
					Map<String, Object> memberParams=new HashMap<String, Object>();
						memberParams.put("idList", listuser);
					Page page = this.getPageObj();
					page.setPageSize(1000);
					List<Object> list= memberApiConsumer.getAllMember(memberParams, page, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY);
					list.remove(list.size()-1);
					map.put("userList", list);

				}
				if(couponList!=null && couponList.size()>-1 ){
					map.addAttribute("couponIdList",couponList.toString().substring(1,couponList.toString().length()-1).replaceAll(" ", "")+",");
					Map<String, Object> couponParams=new HashMap<String, Object>();
					couponParams.put("idList", couponList);
					Page page = this.getPageObj();
					page.setPageSize(1000);
					List<CouponAdmin> list= couponAdminService.getCouponAdminList(page, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY,couponParams);
					if(list!=null && list.size()>0)
						map.put("couponList", list);
				}
				map.addAttribute("couponGrant", couponGrantService.findById(id));
			}
		}catch (ServiceException e) {
			logger.error(" ", e);
			throw e;
		} catch (Exception e) {
			logger.error(" ", e);
			throw new ServiceException(e);
		}
		map.put("viewstatus", viewstatus);
		return "coupon/coupon_grant_code_form";
	}
	
	@RequestMapping(value = "form")
	public String form(String id, ModelMap map,String viewstatus){

		try {
			if(StringUtil.isNotNull(id)){
				List<String> listuser=couponGrantMemberService.selectMemberid(id);
				List<String> couponList=couponAdminCouponGrantService.selectCouponAdminId(id);
				if(listuser!=null){
					map.addAttribute("idList", listuser.toString().substring(1, listuser.toString().length()-1).replaceAll(" ", "")+",");
					Map<String, Object> memberParams=new HashMap<String, Object>();
						memberParams.put("idList", listuser);
					Page page = this.getPageObj();
					page.setPageSize(2000);
					List<Object> list= memberApiConsumer.getAllMember(memberParams, page, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY);
					list.remove(list.size()-1);
					map.put("userList", list);

				}
				if(couponList!=null && couponList.size()>-1 ){
					map.addAttribute("couponIdList",couponList.toString().substring(1,couponList.toString().length()-1).replaceAll(" ", "")+",");
					Map<String, Object> couponParams=new HashMap<String, Object>();
					couponParams.put("idList", couponList);
					Page page = this.getPageObj();
					page.setPageSize(1000);
					List<CouponAdmin> list= couponAdminService.getCouponAdminList(page, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY,couponParams);
					if(list!=null && list.size()>0)
						map.put("couponList", list);
				}
				map.addAttribute("couponGrant", couponGrantService.findBydetail(id));
			}else{

				map.addAttribute("couponGrant",new CouponGrant());
			}
		}catch (ServiceException e) {
			logger.error(" ", e);
			throw e;
		} catch (Exception e) {
			logger.error(" ", e);
			throw new ServiceException(e);
		}
		map.put("viewstatus", viewstatus);
		return "coupon/coupon_grant_form";
	}
	
	@RequestMapping(value = "link")
	public String link(String id, ModelMap map){
		couponLinkUrl = dictApiConsumer.getCouponLink();
		map.put("url", couponLinkUrl);
		map.put("id",id);
		return "coupon/coupon_grant_link";
	}
	
	@RequestMapping("toGrant")
	public void toGrant(String id){
		this.couponGrantService.sendCouponAdmin();
	}
	
	/**
	* 
	* @param id 
	* @param map
	* @return String    返回类型
	 */
	@Permission(privilege={"prms:grant:edit","prms:grant:add"})
	@RequestMapping(value = "recommendform")
	public String recommendForm(ModelMap map){
		try {
			List<AppRecommendAwards> appRecommendAwardsList = appRecommendAwardsService.findByBiz(null);
			if (appRecommendAwardsList != null && appRecommendAwardsList.size() > 0) {
				map.addAttribute("data", appRecommendAwardsList.get(0));
			}
		}catch (ServiceException e) {
			logger.error("促销系统-优惠券发放列表-推荐有奖页面-ServiceException", e);
			throw e;
		} catch (Exception e) {
			logger.error("促销系统-优惠券发放列表-推荐有奖页面-Exception", e);
			throw new ServiceException(e);
		}
		
		return "coupon/recommendawards_form";
	}
	
	
	/**
	* 推荐有奖保存
	* @param id 
	* @param map
	* @return String    返回类型
	 */
	@Permission(privilege={"prms:grant:edit","prms:grant:add"})
	@RequestMapping(value = "recommendsave",method=RequestMethod.POST)
	@ResponseBody
	public ResultVo recommendSave(@Valid AppRecommendAwards appRecommendAwards,BindingResult result, HttpSession session){
		ResultVo resultVo = new ResultVo();
		if(result.hasErrors()){		//验证失败说明
			resultVo.setStatus(Constant.RESULT_VALID);
			resultVo.setInfoMap(this.getValidErrors(result.getFieldErrors()));
			return resultVo;
		}
		try {
			if(StringUtil.isNotNull(appRecommendAwards.getId())){		//修改

				SysUser updateBy = RedisWebUtils.getLoginUser();
				appRecommendAwards.setLastUpdateBy(updateBy);
				appRecommendAwards.setLastUpdateDate(new Date());
			}else{
				//设置新增人信息
				SysUser createdBy = RedisWebUtils.getLoginUser();
				appRecommendAwards.setCreateBy(createdBy);
				appRecommendAwards.setCreateDate(new Date());
			}
			ServiceCode serviceCode = couponGrantService.saveOrUpdateRecommend(appRecommendAwards);
			resultVo.setResult(serviceCode,"/couponGrant/list.do");
			
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("促销系统-推荐有奖新增-ServiceException", e);
			throw e;
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("促销系统-推荐有奖新增-Exceptio", e);
			throw new ServiceException(e);
		}
		
		if(resultVo.isHasException()){ //异常返还
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}
}

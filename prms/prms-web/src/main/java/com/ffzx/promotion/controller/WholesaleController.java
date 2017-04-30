package com.ffzx.promotion.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.commodity.api.dto.Commodity;
import com.ffzx.commodity.api.dto.CommoditySku;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.consumer.CommodityApiConsumer;
import com.ffzx.promotion.api.service.consumer.CommoditySkuApiConsumer;
import com.ffzx.promotion.service.ActivityCommodityService;
import com.ffzx.promotion.service.ActivityCommoditySkuService;
import com.ffzx.promotion.service.ActivityManagerService;
import com.ffzx.promotion.service.GiftCommodityService;


/**
 * 批发活动管理控制器
 * @Description: 批发活动管理列查询表、编辑、启用/禁用、查看活动信息
 * @author sheng.shan
 * @email  sheng.shan@ffzxnet.com
 * @date 2016年5月16日 下午20:55:27
 * @version V1.0 
 */
@Controller
@RequestMapping("wholesaleActivity/*")
public class WholesaleController extends BaseController {

	/**
	 * 日志
	 */
	protected final Logger logger = LoggerFactory.getLogger(WholesaleController.class);
	
	@Autowired
	private ActivityManagerService activityManagerService;
	
	@Autowired
	private ActivityCommodityService activityCommodityService;
	
	@Autowired
	private ActivityCommoditySkuService activityCommoditySkuService;
	
	@Autowired
	private CommodityApiConsumer commodityApiConsumer;
	
	@Autowired
	private CommoditySkuApiConsumer commoditySkuApiConsumer;
	@Autowired
	private GiftCommodityService giftCommodityService;
	
	
	@RequestMapping(value = "list")
	//@Permission(privilege = {"prms:activity:view", "prms:activity:edit", "prms:activity:delete", "prms:activity:tg"})
	public String list(ActivityManager activity, ModelMap map){
		// 分页数据
		Page pageObj = this.getPageObj();
		activity.setActivityType(ActivityTypeEnum.WHOLESALE_MANAGER);
		// 活动列表数据
		List<ActivityManager> activityList = null;
		
		try {
			activityList = activityManagerService.findList(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, activity);
		} catch (ServiceException e) {
			logger.error("WholesaleController.list() ServiceException=》促销系统管理-批发活动列表", e);
			throw e;
		} catch (Exception e) {
			logger.error("WholesaleController.list() Exception=》促销系统管理-批发活动列表", e);
			throw new ServiceException(e);
		}
		// 设置页面显示
		map.put("activity", activity);
		map.put("dataList", activityList);
		map.put("pageObj", pageObj);
		return "activity/wholesale/wholesale_activity_list";
	}
	
	@RequestMapping(value = "/form")
	public String form(String id, ModelMap map){
		
		ActivityManager newUserActivity = null;
		//查询编辑数据
		try {
			if(StringUtil.isNotEmpty(id)){
				newUserActivity = activityManagerService.findById(id);
			}
		} catch (Exception e) {
			logger.error("WholesaleController.form() Exception=》系统管理-演示数据页面", e);
			throw new ServiceException(e);
		}
		
		map.put("activity",newUserActivity);
		map.put("image_path",System.getProperty("image.web.server"));
		return "activity/wholesale/wholesale_activity_form";
	}
	
	@RequestMapping(value = "/view")
	public String view(String id, ModelMap map){
		
		ActivityManager newUserActivity = null;
		//查询编辑数据
		try {
			if(StringUtil.isNotEmpty(id)){
				newUserActivity = activityManagerService.findById(id);
			}
		} catch (Exception e) {
			logger.error("WholesaleController.view() Exception=》系统管理-演示数据页面", e);
			throw new ServiceException(e);
		}
		
		map.put("activity",newUserActivity);
		map.put("image_path",System.getProperty("image.web.server"));
		return "activity/wholesale/wholesale_activity_view";
	}
	
	/**
	 * 更新新用户活动状态
	 * @param id
	 * @return
	 */
	@ResponseBody	
	@RequestMapping(value = "/updateStatus")
	public ResultVo updateStatus(String id, String releaseStatus) {
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		int resultInt = 0;
		ActivityManager activityManager = new ActivityManager();
		activityManager.setId(id);
		activityManager.setReleaseStatus(releaseStatus);
		activityManager.setLastUpdateBy(RedisWebUtils.getLoginUser());
		activityManager.setLastUpdateDate(new Date());
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("activity", activityManager);
		resultInt = activityCommodityService.findCount(params);
		
		if (resultInt > 0 || "0".equals(releaseStatus)) {
			//执行操作
			try {
				resultInt = 0;
				resultInt = activityManagerService.modifyById(activityManager);
			} catch (Exception e) {
				logger.error("WholesaleController.updateStatus() Exception=》批发活动管理-更新批发活动状态", e);
				throw new ServiceException(e);
			}
			
			//判断执行结课
			if(resultInt > 0) {
				resultVo.setStatus(Constant.RESULT_SCS);
				resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
				resultVo.setResult(ServiceResultCode.SUCCESS,"/wholesaleActivity/list.do");
			}else{
				resultVo.setStatus(Constant.RESULT_ERR);
				resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
				resultVo.setResult(ServiceResultCode.FAIL,"/wholesaleActivity/list.do");
			}
		} else {
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr("发布失败，请先在【商品设置】添加商品，再进行发布操作。");
		}
		return resultVo;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@ResponseBody	
	@RequestMapping(value = "/delete")
	public ResultVo delete(String id) {
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		int resultInt = 0;
		ActivityManager activityManager = new ActivityManager();
		//执行操作
		try {
			activityManager = activityManagerService.findById(id);
			activityManager.setDelFlag(Constant.DELTE_FLAG_YES);
			//删除之前判断商品设置是不是有商品
			Map<String, Object> params=new HashMap<>();
			params.put("activityNo", activityManager.getActivityNo());
			List<ActivityCommodity> activitycommodityList=this.activityCommodityService.findActivityCommdity(null, params);
			if(activitycommodityList !=null && activitycommodityList.size()!=0){
				resultVo.setStatus(Constant.RESULT_ERR);
				resultVo.setInfoStr("删除失败，请先在【商品设置】删除全部商品，再进行删除操作");
				resultVo.setResult(ServiceResultCode.FAIL,"/newUserActivity/list.do");
				return resultVo;
			}else{
				resultInt = activityManagerService.modifyById(activityManager);
			}
		} catch (Exception e) {
			logger.error("WholesaleController.detele() Exception=》活动管理-删除批发活动状态", e);
			throw new ServiceException(e);
		}
		
		//判断执行结课
		if(resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
			resultVo.setResult(ServiceResultCode.SUCCESS,"/wholesaleActivity/list.do");
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
			resultVo.setResult(ServiceResultCode.FAIL,"/wholesaleActivity/list.do");
		}
		return resultVo;
	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "save",method=RequestMethod.POST)@ResponseBody
	public ResultVo save(@Valid ActivityManager activityManager,BindingResult result, HttpSession session){


		ResultVo resultVo = new ResultVo();
		if(result.hasErrors()){		//验证失败说明
			resultVo.setStatus(Constant.RESULT_VALID);
			resultVo.setInfoMap(this.getValidErrors(result.getFieldErrors()));
			return resultVo;
		}
		try {
			if(StringUtil.isNotNull(activityManager.getId())){		//修改
				SysUser updateBy = RedisWebUtils.getLoginUser();
				activityManager.setLastUpdateBy(updateBy);
				activityManager.setLastUpdateDate(new Date());
			}else{
				//设置新增人信息
				SysUser createdBy = RedisWebUtils.getLoginUser();
				activityManager.setCreateBy(createdBy);
				activityManager.setCreateDate(new Date());
				activityManager.setLastUpdateBy(createdBy);
				activityManager.setLastUpdateDate(new Date());
				activityManager.setActivityType(ActivityTypeEnum.WHOLESALE_MANAGER);
			}
			ServiceCode serviceCode = activityManagerService.saveOrUpdate(activityManager);
			resultVo.setResult(serviceCode,"/wholesaleActivity/list.do");
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("WholesaleController-save-Exception=》促销系统-批发活动新增/编辑-Exception", e);
			throw e;
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("WholesaleController-save-Exception=》促销系统-批发活动新增/编辑-Exceptio", e);
			throw new ServiceException(e);
		}
		if(resultVo.isHasException()){ //异常返还
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}
	
	/**
	* 商品设置列表
	* @Title: CommodityList 
	* @Description: 显示普通活动对应的商品设置列表 
	* @param id 活动id
	* @return String    返回类型
	 */
	@RequestMapping(value = "commoditylist")
	public String CommodityList(String activityId,String activityNo,ActivityCommodity activityCommodity ,ModelMap map){
		Page pageObj = this.getPageObj();
		// 活动列表数据
		List<ActivityCommodity> activityCommodityList = null;
		if(StringUtil.isNotNull(activityId)){
			ActivityManager activityManager = new ActivityManager();
			activityManager.setId(activityId);
			if(StringUtil.isNotNull(activityNo)){
				if(!activityNo.equals("undefined")){
					activityManager.setActivityNo(activityNo);
				}
			}
			activityCommodity.setActivity(activityManager);
		}
		try {
			activityCommodityList = activityCommodityService.findList(pageObj,null,null, activityCommodity);
		} catch (ServiceException e) {
			logger.error("WholesaleController.CommodityList() ServiceException=》促销系统管理-批发活动商品设置列表", e);
			throw e;
		} catch (Exception e) {
			logger.error("WholesaleController.CommodityList() Exception=》促销系统管理-批发活动商品设置列表", e);
			throw new ServiceException(e);
		}
		map.put("activityCommodity",activityCommodity);
		map.put("dataList", activityCommodityList);
		map.put("pageObj", pageObj);
		return "activity/wholesale/wholesale_commodity_list";
	}
	
	/**
	 * 删除商品
	 * @param id
	 * @return
	 */
	@ResponseBody	
	@RequestMapping(value = "/commodityDelete")
	public ResultVo commodityDelete(String id) {
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		int resultInt = 0;
		//执行操作
		ActivityCommodity activityCommodity = null;
		try {
			activityCommodity = activityCommodityService.findById(id);
			if (null != activityCommodity) {
				resultInt = activityCommodityService.deleteCommandSku(id, activityCommodity.getCommodityNo());
			} else {
				logger.info("WholesaleController.CommodityDelete() =》未找到改数据的记录");
			}
		} catch (Exception e) {
			logger.error("WholesaleController.Commoditydetele() Exception=》活动管理-删除批发活动商品", e);
			throw new ServiceException(e);
		}
		
		//判断执行结课
		if(resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
			resultVo.setResult(ServiceResultCode.SUCCESS,"/wholesaleActivity/commoditylist.do?activityId="+activityCommodity.getActivity().getId());
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
			resultVo.setResult(ServiceResultCode.FAIL,"/wholesaleActivity/commoditylist.do?activityId="+activityCommodity.getActivity().getId());
		}
		return resultVo;
	}
	
	/**
	 * 批量删除商品
	 * @param id
	 * @return
	 */
	@ResponseBody	
	@RequestMapping(value = "/commodityDeletes")
	public ResultVo commodityDeletes(String ids) {
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		int resultInt = 0;
		String[] idArray = ids.split(",");
		//执行操作
		ActivityCommodity activityCommodity = activityCommodityService.findById(idArray[0]);
		try {
			resultInt = activityCommodityService.deleteCommandSkus(idArray);
		} catch (Exception e) {
			logger.error("WholesaleController.Commoditydeteles() Exception=》活动管理-批量删除批发活动商品", e);
			throw new ServiceException(e);
		}
		
		//判断执行结课
		if(resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
			resultVo.setResult(ServiceResultCode.SUCCESS,"/wholesaleActivity/commoditylist.do?activityId="+activityCommodity.getActivity().getId());
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
			resultVo.setResult(ServiceResultCode.FAIL,"/wholesaleActivity/commoditylist.do?activityId="+activityCommodity.getActivity().getId());
		}
		return resultVo;
	}
	
	/****
	 * 跳转商品新增
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "toSetCommdity")
	public String toSetCommdity(String activityId,ModelMap map,String activityNo,String activityCommodityId,String view){
		if(StringUtil.isNotNull(activityId)){
			try {
				//活动信息
				ActivityManager activity=this.activityManagerService.findById(activityId);
				map.put("activity", activity);
				if(StringUtil.isNotNull(this.getString("activityCommodityId"))){
					//编辑对象商品信息
					ActivityCommodity activityCommodity=this.activityCommodityService.findById(this.getString("activityCommodityId"));
					map.put("activityCommodity", activityCommodity);
					//编辑对象商品对应的sku
					Map<String, Object> params=new HashMap<>();
					params.put("activityCommodity", activityCommodity);
					List<ActivityCommoditySku> activityCommoditySkuList=this.activityCommoditySkuService.findByBiz(params);
					map.put("activityCommoditySkuList", activityCommoditySkuList);
				}				
			} catch (ServiceException e) {
				logger.error("WholesaleController-toSetCommdity-ServiceException=》批发管理-弹窗选择商品页面-ServiceException", e);
				throw e;
			}
		}
		map.put("view",view);
		return "activity/wholesale/wholesale_set_commodity";
	}
	
	
	/****
	 * 跳转商品列表
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "toCommdity")
	public String toCommdity(Commodity commodity,ModelMap map){
		Map<String, Object> params=new HashMap<>();
		Page pageObj = this.getPageObj();
		Map<String, Object> mapValue;
		List<Commodity> commodityList=null;
		if(StringUtil.isNotNull(commodity.getBarCode())){
			params.put("barCode",commodity.getBarCode());
		}
		if(StringUtil.isNotNull(commodity.getName())){
			params.put("commodityname",commodity.getName());
		}
		try {
			params.put("buyType", "COMMON_BUY");
			params.put("giveType", "0");
			Map<String, Object> giftMap=this.giftCommodityService.getMapValue();
			if(giftMap!=null){
				params.put("goodsId", giftMap.get("goodsId"));
				params.put("flag", Constant.YES);
			}
			mapValue = commodityApiConsumer.findList(pageObj, "create_date", Constant.ORDER_BY, params);
			if(mapValue!=null){
				pageObj=(Page) mapValue.get("page");
				commodityList = (List<Commodity>) mapValue.get("list");
			}
			map.put("commodity", commodity);
			map.put("commodityList", commodityList);
			map.put("pageObj", pageObj);
		} catch (ServiceException e) {
			logger.error("WholesaleController-toCommdity-ServiceException=》批发管理-弹窗选择商品页面-ServiceException", e);
			throw e;
		}		
		return "activity/wholesale/wholesale_select_goods";
	}
	
	/******
	 * 根据商品code获取sku
	 * @param commodityCode
	 * @return
	 */
	@RequestMapping(value="getCommoditySku")
	public void getCommoditySku(HttpServletResponse response,String commodityCode){
		//执行操作
		try {
			Map<String, Object> params=new HashMap<>();
			params.put("commodityCode", commodityCode);
			List<CommoditySku> skuList=commoditySkuApiConsumer.findList(params);
			this.responseWrite(response, this.getJsonByObject(skuList));
		} catch (Exception e) {
			logger.error("WholesaleController.getCommoditySku() Exception=》批发管理-获取sku-", e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 商品设置新增
	 * 
	 * @param activityCommodity
	 * @param result
	 * @return
	 */
	@RequestMapping(value="saveCommodity")
	@ResponseBody
	public ResultVo saveCommodity(ActivityCommodity activityCommodity, BindingResult result){
		ResultVo resultVo = new ResultVo();
		String viewStatus=null;//是否新增或者修改
		if (result.hasErrors()) {
			resultVo.setStatus(Constant.RESULT_VALID);
			resultVo.setInfoMap(this.getValidErrors(result.getFieldErrors()));
			return resultVo;
		}
		try {
			if(StringUtil.isNotNull(activityCommodity.getId())){
				viewStatus="edit";
			}else{
				viewStatus="save";
				activityCommodity.setId(UUIDGenerator.getUUID());
			}
			ActivityManager man=new ActivityManager();
			// 判断编辑时是否改动过商品
			if (StringUtils.isNotEmpty(activityCommodity.getNewCommodityId())) {
				//获取要添加的商品是否存在
				Map<String, Object> params=new HashMap<>();
				params.put("commodityId", activityCommodity.getCommodityId());
				List<ActivityCommodity> activitycommodityList=this.activityCommodityService.findActivityCommdity(null, params);	
				if(StringUtil.isNotNull(activitycommodityList)){
					for(ActivityCommodity co:activitycommodityList){
						//添加的商品是否已经参加了其他活动
						if(!co.getActivityType().getValue().equals("COMMON_BUY")){
							resultVo.setStatus(Constant.RESULT_ERR);
							resultVo.setInfoStr(com.ffzx.promotion.api.dto.constant.Constant.ERROR_RE);
							return resultVo;
						}
					}
				}		
			}
			man.setId(activityCommodity.getActivityId());
			activityCommodity.setActivity(man);
			activityCommodity.setActivityType(ActivityTypeEnum.WHOLESALE_MANAGER);
			//新增商品设置
			ServiceCode serviceCode = this.activityCommodityService.saveCommdity(activityCommodity,viewStatus);
			resultVo.setResult(serviceCode, "/wholesaleActivity/commoditylist.do?id="+activityCommodity.getActivity().getId()+"&activityNo="+activityCommodity.getActivityNo());
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("WholesaleController-saveCommodity-ServiceException=》商品设置-保存", e);
			throw e;
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("WholesaleController-saveCommodity-Exception=》商品设置-保存", e);
			throw new ServiceException(e);
		}
		if (resultVo.isHasException()) {
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}
	
}

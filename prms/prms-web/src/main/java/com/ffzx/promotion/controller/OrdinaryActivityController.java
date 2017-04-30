package com.ffzx.promotion.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil.ExportModel;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.commodity.api.dto.Commodity;
import com.ffzx.commodity.api.dto.CommoditySku;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.ActivityGive;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.UpdateActivityIndex;
import com.ffzx.promotion.api.service.consumer.CommodityApiConsumer;
import com.ffzx.promotion.api.service.consumer.CommoditySkuApiConsumer;
import com.ffzx.promotion.mapper.ActivityGiveMapper;
import com.ffzx.promotion.service.ActivityCommodityService;
import com.ffzx.promotion.service.ActivityCommoditySkuService;
import com.ffzx.promotion.service.ActivityManagerService;

import net.sf.json.JSONArray;

/**
 * 普通活动管理控制器
 * @Description: 活动管理列查询表、编辑、启用/禁用、查看活动信息
 * @author sheng.shan
 * @email  sheng.shan@ffzxnet.com
 * @date 2016年5月6日 下午5:23:27
 * @version V1.0 
 */
@Controller
@RequestMapping("ordinaryActivity/*")
public class OrdinaryActivityController extends BaseController {

	/**
	 * 日志
	 */
	protected final Logger logger = LoggerFactory.getLogger(OrdinaryActivityController.class);
	
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
	private ActivityGiveMapper activityGiveMapper;
	
	@Autowired
	private UpdateActivityIndex updateActivityIndex;
	
	/**
	* 普通活动列表
	* @Title: DataList 
	* @Description: 查询类型为普通活动管的列表数据
	* @param activity 查询条件
	* @param map
	* @return String    返回类型
	 */
	@RequestMapping(value = "dataList")
//	@Permission(privilege = {"prms:ordinaryactivity:add", "prms:ordinaryactivity:edit","prms:ordinaryactivity:delete", 
//			"prms:ordinaryactivity:change","prms:ordinaryactivity:revoke","prms:ordinaryactivity:release",
//			"prms:ordinaryactivity:goodsconfig","prms:ordinaryactivity:recommend"})
	public String DataList(ActivityManager activity, ModelMap map){
		// 分页数据
		Page pageObj = this.getPageObj();
		
		// 活动类型
		activity.setActivityType(ActivityTypeEnum.ORDINARY_ACTIVITY);
		
		// 活动状态
//		String releaseStatus = activity.getReleaseStatus();
//		if (!StringUtil.isNotNull(releaseStatus)) {
//			releaseStatus = this.getString("releaseStatu");
//			if (StringUtil.isNotNull(releaseStatus)) {
//				activity.setReleaseStatus(releaseStatus);
//			}
//		}
		
		// 活动列表数据
		List<ActivityManager> activityList = null;
		
		try {
			activityList = activityManagerService.findList(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, activity);
		} catch (ServiceException e) {
			logger.error("OrdinaryActivityController.DataList() ServiceException=》促销系统管理-活动列表", e);
			throw e;
		} catch (Exception e) {
			logger.error("OrdinaryActivityController.DataList() Exception=》促销系统管理-活动列表", e);
			throw new ServiceException(e);
		}
		
		// 设置页面显示
//		map.put("releaseStatu", releaseStatus);
		map.put("activity", activity);
		map.put("dataList", activityList);
		map.put("pageObj", pageObj);
		
		return "activity/ordinary/ordinary_activity_list";
	}
	
	/**
	* 普通活动信息查看
	* @Title: DataView 
	* @Description: 查看活动信息 
	* @param id 活动id
	* @param map
	* @return String    返回类型
	 */
	@RequestMapping(value = "dataView")
//	@Permission(privilege = {"prms:ordinaryactivity:view", "prms:activity:tg"})
	public String DataView(String id, ModelMap map){
		ActivityManager activity = null;
		try {
			// 判断是编辑还是新增
			if(StringUtil.isNotNull(id)){
				activity = activityManagerService.findById(id);
			}
			// 返回显示结果
			map.put("activity", activity);
		} catch (ServiceException e) {
			logger.error("OrdinaryActivityController.DataForm() ServiceException=》促销系统管理-活动数据编辑", e);
			throw e;
		} catch (Exception e) {
			logger.error("OrdinaryActivityController.DataForm() Exception=》促销系统管理-活动数据编辑", e);
			throw new ServiceException(e);
		}
		map.put("image_path",System.getProperty("image.web.server"));
		
		return "activity/ordinary/ordinary_activity_view";
	}
	
	/**
	* 普通活动信息编辑页面
	* @Title: DataView 
	* @Description: 编辑活动信息 
	* @param id 活动id
	* @param map
	* @return String    返回类型
	 */
	@RequestMapping(value = "dataForm")
//	@Permission(privilege = {"prms:activity:edit", "prms:activity:tg"})
	public String DataForm(String id, ModelMap map){
		ActivityManager activity = null;
		try {
			// 判断是编辑还是新增
			if(StringUtil.isNotNull(id)){
				activity = activityManagerService.findById(id);
			}
			// 返回显示结果
			map.put("activity", activity);
		} catch (ServiceException e) {
			logger.error("OrdinaryActivityController.DataForm() ServiceException=》促销系统管理-活动数据编辑", e);
			throw e;
		} catch (Exception e) {
			logger.error("OrdinaryActivityController.DataForm() Exception=》促销系统管理-活动数据编辑", e);
			throw new ServiceException(e);
		}
		map.put("image_path",System.getProperty("image.web.server"));
		
		return "activity/ordinary/ordinary_activity_form";
	}
	
	/**
	* 保存修改普通活动信息
	* @Title: DataSave 
	* @Description:  保存修改普通活动信息
	* @param activity 活动信息
	* @param result
	* @return ResultVo    返回类型
	 */
	@RequestMapping(value = "dataSave")
	@ResponseBody
//	@Permission(privilege = {"prms:activity:view", "prms:activity:edit", "prms:activity:delete", "prms:activity:tg"})
	public ResultVo DataSave(@Valid ActivityManager activity, BindingResult result){
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		
//		if(result.hasErrors()) {//验证错误  
//			//验证失败统一说明
//			resultVo.setStatus(Constant.RESULT_VALID_STR);
//			resultVo.setInfoStr(Constant.RESULT_VALID_MSG);
//			return resultVo;
//		}
		
		// 更新数据
		Date date = new Date();
		if (!StringUtil.isNotNull(activity.getId())) {
			activity.setCreateBy(RedisWebUtils.getLoginUser()); 	// 创建人
			activity.setCreateDate(date);							// 创建时间
			activity.setActivityType(ActivityTypeEnum.ORDINARY_ACTIVITY); // 活动类型
			activity.setReleaseStatus("0"); // 默认  0：未发布
		}
		
		activity.setLastUpdateBy(RedisWebUtils.getLoginUser());	// 最后更新人
		activity.setLastUpdateDate(date);						// 最后更新时间
		
		ServiceCode serviceCode = null;
		// 执行更新操作
		try {
			// 普通活动时间
			String startDateStr = this.getString("startDateStr");
			String endDateStr = this.getString("endDateStr");
			if (StringUtil.isNotNull(startDateStr)) {
				activity.setStartDate(DateUtil.parseTime(startDateStr));
			}
			if (StringUtil.isNotNull(endDateStr)) {
				activity.setEndDate(DateUtil.parseTime(endDateStr));
			}
			
			// 新增/修改
			serviceCode = activityManagerService.saveOrUpdate(activity);
			
			resultVo.setResult(serviceCode, "/ordinaryActivity/dataList.do");
			
			//更新es信息
			updateActivityIndex.activityResolved(activity.getId(), "1");
		} catch (ServiceException e) {
			logger.error("OrdinaryActivityController.DataSave() ServiceException=》促销系统管理-活动数据更新", e);
			throw e;
		} catch (Exception e) {
			logger.error("OrdinaryActivityController.DataSave() Exception=》促销系统管理-活动数据更新", e);
			throw new ServiceException(e);
		}
		
		return resultVo;
	}
	
	/**
	* 更新普通活动发布状态
	* @Title: UpdateStatus 
	* @Description: 更新普通活动发布状态 
	* @param id 活动id
	* @param releaseStatus
	* @return ResultVo    返回类型
	 */
	@RequestMapping("updateStatus")
	@ResponseBody
	public ResultVo UpdateStatus(String id, String releaseStatus){
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		// 实体对象
		ActivityManager activity = new ActivityManager();
		activity.setId(id); // 活动id
		activity.setReleaseStatus(releaseStatus); // 1：发布、 0：撤销
		activity.setLastUpdateBy(RedisWebUtils.getLoginUser());
		activity.setLastUpdateDate(new Date());
		
		int resultInt = 0;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("activity", activity);
		resultInt = activityCommodityService.findCount(params);
		if (resultInt > 0 || "0".equals(releaseStatus)) {
			// 执行修改操作
			try {
				resultInt = 0;
				resultInt = activityManagerService.modifyById(activity);
			} catch (ServiceException e) {
				logger.error("OrdinaryActivityController.UpdateStatus() Exception=》促销系统管理-活动数据发布/撤销", e);
				throw e;
			}
			// 判断执行结课
			if (resultInt > 0) {
				resultVo.setStatus(Constant.RESULT_SCS);
				resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
				resultVo.setUrl("/ordinaryActivity/dataList.do");
				//2016-09-14 add by ying.cai reason:一个活动发布或取消，则更新搜索引擎仓库数据
				updateActivityIndex.activityResolved(id, releaseStatus);
			} else {
				resultVo.setStatus(Constant.RESULT_ERR);
				resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
				resultVo.setResult(ServiceResultCode.FAIL,"/ordinaryActivity/dataList.do");
			}
		} else {
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr("发布失败，请先在【商品设置】添加商品，再进行发布操作。");
		}
		return resultVo;
	}
	
	/**
	* 删除活动
	* @Title: DataDelete 
	* @Description: 在普通活动列表中删除活动 
	* @param id
	* @return     
	* @return ResultVo    返回类型
	 */
	@RequestMapping("dataDelete")
	@ResponseBody	
//	@Permission(privilege = {"prms:activity:delete"})
	public ResultVo DataDelete(String id){
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		// 实体对象
		ActivityManager activity = new ActivityManager();
		int resultInt = 0;
		//执行操作
		try {
			activity = activityManagerService.findById(id);
			activity.setDelFlag(Constant.DELTE_FLAG_YES);
			//删除之前判断商品设置是不是有商品
			Map<String, Object> params=new HashMap<>();
			params.put("activityNo", activity.getActivityNo());
			List<ActivityCommodity> activitycommodityList=this.activityCommodityService.findActivityCommdity(null, params);
			if(activitycommodityList !=null && activitycommodityList.size()!=0){
				resultVo.setStatus(Constant.RESULT_ERR);
				resultVo.setInfoStr("删除失败，请先在【商品设置】删除全部商品，再进行删除操作");
				resultVo.setResult(ServiceResultCode.FAIL,"/newUserActivity/list.do");
				return resultVo;
			}else{
				resultInt = activityManagerService.modifyById(activity);
			}
		} catch (Exception e) {
			logger.error("OrdinaryActivityController.DataDelete() Exception=》促销系统管理-活动数据删除", e);
			throw new ServiceException(e);
		}
		
		// 判断执行结课
		if (resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
			resultVo.setUrl("/ordinaryActivity/dataList.do");
		} else {
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
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
	@RequestMapping(value = "commodityList")
//	@Permission(privilege = {"prms:activity:view", "prms:activity:edit", "prms:activity:delete", "prms:activity:tg"})
	public String CommodityList(ActivityCommodity activityCommodity,String activityId, ModelMap map){
		Page pageObj = this.getPageObj();
		
		// 活动列表数据
		List<ActivityCommodity> commodityList = null;
		
		// 活动类型
		activityCommodity.setActivityType(ActivityTypeEnum.ORDINARY_ACTIVITY);
		
		// 活动id
//		String id = this.getString("actId");
		if (StringUtil.isNotNull(activityId)) {
			ActivityManager activity = new ActivityManager();
			activity.setId(activityId);
			activityCommodity.setActivity(activity);
		}
		
		// 根据活动id查询，活动的商品设置
		try {
			commodityList = activityCommodityService.findList(
					pageObj, null, null, activityCommodity);
		} catch (ServiceException e) {
			logger.error("OrdinaryActivityController.CommodityList() ServiceException=》促销系统管理-活动商品设置列表", e);
			throw e;
		} catch (Exception e) {
			logger.error("OrdinaryActivityController.CommodityList() Exception=》促销系统管理-活动商品设置列表", e);
			throw new ServiceException(e);
		}
		map.put("size",commodityList.size());
		map.put("commodity", activityCommodity);
		map.put("dataList", commodityList);
		map.put("pageObj", pageObj);
		
		return "activity/ordinary/ordinary_commodity_list";
	}
	
	/**
	* 商品设置删除（单个）
	* @Title: CommodityDelete 
	* @Description: 通过id删除单条记录
	* @param id 商品设置id
	* @return ResultVo    返回类型
	 */
	@RequestMapping(value = "commodityDelete")
	@ResponseBody
	public ResultVo CommodityDelete(String id){
		// 返回结果
		ResultVo resultVo = new ResultVo();
		// 创建实体，赋值
		ActivityCommodity activityCommodity = new ActivityCommodity(); 
//		activityCommodity.setId(id);
		
		// 操作记录数
		int resultInt = 0;
		// 执行删除操作
		try {
			activityCommodity = activityCommodityService.findById(id);
			if (null != activityCommodity) {
				resultInt = activityCommodityService.deleteCommandSku(id, activityCommodity.getCommodityNo());
			} else {
				logger.info("OrdinaryActivityController.CommodityDelete() =》未找到改数据的记录");
			}
		} catch (ServiceException e) {
			logger.error("OrdinaryActivityController.CommodityDelete() ServiceException=》促销系统管理-活动商品设置删除", e);
			throw e;
		} catch (Exception e) {
			logger.error("OrdinaryActivityController.CommodityDelete() Exception=》促销系统管理-活动商品设置删除", e);
			throw new ServiceException(e);
		}
		
		// 判断执行结课
		if (resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
			resultVo.setUrl("/ordinaryActivity/commodityList.do?activityId="+activityCommodity.getActivity().getId());
		} else {
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
		}
		
		return resultVo;
	}
	
	/**
	* 批量删除
	* @Title: CommodityDeletes 
	* @Description: 根据选中的ids，删除对应的记录 
	* @param ids 选中的商品设置ids
	* @return ResultVo    返回类型
	 */
	@RequestMapping(value = "commodityDeletes")
	@ResponseBody
	public ResultVo CommodityDeletes(String ids){
		// 返回结果
		ResultVo resultVo = new ResultVo();
		
		// 操作记录数
		int resultInt = 0;
		// id数组
		String[] idArry = ids.split(",");
		// 创建实体
		ActivityCommodity activityCommodity = activityCommodityService.findById(idArry[0]); 
		
		// 执行删除操作
		try {
			resultInt = activityCommodityService.deleteCommandSkus(idArry);
		} catch (ServiceException e) {
			logger.error("OrdinaryActivityController.CommodityDeletes() ServiceException=》促销系统管理-活动商品设置删除", e);
			throw e;
		} catch (Exception e) {
			logger.error("OrdinaryActivityController.CommodityDeletes() Exception=》促销系统管理-活动商品设置删除", e);
			throw new ServiceException(e);
		}
		
		// 判断执行结课
		if (resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
			resultVo.setUrl("/ordinaryActivity/commodityList.do?activityId="+activityCommodity.getActivity().getId());
		} else {
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
		}
		return resultVo;
	}
	
	/**
	* 推荐商品列表
	* @Title: RecommendList 
	* @Description: 查询活动推荐商品 
	* @param id 活动id
	* @param map
	* @return String    返回类型
	 */
	@RequestMapping(value = "recommendList")
//	@Permission(privilege = {"prms:activity:view", "prms:activity:edit", "prms:activity:delete", "prms:activity:tg"})
	public String RecommendList(ModelMap map){
//		Page pageObj = this.getPageObj();
		// 活动列表数据
		List<ActivityCommodity> recommendList = null;
		// 活动类型
		ActivityCommodity activityCommodity = new ActivityCommodity();
		activityCommodity.setActivityType(ActivityTypeEnum.ORDINARY_ACTIVITY);
		// 活动id
		String acrivityId = this.getString("acrivityId");
		if (StringUtil.isNotNull(acrivityId)) {
			ActivityManager activity = new ActivityManager();
			activity.setId(acrivityId);
			activityCommodity.setActivity(activity);
		}
		// 推荐
		activityCommodity.setIsRecommend(Constant.YES);
		
		// 根据活动id查询，活动的商品设置
		try {
			recommendList = activityCommodityService.findList(
					null, null, null, activityCommodity);
		} catch (ServiceException e) {
			logger.error("OrdinaryActivityController.CommodityList() ServiceException=》促销系统管理-活动商品设置列表", e);
			throw e;
		} catch (Exception e) {
			logger.error("OrdinaryActivityController.CommodityList() Exception=》促销系统管理-活动商品设置列表", e);
			throw new ServiceException(e);
		}
		
		map.put("dataList", recommendList);
		map.put("acrivityId", acrivityId);
		map.put("image_path",System.getProperty("image.web.server"));
		
		return "activity/ordinary/ordinary_recommend_list";
	}
	
	/**
	* 更新推荐商品
	* @Title: RecommendSave 
	* @Description: 编辑保存推荐商品数据 
	* @param commoditys 推荐商品数组
	* @param id 活动id
	* @param result
	* @return ResultVo    返回类型
	 */
	@RequestMapping(value = "recommendSave")
	@ResponseBody
//	@Permission(privilege = {"prms:activity:view", "prms:activity:edit", "prms:activity:delete", "prms:activity:tg"})
	public ResultVo RecommendSave(String commoditys){
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		
		ServiceCode serviceCode = null;
		// 执行更新操作
		try {
			// 活动id
			String acrivityId = this.getString("acrivityId");
			// 推荐集合信息
			List<ActivityCommodity> commodityList = null;
			if (StringUtil.isNotNull(commoditys)) {
				commodityList = new ArrayList<ActivityCommodity>(JSONArray.toCollection(JSONArray.fromObject(commoditys), ActivityCommodity.class));
			}
			
			// 根据活动id修改所有设置商品
			serviceCode = activityCommodityService.updateRecommendInfo(commodityList, acrivityId);
			
			resultVo.setResult(serviceCode,"/ordinaryActivity/recommendList.do?acrivityId="+acrivityId);
		} catch (ServiceException e) {
			logger.error("OrdinaryActivityController.RecommendSave() ServiceException=》促销系统管理-推荐商品更新", e);
			throw e;
		} catch (Exception e) {
			logger.error("OrdinaryActivityController.RecommendSave() Exception=》促销系统管理-推荐商品更新", e);
			throw new ServiceException(e);
		}
		
		return resultVo;
	}
	
	
	@RequestMapping(value = "selectOrdinary")
//	@Permission(privilege = {"prms:activity:view", "prms:activity:edit", "prms:activity:delete", "prms:activity:tg"})
	public String SelectOrdinary(ActivityCommodity activityCommodity, ModelMap map){
		// 分页数据
		Page pageObj = this.getPageObj();
		
		// 活动类型
		activityCommodity.setActivityType(ActivityTypeEnum.ORDINARY_ACTIVITY);
		
		// 活动商品列表数据
		List<ActivityCommodity> commodityList = null;
		
		// 给活动id赋值
		String activityId = this.getString("activityId");
		// 活动id
		if (StringUtil.isNotNull(activityId)) {
			ActivityManager activity = new ActivityManager();
			activity.setId(activityId);
			activityCommodity.setActivity(activity);
		}
		
		// 根据活动id查询，活动的商品设置
		try {
			commodityList = activityCommodityService.findList(
					pageObj, null, null, activityCommodity);
		} catch (ServiceException e) {
			logger.error("OrdinaryActivityController.CommodityList() ServiceException=》促销系统管理-活动商品设置列表", e);
			throw e;
		} catch (Exception e) {
			logger.error("OrdinaryActivityController.CommodityList() Exception=》促销系统管理-活动商品设置列表", e);
			throw new ServiceException(e);
		}
		
		map.put("commodity", activityCommodity);
		map.put("dataList", commodityList);
		map.put("pageObj", pageObj);
		
		return "activity/ordinary/ordinary_select_activity";
	}
	
	/**
	* 进入商品设置的编辑页面
	* @Title: CommdityFrom 
	* @param map
	* @return String    返回类型
	 */
	@RequestMapping(value = "commdityFrom")
	public String CommdityFrom(ModelMap map,String view){
		// 商品设置id
		String id = this.getString("id");
		// 活动id
		String actId = this.getString("activityId");
		// 活动编号
//		String activityNo = this.getString("activityNo");
		// 创建商品设置实体
		ActivityCommodity activityCommodity = null;
		// itme集合
		List<ActivityCommoditySku> commoditySkuList = null;
		// 编辑
		try {
			if(StringUtil.isNotNull(id)){
				// 商品设置信息
				activityCommodity = activityCommodityService.findById(id);
				// 查询过滤条件
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("activityCommodity", activityCommodity);
				// 商品设置Item信息
				commoditySkuList = activityCommoditySkuService.findByBiz(params);
			}
		} catch (ServiceException e) {
			logger.error("OrdinaryActivityController.CommdityFrom() ServiceException=》促销系统管理-活动商品设置编辑", e);
			throw e;
		} catch (Exception e) {
			logger.error("OrdinaryActivityController.CommdityFrom() Exception=》促销系统管理-活动商品设置编辑", e);
			throw new ServiceException(e);
		}
		
		map.put("id", id);
		map.put("activityId", actId);
		map.put("commodity", activityCommodity);
		map.put("dataList", commoditySkuList);
		map.put("view",view);
		
		return "activity/ordinary/ordinary_commodity_form";
	}
	
	
	@RequestMapping(value="commoditySave")
	@ResponseBody
	public ResultVo commoditySave(ActivityCommodity activityCommodity, BindingResult result){
		ResultVo resultVo = new ResultVo();
		if (result.hasErrors()) {
			resultVo.setStatus(Constant.RESULT_VALID);
			resultVo.setInfoMap(this.getValidErrors(result.getFieldErrors()));
			return resultVo;
		}
		
		ServiceCode serviceCode = null;
		
		try {
			// 判断商品设置id是否存在，存在：编辑
			String viewStatus = "";
			if (StringUtil.isNotNull(activityCommodity.getId())) {
				viewStatus = "edit";
			} else {
				viewStatus = "save";
				activityCommodity.setId(UUIDGenerator.getUUID());
			}
			
			//获取要添加的商品是否存在
			Map<String, Object> params=new HashMap<>();
			params.put("commodityId", activityCommodity.getCommodityId());
			List<ActivityCommodity> activitycommodityList=this.activityCommodityService.findActivityCommdity(null, params);	
			//查询该商品是否参加了买赠活动
			params.put("delFlag", Constant.NO);
			List<ActivityGive> activityGiveList=this.activityGiveMapper.selectByParams(params);
			if(activityGiveList!=null && activityGiveList.size()!=0){
				for(ActivityGive give:activityGiveList){
					//如果是0 表示该商品参加了买赠 但是未启用 不可以添加到普通活动
					if(give.getActFlag().equals(Constant.NO)){
						resultVo.setStatus(Constant.RESULT_ERR);
						resultVo.setInfoStr(com.ffzx.promotion.api.dto.constant.Constant.GIVE_PC);
						return resultVo;
					}
				}
			}
			if(StringUtil.isNotNull(activitycommodityList)){
				for(ActivityCommodity co:activitycommodityList){
					//添加的商品是否已经参加了其他活动
					if(!co.getActivityType().equals(ActivityTypeEnum.ORDINARY_ACTIVITY)){
						resultVo.setStatus(Constant.RESULT_ERR);
						resultVo.setInfoStr(com.ffzx.promotion.api.dto.constant.Constant.ERROR_RE);
						return resultVo;
					}
				}			
			}
			// 获取活动对象
			ActivityManager activity = activityManagerService.findById(activityCommodity.getActivity().getId());
			// 赋值
			activityCommodity.setActivity(activity);
			activityCommodity.setActivityNo(activity.getActivityNo());
			activityCommodity.setActivityType(ActivityTypeEnum.ORDINARY_ACTIVITY);
			
			//新增商品设置
			serviceCode = this.activityCommodityService.saveCommdity(activityCommodity,viewStatus);
			resultVo.setResult(serviceCode, "/ordinaryActivity/commodityList.do?activityId="+activityCommodity.getActivity().getId());
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("newUserActivityController-save-ServiceException=》商品设置-保存", e);
			throw e;
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("newUserActivityController-save-Exception=》商品设置-保存", e);
			throw new ServiceException(e);
		}
		if (resultVo.isHasException()) {
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		//修改或者新增成功，往ES推送变更信息  add by ying.cai
		updateActivityIndex.updateCommodity(activityCommodity.getId());
		return resultVo;
	}
	
	/****
	 * 跳转商品列表
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "toSelectCommdity")
	public String toSelectCommdity(Commodity commodity,ModelMap map){
		Map<String, Object> params=new HashMap<>();
		Page pageObj = this.getPageObj();
		Map<String, Object> mapValue;
		List<Commodity> commodityList =null;
		if(StringUtil.isNotNull(commodity.getBarCode())){
			params.put("barCode",commodity.getBarCode());
		}
		if(StringUtil.isNotNull(commodity.getName())){
			params.put("commodityname",commodity.getName());
		}
		try {
			params.put("buyType", "COMMON_BUY");
			mapValue = commodityApiConsumer.findList(pageObj, "create_date", Constant.ORDER_BY, params);
			if(mapValue!=null){
				pageObj=(Page) mapValue.get("page");
				commodityList = (List<Commodity>) mapValue.get("list");
			}
			map.put("commodity", commodity);
			map.put("commodityList", commodityList);
			map.put("pageObj", pageObj);
		} catch (ServiceException e) {
			logger.error("OrdinaryActivityController-toSelectCommdity-ServiceException=》普通活动管理-商品设置-弹窗选择商品页面-ServiceException", e);
			throw e;
		}		
		return "activity/ordinary/ordinary_select_goods";
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
			logger.error("OrdinaryActivityController.getCommoditySku() Exception=》普通活动管理-获取sku-", e);
			throw e;
		}
	}
	
	
	@RequestMapping(value = "ordinaryActivityCommodityExport")
	public void ordinaryActivityCommodityExport(ActivityCommodity activityCommodity,HttpServletRequest request,HttpServletResponse response){
		final String[] headers = new String[]{"商品条形码","标题","置顶序号","活动优惠价"};
		final String[] properties = new String[]{"commodityBarcode","activityTitle","sortTopNo","showPrice"};
		final String fileName = "普通活动商品列表";
		try {
			final List<ActivityCommodity> activityCommodityList=activityCommodityService.findList(null,null,null, activityCommodity);
			ExcelExportUtil.exportXls(new ExportModel() {
				public String[] getProperties() {
					return properties;
				}
				public String[] getHeaders() {
					return headers;
				}
				public List getData() {
					return activityCommodityList;
				}
				public String getFileName() {
					return fileName;
				}
			},response);
		} catch (IOException e) {
			logger.error("PresellActivityController.ordinaryActivityCommodityExport() Exception=》普通活动管理-导出-", e);
			throw new ServiceException(e);
		}
	}
	
}

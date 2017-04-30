package com.ffzx.promotion.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.system.entity.SysUser;
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
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.dto.ActivityPreSaleTag;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.UpdateActivityIndex;
import com.ffzx.promotion.api.service.consumer.CommodityApiConsumer;
import com.ffzx.promotion.api.service.consumer.CommoditySkuApiConsumer;
import com.ffzx.promotion.api.service.consumer.StockManagerApiConsumer;
import com.ffzx.promotion.model.CommoditySkuTransform;
import com.ffzx.promotion.service.ActivityCommodityService;
import com.ffzx.promotion.service.ActivityCommoditySkuService;
import com.ffzx.promotion.service.ActivityManagerService;
import com.ffzx.promotion.service.ActivityPreSaleTagService;
import com.ffzx.promotion.service.GiftCommodityService;

/**
 * 预售活动管理控制器
 * @Description: 活动管理列查询表、编辑、启用/禁用
 * @author lushi.guo
 * @date 2016年5月11日
 * @version V1.0 
 */
@Controller
@RequestMapping("presellActivity/*")
public class PresellActivityController extends BaseController {

	/**
	 * 日志
	 */
	protected final Logger logger = LoggerFactory.getLogger(PresellActivityController.class);
	
	@Autowired
	private ActivityManagerService activityManagerService;
	@Autowired
	private ActivityCommodityService activityCommodityService;
	@Autowired
	private CommodityApiConsumer commodityApiConsumer;
	@Autowired
	private CommoditySkuApiConsumer commoditySkuApiConsumer;
	@Autowired
	private ActivityCommoditySkuService activityCommoditySkuService;
	@Autowired
	private StockManagerApiConsumer stockManagerApiConsumer;
	@Autowired
	private ActivityPreSaleTagService activityPreSaleTagService;
	@Autowired
	private GiftCommodityService giftCommodityService;
	@Autowired
	private UpdateActivityIndex updateActivityIndex;
	
	@RequestMapping(value = "dataList")
//	@Permission(privilege = {"prms:activity:view", "prms:activity:edit", "prms:activity:delete", "prms:activity:tg"})
	public String DataList(ActivityManager activity, ModelMap map){
		// 分页数据
		Page pageObj = this.getPageObj();
		// 活动类型
		activity.setActivityType(ActivityTypeEnum.PRE_SALE);
//		String status=this.getString("status");
//		if(StringUtil.isNotNull(status)){
//			if(!status.equals("-1")){
//				activity.setReleaseStatus(status);
//			}
//		}
		// 活动列表数据
		List<ActivityManager> activityList = null;
		
		try {
			activityList = activityManagerService.findList(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, activity);
		} catch (ServiceException e) {
			logger.error("PresellActivityController.DataList() ServiceException=》促销系统管理-活动列表", e);
			throw e;
		} catch (Exception e) {
			logger.error("PresellActivityController.DataList() Exception=》促销系统管理-活动列表", e);
			throw new ServiceException(e);
		}
//		// 设置页面显示
//		if (tblshow == null || tblshow.equals("")) {
//			
//			map.put("tblshow", "-1");
//		} else {
//			map.put("tblshow", tblshow);
//		}
		map.put("activity", activity);
		map.put("dataList", activityList);
		map.put("pageObj", pageObj);
		
		return "activity/presale_activity_list";
	}
	
	@RequestMapping(value = "dataform")
//	@Permission(privilege = {"prms:activity:view", "prms:activity:edit", "prms:activity:delete", "prms:activity:tg"})
	public String DataForm(String id, ModelMap map){
		
		try {
			// 判断是编辑还是新增
			if(StringUtil.isNotNull(id)){
				
			}
//		} catch (ServiceException e) {
//			logger.info("MemberAddressController.form()-ServiceException=》收货地址管理-加载新增/编辑页面-ServiceException", e);
		} catch (Exception e) {
			logger.info("PresellActivityController.form()-Exception=》收货地址管理-加载新增/编辑页面-Exception", e);
			throw new ServiceException(e);
		}
		
		return "activity/ordinaryActivity_form";
	}
	
	@RequestMapping(value = "datasave")
//	@Permission(privilege = {"prms:activity:view", "prms:activity:edit", "prms:activity:delete", "prms:activity:tg"})
	public ResultVo DataSave(@Valid ActivityManager activity, BindingResult result){
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		
		if(result.hasErrors()) {//验证错误  
			//验证失败统一说明
			resultVo.setStatus(Constant.RESULT_VALID_STR);
			resultVo.setInfoStr(Constant.RESULT_VALID_MSG);
			return resultVo;
		}
		
		// 更新数据
		activity.setCreateBy(RedisWebUtils.getLoginUser());
		activity.setLastUpdateBy(RedisWebUtils.getLoginUser());
		
		// 执行更新操作
		
		
		return resultVo;
	}
	
	@RequestMapping("dataDelete")
	@ResponseBody	
//	@Permission(privilege = {"prms:activity:delete"})
	public ResultVo DataDelete(String id){
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		
		int resultInt = 0;
		//执行操作
		try {
//			resultInt = memberAddressService.deleteById(id);
		} catch (Exception e) {
			logger.error("PresellActivityController.delete() Exception=》会员管理-收货人地址-删除演示数据", e);
			throw new ServiceException(e);
		}
		
		// 判断执行结课
		if (resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
			resultVo.setUrl("");
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
	@RequestMapping(value = "commoditylist")
	//@Permission(privilege = {"prms:activity:view", "prms:activity:edit", "prms:activity:delete", "prms:activity:tg"})
	public String CommodityList(ModelMap map,ActivityCommodity commodity,String tblshow,String activityId,String activityNo){
		Page pageObj = this.getPageObj();
		List<ActivityCommodity> activitycommodityList=null;		
			try {
				Map<String, Object> params=new HashMap<>();
				if(StringUtil.isNotNull(activityId)||StringUtil.isNotNull(activityNo)){
						ActivityManager activityManager = new ActivityManager();
						activityManager.setId(activityId);
						activityManager.setActivityNo(activityNo);
						commodity.setActivity(activityManager);
						params.put("activity", activityManager);
					}					
				params.put("activityStatus", tblshow);
				params.put("nowDate", new Date());
				params.put("orderByField", "sort_no");
				params.put("orderBy", Constant.ORDER_BY);
				params.put("activityTitle", commodity.getActivityTitle());
				params.put("commodityBarcode",commodity.getCommodityBarcode());			
				params.put("activityType",ActivityTypeEnum.PRE_SALE.getValue());	
				activitycommodityList=this.activityCommodityService.findActivityCommdity(pageObj, params);
			} catch (ServiceException e) {
				logger.error("PresellActivityController-commoditylist-ServiceException=》预售管理-跳转商品设置页面-ServiceException", e);
				throw e;
			}
		// 设置页面显示
				if (tblshow ==null || tblshow.equals("")) {
					map.put("tblshow", "-1");
				} else {
					map.put("tblshow", tblshow);
				}
		map.put("size", activitycommodityList.size());
		map.put("commodityList", activitycommodityList);
		map.put("activityCommodity", commodity);
		map.put("pageObj", pageObj);
		map.put("activityTitle", commodity.getActivityTitle());
		
		map.put("commodityBarcode",commodity.getCommodityBarcode());
		return "activity/presale_commodity_list";
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
				Map<String, Object> mapvalue=activityCommodityService.toSetCommdity(activityId,this.getString("activityCommodityId"));
				Map<String, Object> tagmap=new HashMap<String, Object>();
				tagmap.put("delFlag", "0");
				List<ActivityPreSaleTag> tagList=this.activityPreSaleTagService.findList(null, null, null, tagmap);
				map.put("activity", mapvalue.get("activity"));
				map.put("activityCommodity", mapvalue.get("activityCommodity"));
				map.put("activityCommoditySkuList", mapvalue.get("activityCommoditySkuList"));
				map.put("image_path",System.getProperty("image.web.server"));		
				map.put("view", view);					
				map.put("tagList", tagList);
			} catch (ServiceException e) {
				logger.error("PresellActivityController-toSetCommdity-ServiceException=》预售管理-弹窗选择商品页面-ServiceException", e);
				throw e;
			}
		}
		return "activity/activity_set_commodity";
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
		List<Commodity> commodityList =null;
		if(StringUtil.isNotNull(commodity.getBarCode())){
			params.put("barCode",commodity.getBarCode());
		}
		if(StringUtil.isNotNull(commodity.getName())){
			params.put("commodityname",commodity.getName());
		}
		try {
			String [] buyTypes= {"PRE_SALE","COMMON_BUY"};
			params.put("buyTypes", buyTypes);
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
			logger.error("PresellActivityController-toCommdity-ServiceException=》预售管理-弹窗选择商品页面-ServiceException", e);
			throw e;
		}		
		return "activity/activity_select_goods";
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
			List<CommoditySkuTransform> skuTransformList= activityCommoditySkuService.getCommoditySku(commodityCode);
				this.responseWrite(response, this.getJsonByObject(skuTransformList));			
		} catch (Exception e) {
			logger.error("PresellActivityController.getCommoditySku() Exception=》预售管理-获取sku-", e);
			throw new ServiceException(e);
		}
	}
	
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
			if(StringUtils.isNotEmpty(activityCommodity.getId())){
				viewStatus="edit";
				if(StringUtils.isNotEmpty(activityCommodity.getNewCommodityId())){
					activityCommodity.setCommodityId(activityCommodity.getNewCommodityId());
					viewStatus="saveandedit";
				}				
			}else{
				viewStatus="save";
				if(StringUtils.isNotEmpty(activityCommodity.getNewCommodityId())){
					activityCommodity.setCommodityId(activityCommodity.getNewCommodityId());
				}
				activityCommodity.setId(UUIDGenerator.getUUID());
			}
			ActivityManager man=new ActivityManager();
			//获取要添加的商品是否存在
			Map<String, Object> params=new HashMap<>();
			params.put("commodityId", activityCommodity.getCommodityId());
			List<ActivityCommodity> activitycommodityList=this.activityCommodityService.findActivityCommdity(null, params);						
			Map<String, Object> resultMap=this.activityCommodityService.validateActivityCommodity(activityCommodity, resultVo, activitycommodityList,ActivityTypeEnum.PRE_SALE,viewStatus);			
			//当前设置的开始结束时间和预售批次重合
			if(resultMap!=null){
				resultVo.setStatus(Constant.RESULT_ERR);
				resultVo.setInfoStr(resultMap.get("msg").toString());
				return resultVo;
			}
			man.setId(activityCommodity.getActivityId());
			activityCommodity.setActivity(man);
			activityCommodity.setActivityType(ActivityTypeEnum.PRE_SALE);
			//如果有预售标签，先验证改标签是否存在
			if(activityCommodity.getPreSaleTag().getId()!=null){
				String id=activityCommodity.getPreSaleTag().getId();
				ActivityPreSaleTag tag=this.activityPreSaleTagService.findById(id);
				if(tag.getDelFlag().equals("1")){
					resultVo.setStatus(Constant.RESULT_EXC);
					resultVo.setInfoStr("保存失败，原因:选中的【"+tag.getName()+"】已经不存在啦，请修改后重试！");
				}else{
					//新增商品设置
					ServiceCode serviceCode = this.activityCommodityService.saveCommdity(activityCommodity,viewStatus);
					resultVo.setResult(serviceCode, "/presellActivity/commoditylist.do?activityId="+activityCommodity.getActivity().getId()+"&activityNo="+activityCommodity.getActivityNo());
				}
			}			
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("PresellActivityController-save-ServiceException=》商品设置-保存", e);
			throw e;
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("PresellActivityController-save-Exception=》商品设置-保存", e);
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
				logger.info("PresellActivityController.CommodityDelete() =》未找到改数据的记录");
			}
		} catch (Exception e) {
			logger.error("PresellActivityController.CommodityDelete() Exception=》活动管理-删除预售活动商品", e);
			throw new ServiceException(e);
		}
		
		//判断执行结课
		if(resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
			resultVo.setResult(ServiceResultCode.SUCCESS,"/presellActivity/commoditylist.do?activityId="+activityCommodity.getActivity().getId());
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
			resultVo.setResult(ServiceResultCode.FAIL,"/presellActivity/commoditylist.do?activityId="+activityCommodity.getActivity().getId());
		}
		return resultVo;
	}
	
	/**
	 * 删除商品
	 * @param id
	 * @return
	 */
	@ResponseBody	
	@RequestMapping(value = "/commodityDeletes")
	public ResultVo commodityDeletes(String ids) {
		//返回结果对象
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
		} catch (Exception e) {
			logger.error("PresellActivityController.CommodityDeletes() Exception=》活动管理-批量删除预售活动商品", e);
			throw new ServiceException(e);
		}
		
		//判断执行结课
		if(resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
			resultVo.setResult(ServiceResultCode.SUCCESS,"/presellActivity/commoditylist.do?activityId="+activityCommodity.getActivity().getId());
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
			resultVo.setResult(ServiceResultCode.FAIL,"/presellActivity/commoditylist.do?activityId="+activityCommodity.getActivity().getId());
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
			logger.error("PresellActivityController.delete() Exception=》活动管理-删除预售享活动状态", e);
			throw new ServiceException(e);
		}
		
		//判断执行结课
		if(resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
			resultVo.setResult(ServiceResultCode.SUCCESS,"/presellActivity/dataList.do");
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
			resultVo.setResult(ServiceResultCode.FAIL,"/presellActivity/dataList.do");
		}
		return resultVo;
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
				resultInt = activityManagerService.updateActivityStatus(activityManager);
				
			} catch (Exception e) {
				logger.error("PresellActivityController.update() Exception=》活动管理-更新预售活动状态", e);
				throw new ServiceException(e);
			}
			
			//判断执行结课
			if(resultInt > 0) {
				resultVo.setStatus(Constant.RESULT_SCS);
				resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
				resultVo.setResult(ServiceResultCode.SUCCESS,"/presellActivity/dataList.do");	
				//2016-09-14 add by ying.cai reason:一个活动发布或取消，则更新搜索引擎仓库数据
				updateActivityIndex.activityResolved(id, releaseStatus);
			}else{
				resultVo.setStatus(Constant.RESULT_ERR);
				resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
				resultVo.setResult(ServiceResultCode.FAIL,"/presellActivity/dataList.do");
			}
		} else {
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr("发布失败，请先在【商品设置】添加商品，再进行发布操作。");
		}
		
		return resultVo;
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
			logger.error("PageDemo.editUI() Exception=》系统管理-演示数据页面", e);
			throw new ServiceException(e);
		}
		
		map.put("activity",newUserActivity);
		map.put("image_path",System.getProperty("image.web.server"));
		return "activity/presale_activity_form";
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
			logger.error("PageDemo.editUI() Exception=》系统管理-演示数据页面", e);
			throw new ServiceException(e);
		}
		
		map.put("activity",newUserActivity);
		map.put("image_path",System.getProperty("image.web.server"));
		return "activity/presale_activity_view";
	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "save",method=RequestMethod.POST)@ResponseBody
	public ResultVo save(ActivityManager activityManager,BindingResult result, HttpSession session){

		ResultVo resultVo = new ResultVo();
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
				activityManager.setLastUpdateDate(new Date());
				activityManager.setActivityType(ActivityTypeEnum.PRE_SALE);
			}
			String startDateStr = this.getString("startDateStr");
			String endDateStr = this.getString("endDateStr");
			if (StringUtil.isNotNull(startDateStr)) {
				activityManager.setStartDate(DateUtil.parseTime(startDateStr));
			}
			if (StringUtil.isNotNull(endDateStr)) {
				activityManager.setEndDate(DateUtil.parseTime(endDateStr));
			}
			ServiceCode serviceCode = activityManagerService.saveOrUpdate(activityManager);
			resultVo.setResult(serviceCode,"/presellActivity/dataList.do");
			//更新es信息
			updateActivityIndex.activityResolved(activityManager.getId(), "1");
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("PresellActivityController-save-Exception=》促销系统-预售新增/编辑-Exception", e);
			throw e;
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("PresellActivityController-save-Exception=》促销系统-预售新增/编辑-Exceptio", e);
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
	 * 预售商品设置导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/activityCommodityExport")
	public void activityCommodityExport(ActivityCommodity activityCommodity,HttpServletRequest request,HttpServletResponse response){
		final String[] headers = new String[]{"商品条形码","标题","所属标签","开始时间","结束时间","置顶序号","活动优惠价","限定数量","ID限购量","销量增量值","启用特殊增量值","排序号","发货时间"};
		final String[] properties = new String[]{"commodityBarcode","activityTitle","preSaleTag.name","startDate","endDate","sortTopNo","showPrice","limitCount","idLimitCount","saleIncrease","enableSpecialCountStr","sortNo","deliverDate"};
		final String fileName = "预售活动商品列表";
		try {
			Page pageObj = this.getPageObj();
			String activityId = activityCommodity.getActivityId();
			String activityNo = activityCommodity.getActivityNo();
			Map<String, Object> params=new HashMap<>();
			if(StringUtil.isNotNull(activityId)||StringUtil.isNotNull(activityNo)){
				ActivityManager activityManager = new ActivityManager();
				activityManager.setId(activityId);
				activityManager.setActivityNo(activityNo);
				activityCommodity.setActivity(activityManager);
				params.put("activity", activityManager);
			}
			params.put("orderByField", "sort_no");
			params.put("orderBy", Constant.ORDER_BY);
			params.put("activityTitle", activityCommodity.getActivityTitle());
			params.put("number",activityCommodity.getCommodityBarcode());
			params.put("activityType", ActivityTypeEnum.PRE_SALE.getValue());
			
			final List<ActivityCommodity> activityCommodityList = this.activityCommodityService.findActivityCommdity(null, params);
			if(activityCommodityList != null && activityCommodityList.size() !=0 ){
				for(ActivityCommodity ac : activityCommodityList){
					if(ac.getEnableSpecialCount().equals("0")){
						ac.setEnableSpecialCountStr("否");
					}else{
						ac.setEnableSpecialCountStr("是");
					}
				}
			}
			System.out.println(activityCommodityList);
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
			logger.error("", e);
			throw new ServiceException(e);
		}
	}
}

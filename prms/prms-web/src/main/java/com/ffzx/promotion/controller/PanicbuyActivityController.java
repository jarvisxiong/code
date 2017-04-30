package com.ffzx.promotion.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.ffzx.commerce.framework.utils.ExcelReader;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commerce.framework.utils.UploadFileUtils;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.commodity.api.dto.Commodity;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.UpdateActivityIndex;
import com.ffzx.promotion.api.service.consumer.CommodityApiConsumer;
import com.ffzx.promotion.api.service.consumer.CommoditySkuApiConsumer;
import com.ffzx.promotion.model.CommoditySkuTransform;
import com.ffzx.promotion.service.ActivityCommodityService;
import com.ffzx.promotion.service.ActivityCommoditySkuService;
import com.ffzx.promotion.service.ActivityManagerService;
import com.ffzx.promotion.service.GiftCommodityService;

/**
 * 抢购管理控制器
 * @Description: 抢购查询表、编辑、启用/禁用
 * @Description: TODO
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月11日 上午9:27:57
 * @version V1.0 
 * @version V1.0 
 */
@Controller
@RequestMapping("panicbuyActivity/*")
public class PanicbuyActivityController extends BaseController {

	/**
	 * 日志
	 */
	protected final Logger logger = LoggerFactory.getLogger(PanicbuyActivityController.class);

	@Autowired
	private CommodityApiConsumer commodityApiConsumer;
	@Autowired
	private ActivityCommoditySkuService activityCommoditySkuService;
	@Autowired
	private ActivityManagerService activityManagerService;
	@Autowired
	private ActivityCommodityService activityCommodityService;
    @Autowired
    private CommoditySkuApiConsumer commoditySkuApiConsumer;
	@Autowired
	private GiftCommodityService giftCommodityService;

	@Autowired
	private UpdateActivityIndex updateActivityIndex;
	
	/**
	 * 
	 * @param path "/WEB-INF/template/xxx.xls"
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="downFile")
	public void downFile(@RequestParam(required=true,value="path")String path, HttpServletResponse response) throws IOException{
		if(!StringUtil.isEmpty(path)){
			String[] pathParts = path.split("/");
			String realPath = this.getRequest().getSession().getServletContext().getRealPath("");
			for(int i = 0 ; i < pathParts.length ; i ++){
				realPath += File.separator + pathParts[i] ;
			}
			File file = new File(realPath);
			
			// 取得文件名。
			String filename = path.substring(path.lastIndexOf("/")+1,path.length());
			
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gb2312"),"iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			
			    FileInputStream fis=new FileInputStream(file);
		        BufferedInputStream buff=new BufferedInputStream(fis);

		        byte [] b=new byte[1024];//相当于我们的缓存

		        long k=0;//该值用于计算当前实际下载了多少字节

		        //从response对象中得到输出流,准备下载

		        OutputStream myout=response.getOutputStream();

		        //开始循环下载

		        while(k<file.length()){

		            int j=buff.read(b,0,1024);
		            k+=j;

		            //将b中的数据写到客户端的内存
		            myout.write(b,0,j);

		        }

		        //将写入到客户端的内存的数据,刷新到磁盘
		        myout.flush();

		}

	}
	
	@RequestMapping(value = "dataList")
//	@Permission(privilege = {"prms:activity:view", "prms:activity:edit", "prms:activity:delete", "prms:activity:tg"})
	public String DataList(ActivityManager activity, ModelMap map){
		// 分页数据
		Page pageObj = this.getPageObj();
		activity.setActivityType(ActivityTypeEnum.PANIC_BUY);

//		// 活动状态
//		String releaseStatus = this.getString("releaseStatus");
//		if (StringUtil.isNotNull(releaseStatus)) {
//			activity.setReleaseStatus(releaseStatus);
//		}
		
		// 活动列表数据
		List<ActivityManager> activityList = null;
		
		try {
			activityList = activityManagerService.findList(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, activity);
		} catch (ServiceException e) {
			logger.error("PanicbuyActivityController.DataList() ServiceException=》促销系统管理-活动列表", e);
			throw e;
		} catch (Exception e) {
			logger.error("PanicbuyActivityController.DataList() Exception=》促销系统管理-活动列表", e);
			throw new ServiceException(e);
		}
		// 设置页面显示
		map.put("activity", activity);
		map.put("dataList", activityList);
		map.put("pageObj", pageObj);
		
		return "activity/panicbuyactivity_list";
	}

	/**
	* 商品设置列表
	* @Title: CommodityList 
	* @Description: 显示抢购活动对应的商品设置列表 
	* @param id 活动id
	* @return String    返回类型
	 * @throws ParseException 
	 */
	@RequestMapping(value = "commoditylist")
	//@Permission(privilege = {"prms:activity:view", "prms:activity:edit", "prms:activity:delete", "prms:activity:tg"})
	public String CommodityList(ModelMap map,ActivityCommodity commodity,String tblshow,String activityId,String activityNo) throws ParseException{
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
				activitycommodityList=this.activityCommodityService.findActivityCommdity(pageObj, params);
			} catch (ServiceException e) {
				logger.error("PanicbuyActivityController-commoditylist-ServiceException=》预售管理-跳转商品设置页面-ServiceException", e);
				throw e;
			}
		// 设置页面显示
				if (tblshow == null || tblshow.equals("")) {
					map.put("tblshow", "-1");
				} else {
					map.put("tblshow", tblshow);
				}
		map.put("size", activitycommodityList.size());
		map.put("commodityList", activitycommodityList);
		map.put("activityCommodity", commodity);
		map.put("id", activityId);
		map.put("pageObj", pageObj);
		return "activity/panicbuycommodity_list";
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
			logger.error("PanicbuyActivityController.commodityDeletes() Exception=》活动管理-批量删除新用户专享活动商品", e);
			throw new ServiceException(e);
		}
		
		//判断执行结课
		if(resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
			resultVo.setResult(ServiceResultCode.SUCCESS,"/panicbuyActivity/commoditylist.do?activityId="+activityCommodity.getActivity().getId());
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
			resultVo.setResult(ServiceResultCode.FAIL,"/panicbuyActivity/commoditylist.do?activityId="+activityCommodity.getActivity().getId());
		}
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
				resultInt += activityCommodityService.deleteCommandSku(id, activityCommodity.getCommodityNo());
			} else {
				logger.info("PanicbuyActivityController.CommodityDelete() =》未找到改数据的记录");
			}
		} catch (Exception e) {
			logger.error("PanicbuyActivityController.CommodityDelete() Exception=》活动管理-删除新用户专享活动商品", e);
			throw new ServiceException(e);
		}
		
		//判断执行结课
		if(resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
			resultVo.setResult(ServiceResultCode.SUCCESS,"/panicbuyActivity/commoditylist.do?activityId="+activityCommodity.getActivity().getId());
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
			resultVo.setResult(ServiceResultCode.FAIL,"/panicbuyActivity/commoditylist.do?activityId="+activityCommodity.getActivity().getId());
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
		Map<String, Object> mapvalue=activityCommodityService.toSetCommdity(activityId,this.getString("activityCommodityId"));
		map.put("activity", mapvalue.get("activity"));
		map.put("activityCommodity", mapvalue.get("activityCommodity"));
		map.put("activityCommoditySkuList", mapvalue.get("activityCommoditySkuList"));
		map.put("image_path",System.getProperty("image.web.server"));
		map.put("jsessionId", this.getSession().getId());
		map.put("view", view);
		return "activity/panicbuyactivity_set_commodity";
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
			String [] buyTypes= {"PANIC_BUY","COMMON_BUY"};
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
			logger.error("PanicbuyActivityController-toCommdity-ServiceException=》抢购管理-弹窗选择商品页面-ServiceException", e);
			throw e;
		}		
		return "activity/panicbuyactivity_select_goods";
	}
	@RequestMapping(value="saveCommodity")
	@ResponseBody
	public ResultVo saveCommodity(ActivityCommodity activityCommodity, BindingResult result,ModelMap map){
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
			Map<String, Object> resultMap=this.activityCommodityService.validateActivityCommodity(activityCommodity, resultVo, activitycommodityList,ActivityTypeEnum.PANIC_BUY,viewStatus);			
			//当前设置的开始结束时间和预售批次重合
			if(resultMap!=null){
				resultVo.setStatus(Constant.RESULT_ERR);
				resultVo.setInfoStr(resultMap.get("msg").toString());
				return resultVo;
			}			
			man.setId(activityCommodity.getActivityId());
			activityCommodity.setActivity(man);
			activityCommodity.setActivityType(ActivityTypeEnum.PANIC_BUY);
			//新增商品设置
			ServiceCode serviceCode = this.activityCommodityService.saveCommdity(activityCommodity,viewStatus);
			resultVo.setResult(serviceCode, "/panicbuyActivity/commoditylist.do?activityId="+activityCommodity.getActivity().getId()+"&activityNo="+activityCommodity.getActivityNo());
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("panicbuyActivityController-save-ServiceException=》商品设置-保存", e);
			throw e;
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("panicbuyActivityController-save-Exception=》商品设置-保存", e);
		}
		map.put("viewStatus", viewStatus);
		if (resultVo.isHasException()) {
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		//修改或者新增成功，往ES推送变更信息  add by ying.cai
		updateActivityIndex.updateCommodity(activityCommodity.getId());
		return resultVo;
	}

	/******
	 * 根据商品code获取sku
	 * @param commodityCode
	 * @return
	 */
	@RequestMapping(value="getCommoditySku")
	public void getCommoditySku(HttpServletResponse response,String commodityCode,String stockLimit){
		//执行操作
		try {
			List<CommoditySkuTransform> skuTransformList= activityCommoditySkuService.getCommoditySku(commodityCode);
			this.responseWrite(response, this.getJsonByObject(skuTransformList));
			
		} catch (Exception e) {
			logger.error("PanicbuyActivityController.getCommoditySku() Exception=》预售管理-获取sku-", e);
			throw new ServiceException(e);
		}
	}
	/**
	* 抢购活动信息查看
	* @Title: DataView 
	* @Description: 查看活动信息 
	* @param id 活动id
	* @param map
	* @return String    返回类型
	 */
	@RequestMapping(value = "dataView")
//	@Permission(privilege = {"prms:activity:edit", "prms:activity:tg"})
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
			logger.error("PanicbuyActivityController.DataView() ServiceException=》促销系统管理-活动数据view", e);
			throw e;
		} catch (Exception e) {
			logger.error("PanicbuyActivityController.DataView() Exception=》促销系统管理-活动数据view", e);
			throw new ServiceException(e);
		}
		map.put("image_path",System.getProperty("image.web.server"));
		
		return "activity/panicbuyactivity_view";
	}
	/**
	* 抢购活动信息编辑页面
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
			logger.error("PanicbuyActivityController.DataForm() ServiceException=》促销系统管理-活动数据编辑", e);
			throw e;
		} catch (Exception e) {
			logger.error("PanicbuyActivityController.DataForm() Exception=》促销系统管理-活动数据编辑", e);
			throw new ServiceException(e);
		}
		map.put("image_path",System.getProperty("image.web.server"));
		
		return "activity/panicbuyactivity_form";
	}
	
	/**
	* 更新抢购活动发布状态
	* @Title: UpdateStatus 
	* @Description: 更新抢购活动发布状态 
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
		// 取消发布，先判断是否app推荐（如果是app推荐）
		if ("0".equals(releaseStatus)) {
			ActivityManager act = activityManagerService.findById(id);
			if (act != null && "1".equals(act.getAppRecommend())) {
				resultVo.setStatus(Constant.RESULT_ERR);
				resultVo.setInfoStr("撤销失败，原因：该抢购活动当前被“APP推荐管理”推荐，请修改后重试！");
				return resultVo;
			}
		}
		
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
				logger.error("PanicbuyActivityController.UpdateStatus() Exception=》促销系统管理-活动数据发布/撤销", e);
				throw e;
			}
			
			// 判断执行结课
			if (resultInt > 0) {
				resultVo.setStatus(Constant.RESULT_SCS);
				resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
				resultVo.setUrl("panicbuyActivity/dataList.do");
				//2016-09-14 add by ying.cai reason:一个活动发布或取消，则更新搜索引擎仓库数据
				updateActivityIndex.activityResolved(id, releaseStatus);
			} else {
				resultVo.setStatus(Constant.RESULT_ERR);
				resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
			}
		} else {
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr("发布失败，请先在【商品设置】添加商品，再进行发布操作。");
		}
		
		return resultVo;
	}
	
	
	@RequestMapping(value = "dataSave",method=RequestMethod.POST)
	@ResponseBody	
//	@Permission(privilege = {"prms:activity:view", "prms:activity:edit", "prms:activity:delete", "prms:activity:tg"})
	public ResultVo DataSave(ActivityManager activity, BindingResult result){
		//返回结果对象
			ResultVo resultVo = new ResultVo();	
				// 更新数据
				Date date = new Date();
				if (!StringUtil.isNotNull(activity.getId())) {
					activity.setCreateBy(RedisWebUtils.getLoginUser()); 	// 创建人
					activity.setCreateDate(date);							// 创建时间
					activity.setActivityType(ActivityTypeEnum.PANIC_BUY); // 抢购类型
					activity.setReleaseStatus("0"); // 默认  0：未发布
				}
				
				activity.setLastUpdateBy(RedisWebUtils.getLoginUser());	// 最后更新人
				activity.setLastUpdateDate(date);						// 最后更新时间
				
				
				ServiceCode serviceCode = null;
				// 执行更新操作
				try {
					// 抢购活动时间
					String startDateStr = this.getString("startDateStr");
					String endDateStr = this.getString("endDateStr");
					if (StringUtil.isNotNull(startDateStr)) {
						activity.setStartDate(DateUtil.parseTime(startDateStr));
					}
					if (StringUtil.isNotNull(endDateStr)) {
						activity.setEndDate(DateUtil.parseTime(endDateStr));
					}
					// 验证是否已存在：活动开始时间是同一天同同一小时同一分的抢购活动
					Map<String, Object> params = new HashMap<>();
					params.put("startTime", DateUtil.parseTime(startDateStr));
					params.put("activityType", ActivityTypeEnum.PANIC_BUY.toString()); // 活动类型
					params.put("delFlag", Constant.DELTE_FLAG_NO);//是否删除 1：删除、 0：正常
					List<ActivityManager> actManagerList = activityManagerService.findByBiz(params);
					if (actManagerList != null && actManagerList.size() > 0) {
						// 如果是修改，判断查询到的一个对象是否是当前对象
						if (StringUtil.isNotNull(activity.getId()) && actManagerList.size() == 1) {
							if (!activity.getId().equals(actManagerList.get(0).getId())) {
								resultVo.setStatus(Constant.RESULT_ERR);
								resultVo.setInfoStr("保存失败，原因：开始时间和其他已存在的抢购活动开始时间是同一天同同一小时同一分，请修改后重试！");
								return resultVo;
							}
						} else {
							resultVo.setStatus(Constant.RESULT_ERR);
							resultVo.setInfoStr("保存失败，原因：开始时间和其他已存在的抢购活动开始时间是同一天同同一小时同一分，请修改后重试！");
							return resultVo;
						}
					}
					if (StringUtil.isNotNull(activity.getId())) {
						// 验证是否存在：同一商品在不同批次时间重叠
						activityManagerService.isActicityUpdateDate(activity);
					}
					// 新增/修改
					serviceCode = activityManagerService.saveOrUpdate(activity);
					resultVo.setResult(serviceCode, "/panicbuyActivity/dataList.do");
					//更新es信息
					updateActivityIndex.activityResolved(activity.getId(), "1");
				} catch (ServiceException e) {
					logger.error("panicbuyActivityController.DataSave() ServiceException=》促销系统管理-活动数据更新", e);
					throw e;
				}
				catch (RuntimeException e) {
					resultVo.setStatus(Constant.RESULT_ERR);
					resultVo.setInfoStr(e.getMessage());
					logger.error("panicbuyActivityController.DataSave() Exception=》促销系统管理-活动数据更新", e);
					// TODO: handle exception
				}
				catch (Exception e) {
					logger.error("panicbuyActivityController.DataSave() Exception=》促销系统管理-活动数据更新", e);
					throw new ServiceException(e);
				}
				
				return resultVo;
	}
	
	/**
	* 删除活动
	* @Title: DataDelete 
	* @Description: 在抢购活动列表中删除活动 
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
			logger.error("PanicbuyActivityController.DataDelete() Exception=》促销系统管理-活动数据删除", e);
			throw new ServiceException(e);
		}
		
		// 判断执行结课
		if (resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
			resultVo.setUrl("/panicbuyActivity/dataList.do");
		} else {
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
		}
		
		return resultVo;
	}
	
	@RequestMapping(value = "importView")
	public String importView(HttpServletRequest request,String id,String type){
		
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		return "activity/panicbuy_importview";
	}
	@RequestMapping(value = "importExcel")
	public  String importExcel(HttpServletRequest request,String id,String type){

		//条形码重复验证
		String msg="导入失败,重新导入";
		
		List<String> list;
		try {
			list = UploadFileUtils.uploadFiles(request);
			if(list !=null && list.size()>0){
				for (String path:list) {
					List<String[]> listRow = ExcelReader.getExcelData(path);
					//edit by ying.cai 2016-11-18  给ES推送变更信息
					Map<String,Object> result= activityCommodityService.importExcelPanicbuy(listRow,ActivityTypeEnum.valueOf(type),id);
					if( null!=result ){
						msg =(String)result.get("MSG");
					}
					Object items = result.get("ACTIVITY_ITEMS");
					if( null!=items ){
						List<ActivityCommodity> activityCommditys = (List<ActivityCommodity>)items;
						for (ActivityCommodity activityCommodity : activityCommditys) {
							updateActivityIndex.updateCommodity(activityCommodity.getId());
						}
					}
				}
			}
//			updateActivityIndex.activityResolved(activityManagerId, operateType);
		} catch (IllegalStateException e) {
			logger.error("PanicbuyActivityController.importExcel() Exception=》抢购活动-excel导入", e);
			msg= "导入数据出现异常" +e.getMessage();
			throw e;
			
		} catch (IOException e) {
			logger.error("PanicbuyActivityController.importExcel() Exception=》抢购活动-excel导入", e);

			msg= "导入数据出现异常" +e.getMessage();
			throw new ServiceException(e);
			
		}catch(Exception e){
			logger.error("PanicbuyActivityController.importExcel() Exception=》抢购活动-excel导入", e);
			msg= "导入数据出现异常" +e.getMessage();
			
		}
		request.setAttribute("msg", msg);
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		return "activity/panicbuy_importmsg";
	}
	
	/**
	 * 抢购商品设置导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/activityCommodityExport")
	public void activityCommodityExport(ActivityCommodity activityCommodity,HttpServletRequest request,HttpServletResponse response){
		final String[] headers = new String[]{"商品条形码","标题","开始时间","结束时间","置顶序号","活动优惠价","限定数量","ID限购量","销量增量值","启用特殊增量值","排序号"};
		final String[] properties = new String[]{"commodityBarcode","activityTitle","startDate","endDate","sortTopNo","showPrice","limitCount","idLimitCount","saleIncrease","enableSpecialCount","sortNo"};
		final String fileName = "抢购活动商品列表";
		try {
			ActivityManager manager = activityManagerService.findById(activityCommodity.getActivityId());
			activityCommodity.setActivity(manager);
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
			logger.error("", e);
			throw new ServiceException(e);
		}
	}
	
	
	@RequestMapping(value="appRecommend")
	public String AppRecommendList(ModelMap map){
		// 分页数据
		Page pageObj = this.getPageObj();
		ActivityManager activity = new ActivityManager();
		activity.setActivityType(ActivityTypeEnum.PANIC_BUY);
		activity.setAppRecommend(Constant.YES);  // 查询是app推荐的抢购活动（0：否  1：是）
		// 活动列表数据
		List<ActivityManager> activityList = null;
		
		try {
			activityList = activityManagerService.findAppRecommendList(pageObj, activity);
		} catch (ServiceException e) {
			logger.error("PanicbuyActivityController.DataList() ServiceException=》促销系统管理-活动列表", e);
			throw e;
		} catch (Exception e) {
			logger.error("PanicbuyActivityController.DataList() Exception=》促销系统管理-活动列表", e);
			throw new ServiceException(e);
		}
		// 设置页面显示
		map.put("dataList", activityList);
		map.put("pageObj", pageObj);
		
		return "activity/panicbuy_app_list";
	}
	
	/**
	*  取消app推荐
	* @param id 活动id
	* @return ResultVo    返回类型
	 */
	@RequestMapping("updateAppRecommend")
	@ResponseBody	
	public ResultVo UpdateAppRecommend(String id){
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		// 实体对象
		ActivityManager activity = new ActivityManager();
		activity.setId(id); // 活动id
		
		int resultInt = 0;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("activity", activity);
		resultInt = activityManagerService.findCount(params);
		if (resultInt > 0) {
			activity.setAppRecommend("0"); // 是否app推荐（1：是、 0：否）
			activity.setLastUpdateBy(RedisWebUtils.getLoginUser());
			activity.setLastUpdateDate(new Date());
			// 执行修改操作
			try {
				resultInt = 0;
				resultInt = activityManagerService.modifyById(activity);
			} catch (ServiceException e) {
				logger.error("促销系统管理-app推荐管理", e);
				throw e;
			}
			
			// 判断执行结课
			if (resultInt > 0) {
				resultVo.setStatus(Constant.RESULT_SCS);
				resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
				resultVo.setUrl("panicbuyActivity/appRecommend.do");
			} else {
				resultVo.setStatus(Constant.RESULT_ERR);
				resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
			}
		} else {
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr("活动不存在，无法取消推荐");
		}
		
		return resultVo;
	}
	
	@RequestMapping("findActivityList")
	public String FindActivityList(ActivityManager activity, ModelMap map){
		// 分页数据
		Page pageObj = this.getPageObj();
		activity.setActivityType(ActivityTypeEnum.PANIC_BUY);
		activity.setReleaseStatus(Constant.YES); // 1：发布、 0：撤销
		activity.setAppRecommend(Constant.NO);  // 查询是app推荐的抢购活动（0：否  1：是）
		// 活动列表数据
		List<ActivityManager> activityList = null;
		try {
			activityList = activityManagerService.findAppRecommendList(pageObj, activity);
		} catch (ServiceException e) {
			logger.error("PanicbuyActivityController.DataList() ServiceException=》促销系统管理-活动列表", e);
			throw e;
		} catch (Exception e) {
			logger.error("PanicbuyActivityController.DataList() Exception=》促销系统管理-活动列表", e);
			throw new ServiceException(e);
		}
		// 设置页面显示
		map.put("activity", activity);
		map.put("dataList", activityList);
		map.put("pageObj", pageObj);
		
		return "activity/panicbuy_app_form";
	}
	
	@RequestMapping("saveAppRecommend")
	@ResponseBody	
	public ResultVo SaveAppRecommend(String id){
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		// 处理选中的抢购活动id
		List<String> ids = Arrays.asList(id.split(","));
		// 通过id获取活动对象
		List<ActivityManager> activityList = 
				activityManagerService.findPanicBuyActivityList(ActivityTypeEnum.PANIC_BUY.getValue(), ids);
			
		if (activityList != null && ids.size() == activityList.size()) {
			// 遍历验证是否存在
			for (ActivityManager actManager : activityList) {
				// 活动是否已删除或活动是否已发布
				if (Constant.DELTE_FLAG_YES.equals(actManager.getDelFlag()) || Constant.NO.equals(actManager.getReleaseStatus())) {
					resultVo.setStatus(Constant.RESULT_ERR);
					resultVo.setInfoStr("新增推荐失败，原因：您选择的【"+ actManager.getTitle() +"】当前已被撤销或删除，请修改后重试！");
					return resultVo;
				}
				// 活动是否已推荐
				if (Constant.YES.equals(actManager.getAppRecommend())) {
					resultVo.setStatus(Constant.RESULT_ERR);
					resultVo.setInfoStr("新增推荐失败，原因：您选择的【"+ actManager.getTitle() +"】当前已被推荐，请修改后重试！");
					return resultVo;
				}
			}
			// 修改app推荐
			int resultInt = activityManagerService.updateAppRecommend(Constant.YES, ids);
			// 判断执行结课
			if (resultInt > 0) {
				resultVo.setStatus(Constant.RESULT_SCS);
				resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
				resultVo.setUrl("panicbuyActivity/appRecommend.do");
			} else {
				resultVo.setStatus(Constant.RESULT_ERR);
				resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
			}
		} else {
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr("新增推荐失败，原因：未查到您选择的活动，请修改后重试！");
		}
		
		return resultVo;
	}

}

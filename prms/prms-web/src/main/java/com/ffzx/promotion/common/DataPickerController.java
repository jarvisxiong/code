/**
 * 
 */
package com.ffzx.promotion.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commodity.api.dto.Category;
import com.ffzx.commodity.api.dto.Commodity;
import com.ffzx.commodity.api.service.CategoryApiService;
import com.ffzx.commodity.api.service.CommodityApiService;
import com.ffzx.promotion.api.dto.ActivityCommodityVo;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.service.ActivityCommodityService;
import com.ffzx.promotion.service.ActivityManagerService;
import com.ffzx.promotion.service.CouponGrantService;

/**
 * 公共选择器 控制器
 * @author hyl
 * 2016/05/05
 */
@Controller
@RequestMapping("dataPicker/*")
public class DataPickerController extends BaseController {
	// 记录日志
		protected final  Logger logger = LoggerFactory.getLogger(DataPickerController.class);
		@Autowired
		private CommodityApiService commodityApiService;
		@Autowired
		private ActivityManagerService activityManagerService;
		@Autowired
		private ActivityCommodityService activityCommodityService;
		@Autowired
		private CategoryApiService categoryApiService;
		@Autowired
		private CouponGrantService couponGrantservice;
		@Resource
		private RedisUtil redisUtil;
		/**
		 * 商品选择器列表
		 * @return
		 */
		@RequestMapping(value = "commodity_dataPicker")
		public String commodity_dataPicker(ModelMap map) {
			
		    List<Commodity> commodityList = null;
		    // 查询所有且不分页
		    Map<String, Object> params = this.getParaMap();
		    // 购买类型--过滤出普通商品购买类型
		    params.put("buyType", "COMMON_BUY");
		    Page pageObj = this.getPageObj();
		  /*  if(params.size()==0){
		    	params = (Map<String, Object>) redisUtil.get("commodity_dataPicker"+RedisWebUtils.getLoginUser().getId());
		    }else{
		    	redisUtil.set("commodity_dataPicker"+RedisWebUtils.getLoginUser().getId(), params);
		    }*/
			//查询数据
			try {
				ResultDto dto = commodityApiService.findList(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, params);
				Map<String,Object> dataMap = (Map<String, Object>) dto.getData();
				commodityList =  (List<Commodity>) dataMap.get("list");
				pageObj =   (Page) dataMap.get("page");
			} catch (ServiceException e) {
				logger.error("DataPickerController ServiceException", e);
				throw e;
			} catch (Exception e) {
				logger.error("DataPickerController Exception", e);
				throw new ServiceException(e);
			}
			map.put("list", commodityList);
			
			map.put("pageObj", pageObj);
			
			map.put("params", params);
			
			return "datapicker/commodity_dataPicker";
		}
		
		/**
		 * 活动列表选择器
		 * @return
		 */
		@RequestMapping(value = "activity_dataPicker")
		public String activity_dataPicker( ModelMap map){
			  List<ActivityManager> activityManagerList = null;
			    // 查询所有且不分页
			    Map<String, Object> params = this.getParaMap();
			    params.put("releaseStatus", "1");
			    Page pageObj = this.getPageObj();
			  /*  if(params.size()==0){
			    	params = (Map<String, Object>) redisUtil.get("commodity_dataPicker"+RedisWebUtils.getLoginUser().getId());
			    }else{
			    	redisUtil.set("commodity_dataPicker"+RedisWebUtils.getLoginUser().getId(), params);
			    }*/
				//查询数据
				try {
					activityManagerList = this.activityManagerService.findByPage(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, params);
				} catch (ServiceException e) {
					logger.error("DataPickerController ServiceException", e);
					throw e;
				} catch (Exception e) {
					logger.error("DataPickerController Exception", e);
					throw new ServiceException(e);
				}
				map.put("list", activityManagerList);
				
				map.put("pageObj", pageObj);
				
				map.put("params", params);
				return "datapicker/activity_dataPicker";
		}
		/**
		 * 活动商品详情选择器
		 * @return
		 */
		@RequestMapping(value = "activityCommodity_dataPicker")
		public String activityCommodity_dataPicker( ModelMap map){
			//TODO
			  List<ActivityCommodityVo> activityCommodityList = null;
			    // 查询所有且不分页
			    Map<String, Object> params = this.getParaMap();
			    params.put("releaseStatus", "1");
			    Page pageObj = this.getPageObj();
				//查询数据
				try {
					activityCommodityList = this.activityCommodityService.selectByAdvert(pageObj, params);
				} catch (ServiceException e) {
					logger.error("DataPickerController ServiceException", e);
					throw e;
				} catch (Exception e) {
					logger.error("DataPickerController Exception", e);
					throw new ServiceException(e);
				}
				map.put("list",activityCommodityList);
				
				map.put("pageObj", pageObj);
				
				map.put("params", params);
				return "datapicker/activityCommodity_dataPicker";
		}
		
	
		
		/**
		 * 类目树形
		 * 
		 * @return
		 */
		@RequestMapping(value = "ajax_categorySimpleTree")
		public void getAjaxList(HttpServletResponse response) {
			logger.debug("MenuController-ajaxgetMenuList=》菜单权限-查看所有的菜单权限");
			try {
				// 查询所有且不分页
				Map<String, Object> params = new HashMap<String, Object>();
				params.put(Constant.ACT_FLAG, Constant.ACT_FLAG_YES);
				ResultDto resultDto = categoryApiService.getList(params);
				List<Category> categoryList = (List<Category>) resultDto.getData();
				List<Map<String, Object>> listResultObj = new ArrayList<>() ;
				for (Category data : categoryList) {
					Map<String, Object> row = new HashMap<String, Object>();
					row.put("id", data.getId());
					row.put("name", data.getName());
					row.put("pId", data.getParentId());
					row.put("pIds", data.getParentIds());
					row.put("open", true);
					listResultObj.add(row);
				}
				System.out.println( this.getJsonByObject(listResultObj) );
				getOutputMsg().put("status",ServiceResultCode.SUCCESS);
				getOutputMsg().put("data", listResultObj);
			} catch (ServiceException e) {
				logger.error("AdvertController-ajaxList-ServiceException=》广告管理--查看所有区域-ServiceException", e);
				getOutputMsg().put("status",ServiceResultCode.FAIL);
			} catch (Exception e) {
				getOutputMsg().put("status",ServiceResultCode.FAIL);
				logger.error("AdvertController-ajaxList-Exception=》广告管理--查看所有区域-Exception", e);
			}
			outPrint(response, this.getJsonByObject(this.getOutputMsg()));
		}
		
		/**
		* 
		* @param map
		* @return String    返回类型
		 */
		@RequestMapping(value = "coupon_dataPicker")
		public String coupon_dataPicker(ModelMap map){
			// 数据集合
			List<CouponGrant> couponGrantList = null;
			// 查询所有且不分页
		    String grantStr = this.getString("grantStr");
		    Page pageObj = this.getPageObj();
		   //查询数据
			try {
				couponGrantList = this.couponGrantservice.findDataPicker(pageObj, grantStr);
			} catch (ServiceException e) {
				logger.error("DataPickerController ServiceException", e);
				throw e;
			} catch (Exception e) {
				logger.error("DataPickerController Exception", e);
				throw new ServiceException(e);
			}
		    // 数据
		    map.put("list", couponGrantList);
		    map.put("pageObj", pageObj);
			map.put("grantStr", grantStr);
		    
			return "datapicker/coupon_dataPicker";
		}
		
}

package com.ffzx.promotion.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.commodity.api.dto.Category;
import com.ffzx.commodity.api.dto.Commodity;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponAdminCategory;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.consumer.CategoryApiConsumer;
import com.ffzx.promotion.api.service.consumer.CommodityApiConsumer;
import com.ffzx.promotion.constant.CouponConstant;
import com.ffzx.promotion.service.ActivityManagerService;
import com.ffzx.promotion.service.CouponAdminCategoryService;
import com.ffzx.promotion.service.CouponAdminService;
/**
 * 优惠券管理
 * 
 * @className CouponAdminController.java
 * @author lushi.guo
 * @version 1.0
 */
@Controller
@RequestMapping("/coupon")
public class CouponAdminController extends BaseController {
	// 记录日志
		private final static Logger logger = LoggerFactory.getLogger(CouponAdminController.class);

		@Autowired
		private ActivityManagerService activityManagerService;
		@Autowired
		private CouponAdminService couponAdminService;
		@Autowired
		private CommodityApiConsumer commodityApiConsumer;
		@Autowired
		private CategoryApiConsumer categoryApiConsumer;
		@Autowired
		private CouponAdminCategoryService couponAdminCategorySevice;
		
		@RequestMapping(value = "/list")
		@Permission(privilege = {"prms:cupon:add","prms:cupon:edit","prms:cupon:status","prms:cupon:details","prms:cupon:view"})
		public String list(CouponAdmin coupon,ModelMap map){
			Page pageObj = this.getPageObj();
			List<CouponAdmin> couponList = null;
			Map<String, Object> params=new HashMap<String, Object>();
			try {				
					params.put("orderByField", "create_date");
					params.put("orderBy", Constant.ORDER_BY);
					params.put("name", coupon.getName());
					params.put("couponNumber", coupon.getNumber());
//					params.put("number", coupon.getNumber());
					params.put("faceValue", coupon.getFaceValue());
					params.put("maxFaceValue", coupon.getMaxFaceValue());
					params.put("delFlag", coupon.getDelFlagTemp());
/*					params.put("couponState", coupon.getCouponState());
					if(StringUtil.isNotNull(coupon.getCouponState())){
						params.put("couponStateDate", new Date());
					}*/
					if(StringUtil.isNotNull(coupon.getEffectiveDateStartStr())||StringUtil.isNotNull(coupon.getEffectiveDateEndStr())){
						params.put("effectiveDateState", "1");
					}
					params.put("effectiveDateStartStr", coupon.getEffectiveDateStartStr());
					params.put("effectiveDateEndStr", coupon.getEffectiveDateEndStr());
					// 创建时间
					params.put("createDateStart", coupon.getCreateDateStart());
					params.put("createDateEnd", coupon.getCreateDateEnd());
					
					couponList = couponAdminService.getCouponList(pageObj,params);		
			} catch (ServiceException e) {
				logger.error("CouponAdminController-list-Exception=》促销管理-优惠券列表-Exception", e);
				throw e;
			} catch (Exception e) {
				logger.error("CouponAdminController-list-Exception=》促销管理-优惠券列表-Exception", e);
				throw new ServiceException(e);
			}
			//设置页面显示
			map.put("couponList", couponList);
			map.put("pageObj", pageObj);
			map.put("coupon", coupon);
			return "coupon/coupon_list";
		}
		
		/*****
		 * 跳转添加
		 * @return
		 */
		@RequestMapping(value="toSave")
		@Permission(privilege = {"prms:cupon:add","prms:cupon:edit"})
		public String toSave(String id, ModelMap map,String view){
			try {
				if(StringUtil.isNotNull(id)){
					CouponAdmin coupon=this.couponAdminService.findById(id);
					map.addAttribute("coupon",coupon);
					Map<String, Object> params=new HashMap<>();
					params.put("couponAdmin",coupon);
					params.put("couponId", id);
					List<CouponAdminCategory> couponcategoryList=this.couponAdminCategorySevice.findList(null, params);
					//商品类目
					if(coupon.getGoodsSelect().equals(com.ffzx.promotion.api.dto.constant.Constant.COMM_CATEGORY)){						
						if(couponcategoryList.size()!=0){
							String [] categoryId=	 new String [couponcategoryList.size()];
							StringBuffer sb=new StringBuffer();
							for(int i=0;i<couponcategoryList.size();i++){
								categoryId[i]=couponcategoryList.get(i).getCategoryId();
								sb.append(couponcategoryList.get(i).getCategoryId()+",");
							}
							params=new HashMap<>();
							params.put("inIds", categoryId);
							List<Category> categoryList = categoryApiConsumer.getTreeTableList(params, null);
							//设置页面显示
							map.put("categoryList", categoryList);
							map.put("categoryId", sb.deleteCharAt(sb.length()-1).toString());
						}						
					}
					else if(coupon.getGoodsSelect().equals(com.ffzx.promotion.api.dto.constant.Constant.COMM_FIXED)){						
						if(couponcategoryList.size()!=0){
							String [] commodityId=	 new String [couponcategoryList.size()];
							StringBuffer sbId=new StringBuffer();
							StringBuffer sbName=new StringBuffer();
							for(int i=0;i<couponcategoryList.size();i++){
								commodityId[i]=couponcategoryList.get(i).getCommodityId();								
							}
							params=new HashMap<>();
							params.put("inIds", commodityId);
							Page pageObj = this.getPageObj();
							Map<String, Object> mapValue=commodityApiConsumer.findList(null, null, null, params);
							pageObj=(Page) mapValue.get("page");
							List<Commodity> commodityList = (List<Commodity>) mapValue.get("list");
							for(Commodity comm:commodityList){
								sbName.append(comm.getName()+",");
								sbId.append(comm.getId()+",");
							}
							map.put("commodityList", commodityList);
							map.put("goodsId", sbId.deleteCharAt(sbId.length()-1).toString());
							map.put("goodsName", sbName.length()==0?null:sbName.deleteCharAt(sbName.length()-1).toString());
							map.put("pageObj", pageObj);
						}						
					}else if(coupon.getGoodsSelect().equals(com.ffzx.promotion.api.dto.constant.Constant.ActivityPT)){						
						if(couponcategoryList.size()!=0){
							StringBuilder  activityIdsbuilder=	 new StringBuilder();
							StringBuffer sbId=new StringBuffer();
							StringBuffer sbtitle=new StringBuffer();
							for(int i=0;i<couponcategoryList.size();i++){
								activityIdsbuilder.append(couponcategoryList.get(i).getActivityManagerId()+",");								
							}
							ActivityManager activity=new ActivityManager();
							activity.setActivityType(ActivityTypeEnum.ORDINARY_ACTIVITY);
							activity.setActivityIsIds(activityIdsbuilder.toString());
							List<ActivityManager> activityList=activityManagerService.findList(null,  Constant.ORDER_BY_FIELD, Constant.ORDER_BY, activity);
							for(ActivityManager comm:activityList){
								sbId.append(comm.getId()+",");
								sbtitle.append(comm.getTitle()+",");
							}
							if(activityList!=null && activityList.size()>0){
								map.put("activityList", activityList);
								map.put("activityManageIds", sbId.deleteCharAt(sbId.length()-1).toString());
								map.put("activitytitles", sbtitle.deleteCharAt(sbtitle.length()-1).toString());
							}
						}						
					}
					map.put("coupon", coupon);
				}
				
			} catch (ServiceException e) {
				logger.error("", e);
				throw e;
			} catch (Exception e) {
				logger.error("", e);
				throw new ServiceException(e);
			}
			map.put("view", view);
			return"coupon/coupon_add";
		}
		
		/*****
		 * 跳转详情页面
		 * @return
		 */
		@RequestMapping(value="toDetail")
		 @Permission(privilege = "prms:cupon:details")
		public String toDetail(String id,ModelMap map){
			CouponAdmin coupon=null;
			Page pageObj = this.getPageObj();
			if(StringUtil.isNotNull(id)){
				try {
					coupon=this.couponAdminService.findById(id);
					Map<String, Object> params=new HashMap<>();
					params.put("couponId", id);
					params.put("couponAdmin",coupon);
					params.put("orderByField", "create_date");
					params.put("orderBy", Constant.ORDER_BY);
					List<CouponAdminCategory> couponAdminCategoryList=this.couponAdminCategorySevice.findList(null, params);
					//商品类目
					if(coupon.getGoodsSelect().equals(com.ffzx.promotion.api.dto.constant.Constant.COMM_CATEGORY)){						
						if(couponAdminCategoryList.size()!=0){
							String [] categoryId=	 new String [couponAdminCategoryList.size()];
							for(int i=0;i<couponAdminCategoryList.size();i++){
								categoryId[i]=couponAdminCategoryList.get(i).getCategoryId();
							}
							params=new HashMap<>();
							params.put("inIds", categoryId);
							List<Category> categoryList = categoryApiConsumer.getTreeTableList(params, null);
							//设置页面显示
							map.put("categoryList", categoryList);
						}						
					}
					else if(coupon.getGoodsSelect().equals(com.ffzx.promotion.api.dto.constant.Constant.COMM_FIXED)){						
						if(couponAdminCategoryList.size()!=0){
							String [] commodityId=	 new String [couponAdminCategoryList.size()];
							for(int i=0;i<couponAdminCategoryList.size();i++){
								commodityId[i]=couponAdminCategoryList.get(i).getCommodityId();
							}
							params=new HashMap<>();
							params.put("inIds", commodityId);
							Map<String, Object> mapValue=commodityApiConsumer.findList(null, "create_date", Constant.ORDER_BY, params);
							pageObj=(Page) mapValue.get("page");
							List<Commodity> commodityList = (List<Commodity>) mapValue.get("list");
							map.put("commodityList", commodityList);
							map.put("pageObj", pageObj);
						}						
					}
					else if(coupon.getGoodsSelect().equals(com.ffzx.promotion.api.dto.constant.Constant.ActivityPT)){						
						if(couponAdminCategoryList.size()!=0){
							StringBuilder  activityIdsbuilder=	 new StringBuilder();
							StringBuffer sbId=new StringBuffer();
							for(int i=0;i<couponAdminCategoryList.size();i++){
								activityIdsbuilder.append(couponAdminCategoryList.get(i).getActivityManagerId()+",");								
							}
							ActivityManager activity=new ActivityManager();
							activity.setActivityType(ActivityTypeEnum.ORDINARY_ACTIVITY);
							activity.setActivityIsIds(activityIdsbuilder.toString());
							List<ActivityManager> activityList=activityManagerService.findList(null,  Constant.ORDER_BY_FIELD, Constant.ORDER_BY, activity);
							for(ActivityManager comm:activityList){
								sbId.append(comm.getId()+",");
							}
							if(activityList!=null && activityList.size()>0){
								map.put("activityList", activityList);
								map.put("activityManageIds", sbId.deleteCharAt(sbId.length()-1).toString());
							}
						}						
					}
				} catch (ServiceException e) {
					logger.error("CouponAdminController-toDetail-Exception=》促销管理-优惠券详情-Exception", e);
					throw e;
				}
			}
			map.put("coupon", coupon);
			return"coupon/coupon_detail";
		}
		
		/**
		 * 保存/修改
		 */
		@RequestMapping(value = "save", method = RequestMethod.POST)
		@Permission(privilege = {"prms:cupon:add","prms:cupon:edit"})
		@ResponseBody
		public ResultVo save(CouponAdmin coupon, BindingResult result){
			ResultVo resultVo = new ResultVo();
			if(StringUtil.isNotNull(coupon.getLimitValue())){
				coupon.setConsumptionLimit(new BigDecimal(coupon.getLimitValue()));
			}
			try {			
				if(!StringUtil.isNotNull(coupon.getId())){
					coupon.setId(UUIDGenerator.getUUID());
				}
				ServiceCode serviceCode = couponAdminService.save(coupon);
							
				resultVo.setResult(serviceCode, "/coupon/list.do");
			} catch (ServiceException e) {
				resultVo.setHasException(true);
				logger.error("CouponAdminController-save-ServiceException=》优惠券管理-保存", e);
				throw e;
			} catch (Exception e) {
				resultVo.setHasException(true);
				logger.error("CouponAdminController-save-Exception=》优惠券管理-保存", e);
				throw new ServiceException(e);
			}
			if (resultVo.isHasException()) {
				resultVo.setStatus(Constant.RESULT_EXC);
				resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
				return resultVo;
			}
			return resultVo;
		}
	
			
		/*****
			* 跳转选择商品
			* @return
			 */
		@Permission(privilege = {"prms:cupon:add","prms:cupon:edit"})
		@RequestMapping(value="toSelectGoods")
		public String toSelectGoods(String type,ModelMap map,Commodity commodity,String goodsId){		
			Page pageObj = this.getPageObj();	
			List<Commodity> commodityList = null;
			Map<String, Object> params=new HashMap<String, Object>();
				try {				
						params.put("name", commodity.getName());
						params.put("barCode", commodity.getBarCode());
						String [] buyTypes= {"NEWUSER_VIP","COMMON_BUY","WHOLESALE_MANAGER","ORDINARY_ACTIVITY"};
						params.put("buyTypes", buyTypes);
						if(StringUtil.isNotNull(commodity.getId())){
							String [] goodsIds=commodity.getId().split(",");
							params.put("flag", "YES");
							params.put("goodsId", goodsIds);
							map.put("id", commodity.getId());
						}
						else if(StringUtil.isNotNull(goodsId)){
							String [] goodsIds=goodsId.split(",");
							params.put("flag", "YES");
							params.put("goodsId", goodsIds);
							map.put("id", goodsId);
						}
						params.put("commodityDummyType", PrmsConstant.notVartual);
						Map<String, Object> mapValue=commodityApiConsumer.findList(pageObj, "create_date", Constant.ORDER_BY, params);
						if(mapValue!=null){
							pageObj=(Page) mapValue.get("page");
							commodityList = (List<Commodity>) mapValue.get("list");
						}
						

				} catch (ServiceException e) {
					logger.error("CouponAdminController-toSelectGoods-Exception=》促销管理-优惠券列表-Exception", e);
					throw e;
				} catch (Exception e) {
					logger.error("CouponAdminController-toSelectGoods-Exception=》促销管理-优惠券列表-Exception", e);
					throw new ServiceException(e);
				}
				//设置页面显示
				map.put("commodityList", commodityList);
				map.put("pageObj", pageObj);
				return"coupon/coupon_goods";
			
		}

		@RequestMapping(value="toSelectptActivity")
		public String toSelectptActivity(ModelMap map,String activityManageIds,ActivityManager activity){		
			Page pageObj = this.getPageObj();	
			
			// 活动列表数据
			List<ActivityManager> activityList = null;
				try {				
					activity.setDelFlag(Constant.NO);
					activity.setActivityIds(activityManageIds);
					// 活动类型
					activity.setActivityType(ActivityTypeEnum.ORDINARY_ACTIVITY);
					activity.setActivityStatus(CouponConstant.three);
					activityList = activityManagerService.findList
							(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, activity);
						
						

				} catch (ServiceException e) {
					logger.error("CouponAdminController-toSelectGoods-Exception=》促销管理-优惠券列表-Exception", e);
					throw e;
				} catch (Exception e) {
					logger.error("CouponAdminController-toSelectGoods-Exception=》促销管理-优惠券列表-Exception", e);
					throw new ServiceException(e);
				}
				//设置页面显示
				map.put("activityList", activityList);
				map.put("activity", activity);
				map.put("pageObj", pageObj);
				return"coupon/coupon_ptActivity";
			
		}
		
		/**
		 * 加载分配商品类木页面
		 * @return
		 */
		@RequestMapping("toCouponCategory")
		public void toCouponCategory(HttpServletResponse response,String categoryId) {
			logger.debug("UserGroupController-assignRoleToUserGroupForm=》用户组管理-加载分配角色页面");
			try {
				Map<String, Object> params=new HashMap<String, Object>();		
				List<Category> categoryList = categoryApiConsumer.getTreeTableList(params, null);
				this.responseWrite(response, this.getCategoryNodes(categoryList, categoryId));
			} catch (ServiceException e) {
				logger.error("CouponAdminController-toCouponCategory-ServiceException=》促销管理-优惠券新增-Exception", e);
				throw e;
			} catch (Exception e) {
				logger.error("CouponAdminController-toCouponCategory-Exception=》促销管理-优惠券新增-Exception", e);
				throw new ServiceException(e);
			}
		}
		
		/******
		 * 
		 * @param list
		 * @return
		 * @throws ServiceException
		 */
		public String getCategoryNodes(List<Category> list,String categoryId) throws ServiceException{
			List<Object> listResult = new ArrayList<Object>();
			if(list.size() > 0){				
				if(StringUtil.isNotNull(categoryId)){
					String [] categoryIds=categoryId.trim().split(",");
					for (Category category : list){
						Map<String, Object> row = new HashMap<String, Object>();
						row.put("id", category.getId());
						row.put("name", category.getName());
						row.put("parentId", category.getParent().getId());
						row.put("open", true);
						for(String id:categoryIds){							
							if(id.equals(category.getId())){
								row.put("checeked", true);
							}						
						}	
						listResult.add(row);
					}
				}else{
					for (Category category : list){
							Map<String, Object> row = new HashMap<String, Object>();
							row.put("id", category.getId());
							row.put("name", category.getName());
							row.put("parentId", category.getParent().getId());
							row.put("open", true);
							row.put("checeked", false);
							listResult.add(row);			
					}
				}
				
			}
			return this.getJsonByObject(listResult);
		}
		
		/**
		 * 启用
		 */
		@RequestMapping(value = "runCoupon", method = RequestMethod.POST)
		@Permission(privilege = {"prms:cupon:status"})
		@ResponseBody
		public ResultVo runCoupon(String id){
			ResultVo resultVo = new ResultVo();
			try {
			CouponAdmin	coupon=this.couponAdminService.findById(id);
			ServiceCode serviceCode = couponAdminService.runCoupon(coupon);
				resultVo.setResult(serviceCode, "/coupon/list.do");
			} catch (ServiceException e) {
				resultVo.setHasException(true);
				logger.error("CouponAdminController-runCoupon-ServiceException=》优惠券管理-保存", e);
				throw e;
			} catch (Exception e) {
				resultVo.setHasException(true);
				logger.error("CouponAdminController-runCoupon-Exception=》优惠券管理-保存", e);
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

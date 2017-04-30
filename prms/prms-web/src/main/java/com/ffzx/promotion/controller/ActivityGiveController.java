package com.ffzx.promotion.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil.ExportModel;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.commodity.api.dto.Commodity;
import com.ffzx.commodity.api.service.CommodityApiService;
import com.ffzx.commodity.api.service.CommoditySkuApiService;
import com.ffzx.order.api.enums.CommodityStatusEnum;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityGive;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.mapper.ActivityCommodityMapper;
import com.ffzx.promotion.service.ActivityGiveService;
import com.ffzx.promotion.service.GiftCommodityService;
import com.ffzx.promotion.vo.CouponExportVo;
import com.ffzx.promotion.vo.GiftExportVo;
import com.ffzx.promotion.vo.GiveExportVo;


 /**
 * @Description: 买赠控制器
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年9月13日 下午3:37:40
 * @version V1.0 
 *
 */
@Controller
@RequestMapping("/activityGive")
public class ActivityGiveController extends BaseController {
	// 记录日志
		private final static Logger logger = LoggerFactory.getLogger(ActivityGiveController.class);

		private final String NAME="name";
		private final String CODE="code";
		private final String STATUS="status";
		private final String GIVETYPE="giveType";
		private final String STATUFor="statuFor";//商品集合砖头
		@Autowired
		private CommodityApiService commodityApiService;
		@Autowired
		private CommoditySkuApiService commoditySkuApiService;
		@Autowired
		private ActivityGiveService activityGiveService;
		@Autowired
		private ActivityCommodityMapper activityCommodityMapper;
		@Autowired
		private GiftCommodityService giftCommodityService;
		
		@RequestMapping(value = "/list")
		@Permission(privilege = {"prms:mz:export","prms:mz:delete","prms:mz:enable",
		"prms:mz:view","prms:mz:view","prms:mz:list","prms:mz:add"})
		public String list(ActivityGive activityGive,ModelMap map){
			Page pageObj = this.getPageObj();
			List<ActivityGive> activityGives=null;
			try {
				activityGives=activityGiveService.findList(pageObj, "create_date",  Constant.ORDER_BY, activityGive);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				logger.error("CouponGrantController-couponGrantList-Exception=》促销系统-优惠券发放列表-Exception", e);
				throw e;
				
			}
			map.put("activityGive", activityGive);
			map.put("activityGives", activityGives);
			map.put("pageObj",pageObj);
			return "activityGive/activityGive_list";
		}
		

		/**
		 * 导出
		 * @param request
		 * @param response
		 */
		@RequestMapping(value = "/export")
		public void export(HttpServletRequest request,HttpServletResponse response){
			final String fileName = "买赠数据导出_"+RedisWebUtils.getLoginUser().getName()
					+"_"+DateUtil.format(new Date(), DateUtil.FORMAT_DATE);
			final String[] headers = new String[]{
					"活动ID","活动名称","主商品条形码","主商品限定数量","主商品优惠价","创建人","创建时间","赠品类型",
					"赠品商品条形码","赠品商品名称","赠品SKU条形码","赠品限定数量","单次赠送数量",
					"优惠券编码","优惠券名称","面值","消费限制","有效期"
					};
			final String[] properties = new String[]{
					"giveId","giveTitle","commodityBarcode","limitCount","preferentialPrice",
					"createBy","createDate[yyyy-MM-dd HH:mm:ss]","giftType",
					"giftExportVo.giftCommodityBarcode","giftExportVo.commodityName","giftExportVo.commoditySkuBarcode",
					"giftExportVo.giftLimtCount","giftExportVo.giftCount",
					"couponExportVo.couponCode","couponExportVo.couponName","couponExportVo.couponFace",
					"couponExportVo.couponLimit","couponExportVo.couponNum"
			};
			try {
				final List<GiveExportVo> giveList= activityGiveService.findListToExport(buildQueryParamToExport());
					ExcelExportUtil.exportXls(new ExportModel() {
						public String[] getProperties() {
							return properties;
						}
						public String[] getHeaders() {
							return headers;
						}
						public List getData() {
							return resolvedExportData(giveList);
						}
						public String getFileName() {
							return fileName;
						}
					},response);
			} catch (IOException e) {
				logger.error("导出数据报错===>>>", e);
				throw new ServiceException("导出数据报错===>>>",e);
			}
		}
		
		/***
		 * 逻辑处理导出数据
		 * @return
		 * @date 2016年9月19日 上午11:50:55
		 * @author ying.cai
		 * @email ying.cai@ffzxnet.com
		 */
		private List<GiveExportVo> resolvedExportData(List<GiveExportVo> giveList){
			List<GiveExportVo> finalData = new ArrayList<>();
			for (GiveExportVo giveExportVo : giveList) {
				GiveExportVo finalGiveExportVo = giveExportVo;
				int couponSize = giveExportVo.getCouponList().size();
				int giftSize = giveExportVo.getGiftList().size();
				for (int i = 0; i < (couponSize>giftSize?couponSize:giftSize); i++) {
					if(i!=0){
						finalGiveExportVo = new GiveExportVo();
					}
					if(couponSize!=0 && couponSize > i){
						finalGiveExportVo.setCouponExportVo(giveExportVo.getCouponList().get(i));
					}
					
					if(giftSize!=0 && giftSize > i){
						finalGiveExportVo.setGiftExportVo(giveExportVo.getGiftList().get(i));
					}
					if(null==finalGiveExportVo.getCouponExportVo()){
						finalGiveExportVo.setCouponExportVo(new CouponExportVo());
					}
					if(null==finalGiveExportVo.getGiftExportVo()){
						finalGiveExportVo.setGiftExportVo(new GiftExportVo());
					}
					finalData.add(finalGiveExportVo);
				}
			}
			return finalData;
		}
		
		/***
		 * 构建查询参数，用作数据导出条件
		 * @return
		 * @date 2016年9月19日 下午2:36:19
		 * @author ying.cai
		 * @email ying.cai@ffzxnet.com
		 */
		private Map<String,Object> buildQueryParamToExport(){
			Map<String,Object> params = new HashMap<>();
			List<String> idList = Arrays.asList(getString("ids").split(","));
			params.put("idList", idList);
			return params;
		}
		//主商品列表
		@RequestMapping(value = "/findMainCommodity")
		public String findMainCommodity(CouponAdmin coupon,ModelMap map,
				String name,String code){

			Map<String, Object> params=new HashMap<String, Object>();
			if(!StringUtil.isEmpty(name)){
				params.put(NAME, name);
			}
			if(!StringUtil.isEmpty(code)){
				params.put(CODE, code);
			}
			params.put("buyType", "COMMON_BUY");
			params.put(STATUS, CommodityStatusEnum.COMMODITY_STATUS_ADDED);//上架
			params.put(GIVETYPE, Constant.NO);//非主商品
			//把已经参加赠品的商品排除掉
			Map<String, Object> giftMap=this.giftCommodityService.getMapValue();
			if(giftMap!=null){
				params.put("goodsId", giftMap.get("goodsId"));
				params.put("flag", Constant.YES);
			}
			params.put("commodityDummyType", PrmsConstant.notVartual);
			Page pageObj = this.getPageObj();			
			ResultDto resultDto=commodityApiService.findList(pageObj, Constant.ORDER_BY_FIELD_CREATE,  Constant.ORDER_BY, params);
			Map<String, Object> returndate=(Map<String, Object>) resultDto.getData();
			List<Commodity> list=(List<Commodity>) returndate.get("list");
			Page page=(Page) returndate.get("page");
			map.put("list", list);
			map.put("pageObj", page);
			map.put("name", name);
			map.put("code", code);
			return "activityGive/findMainCommodity";
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
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("commodityCode", commodityCode);
				ResultDto resultDto=commoditySkuApiService.findList(params);
				this.responseWrite(response, this.getJsonByObject(resultDto.getData()));
				
			} catch (Exception e) {
				logger.error("", e);
				throw new ServiceException(e);
			}
		}

		//赠品列表
		@RequestMapping(value = "/findzpCommodity")
		public String findzpCommodity(CouponAdmin coupon,ModelMap map,
				String name,String code,String giveCommodityId){

			Map<String, Object> params=new HashMap<String, Object>();
			if(!StringUtil.isEmpty(name)){
				params.put(NAME, name);
			}
			if(!StringUtil.isEmpty(code)){
				params.put(CODE, code);
			}
			if(StringUtils.isNoneEmpty(giveCommodityId)){
				List<String> list=new ArrayList<String>();
				list.add(giveCommodityId);
				params.put("goodsId", list);
				params.put("flag", Constant.YES);
			}
			List<String> giveTypes=new ArrayList<String>();
			giveTypes.add(com.ffzx.promotion.api.dto.constant.Constant.NO);
			giveTypes.add(com.ffzx.promotion.api.dto.constant.Constant.GoodsAndCoupon);
			params.put("buyType", "COMMON_BUY");
			params.put(GIVETYPE, Constant.NO);//非主商品
			List<String> statuList=new ArrayList<String>();
			statuList.add(CommodityStatusEnum.COMMODITY_STATUS_ADDED.getValue());
			statuList.add(CommodityStatusEnum.COMMODITY_STATUS_SHELVES.getValue());
			params.put(STATUFor, statuList);//上架

			params.put("commodityDummyType", PrmsConstant.notVartual);//非虚拟商品
			Page pageObj = this.getPageObj();			
			ResultDto resultDto=commodityApiService.findListAll(pageObj, Constant.ORDER_BY_FIELD_CREATE,  Constant.ORDER_BY, params);
			Map<String, Object> returndate=(Map<String, Object>) resultDto.getData();
			List<Commodity> list=(List<Commodity>) returndate.get("list");
			Page page=(Page) returndate.get("page");
			map.put("list", list);
			map.put("pageObj", page);
			map.put("name", name);
			map.put("code", code);
			map.put("giveCommodityId", giveCommodityId);
			return "activityGive/findzpCommodity";
		}
		

		/******
		 * 根据商品code获取sku
		 * @param commodityCode
		 * @return
		 */
		@RequestMapping(value="getuserBuyCount")
		public void getuserBuyCount(HttpServletResponse response,String commodityCode){
			//执行操作
			try {
				String num="111";
				this.responseWrite(response, this.getJsonByObject(num));
				
			} catch (Exception e) {
				logger.error("", e);
				throw new ServiceException(e);
			}
		}
		
		

		@RequestMapping(value = "/formdetail")
		public String formdetail(ActivityGive give,String viewstatus,String id,ModelMap map){
			Map<String, Object>  mapdate=new HashMap<String, Object>();
				 mapdate=activityGiveService.getActivityGiveDate(id);
			ActivityGive activityGive=new ActivityGive();
			map.put("give", mapdate.get("activityGive")==null?activityGive:mapdate.get("activityGive"));
			map.put("couponList", mapdate.get("couponList"));
			map.put("giftSkuList", mapdate.get("skuList"));
			map.put("mainCommodityList", mapdate.get("mainCommodityGive"));
			return "activityGive/formdetail";
		}
		
		
		@RequestMapping(value = "/form")
		@Permission(privilege = {"prms:mz:add","prms:mz:edit"})
		public String form(ActivityGive give,String viewstatus,String id,ModelMap map){
			Map<String, Object>  mapdate=new HashMap<String, Object>();
			if(viewstatus.equals("edit") ||viewstatus.equals("view")  ){
				 mapdate=activityGiveService.getActivityGiveDate(id);
			}

			ActivityGive activityGive=new ActivityGive();
			map.put("give", mapdate.get("activityGive")==null?activityGive:mapdate.get("activityGive"));
			map.put("couponList", mapdate.get("couponList"));
			map.put("giftSkuList", mapdate.get("skuList"));
			map.put("mainCommodityList", mapdate.get("mainCommodityGive"));
			return "activityGive/activityGive_form";
		}
		

		/**
		 * 保存/修改
		 */
		@RequestMapping(value = "save", method = RequestMethod.POST)
		@Permission(privilege = {"prms:mz:add","prms:mz:edit"})
		@ResponseBody
		public ResultVo save(ActivityGive activityGive, BindingResult result){
			ResultVo resultVo = new ResultVo();
			try {					
				String errorMsg = activityGiveService.saveGiveDate(activityGive);

				if(!StringUtil.isEmpty(errorMsg)){

					resultVo.setStatus(Constant.RESULT_EXC);
					resultVo.setHasException(true);
					resultVo.setInfoStr(errorMsg);
//					resultVo.setInfoMap(this.getValidErrors(errorMsg));
					return resultVo;
				}
					
				resultVo.setResult(ServiceResultCode.SUCCESS, "/activityGive/list.do");
			} catch (ServiceException e) {
				resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
				resultVo.setHasException(true);
				logger.error("", e);
			}
			catch (Exception e) {
				resultVo.setHasException(true);
				resultVo.setInfoStr(e.toString());
				logger.error("", e);
			}
			if (resultVo.isHasException()) {
				resultVo.setStatus(Constant.RESULT_EXC);
				return resultVo;
			}
			return resultVo;
		}
	
		/**
		 * 启用禁用
		 * @param coupon
		 * @param map
		 * @return
		 */
		@RequestMapping(value = "/status")
		@Permission(privilege = {"prms:mz:enable"})
		@ResponseBody
		public ResultVo status(ActivityGive activityGive,ModelMap map){
			ResultVo resultVo = new ResultVo();
			try {
				activityGive.setDelFlag(null);
				ActivityCommodity comm=new ActivityCommodity();
				comm.setCommodityId(activityGive.getCommodityId());
				comm.setActivityType(ActivityTypeEnum.ORDINARY_ACTIVITY);
				List<ActivityCommodity> activityCommodityList=null;
				if(activityGive.getActFlag().equals(Constant.ACT_FLAG_YES)){
					//禁用要判断
					activityCommodityList=this.activityCommodityMapper.getActivityCommodityByGive(comm);						
				}
				if(activityCommodityList!=null && activityCommodityList.size()!=0){
					resultVo.setStatus(Constant.RESULT_EXC);
					resultVo.setInfoStr("禁用失败，原因：当前商品已经被加入到普通活动，请先从活动中删除后重试。");
				}else{
					activityGiveService.modifyById(activityGive);
					resultVo.setResult(ServiceResultCode.SUCCESS, "/activityGive/list.do");
				}			
			} catch (ServiceException e) {
				resultVo.setHasException(true);
				logger.error(" ", e);
				throw e;
			} catch (Exception e) {
				resultVo.setHasException(true);
				logger.error(" ", e);
				throw new ServiceException(e);
			}
			if (resultVo.isHasException()) {
				resultVo.setStatus(Constant.RESULT_EXC);
				resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
				return resultVo;
			}
			return resultVo;
		}
		

		/**
		 * 删除
		 * @param coupon
		 * @param map
		 * @return
		 */
		@RequestMapping(value = "/delete")
		@Permission(privilege = {"prms:mz:delete"})
		@ResponseBody
		public ResultVo delete(ActivityGive activityGive,ModelMap map){
			ResultVo resultVo = new ResultVo();
			try {
				activityGive.setDelFlag(Constant.YES);
				activityGiveService.deleteAndhuifu(activityGive);
				resultVo.setResult(ServiceResultCode.SUCCESS, "/activityGive/list.do");
			} catch (ServiceException e) {
				resultVo.setHasException(true);
				logger.error(" ", e);
				throw e;
			} catch (Exception e) {
				resultVo.setHasException(true);
				logger.error(" ", e);
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

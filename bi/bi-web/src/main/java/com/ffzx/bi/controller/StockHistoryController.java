package com.ffzx.bi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.bi.api.service.consumer.CategoryApiConsumer;
import com.ffzx.bi.api.service.consumer.VendorApiConsumer;
import com.ffzx.bi.model.StockHistory;
import com.ffzx.bi.service.StockHistoryService;
import com.ffzx.bi.vo.StockHistoryCustom;
import com.ffzx.bi.vo.StockHistoryVo;
import com.ffzx.commerce.framework.annotation.Permission;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil.ExportModel;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commodity.api.dto.Category;
import com.ffzx.member.api.dto.Vendor;

/**
 * PageDemo
 * 
 * @author CMM
 *
 * 2016年2月18日 下午3:39:37
 */
@Controller
@RequestMapping("/stockHistory/")
public class StockHistoryController extends BaseController {
	
	private final static Logger logger = LoggerFactory.getLogger(StockHistoryController.class);
	
	@Resource
	private StockHistoryService stockHistoryService;
	
	@Resource
	private VendorApiConsumer vendorApiConsumer;
	
	@Resource
	private CategoryApiConsumer categoryApiConsumer; 
	
	@RequestMapping("demo")
	public String demo() {
		return "stockHistory/dashboard_main";
	}
	
	@RequestMapping("list")
	public String list() {
		return "stockHistory/stockHistory_list";
	}
	
	/**
	 * 
	 * @param stockHistoryVo
	 * @param response
	 * @param draw 防止重复的变量， 每次提交时会加1
	 * @param start 记录开始的index，从0开始
	 * @param length 每页要显示多少条	
	 */
	@RequestMapping("listTable")
	public void listTable(StockHistoryVo stockHistoryVo, HttpServletResponse response, int draw, int start, int length) {
		Page pageObj = this.getPageObj();
		pageObj.setPageIndex((start / length) + 1);
		pageObj.setPageSize(length);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (stockHistoryVo != null) {
			StockHistoryCustom shc = stockHistoryVo.getStockHistoryCustom();
			//当按年， 月， 周统计时， 取消掉时间限制
			if (shc != null && ("year".equals(shc.getDate()) || "month".equals(shc.getDate()) || "week".equals(shc.getDate()))) {
				shc.setSearchMinDate(null);
				shc.setSearchMaxDate(null);
			}
		}
		List<StockHistory> stockHistoryList = stockHistoryService.findList(pageObj, "transfer_time", Constant.ORDER_BY, stockHistoryVo);
		//logger.info(stockHistoryList.get(0).toString());
		map.put("data", stockHistoryList);
		map.put("draw", draw);
		map.put("recordsTotal", pageObj.getTotalCount());
		map.put("recordsFiltered", pageObj.getTotalCount());
		this.responseWrite(response, this.getJsonByObject(map));
	}
	
	@RequestMapping("getStockNum")
	public void getStockNum(StockHistoryVo stockHistoryVo, HttpServletRequest request, HttpServletResponse response) {
		String vendorCodeArrStr = getString("vendorCodeArrStr");
		String skuBarcodeArrStr = getString("skuBarcodeArrStr");
		String categoryIdArrStr = getString("categoryIdArrStr");
		
		String init = getString("init");
		if (StringUtil.isNotNull(vendorCodeArrStr)) {
			stockHistoryVo.setVendorCodeList(Arrays.asList(vendorCodeArrStr.split(",")));
		}
		if (StringUtil.isNotNull(categoryIdArrStr)) {
			stockHistoryVo.setCategoryIdList(Arrays.asList(categoryIdArrStr.split(",")));
		}
		if (StringUtil.isNotNull(skuBarcodeArrStr)) {
			stockHistoryVo.setSkuBarcodeList(Arrays.asList(skuBarcodeArrStr.split(",")));
		}
		if (stockHistoryVo != null) {
			StockHistoryCustom shc = stockHistoryVo.getStockHistoryCustom();
			//当按年， 月， 周统计时， 取消掉时间限制
			if (shc != null && ("year".equals(shc.getDate()) || "month".equals(shc.getDate()) || "week".equals(shc.getDate()))) {
				shc.setSearchMinDate(null);
				shc.setSearchMaxDate(null);
			}
		}
		Map<String, Object> map = stockHistoryService.findStockNum(null, "transfer_time", null, stockHistoryVo);
		this.responseWrite(response, JsonMapper.toJsonString(map));
	}

	/** 
	 * @param stockHistory
	 * @return
	 * @author wg  
	 * @time 2016年8月12日 下午2:26:52 
	 */ 
	private Map<String, Object> initSelectCondition(StockHistory stockHistory) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtil.isNotNull(stockHistory.getVendorCode())){
			params.put("vendorCode", stockHistory.getVendorCode());				
		}
		
		if(StringUtil.isNotNull(stockHistory.getCategoryIdOneLevel())){
			params.put("categoryIdOneLevel", stockHistory.getCategoryIdOneLevel());				
		}
		
		if(StringUtil.isNotNull(stockHistory.getCommodityBarcode())){
			params.put("commodityBarcode", stockHistory.getCommodityBarcode());				
		}
		return params;
	}
	
	/**
	 * 供应商选择框
	 * @return
	 */
	@RequestMapping(value = "selectVendor")
	public String selectVendor(Vendor vendor, ModelMap map){
		logger.debug("StockHistoryController-selectVendor=》历史库存报表控制器-供应商选择框 ");
		Page pageObj = this.getPageObj();
		Map<String, Object> params = new HashMap<String, Object>();
		String code = vendor.getCode();
		String name = vendor.getName();
		if (StringUtil.isNotNull(code)) {
			params.put("codeLike", code);
		}
		if (StringUtil.isNotNull(name)) {
			params.put("name", name);
		}
		try {
			Map<String, Object> vendorMap = vendorApiConsumer.findVendorList(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, params);
//			List<Dict> purchaseTypeList=vendorService.getDictByType(Constant.VENDOR_PURCHASE_TYPE);//采购类型
			map.put("vendorList", vendorMap.get("list"));
//			map.put("purchaseTypeList", purchaseTypeList);
			map.put("pageObj", vendorMap.get("page"));
		} catch (ServiceException e) {
			logger.error("StockHistoryController-selectVendor=》历史库存报表控制器-供应商选择框 -ServiceException", e);
		} catch (Exception e) {
			logger.error("StockHistoryController-selectVendor=》历史库存报表控制器-供应商选择框 --Exception", e);
		}
		return "stockHistory/selectVendor";
	}
	
	/**
	 * 商品分类选择框
	 * 
	 * @return
	 */
	@RequestMapping(value = "selectCategory")
	public void selectCategory(HttpServletResponse response) {
		logger.debug("StockHistoryController-selectCategory=》历史库存报表控制器-商品分类选择框");
		try {
			// 查询所有且不分页
			List<Category> listResult = categoryApiConsumer.findCategoryList();
			this.responseWrite(response, this.getJsonByObject(listResult));
		} catch (ServiceException e) {
			logger.error("StockHistoryController-selectCategory=》历史库存报表控制器-商品分类选择框-ServiceException", e);
		} catch (Exception e) {
			logger.error("StockHistoryController-selectCategory=》历史库存报表控制器-商品分类选择框-Exception", e);
		}
	}
	
	/**
	 * 导出
	 * @param request
	 * @param response
	 * @author da.ouyang  
	 */
	@RequestMapping(value = "exportExcel")
	@Permission(privilege = {"bi:stockHistory:export"})
	public void exportExcel(StockHistoryVo stockHistoryVo, HttpServletRequest request, HttpServletResponse response) {
		StockHistoryCustom shc = stockHistoryVo.getStockHistoryCustom();
		if (shc != null) {
			String date = null;
			if (StringUtil.isNotNull(shc.getDate())) {
				if ("day".equals(shc.getDate())) {
					date = "day";
				} else if ("week".equals(shc.getDate())) {
					date = "week";
				} else if ("month".equals(shc.getDate())) {
					date = "month";
				} else if ("year".equals(shc.getDate())) {
					date = "year";
				} else {
					date = "day";
				}
			}
			if (StringUtil.isNotNull(shc.getIsCheckedBarcode())) {
				final String[] headers = new String[] { "sku条形码", "商品名称", "SKU编码", "一级类目", "二级类目", "三级类目", "供应商", "库存数", "日期" };
				final String[] properties = new String[] { "skuBarcode", "commodityName", "skuCode", "categoryNameOneLevel", "categoryNameTwoLevel", "categoryNameThreeLevel", "vendorName", "stocknum", date};
				exportData(headers, properties, stockHistoryVo, response);
			} else if (StringUtil.isNotNull(shc.getGroupByVendor()) && StringUtil.isNotNull(shc.getGroupByCategory())) {
				final String[] headers = new String[] { "供应商", "一级类目", "二级类目", "三级类目", "库存数", "日期"};
				final String[] properties = new String[] { "vendorName", "categoryNameOneLevel", "categoryNameTwoLevel", "categoryNameThreeLevel", "totleStockNum", date};
				exportData(headers, properties, stockHistoryVo, response);
			} else if (StringUtil.isNotNull(shc.getGroupByVendor())) {
				final String[] headers = new String[] { "供应商", "库存数", "日期"};
				final String[] properties = new String[] { "vendorName", "totleStockNum", date};
				exportData(headers, properties, stockHistoryVo, response);
			} else if (StringUtil.isNotNull(shc.getGroupByCategory())) {
				if ("0".equals(shc.getCategoryLevel())) {
					final String[] headers = new String[] { "一级类目", "库存数", "日期" };
					final String[] properties = new String[] { "categoryNameOneLevel", "totleStockNum", date};
					exportData(headers, properties, stockHistoryVo, response);
				} else if ("1".equals(shc.getCategoryLevel())) {
					final String[] headers = new String[] {  "二级类目", "库存数", "日期" };
					final String[] properties = new String[] { "categoryNameTwoLevel", "totleStockNum", date};
					exportData(headers, properties, stockHistoryVo, response);
				} else {
					final String[] headers = new String[] { "一级类目", "二级类目", "三级类目", "库存数", "日期" };
					final String[] properties = new String[] { "categoryNameOneLevel", "categoryNameTwoLevel", "categoryNameThreeLevel", "totleStockNum", date};
					exportData(headers, properties, stockHistoryVo, response);
				}
			}
		}
	}

	private void exportData(final String[] headers, final String[] properties, StockHistoryVo stockHistoryVo, HttpServletResponse response) {
		final String fileName = "库存报表";
		Map<String, Object> info = new HashMap<String, Object>();
		//数据
		final List<StockHistory> stockHistoryList = stockHistoryService.findList(null, "day", null, stockHistoryVo);
		try {
			Map<String, Object> newInfo = new HashMap<>();
			newInfo.put("params",info);
			ExcelExportUtil.exportXlsx(new ExportModel() {
				public String[] getProperties() {
					return properties;
				}
				public String[] getHeaders() {
					return headers;
				}
				public String getFileName() {
					return fileName;
				}
				public List<StockHistory> getData() {
					return stockHistoryList;
				}
			}, response, stockHistoryService, newInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

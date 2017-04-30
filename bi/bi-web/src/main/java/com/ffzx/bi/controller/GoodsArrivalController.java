package com.ffzx.bi.controller;

import java.io.IOException;
import java.util.HashMap;
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

import com.ffzx.bi.api.service.consumer.VendorApiConsumer;
import com.ffzx.bi.model.StockHistory;
import com.ffzx.bi.service.GoodsArrivalService;
import com.ffzx.bi.vo.GoodsArrivalCustom;
import com.ffzx.bi.vo.GoodsArrivalVo;
import com.ffzx.commerce.framework.annotation.Permission;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.ExcelExportUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil.ExportModel;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil.ExportModel;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.member.api.dto.Vendor;

/**
 * PageDemo
 * 
 * @author CMM
 *
 * 2016年2月18日 下午3:39:37
 */
@Controller
@RequestMapping("/goodsArrival/")
public class GoodsArrivalController extends BaseController {
	
	private final static Logger logger = LoggerFactory.getLogger(GoodsArrivalController.class);
	
	@Resource
	private GoodsArrivalService goodsArrivalService;
	
	@Resource
	private VendorApiConsumer vendorApiConsumer;
	
	@RequestMapping("list")
	public String list() {
		return "goodsArrival/goodsArrival_list";
	}
	
	
	
	@RequestMapping("listTable")
	public @ResponseBody ResultVo listTable(GoodsArrivalVo goodsArrivalVo) {
		Page pageObj = this.getPageObj();
		List<GoodsArrivalCustom> goodsArrivalList = goodsArrivalService.findByList(pageObj, null, null, goodsArrivalVo);
		//设置页面显示
	    ResultVo resultVo = new ResultVo();
	    resultVo.setRecordsTotal(pageObj.getTotalCount());
	    resultVo.setInfoData(goodsArrivalList);
		return  resultVo;
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
		return "goodsArrival/selectVendor";
	}
	
	/**
	 * 导出
	 * @param request
	 * @param response
	 * @author da.ouyang  
	 */
	@RequestMapping(value = "exportExcel")
	@Permission(privilege = {"bi:goodsArrival:export"})
	public void exportExcel(GoodsArrivalVo goodsArrivalVo, HttpServletRequest request, HttpServletResponse response) {
		GoodsArrivalCustom gac = goodsArrivalVo.getGoodsArrivalCustom();
		if (gac != null) {
			if (StringUtil.isNotNull(gac.getIsCheckedBarcode())) {
				final String[] headers = new String[] { "供应商", "SKU条形码", "商品名称", "SKU编码", "采购数量", "到货数量", "质检良品数", "退货数", "到货率", "良品率", "退货率"};
				final String[] properties = new String[] { "supplierName", "skuBarcode", "commodityName", "skuCode", "totalPurchaseQuantity", "totalReceivingQuantity", "totalcStorageQuantity", "totalcRejectedQuantity", "arrivalRate", "goodProductsRate", "refundRate", "day"};
				exportData(goodsArrivalVo, response, headers, properties);
			} else {
				final String[] headers = new String[] { "供应商", "采购数量", "到货数量", "质检良品数", "退货数", "到货率", "良品率", "退货率"};
				final String[] properties = new String[] { "supplierName", "totalPurchaseQuantity", "totalReceivingQuantity", "totalcStorageQuantity", "totalcRejectedQuantity", "arrivalRate", "goodProductsRate", "refundRate", "day"};
				exportData(goodsArrivalVo, response, headers, properties);
			}
		}
	}

	private void exportData(GoodsArrivalVo goodsArrivalVo, HttpServletResponse response, final String[] headers,
			final String[] properties) {
		final String fileName = "商品到货";
		Map<String, Object> info = new HashMap<String, Object>();
		//数据
		final List<GoodsArrivalCustom> stockHistoryList = goodsArrivalService.findByList(null, null, null, goodsArrivalVo);
		
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
				public List<GoodsArrivalCustom> getData() {
					return stockHistoryList;
				}
			}, response, goodsArrivalService, newInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

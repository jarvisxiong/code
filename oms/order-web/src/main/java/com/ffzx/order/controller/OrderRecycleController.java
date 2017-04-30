package com.ffzx.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.ExcelExportUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil.ExportModel;
import com.ffzx.order.constant.OrderDetailStatusConstant;
import com.ffzx.order.model.OrderRecycle;
import com.ffzx.order.service.OrderRecycleService;

@Controller
@RequestMapping("/order")
public class OrderRecycleController extends BaseController {

	protected final Logger logger = LoggerFactory.getLogger(OrderRecycleController.class);

	@Autowired
	private OrderRecycleService orderRecycleService;
	
	@RequestMapping(value = "orderRecycleList")
	public String queryOrderRecycleList(ModelMap map) throws Exception {
		Page pageObj = this.getPageObj();
		Map<String, Object> params = this.getParaMap();
		Object multipleCondition = params.get("searchValue");
		if (null != multipleCondition) {
			multipleCondition = multipleCondition.toString().trim();
			params.put("multipleCondition", "Y");
			params.put("orderNoSigned", multipleCondition); // 销售发货单号
			params.put("barCodeSigned", multipleCondition); // 销售发货订单条形码
			params.put("saleNo", multipleCondition); // 销售订单号
			params.put("partner", multipleCondition); // 合伙人
			params.put("partnerPhone", multipleCondition); // 合伙人电话
		}

		// 按签收单号
		String groupByField = "order_no_signed";
		params.put("groupByField", groupByField);

		String orderByField = "create_date";
		String orderBy = "desc";
		List<OrderRecycle> orderRecycleList = orderRecycleService.queryByPage(pageObj, params, orderByField, orderBy);

		map.put("params", params);
		map.put("orderRecycleList", orderRecycleList);
		map.put("pageObj", pageObj);
		return "orderRecovery/order_recycle_list";
	}

	@ResponseBody
	@RequestMapping(value = "querySalesNo")
	public Set<String> querySalesNo(String orderNoSigned) throws Exception {
		logger.info("查询销售单号orderNoSigned=" +orderNoSigned);
		
		List<String> salesNoList = orderRecycleService.querySalesNoByOrderNoSigned(orderNoSigned);
		return new HashSet<String>(salesNoList);
	}

	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletResponse response) throws Exception {
		final String[] headers = new String[] { "客户签收单号", "客户签收单条形码", "合伙人", "合伙人工号", "销售订单号", "销售发货订单号", "商品条形码", "商品名称", "数量",
				"商品单价", "交易金额", "物流是否回收", "财务是否回收" };

		final String[] properties = new String[] { "orderNoSigned", "barCodeSigned", "partner", "partnerNo", "saleNo",
				"saleNoDelivery", "commodityBarCode", "commodityName", "commodityNum", "commodityPrice", "commodityMoney",
				"logisticsRecyleStatus", "financeRecyleStatus" };

		final List<Map<String, String>> listMap = new ArrayList<>();
		final String fileName = "收货人签收单回收管理";

		Map<String, Object> params = this.getParaMap();
		Object multipleCondition = params.get("searchValue");

		String orderByField = "partner_no, t.order_no_signed, t.sale_no";
		String orderBy = "desc";
		if (null != multipleCondition) {
			multipleCondition = multipleCondition.toString().trim();
			params.put("multipleCondition", "Y"); // 是否需要满足单个文本框输入多个值的查询
			
			params.put("orderNoSigned", multipleCondition); // 销售发货单号
			params.put("barCodeSigned", multipleCondition); // 销售发货订单条形码
			params.put("saleNo", multipleCondition); // 销售订单号
			params.put("partner", multipleCondition); // 合伙人
			params.put("partnerPhone", multipleCondition); // 合伙人电话

			
		}
		params.put("isSort", "Y"); // 是否需要排序
		params.put("orderByField", orderByField);
		params.put("orderBy", orderBy);
		
		List<OrderRecycle> orderRecycleList = orderRecycleService.queryByParams(params);
		if (orderRecycleList.size() == 0) {
			return;
		}

		for (OrderRecycle orderRecycle : orderRecycleList) {
			Map<String, String> orMap = new HashMap<String, String>();
			orMap.put("orderNoSigned", orderRecycle.getOrderNoSigned());
			orMap.put("barCodeSigned", orderRecycle.getBarCodeSigned());
			orMap.put("partner", orderRecycle.getPartner());
			orMap.put("partnerNo", orderRecycle.getPartnerNo());
			orMap.put("saleNo", orderRecycle.getSaleNo());
			orMap.put("saleNoDelivery", orderRecycle.getSaleNoDelivery());
			orMap.put("commodityBarCode", orderRecycle.getCommodityBarCode());
			orMap.put("commodityName", orderRecycle.getCommodityName());
			orMap.put("commodityNum",
					orderRecycle.getCommodityNum() == null ? "" : orderRecycle.getCommodityNum().toString());
			orMap.put("commodityPrice",
					orderRecycle.getCommodityPrice() == null ? "" : orderRecycle.getCommodityPrice().toString());
			orMap.put("commodityMoney",
					orderRecycle.getCommodityMoney() == null ? "" : orderRecycle.getCommodityMoney().toString());
			orMap.put("logisticsRecyleStatus", orderRecycle.getLogisticsRecyleStatus());
			orMap.put("financeRecyleStatus", orderRecycle.getFinanceRecyleStatus());
			listMap.add(orMap);
		}

		ExcelExportUtil.exportXls(new ExportModel() {
			public String[] getProperties() {
				return properties;
			}

			public String[] getHeaders() {
				return headers;
			}

			public List<Map<String, String>> getData() {
				return listMap;
			}

			public String getFileName() {
				return fileName;
			}
		}, response);

	}

	@RequestMapping(value = "to_scan_confirm")
	public String forwardSacnPage(String flag, ModelMap map) throws Exception {
		map.put("flag", flag);
		return "orderRecovery/scan_confirm";
	}

	@RequestMapping(value = "doUpdateStatus")
	@ResponseBody
	public String doUpdateStatus(String barCodeSigned, String flag) throws Exception {
		if (StringUtils.isEmpty(barCodeSigned)) {
			return "请输入签收单号";
		}

		List<OrderRecycle> orderRecycleList = orderRecycleService.queryByOrderNoSigned(barCodeSigned);
		if (orderRecycleList.size() == 0) {
			return "该签收条形码不存在";
		}

		// 判断该签收单号是否已回收
		OrderRecycle orderRecycle = orderRecycleList.get(0);
		if (orderRecycleService.checkStatusRecovered(orderRecycle, flag)) {
			return "该签收单已回收";
		}

		// 修改签收订单为‘已回收状态’
		String status = OrderDetailStatusConstant.RECOVERED_ALREADY;
		orderRecycleService.updateStatusByBarCodeSigned(barCodeSigned, status, flag);

		return "该签收单状态已修改为'已回收'";
	}
}

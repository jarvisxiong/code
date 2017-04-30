package com.ffzx.order.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.basedata.api.dto.Address;
import com.ffzx.basedata.api.dto.Dict;
import com.ffzx.basedata.api.dto.Partner;
import com.ffzx.basedata.api.dto.SubSystemConfig;
import com.ffzx.basedata.api.dto.Warehouse;
import com.ffzx.basedata.api.service.AddressApiService;
import com.ffzx.basedata.api.service.DictApiService;
import com.ffzx.basedata.api.service.PartnerApiService;
import com.ffzx.basedata.api.service.SubSystemConfigApiService;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.thirdparty.ShortMsgEnum;
import com.ffzx.commerce.framework.thirdparty.ShortMsgFactory;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil.ExportModel;
import com.ffzx.commerce.framework.utils.ExcelReader;
import com.ffzx.commerce.framework.utils.JPushUtils;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UploadFileUtils;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.order.api.dto.GoodsVo;
import com.ffzx.order.api.dto.ImportBuildOrderVo;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderRecord;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.enums.BuyTypeEnum;
import com.ffzx.order.api.enums.OrderDetailStatusEnum;
import com.ffzx.order.api.enums.OrderOperationRecordEnum;
import com.ffzx.order.api.enums.OrderStatusEnum;
import com.ffzx.order.constant.OrderConstant;
import com.ffzx.order.constant.OrderDetailStatusConstant;
import com.ffzx.order.service.OmsOrderFormService;
import com.ffzx.order.service.OmsOrderRecordService;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.order.service.OmsOrderdetailService;
import com.ffzx.order.utils.OmsConstant;

import net.sf.json.JSONArray;

/**
 * 订单控制器
 * 
 * @className OrderController.java
 * @author hyl
 * @date 2016年3月29日 上午9:54:36
 * @version 1.0
 */
@Controller
@RequestMapping("/order")
public class OmsOrderController extends BaseController {
	// 记录日志
	protected final Logger logger = LoggerFactory.getLogger(OmsOrderController.class);
	@Autowired
	private OmsOrderService omsOrderService;
	@Autowired
	private OmsOrderdetailService omsOrderdetailService;
	@Autowired
	private OmsOrderRecordService omsOrderRecordService;
	@Autowired
	private PartnerApiService partnerApiService;
	@Autowired
	private DictApiService dictApiService;
	@Autowired
	private AddressApiService addressApiService;
	@Autowired
	private SubSystemConfigApiService subSystemConfigApiService;
	@Autowired
	private OmsOrderFormService omsOrderFormService;
	@Resource
	private RedisUtil redisUtil;
	@RequestMapping(value = "list")
	public String orderList(ModelMap map) {
		Page pageObj = this.getPageObj();
		Map<String, Object> params = this.getParaMap();
		String L3MONTH = DateUtil.formatDate(DateUtil.getLastMonth(new Date(), -3)) + " 00:00:00";
		if (params.size() == 0||(params.size()==1&&params.containsKey("tabPageId") )|| (params.containsKey("queryType") && params.get("queryType").toString() == "L3MONTH")) {
			if (StringUtils.isNotBlank(String.valueOf(params.get("createDateStart"))))
				params.put("createDateStart", L3MONTH);
			params.put("queryType", "L3MONTH");
		}
		params.put("orderBy", "t.create_date desc,t.order_no desc");
		params.put("isPerson", getString("isPerson"));
		params.put("delFlag", Constant.DELTE_FLAG_NO);
		List<OmsOrder> orderList = null;
		SubSystemConfig ucSystem = null;
		SubSystemConfig orderSystem = null;
		List<SubSystemConfig> systemConfigList = null;
		try {
			orderList = omsOrderService.queryByPage(pageObj, params);	
			//获取子系统信息(域名和端口)
			ResultDto allResult = subSystemConfigApiService.getSubSystemConfigALl();
			if(allResult.getCode().equals(ResultDto.OK_CODE)){
				systemConfigList = (List<SubSystemConfig>)allResult.getData();
				for (SubSystemConfig subSystemConfig : systemConfigList) {
					if(subSystemConfig!= null && OrderConstant.UC_CODE.equals(subSystemConfig.getSubSystemCode())){
						ucSystem = subSystemConfig;
					}else if(subSystemConfig!= null && OrderConstant.OMS_CODE.equals(subSystemConfig.getSubSystemCode())){
						orderSystem = subSystemConfig;
					}
					if(ucSystem != null && orderSystem != null){
						break;
					}
				}
			}else{
				throw new ServiceException("SubSystemConfigApiConsumer-getSubSystem=>dubbo调用失败：" + allResult);
			}
		} catch (ServiceException e) {
			logger.error("OrderController.orderList ServiceException=》订单管理-列表", e);
			throw e;
		} catch (Exception e) {
			logger.error("OrderController.orderList Exception=》订单管理-列表", e);
			throw new ServiceException(e);
		}
		map.put("orderStatusList", OrderStatusEnum.toList());
		map.put("buyTypeList", BuyTypeEnum.toList());
		
		map.put("afterSaleStatusList", OrderDetailStatusEnum.toList());
		//map.put("afterSaleStatusList", AfterSaleStatusEnum.toList());
		map.put("params", params);
		map.put("pageObj", pageObj);
		map.put("list", orderList);
		map.put("L3MONTH", L3MONTH);
		map.put("person", getInt("isPerson"));
		map.put("errorOrder", getInt("isErrorOrder"));
		map.put("image_path", System.getProperty("image.web.server"));
		map.put("ucDomain", ucSystem.getSubSystemDomain());
		map.put("ucPort", ucSystem.getSubSystemPort());
		map.put("orderDomain", orderSystem.getSubSystemDomain());
		map.put("orderPort", orderSystem.getSubSystemPort());
		return "order/order_list";
	}

	@RequestMapping(value = "view")
	public String orderView(String id, ModelMap map,String orderNo,String pageType) {
		OmsOrder data = null;
		SubSystemConfig ucSystem = null;
		SubSystemConfig orderSystem = null;
		List<SubSystemConfig> systemConfigList = null;
			try {
				if(StringUtil.isNotNull(id)){
					data = omsOrderService.findById(id);
				}else if(StringUtil.isNotNull(orderNo)){
					data=omsOrderService.getOmsOrderByOrderNo(orderNo);
				}
				// 获取订单明细
				Map<String, Object> odParams = new HashMap<String, Object>();
				odParams.put("orderId", data.getId());
				List<OmsOrderdetail> odList = omsOrderdetailService.findByBiz(odParams);
				if (odList != null) {
					data.setDetailList(odList);
				}
				map.put("pageType", pageType);
				map.put("data", data);
				odParams.clear();
				odParams.put("orderBy", "t.create_date desc,t.outbound_status desc");// 降序查询
				odParams.put("orderNo", data.getOrderNo());
				odParams.put("recordType", OrderDetailStatusConstant.NO);
				List<OmsOrderRecord> orderRecordList = omsOrderRecordService.findByBiz(odParams);
				map.put("orderRecordList", orderRecordList);
				Map<String, Object> wms_params = new HashMap<>();
				wms_params.put("orderNo", data.getOrderNo());
				wms_params.put("recordType", OrderDetailStatusConstant.YES);
				List<OmsOrderRecord> wms_OrderRecordList = this.omsOrderRecordService.findByPage(null, "create_date desc,outbound_status desc","", wms_params);
				map.put("wms_OrderRecordList", wms_OrderRecordList);
				map.put("image_path", System.getProperty("image.web.server"));
				//获取子系统信息(域名和端口)
				ResultDto allResult = subSystemConfigApiService.getSubSystemConfigALl();
				if(allResult.getCode().equals(ResultDto.OK_CODE)){
					systemConfigList = (List<SubSystemConfig>)allResult.getData();
					for (SubSystemConfig subSystemConfig : systemConfigList) {
						if(subSystemConfig!= null && OrderConstant.UC_CODE.equals(subSystemConfig.getSubSystemCode())){
							ucSystem = subSystemConfig;
						}else if(subSystemConfig!= null && OrderConstant.OMS_CODE.equals(subSystemConfig.getSubSystemCode())){
							orderSystem = subSystemConfig;
						}
						if(ucSystem != null && orderSystem != null){
							break;
						}
					}
				}else{
					throw new ServiceException("SubSystemConfigApiConsumer-getSubSystem=>dubbo调用失败：" + allResult);
				}
				map.put("ucDomain", ucSystem.getSubSystemDomain());
				map.put("ucPort", ucSystem.getSubSystemPort());
				map.put("orderDomain", orderSystem.getSubSystemDomain());
				map.put("orderPort", orderSystem.getSubSystemPort());
			} catch (Exception e) {
				logger.error("OrderController-orderView-Exception=》订单管理-订单详情-Exception", e);
				throw new ServiceException(e);
			}
		
		return "order/order_view";
	}

	@RequestMapping(value = "print")
	public String print(ModelMap map) {
		try {
			String ids = getString("ids");
			List<OmsOrder> orderList = null;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ids", ids);
			orderList = omsOrderService.queryByList(params);
			map.put("list", orderList);
			map.put("ids", ids);
		} catch (Exception e) {
			logger.error("OrderController-print-Exception=》订单管理-订单打印-Exception", e);
			throw new ServiceException(e);
		}
		return "order/order_print";
	}
	
	@RequestMapping(value = "importBuildOrder")
	public String importBuildOrder(){
		return "order/order_import_buildOrder";
	}
	
	@RequestMapping(value="/importBuildOrderExcel")
	public  String  importExcel(HttpServletRequest request)throws InterruptedException, ExecutionException {
		String errorMsg = "导入成单失败";
		ImportBuildOrderVo importBuildOrderVo = null;
		try {
			List<String> list = new ArrayList<String>();
			list = UploadFileUtils.uploadFiles(request);
			if (list != null && list.size() == 1) {
 				for (String path : list) {
					List<String[]> listRow = ExcelReader.getExcelData(path);
					if((listRow.size()-2)>110){
						throw new ServiceException("成单商品不能大于110个");
					}
					importBuildOrderVo =  omsOrderService.importBuildOrderExcel(listRow);
					String errorCode = importBuildOrderVo.getErrorCode();
					if(errorCode.equals("1")){
						throw new ServiceException(importBuildOrderVo.getMsg());
					}
				}
			}else{
				logger.error("导入成单失败：一次只能导入一个文件");
				throw new ServiceException("文件导入失败，一次最多导入一个文件");
			}
			
			String  memberId = importBuildOrderVo.getMemberId();
			String  memberAddressId = importBuildOrderVo.getMemberAddressId();
			List<GoodsVo> goodsList = importBuildOrderVo.getGoodsVoList();
			OmsOrder omsOrder = null;
			try {
			omsOrder = this.omsOrderFormService.importBuildOrder(memberId, memberAddressId, goodsList);
			} catch (ServiceException e) {
				logger.error("===importExcel===成单[业务]异常",e);
				if(e.getCode()==1){
					errorMsg = e.getMessage();
					throw new ServiceException(errorMsg);
				}else{
					throw e;
				}
			}
			errorMsg = "导入成单成功，单号【"+omsOrder.getOrderNo()+"】,下单人【"+omsOrder.getMemberPhone()+"】";
		} catch (Exception e) {
			logger.error("导入成单失败", e);
			errorMsg = e.getMessage();
		}
		
		request.setAttribute("msg", errorMsg);
		return "order/order_importBuildMsg";
	}
	@RequestMapping(value="downFile")
	public void downFile(@RequestParam(required = true, value = "path") String path, HttpServletResponse response) throws IOException {
		if (!StringUtil.isEmpty(path)) {
			String[] pathParts = path.split("/");
			String realPath = this.getRequest().getSession().getServletContext().getRealPath("");
			for (int i = 0; i < pathParts.length; i++) {
				realPath += File.separator + pathParts[i];
			}
			File file = new File(realPath);
			// 取得文件名。
			String filename = path.substring(path.lastIndexOf("/") + 1, path.length());
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gb2312"), "iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);
			byte[] b = new byte[1024];// 相当于我们的缓存
			long k = 0;// 该值用于计算当前实际下载了多少字节
			// 从response对象中得到输出流,准备下载
			OutputStream myout = response.getOutputStream();
			// 开始循环下载
			while (k < file.length()) {

				int j = buff.read(b, 0, 1024);
				k += j;
				// 将b中的数据写到客户端的内存
				myout.write(b, 0, j);
			}
			// 将写入到客户端的内存的数据,刷新到磁盘
			myout.flush();
		}
	}
	@RequestMapping(value = "ajax_printInsertOmsOrderRecord")
    public void ajax_printInsertOmsOrderRecord( HttpServletResponse response){
    	List<OmsOrder> orderList = null;
    	try {
    		String _ids = getString("ids");
    		String[] idsArr =  _ids.split(",");
    		String idsStr = "";
    		for(int i=0;i<idsArr.length;i++){
    			if(idsArr.length==1){
    				idsStr+=idsArr[i];
    			}else{
    				if(i!=0){idsStr+=",";}
    				idsStr+=idsArr[i];
    			}
    			
    		}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ids", idsStr);
			orderList = omsOrderService.queryByList(params);
			for (OmsOrder omsOrder : orderList) {
				String msg = "打印订单";
				this.omsOrderService.insertOmsOrderRecord(omsOrder,RedisWebUtils.getLoginUser().getId(), RedisWebUtils.getLoginUser().getName()==null?RedisWebUtils.getLoginUser().getLoginName(): RedisWebUtils.getLoginUser().getName(), msg, "0");
			}
			getOutputMsg().put("MSG", "操作成功");
		} catch (ServiceException e) {
			logger.error("OrderController-ajax_cancelOrder-ServiceException", e);
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			throw e;
		} catch (Exception e) {
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			logger.error("OrderController-ajax_cancelOrder-Exception", e);
			//throw new ServiceException(e);
		}
		outPrint(response, this.getJsonByObject(this.getOutputMsg()));
    }
	@RequestMapping(value = "ajax_cancelOrder")
	public void ajax_cancelOrder(String id, HttpServletResponse response) {
		try {
			OmsOrder omsOrder = omsOrderService.findById(id);
			this.omsOrderService.operateOrderByType(omsOrder.getOrderNo(), OrderOperationRecordEnum.MANUAL_CANCEL.getValue());
			getOutputMsg().put("STATUS", ServiceResultCode.SUCCESS);
			getOutputMsg().put("MSG", "操作成功");
		} catch (ServiceException e) {
			logger.error("OrderController-ajax_cancelOrder-ServiceException", e);
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			throw e;
		} catch (Exception e) {
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			logger.error("OrderController-ajax_cancelOrder-Exception", e);
			//throw new ServiceException(e);
		}
		outPrint(response, this.getJsonByObject(this.getOutputMsg()));
	}

	
	@RequestMapping(value = "ajax_checkExport")
	public void ajax_checkExport(HttpServletResponse response) {
		String lockKey = "ORDEREXPORT_LOCKKEY";
		if(!redisUtil.exists(lockKey)){
			getOutputMsg().put("STATUS", ServiceResultCode.SUCCESS);
			getOutputMsg().put("MSG", "允许导出");
		}else{
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			getOutputMsg().put("MSG","服务正忙，请稍后重试导出功能");
		}
		outPrint(response, this.getJsonByObject(this.getOutputMsg()));
	}
	/**
	 * 订单导出
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "orderExport")
	public void orderExport(HttpServletRequest request, HttpServletResponse response) {
		StopWatch sw_orderExport = new StopWatch("orderExport");
		String lockKey = "ORDEREXPORT_LOCKKEY";
		final String[] headers = new String[] { "订单编号", "订单状态", "购买类型", "配送人", "配送人工号", "配送仓库", "条形码",
				"商品名称", "数量", "单价", "下单人", "下单人电话", "收货人电话", "下单时间", "支付时间", "售后", "订单类型","全地址",
				"收货人姓名", "订单总价", "实际支付金额", "优惠券金额","红包金额","优惠金额", "发票", "供应商", "供应商编码" };
		
		final String[] properties = new String[] { "orderNo", "status", "buyType", "sendPersonName", "partnerCode",
				"storageName", "barCode", "name", "buyNum", "price", "creatorName", "creatorPhone", "receiverPhone",
				"createTime", "payTime", "afterSale", "orderType", "allAddr",
				"receiverName", "totalPrice", "actPrice","couponAmount","redPacketAmount", "favorablePrice","isInvoice","vendorName", "vendorId" };
		
		final List<Map<String, String>> listMap = new ArrayList<>();
		List<OmsOrder> list = null;
		Map<String, Object> params = this.getParaMap();
		try {
			if(!redisUtil.exists(lockKey)){
			redisUtil.set(lockKey, "1", OmsConstant.REDIS_EXPIRETIME_15MIN);
			final String fileName = "订单导出";
			params.put("orderBy", "t.create_date desc,t.order_no desc");
			params.put("isPerson", getString("isPerson"));
			params.put("delFlag", Constant.DELTE_FLAG_NO);
			sw_orderExport.start("sw_orderExport:queryListExport查询数据"); 
			list = omsOrderService.queryListExport(params);
			//list = omsOrderService.queryByList(params);
			sw_orderExport.stop();
			logger.info("订单列表导出数据，OmsOrderController==>>orderExport==》》订单数量：size【"+list.size()+"】条");
			sw_orderExport.start("sw_orderExport:查询数据处理");
			for (OmsOrder omsOrder : list) {
				if (omsOrder.getDetailList().size() > 0)
					for (OmsOrderdetail detail : omsOrder.getDetailList()) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("orderNo", omsOrder.getOrderNo());
						map.put("status", omsOrder.getStatusName()==null?"":omsOrder.getStatusName());
						map.put("buyType", omsOrder.getBuyType()==null?"":omsOrder.getBuyTypeName());
						map.put("sendPersonName", omsOrder.getSendPersonName());
						map.put("partnerCode", omsOrder.getPartnerCode());
						map.put("storageName", omsOrder.getStorageName());
						map.put("barCode", detail.getCommodityBarcode()==null?"":detail.getCommodityBarcode());
						map.put("name", detail.getCommodity()==null?"":detail.getCommodity().getName());
						map.put("buyNum", detail.getBuyNum()==null?"":detail.getBuyNum().toString());
						map.put("price", detail.getActSalePrice()==null?"":detail.getActSalePrice().toString());
						map.put("creatorName", omsOrder.getMemberName());
						map.put("creatorPhone", omsOrder.getMemberPhone());
						map.put("receiverPhone", omsOrder.getConsignPhone());
						map.put("createTime", DateUtil.formatDateTime(omsOrder.getCreateDate()));
						map.put("payTime", DateUtil.formatDateTime(omsOrder.getPayTime()));
						map.put("afterSale", detail.getOrderDetailStatus()==null?"":formartAfterStatus(detail.getOrderDetailStatus()));
						map.put("orderType", omsOrder.getOrderTypeName());
						//map.put("selfSelectAddr", omsOrder.getRegionName()==null?"":omsOrder.getRegionName());
						//map.put("selfFilAddr",omsOrder.getDetailedAddress()==null?"":omsOrder.getDetailedAddress());
						map.put("allAddr", omsOrder.getAddressInfo());
						map.put("receiverName", omsOrder.getConsignName());
						map.put("totalPrice", omsOrder.getTotalPrice() == null ? "" : omsOrder.getTotalPrice().toString());
						map.put("actPrice", omsOrder.getActualPrice()==null ? "" : omsOrder.getActualPrice().toString());
						map.put("couponAmount", detail.getFavouredAmount()==null ? "0" : detail.getFavouredAmount().toString());
						map.put("redPacketAmount", detail.getRedPacketAmount()==null ? "0" : detail.getRedPacketAmount().toString());
						BigDecimal favorablePrice = new BigDecimal(String.valueOf("0"));
						favorablePrice = favorablePrice.add(detail.getFavouredAmount()==null ?new BigDecimal(String.valueOf("0")):detail.getFavouredAmount());
						favorablePrice = favorablePrice.add(detail.getRedPacketAmount()==null ?new BigDecimal(String.valueOf("0")): detail.getRedPacketAmount());
						map.put("favorablePrice", favorablePrice.toString());
						map.put("isInvoice", null!=omsOrder.getIsInvoice()?(omsOrder.getIsInvoice().equals("1") ? "有" : "无"):"");
						map.put("vendorName", detail.getCommodity()==null?"":detail.getCommodity().getVendorName());
						map.put("vendorId", detail.getCommodity()==null?"":detail.getCommodity().getVendorCode());
						listMap.add(map);
					}
			}
			logger.info("订单列表导出数据，OmsOrderController==>>orderExport==》》导出订单明细数量：size【"+listMap.size()+"】条");
			sw_orderExport.stop();
			sw_orderExport.start("sw_orderExport:导出工具处理");
			ExcelExportUtil.exportXls(new ExportModel() {
				public String[] getProperties() {
					return properties;
				}

				public String[] getHeaders() {
					return headers;
				}

				public List getData() {
					return listMap;
				}

				public String getFileName() {
					return fileName;
				}
			}, response);
			sw_orderExport.stop();
			logger.error("导出订单数据耗时："+sw_orderExport.prettyPrint());
			}
		} catch (IOException e) {
			logger.error("OrderController-orderExport-Exception=》订单管理-订单导出-Exception", e);
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("OrderController-orderExport-Exception=》订单管理-订单导出-Exception", e);
			throw new ServiceException(e);
		} finally{
			redisUtil.remove(lockKey);
		}


	}
	private  String formartAfterStatus(String value){
		String statusName = "";
		switch(value)
		//0：正常，1：退款处理中，2：换货处理中，3：退款成功，4：换货成功
		{
		case "0":
			statusName = "正常";
			break;
		case "1":
			statusName = "退款处理中";
			break;
		case "2": 
			statusName = "换货处理中";
			break;
		case "3":
			statusName = "退款成功";
			break;
		case "4":
			statusName = "换货成功";
			break;
		default:statusName = "其他";
		}
		return statusName;
	}
	@RequestMapping("comments")
	public String oneBtnPub(ModelMap map) {
		String phones_Str = getString("phones_Str");
		if (!StringUtil.isStrTrimEmpty(phones_Str)) {
			map.put("phones_Str", phones_Str);
		}
		return "order/order_comments";
	}

	@RequestMapping("updateOrderPrice")
	public String updateOrderPrice(String id, ModelMap map) {

		OmsOrder data = null;
		if (StringUtil.isNotNull(id)) {
			try {
				data = omsOrderService.findById(id);
				// 获取订单明细
				Map<String, Object> odParams = new HashMap<String, Object>();
				odParams.put("orderId", data.getId());
				List<OmsOrderdetail> odList = omsOrderdetailService.findByBiz(odParams);
				if (odList != null) {
					data.setDetailList(odList);
				}
			} catch (Exception e) {
				logger.error("OrderController-updateOrderPrice-Exception=》订单管理-修改订单价格-Exception", e);
				throw new ServiceException(e);
			}
		}
		map.put("data", data);
		return "order/order_updateorderprice";
	}

	@RequestMapping(value = "ajax_savePrice")
	public void ajax_advertDelete(HttpServletResponse response) {
		try {
			String id = getString("dataId");
			String totalPrice = getString("totalPrice");
			String actualPrice = getString("actualPrice");
			String detailJson = getString("detailJson");

			JSONArray array = null;
			List<OmsOrderdetail> detailList = null;
			if (StringUtil.isNotNull(detailJson)) {
				array = JSONArray.fromObject(detailJson);
				detailList = (List<OmsOrderdetail>) JSONArray.toCollection(array, OmsOrderdetail.class);
			}
			OmsOrder data = new OmsOrder();
			data.setId(id);
			data.setTotalPrice(new BigDecimal(totalPrice));
			data.setActualPrice(new BigDecimal(actualPrice));
			this.omsOrderService.updatePrice(data, detailList);
			getOutputMsg().put("STATUS", ServiceResultCode.SUCCESS);
			getOutputMsg().put("MSG", "操作成功");
		} catch (ServiceException e) {
			logger.error("OrderController-ajax_advertDelete-ServiceException", e);
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			throw e;
		} catch (Exception e) {
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			getOutputMsg().put("MSG", e.getMessage());
			logger.error("OrderController-ajax_advertDelete-Exception", e);
			//throw new ServiceException(e);
		}
		outPrint(response, this.getJsonByObject(this.getOutputMsg()));
	}

	/**
	 * 消息推送
	 * 
	 * @return
	 */
	@RequestMapping("ajaxToSend")
	@ResponseBody
	public ResultVo ajaxToSend() {
		// 返回结果对象
		ResultVo resultVo = new ResultVo();
		String content = getString("content");
		String receivers = getString("receivers");
		String sendType = getString("sendType");

		if (sendType.equals("PHONE_MSG")) {
			content = "【大麦场】" + content;
//          Map<String, String> result = MxtSmsUtil.sendMsg(receivers, content, 1, new Date());
//			if (result.get("STATE").equals("Sucess")) {
//				resultVo.setStatus(Constant.RESULT_SCS);
//				resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
//			} else {
//				resultVo.setStatus(Constant.RESULT_ERR);
//				resultVo.setInfoStr("短信发送失败,请联系管理员");
//			}
			try
			{
				ShortMsgFactory.getInstance().getShortMsg().send(ShortMsgEnum.send_txt, receivers, content);
				resultVo.setStatus(Constant.RESULT_SCS);
				resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
			}
			catch(Exception e)
			{
				resultVo.setStatus(Constant.RESULT_ERR);
				resultVo.setInfoStr("短信发送失败,请联系管理员");
			}
			

		} else {
			String[] receiver = receivers.split(";");
			for (String string : receiver) {
				JPushUtils.send_all_alias(content, string);
			}
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
		}

		return resultVo;
	}

	@RequestMapping("updateAddr")
	public String updateAddr(String id, ModelMap map) {
		OmsOrder data = null;
		if (StringUtil.isNotNull(id)) {
			try {
				data = omsOrderService.findById(id);
			} catch (Exception e) {
				logger.error("OrderController-updateAddr-Exception=》订单管理-修改订单地址-Exception", e);
				throw new ServiceException(e);
			}
		}
		map.put("data", data);
		return "order/order_updateaddr";
	}
	//重新分配合伙人
	@RequestMapping("updatePartner")
	public String updatePartner(String id, ModelMap map) {
		OmsOrder data = null;
		if (StringUtil.isNotNull(id)) {
			try {
				data = omsOrderService.findById(id);
			} catch (Exception e) {
				logger.error("OrderController-updatePartner-Exception=》订单管理-重新分配合伙人 -Exception", e);
				throw new ServiceException(e);
			}
		}
		map.put("data", data);
		 Map<String, Object> resultMap;
		try {
			resultMap = this.omsOrderService.getAddressInfo(data.getRegionId());
		    if (resultMap != null) {
			Address address = (Address) resultMap.get("address");
			map.put("address", address);
			Partner partner = (Partner) resultMap.get("partner");
			map.put("partner", partner);
			// 当配送人地址修改 则 将其相对应的合伙人信息以及配送人信息改掉
			if (partner != null && StringUtil.isNotNull(partner.getId())) {
			} else {
				throw new Exception("无法查到收货地址所对应的合伙人,请检查所选地址是否配置了合伙人");
			}
			}
		    String logisticsStatus = this.omsOrderService.getLogisticsStatus(data.getOrderNo());//若0代表 物流未分配
		    map.put("logisticsStatus", logisticsStatus);
		} catch (Exception e) {
			logger.error("查询数据失败", e);
			map.put("msg",  e.getMessage());
			//throw new ServiceException(e);
		}
		
		return "order/order_updatePartner";
	}

	@RequestMapping("updateServicePoint")
	public String updateServicePoint(String id, ModelMap map) {
		OmsOrder data = null;
		if (StringUtil.isNotNull(id)) {
			try {
				data = omsOrderService.findById(id);
			} catch (Exception e) {
				logger.error("OrderController-updateServicePoint-Exception=》", e);
				throw new ServiceException(e);
			}
		}
		map.put("data", data);
		return "order/order_updateservicepoint";
	}
	//初始化订单收货信息
		private  void initOrderInfoByPartnerId(OmsOrder omsOrder) throws Exception{
			  Map<String, Object> resultMap = this.omsOrderService.getAddressInfo(omsOrder.getRegionId());
				Address address = (Address) resultMap.get("address");
				Partner partner = (Partner) resultMap.get("partner");
				//区域仓库 
				Warehouse aeraWarehouse = (Warehouse) resultMap.get("warehousecode");
				//中央仓库
				Warehouse centerWarehouse = (Warehouse) resultMap.get("warehouse");
				// 当配送人地址修改 则 将其相对应的合伙人信息以及配送人信息改掉
				if (partner != null && StringUtil.isNotNull(partner.getId())) {
					omsOrder.setPartnerId(partner.getId());// 合伙人ID
				} else {
					throw new Exception("无法查到收货地址所对应的合伙人,请检查所选地址是否配置了合伙人");
				}
				if (!StringUtil.isNotNull(partner.getPartnerCode())) {
					omsOrder.setPartnerCode(partner.getPartnerCode());// 合伙人编码
				}
				omsOrder.setSendPerson(partner.getId());// 配送员id即合伙人
				omsOrder.setSendPersonName(partner.getName());// 配送员姓名即合伙人
				omsOrder.setServicePoint(partner.getAddressName() + partner.getAddressDeal());// 配送信息是合伙人的具体地址
				//初始化仓库信息
				omsOrder.setStorageId(aeraWarehouse.getId());
				omsOrder.setStorageName(aeraWarehouse.getName());
				omsOrder.setWarehouseCode(aeraWarehouse.getCode());
				//初始化所在县仓信息
				omsOrder.setCountyStoreId(aeraWarehouse.getId());
				omsOrder.setCountyStoreName(aeraWarehouse.getName());
				omsOrder.setCountyStoreCode(aeraWarehouse.getCode());
		}
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "ajax_save")
	public void ajax_save(OmsOrder data, HttpServletResponse response) {
		try {
			String regionId = data.getRegionId();
			if (!StringUtil.isNotNull(regionId)) {
				throw new Exception("请选择区域");
			}
				String logisticsStatus = this.omsOrderService.getLogisticsStatus(data.getOrderNo()); 
				//查看当前物流状态
				if(logisticsStatus!=null&&!logisticsStatus.equals("0")){
					throw new Exception("该订单仓库已受理，不能修改地址");
				}
				this.omsOrderService.updateOrderAddrInfo(data);
				getOutputMsg().put("STATUS", ServiceResultCode.SUCCESS);
				getOutputMsg().put("MSG", "操作成功");
		} catch (ServiceException e) {
			logger.error("OrderController-ajax_save-ServiceException", e);
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			getOutputMsg().put("MSG", e.getMessage());
			throw e;
		} catch (Exception e) {
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			getOutputMsg().put("MSG", e.getMessage());
			logger.error("OrderController-ajax_save-Exception", e);
			//throw new ServiceException(e);
		}
		outPrint(response, this.getJsonByObject(this.getOutputMsg()));
	}
	

	//重新分配合伙人
	@RequestMapping(value = "ajax_savePartner")
	public void ajax_savePartner(OmsOrder data, HttpServletResponse response) {
		try {
			omsOrderService.updateOrderPartnerInfo(data);
			getOutputMsg().put("STATUS", ServiceResultCode.SUCCESS);
			getOutputMsg().put("MSG", "操作成功");
		} catch (ServiceException e) {
			logger.error("OrderController-ajax_savePartner-ServiceException", e);
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			getOutputMsg().put("MSG", e.getMessage());
			throw e;
		} catch (Exception e) {
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			getOutputMsg().put("MSG", e.getMessage());
			logger.error("OrderController-ajax_savePartner-Exception", e);
			//throw new ServiceException(e);
		}
		outPrint(response, this.getJsonByObject(this.getOutputMsg()));
	}

	@RequestMapping("partner_dataPicker")
	public String partner_dataPicker(ModelMap map) {

		return "order/partner_dataPicker";
	}
	@RequestMapping("aftersaleapply")
	public String aftersaleapply(ModelMap map) {
		String orderId =  getString("orderId");
		OmsOrder data = null;
		if(StringUtil.isNotNull(orderId)){
			data = omsOrderService.findById(orderId);
		}else{
			String detailId = getString("detailId");
			OmsOrderdetail omsOrderdetail = this.omsOrderdetailService.findById(detailId);
			data = omsOrderService.findById(omsOrderdetail.getOrderId());
			map.put("omsOrderdetail", omsOrderdetail);
			/**
			 * 如果是主商品要查出来赠品的信息
			 */
			if(null!=omsOrderdetail && OrderDetailStatusConstant.BUYGIFTS_MAIN.equals(omsOrderdetail.getBuyGifts()) ){
				List<OmsOrderdetail> orderdetails=this.omsOrderdetailService.getdetailByIDList(omsOrderdetail.getId());
				map.put("orderdetails", orderdetails);
			}
			
		}
		map.put("data", data);
		return "order/order_aftersaleapply";
	}

	/**
	 * 给予合伙人模块地址选择框
	 * 
	 * @return
	 */
	@RequestMapping(value = "addressListSelect")
	public String addressListSelect(String id, String searchType, ModelMap map) {

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String treeData = "[]";

			// 根据设置的查询的行政级别过滤地址显示树
			ResultDto dicDto = dictApiService.getDictByType((Constant.PARTNER_ADDRESS_TYPE));
			List<Dict> dictList = (List<Dict>) dicDto.getData();
			String selectAddressType = null;
			if (null != dictList && dictList.size() > 0) {
				Dict Dict = dictList.get(0);
				selectAddressType = Dict.getValue();
			}
			params.put("selectAddressType", selectAddressType);
			List<Object> listResult = getSelectAddressList(params);
			// 左侧菜单树
			treeData = this.getJsonByObject(listResult);
			map.put("result", treeData);
			// 树形Table
			// 筛选过滤条件-编码
			String addressCode = this.getParaMap().get("addressCode") != null ? this.getParaMap().get("addressCode").toString() : "";
			if (StringUtil.isNotNull(addressCode)) {
				params.put("addressCode", addressCode);
			}
			// 筛选过滤条件-名称
			String name = this.getParaMap().get("name") != null ? this.getParaMap().get("name").toString() : "";
			if (StringUtil.isNotNull(name)) {
				params.put("name", name);
			}
			if(StringUtil.isNotNull(id)){
				params.put("parentIdsAndOrId", id); 
 			}
			// 查询来源类型，是否通过查询按钮还是菜单点击
			//params.put("searchType", searchType);
			ResultDto addrTableDto = this.addressApiService.getAddressTable(params, id);
			List<Object> treeTable = (List<Object>) addrTableDto.getData();
			map.put("params", this.getParaMap());
			map.put("treeTable", treeTable);
		} catch (ServiceException e) {
			logger.error("OmsOrderController-addressListSelect-ServiceException=》地址管理-地址选择框-ServiceException", e);
			throw e;
		} catch (Exception e) {
			logger.error("OmsOrderController-addressListSelect-Exception=》地址管理-地址选择框-Exception", e);
			throw new ServiceException(e);
		}
		return "order/address_list_select";
	}

	public List<Object> getSelectAddressList(Map<String, Object> params) throws ServiceException {
		if (!StringUtil.isNotNull(params)) {
			params = new HashMap<String, Object>();
		}
		params.put(Constant.ORDER_BY_FIELD_PARAMS, Constant.ORDER_BY_FIELD);
		params.put(Constant.ORDER_BY_PARAMS, Constant.ORDER_BY);
		params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
		params.put(Constant.ACT_FLAG, Constant.ACT_FLAG_YES);
		List<Address> addressList = (List<Address>) this.addressApiService.getAddressList(params).getData();
		List<Object> listResult = new ArrayList<Object>();
		addressToMap(addressList, listResult);
		return listResult;
	}

	public void addressToMap(List<Address> addressList, List<Object> listResult) throws ServiceException {
		for (Address area : addressList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", area.getId());
			row.put("name", area.getName());
			row.put("pId", area.getParentId());
			row.put("open", true);
			row.put("addressCode", area.getAddressCode());
			row.put("preAddress", area.getPreAddress());
			row.put("actFlag", area.getActFlag());
			row.put("type", area.getType());
			row.put("partner", area.getPartner());
			row.put("remarks", area.getRemarks());
			listResult.add(row);
			if (null != area.getSub() && area.getSub().size() > 0) {
				addressToMap(area.getSub(), listResult);
			}
		}
	}

	/**
	 * 合伙人选择框
	 * 
	 * @return
	 */
	@RequestMapping(value = "partnerlistSelect")
	public String partnerlistSelect(Partner partner, ModelMap map, String addressId) {
		logger.debug("OmsOrderController-list=》合伙人管理-合伙人列表 ");
		Page pageObj = this.getPageObj();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if (StringUtil.isNotNull(partner.getPartnerCode())) {
				params.put("partnerCode", partner.getPartnerCode());
			}
			if (StringUtil.isNotNull(partner.getName())) {
				params.put("nameLike", partner.getName());
			}
			ResultDto reultDto = partnerApiService.findByPage2(pageObj, params);
			pageObj = (Page)reultDto.getData();
			List<Partner> list = (List<Partner>)pageObj.getRecords();
			map.put("partnerList", list);
			map.put("addressId", addressId);
			map.put("pageObj", pageObj);
			
		} catch (ServiceException e) {
			logger.error("OmsOrderController-partnerlistSelect-ServiceException=》合伙人管理-列表-ServiceException", e);
			throw e;
		} catch (Exception e) {
			logger.error("OmsOrderController-partnerlistSelect-Exception=》合伙人管理-列表--Exception", e);
			throw new ServiceException(e);
		}
		return "order/partner_list_slect";
	}

	@RequestMapping(value = "getOmsOrderWms")
	public void getOmsOrderStatus(HttpServletResponse response, String orderNo) {
		try {
			// 物流信息跟踪
			Map<String, Object> params = new HashMap<>();
			params.put("orderNo", orderNo);
			params.put("recordType", OrderDetailStatusConstant.YES);
			List<OmsOrderRecord> omsOrderRecordList = this.omsOrderRecordService.findByPage(null, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY, params);
			this.responseWrite(response, this.getJsonByObject(omsOrderRecordList));
		} catch (Exception e) {
			logger.error("OmsOrderController-getOmsOrderStatus Exception=》售后状态-", e);
			throw new ServiceException(e);
		}
	}
	@RequestMapping(value="repush2wms")
	public void order_repush2wms(HttpServletResponse response){
		String  orderNoStr =  getString("orderNos");
		logger.error("OrderController-order_repush2wms-orderNoStr"+orderNoStr);
		String [] orderNoArr = orderNoStr.split(";");
		logger.error("orderNoArr:"+orderNoStr+"条");
		try {
		for (String orderNo : orderNoArr) {
			logger.info("OrderController-order_repush2wms-orderNo"+orderNo);
			this.omsOrderService.pushOrder2Wms(orderNo);
		}
		getOutputMsg().put("STATUS", ServiceResultCode.SUCCESS);
		getOutputMsg().put("MSG", "操作成功");
		}catch (Exception e) {
		getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
		getOutputMsg().put("MSG", e.getMessage());
		logger.error("OrderController-order_repush2wms-Exception", e);
		}
		}
}

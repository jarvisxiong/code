/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年3月23日 下午3:27:16
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package com.ffzx.order.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.basedata.api.dto.Partner;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil.ExportModel;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.order.model.PriceSettlement;
import com.ffzx.order.model.PriceSettlementDetail;
import com.ffzx.order.model.PriceSettlementQuery;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.order.service.PriceSettlementDetailService;
import com.ffzx.order.service.PriceSettlementService;
import com.ffzx.order.utils.OmsConstant;
import com.ffzx.order.utils.OrderHelper;

import net.sf.json.JSONArray;

/*** * 订单结算
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年3月23日 下午3:27:16
 * @version V1.0
 */
@Controller
@RequestMapping("/pricesettlement")
public class PriceSettlementController extends BaseController {
	// 记录日志
		protected final Logger logger = LoggerFactory.getLogger(PriceSettlementController.class);
		@Resource
		private RedisUtil redisUtil;
		@Autowired
		private PriceSettlementService priceSettlementService;
		@Autowired
		private PriceSettlementDetailService priceSettlementDetailService;
		@Autowired
		private OmsOrderService omsOrderService;
		@RequestMapping(value = "list")
		public String PricesettlementList(ModelMap map) {
			return "pricesettlement/pricesettlement_list";
		}
		
		@RequestMapping(value = "listData")
		@ResponseBody
		public ResultVo listData(PriceSettlementQuery priceSettlementQuery,ModelMap map,HttpServletResponse response){
			ResultVo resultVo = new ResultVo();
			Page pageObj = this.getPageObj();
			List<PriceSettlement> dataList = priceSettlementService.findByPage(pageObj, "create_date", Constant.ORDER_BY, getQueryParms(priceSettlementQuery));
 			resultVo.setRecordsTotal(pageObj.getTotalCount());
		    resultVo.setInfoData(dataList);
		    resultVo.setStatus("success");
		    map.put("pageObj", pageObj);
			return resultVo;
		}
		
		private Map<String, Object> getQueryParms(PriceSettlementQuery priceSettlementQuery){
			Map<String, Object> params = new HashMap<String, Object>();
			if(StringUtil.isNotNull(priceSettlementQuery.getPsNoLike())){
				params.put("psNoLike",priceSettlementQuery.getPsNoLike());
			}
			if(StringUtil.isNotNull(priceSettlementQuery.getStatus())){
				params.put("status",priceSettlementQuery.getStatus());
			}
			if(StringUtil.isNotNull(priceSettlementQuery.getPartnerCodeLike())){
				params.put("partnerCodeLike",priceSettlementQuery.getPartnerCodeLike());
			}
			if(StringUtil.isNotNull(priceSettlementQuery.getPartnerNameLike())){
				params.put("partnerNameLike",priceSettlementQuery.getPartnerNameLike());
			}
			if(StringUtil.isNotNull(priceSettlementQuery.getServiceStationNameLike())){
				params.put("serviceStationNameLike",priceSettlementQuery.getServiceStationNameLike());
			}
			if(StringUtil.isNotNull(priceSettlementQuery.getCreateDateStart())){
				params.put("createDateStart",priceSettlementQuery.getCreateDateStart());
			}
			if(StringUtil.isNotNull(priceSettlementQuery.getCreateDateEnd())){
				params.put("createDateEnd",priceSettlementQuery.getCreateDateEnd());
			}
			if(StringUtil.isNotNull(priceSettlementQuery.getCutOffTimeStart())){
				params.put("cutOffTimeStart",priceSettlementQuery.getCutOffTimeStart());
			}
			if(StringUtil.isNotNull(priceSettlementQuery.getCutOffTimeEnd())){
				params.put("cutOffTimeEnd",priceSettlementQuery.getCutOffTimeEnd());
			}
			return params;
		}
		
		@RequestMapping(value = "print")
		// @Permission(privilege = {"print"})
		public void print(@RequestParam("psNo")String psNo, HttpServletRequest request, HttpServletResponse response) {
			List<PriceSettlement> riceSettlementList = null;
			Map<String,Object> map=new HashMap<>();
			Map<String, Object> params = new HashMap<>();
			params.put("delFlag", Constant.DELTE_FLAG_NO);
			params.put("psNo", psNo);
			riceSettlementList = priceSettlementService.selectUninoDeatail(params);
			PriceSettlement priceSettlement = riceSettlementList.get(0);
			priceSettlement.setPrintDate(new Date());
			//拉取合伙人电话
			Partner partner = omsOrderService.getPartnerById(priceSettlement.getPartnerId());
			if(null!=partner){
				priceSettlement.setPartnerPhone(partner.getMobilePhone());
			}
			map.put("priceSettlement", priceSettlement);
			String priceSettlementJson=JSONArray.fromObject(priceSettlement).toString();
			logger.info("param======================:"+priceSettlementJson);
			response.setContentType("application/pdf");
			try {
				OrderHelper.jasperExportPdf(response.getOutputStream(), map);
			} catch (Exception e) {
				//IGNORE 
				e.printStackTrace();
			}
		}
		
		@RequestMapping(value = "batchPrint")
		// @Permission(privilege = {"print"})
		public void batchPrint(@RequestParam("psNos")String[] psNos, HttpServletRequest request, HttpServletResponse response) {
			
			List<PriceSettlement> riceSettlementList = null;
			Map<String,Object> map=new HashMap<>();
			Map<String, Object> params = new HashMap<>();
			params.put("delFlag", Constant.DELTE_FLAG_NO);
			params.put("psNoList", Arrays.asList(psNos));
			riceSettlementList = priceSettlementService.selectUninoDeatail(params);
			if(riceSettlementList!=null && riceSettlementList.size()>0){
				map.put("priceSettlements", riceSettlementList);
				response.setContentType("application/pdf");
				try {
					OrderHelper.batchExportPdf(response.getOutputStream(), map);
				} catch (Exception e) {
					//IGNORE 
					e.printStackTrace();
				}
				
			}
			
		}
		
		
		/**
		 * 导出
		 * 
		 * @param request
		 * @param response
		 */
		@RequestMapping(value = "priceSettlementExport")
		public void orderExport(PriceSettlementQuery priceSettlementQuery,HttpServletRequest request, HttpServletResponse response) {
			StopWatch sw_orderExport = new StopWatch("orderExport");
			String lockKey = "EXPORT_LOCKKEY";
			final String[] headers = new String[] { "结算编号","销售订单号" ,"商品名称" ,"数量" ,"单位" ,"销售价" ,"总金额" ,"批发价" 
					,"总差价" ,"购买人" ,"关联服务站","关联合伙人","关联合伙人ID","创建时间","状态","结算时间" };
			
			final String[] properties = new String[] { "psNo", "orderNo", "commodityTitle", "buyNum", "commodityUnit",
					 "actSalePrice", "saleAmount", "pifaPrice", "balance", "memberName", "serviceStationName",
					"partnerName", "partnerCode", "createDate","status","cutOffTime"};
					 
			List<PriceSettlement> dataList = null;
			final List<Map<String, String>> listMap = new ArrayList<>();
			
			Map<String, Object> params = getQueryParms(priceSettlementQuery);
			try {
				if(!redisUtil.exists(lockKey)){
				redisUtil.set(lockKey, "1", OmsConstant.REDIS_EXPIRETIME_15MIN);
				final String fileName = "结算单导出";
				//params.put("orderBy", "t.create_date desc,t.order_no desc");
				params.put("delFlag", Constant.DELTE_FLAG_NO);
				dataList = priceSettlementService.selectUninoDeatail(params);
				if(null!=dataList&&dataList.size()>0){
				logger.info("结算数量：size【"+dataList.size()+"】条");
				for (PriceSettlement data : dataList) {
					if (data.getDetailList().size() > 0)
						for (PriceSettlementDetail detail : data.getDetailList()) {
							Map<String, String> map = new HashMap<String, String>();
							map.put("psNo",detail.getPsNo());
							map.put("orderNo", detail.getOrderNo());
							map.put("commodityTitle", detail.getCommodityTitle()==null?"":detail.getCommodityTitle());
							map.put("buyNum", detail.getBuyNum()==null?"":detail.getBuyNum().toString());
							map.put("commodityUnit", detail.getCommodityUnit()==null?"":detail.getCommodityUnit());
							map.put("actSalePrice", detail.getActSalePrice()==null?"":detail.getActSalePrice().toString());
							map.put("saleAmount", detail.getSaleAmount()==null?"":detail.getSaleAmount().toString());
							map.put("pifaPrice", detail.getPifaPrice()==null?"":detail.getPifaPrice().toString());
							map.put("balance", detail.getBalance()==null?"":detail.getBalance().toString());
							map.put("memberName", detail.getMemberName()==null?"":detail.getMemberName());
							map.put("serviceStationName", detail.getServiceStationName()==null?"":detail.getServiceStationName());
							map.put("partnerName",detail.getPartnerName()==null?"":detail.getPartnerName());
							map.put("partnerCode", detail.getPartnerCode()==null?"":detail.getPartnerCode());
							map.put("createDate", detail.getCreateDate()==null?"":DateUtil.formatDateTime(detail.getCreateDate()));
							map.put("status", data.getStatusName()==null?"":data.getStatusName());
							map.put("cutOffTime", data.getCutOffTime()==null?"":DateUtil.formatDateTime(data.getCutOffTime()));
							
							listMap.add(map);
						}
				}
				logger.info("导出明细数量：size【"+listMap.size()+"】条");
				
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
				}}
			} catch (IOException e) {
				logger.error("导出-Exception", e);
				throw new ServiceException(e);
			} catch (Exception e) {
				logger.error("导出-Exception", e);
				throw new ServiceException(e);
			} finally{
				redisUtil.remove(lockKey);
			}
		}
		
		@RequestMapping("statusOpt")
	    @ResponseBody
		public ResultVo statusOpt(String ids,String status){
	        ResultVo resultVo = new ResultVo();
	        Date now = new Date();
	        String[] idsArry = ids.split(";");
	        List<PriceSettlement> priceSettlementList = new ArrayList<>();
	        try {
	        	 for (String id : idsArry) {
	 	        	PriceSettlement obj = new PriceSettlement();
	 	        	obj.setId(id);
	 	        	obj.setStatus(status);
	 	        	 if(obj.getStatus().equals("2")){//结算操作
	 	 	        	obj.setCutOffTime(now);
	 	 	        }
	 	        	 obj.setLastUpdateDate(now);
	 	        	 obj.setLastUpdateBy(RedisWebUtils.getLoginUser());
	 	        	 obj.setRemarks("操作人：【"+RedisWebUtils.getLoginUser().getId()+"】,status:【"+obj.getStatus()+"】");
	 	 	        priceSettlementList.add(obj);
	 			}
	        for (PriceSettlement priceSettlement : priceSettlementList) {
	        	priceSettlementService.modifyById(priceSettlement);	      
			}
	        resultVo.setStatus("success");
            resultVo.setInfoStr("操作成功！");
	   
	        }catch(ServiceException se){
	        	logger.error("",se);
	        	resultVo.setStatus("error");
	            resultVo.setInfoStr(se.getMessage());
	        }
	        return resultVo;
	   	    }
}

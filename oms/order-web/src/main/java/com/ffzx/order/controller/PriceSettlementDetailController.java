/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年3月23日 下午3:27:16
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package com.ffzx.order.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.order.model.PriceSettlement;
import com.ffzx.order.model.PriceSettlementDeatilQuery;
import com.ffzx.order.model.PriceSettlementDetail;
import com.ffzx.order.service.PriceSettlementDetailService;
import com.ffzx.order.service.PriceSettlementService;

/***
 * 订单明细结算
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年3月23日 下午3:27:16
 * @version V1.0
 */
@Controller
@RequestMapping("/pricesettlementdetail")
public class PriceSettlementDetailController extends BaseController {
	// 记录日志
		protected final Logger logger = LoggerFactory.getLogger(PriceSettlementDetailController.class);
		@Resource
		private RedisUtil redisUtil;
		@Autowired
		private PriceSettlementDetailService priceSettlementDetailService;
		@Autowired PriceSettlementService priceSettlementService;
		@RequestMapping(value = "list")
		public String PricesettlementList(String psNo,ModelMap map) {
			map.put("psNo", psNo);
			Map<String,Object> params = new HashMap<>();
 			params.put("psNo", psNo);
 			List<PriceSettlement> priceSettlementLiist= priceSettlementService.findByBiz(params);
			map.put("pricesettlement", priceSettlementLiist==null?null:priceSettlementLiist.get(0));
			map.put("viewState", getString("viewState"));
			return "pricesettlement/pricesettlementdetail_list";
		}
		
		@RequestMapping(value = "listData")
		@ResponseBody
		public ResultVo listData(PriceSettlementDeatilQuery priceSettlementDeatilQuery,ModelMap map,HttpServletResponse response){
			ResultVo resultVo = new ResultVo();
			Page pageObj = this.getPageObj();
			List<PriceSettlementDetail> dataList = priceSettlementDetailService.findByPage(pageObj, "create_date", Constant.ORDER_BY, getQueryParms(priceSettlementDeatilQuery));
 			resultVo.setRecordsTotal(pageObj.getTotalCount());
 			Map<String,Object> params = new HashMap<>();
 			params.put("psNo", priceSettlementDeatilQuery.getPsNo());
 			List<PriceSettlement> priceSettlementLiist= priceSettlementService.findByBiz(params);
 			if(null!=priceSettlementLiist&&priceSettlementLiist.size()>0){
 				PriceSettlement priceSettlement = priceSettlementLiist.get(0);
 				for (PriceSettlementDetail priceSettlementDetail : dataList) {
 					priceSettlementDetail.setPriceSettlement(priceSettlement);
				}
 			}
		    resultVo.setInfoData(dataList);
		    resultVo.setStatus("success");
		    map.put("pageObj", pageObj);
			return resultVo;
		}
		
		private Map<String, Object> getQueryParms(PriceSettlementDeatilQuery priceSettlementDetailQuery){
			Map<String, Object> params = new HashMap<String, Object>();
			if(StringUtil.isNotNull(priceSettlementDetailQuery.getPsNo())){
				params.put("psNo",priceSettlementDetailQuery.getPsNo());
			}
			if(StringUtil.isNotNull(priceSettlementDetailQuery.getPartnerCodeLike())){
				params.put("partnerCodeLike",priceSettlementDetailQuery.getPartnerCodeLike());
			}
			if(StringUtil.isNotNull(priceSettlementDetailQuery.getPartnerNameLike())){
				params.put("partnerNameLike",priceSettlementDetailQuery.getPartnerNameLike());
			}
			if(StringUtil.isNotNull(priceSettlementDetailQuery.getServiceStationNameLike())){
				params.put("serviceStationNameLike",priceSettlementDetailQuery.getServiceStationNameLike());
			}
			if(StringUtil.isNotNull(priceSettlementDetailQuery.getCreateDateStart())){
				params.put("createDateStart",priceSettlementDetailQuery.getCreateDateStart());
			}
			if(StringUtil.isNotNull(priceSettlementDetailQuery.getCreateDateEnd())){
				params.put("createDateEnd",priceSettlementDetailQuery.getCreateDateEnd());
			}
			if(StringUtil.isNotNull(priceSettlementDetailQuery.getCutOffTimeStart())){
				params.put("cutOffTimeStart",priceSettlementDetailQuery.getCutOffTimeStart());
			}
			if(StringUtil.isNotNull(priceSettlementDetailQuery.getCutOffTimeEnd())){
				params.put("cutOffTimeEnd",priceSettlementDetailQuery.getCutOffTimeEnd());
			}
			return params;
		}
		
		
		@RequestMapping("delete")
	    @ResponseBody
		public ResultVo statusOpt(String id){
	        ResultVo resultVo = new ResultVo();
	        try {
	        	priceSettlementDetailService.deleteDetail(id);
	        resultVo.setStatus("success");
            resultVo.setInfoStr("操作成功！");
	        }catch(ServiceException se){
	        	resultVo.setStatus("error");
	            resultVo.setInfoStr(se.getMessage());
	        }
	        return resultVo;
	   	    }
}

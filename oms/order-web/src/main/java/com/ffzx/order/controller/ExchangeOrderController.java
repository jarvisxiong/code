package com.ffzx.order.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ffzx.basedata.api.dto.SubSystemConfig;
import com.ffzx.basedata.api.service.SubSystemConfigApiService;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.enums.OrderTypeEnum;
import com.ffzx.order.constant.OrderConstant;
import com.ffzx.order.service.OmsOrderService;


/**
 * 换货订单
 * @className ChangeOrderController.java
 * @author sheng.shan
 * @date 
 * @version 1.0
 */
@Controller
@RequestMapping("/exchangeorder/*")
public class ExchangeOrderController extends BaseController {
	// 日志
	protected final   Logger logger = LoggerFactory.getLogger(ExchangeOrderController.class);
	
	@Autowired
	private OmsOrderService omsOrderService;
	@Autowired
	private SubSystemConfigApiService subSystemConfigApiService;
	
	
	
	@RequestMapping(value = "exchangeOrderList")
	public String  exchangeOrderList(ModelMap map,String tblshow) {
		Page pageObj = this.getPageObj();
		Map<String,Object> params =  this.getParaMap();
		// 取值
		String orderNo = this.getString("orderNo_like"); // 订单编号
		String phone = this.getString("phone_like"); // 手机号
		String startTime = this.getString("payTimeStart"); // 开始时间
		String endTime = this.getString("payTimeEnd"); // 结束时间
		
		List<OmsOrder> orderList = null;
		SubSystemConfig ucSystem = null;
		SubSystemConfig orderSystem = null;
		List<SubSystemConfig> systemConfigList = null;
		try {
			Date payTimeStart = null;
			Date payTimeEnd = null;
			if (StringUtil.isNotNull(startTime)) {
				payTimeStart = DateUtil.parse(startTime, DateUtil.FORMAT_DATETIME); // 付款开始时间 
			}
			if(StringUtil.isNotNull(endTime)){
				payTimeEnd = DateUtil.parse(endTime, DateUtil.FORMAT_DATETIME); // 付款结束时间
			}
			// 过滤条件
			params.put("orderNo_like", orderNo);
			params.put("phone_like",phone);
			params.put("payTimeStart",payTimeStart);
			params.put("payTimeEnd",payTimeEnd);
			params.put("orderType", OrderTypeEnum.EXCHANGE_ORDER.getValue());
			
			orderList = omsOrderService.searchExchangeOrderList(pageObj, params);
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
			logger.error("OrderController.exchangeOrderList ServiceException=》订单管理-列表", e);
			throw e;
		} catch (Exception e) {
			logger.error("OrderController.exchangeOrderList Exception=》订单管理-列表", e);
			throw new ServiceException(e);
		}
		// 页签
		if (tblshow == "" || tblshow == null) {
			map.put("tblshow", "3");
		} else {
			map.put("tblshow", tblshow);
		}
		 map.put("params", params);
		 map.put("pageObj", pageObj);
		 map.put("list",orderList );
		 map.put("image_path", System.getProperty("image.web.server"));
		 map.put("ucDomain", ucSystem.getSubSystemDomain());
		 map.put("ucPort", ucSystem.getSubSystemPort());
		 map.put("orderDomain", orderSystem.getSubSystemDomain());
		 map.put("orderPort", orderSystem.getSubSystemPort());
		return "aftersale/exchange/exchange_order_list";
	}
	
}

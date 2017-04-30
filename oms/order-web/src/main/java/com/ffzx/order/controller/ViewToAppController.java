package com.ffzx.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.order.api.vo.OrderBiVo;
import com.ffzx.order.service.OmsOrderService;

import net.sf.json.JSONArray;

/***
 * 专门处理需要提供给APP端的界面的控制器
 * @author ying.cai
 * @date 2016年6月15日 下午2:15:02
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/appViews")
public class ViewToAppController extends BaseController{
	
	@Autowired
	private OmsOrderService omsOrderService;
	
	/***
	 * 配送App取得销售报表
	 * @pathParam partnerId 合伙人(配送员)ID
	 * @return
	 * @date 2016年6月15日 下午2:17:21
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	@RequestMapping("findSaleOrderBi/{partnerId}")
	public String findSaleOrderBi(@PathVariable()String partnerId,HttpServletResponse response){
		List<OrderBiVo> orderBiVoList = omsOrderService.findSaleOrderBi(partnerId);
		List<Map<String,Object>> items = new ArrayList<>();
		for (OrderBiVo orderBiVo : orderBiVoList) {
			String month = orderBiVo.getMonth();
			if(month.length()>1 && month.startsWith("0")){
				month = month.substring(1, month.length());
			}
			Map<String,Object> item = new HashMap<>();
			item.put("month", Integer.parseInt(month));
			item.put("salesVolumeTotal", StringUtils.isEmpty(orderBiVo.getSalesTotal())?0:Double.parseDouble(orderBiVo.getSalesTotal()));
			item.put("refundTotal", StringUtils.isEmpty(orderBiVo.getRefundTotal())?0:Double.parseDouble(orderBiVo.getRefundTotal()));
			items.add(item);
		}
		String jsonData = JSONArray.fromObject(items).toString();
		getRequest().setAttribute("jsonDatas",jsonData);
		return "appViews/saleOrderBi";
	}
	
}

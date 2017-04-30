/**
 * @Description 
 * @author tangjun
 * @date 2016年8月15日
 * 
 */
package com.ffzx.bi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ff.common.web.json.BaseRspJson;
import com.ffzx.basedata.api.dto.SubSystemConfig;
import com.ffzx.basedata.api.service.SubSystemConfigApiService;
import com.ffzx.bi.controller.base.FFBaseController;
import com.ffzx.commerce.framework.dto.ResultDto;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月15日
 */ 
@Controller
@RequestMapping("/SystemConfig")
public class SystemConfigController extends FFBaseController
{
	@Autowired
	private SubSystemConfigApiService subSystemConfigApiService;
 
	@Override
	public BaseRspJson Load()
	{
		BaseRspJson rsp = new BaseRspJson();
 		ResultDto allResult = subSystemConfigApiService.getSubSystemConfigALl();
 		if(null != allResult){
			List<SubSystemConfig> systemConfigList  = (List<SubSystemConfig>)allResult.getData();
			rsp.setObj(systemConfigList);
 		} 
 		return rsp;
	}
}

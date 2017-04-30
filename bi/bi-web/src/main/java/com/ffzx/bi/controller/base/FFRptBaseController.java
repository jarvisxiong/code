/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 * 
 */
package com.ffzx.bi.controller.base;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ff.common.dao.model.FFCondition;
import com.ff.common.service.model.FFExcelMeta;
import com.ff.common.util.format.JsonConvert;
import com.ff.common.util.meta.ReflectionUtil;
import com.ff.common.util.validate.ValidatorUtil;
import com.ff.common.web.json.BaseReqJson;
import com.ff.common.web.json.BaseRspJson;
import com.ff.common.web.json.PageJson;
import com.ffzx.bi.common.model.RptDataRspJson;
import com.ffzx.bi.common.model.RptDim;
import com.ffzx.bi.common.model.RptDimDataJson;
import com.ffzx.bi.common.model.RptIndicator;
import com.ffzx.bi.common.service.RptBaseService;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 */
public class FFRptBaseController<E> extends FFTableController<E>
{
	@Autowired
	private RptBaseService<E> rptBaseService;
	@RequestMapping("/Load")
	@ResponseBody
	public BaseRspJson Load()
	{
		BaseRspJson rsp =  new BaseRspJson<>();
		
 		PageJson page = this.GetPage();
		List<FFCondition> conditionList = this.GetFilterCondition();

		BaseReqJson<List<RptDimDataJson>> req = JsonConvert.jsonToObject(GetReqJson(), BaseReqJson.class,List.class,RptDimDataJson.class);
		if(null != req)
		{
			RptDataRspJson obj = rptBaseService.loadRpt(this.GetSysUser(), req.getObj(), conditionList);
			rsp.setObj(obj);

		}
 		return rsp;
	}
	
	@RequestMapping("/LoadSum")
	@ResponseBody
	public BaseRspJson<E> LoadSum()
	{
		BaseRspJson<E> rsp =  new BaseRspJson<E>();
		
 		List<FFCondition> conditionList = this.GetFilterCondition();

		BaseReqJson<List<RptIndicator>> req = JsonConvert.jsonToObject(GetReqJson(), BaseReqJson.class,List.class,RptIndicator.class);
		if(null != req)
		{
			E obj = rptBaseService.loadSum(this.GetSysUser(), req.getObj(), conditionList);
			rsp.setObj(obj);
		}
 		return rsp;
	}
	
 
	
	@RequestMapping("/Dim")
	@ResponseBody
	public BaseRspJson<List<RptDim>> Dim()
	{
		BaseRspJson<List<RptDim>> rsp =  new BaseRspJson<List<RptDim>>();
		
 		BaseReqJson<List<String>> req = JsonConvert.jsonToObject(GetReqJson(), BaseReqJson.class,List.class,String.class);
		if(null != req)
		{
			List<RptDim> obj = rptBaseService.getDim(req.getObj());
			rsp.setObj(obj);
		}
 		return rsp;
	}
	
	@RequestMapping("/Indicator")
	@ResponseBody
	public BaseRspJson<List<RptIndicator>> Indicator()
	{
		BaseRspJson<List<RptIndicator>> rsp =  new BaseRspJson<>();
		
 
		BaseReqJson<List<String>> req = JsonConvert.jsonToObject(GetReqJson(), BaseReqJson.class,List.class,String.class);
		if(null != req)
		{
			List<RptIndicator> obj = rptBaseService.getIndicator(req.getObj());
			for(RptIndicator e:obj)
			{
				e.getCalcList().clear();
			}
  			rsp.setObj(obj);
		}
 		return rsp;
	}
	
	/**
	 * just support one dim for export excel  
	 */
	@RequestMapping("/Export")
	@Override
	public void Export(HttpServletRequest request, HttpServletResponse response)
	{
		BaseReqJson<List<RptDimDataJson>> req = JsonConvert.jsonToObject(GetReqJson(), BaseReqJson.class,List.class,RptDimDataJson.class);
		if(null != req)
		{
			
			if(!ValidatorUtil.isEmpty(req.getObj()))
			{
				List<FFExcelMeta> excelMetaList = rptBaseService.getExportMeta(req.getObj());
	 			RptDataRspJson obj = rptBaseService.loadRpt(this.GetSysUser(), req.getObj(), this.GetFilterCondition());
	 			exportWithMeta(response,excelMetaList,(List<E>) obj.getTable_data().get(req.getObj().get(0).getDim()));
			}
 		}		

 	}
	
	@Override
	public void destroy() throws Exception
	{
		// TODO Auto-generated method stub
		super.destroy();
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		if(null != rptBaseService)
		{
			rptBaseService.setEnitiyClass(ReflectionUtil.getSuperClassGenricType(getClass()));	
		}		
		super.afterPropertiesSet();
	}
}

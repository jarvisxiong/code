/**
 * @Description 
 * @author tangjun
 * @date 2016年8月8日
 * 
 */
package com.ffzx.bi.controller.base;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ff.common.constant.FFErrorCode;
import com.ff.common.dao.model.FFCondition;
import com.ff.common.service.BaseService;
import com.ff.common.service.model.FFExcelMeta;
import com.ff.common.util.file.ExcelUtil;
import com.ff.common.util.format.DateUtil;
import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.meta.ReflectionUtil;
import com.ff.common.web.json.BaseReqJson;
import com.ff.common.web.json.BaseRspJson;
import com.ff.common.web.json.PageDataJson;
import com.ff.common.web.json.PageJson;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.utils.ExcelExportUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil.ExportModel;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月8日
 */
public class FFTableController<E> extends FFBaseController implements InitializingBean, DisposableBean
{
	private Logger log = FFLogFactory.getLog(this.getClass());
	@Autowired
	private BaseService<E> baseService;
	
	public FFTableController()
	{

		
	}
 
	public Class GetTableClass()
	{
		// TODO Auto-generated method stub
		return  ReflectionUtil.getSuperClassGenricType(getClass());
	}
	/**
	 * @return the service
	 */
	public BaseService<E> getService()
	{
		return baseService;
	}
 
	/**
	 * @param service the service to set
	 */
	public void setService(BaseService<E> service)
	{
		this.baseService = service;
	}
 
	public BaseRspJson<PageDataJson<E>> Load()
	{
	    BaseRspJson<PageDataJson<E>> rsp = new BaseRspJson<PageDataJson<E>>();
		PageJson page = this.GetPage();
		List<FFCondition> conditionList = this.GetFilterCondition();
		PageDataJson<E> obj = getService().load(GetSysUser(),conditionList, page);
		rsp.setObj(obj);
 		return rsp;
 	}
	
	@RequestMapping("/LoadAll")
	@ResponseBody
	public BaseRspJson<List<E>> LoadAll()
	{
		BaseRspJson<List<E>> rsp = new BaseRspJson<List<E>>();
  		List<FFCondition> conditionList = this.GetFilterCondition();
		List<E> obj = getService().load(GetSysUser(),conditionList);
		rsp.setObj(obj);
  		return rsp;
	}
	
	@RequestMapping("/Create")
	@ResponseBody
	public BaseRspJson<E> Create()
	{
		BaseRspJson<E> rsp = new BaseRspJson<E>();
 
  		E obj = this.getObj(this.GetTableClass());
		try
		{
			getService().create(obj);
			rsp.setObj(obj);
		}
		catch(Exception e)
		{
			log.error("create failed",e);
			rsp.setErrorCode(FFErrorCode.FAIL);
		}
  		return rsp;
	}
	
	
	@RequestMapping("/Export")
	public void Export(HttpServletRequest request, HttpServletResponse response)
	{
 		
		List<FFExcelMeta> excelMetaList = ExcelUtil.getExcelMeta(this.GetTableClass()); 
		final List<E> dataList = getService().load(this.GetSysUser(), this.GetFilterCondition());

		exportWithMeta(response,excelMetaList,dataList);

 	}

	/**
	 * 	 
	 * 
	 * @param response
	 * @param excelMetaList
	 */
	protected void exportWithMeta(HttpServletResponse response, List<FFExcelMeta> excelMetaList,final List<E> dataList)
	{
		File file = ExcelUtil.export(excelMetaList, dataList);
		String fileName = DateUtil.DateToString(DateUtil.getCurrentDate(),DateUtil.yyyyMMddHHmmss);
		ExcelExportUtil.down(response, file, fileName);
 
	}


	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		if(null != getService())
		{
			getService().setEnitiyClass(this.GetTableClass());	
		}		
	}
}

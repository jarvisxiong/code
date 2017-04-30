/**
 * @Description 
 * @author tangjun
 * @date 2016年8月4日
 * 
 */
package com.ffzx.bi.controller.base;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ff.common.dao.model.FFCondition;
import com.ff.common.util.format.JsonConvert;
import com.ff.common.util.log.FFLogFactory;
import com.ff.common.web.json.BaseReqJson;
import com.ff.common.web.json.BaseRspJson;
import com.ff.common.web.json.FFSysUser;
import com.ff.common.web.json.PageJson;
import com.ffzx.commerce.framework.controller.BaseController;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月4日
 */
public class FFBaseController extends BaseController
{
	private Logger log = FFLogFactory.getLog(this.getClass());
 
	
	@ModelAttribute
	public void init()
	{
		String reqJson = getString("para");
		log.info("the json is " + reqJson);
	}
	
	@RequestMapping("/Load")
	@ResponseBody
	public BaseRspJson Load()
	{
	    BaseRspJson rsp = new BaseRspJson();
  		return rsp;
 	}
	
 	@RequestMapping("/Index")
	public String Index(ModelMap map){
		
 		RequestMapping mappingClass = this.getClass().getAnnotation(RequestMapping.class);
 		String action = getUrlFromAnno(mappingClass);
 		
 		if(action.startsWith("/"))
 		{
 			action = action.substring(1, action.length());
 		}
 		map.put("action", action);
		return ReturnPage();
	}
 	
 	
 	private String getUrlFromAnno(RequestMapping mapping)
 	{
 		String url = "";
 		if(null != mapping)
 		{
 			String[] classMap = mapping.value();
 	 		if(null != classMap && classMap.length > 0)
 	 		{
 	 			url = classMap[0];
 	 		}
 		}
 		return url;
 	}
 	protected String  ReturnPage()
 	{
  		RequestMapping mappingClass = this.getClass().getAnnotation(RequestMapping.class);
 		String url = getUrlFromAnno(mappingClass);
 		
 		if(url.startsWith("/"))
 		{
 			url = url.substring(1, url.length());
 		}
 		
        try
		{
        	
     		StackTraceElement[] stack = new Throwable().getStackTrace();

     		Method method = null;
        	for(Method e :this.getClass().getMethods())
        	{
        		if(e.getName().equals(stack[1].getMethodName()))
        		{
        			method = e;
        			break;
        		}
        	}
 
			RequestMapping mapping = method.getAnnotation(RequestMapping.class);
			
			url = url + this.getUrlFromAnno(mapping);
			
		} catch (Exception e)
		{
			log.error("can not get method",e);
		}  
        return url;
 	}
 	


	public List<FFCondition> GetFilterCondition()
	{
		BaseReqJson req = JsonConvert.jsonToObject(GetReqJson(), BaseReqJson.class);
		List<FFCondition> conditionList = new ArrayList<FFCondition>();
		
		//处理时间的开闭区间的默认值
 
		if(null != req && null != req.getCondition())
		{
			
			conditionList = req.getCondition();
			
		}
 
		return conditionList;
	}
	
	public <T> T getObj(Class<?>... clazz)
	{
		BaseReqJson req = JsonConvert.jsonToObject(GetReqJson(), BaseReqJson.class,clazz);
		if(null != req)
		{
			return (T) req.getObj();
		}
		return null;
	}
	
	
	protected FFSysUser GetSysUser()
	{
		FFSysUser user = new FFSysUser();
		return user;
	}
	
	 
	
	protected String GetReqJson()
	{
		 
		String reqJson = getString("para");
  	 
		return reqJson;
	}
	
	protected PageJson GetPage()
	{
		PageJson page = new PageJson();
		return page;
	}
	
 

}

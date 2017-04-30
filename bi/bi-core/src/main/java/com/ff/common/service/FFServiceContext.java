/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 * 
 */
package com.ff.common.service;

import com.ff.common.util.spring.SpringContextUtil;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 */
public class FFServiceContext
{
	private static FFServiceContext instance;
	 
	 

 	private FFServiceContext()
	{

	}

	public static synchronized FFServiceContext getInstance()
	{
		if (null == instance)
		{
			instance = new FFServiceContext();
		}
		return instance;
	}
	public <T> BaseService<T> getService(Class<T> clazz)
	{
		BaseService<T> service = (BaseService<T>) SpringContextUtil.getBean("baseService");
 		service.setEnitiyClass(clazz);
 		return service;
	}
	
}

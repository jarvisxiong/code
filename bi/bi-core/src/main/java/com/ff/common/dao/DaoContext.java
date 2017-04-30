/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 * 
 */
package com.ff.common.dao;

import java.lang.reflect.Proxy;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import com.ff.common.dao.mybatis.FFMapperProxy;
import com.ff.common.util.spring.SpringContextUtil;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 */
public class DaoContext
{
	private static DaoContext instance;
	 
 

 	private DaoContext()
	{

	}

	public static synchronized DaoContext getInstance()
	{
		if (null == instance)
		{
			instance = new DaoContext();
		}
		return instance;
	}
	
	public <T> Dao<T> getDao(Class<T> clazz)
	{
		Dao<T> dao = (Dao<T>) SpringContextUtil.getBean("dao");
		
		initClass(dao,clazz);
		
		return dao;
	}
	
	private void initClass(Dao<?> dao,Class<?> clazz)
	{
		if(null != dao)
		{
	 		Proxy proxy = (Proxy) dao;
	 		MetaObject mo = SystemMetaObject.forObject(proxy);
	 		Object val = mo.getValue("h");
	 		if(null != val && val.getClass() == FFMapperProxy.class)
	 		{
				mo.setValue("h.entityClass", clazz);
	 		}
		}

	}
	
}

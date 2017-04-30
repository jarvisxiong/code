package com.ff.common.dao.datasource;

import org.slf4j.Logger;

import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.spring.SpringContextUtil;

/**
 * 
 * @Description 
 * @author tangjun
 * @date 2016年7月5日
 */
public class FFSourceFactory   
{
	private final static Logger log = FFLogFactory.getLog(FFSourceFactory.class);

 

	private static FFSourceFactory instance;
	
 	private FFSourceFactory()
	{
	}
 	
	public static synchronized FFSourceFactory getInstance()
	{
		if (null == instance)
		{
			instance = new FFSourceFactory();
		}
		return instance;
	}
	
	public FFDataSource getDataSource(String name)
	{
		return SpringContextUtil.getApplicationContext().getBean(name, FFDataSource.class);
	}
	

}

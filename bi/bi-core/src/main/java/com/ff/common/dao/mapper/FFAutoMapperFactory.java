/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 * 
 */
package com.ff.common.dao.mapper;

import com.ff.common.dao.annotation.FFTable;
import com.ff.common.util.spring.SpringContextUtil;
import com.ff.common.util.validate.ValidatorUtil;

/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 */
public class FFAutoMapperFactory
{
	private static FFAutoMapperFactory instance;
	
 	private FFAutoMapperFactory()
	{

	}
 	
	public static synchronized FFAutoMapperFactory getInstance()
	{
		if (null == instance)
		{
			instance = new FFAutoMapperFactory();
		}
		return instance;
	}
	
	public FFAutoMapper getAutoMpper(Class<?> clazz)
	{
		return SpringContextUtil.getApplicationContext().getBean(getMapperName(clazz), FFAutoMapper.class);
		//return new FFAutoMapperHumpImpl();
	}
	
	public String getMapperName(Class<?> clazz)
	{
		FFTable table = clazz.getAnnotation(FFTable.class);
		String mapper = "";
		if(null == table || ValidatorUtil.isEmpty(table.mapper()))
		{
			mapper = FFAutoMapper.SameMapper;
		}
		else
		{
			mapper = table.mapper();
		}
		return mapper;
	}
}

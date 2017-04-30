/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 * 
 */
package com.ff.common.dao.mapper;

import java.lang.reflect.Field;
import java.util.Date;

import org.slf4j.Logger;

import com.ff.common.dao.annotation.FFColumn;
import com.ff.common.dao.annotation.FFTable;
import com.ff.common.util.format.DataTypeConvert;
import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.validate.ValidatorUtil;

/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 */
public class FFAutoMapperAbstract implements FFAutoMapper
{
	private final static Logger log = FFLogFactory.getLog(FFAutoMapperAbstract.class);

	/* (non-Javadoc)
	 * @see com.ffzx.commerce.framework.dao.FFAutoMapper#getTable(java.lang.Class)
	 */
	@Override
	public String getTableName(Class<?> clazz)
	{
		FFTable table = clazz.getAnnotation(FFTable.class);
		String tableName = "";
		if(null == table || ValidatorUtil.isEmpty(table.name()))
		{
			tableName = entity2Db(clazz.getSimpleName());
		}
		else
		{
			tableName = table.name();
		}
		return tableName;
	}
	
	/* (non-Javadoc)
	 * @see com.ffzx.commerce.framework.dao.FFAutoMapper#isIdFiled(java.lang.reflect.Field)
	 */
	@Override
	public boolean isKey(Field field)
	{
		FFColumn column = field.getAnnotation(FFColumn.class);
		if(null == column)
		{
			return false;
		}
		 
		return column.isKey();
	}

	
	/* (non-Javadoc)
	 * @see com.ffzx.commerce.framework.dao.FFAutoMapper#isExcludle(java.lang.reflect.Field)
	 */
	@Override
	public boolean isExcludle(Field field)
	{
		FFColumn column = field.getAnnotation(FFColumn.class);
		if(null == column)
		{
			return false;
		}
		return column.exclude();
	}
	
	/* (non-Javadoc)
	 * @see com.ffzx.commerce.framework.dao.FFAutoMapper#fieldToJdbcType(java.lang.reflect.Field)
	 */
	@Override
	public String getJdbcType(Field field)
	{
		FFColumn column = field.getAnnotation(FFColumn.class);
		if(null != column &&  !ValidatorUtil.isEmpty(column.jdbcType()))
		{
			return column.jdbcType();
		}
		
		Class<?> fieldClass = field.getType();
		
		String  jdbcType = "VARCHAR";
		if(fieldClass == long.class || fieldClass == Long.class)
		{
			jdbcType = "INTEGER";
		}
		else if(fieldClass == int.class || fieldClass == Integer.class)
		{
			jdbcType = "INTEGER";
		}
		else if(fieldClass == float.class || fieldClass == Float.class)
		{
			jdbcType = "DECIMAL";
		}
		else if(fieldClass == double.class || fieldClass == Double.class)
		{
			jdbcType = "DECIMAL";
		}
		else if(fieldClass == String.class )
		{
			jdbcType = "VARCHAR";
		}
		else if(fieldClass == Date.class)
		{
			jdbcType = "TIMESTAMP";
		}
		else
		{
			log.info("can not support the type " + fieldClass);
		}
		
		return jdbcType;
	}

	/* (non-Javadoc)
	 * @see com.ffzx.commerce.framework.dao.FFAutoMapper#fieldToColumName(java.lang.reflect.Field)
	 */
	@Override
	public String getColumName(Field field)
	{
		FFColumn column = field.getAnnotation(FFColumn.class);
		String columnName = "";
		if(null == column || ValidatorUtil.isEmpty(column.name()))
		{
			columnName = entity2Db(field.getName());
		}
		else
		{
			columnName = column.name();
		}
		
		return columnName;
	}

	/* (non-Javadoc)
	 * @see com.ffzx.dao.mapper.FFAutoMapper#Strategy(java.lang.reflect.Field)
	 */
	@Override
	public String getStrategy(Field field)
	{
		FFColumn column = field.getAnnotation(FFColumn.class);
		String strategy = "";
		if(null != column)
		{
			strategy = column.strategy();	
		}
	 
		return strategy;
	}

	/* (non-Javadoc)
	 * @see com.fuego.common.dao.mapper.FFAutoMapper#entity2Db(java.lang.String)
	 */
	@Override
	public String entity2Db(String name)
	{
		
		return DataTypeConvert.camelToUnderline(name);
	}

	/* (non-Javadoc)
	 * @see com.fuego.common.dao.mapper.FFAutoMapper#entity2Db(java.lang.String)
	 */
	@Override
	public String field2Column(String name)
	{
		
		return DataTypeConvert.camelToUnderline(name);
	}

	@Override
	public String defaultKey()
	{
		// TODO Auto-generated method stub
		return "id";
	}





}

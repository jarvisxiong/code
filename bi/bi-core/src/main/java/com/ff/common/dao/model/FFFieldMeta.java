/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 * 
 */
package com.ff.common.dao.model;

import java.lang.reflect.Field;

/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 */
public class FFFieldMeta
{
	private Field field;
	
	private String columnName;
	private boolean isKey;
	private String strategy;
	private String jdbcType;
	
	private Object value;
	private String filterType;
	
	/**
	 * @return the field
	 */
	public Field getField()
	{
		return field;
	}
	/**
	 * @param field the field to set
	 */
	public void setField(Field field)
	{
		this.field = field;
	}
	/**
	 * @return the columnName
	 */
	public String getColumnName()
	{
		return columnName;
	}
	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}
	/**
	 * @return the isIKey
	 */
 
	/**
	 * @return the jdbcType
	 */
	public String getJdbcType()
	{
		return jdbcType;
	}
	/**
	 * @return the isKey
	 */
	public boolean isKey()
	{
		return isKey;
	}
	/**
	 * @param isKey the isKey to set
	 */
	public void setKey(boolean isKey)
	{
		this.isKey = isKey;
	}
	/**
	 * @param jdbcType the jdbcType to set
	 */
	public void setJdbcType(String jdbcType)
	{
		this.jdbcType = jdbcType;
	}
	/**
	 * @return the strategy
	 */
	public String getStrategy()
	{
		return strategy;
	}
	/**
	 * @param strategy the strategy to set
	 */
	public void setStrategy(String strategy)
	{
		this.strategy = strategy;
	}
	/**
	 * @return the value
	 */
	public Object getValue()
	{
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Object value)
	{
		this.value = value;
	}
	/**
	 * @return the filterType
	 */
	public String getFilterType()
	{
		return filterType;
	}
	/**
	 * @param filterType the filterType to set
	 */
	public void setFilterType(String filterType)
	{
		this.filterType = filterType;
	}
 
	
	
	

}

/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 * 
 */
package com.ff.common.dao.model;

import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 */
public class FFEntityMeta
{
	private Class<?>  clazz;
	private String tableName;
	private String mapper;
	private List<String>  idColumnList;
 	private List<FFFieldMeta> fieldMetaList;
	private Map<String,FFFieldMeta> columnMap;
	private Map<String,FFFieldMeta> fieldMap;
 
	/**
	 * @return the tableName
	 */
	public String getTableName()
	{
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}
 
	/**
	 * @return the idColumnList
	 */
	public List<String> getIdColumnList()
	{
		return idColumnList;
	}
	/**
	 * @param idColumnList the idColumnList to set
	 */
	public void setIdColumnList(List<String> idColumnList)
	{
		this.idColumnList = idColumnList;
	}
	/**
	 * @return the fieldMetaList
	 */
	public List<FFFieldMeta> getFieldMetaList()
	{
		return fieldMetaList;
	}
	/**
	 * @param fieldMetaList the fieldMetaList to set
	 */
	public void setFieldMetaList(List<FFFieldMeta> fieldMetaList)
	{
		this.fieldMetaList = fieldMetaList;
	}
	/**
	 * @return the columnMap
	 */
	public Map<String, FFFieldMeta> getColumnMap()
	{
		return columnMap;
	}
	/**
	 * @param columnMap the columnMap to set
	 */
	public void setColumnMap(Map<String, FFFieldMeta> columnMap)
	{
		this.columnMap = columnMap;
	}
	/**
	 * @return the fieldMap
	 */
	public Map<String, FFFieldMeta> getFieldMap()
	{
		return fieldMap;
	}
	/**
	 * @param fieldMap the fieldMap to set
	 */
	public void setFieldMap(Map<String, FFFieldMeta> fieldMap)
	{
		this.fieldMap = fieldMap;
	}
	/**
	 * @return the clazz
	 */
	public Class<?> getClazz()
	{
		return clazz;
	}
	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(Class<?> clazz)
	{
		this.clazz = clazz;
	}
	/**
	 * @return the mapper
	 */
	public String getMapper()
	{
		return mapper;
	}
	/**
	 * @param mapper the mapper to set
	 */
	public void setMapper(String mapper)
	{
		this.mapper = mapper;
	}
	
	

}

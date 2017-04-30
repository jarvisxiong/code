/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 * 
 */
package com.ff.common.dao.mapper;

import java.lang.reflect.Field;

/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 */
public interface FFAutoMapper
{
	public final static String HumpMapper="humpMapper";
	public final static String SameMapper = "sameMapper";
	
	public String getTableName(Class<?> clazz);
	public boolean isKey(Field field);
	public boolean isExcludle(Field field);
	
	public String defaultKey();
	public String getJdbcType(Field field);
	public String getColumName(Field field);
	public String getStrategy(Field field);
	public String entity2Db(String name);
	public String field2Column(String name);

}

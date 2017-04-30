/**
 * @Description 
 * @author tangjun
 * @date 2016年7月4日
 * 
 */
package com.ff.common.dao.sqlbuilder;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * @Description 
 * @author tangjun
 * @date 2016年7月4日
 */
public interface ISqlBuilder
{
	public static final String dynamicSQL = "dynamicSQL";
	
	public static final String PARA_OBJ = "obj";
 	public static final String PARA_CONIDTION = "condition";
 	public static final String PARA_PAGE = "page";
	public String getSql(Class<?> clazz);
	public void prepare(Class<?> clazz, Object para);
	
	public void handleResult(MappedStatement ms,Class<?> entityClass); 
}

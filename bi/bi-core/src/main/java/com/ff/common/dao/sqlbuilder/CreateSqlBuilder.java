/**
 * @Description 
 * @author tangjun
 * @date 2016年7月4日
 * 
 */
package com.ff.common.dao.sqlbuilder;

import java.util.Collection;

import com.ff.common.dao.datasource.FFDataSource;
import com.ff.common.dao.datasource.FFSourceFactory;
import com.ff.common.dao.model.FFEntityMeta;
import com.ff.common.dao.model.FFFieldMeta;
import com.ff.common.dao.sqlbuilder.base.CreateUpdateSqlBuilder;
import com.ff.common.util.meta.ReflectionUtil;
import com.ff.common.util.validate.ValidatorUtil;

/**
 * @Description 
 * @author tangjun
 * @date 2016年7月4日
 */
public class CreateSqlBuilder extends CreateUpdateSqlBuilder
{


	/* (non-Javadoc)
	 * @see com.ffzx.commerce.framework.dao.sqlbuilder.ISqlBuilder#getSql(java.lang.Class, java.lang.Object)
	 */
	@Override
	public String getSql(Class<?> clazz)
	{

 		FFEntityMeta tableMeta = SqlBuilderFactory.getInstance().getEntityMeta(clazz);
 		
 		String tableName = tableMeta.getTableName();
 		StringBuffer tableSql = new StringBuffer();
		StringBuffer valueSql = new StringBuffer();
		
		tableSql.append("insert into ").append(tableName).append("(");
		

		{
 			valueSql.append("values <foreach collection=\"");
			valueSql.append(INNER_PARA_OBJECT_LIST);
			valueSql.append("\" item=\"obj\" index=\"index\" separator=\",\"> (");
		}
  		
 		for (String columnName : tableMeta.getColumnMap().keySet())
		{
			FFFieldMeta fieldMeta = tableMeta.getColumnMap().get(columnName);
			String fieldName = fieldMeta.getField().getName();
  			tableSql.append(columnName).append(",");
  			valueSql.append("#{");
  			valueSql.append("obj.");
  			valueSql.append(fieldName);
  			valueSql.append(",").append("jdbcType=").append(fieldMeta.getJdbcType().toString()).append("},");
  		}
		
		
		tableSql.delete(tableSql.lastIndexOf(","), tableSql.lastIndexOf(",") + 1);
		tableSql.append(")");
		valueSql.delete(valueSql.lastIndexOf(","), valueSql.lastIndexOf(",") + 1);
		valueSql.append(")");

		{
	    	valueSql.append("</foreach>");
		}

		return tableSql.append(valueSql).toString();
	}
	/* (non-Javadoc)
	 * @see com.ffzx.dao.sqlbuilder.base.BaseSqlBuilder#prepare(java.lang.Class, java.lang.Object)
	 */
	@Override
	public void prepare(Class<?> clazz, Object para)
	{
 
		Collection<?> objList = getObjList(clazz,para);
		generatorForPara(objList);
        this.setPara(para, INNER_PARA_OBJECT_LIST, objList);
	
	}
	

	
 
	private void generatorForPara(Object para)
	{
 		if(para instanceof Collection)
		{
			Collection<?> objList = (Collection<?>)para;
			for(Object obj : objList)
			{
				generatorForEntity(obj);
			}
		}
 		else
 		{
 			generatorForEntity(para);
 		}
	}
	
	private void generatorForEntity(Object para)
	{
 		FFEntityMeta tableMeta = SqlBuilderFactory.getInstance().getEntityMeta(para.getClass());
		for (String fieldName : tableMeta.getFieldMap().keySet())
		{
			FFFieldMeta fieldMeta = tableMeta.getFieldMap().get(fieldName);
			if(!ValidatorUtil.isEmpty(fieldMeta.getStrategy()))
			{
				FFDataSource source = FFSourceFactory.getInstance().getDataSource(fieldMeta.getStrategy());
				Object obj = source.generator();
				
				ReflectionUtil.setObjectField(para, fieldName, obj);
			}

		}
	}
 

}

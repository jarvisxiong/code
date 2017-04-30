/**
 * @Description 
 * @author tangjun
 * @date 2016年7月5日
 * 
 */
package com.ff.common.dao.sqlbuilder;

import java.util.Collection;

import com.ff.common.dao.model.FFEntityMeta;
import com.ff.common.dao.model.FFFieldMeta;
import com.ff.common.dao.sqlbuilder.base.CreateUpdateSqlBuilder;

/**
 * @Description 
 * @author tangjun
 * @date 2016年7月5日
 */
public class UpdateSqlBuilder extends CreateUpdateSqlBuilder
{

	/* (non-Javadoc)
	 * @see com.ffzx.dao.sqlbuilder.ISqlBuilder#getSql(java.lang.Class, java.lang.Object)
	 */
	@Override
	public String getSql(Class<?> clazz)
	{
 
		FFEntityMeta tableMeta = SqlBuilderFactory.getInstance().getEntityMeta(clazz);
 		String tableName = tableMeta.getTableName();
 
		StringBuffer tableSql = new StringBuffer();
 
		tableSql.append(" <foreach collection=\"");
		tableSql.append(INNER_PARA_OBJECT_LIST);
		tableSql.append("\" item=\"obj\" index=\"index\" separator=\";\"> ");
		
		//update one object
		tableSql.append("update ").append(tableName).append(" set ");
		for (String columnName : tableMeta.getColumnMap().keySet())
		{
			FFFieldMeta fieldMeta = tableMeta.getColumnMap().get(columnName);
			String fieldName = fieldMeta.getField().getName();
 

			tableSql.append(columnName).append("=#{obj.").append(fieldName).append(",").append("jdbcType=")
					.append(fieldMeta.getJdbcType().toString()).append("},");
		}
 
		tableSql.delete(tableSql.lastIndexOf(","), tableSql.lastIndexOf(",") + 1);
		tableSql.append(" where ");
		for (String idColumn : tableMeta.getIdColumnList())
		{
			tableSql.append(idColumn);
			FFFieldMeta fieldMeta = tableMeta.getColumnMap().get(idColumn);
			String fieldName = fieldMeta.getField().getName();
 
			tableSql.append("=#{obj.").append(fieldName).append(",").append("jdbcType=")
					.append(fieldMeta.getJdbcType().toString()).append("} and ");
		}
		tableSql.delete(tableSql.lastIndexOf("and"), tableSql.lastIndexOf("and") + 3);
		
		
		tableSql.append("</foreach>");

		
		
		return tableSql.toString();
	}
	
	@Override
	public void prepare(Class<?> clazz, Object para)
	{
 
		Collection<?> objList = getObjList(clazz,para);
        this.setPara(para, INNER_PARA_OBJECT_LIST, objList);
 	}

}

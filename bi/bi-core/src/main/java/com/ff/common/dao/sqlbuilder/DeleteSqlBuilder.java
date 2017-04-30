/**
 * @Description 
 * @author tangjun
 * @date 2016年7月4日
 * 
 */
package com.ff.common.dao.sqlbuilder;

import com.ff.common.dao.model.FFEntityMeta;
import com.ff.common.dao.sqlbuilder.base.BaseConditionSqlBuilder;

/**
 * @Description 
 * @author tangjun
 * @date 2016年7月4日
 */
public class DeleteSqlBuilder extends BaseConditionSqlBuilder
{

	/* (non-Javadoc)
	 * @see com.ffzx.commerce.framework.dao.sqlbuilder.ISqlBuilder#getSql(java.lang.Class, java.lang.Object)
	 */
	@Override
	public String getSql(Class<?> clazz)
	{

		FFEntityMeta tableMeta = SqlBuilderFactory.getInstance().getEntityMeta(clazz);
 		String tableName = tableMeta.getTableName();
 		
 
		StringBuffer sql = new StringBuffer();

		sql.append("delete from ").append(tableName);
		
		sql.append(this.getWhereSql(tableMeta));
 

		return sql.toString();
	}


}

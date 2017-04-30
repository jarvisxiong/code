/**
 * @Description 
 * @author tangjun
 * @date 2016年7月7日
 * 
 */
package com.ff.common.dao.sqlbuilder;

import com.ff.common.dao.model.FFEntityMeta;
import com.ff.common.dao.sqlbuilder.base.BaseConditionSqlBuilder;

/**
 * @Description 
 * @author tangjun
 * @date 2016年7月7日
 */
public class CountSqlBuilder extends BaseConditionSqlBuilder
{

	/* (non-Javadoc)
	 * @see com.ffzx.dao.sqlbuilder.ISqlBuilder#getSql(java.lang.Class, java.lang.Object)
	 */
	@Override
	public String getSql(Class<?> clazz)
	{
		FFEntityMeta tableMeta = SqlBuilderFactory.getInstance().getEntityMeta(clazz);
		  
 		StringBuffer sql = new StringBuffer();
 		String whereSql = this.getWhereSql(tableMeta);
  
		sql.append("select count(1) from ");
		sql.append(tableMeta.getTableName());
		sql.append(whereSql);
		
	    return sql.toString();
	}

}

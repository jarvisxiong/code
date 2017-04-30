/**
 * @Description 
 * @author tangjun
 * @date 2016年7月5日
 * 
 */
package com.ff.common.dao.sqlbuilder;

import com.ff.common.dao.model.FFEntityMeta;
import com.ff.common.dao.sqlbuilder.base.BaseSelectSqlBuilder;

/**
 * @Description 
 * @author tangjun
 * @date 2016年7月5日
 */
public class GetAllSqlBuilder extends BaseSelectSqlBuilder
{

	/* (non-Javadoc)
	 * @see com.ffzx.dao.sqlbuilder.ISqlBuilder#getSql(java.lang.Class, java.lang.Object)
	 */
	@Override
	public String getSql(Class<?> clazz)
	{
 
		FFEntityMeta tableMeta = SqlBuilderFactory.getInstance().getEntityMeta(clazz);
  
 		StringBuffer sql = new StringBuffer();
		String selectSql = this.getSelectSql(tableMeta);
		String whereSql = this.getWhereSql(tableMeta);
		String groupSql = this.getGroupBySql(tableMeta);
		String orderSql = this.getOrderSql(tableMeta);
 
		sql.append(selectSql);
		sql.append(whereSql);
		sql.append(groupSql);
		sql.append(orderSql);
 
		return sql.toString();
	}


	
	

}

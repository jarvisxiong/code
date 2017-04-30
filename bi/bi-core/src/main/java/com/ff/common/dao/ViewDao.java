/**
 * @Description 
 * @author tangjun
 * @date 2016年6月30日
 * 
 */
package com.ff.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.ff.common.dao.model.FFCondition;
import com.ff.common.dao.sqlbuilder.CountSqlBuilder;
import com.ff.common.dao.sqlbuilder.GetAllSqlBuilder;
import com.ff.common.dao.sqlbuilder.GetSqlBuilder;
import com.ff.common.dao.sqlbuilder.ISqlBuilder;
import com.ff.common.web.json.PageJson;

/**
 * @Description 
 * @author tangjun
 * @date 2016年6月30日
 */
public interface ViewDao<E>  
{

 	@SelectProvider(type=CountSqlBuilder.class,method=ISqlBuilder.dynamicSQL)
	long getCount();
	long getCount(@Param(ISqlBuilder.PARA_CONIDTION)FFCondition conditionList);
	long getCount(@Param(ISqlBuilder.PARA_CONIDTION)List<FFCondition> conditionList);

	
	@SelectProvider(type=GetAllSqlBuilder.class,method=ISqlBuilder.dynamicSQL)
	List<E> getAll();
	List<E> getAll(@Param(ISqlBuilder.PARA_CONIDTION)FFCondition condition);
	List<E> getAll(@Param(ISqlBuilder.PARA_CONIDTION)List<FFCondition> conditionList);
	
	List<E> getAll(@Param(ISqlBuilder.PARA_PAGE)PageJson page);
	List<E> getAll(@Param(ISqlBuilder.PARA_CONIDTION)FFCondition condition,@Param(ISqlBuilder.PARA_PAGE)PageJson page);
	List<E> getAll(@Param(ISqlBuilder.PARA_CONIDTION)List<FFCondition> conditionList,@Param(ISqlBuilder.PARA_PAGE)PageJson page);
	
	
	@SelectProvider(type=GetSqlBuilder.class,method=ISqlBuilder.dynamicSQL)
	E get(@Param(ISqlBuilder.PARA_CONIDTION)E value);
	E get(@Param(ISqlBuilder.PARA_CONIDTION)String value);
	E get(@Param(ISqlBuilder.PARA_CONIDTION)Integer value);
	E get(@Param(ISqlBuilder.PARA_CONIDTION)Long value);
 	E get(@Param(ISqlBuilder.PARA_CONIDTION)FFCondition condition);
	E get(@Param(ISqlBuilder.PARA_CONIDTION)List<FFCondition> conditionList);

	@SelectProvider(type=GetSqlBuilder.class,method=ISqlBuilder.dynamicSQL)
	Object getMaxValueByField(String fieldName,List<FFCondition> conditionList);
	Object getMinValueByField(String fieldName,List<FFCondition> conditionList);

}

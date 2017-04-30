/**
 * @Description 
 * @author tangjun
 * @date 2016年6月30日
 * 
 */
package com.ff.common.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import com.ff.common.dao.model.FFCondition;
import com.ff.common.dao.sqlbuilder.CreateSqlBuilder;
import com.ff.common.dao.sqlbuilder.DeleteSqlBuilder;
import com.ff.common.dao.sqlbuilder.ISqlBuilder;
import com.ff.common.dao.sqlbuilder.UpdateSqlBuilder;

/**
 * @Description 
 * @author tangjun
 * @date 2016年6月30日
 */
public interface Dao<E> extends ViewDao<E>
{
	
 
	/**
	 * create object 
	 * @param object
	 */
	@InsertProvider(type=CreateSqlBuilder.class,method=ISqlBuilder.dynamicSQL)
	void create(@Param(ISqlBuilder.PARA_OBJ)E object);
	void create(@Param(ISqlBuilder.PARA_OBJ)Collection<E> objList);

	/**
	 * 
	 * 
	 * update object
	 * @param object
	 */
	@UpdateProvider(type=UpdateSqlBuilder.class,method=ISqlBuilder.dynamicSQL)
	void update(@Param(ISqlBuilder.PARA_OBJ)E object);
	void update(@Param(ISqlBuilder.PARA_OBJ)Collection<E> objList);
 

	/**
	 * 根据条件删除
	 */
	@DeleteProvider(type=DeleteSqlBuilder.class,method=ISqlBuilder.dynamicSQL)
	void delete();
	void delete(@Param(ISqlBuilder.PARA_CONIDTION)E e);
	void delete(@Param(ISqlBuilder.PARA_CONIDTION)String value);
	void delete(@Param(ISqlBuilder.PARA_CONIDTION)Integer value);
	void delete(@Param(ISqlBuilder.PARA_CONIDTION)Long value);
	void delete(@Param(ISqlBuilder.PARA_CONIDTION)FFCondition condition);
	void delete(@Param(ISqlBuilder.PARA_CONIDTION)List<FFCondition> conditionList);
 
	

}

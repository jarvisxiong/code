/**
 * @Description 
 * @author tangjun
 * @date 2016年7月7日
 * 
 */
package com.ff.common.dao.sqlbuilder.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;

import com.ff.common.dao.model.ConditionTypeEnum;
import com.ff.common.dao.model.FFEntityMeta;
import com.ff.common.dao.model.FFFieldMeta;
import com.ff.common.dao.model.FFCondition;
import com.ff.common.dao.sqlbuilder.SqlBuilderFactory;
import com.ff.common.util.calc.CalcUtil;
import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.validate.ValidatorUtil;

/**
 * @Description 
 * @author tangjun
 * @date 2016年7月7日
 */
public abstract class BaseSelectSqlBuilder extends BaseConditionSqlBuilder
{
	private final static Logger log = FFLogFactory.getLog(BaseSelectSqlBuilder.class);
 
	private static final String INNER_PARA_ORDERBY = "inner_"+PARA_CONIDTION+"_order_by";
	private static final String INNER_PARA_GROUPBY = "inner_"+PARA_CONIDTION+"_group_by";
	private static final String INNER_PARA_CALC = "inner_"+PARA_CONIDTION+"_calc";

	
	protected String getSelectSql(FFEntityMeta tableMeta)
	{
  		String tableName = tableMeta.getTableName();
 
		StringBuffer selectSql = new StringBuffer();
		selectSql.append("select ");
		
		selectSql.append(getCalcSql(tableMeta));

		selectSql.append(" from ").append(tableName);
		
		return selectSql.toString();
	}
    private String getCalcSql(FFEntityMeta tableMeta)
    {
  

    	StringBuffer sql = new StringBuffer();

     	sql.append("<if test=\"null!="+INNER_PARA_CALC+"\" > ");
    	sql.append(" <foreach collection=\"");
    	sql.append(INNER_PARA_CALC);
    	sql.append("\" item=\"obj\" index=\"index\" separator=\" , \"> ");
    	sql.append(" ${obj.myBatisFlag} ");
      	sql.append(" as ${obj.columnName} ");

    	sql.append("</foreach>");
    	sql.append("</if>");
     	sql.append("<if test=\"null=="+INNER_PARA_CALC+"\" > ");
		for (String columnName : tableMeta.getColumnMap().keySet())
     	{
			sql.append(columnName);
			sql.append(",");
      	}
		sql.delete(sql.lastIndexOf(","), sql.lastIndexOf(",") +1);
    	sql.append("</if>");
    	
    	return sql.toString();
    }
	
 
    protected String getOrderSql(FFEntityMeta tableMeta)
    {
  

    	StringBuffer sql = new StringBuffer();

     	sql.append("<if test=\"null!="+INNER_PARA_ORDERBY+"\" > order by  ");
    	sql.append(" <foreach collection=\"");
    	sql.append(INNER_PARA_ORDERBY);
    	sql.append("\" item=\"obj\" index=\"index\" separator=\" , \"> ");
     	sql.append(" ${obj.columnName}");
    	sql.append(" ${obj.myBatisFlag} ");
    	sql.append("</foreach>");
    	sql.append("</if>");
 
    	return sql.toString();
    }
    
    protected String getGroupBySql(FFEntityMeta tableMeta)
    {
 
    	StringBuffer sql = new StringBuffer();
      	sql.append("<if test=\"null!="+INNER_PARA_GROUPBY+"\" > group by  ");
    	sql.append(" <foreach collection=\"");
    	sql.append(INNER_PARA_GROUPBY);
    	sql.append("\" item=\"obj\" index=\"index\" separator=\" , \"> ");

    	sql.append(" ${obj.columnName} ");
    	//sql.append(" ${obj.myBatisFlag} ");
    	sql.append("</foreach>");
    	sql.append("</if>");
 
    	return sql.toString();
    }
    	
    private List<FFCondition> getOrderCondition(FFEntityMeta tableMeta,Object obj)
    {
     	return getConditionByKind(ConditionTypeEnum.KIND_ORDER, tableMeta, obj);
    }
    private List<FFCondition> getGroupCondition(FFEntityMeta tableMeta,Object obj)
    {
     	return getConditionByKind(ConditionTypeEnum.KIND_GROUP_BY, tableMeta, obj);
    }
    
    private List<FFCondition> getCalcCondition(FFEntityMeta tableMeta,Object obj)
    {
    	List<FFCondition> conditionList = getConditionByKind(ConditionTypeEnum.KIND_CALC, tableMeta, obj);
    	Map<String,FFCondition> conditionMap = new HashMap<String,FFCondition>();
    	
    	for(FFCondition e : conditionList)
    	{
    		conditionMap.put(e.getColumnName(), e);
    	}
    	
    	List<FFCondition> calcCondition = new ArrayList<>();
    	
    	for(String columnName : tableMeta.getColumnMap().keySet())
    	{
    		FFFieldMeta meta = tableMeta.getColumnMap().get(columnName);
    		
    		FFCondition condition = null;
    		if(conditionMap.containsKey(columnName))
    		{
    			condition = conditionMap.get(columnName);
    			if(condition.getConditionType() == ConditionTypeEnum.COMPLEX_CALC)
    			{
    				CalcUtil calcUtil = new CalcUtil();
    				Set<String> paraSet = calcUtil.getVariable(condition.getMyBatisFlag());
    				Map<String,String> paraMap = new HashMap<String,String>();

    				for(String key : paraSet)
    				{
    					paraMap.put(key, tableMeta.getFieldMap().get(key).getColumnName());
    				}
    				String temp = calcUtil.handleFormula(condition.getMyBatisFlag(), paraMap);
    				condition.setMyBatisFlag(temp);
    			}
    			else
    			{
    				condition.setMyBatisFlag(condition.getMyBatisFlag() + "(" +condition.getColumnName() + ")");
    			}
    		}
    		else
    		{
    			condition = new FFCondition(ConditionTypeEnum.ORIGIN,meta.getField().getName());
     		    condition.setColumnName(columnName);
     		    condition.setJdbcType(meta.getJdbcType());
				condition.setMyBatisFlag(columnName);

    		}

     		calcCondition.add(condition);
    	}
    	
     	return calcCondition;
    }
    
    
	/* (non-Javadoc)
	 * @see com.ffzx.dao.sqlbuilder.base.BaseConditionSqlBuilder#prepare(java.lang.Class, java.lang.Object)
	 */
	@Override
	public void prepare(Class<?> clazz, Object para)
	{
		FFEntityMeta tableMeta = SqlBuilderFactory.getInstance().getEntityMeta(clazz);
     	List<FFCondition> orderList = getOrderCondition(tableMeta,para);
     	
     	if(!ValidatorUtil.isEmpty(orderList))
     	{
         	this.setPara(para, INNER_PARA_ORDERBY, orderList);
     	}
     	else
     	{
     		this.setPara(para, INNER_PARA_ORDERBY, null);
     	}
     	
     	List<FFCondition> groupByList = getGroupCondition(tableMeta,para);

     	if(!ValidatorUtil.isEmpty(groupByList))
     	{
         	this.setPara(para, INNER_PARA_GROUPBY, groupByList);
     	}
     	else
     	{
     		this.setPara(para, INNER_PARA_GROUPBY, null);
     	}
     	
     	List<FFCondition> calcList = getCalcCondition(tableMeta,para);

     	if(!ValidatorUtil.isEmpty(calcList))
     	{
         	this.setPara(para, INNER_PARA_CALC, calcList);
     	}
     	else
     	{
     		this.setPara(para, INNER_PARA_CALC, null);
     	}
     	
     	
		super.prepare(clazz, para);
	}

	/* (non-Javadoc)
	 * @see com.ffzx.dao.sqlbuilder.BaseSqlBuilder#handleResult(org.apache.ibatis.mapping.MappedStatement)
	 */
	@Override
	public void handleResult(MappedStatement ms,Class<?> entityClass)
	{
		// TODO Auto-generated method stub
		entityResult(ms,entityClass);
	}

	protected void entityResult(MappedStatement ms,Class<?> entityClass)
	{
 		List<ResultMap> resultMaps = new ArrayList<ResultMap>();
		resultMaps.add(getResultMap(ms,entityClass));
		MetaObject msObject = SystemMetaObject.forObject(ms);
 		msObject.setValue("resultMaps", Collections.unmodifiableList(resultMaps));
	}
	

	
	private ResultMap getResultMap(MappedStatement ms,Class<?> entityClass)
	{

 
		FFEntityMeta entityTable = SqlBuilderFactory.getInstance().getEntityMeta(entityClass);

		List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
		for (FFFieldMeta entityColumn : entityTable.getFieldMetaList())
		{
			ResultMapping.Builder builder = new ResultMapping.Builder(ms.getConfiguration(),
					entityColumn.getField().getName(), entityColumn.getColumnName(), entityColumn.getField().getType());

			List<ResultFlag> flags = new ArrayList<ResultFlag>();
			if (entityColumn.isKey())
			{
				flags.add(ResultFlag.ID);
			}
			builder.flags(flags);
			resultMappings.add(builder.build());
		}
		ResultMap.Builder builder = new ResultMap.Builder(ms.getConfiguration(), "AutoMapperResultMap", entityClass,
				resultMappings, true);
		ResultMap resultMap = builder.build();
		return resultMap;
	}
	
}

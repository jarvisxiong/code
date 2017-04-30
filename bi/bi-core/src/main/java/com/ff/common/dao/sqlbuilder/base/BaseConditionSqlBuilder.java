/**
 * @Description 
 * @author tangjun
 * @date 2016年7月7日
 * 
 */
package com.ff.common.dao.sqlbuilder.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.ff.common.dao.model.ConditionTypeEnum;
import com.ff.common.dao.model.FFEntityMeta;
import com.ff.common.dao.model.FFFieldMeta;
import com.ff.common.dao.model.FFCondition;
import com.ff.common.dao.sqlbuilder.SqlBuilderFactory;
import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.meta.ReflectionUtil;
import com.ff.common.util.validate.ValidatorUtil;

/**
 * @Description 
 * @author tangjun
 * @date 2016年7月7日
 */
public abstract class BaseConditionSqlBuilder extends BaseSqlBuilder
{
	private final static Logger log = FFLogFactory.getLog(BaseConditionSqlBuilder.class);
	
	private static final String INNER_PARA_CONDITION = "inner_" + PARA_CONIDTION+"_where";

    protected String getWhereSql(FFEntityMeta tableMeta)
    {
  

    	StringBuffer sql = new StringBuffer();

     	sql.append("<if test=\"null!="+INNER_PARA_CONDITION+"\" > where ");
    	sql.append(" <foreach collection=\"");
    	sql.append(INNER_PARA_CONDITION);
    	sql.append("\" item=\"obj\" index=\"index\" separator=\" and \"> ");

    	sql.append(" ${obj.columnName} ");
    	sql.append(" ${obj.myBatisFlag} ");
    	sql.append(" #{obj.attrValue}" );
    	sql.append("</foreach>");
    	sql.append("</if>");
    	

 
    	return sql.toString();
    }

    
	public void  prepare(Class<?> clazz, Object para)
	{
		this.prepareCondition(clazz, (Map) para);
		super.prepare(clazz, para);
	}
    
	protected void prepareCondition(Class<?> clazz, Map para)
	{
		FFEntityMeta tableMeta = SqlBuilderFactory.getInstance().getEntityMeta(clazz);
     	List<FFCondition> metaList = getWhereData(tableMeta,para);
     	
     	if(!ValidatorUtil.isEmpty(metaList))
     	{
     		this.setPara(para, INNER_PARA_CONDITION, metaList);
     	}
     	else
     	{
     		this.setPara(para, INNER_PARA_CONDITION, null);
     	}

    	

 	}
	
    private List<FFCondition> getOriginCondition(FFEntityMeta tableMeta,Object obj)
    {
    	List<FFCondition> conditionList = new ArrayList<FFCondition>();

    	if(null == obj)
    	{
    		return conditionList;
    	}
    	if(!(obj instanceof Map))
    	{
    		return conditionList;
    	}
    	Map para = (Map) obj; 
    	Object objCondition = null;

    	if(para.containsKey(PARA_CONIDTION))
    	{
    		objCondition =  para.get(PARA_CONIDTION);
     	}
    	if(null == objCondition)
    	{
    		log.warn("can not get the condition");
    		return conditionList;
    	}
     	
    	if(objCondition instanceof List)
    	{
    		conditionList = (List<FFCondition>) objCondition;
    	}
    	else if(objCondition instanceof FFCondition)
    	{
    		conditionList.add((FFCondition) objCondition);
    	}
    	
    	else if(objCondition.getClass() == tableMeta.getClazz())
    	{
    		//如果传递的是实体对象，就按照主键的条件匹配
    		for(String col : tableMeta.getIdColumnList())
    		{
    			FFFieldMeta fieldMeata = tableMeta.getColumnMap().get(col);
    			Object value = ReflectionUtil.getValueByFieldName(objCondition, fieldMeata.getField().getName());
        		FFCondition conditon = new FFCondition(ConditionTypeEnum.EQUAL,fieldMeata.getField().getName(),value);
        		conditionList.add(conditon);
    		}
    	}
    	else 
    	{
    		//如果传递是其他的对象，就转成主键，按照主键匹配
    		String idColumn = tableMeta.getIdColumnList().get(0);
    		
    		String fieldName =  tableMeta.getColumnMap().get(idColumn).getField().getName();
    		
    		Object value = ReflectionUtil.convertToFieldObject(tableMeta.getClazz(),fieldName, objCondition);
    		FFCondition conditon = new FFCondition(ConditionTypeEnum.EQUAL,fieldName,value);
    		conditionList.add(conditon);
    	}
    	
    	return conditionList;
    }
    protected List<FFCondition> getConditionByKind(int conditionKind,FFEntityMeta tableMeta,Object obj)
    {
    	List<FFCondition> conditionList = getOriginCondition(tableMeta,obj);
    	
    	List<FFCondition> newConditionList = fillConditionValue(conditionKind,tableMeta, conditionList);
    	
    	return newConditionList;
    }
    private List<FFCondition> getWhereData(FFEntityMeta tableMeta,Object obj)
    {
     	return getConditionByKind(ConditionTypeEnum.KIND_WHERE, tableMeta, obj);
    }


 
}

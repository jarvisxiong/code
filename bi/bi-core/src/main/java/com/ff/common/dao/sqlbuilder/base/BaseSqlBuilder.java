/**
 * @Description 
 * @author tangjun
 * @date 2016年7月4日
 * 
 */
package com.ff.common.dao.sqlbuilder.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.MappedStatement;
import org.slf4j.Logger;

import com.ff.common.dao.model.FFEntityMeta;
import com.ff.common.dao.model.FFFieldMeta;
import com.ff.common.dao.model.FFCondition;
import com.ff.common.dao.sqlbuilder.ISqlBuilder;
import com.ff.common.util.format.DateUtil;
import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.meta.ReflectionUtil;

/**
 * @Description 
 * @author tangjun
 * @date 2016年7月4日
 */
public abstract class BaseSqlBuilder implements ISqlBuilder
{
	private final static Logger log = FFLogFactory.getLog(BaseSqlBuilder.class);

    public String dynamicSQL(Object record) {
        return ISqlBuilder.dynamicSQL;
    }
    

	public void  prepare(Class<?> clazz, Object para)
	{
		
	}

	public void handleResult(MappedStatement ms,Class<?> entityClass)
	{
 
	}
	

	public void setPara(Object para,String key, Object obj)
	{
      	{
         	if(null != para && para instanceof Map)
         	{
         		((Map)para).put(key, obj);	
         	}
     	}
	}
	
	public List<FFCondition> fillConditionValue(int type,FFEntityMeta tableMeta,List<FFCondition> conditionList)
	{
    	List<FFCondition> newConditionList = new ArrayList<FFCondition>();
    	for(FFCondition e : conditionList)
    	{
    		if(tableMeta.getFieldMap().containsKey(e.getAttrName()))
    		{
    			if(e.getConditionType().isFilterByType(type))
    			{
    				
        			FFFieldMeta cache = tableMeta.getFieldMap().get(e.getAttrName());
        			FFCondition newCondition = new FFCondition(e.getConditionType());
        			

        			newCondition.setAttrName(e.getAttrName());
        			newCondition.setJdbcType(cache.getJdbcType());
        			newCondition.setColumnName(cache.getColumnName());
        			newCondition.setMyBatisFlag(e.getMyBatisFlag());
        			
    				newCondition.setAttrValue(getValue(e,cache));
 
            		newConditionList.add(newCondition);
    			}
     		}
    		else
    		{
    			log.warn("the field is not spport. the name is " + e.getAttrName());
    		}
     	}
    	return newConditionList;
	}
	
	public static Object getValue(FFCondition condition,FFFieldMeta meta)
	{

		Object obj = condition.getAttrValue();
		switch (condition.getConditionType())
		{

			case LIKE_LEFT:
			{
				obj = obj + "%";
				break;
			}
			case LIKE_RIGHT:
			{
				obj = "%" + obj;
				break;
			}
	
			case LIKE:
			{
				obj = "%" + obj + "%";
				break;
			}
			case LT_EQ:
			{
				//date type, we should add one day for end date
				if(meta.getField().getType() == Date.class)
				{
					
					Object temp = ReflectionUtil.convertToType(obj, meta.getField().getType());
					if(temp instanceof Date)
					{
						obj = DateUtil.dayCalculate((Date) temp, 1);
					}
				}
				break;
			}
		
		}
		
 

		return obj;
	}
    
}

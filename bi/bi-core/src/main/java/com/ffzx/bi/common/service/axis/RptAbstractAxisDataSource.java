/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 * 
 */
package com.ffzx.bi.common.service.axis;

import java.util.List;

import com.ff.common.dao.model.ConditionTypeEnum;
import com.ff.common.dao.model.FFCondition;
import com.ff.common.dao.util.ConditionHandler;
import com.ff.common.service.BaseService;
import com.ffzx.bi.common.model.RptDim;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 */
public abstract class RptAbstractAxisDataSource implements RptAxisDataSource
{
	/* (non-Javadoc)
	 * @see com.ffzx.bi.common.service.RptAxisDataSource#getXStartValue(java.lang.String, java.util.List)
	 */

	public Object getXStartValue(RptDim dim, List<FFCondition> conditionList,BaseService servce)
	{
		String fieldName = this.getDimMapField(dim);
		FFCondition condition = ConditionHandler.getUniByAttrName(conditionList, ConditionTypeEnum.GT_EQ,fieldName);
 		Object value = null;
		if(null != condition)
		{
			value = condition.getAttrValue();
		}
		if(null == value)
		{
			value = servce.getMinByField(fieldName, conditionList);
			
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see com.ffzx.bi.common.service.RptAxisDataSource#getXEndValue(java.lang.String, java.util.List)
	 */
	
	public Object getXEndValue(RptDim dim, List<FFCondition> conditionList,BaseService servce)
	{
		String fieldName = this.getDimMapField(dim);
		FFCondition condition = ConditionHandler.getUniByAttrName(conditionList, ConditionTypeEnum.LT_EQ,fieldName);
 		Object value = null;
		if(null != condition)
		{
			value = condition.getAttrValue();
		}
		if(null == value)
		{
			value = servce.getMaxByField(fieldName, conditionList);
			
		}
		return value;
	}
	
	public String getDimMapField(RptDim dim)
	{
		return dim.getField_name();
	}
	
}

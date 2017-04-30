/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 * 
 */
package com.ff.common.dao.util;

import java.util.ArrayList;
import java.util.List;

import com.ff.common.dao.model.ConditionTypeEnum;
import com.ff.common.dao.model.FFCondition;
import com.ff.common.util.validate.ValidatorUtil;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 */
public class ConditionHandler
{
	
	public static List<FFCondition> getGroupBy(List<FFCondition> originList)
	{
		List<FFCondition> newConditionList = filterByKind(originList,ConditionTypeEnum.KIND_GROUP_BY);
 
		return newConditionList;
	}
	
	public static List<FFCondition> getByAttrName(List<FFCondition> originList,ConditionTypeEnum type,String attrName)
	{
		List<FFCondition> newConditionList = filterByAttrName(originList,type,attrName);
 		return newConditionList;
	}
	
	public static FFCondition getUniByAttrName(List<FFCondition> originList,ConditionTypeEnum type,String attrName)
	{
		List<FFCondition> newConditionList = filterByAttrName(originList,type,attrName);
		
		if(!ValidatorUtil.isEmpty(newConditionList))
		{
			return newConditionList.get(0);
		}
 		return null;
	}
	
	public static List<FFCondition> getByAttrName(List<FFCondition> originList,String attrName)
	{
		List<FFCondition> newConditionList = filterByAttrName(originList,attrName);
 		return newConditionList;
	}
	
	public static FFCondition getUniByAttrName(List<FFCondition> originList,String attrName)
	{
		List<FFCondition> newConditionList = filterByAttrName(originList,attrName);
		
		if(!ValidatorUtil.isEmpty(newConditionList))
		{
			return newConditionList.get(0);
		}
 		return null;
	}
	private static List<FFCondition> filterByAttrName(List<FFCondition> originList,ConditionTypeEnum type,String attrName)
	{
		List<FFCondition> newConditionList = new ArrayList<FFCondition>();
		for(FFCondition e : originList)
		{
			if(!ValidatorUtil.isEmpty(attrName) && attrName.equals(e.getAttrName()) && type == e.getConditionType())
			{
				newConditionList.add(e);
 			}
		}
		
		return newConditionList;
	}
	
	private static List<FFCondition> filterByAttrName(List<FFCondition> originList,String attrName)
	{
		List<FFCondition> newConditionList = new ArrayList<FFCondition>();
		for(FFCondition e : originList)
		{
			if(!ValidatorUtil.isEmpty(attrName) && attrName.equals(e.getAttrName()))
			{
				newConditionList.add(e);
 			}
		}
		
		return newConditionList;
	}
	
	private static List<FFCondition> filterByKind(List<FFCondition> originList,int kind)
	{
		List<FFCondition> newConditionList = new ArrayList<FFCondition>();
		for(FFCondition e : originList)
		{
			if(kind == e.getConditionType().getKind())
			{
				newConditionList.add(e);
 			}
		}
		
		return newConditionList;
	}
}

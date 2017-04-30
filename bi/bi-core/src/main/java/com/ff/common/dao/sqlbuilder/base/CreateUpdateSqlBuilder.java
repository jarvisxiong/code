package com.ff.common.dao.sqlbuilder.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class CreateUpdateSqlBuilder extends BaseSqlBuilder
{
	protected static final String INNER_PARA_OBJECT_LIST = "inner_" + PARA_OBJ;

	
	protected Collection<?> getObjList(Class<?> clazz,Object para)
	{
		if (null == para)
		{
			throw new RuntimeException("the para is empty");
		}
		String paraKey = PARA_OBJ;

		Object obj = para;
		if(para.getClass() != clazz)
		{
			if(para instanceof Map)
			{
				obj = ((Map) para).get(paraKey);
			}
		}

		if(obj instanceof Collection)
		{
			return (Collection<?>) obj;
		}
		List objList = new ArrayList();
        objList.add(obj);
		 
		return objList;
	}
}

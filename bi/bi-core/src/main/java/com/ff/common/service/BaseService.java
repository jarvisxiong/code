package com.ff.common.service;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ff.common.dao.Dao;
import com.ff.common.dao.model.ConditionTypeEnum;
import com.ff.common.dao.model.FFCondition;
import com.ff.common.dao.mybatis.FFMapperProxy;
import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.meta.ReflectionUtil;
import com.ff.common.util.validate.ValidatorUtil;
import com.ff.common.web.json.FFSysUser;
import com.ff.common.web.json.PageDataJson;
import com.ff.common.web.json.PageJson;

@Service()
@Scope("prototype")
public class BaseService<E> implements InitializingBean, DisposableBean
{
	private Logger log = FFLogFactory.getLog(getClass());
	@Autowired
	private Dao<E> dao;
	
	private Class<?> entityClass;
 

	public BaseService()
	{
		this.entityClass = ReflectionUtil.getSuperClassGenricType(getClass());
	}

 
	public Class<?> getEntityClass()
	{
		return entityClass;
	}


	public void setEnitiyClass(Class<?> clazz)
	{
		this.entityClass = clazz;
		initClass();
	}
	
	public PageDataJson<E> load(FFSysUser user,List<FFCondition> conditionList,PageJson page)
	{
		return this.getAll(conditionList, page);
	}
	
	public void loadForward(FFSysUser user, List<FFCondition> conditionList)
	{	
	}
	public List<E> load(FFSysUser user,List<FFCondition> conditionList)
	{
		return this.getAll(conditionList);
	}
	public void loadBack(List<E> dataList)
	{	
	}
	
	public void create(FFSysUser user,E object)
	{
		dao.create(object);
	}
	
	public void update(FFSysUser user,E object)
	{
		dao.update(object);
	}


	public void create(E object)
	{
 		dao.create(object);
 	}
	
	public void create(List<E> objList)
	{
 		dao.create(objList);
 	}

	public long getCount()
	{
		return dao.getCount();
	}
	public long getCount(FFCondition condition)
	{
		return dao.getCount(condition);
	}
	
	public long getCount(List<FFCondition> conditionList)
	{
		return dao.getCount(conditionList);
	}

	public List<E> getAll()
 	{
 		return dao.getAll();
 	}
 	
	public List<E> getAll(FFCondition condition)
 	{
 		return dao.getAll(condition);
 	}
	public List<E> getAll(List<FFCondition> conditionList)
 	{
 		return dao.getAll(conditionList);
 	}
	
	
	public PageDataJson<E> getAll(PageJson page)
 	{
		PageDataJson<E> pageData = new PageDataJson<E>();
		List<E> dataList = dao.getAll(page);
		pageData.setDataList(dataList);
		pageData.setPage(page);
 		return pageData;
 	}
 	
	public PageDataJson<E> getAll(FFCondition condition,PageJson page)
 	{
		PageDataJson<E> pageData = new PageDataJson<E>();

		List<E> dataList = dao.getAll(condition,page);
		pageData.setDataList(dataList);
		pageData.setPage(page);
 		return pageData;
 	}
	public PageDataJson<E> getAll(List<FFCondition> conditionList,PageJson page)
 	{
		PageDataJson<E> pageData = new PageDataJson<E>();

		List<E> dataList = dao.getAll(conditionList,page);
		pageData.setDataList(dataList);
		pageData.setPage(page);
 		return pageData;
 	}
	
 
	public E get(E value)
	{
		return dao.get(value);
	}
	
	public E get(String value)
	{
		return dao.get(value);
	}
	public E get(Integer value)
	{
		return dao.get(value);
	}
	public E get(Long value)
	{
		return dao.get(value);
	}
	public E get(FFCondition condition)
	{
		return dao.get(condition);
	}
	public E get(List<FFCondition> conditionList)
	{
		return dao.get(conditionList);
	}
	public void update(E obj)
	{
		dao.update(obj);
	}
	public void update(Collection<E> objList)
	{
		dao.update(objList);
	}
	public void delete()
	{
		dao.delete();
	}
	public void delete(String value)
	{
		dao.delete(value);
	}
	public void delete(Integer value)
	{
		dao.delete(value);
	}
	public void delete(Long value)
	{
		dao.delete(value);
	}
	public void delete(FFCondition condition)
	{
		dao.delete(condition);
	}
	public void delete(List<FFCondition> conditionList)
	{
		dao.delete(conditionList);
	}
	
	public Object getMinByField(String fieldName,List<FFCondition> conditionList)
	{
 		return getMaxMinByField(ConditionTypeEnum.MIN,fieldName,conditionList);
	}

	
	public Object getMaxByField(String fieldName,List<FFCondition> conditionList)
	{
 		return getMaxMinByField(ConditionTypeEnum.MAX,fieldName,conditionList);
	}

	private Object getMaxMinByField(ConditionTypeEnum type,String fieldName,List<FFCondition> conditionList)
	{
		List<FFCondition> conditions = new ArrayList<FFCondition>();
		if(!ValidatorUtil.isEmpty(conditionList))
		{
			conditions.addAll(conditionList);
		}
		conditions.add(new FFCondition(type,fieldName));
		List<E> dataList = dao.getAll(conditions);
		if(!ValidatorUtil.isEmpty(dataList))
		{
			return ReflectionUtil.getValueByFieldName(dataList.get(0), fieldName);
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void afterPropertiesSet() throws Exception
	{
		initClass();
 	}
	
	private void initClass()
	{
		if(null != dao)
		{
	 		Proxy proxy = (Proxy) dao;
	 		MetaObject mo = SystemMetaObject.forObject(proxy);
	 		Object val = mo.getValue("h");
	 		if(null != val && val.getClass() == FFMapperProxy.class)
	 		{
				mo.setValue("h.entityClass", entityClass);
	 		}
		}

	}


	@Override
	public void destroy() throws Exception
	{
		// TODO Auto-generated method stub
		  
	}

}

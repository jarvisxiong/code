package com.ff.common.dao.mybatis;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.SqlSession;

public class FFMapperProxy<T> extends MapperProxy<T>
{
	public FFMapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache)
	{
		super(sqlSession, mapperInterface, methodCache);
		// TODO Auto-generated constructor stub
	    this.sqlSession = sqlSession;
	    this.mapperInterface = mapperInterface;
	    this.methodCache = methodCache;
	}



	private Class<?> entityClass = null;

 
	public Class<?> getEntityClass()
	{
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass)
	{
		this.entityClass = entityClass;
	}

 

	  private static final long serialVersionUID = -6424540398559729838L;
	  private final SqlSession sqlSession;
	  private final Class<T> mapperInterface;
	  private final Map<Method, MapperMethod> methodCache;

 

	  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	    if (Object.class.equals(method.getDeclaringClass())) {
	      try {
	        return method.invoke(this, args);
	      } catch (Throwable t) {
	        throw ExceptionUtil.unwrapThrowable(t);
	      }
	    }
	    final MapperMethod mapperMethod = cachedMapperMethod(method);
	    return mapperMethod.execute(sqlSession, args);
	  }

	  private MapperMethod cachedMapperMethod(Method method) {
	    MapperMethod mapperMethod = methodCache.get(method);
	    if (mapperMethod == null) {
	      mapperMethod = new FFMapperMethod(mapperInterface, method, sqlSession.getConfiguration(),this.entityClass);
	      methodCache.put(method, mapperMethod);
	    }
	    return mapperMethod;
	  }

}

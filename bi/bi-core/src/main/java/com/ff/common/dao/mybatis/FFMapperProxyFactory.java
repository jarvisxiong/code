package com.ff.common.dao.mybatis;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.session.SqlSession;

public class FFMapperProxyFactory<T> extends MapperProxyFactory<T>
{

	public FFMapperProxyFactory(Class<T> mapperInterface)
	{
		super(mapperInterface);
		this.mapperInterface = mapperInterface;
		// TODO Auto-generated constructor stub
	}

	private final Class<T> mapperInterface;
	private Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<Method, MapperMethod>();

	public Class<T> getMapperInterface()
	{
		return mapperInterface;
	}

	public Map<Method, MapperMethod> getMethodCache()
	{
		return methodCache;
	}

	@SuppressWarnings("unchecked")
	protected T newInstance(MapperProxy<T> mapperProxy)
	{
		return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]
		{ mapperInterface }, mapperProxy);
	}

	public T newInstance(SqlSession sqlSession)
	{
		final MapperProxy<T> mapperProxy = new FFMapperProxy<T>(sqlSession, mapperInterface, methodCache);
		return newInstance(mapperProxy);
	}

}

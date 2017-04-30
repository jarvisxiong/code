package com.ff.common.dao.mybatis;

import org.mybatis.spring.mapper.MapperFactoryBean;

public class FFMapperFactoryBean<T> extends MapperFactoryBean<T>
{

 

	@Override
	public boolean isSingleton()
	{
		// TODO Auto-generated method stub
		return false;
	}

	
}

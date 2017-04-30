package com.ffzx.order.utils;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import com.ffzx.commerce.framework.dao.DynamicRoutingDataSource;

@Component
public class DataSourceAdvise implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Master master = method.getAnnotation(Master.class);
		if (master != null) {
			Integer count=DataSourceHolder.counter.get();
			if(count==null){
				count=0;
			}
			DataSourceHolder.counter.set(count++);
			DynamicRoutingDataSource.setDbMaster();
			Object ret = invocation.proceed();
			count--;
			if(0==count.intValue()){
				DynamicRoutingDataSource.clearDBType();
			}
			return ret;
		}else{
			return invocation.proceed();
		}
	
	}
	
	static class DataSourceHolder{
		static ThreadLocal<Integer> counter=new ThreadLocal<>();
	}

}

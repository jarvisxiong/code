package com.ff.common.dao.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.NamedThreadLocal;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.ff.common.util.log.FFLogFactory;

/**
 * 
 * @author cmm
 * 
 */
public class DataBaseRouting extends AbstractRoutingDataSource implements BeanPostProcessor {

	private static final Logger log = FFLogFactory.getLog(DataBaseRouting.class);

	public static final String MASTER_DATA_SOURCE = "MASTER";

	public static final String SLAVE_DATA_SOURCE = "SLAVE";

	private static final ThreadLocal<Object> currentLookupKey = new NamedThreadLocal<Object>("Current lookup key");

	@Override
	protected Object determineCurrentLookupKey() {
		return DataBaseRouting.currentLookupKey.get();
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (TransactionInterceptor.class.isInstance(bean)) {
			ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
			proxyFactoryBean.setInterfaces(MethodInterceptor.class);
			proxyFactoryBean.setTarget(bean);
			proxyFactoryBean.addAdvice(new TransactionInterceptorAdvice());
			proxyFactoryBean.setProxyTargetClass(true);
			return proxyFactoryBean.getObject();
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	/**
	 * 事务数据源决策者
	 * 
	 * @author ching
	 * 
	 */
	public class TransactionInterceptorAdvice implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			try {
				if (!TransactionSynchronizationManager.isSynchronizationActive()) {
					DataBaseRouting.currentLookupKey.set(MASTER_DATA_SOURCE);
					if (log.isDebugEnabled()) {
						log.debug("Current lookup key is " + MASTER_DATA_SOURCE);
					}
				}
				return invocation.proceed();
			} finally {
				DataBaseRouting.currentLookupKey.remove();
			}
		}
	}
}

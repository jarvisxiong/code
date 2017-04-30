package com.ff.common.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext.<br>
 * @author zhugj
 * @date 2016-02-17 上午10:26:41
 * @version 0.1.0 
 */
@Service
@Lazy(false)
public class SpringContextUtil implements ApplicationContextAware, DisposableBean {

    // Spring应用上下文环境  
    private static ApplicationContext applicationContext;  
    /** 
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境 
     *  
     * @param applicationContext 
     */  
    public void setApplicationContext(ApplicationContext applicationContext) {  
        SpringContextUtil.applicationContext = applicationContext;  
    }  
    /** 
     * @return ApplicationContext 
     */  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
    /** 
     * 获取对象 
     *  
     * @param name 
     * @return Object
     * @throws BeansException 
     */  
    public static Object getBean(String name)  {  
         return applicationContext.getBean(name);  
    }
    
    public static <T> T getBean(String name,Class<T> clazz)  
    {  
        return applicationContext.getBean(name,clazz);  
    }
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}  
}
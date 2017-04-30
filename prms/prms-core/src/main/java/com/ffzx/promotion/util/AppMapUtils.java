/*
 * 文 件 名:  BeanMapUtils.java
 * 版   权: Copyright  Corporation 2013 版权所有
 * 描     述:  <描述>
 * 修 改 人:  zhugj
 * 修改时间:  2013-7-27
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.ffzx.promotion.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ffzx.commerce.framework.utils.StringUtil;


 /**
 * @Description: app专用map转，私人专用
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年8月22日 下午3:04:22
 * @version V1.0 
 *
 */
public class AppMapUtils {
	public static Logger logger = LoggerFactory.getLogger(AppMapUtils.class);



	 /**
		 * @Description: 对象转map,查询列表专用
		 * @author yuzhao.xu
		 * @email  yuzhao.xu@ffzxnet.com
		 * @date 2016年8月22日 下午3:04:22
		 * @version V1.0 
		 *
		 */
		public static final Map<String, Object> toMapObjcet(Object bean) {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			if (null == bean)
				return returnMap;
			try {
				BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				for (int i = 0; i < propertyDescriptors.length; i++) {
					PropertyDescriptor descriptor = propertyDescriptors[i];
					String propertyName = descriptor.getName();
					
					if (!propertyName.equals("class")) {
						Method readMethod = descriptor.getReadMethod();
						Object result = readMethod.invoke(bean, new Object[0]);
						if (result != null && !StringUtil.isEmpty(result.toString())) {
							returnMap.put(propertyName, result.toString());
						} 
					}
				}
			} catch (Exception e) {
				logger.error("", e);
			}
			return returnMap;
		}
		
	 /**
	 * @Description: 对象转map
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @date 2016年8月22日 下午3:04:22
	 * @version V1.0 
	 *
	 */
	public static final Map<String, String> toMap(Object bean) {
		Map<String, String> returnMap = new HashMap<String, String>();
		if (null == bean)
			return returnMap;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null) {
						returnMap.put(propertyName, result.toString());
					} else {
						returnMap.put(propertyName, "");
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return returnMap;
	}
	/**
	 * list<Object>格式转变成List<map>格式
	 * 
	 * @param subAreae
	 *            list
	 * @param listResult
	 * @return
	 */
	public static final List<Object> objectsToMap(Object sourcesbean) {
		List<Object> sources=(List<Object>) sourcesbean;
		List<Object> listResult=new ArrayList<Object>();
		for (Object source : sources) {
			Map<String, String> row =toMap(source);
			listResult.add(row);
		}
		return listResult;
	}
	/**
	 * @Description: 对象转map，排除List<name>
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @date 2016年8月22日 下午3:04:22
	 * @version V1.0 
	 *
	 */
	public static final Map<String, Object> toMap(Object bean,List<String> propertys) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (null == bean)
			return returnMap;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				//如果存在此字段，不反射
				if(propertys!=null && propertys.size()>=1 && propertys.contains(propertyName)){
					continue;
				}
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null) {
						returnMap.put(propertyName, result);
					} else {
						returnMap.put(propertyName, "");
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return returnMap;
	}

}

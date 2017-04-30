/**
 * @Description 
 * @author tangjun
 * @date 2016年7月5日
 * 
 */
package com.ff.common.dao.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description 
 * @author tangjun
 * @date 2016年7月5日
 */
@Retention(RetentionPolicy.RUNTIME) 
public @interface FFDao
{
	Class<?> type() default Object.class;  //实体类型名称
	boolean exclude() default false;       //是否排除，不走映射关系
}

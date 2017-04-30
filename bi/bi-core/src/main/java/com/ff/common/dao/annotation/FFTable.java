/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 * 
 */
package com.ff.common.dao.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 */
@Retention(RetentionPolicy.RUNTIME) 
public @interface FFTable
{
	public String name() default "";     //表明
	public String mapper() default "";   //映射规则
}

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
public @interface FFColumn
{
	public final static String DEFAULT_VALUE="";
	public String name() default DEFAULT_VALUE;         //列名
	public boolean isKey() default false;               //该字段是否为主键
	public String strategy() default DEFAULT_VALUE;     //生成策略
	public String jdbcType() default DEFAULT_VALUE;     //jdbc类型
	public boolean exclude() default false;             //是否排除该字段
}

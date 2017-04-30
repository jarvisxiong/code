/**
 * @Description 
 * @author tangjun
 * @date 2016年8月14日
 * 
 */
package com.ffzx.bi.common.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月14日
 */
@Retention(RetentionPolicy.RUNTIME) 
public @interface RptIndicatorAnno
{
	RptCalcAnno[] calcList() default {}; 
	String name() default "";
	String indicator() default "";

}

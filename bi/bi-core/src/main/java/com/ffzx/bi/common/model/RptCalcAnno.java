/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 * 
 */
package com.ffzx.bi.common.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.ffzx.bi.common.constant.RptCalcEnum;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 */

@Retention(RetentionPolicy.RUNTIME) 
public @interface RptCalcAnno
{
	public RptCalcEnum calc();
	public String formula() default "";
 
}

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 * 
 */
package com.ff.common.cache;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 */
@Retention(RetentionPolicy.RUNTIME) 
public @interface FFCacheAnno
{
	public boolean isKey() default false;
}

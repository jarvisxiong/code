/**
 * @Description 
 * @author tangjun
 * @date 2016年7月5日
 * 
 */
package com.ff.common.dao.datasource;

/**
 * @Description 
 * @author tangjun
 * @date 2016年7月5日
 */
public interface FFKeyValueSource
{
	public String getKey(String value);
	
	public String getValue(String key);

}

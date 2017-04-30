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
public interface FFDataSource
{
	public final static String UUID="UUIDSource";
	public final static String NowDate = "NowDateSource";
	
	public Object generator();
}

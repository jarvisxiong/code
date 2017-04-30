/**
 * @Description 
 * @author tangjun
 * @date 2016年7月5日
 * 
 */
package com.ff.common.dao.datasource;

import org.springframework.stereotype.Component;

/**
 * @Description 
 * @author tangjun
 * @date 2016年7月5日
 */
@Component(FFDataSource.UUID)
public class UUIDSource implements FFDataSource
{

	/* (non-Javadoc)
	 * @see com.ffzx.dao.datasource.FFIdSource#genertor()
	 */
	@Override
	public Object generator()
	{
		// TODO Auto-generated method stub
		java.util.UUID uuid = java.util.UUID.randomUUID(); 
		return uuid.toString().replaceAll("-", "");
	}

}

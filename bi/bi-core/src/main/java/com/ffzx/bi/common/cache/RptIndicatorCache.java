/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 * 
 */
package com.ffzx.bi.common.cache;


import org.springframework.stereotype.Service;

import com.ff.common.cache.FFAbstractCache;
import com.ffzx.bi.common.model.RptIndicator;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 */
@Service
public class RptIndicatorCache extends FFAbstractCache<String,RptIndicator>
{

	/* (non-Javadoc)
	 * @see com.fuego.common.cache.AbstractCache#load()
	 */
	@Override
	public void load()
	{
 
 	}


}

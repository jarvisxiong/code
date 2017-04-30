package com.ffzx.bi.api.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ff.common.util.log.FFLogFactory;
import com.ffzx.bi.api.service.SalesRptJobApiService;
import com.ffzx.bi.common.util.ExecRepositoryTransUtil;
import com.ffzx.commerce.framework.utils.RedisUtil;

public class SalesRptJobApiServiceImpl implements SalesRptJobApiService
{
	private Logger log = FFLogFactory.getLog(getClass());
	
	@Autowired
	private RedisUtil redisUtil;
	@Override
	public void runSalesRpt()
	{
		log.info("running sales report job");

		String key = "SalesRptJobApiServiceImpl.runSalesRpt";

		if (redisUtil.setNX(key, "running"))
		{
			try
			{
 				log.info("start running job");
				ExecRepositoryTransUtil.dbResourceForJob("order", "/ffzx_bi/order");
				log.info("running sales report job success");
 			} catch (Throwable e)
			{
				log.error("exe kettle script failed", e);
			} finally
			{
 				redisUtil.remove(key);
				log.info("remove the key " + key);
			}
 		}
		else
		{
			log.warn("exist job running,so discard");
		}

  
	}
	
 
	

 

}

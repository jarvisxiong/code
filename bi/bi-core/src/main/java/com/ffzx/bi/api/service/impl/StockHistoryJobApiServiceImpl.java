package com.ffzx.bi.api.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ff.common.util.log.FFLogFactory;
import com.ffzx.bi.api.service.StockHistoryJobApiService;
import com.ffzx.bi.common.util.ExecRepositoryTransUtil;
import com.ffzx.commerce.framework.utils.RedisUtil;

public class StockHistoryJobApiServiceImpl implements StockHistoryJobApiService {
	private Logger log = FFLogFactory.getLog(getClass());
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void runStockHistory() {
		log.info("runing stock history job");

		String key = "StockHistoryJobApiServiceImpl.runStockHistory";
		if (redisUtil.setNX(key, "running")) {
			try {
				log.info("start running job");
				ExecRepositoryTransUtil.dbResourceForJob("stock_main", "/ffzx_bi/stock");
				log.info("running stock history job success");
			} catch (Throwable e) {
				log.error("exe kettle script failed", e);
			} finally {
				redisUtil.remove(key);
				log.info("remove the key " + key);
			}
			log.info("runing stock history job success");
		} else {
			log.warn("exist job running");
		}
	}
}

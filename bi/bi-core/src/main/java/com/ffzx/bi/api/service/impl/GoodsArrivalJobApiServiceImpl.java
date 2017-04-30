package com.ffzx.bi.api.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ff.common.util.log.FFLogFactory;
import com.ffzx.bi.api.service.GoodsArrivalJobApiService;
import com.ffzx.bi.common.util.ExecRepositoryTransUtil;
import com.ffzx.commerce.framework.utils.RedisUtil;

public class GoodsArrivalJobApiServiceImpl implements GoodsArrivalJobApiService {
	private Logger log = FFLogFactory.getLog(getClass());
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void runGoodsArrival() {
		log.info("runing goods arrival job");

		String key = "GoodsArrivalJobApiServiceImpl.runGoodsArrival";
		if (redisUtil.setNX(key, "running")) {
			try {
				log.info("start running job");
				ExecRepositoryTransUtil.dbResourceForJob("goods_arrival", "/ffzx_bi/goods_arrival");
				log.info("running goods arrival job success");
			} catch (Exception e) {
				log.error("exe kettle script failed", e);
			} finally {
				redisUtil.remove(key);
				log.info("remove the key " + key);
			}
			log.info("runing goods arrival job success");
		} else {
			log.warn("exist job running");
		}
		
	}
}

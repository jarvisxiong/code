package com.ffzx.bi.api.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ff.common.util.log.FFLogFactory;
import com.ffzx.bi.api.service.FinanceInventoryMonthJobApiService;
import com.ffzx.bi.common.util.ExecRepositoryTransUtil;
import com.ffzx.commerce.framework.utils.RedisUtil;

public class FinanceInventoryMonthJobApiServiceImpl implements FinanceInventoryMonthJobApiService {
	private Logger log = FFLogFactory.getLog(getClass());
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void jobInitInboundOutboundBillsData() {
		log.info("init inbound outbound bills job");
		String key = "FinanceInventoryMonthJobApiServiceImpl.jobInitInboundOutboundBillsData";
		if (redisUtil.setNX(key, "running")) {
			try {
				log.info("start running job");
				ExecRepositoryTransUtil.dbResourceForJob("inventory", "/in_out_inventory");
				log.info("init inbound outbound bills job success");
			} catch (Exception e) {
				log.error("exe kettle script failed", e);
			} finally {
				redisUtil.remove(key);
				log.info("remove the key " + key);
			}
			log.info("init inbound outbound bills job success");
		} else {
			log.warn("exist job running");
		}
	}
}

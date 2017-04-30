/*package com.ffzx.order.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.api.dto.CommoditySwitchLog;
import com.ffzx.order.mapper.CommoditySwitchLogMapper;
import com.ffzx.order.service.CommoditySwitchLogService;

*//**
 * @className CommoditySwitchLogServiceImpl
 *
 * @author liujunjun
 * @date 2016-08-17 10:32:13
 * @version 1.0.0
 *//*
@Service("commoditySwitchLogService")
public class CommoditySwitchLogServiceImpl extends BaseCrudServiceImpl implements CommoditySwitchLogService {

	@Resource
	private CommoditySwitchLogMapper commoditySwitchLogMapper;
	
	@Override
	public CrudMapper init() {
		return commoditySwitchLogMapper;
	}

	@Override
	public CommoditySwitchLog findCommoditySwitchLogByCommodityCode(String commodityCode) throws ServiceException {
		return commoditySwitchLogMapper.findCommoditySwitchLogByCommodityCode(commodityCode);
	}

}*/
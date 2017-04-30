package com.ffzx.order.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.mapper.StockNumLogMapper;
import com.ffzx.order.service.StockNumLogService;

/**
 * @className StockNumLogServiceImpl
 *
 * @author liujunjun
 * @date 2016-05-20 20:43:31
 * @version 1.0.0
 */
@Service("stockNumLogService")
public class StockNumLogServiceImpl extends BaseCrudServiceImpl implements StockNumLogService {

	@Resource
	private StockNumLogMapper stockNumLogMapper;
	
	@Override
	public CrudMapper init() {
		return stockNumLogMapper;
	}	
}
package com.ffzx.order.service;

import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.model.StockThroughLog;

/**
 * stock_through_log数据库操作接口
 * 
 * @author ffzx
 * @date 2016-06-17 16:33:34
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface StockThroughLogService extends BaseCrudService {

	void insertStockWmsUsed(StockThroughLog stockLog);


}
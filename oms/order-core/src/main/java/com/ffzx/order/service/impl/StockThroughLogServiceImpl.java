package com.ffzx.order.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.mapper.StockThroughLogMapper;
import com.ffzx.order.model.StockThroughLog;
import com.ffzx.order.service.StockThroughLogService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-06-17 16:33:34
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("stockThroughLogService")
public class StockThroughLogServiceImpl extends BaseCrudServiceImpl implements StockThroughLogService {

    @Resource
    private StockThroughLogMapper stockThroughLogMapper;

    @Override
    public CrudMapper init() {
        return stockThroughLogMapper;
    }

	@Override
	public void insertStockWmsUsed(StockThroughLog stockLog) {
		
		stockThroughLogMapper.insertSelective(stockLog);
	}
}
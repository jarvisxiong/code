package com.ffzx.bi.service;

import java.util.List;
import java.util.Map;

import com.ffzx.bi.model.StockHistory;
import com.ffzx.bi.vo.StockHistoryVo;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * stock_repository数据库操作接口
 * 
 * @author ffzx
 * @date 2016-08-15 14:24:06
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface StockHistoryService extends BaseCrudService {

	List<StockHistory> findList(Page page, String orderByField, String orderBy, StockHistoryVo stockHistoryVo) throws ServiceException;

	Map<String, Object> findStockNum(Page page, String orderByField, String orderBy, StockHistoryVo stockHistoryVo);
}
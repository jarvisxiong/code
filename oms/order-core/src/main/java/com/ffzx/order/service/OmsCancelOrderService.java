 package com.ffzx.order.service;

import java.util.Map;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.enums.OrderOperationRecordEnum;

/**
 * oms_order数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-10 14:01:15
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface OmsCancelOrderService extends BaseCrudService {
	public void cancelOrder(OmsOrder order,OrderOperationRecordEnum operationRecord) throws ServiceException;
	public void deleteOrder(OmsOrder order) throws ServiceException;
	public void deleteOrderByUser(OmsOrder order) throws ServiceException;
	public OmsOrder getOrderByWebhooks(Map<String, Object> params) throws ServiceException;
}
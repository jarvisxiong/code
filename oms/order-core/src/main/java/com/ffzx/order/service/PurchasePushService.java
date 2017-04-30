package com.ffzx.order.service;

import java.util.Date;
import java.util.Map;

import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * purchase_push数据库操作接口
 * 
 * @author ffzx
 * @date 2016-06-08 17:29:40
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface PurchasePushService extends BaseCrudService {
	public Date selectMaxPushDate(Map<String, Object> params);
}
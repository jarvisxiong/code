package com.ffzx.order.service;

import java.util.List;

import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.api.dto.OmsOrder;

/**
 * 订单拆单接口
 * 
 * @author ffzx
 * @date 2016-09-02 14:01:15
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface OmsOrderSplitService extends BaseCrudService {
	List<OmsOrder> split(OmsOrder order) throws Exception;

}

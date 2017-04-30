package com.ffzx.order.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.model.PriceSettlement;

/**
 * @className PriceSettlementService
 *
 * @author liujunjun
 * @date 2017-03-23 16:35:45
 * @version 1.0.0
 */
public interface PriceSettlementService extends BaseCrudService{
	List<PriceSettlement> selectUninoDeatail(Map<String,Object> params);
}

package com.ffzx.promotion.api.service;

import java.util.Map;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;

public interface ActivityOrderApiService extends BaseCrudService {
	/**
	 * 下单验证
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto getOmsOrder(Map<String, Object> params) throws ServiceException;
	
	public int getActivityCommodityBuyNum(Map<String, Object> map) throws ServiceException;
	
	public Object getRedisCount(Map<String, Object> map) throws ServiceException;
	
	public ResultDto checkOrder(Object order);
}

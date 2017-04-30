package com.ffzx.order.api.service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.order.api.dto.DailySalesVo;

public interface DailySalesApiService {
    
	/**
	 * 销量查询对外接口
	 * @param vo
	 * @return  List<DailySalesVo>
	 * @throws ServiceException
	 */
	public ResultDto queryDailySalesData(DailySalesVo vo)throws ServiceException;
}

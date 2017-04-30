package com.ffzx.promotion.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.AdvertRegion;

/**
 * @className AdvertRegionService
 *
 * @author hyl
 * @date 2016-05-03 14:48:15
 * @version 1.0.0
 */
public interface AdvertRegionService extends BaseCrudService{
	/**
	 * 保存区域
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode save(AdvertRegion data) throws ServiceException;
	
	/**
	 * 获取所有区域
	 * params 查询参数
	 * @return
	 * @throws ServiceException
	 */
	public List<Object> getAdvertRegionSimpleTree(Map<String, Object> params) throws ServiceException;
}

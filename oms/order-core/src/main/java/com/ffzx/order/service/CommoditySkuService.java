package com.ffzx.order.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.api.dto.CommoditySku;

/**
 * commodity_sku数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-13 09:34:39
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface CommoditySkuService extends BaseCrudService {
	List<CommoditySku> queryByJoins(Map<String,Object> params)throws Exception;

	/**
	 * 根据商品编码
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 */
	public List<CommoditySku> getCommoditySkuByCode(String commodityCode)throws ServiceException;
	
	public CommoditySku getCommoditySkuByIdFromRedis(String skuId);
	
	public CommoditySku findBySkuCode(String skuCode)throws ServiceException;
	
	public CommoditySku findByBarCode(String barCode)throws ServiceException;
	
	public List<CommoditySku> getCommoditySkuByParams(Map<String, Object> params)throws ServiceException;
	
}
package com.ffzx.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.order.api.dto.CommoditySku;

/**
 * commodity_sku数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-13 09:34:39
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface CommoditySkuMapper extends CrudMapper {
	public <T> List<T> selectWhitjoinByParams(@Param("params") Map<String, Object> params);
	
	/**
	 * 根据商品编码
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 */
	public List<CommoditySku> getCommoditySkuByCode(@Param("commodityCode")String commodityCode)throws ServiceException;
	
	public CommoditySku findBySkuCode(String skuCode)throws ServiceException;
	
	public CommoditySku findByBarCode(@Param("barCode")String barCode)throws ServiceException;
	
	public List<CommoditySku> getCommoditySkuByParams(@Param("params") Map<String, Object> params)throws ServiceException;
}
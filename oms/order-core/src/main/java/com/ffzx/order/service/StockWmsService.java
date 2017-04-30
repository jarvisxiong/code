/*package com.ffzx.order.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.api.dto.StockUsed;
import com.ffzx.order.api.dto.StockWms;
import com.ffzx.order.api.dto.StockWmsUsed;

*//**
 * @className StockWmsService
 *
 * @author liujunjun
 * @date 2016-05-17 15:26:54
 * @version 1.0.0
 *//*
public interface StockWmsService extends BaseCrudService{
	
	*//**
	 * 根据商品编码查找已绑定的地址
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public List<StockWms> findAddressOrWarehouse(String commodityCode)throws ServiceException;
	
	*//**
	 * 根据自定义非区域行商品 查找wms仓库
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public StockWms findStockWmsBySkuCode(String skuCode)throws ServiceException;
	
	*//**
	 * 根据sku编码和仓库编码查找唯一的库存sku商品
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public StockWms findStockWmsBySkuCodeAndWarehouseCode(String commoditySkuCode,String warehouseCode)throws ServiceException;
	
	*//**
	 * 根据商品编码和仓库编码更新已占用数、用户可购买量
	 * @param skuCode       商品编码
	 * @param warehouseCode 仓库编码
	 * @param number        更新数量
	 * @return
	 * @throws ServiceException
	 *//*
	public int updateStockWmsbyCon(Map<String, Object> params) throws ServiceException;
	
	public int updateByPrimaryKeySelective(StockWms stockWms);
	
	public int insertStockWms(StockWms stockWms) throws ServiceException;
	
	public StockWmsUsed getStockWmsbyCommodityCode(String commodityCode,String warehouseCode);
	
	public StockWmsUsed getStockWmsbySkuCode(String skuCode,String warehouseCode);
	
	*//**
	 * 根据商品编码查找仓库
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public String getWarehouseName(String commodityCode)throws ServiceException;
	
	*//**
	 * wms 分页查询
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public List<StockWms> findByPage(Page pageObj,Map<String, Object> params)throws ServiceException;
	
	*//**
	 * 根据条件筛选出WMS存导出
	 * @param params
	 * @return
	 * @throws ServiceException
	 *//*
	public List<StockWms> findStockWmsExport(Map<String, Object> params)throws ServiceException;
	
	*//***begin***************************************订单流程自用，独立出来，可以调用，但是请不要乱改*******************************************************//*
	
	*//**
	 * 根据skuCode或者商品code为主要条件查询库存以及占用数
	 * @param skuCode
	 * @return
	 * @throws ServiceException
	 *//*
	public StockUsed findStockbyCode(Map<String, Object> params)throws ServiceException;
	
	*//**
	 * 根据skuCode或者商品code为主要条件查询库存以及占用数
	 * @param skuCode
	 * @return
	 * @throws ServiceException
	 *//*
	public StockUsed findStockbyCodeAndWarehouse(Map<String, Object> params)throws ServiceException;
	
	*//***end*************************************************************************************************************//*
}
*/
///**
// * 
// */
//package com.ffzx.order.service;
//
//import com.ffzx.basedata.api.dto.Address;
//import com.ffzx.commerce.framework.dto.ResultDto;
//import com.ffzx.commerce.framework.exception.ServiceException;
//import com.ffzx.order.api.dto.Commodity;
//import com.ffzx.order.api.dto.StockUsed;
//
///**
// * @author as
// *
// */
//public interface OrderStockManagerService {
//	
//	/**
//	 * 根据skuCode获取已占用数、用户可购买量、以及当前库存总数
//	 * @param skuCode       skuCode
//	 * @param type       '0：WMS决定商品可销售数量；1：自定义商品可销售数量'
//	 * @return StockUsed
//	 * @throws ServiceException
//	 */
//	public StockUsed getStockbyskuCode(String skuCode,String type) throws ServiceException;
//	
//	/**
//	 * 根据skuCode获取已占用数、用户可购买量、以及当前库存总数
//	 * @param commodityCode       商品Code
//	 * @param type       '0：WMS决定商品可销售数量；1：自定义商品可销售数量'
//	 * @return StockUsed
//	 * @throws ServiceException
//	 */
//	public StockUsed getStockbyCommodityCode(String commodityCode,String type) throws ServiceException;
//	
//	/**
//	 * wms
//	 * 根据skuCode获取已占用数、用户可购买量、以及当前库存总数
//	 * @param skuCode                  skuCode
//	 * @param warehouseCode             县城(如果没有，则为空)
//	 * @param warehouseCenterCode       中央仓（如果没有，则为空）
//	 * @return StockUsed
//	 * @throws ServiceException
//	 */
//	public StockUsed getWmsStockbySkuCodeAndwarehouseCode(String skuCode,String warehouseCode,String warehouseCenterCode) throws ServiceException;
//	
//	/**
//	 * 自定义
//	 * 根据skuCode获取已占用数、用户可购买量、以及当前库存总数
//	 * @param skuCode                  skuCode
//	 * @param addressCode              地址Code(如果没有，则为空)
//	 * @return StockUsed
//	 * @throws ServiceException
//	 */
//	public StockUsed getCustomStockbySkuCode(String skuCode,String addressCode) throws ServiceException;
//	
//	/**
//	 * wms
//	 * 根据商品Code获取已占用数、用户可购买量、以及当前库存总数
//	 * @param skuCode                  skuCode
//	 * @param warehouseCode             县城(如果没有，则为空)
//	 * @param warehouseCenterCode       中央仓（如果没有，则为空）
//	 * @return StockUsed
//	 * @throws ServiceException
//	 */
//	public StockUsed getWmsStockbyCommodityCodeAndwarehouseCode(String commodityCode,String warehouseCode,String warehouseCenterCode) throws ServiceException;
//	
//	/**
//	 * 自定义
//	 * 根据商品Code获取已占用数、用户可购买量、以及当前库存总数
//	 * @param skuCode                  skuCode
//	 * @param addressCode              地址Code(如果没有，则为空)
//	 * @return StockUsed
//	 * @throws ServiceException
//	 */
//	public StockUsed getCustomStockbyCommodityCodeAndAddressCode(String commodityCode,String addressCode) throws ServiceException;
//	
//	/**
//	 * 获取已占用数、用户可购买量、以及当前库存总数
//	 * 注：如果没有查询到该库存，（非区域性设置为0：表示为没有库存，区域性设置为-1：设置为不买）
//	 * @param skuCode       商品skuCode
//	 * @param commodity     skuCode所对应的商品对象
//	 * @param address       地址对象：该对象
//	 * @return
//	 * data{
//	 * 	 StockUsed对象
//	 * }
//	 * @throws ServiceException
//	 */
//	public ResultDto getInfobyskuCodeAndaddress(String skuCode,Commodity commodity,Address address) throws ServiceException;
//	
//	/**
//	 * 根据地址id获取符合正确的地址对象
//	 * @param addressId
//	 * @return
//	 * @throws ServiceException
//	 */
//	public ResultDto verifyAddressbyAddressId(String addressId) throws ServiceException;
//}

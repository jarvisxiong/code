/*package com.ffzx.order.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.commodity.api.dto.Commodity;
import com.ffzx.order.api.dto.StockCustom;
import com.ffzx.order.api.dto.StockCustomUsed;
import com.ffzx.order.api.dto.StockUsed;

*//**
 * @className StockCustomService
 *
 * @author liujunjun
 * @date 2016-05-17 15:20:32
 * @version 1.0.0
 *//*
public interface StockCustomService extends BaseCrudService{ 
	
	*//**
	 * 根据sku编码和地址编码查找唯一的库存sku商品
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public StockCustom findStockCustomBySkuCodeAndAddressCode(String commoditySkuCode,String addressCode)throws ServiceException;

	*//**
	 * 根据商品编码和地址编码查找当前地址的所有库存sku商品
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public List<StockCustom> findStockCustomByCommodityCodeAndAddressCode(String commodityCode, String addressCode)throws ServiceException;
	
	*//**
	 * 根据商品编码和地址编码查找当前地址的所有库存sku商品
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public List<StockCustom> findByPage(Page pageObj,Map<String, Object> params)throws ServiceException;
	
	*//**
	 * 根据商品编码和地址编码查找当前地址的所有库存sku商品
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public List<StockCustom> findByPage2(Page pageObj,Map<String, Object> params)throws ServiceException;

	*//**
	 * 根据商品编码和地址编码更新对应仓库当前库存
	 * @param skuCode       商品编码
	 * @param addressCode   地址编码
	 * @param number        更新数量
	 * @return
	 * @throws ServiceException
	 *//*
	public int updateStockCustombyCon(Map<String, Object> params) throws ServiceException;
	
	public void updateByPrimaryKeySelective(StockCustom stockCustom );
	*//**
	 * 添加日志
	 *//*
    public String insertLog(String newNum,String stockCustomId)throws ServiceException;
	
	*//**
	 * 根据sku编码和地址编码查找唯一的库存sku商品
	 * @param commodityCode  
	 * @return
	 * @throws ServiceException
	 *//*
	public StockCustom findStockCustomByskuCode(String commoditySkuCode,String addressCode)throws ServiceException;

	public StockCustomUsed getStockCustombyCommodityCode(String commodityCode,String addressCode);

	*//** 
	 * @param
	 * @author zhenqiang.zhang  
	 * @time 2016年5月18日 下午3:51:18 
	 * @return String
	 * @throws Exception 
	 *//* 
	public String importExcel(List<String[]> listRow) throws ServiceException, Exception;
	
	*//** 
	 * @param
	 * @author zhenqiang.zhang  
	 * @time 2016年5月18日 下午3:51:18 
	 * @return String
	 * @throws Exception 
	 *//* 
	public Map<String,Object> loadCommodity() throws ServiceException, Exception;
	
	*//**
	 * 根据sku编码查找唯一自定义非区域性的库存sku商品
	 * @param commodityCode  
	 * @return
	 * @throws ServiceException
	 *//*
	public StockCustom getStockCustomByskuCode(String commoditySkuCode)throws ServiceException;
	
	*//**
	 * 根据条件筛选出自定义库存导出
	 * @param params
	 * @return
	 * @throws ServiceException
	 *//*
	public List<StockCustom> findStockCustomExport(Map<String, Object> params)throws ServiceException;
	
	*//**
	 * 根据商品编码查找已绑定的地址
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public List<StockCustom> findAddressOrWarehouse(String commodityCode)throws ServiceException;
	
	*//**
	 * 根据sku编码和是否共享表示查找唯一的库存sku商品
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public StockCustom findStockCustomByskuCodeAndIsMany(String skuCode)throws ServiceException;
	
	*//**
	 * 根据sku编码查找库存sku商品集合
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public List<StockCustom> findStockCustomByskuCodeIsMany(String skuCode)throws ServiceException;
	
	*//**
	 * 根据sku编码查找库存sku商品数量
	 * @param skuCode
	 * @return
	 * @throws ServiceException
	 *//*
	public int getStockCustomNumByskuCode(String skuCode)throws ServiceException;
	
	*//**
	 * 根据地址编码查找对应的库存记录，并且更新仓库编码
	 * @param addressCode
	 * @param addressName
	 * @param WarehouseCode
	 * @param WarehoseName
	 * @return
	 * @throws ServiceException
	 *//*
	public String modiflyStockCustomWarehouseByAddress(String addressCode,String warehouseCode,String warehoseName)throws ServiceException;
	
	
    *//*******************************************订单流程自用，独立出来，可以调用，但是请不要乱改*******************************************************//*
	
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
	public StockUsed findStockbyCodeAndAddressCode(Map<String, Object> params)throws ServiceException;
	
	*//******************************************************************************************************************//*
	
	*//**
	 * 筛选出
	 * 1.已上架
	 * 2.商品购买类型为普通3
	 * 3.该商品下的所有sku的可购买量=0
	 * 4.该商品下的所有sku的订单的状态不等于待支付
	 * @return
	 * @throws ServiceException
	 *//*
	public List<Commodity> findNoGoodsCommodity()throws ServiceException;
}
*/
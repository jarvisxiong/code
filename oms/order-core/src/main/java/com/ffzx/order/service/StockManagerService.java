package com.ffzx.order.service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.wms.api.dto.InventoryApiVo;

/**
 * @className StockManagerService
 *
 * @author wangricheng
 * @date 2016-05-19 15:29:55
 * @version 1.0.0
 */
public interface StockManagerService extends BaseCrudService{

	/**
	 * 根据skuCode以及最小地址id获取已占用数、用户可购买量、以及当前库存总数
	 * @param skuCode       商品skuCode
	 * @param addressId    最小地址id
	 * @return
	 * data{
	 * 	 StockUsed对象
	 * }
	 * @throws ServiceException
	 */
	public ResultDto getInfobyskuCodeAndaddressId(String skuCode,String addressId) throws ServiceException;
	
	/***
	 * 出入库bubbo接口
	 * @param inventoryApiVo 对象
	 * @author richeng.wang
	 * @email richeng.wang@ffzxnet.com
	 * @date 2016年07月22日 上午9:32:18
	 * @version V1.0
	 * @return ResultDto  
	 * @throws ServiceException
	 */
	public void reduceOrStorageStockWms(InventoryApiVo inventoryApiVo) throws ServiceException;
}

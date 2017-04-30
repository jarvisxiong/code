/**
 * 
 *//*
package com.ffzx.order.mq.consumer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.mq.AbstractMessageConsumer;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.order.api.dto.Commodity;
import com.ffzx.order.api.dto.CommoditySku;
import com.ffzx.order.api.dto.StockWms;
import com.ffzx.order.api.dto.StockWmsUsed;
import com.ffzx.order.model.StockThroughLog;
import com.ffzx.order.service.CommodityService;
import com.ffzx.order.service.StockThroughLogService;
import com.ffzx.order.service.StockWmsService;
import com.ffzx.order.service.StockWmsUsedService;
import com.ffzx.wms.api.dto.InventoryApiVo;

*//**
 * @author as
 *
 *//*
@Component
public class InventoryApiVoConsumer extends AbstractMessageConsumer<List<InventoryApiVo>> {

	@Autowired
	private CommodityService commodityService;
	
	@Autowired
	StockWmsService stockWmsService;
	
	@Autowired
	StockThroughLogService stockThroughLogService;
	
	@Autowired
	StockWmsUsedService stockWmsUsedService;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void onMessage(List<InventoryApiVo> list) {
		logger.info("begin InventoryApiVoConsumer=============================");
		try {
			InventoryApiVo vo = null;
			for (int i = 0; i < list.size(); i++) {
				Map v=(Map)list.get(i);
				vo = JsonMapper.convertVal(v, InventoryApiVo.class);
				if (null != vo)
				{
					reduceOrStorageStockWms(vo);
				}
			}
		}
		catch(Exception e )
		{
			logger.info("InventoryApiVoConsumer :" + ToStringBuilder.reflectionToString(list)+e);
			throw new ServiceException(e);
		}
		logger.info("end InventoryApiVoConsumer=============================");
	}
	
	private void reduceOrStorageStockWms(InventoryApiVo inventoryApiVo)
	{
		logger.info("begin reduceOrStorageStockWms : " + ToStringBuilder.reflectionToString(inventoryApiVo));
		StockThroughLog throughLog = new StockThroughLog();
		throughLog.setThroughResult("SUCCESS");
		throughLog.setId(UUIDGenerator.getUUID());
		
		String skuCode = inventoryApiVo.getCommoditySkuCode();
	    String warehouseCode = inventoryApiVo.getWarehuseCode();
	    String warehouseName =  inventoryApiVo.getWarehuseName();
		int currStockNum = inventoryApiVo.getQty();
		int type = inventoryApiVo.getType();
		
		Map<String, Object> params = new HashMap<String, Object>();
		throughLog.setWarehouseCode(warehouseCode);
		throughLog.setWarehouseName(warehouseName);
		throughLog.setSkuCode(skuCode);
		throughLog.setThroughData(String.valueOf(currStockNum));
		try {
			params.put("skuCode", skuCode);
			params.put("warehouseCode", warehouseCode);
			// 入库
			if (1 == type) {
				throughLog.setThroughDatatype("1");
				throughLog.setThroughLevel("入库");
				// 获取商品信息
				Commodity commodity = commodityService.getCommoditybyskuCode(skuCode);
				if (null == commodity) {
					throw new Exception("没有该商品");
				}
				
				StockWms sckw = stockWmsService.findStockWmsBySkuCodeAndWarehouseCode(skuCode, warehouseCode);
				if (null == sckw) {
					StockWms stockWms = new StockWms();
					
					stockWms.setCommodity(commodity);
					stockWms.setWarehouseCode(warehouseCode);
					stockWms.setWarehouseName(warehouseName);
					CommoditySku commoditySku = new CommoditySku();
					commoditySku.setSkuCode(skuCode);
					stockWms.setCommoditySku(commoditySku);

					// 插入
					stockWms.setLastUpdateDate(new Date());
					stockWms.setId(UUIDGenerator.getUUID());
					stockWms.setCreateDate(new Date());
					stockWms.setActFlag("0");
					stockWms.setDelFlag("0");
					stockWms.setCurrStockNum(String.valueOf(currStockNum));
					stockWms.setAddressNameOrWarehouseName(warehouseName);
					stockWmsService.insertStockWms(stockWms);
					
					*//**
					 * 添加占用对象
					 *//*
					if ("0".equals(commodity.getStockLimit())){
						StockWmsUsed used = changeStockWmsUsed(stockWms);
						stockWmsUsedService.add(used);
						// 更新库存表
						stockWms.setStockWmsUsed(used);
						stockWmsService.updateByPrimaryKeySelective(stockWms);
					}
					
				} else {
					// 更新
					params.put("number", String.valueOf(currStockNum));
					stockWmsService.updateStockWmsbyCon(params);
					
					*//**
					 * 加占用表中库存总数
					 *//*
					stockWmsUsedService.updateCurrstocknumbyCon(params);
				}
			}
			// 出库
			else if (2 == type) {
				throughLog.setThroughDatatype("2");
				throughLog.setThroughLevel("出库");
				params.put("number", "-" + String.valueOf(currStockNum));
				stockWmsService.updateStockWmsbyCon(params);
				
				*//**
				 * 减占用表中库存总数
				 *//*
				stockWmsUsedService.updateCurrstocknumbyCon(params);
			}
			logger.info("end reduceOrStorageStockWms : " + ToStringBuilder.reflectionToString(inventoryApiVo));
		} catch (Exception e) {
			logger.info("InventoryApiVoConsumer.reduceOrStorageStockWms===出入库出现异常 ===》" + e);
			throughLog.setThroughResult("fail");
			throw new ServiceException(e);
		}
		stockThroughLogService.add(throughLog);
	}
	
	*//**
	 * 
	 * @param stockWms
	 * @return
	 *//*
	private StockWmsUsed changeStockWmsUsed(StockWms stockWms) {
		StockWmsUsed used = new StockWmsUsed();
		used.setId(UUIDGenerator.getUUID());
		used.setCommodity(stockWms.getCommodity());
		used.setCommoditySku(stockWms.getCommoditySku());
		used.setWarehouseCode(stockWms.getWarehouseCode());
		// used.setAddressCode(stockWms.getAddressCode());
		used.setStockUsedCount("0");
		used.setUserCanBuy(stockWms.getCurrStockNum());
		used.setStockWms(stockWms);
		used.setCurrStockNum(stockWms.getCurrStockNum());
		used.setCreateDate(new Date());
		used.setLastUpdateDate(new Date());
		return used;
	}
	
}
*/
/**
 * 雷------2016年6月14日
 * 
 */
package com.ffzx.promotion.api.service.consumer;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ffzx.bms.api.dto.StockUsed;
import com.ffzx.bms.api.service.OrderStockManagerApiService;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;

/**
 * @author 雷
 * 
 *         库存管理消费类
 */
@Component("stockManagerApiConsumer")
public class StockManagerApiConsumer {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(StockManagerApiConsumer.class);

//	@Autowired
//	private StockManagerApiService stockManagerApiService;

	@Autowired
	private OrderStockManagerApiService orderStockManagerApiService;
	/**
	 * 
	 * 雷------2016年6月14日 根据skuCode获取可购买数量
	 * 
	 * @param skuCodeList
	 *            list skuCode集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, StockUsed> getStockbySkuCode(List<String> skuCodeList) {
		logger.debug("StockManagerApiConsumer-getStockbySkuCode=》dubbo调用：StockManagerApiService.getStockbySkuCode - Start");
		ResultDto result;
		try {
			result = orderStockManagerApiService.getStockbySkuCode(skuCodeList);
			if ((ResultDto.OK_CODE).equals(result.getCode())) {
				return (Map<String, StockUsed>) result.getData();
			}else{
				logger.error("StockManagerApiConsumer-getStockbySkuCode=》dubbo调用：StockManagerApiService.getStockbySkuCode -失败" );
				
			}
		} catch (Exception e) {
			logger.debug("StockManagerApiConsumer-getStockbySkuCode=》dubbo调用：StockManagerApiService.getStockbySkuCode -失败" , e);
			throw new ServiceException(e);
//			return null;
		}
		return null;

	}

}

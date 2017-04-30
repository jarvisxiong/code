package com.ffzx.promotion.api.service.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commodity.api.dto.CommoditySku;
import com.ffzx.commodity.api.service.CommoditySkuApiService;
import com.ffzx.promotion.exception.CallInterfaceException;


/**
 * cims dubbo CommoditySkuApiService 消费者配置
 * 
 * @author lushi.guo
 * @date 2016年5月17日 
 * @version 1.0
 */
@Service("commoditySkuApiConsumer")
public class CommoditySkuApiConsumer {
	// 记录日志
		private final static Logger logger = LoggerFactory.getLogger(CommoditySkuApiConsumer.class);
		@Resource
		private RedisUtil redisUtil;
		@Autowired
		private CommoditySkuApiService commoditySkuApiService;
		
		/********
		 * 返回所有SKU商品
		 * @param params
		 * @return
		 * @throws ServiceException
		 */
		public List<CommoditySku> findList(Map<String, Object> params) throws ServiceException{
			logger.debug("CommoditySkuApiConsumer-findList=》dubbo调用：CommoditySkuApiService.findList - Start");
			ResultDto resDto = commoditySkuApiService.findList(params);
			logger.debug("CommoditySkuApiConsumer-findList=》dubbo调用：CommoditySkuApiService.findList -",resDto.getMessage());
			List<CommoditySku> list = null;
			if (resDto.getCode().equals(ResultDto.OK_CODE)) {
				list=(List<CommoditySku>) resDto.getData();
			}
			logger.debug("CommodityApiConsumer-findList=》dubbo调用：CommodityApiService.findList - End");
			return list;
		}

		/**
		 * 根据单个商品条形码查询非属性商品
		 * @throws ServiceException 
		 */
		public CommoditySku getCommoditySku(String barcode) throws ServiceException{
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("barcode", barcode);
			logger.debug("CommodityApiConsumer-getCategory=》dubbo调用：CategoryApiService.getCategory - Start");
			ResultDto resDto =  commoditySkuApiService.findList(params);//获取数据categoryApiService.getList(barcode);
			logger.debug("CommodityApiConsumer-getCategory=》dubbo调用：CategoryApiService.getCategory -",resDto.getMessage());
			List<CommoditySku> list = null;
			if (resDto.getCode().equals(ResultDto.OK_CODE)) {
				list=(List<CommoditySku>) resDto.getData();
			}
			if(list==null || list.size()==0 || list.get(0)==null || list.get(0).getId()==null)
				return null;
			logger.debug("CommodityApiConsumer-getCategory=》dubbo调用：CategoryApiService.getCategory - End");
			return list.get(0);
		}
		
		/** @desc 根据skuid集合获取sku数据
		 * @param skuIdList 获取一个skuid集合
		 * @return List<CommoditySku>
		 */
		public List<CommoditySku> getListCommoditySkus(List<String> skuIdList){
			logger.debug("CommoditySkuApiConsumer-getListCommoditySku=》 获取单个属性sku商品   - BEGIN");
			ResultDto result = null;
			try {
			 	 result =commoditySkuApiService.findSku(skuIdList, "1");// commodityApiService;
			} catch (Exception e) {
				logger.error("CommoditySkuApiConsumer-getListCommoditySku=》 获取单个属性sku商品   " , e);
				throw new CallInterfaceException(e.getMessage());
			}
			if(result.getCode().equals(ResultDto.OK_CODE) ){
				return (List<CommoditySku>) result.getData();
			}else{
				logger.error("CommoditySkuApiConsumer-getListCommoditySku=》 获取单个属性sku商品   " , result.getMessage());
				throw new CallInterfaceException(result.getMessage());
			}
		}
}

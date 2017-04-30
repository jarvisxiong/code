/**
 * 
 */
package com.ffzx.promotion.api.service.consumer;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.service.ActivityCommoditySkuApiService;
import com.ffzx.promotion.api.service.ActivityManagerApiService;
import com.ffzx.promotion.exception.CallInterfaceException;


/**
 * @Description: 获取活动消费者
 * @author zi.luo
 * @email  zi.luo@ffzxnet.com
 * @date 2016年5月18日 下午5:02:22
 * @version V1.0 
 *
 */
@Service("activityApiConsumer")
public class ActivityApiConsumer {
	@Resource
	private RedisUtil redisUtil;
	
	@Autowired
	private ActivityManagerApiService activityManagerApiService;
	
	@Autowired
	private ActivityCommoditySkuApiService activityCommoditySkuApiService;
	
	
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(ActivityApiConsumer.class);
	/**
	 * 获取selectActivitySkuPrice非批发数据
	 * @param activityIdList
	 * @return
	 */
	public List<ActivityCommoditySku>  selectActivitySkuPrice(List<Object> activityIdList) {
		logger.debug("ActivityApiConsumer-selectActivitySkuPrice=》获取活动（非批发）数据 - BEGIN");
		ResultDto result = null;
		try {
			
		 	 result =activityCommoditySkuApiService.selectActivitySkuPrice(activityIdList);
		} catch (Exception e) {
			logger.error("ActivityApiConsumer-selectActivitySkuPrice-=》获取活动（非批发）数据 " , e);
			throw new CallInterfaceException(e.getMessage());
		}
		
		if(result.getCode().equals(ResultDto.OK_CODE) ){

			
			return (List<ActivityCommoditySku>) result.getData();
		}else{
			logger.error("ActivityApiConsumer-selectActivitySkuPrice-=》获取活动（非批发）数据 " , result.getMessage());
			throw new CallInterfaceException(result.getMessage());
		}
	}
	//根据数量  获取价格
	public  BigDecimal  getPifaPrice(String commodityNo,int num ){
		
		
		logger.debug("ActivityApiConsumer-getPifaPrice=》根据数量  获取价格 - BEGIN");
		ResultDto result = null;
		try {
			
		 	 result =activityCommoditySkuApiService.getPifaPrice(commodityNo, num);
		} catch (Exception e) {
			logger.error("ActivityApiConsumer-getPifaPrice=》根据数量  获取价格 " , e);
			throw new CallInterfaceException(e.getMessage());
		}
		
		if(result.getCode().equals(ResultDto.OK_CODE) ){
			return (BigDecimal) result.getData();
		}else{
			logger.error("ActivityApiConsumer-=》根据数量  获取价格 " , result.getMessage());
			throw new CallInterfaceException(result.getMessage());
		}
	}
	
}

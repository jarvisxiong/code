package com.ffzx.promotion.api.service.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commodity.api.dto.Category;
import com.ffzx.commodity.api.dto.Commodity;
import com.ffzx.commodity.api.service.CommodityApiService;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.UpdateActivityIndex;
import com.ffzx.promotion.util.AppUtils;


/**
 * cims dubbo CategoryApiService 消费者配置
 * 
 * @author lushi.guo
 * @date 2016年5月17日 
 * @version 1.0
 */
@Service("commodityApiConsumer")
public class CommodityApiConsumer {
	// 记录日志
		private final static Logger logger = LoggerFactory.getLogger(CommodityApiConsumer.class);
		@Resource
		private RedisUtil redisUtil;
		@Autowired
		private CommodityApiService commodityApiService;
		@Autowired
		private UpdateActivityIndex updateActivityIndex;
		/********
		 * 返回所有商品
		 * @param page
		 * @param orderByField
		 * @param orderBy
		 * @param params
		 * @return
		 * @throws ServiceException
		 */
		public Map<String, Object> findList(Page page, String orderByField, String orderBy, Map<String, Object> params) throws ServiceException{
			logger.debug("CommodityApiConsumer-findList=》dubbo调用：CommodityApiService.findList - Start");
			ResultDto resDto = commodityApiService.findList(page, orderByField, orderBy, params);
			logger.debug("CommodityApiConsumer-findList=》dubbo调用：CommodityApiService.findList -",resDto.getMessage());
			List<Commodity> list = null;
			Map<String, Object> paramValue=null;
			if (resDto.getCode().equals(ResultDto.OK_CODE)) {
				paramValue=new HashMap<>();
				paramValue=(Map<String, Object>) resDto.getData();
			}
			logger.debug("CommodityApiConsumer-findList=》dubbo调用：CommodityApiService.findList - End");
			return paramValue;
		}
		/*****
		 * 批量更新商品状态(新增、删除、批量删除)
		 * @param commodityCode 商品code
		 * @param type
		 * @return
		 * @throws ServiceException
		 */
		public Integer updateCommodityTypeyByCode(Vector<String> commodityCode,String type)throws ServiceException{
			logger.debug("CommodityApiConsumer-updateCommodityBuyType=》dubbo调用：CommodityApiService.findList - Start");
			ResultDto resDto=commodityApiService.updateBuyType(commodityCode, type);
			logger.debug("CommodityApiConsumer-updateCommodityBuyType=》dubbo调用：CommodityApiService.findList -",resDto.getMessage());
			if (resDto.getCode().equals(ResultDto.OK_CODE)) {
				//add by ying.cai 2016-11-18 给ES推送信息
				if(ActivityTypeEnum.NEWUSER_VIP.getValue().equals(type) || ActivityTypeEnum.WHOLESALE_MANAGER.getValue().equals(type)){
					for (String code : commodityCode) {
						updateActivityIndex.removeSpecialActivity(code, type);
					}
				}
				return  ServiceCode.SUCCESS;
			}else{
				return ServiceCode.FAIL;
			}
		}
		
		/*****
		 * 批量更新商品状态(导入)
		 * @param commoditySkuCode 商品SkuCode
		 * @param type
		 * @return
		 * @throws ServiceException
		 */
		public Integer updateCommodityBuyType(Vector<String> commoditySkuCode,String type)throws ServiceException{
			logger.debug("CommodityApiConsumer-updateCommodityBuyType=》dubbo调用：CommodityApiService.findList - Start");
			ResultDto resDto=commodityApiService.updateBuyTypeBySkucode(commoditySkuCode, type);
			logger.debug("CommodityApiConsumer-updateCommodityBuyType=》dubbo调用：CommodityApiService.findList -",resDto.getMessage());
			if (resDto.getCode().equals(ResultDto.OK_CODE)) {
				return  ServiceCode.SUCCESS;
			}else{
				return ServiceCode.FAIL;
			}
		}
}

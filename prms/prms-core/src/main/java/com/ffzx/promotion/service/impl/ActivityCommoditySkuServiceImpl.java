package com.ffzx.promotion.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.bms.api.dto.StockUsed;
import com.ffzx.commerce.framework.constant.RedisPrefix;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commodity.api.dto.CommoditySku;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.consumer.CommoditySkuApiConsumer;
import com.ffzx.promotion.api.service.consumer.StockManagerApiConsumer;
import com.ffzx.promotion.mapper.ActivityCommoditySkuMapper;
import com.ffzx.promotion.model.CommoditySkuTransform;
import com.ffzx.promotion.service.ActivityCommoditySkuService;

/**
 * @Description: TODO
 * @author yuzhao.xu
 * @email yuzhao.xu@ffzxnet.com
 * @date 2016年5月11日 下午4:25:32
 * @version V1.0
 * 
 */

@Service("activityCommoditySkuService")
public class ActivityCommoditySkuServiceImpl extends BaseCrudServiceImpl implements ActivityCommoditySkuService {

	@Resource
	private ActivityCommoditySkuMapper activityCommoditySkuMapper;
	@Resource(name = "commoditySkuApiConsumer")
	private CommoditySkuApiConsumer commoditySkuApiConsumer;
	@Resource(name = "stockManagerApiConsumer")
	private StockManagerApiConsumer stockManagerApiConsumer;
    @Autowired
    private RedisUtil redis;

	@Override
	public CrudMapper init() {
		return activityCommoditySkuMapper;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServiceCode saveCommdity(ActivityManager activityManager, ActivityCommodity activityCommodity, List<ActivityCommoditySku> commoditySkuList, String viewStatus, ActivityTypeEnum enums) throws ServiceException {
		int result = 0;
		if (commoditySkuList!=null && commoditySkuList.size()!=0) {
			if (viewStatus.equals("edit") || viewStatus.equals("saveandedit")) {
				// 修改商品设置先删除之前对应的商品 SKU 再新增
				Map<String, Object> params = new HashMap<>();
				params.put("activityCommodity", activityCommodity);
				// 获取所有商品SKU
				List<ActivityCommoditySku> activityCommoditySkuList = this.activityCommoditySkuMapper.selectByPage(null, null, null, params);
				for (ActivityCommoditySku sku : activityCommoditySkuList) {
					this.activityCommoditySkuMapper.deleteByPrimarayKeyForModel(sku);
				}
			}

			for (ActivityCommoditySku sku : commoditySkuList) {
				sku.setActivity(activityManager);
				sku.setActivityCommodity(activityCommodity);
				sku.setActivityNo(activityCommodity.getActivityNo());
				sku.setId(UUIDGenerator.getUUID());
				sku.setActivityType(enums);
				result = this.activityCommoditySkuMapper.insertSelective(sku);
			}
			setRedisDate(activityCommodity.getId(),commoditySkuList);
		}
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	private void setRedisDate(String activityCommodityId, List<ActivityCommoditySku> skuList) {
		for(ActivityCommoditySku sku:skuList){
			//商品限购量存入缓存
			redis.set(PrmsConstant.getCommoditySkuLimitKey(activityCommodityId, sku.getCommoditySkuNo()), sku.getLimitCount());				
			//活动商品价格存入缓存
			redis.set(PrmsConstant.getPriceKey(activityCommodityId, sku.getCommoditySkuNo()),sku.getActivityPrice());						
		}
	}
	@Override
	public List<ActivityCommoditySku> getSkuList(String commoditySkuId,String commodityno) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commoditySkuId", commoditySkuId);
		map.put("orcommodityno", commodityno);
		return activityCommoditySkuMapper.selectByParams(map);
	}

	@Override
	public List<ActivityCommoditySku> findActivityCommoditySkuList(Map<String, Object> params) throws ServiceException {

		return this.activityCommoditySkuMapper.selectByPage(null, null, null, params);
	}

	/**
	 * 雷-----2016年6月14日 根据商品code获取sku (non-Javadoc) 注：后期需重构
	 * 
	 * @see com.ffzx.promotion.service.ActivityCommoditySkuService#getCommoditySku(java.lang.String)
	 */
	@Override
	public List<CommoditySkuTransform> getCommoditySku(String commodityCode) {
		//执行操作

		List<CommoditySkuTransform> skuTransformList= new ArrayList<CommoditySkuTransform>();
			try {
				Map<String, Object> params=new HashMap<>();
				params.put("commodityCode", commodityCode);			
				List<CommoditySku> skuList=commoditySkuApiConsumer.findList(params);
				if(StringUtil.isNotNull(skuList)){
					Map<String,CommoditySku> skuCodeList=new HashMap<String,CommoditySku>();
					for(CommoditySku sku:skuList){
						skuCodeList.put(sku.getSkuCode(), sku);
					}
					//有库存入库的
					Map<String, StockUsed> resultMap = this.stockManagerApiConsumer.getStockbySkuCode(new ArrayList(skuCodeList.keySet()));
					for (String skuCode : resultMap.keySet()) {
						  StockUsed stockUsed = (StockUsed) resultMap.get(skuCode);
						  for(CommoditySku sku : skuList){
							  if(sku.getSkuCode().equals(skuCode)){
								  skuCodeList.remove(skuCode);
								 addCommoditySkuTransform(skuTransformList, sku, stockUsed.getUserCanBuy());
								  break;
							  }
						  }
					}
					//无库存的（add
					for (String skuCode : skuCodeList.keySet()) {
						addCommoditySkuTransform(skuTransformList, skuCodeList.get(skuCode), "0");
					}
				}
			}catch (Exception e) {
				logger.error("ActivityCommoditySkuServiceImpl.getCommoditySku() Exception=》-获取sku-", e);
				throw new ServiceException(e);
			}

		return skuTransformList;
	}
	/**
	 * 为商品加一个库存数量
	 * @param skuTransformList
	 * @param sku
	 * @param userCanBuy
	 */
	private void  addCommoditySkuTransform(List<CommoditySkuTransform> skuTransformList,CommoditySku sku,String userCanBuy){
		 CommoditySkuTransform skuTransform = new CommoditySkuTransform();
		  skuTransform.setBarcode(sku.getBarcode());
		  skuTransform.setCommodityAttributeValues(sku.getCommodityAttributeValues());
		  skuTransform.setFavourablePrice(sku.getFavourablePrice());
		  skuTransform.setId(sku.getId());
		  skuTransform.setSkuCode(sku.getSkuCode());
		  skuTransform.setUserCanBuy(userCanBuy);
		  skuTransformList.add(skuTransform);
	}

	@Override
	public List<ActivityCommoditySku> findActivitySkuByActivityId(String activityId,String commoditySkuIds,String commodityCode) {
		return activityCommoditySkuMapper.findActivitySkuByActivityId(activityId,buildSql(commoditySkuIds),commodityCode);
	}
	
	private static String buildSql(String ids){
		String arr [] = ids.split(",");
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		for (String str : arr) {
			builder.append("'").append(str).append("'").append(",");
		}
		if(builder.length()>0){
			builder.deleteCharAt(builder.length()-1);
		}
		builder.append(")");
		return builder.toString();
	}
	
}
package com.ffzx.promotion.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.commodity.api.dto.CommoditySku;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.model.CommoditySkuTransform;

/**
* @Description: TODO
* @author yuzhao.xu
* @email  yuzhao.xu@ffzxnet.com
* @date 2016年5月11日 下午4:25:32
* @version V1.0 
*
*/
public interface ActivityCommoditySkuService extends BaseCrudService {

	/*********
	 * 商品设置新增
	 * @param activityCommdity
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode saveCommdity(ActivityManager activityManager,ActivityCommodity activityCommodity,List<ActivityCommoditySku> commoditySkuList,String viewStatus,ActivityTypeEnum enums)throws ServiceException;
	
	/**
	 * 获取sku的数据，以及关联父类的开始时间和结束时间
	 * @param commoditySkuId  商品sku的skuid
	 * @return
	 */
	public List<ActivityCommoditySku> getSkuList(String commoditySkuId,String commodityno);
	
	/******
	 * 根据各种参数获取商品设置SKU集合
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<ActivityCommoditySku> findActivityCommoditySkuList(Map<String, Object> params)throws ServiceException;

	/**
	 * 雷------2016年6月14日
	 * 根据商品code获取sku
	 * @param commodityCode
	 * @return
	 */
	public List<CommoditySkuTransform> getCommoditySku(String commodityCode);

	/***
	 * 根据活动ID获得该活动下的所有sku子活动的数据
	 * @param activityId 活动ID
	 * @param commoditySkuIds 商品SkuId集合
	 * @param commodityCode 商品编码
	 * @return
	 * @date 2016年6月16日 下午4:23:40
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public List<ActivityCommoditySku> findActivitySkuByActivityId(String activityId,String commoditySkuIds,String commodityCode);
	

}
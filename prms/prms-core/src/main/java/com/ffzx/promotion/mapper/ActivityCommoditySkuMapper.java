package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;

/**
 * activity_commdity_item数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-10 15:56:22
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface ActivityCommoditySkuMapper extends CrudMapper {

	public int deleteByPrimaryKeycommodity(@Param("commodityid")String commodityid);
	
	public ActivityCommoditySku selectByParamsAll(@Param("params") Map<String, Object> params);
	
	public List<ActivityCommoditySku> selectActivitySkuPrice(@Param("activityIdList")List<Object> activityIdList);

	/***
	 * 根据活动ID获得该活动下的所有sku子活动的数据
	 * @param activityId 活动ID
	 * @param commoditySkuIds 商品SkuId集合
	 * @param commodityCode 商品编码
	 * @return
	 * @date 2016年6月16日 下午4:30:14
	 * @author ying.cai
	 * @param commoditySkuIds 
	 * @email ying.cai@ffzxnet.com
	 */
	public List<ActivityCommoditySku> findActivitySkuByActivityId(@Param("activityId")String activityId,
			@Param("commoditySkuIds")String commoditySkuIds,
			@Param("commodityCode")String commodityCode);
	
}
package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommodityVo;
import com.ffzx.promotion.model.PreSaleTag;
import com.ffzx.promotion.api.dto.ActivityEsVo;

/**
 * activity_commdity数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-10 15:56:22
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface ActivityCommodityMapper extends CrudMapper {

	/**
	* 更新推荐商品信息
	* @Title: updateByAcrivityId 
	* @Description: 根据 活动id 更新推荐商品信息
	* @return int    返回类型
	 */
	public int updateRecommendInfo(ActivityCommodity activityCommodity);
	

	/**
	* 查询批发活动商品列表
	* @Title: selectCommodityByParams 
	* @param params 查询条件
	* @param page 分页信息
	* @return List<ActivityCommodity>    返回类型
	 */
	public List<ActivityCommodity> selectWholeSaleCommodityByParams(@Param("page") Page page, @Param("params") Map<String, Object> params);
	
	/**
	* 获取活动商品列表(普通、新用户、预售、抢购)
	* @Title: selectActivityCommodityByParams 
	* @param params 查询条件
	* @param page 分页信息
	* @return List<ActivityCommodity>    返回类型
	 */
	public List<ActivityCommodity> selectActivityCommodityByParams(@Param("page") Page page, @Param("params") Map<String, Object> params);

	/**
	 * 获取预售抢购
	 * @param params 查询条件
	 * @param page 分页信息
	 * @param releaseStatus 发布type
	 * @param activityType预售抢购
	 * @param categoryId类目id
	 * @param uid会员id
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @date 2016年6月4日 下午5:37:11
	 * @version V1.0 
	 */
	public List<ActivityCommodity>  selectSaleBuy(@Param("page") Page page, @Param("params") Map<String, Object> params);
	
	/***
	 * 带预售标签的（抢购预售详情）
	 * @param page
	 * @param params
	 * @return
	 */
	public List<ActivityCommodity>  selectNewSaleBuy(@Param("page") Page page, @Param("params") Map<String, Object> params);
	/**
	 * 获取预售类目信息
	 * @param params 查询条件 
	 * @param activityType预售
	 * @param releaseStatus 发布type
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @date 2016年6月4日 下午5:37:11
	 * @version V1.0 
	 */
	public List<ActivityCommodity> selectSaleCatory(@Param("params") Map<String, Object> params);
	
	
	/**
	 * 获取正在进行对应活动列表
	 */
	public <T> List<T> getActivityList(@Param("page") Page page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);
	
	/***
	 * 活动以及商品搜索，用用户APP端列表页和搜索结果页展示
	 * @param params 查询参数
	 * @return
	 * @date 2016年6月3日 下午2:57:16
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * */
	public List<Map<String,Object>> searchCommodity(@Param("page")Page page,@Param("params")Map<String,Object> params);
	
	/***
	 * 活动以及商品搜索，用用户APP端列表页和搜索结果页展示
	 * @param params 查询参数
	 * @return
	 * @date 2016年6月3日 下午2:57:16
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * */
	public List<Map<String,Object>> searchCommodity(@Param("page")Page page,@Param("params")Map<String,Object> params,@Param("simpleCountSql") Boolean simpleCountSql);
	
	/***
	 * 根据上平类目获取同类商品并根据历史销量排序
	 * @param params 查询参数
	 * @return
	 * @date 2016年6月3日 下午19:57:16
	 * @author yinglong.huang 
	 * @email yinglong.huang@ffzxnet.com
	 * */
	public List<Map<String,Object>> searchCommodityByCidAndSalecount(@Param("page")Page page,@Param("params")Map<String,Object> params);

	/**
	 * 获取活动详细信息
	 */
	public ActivityCommodity selectByCommodityPrimaryKey(@Param("params")  Map<String,Object> params);
	/**
	 * 更新活动商品抢购关注人数
	 * @param activityCommodity  活动
	 * @author yuzhao.xu
	 * @date 2016年6月3日 下午5:57:16
	 */
	public void updatefollowCount(ActivityCommodity activityCommodity);
	
	/****
	 * 更新已经抢完
	 * @param id
	 */
	public int  updateActivityCommodityStatus(@Param("id")String id);
	
	/****
	 * 更新进行中
	 * @param id
	 */
	public int  updateActivityCommodityStatusIng(@Param("id")String id);
	
	/****
	* 普通活动推荐商品列表
	* @Title: selectRecommend 
	* @param params
	* @return List<ActivityCommodity>    返回类型
	 */
	public List<ActivityCommodity> selectRecommend(@Param("params")Map<String,Object> params);


	/**
	 * @author 雷
	 * @date 2016年6月23日
	 * @Title: selectByAdvert
	 * @Description: 活动商品详情选择器
	 * @param @param pageObj
	 * @param @param params
	 * @param @return    
	 * @return List<ActivityCommodity>
	 * @throws
	 */
	public List<ActivityCommodityVo> selectByAdvert(@Param("page")Page pageObj, @Param("params")Map<String, Object> params);
	
	
	/****
	* 通过 活动id、商品id获取活动商品信息
	* @param params
	* @return     
	* @return ActivityCommodity    返回类型
	 */
	public ActivityCommodity selectActivityByParams(@Param("params")Map<String, Object> params);
	

	/**
	 * 获取预售抢购
	 * @param params 查询条件
	 * @param page 分页信息
	 * @param releaseStatus 发布type
	 * @param activityType抢购
	 * @param activityId 活动管理id
	 * @param uid会员id
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @date 2016年8月23日 下午5:37:11
	 * @version V1.0 
	 */
	public List<ActivityCommodity>  selectSaleList(@Param("page") Page page, @Param("params") Map<String, Object> params);
	
	
	/**
	 * 获取设置预售标签的而商品
	 */
	public List<ActivityCommodity> getActivityCommodityByPresaleTag(@Param("params")PreSaleTag params);
	
	/**
	 * 修改商品时间
	 */
	public int updateCommodityDate(@Param("params") Map<String, Object> params);
	
	/**
	 * 获取抢购活动商品时间重叠数据
	 */
	public List<ActivityCommodity> selectCommodityByDate(@Param("params") Map<String, Object> params);
	
	/***
	 * 根据买赠商品查询是否参加普通活动
	 * @param commodity
	 * @return
	 */
	public List<ActivityCommodity> getActivityCommodityByGive(@Param("commodity")ActivityCommodity commodity);
	
	/***
	 * 搜索引擎数据提供，根据商品id获取活动信息
	 * @param goodsId
	 * @return
	 * @date 2016年9月12日 下午4:39:46
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public List<ActivityEsVo> findActivityForESByCommodityId(@Param("goodsId")String goodsId);

	
	/***
	 * 获取指定activityMamageid下的活动商品明细
	 * @param activityManagerId
	 * @date 2016年9月14日 上午10:54:54
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public List<ActivityEsVo> findActivityGoodsItemByMid(@Param("params")Map<String, Object> params);

	/**
	 * 删除活动商品和活动sku商品,普通商品主健id集合
	 */
	public int deleteActivityAllSku(List<String> commoditys);

	/**
	 * 查询活动信息和主商品类型
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<ActivityCommodity> selectByParamsAndMianCommodity(Map<String, Object> params)throws ServiceException;
	
	/**
	 * 根据过滤条件获取活动详细信息
	 * @param params 查询参数
	 * @date 2016年12月8日 下午13:58:16
	 * @author sheng.shan 
	 * @email sheng.shan@ffzxnet.com
	 */
	public ActivityCommodity selectActivityCommodityByCommodityId(@Param("params")  Map<String,Object> params);
}
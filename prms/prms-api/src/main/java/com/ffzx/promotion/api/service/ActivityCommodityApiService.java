package com.ffzx.promotion.api.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * ActivityCommdityApi数据库操作接口
 * 
 * @author sheng.shan
 * @email  sheng.shan@ffzxnet.com
 * @date 2016年5月28日 下午5:25:32
 */
public interface ActivityCommodityApiService extends BaseCrudService {
	
	/**
	* 获取活动推荐Banner列表
	* @Title: findActivityRecommendList 
	* @param activityId 活动Id
	* @return ResultDto    返回类型
	 */
	public ResultDto findActivityRecommendList(String activityId);
	
	/**
	* 获取批发商品列表
	* @Title: findWholeSaleList 
	* @param actityId 活动id
	* @param sort 排序     1:销量，2：价格从高到低，3：价格从低到高
	* @param cityId 城市id
	* @param page 页码
	* @param pageSize 每页显示数
	* @param addressCode 县级地址编码
	* @param warehouseCode 仓库编码
	* @return ResultDto    返回类型
	 */
	public ResultDto findWholeSaleList(String activityId, String sort, 
			String cityId, int page, int pageSize, String addressCode, String warehouseCode);
	
	
	/**
	* 普通活动、新用户活动商品列表
	* @Title: findActivityCommodityList 
	* @param actityId 活动id
	* @param sort 排序   price:按价格排序；"":按销售量排序
	* @param desc 是否倒序  1:倒序 ；0:顺序（默认）
	* @param cityId 城市id
	* @param page 页码
	* @param pageSize 每页显示数
	* @param addressCode 县级地址编码
	* @param warehouseCode 仓库编码
	* @return ResultDto    返回类型
	 */
	public ResultDto findActivityCommodityList(String activityId, String sort, String desc, 
			String cityId, int page, int pageSize, String addressCode, String warehouseCode);
	
	/**
	 * 获取抢购，预售的商品，分页* 
	
	 * @param params 查询条件
	 * @param page 分页信息
	 * @param releaseStatus 发布type
	 * @param activityType预售抢购
	 * @param categoryId类目id
	 * @param uid会员id
	* @param pageSize 每页显示数
	* @return     
	* @return ResultDto    返回类型
	 */
	public ResultDto selectSaleBuy(Map<String, Object> map, int page, int pageSize);
	
	public ResultDto selectNewSaleBuy(Map<String, Object> map, int page, int pageSize);

	/**
	 * 获取预售类目
	* 获取预售类目信息
	 * @param params 查询条件 
	 * @param activityType预售
	 * @param releaseStatus 发布type
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @date 2016年6月4日 下午5:37:11
	 * @version V1.0 
	* @return ResultDto    返回类型
	 */
	public ResultDto selectSaleCatory(Map<String, Object> map);
	
	/***
	 * 新需求有预售类目的接口
	 * @param map
	 * @return
	 */
	public ResultDto selectNewSaleCatory(Map<String, Object> map);
	/**
	 *  TODO 获取活动商品详细数据
	 *  @param  params  uid  会员id activityId活动id
	 * @date 2016年6月3日 下午7:52:55
	 * @author yz.x
	 */
	public ResultDto getActivityCommodity(Map<String,Object> params);
	/**
	* TODO(这里用一句话描述这个方法的名称)
	* @Title: updatePanicBuyRemind 
	* @param activityId 活动Id
	* @param commodityId 商品Id
	* @param memberId 会员Id
	* @param isRemind 是否提醒
	* @return ResultDto    返回类型
	 */
	public ResultDto updatePanicBuyRemind(String activityId, String commodityId, String memberId, String isRemind);

	/***
	 * 商品以及活动搜索，用于APP端商品列表展示以及搜索列表展示
	 * @param cid 商品类目ID
	 * @param title 搜索关键字
	 * @param sort 排序方式
	 * @param desc 是否倒序
	 * @param page 页码
	 * @param pageSize 每页大小
	 * @param addressCode 县级地址编码
	 * @param warehouseCode 仓库编码
	 * @return
	 * @date 2016年6月3日 下午2:52:55
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public ResultDto searchCommodityAndActivity(String cid, String title, String sort, int desc,
			int page, int pageSize,String addressCode,String warehouseCode);
	
	/***
	 * 根据上平类目获取同类商品并根据历史销量排序
	 * @param params 查询参数
	 * @return
	 * @date 2016年6月3日 下午19:57:16
	 * @author yinglong.huang 
	 * @email yinglong.huang@ffzxnet.com
	 * */
	public ResultDto searchCommodityByCidAndSaleCount(Page pageObj,Map<String, Object> params) ;
	
	/**
	* TODO(更新抢购的关注人数）
	* @Title: updatePanicBuyRemind 
	* @param activityId 活动Id
	* @param num 活动增加人数
	* @return ResultDto    返回类型 true，false
	 */
	public ResultDto updatefollowCount(String activityId,Integer followCountAdd);
	

	
	/**
	* TODO(插入极光推送scheduleId)
	* @Title: updatePanicBuyScheduleId 
	* @param activityId 活动Id
	* @param commodityId 商品Id
	* @param memberId 会员Id
	* @param scheduleId 极光推送id
	* @return ResultDto    返回类型
	* 柯典佑
	 */
	public ResultDto updatePanicBuyScheduleId(String activityId, String commodityId, String memberId, String scheduleId);
	
	/***
	 * 查询活动提醒
	 * @param activityId 
	 * @param commodityId 
	 * @param memberId
	 * @return
	 * @author 柯典佑 
	 * */
	public ResultDto getPanicBuyRemind(String activityId, String commodityId, String memberId);
	
	/***
	 * 查询用户统一时间的活动推送数量
	 * @param memberId 
	 * @param startDate  活动时间
	 * @return
	 * @author 柯典佑 
	 * */
	public ResultDto countRemindByMember(String memberId, Date startDate);
	
	/**
	 * 删除所有指定活动商品
	 * @param commodityCode  普通商品编码
	 * @return
	 */
	public ResultDto deleteActivityCommodity(String commodityCode);
	
	/**
	 * 根据params过滤条件获取活动商品信息
	 * @param params 查询参数
	 * @date 2016年12月8日 下午14:00:16
	 * @author sheng.shan 
	 * @email sheng.shan@ffzxnet.com
	 */
	public ResultDto findActivityCommodity(Map<String,Object> params);

}
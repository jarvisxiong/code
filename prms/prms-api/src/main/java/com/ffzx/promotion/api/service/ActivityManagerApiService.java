package com.ffzx.promotion.api.service;

import java.util.Date;
import java.util.List;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.ShoppingCartVo;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;

/**
 * 活动相关dubbo接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月11日 下午4:25:32
 */

 /**
 * @Description: TODO
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年8月23日 下午5:28:28
 * @version V1.0 
 *
 */

 /**
 * @Description: TODO
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年8月23日 下午5:28:30
 * @version V1.0 
 *
 */

 /**
 * @Description: TODO
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年8月23日 下午5:28:38
 * @version V1.0 
 *
 */
public interface ActivityManagerApiService extends BaseCrudService {

	
	/**
	 * 获取活动列表 
	 * @param page   当前页码    是    int
	 * @param pageSize   当前页面大小    是    int
	 * @param type   活动类型   是    ActivityTypeEnum
	 * @return
	 */
	public ResultDto getActivityList(int page, int pageSize, ActivityTypeEnum type, String actStatus);
	
	
	/**
	 * 刷新购物车记录状态
	 * @param cartList
	 * @return
	 */
	public ResultDto refreshCartStatus(List<ShoppingCartVo> cartList, String cityId);
	
	/****
	 * 根据优惠券ID获取指定的商品或者类目
	 * @param couponId
	 * @return
	 */
	public ResultDto selectActivityCategory(String couponId);
	
	/****
	 * 根据活动商品id修改已抢完状态
	 * @param activityCommodityId
	 * @return
	 */
	public ResultDto updateActivityCommodityStatus(String activityCommodityId);
	
	/****
	 * 根据活动商品id修改进行中状态
	 * @param activityCommodityId
	 * @return
	 */
	public ResultDto updateActivityCommodityStatusIng(String activityCommodityId);
	
	/****
	 * 根据活动商品编号和购买总量获取批发价格
	 * @param commodityNo
	 * @param whosaleCount
	 * @return
	 */
	public ResultDto getWhosaleCommodityPrice(String commodityNo,int whosaleCount);
	
	/****
	 * 根据活动ID 获取活动信息
	 * @param activityCommodityId
	 * @return
	 */
	public ResultDto getActivityCommodity(String activityCommodityId);
	
	/***
	* 根据活动管理id 获取活动商品管理列表
	* @param activityManagerIds 活动管理id列表
	* @return ResultDto    返回类型
	 */
	public ResultDto selectActCommodityList(List<String> activityManagerIds);
	
	/***
	 * 根据活动ID 获取活动详情集合
	 * @param activityCommodityId
	 * @return
	 */
	public ResultDto getActivityCommoditySkuList(String activityCommodityId,String skuId);
	/**
	 * @Description: 获取app抢购类目列表
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @date 2016年8月22日 上午11:09:23
	 * @version V1.0 
	 *
	 */
	public ResultDto findAppSaleCategory();
	/**
	 * 获取用户购买数量+增量值+即将开始不加
	 * @param startDate  开始时间
	 * @param endDate  结束时间
	 * @param commodityNo  activityCommodity.getCommodity().getCode()  
	 * @param activityCommodityItemId   activityCommodity.getId()
	 * @param saleIncrease增量value
	 */
	public String getPresaleCount(Date startDate,Date endDate,String commodityNo,
			String activityCommodityItemId,Integer saleIncrease);
	/**
	 * 获取提醒的集合
	 * @param activityMangerId
	 * @param memberId  如果是null，默认不调用此接口，请谨记
	 * @return
	 */
	public List<String> getIsRemindList(String activityMangerId,String memberId);
	/**
	 * @Description: 获取app抢购列表信息(【抢购】【抢购】【抢购】【抢购】）
	 * @param startDate  开始时间 不能null
	 * @param activityId  活动id 不能null
	 * @param uid  会员id 不能null
	 * @param cityId  城市id，不能为null，必须传value
	 * @param page  分页  不能null
	 * @param pageSize 不能null
	 * @return
	 */
	public ResultDto findSaleList(String startDate,String activityManageId,String uid,String cityId
			,int page,int pageSize);
	
	/***
	* 根据活动id获取活动名称
	* @param ids
	* @return ResultDto    返回类型
	 */
	public ResultDto getActivityNameList(List<String> ids);
}
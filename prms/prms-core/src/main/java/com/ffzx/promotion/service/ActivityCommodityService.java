package com.ffzx.promotion.service;


import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.commodity.api.dto.CommoditySku;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommodityVo;
import com.ffzx.promotion.api.dto.ActivityEsVo;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;

/**
 * activity_commdity数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-10 15:56:22
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface ActivityCommodityService extends BaseCrudService {

	/*******
	 * 获取活动对应的商品列表
	 * @param page
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<ActivityCommodity> findActivityCommdity(Page page,Map<String, Object>params)throws  ServiceException ;
	
	/*********
	 * 商品设置新增
	 * @param activityCommdity
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode saveCommdity(ActivityCommodity activityCommdity,String viewStatus)throws ServiceException;
	
	

	public List<ActivityCommodity> findList(Page pageObj, String orderByField, String orderBy, ActivityCommodity activityCommdity) throws ServiceException;
	
	public Map<String,Object> importExcelPanicbuy(List<String[]> listRow,ActivityTypeEnum enum1,String id) throws Exception;
	
	/**
	* 根据活动id更新推荐商品信息
	* @Title: updateRecommendInfo 
	* @param activityCommdity
	* @param commoditys
	* @throws ServiceException     
	* @return int    返回类型
	 */
	public ServiceCode updateRecommendInfo(List<ActivityCommodity> commodityList, String id)throws ServiceException;

	/**
	* 删除活动商品表和活动商品sku表数据
	* @Title: updateRecommendInfo 
	* @param activityCommdity
	* @throws ServiceException     
	* @return int    返回类型
	 */
	public int deleteCommandSku(String id, String No)throws ServiceException,Exception;
	
	
	/***
	 * 验证设置的开始结束时间是否与已经存在的批次开始结束时间重合
	 * @param activityCommodity
	 * @param resultVo
	 * @param activitycommodityList
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> validateActivityCommodity(ActivityCommodity activityCommodity, ResultVo resultVo,
			List<ActivityCommodity> activitycommodityList,ActivityTypeEnum enums,String viewStatus) throws Exception;

	/**
	 * 获取正在进行对应活动列表
	 * @param page
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<ActivityCommodity> findActivityList(Page page, Map<String, Object> params) throws ServiceException;

	/**
	 * 获取商品新增详细信息
	 * @date 2016-05-10 15:56:22
	 * @author xyz
	 */
	public Map<String, Object> toSetCommdity(String activityId,String activityCommodityId);
	
	/**
	* 批量删除商品
	* @Title: deleteCommandSkus 
	* @param ids
	* @return int    返回类型
	 */
	public int deleteCommandSkus(String[] ids)throws ServiceException,Exception;

	/**
	 * @author 雷
	 * @date 2016年6月23日
	 * @Title: selectByAdvert
	 * @Description: 广告的查询
	 * @param @param pageObj
	 * @param @param params
	 * @param @return    
	 * @return List<ActivityCommodity>
	 * @throws
	 */
	public List<ActivityCommodityVo> selectByAdvert(Page pageObj, Map<String, Object> params);
	
	/**
	 * 修改抢购商品时间--与活动时间保持一致
	 */
	public ServiceCode updateCommodityDate(Map<String, Object> params)throws ServiceException;
	
	/**
	* 验证抢购活动商品是否存在重叠时间
	* @param params
	* @throws ServiceException     
	* @return List<ActivityCommodityVo>    返回类型
	 */
	public List<ActivityCommodity> findCommodityByDate(Map<String, Object> params)throws ServiceException;
	
	
	/***
	 * 获取指定activityMamageid下的活动商品明细
	 * @param activityManagerId
	 * @date 2016年9月14日 上午10:52:48
	 * @author ying.cai
	 * @return List<ActivityEsVo>
	 * @email ying.cai@ffzxnet.com
	 */
	public List<ActivityEsVo> findActivityGoodsItemByMid(String activityManagerId,String activityCommodityId);
}
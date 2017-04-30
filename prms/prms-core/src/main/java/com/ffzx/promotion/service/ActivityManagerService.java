package com.ffzx.promotion.service;

import java.util.List;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.ActivityManager;

/**
 * activity_manager数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月11日 下午4:25:32
 */
public interface ActivityManagerService extends BaseCrudService {

	/**
	 * 活动列表查询
	 * @param pageObj
	 * @param orderByField
	 * @param orderBy
	 * @param activity
	 * @return
	 */
	public List<ActivityManager> findList(Page pageObj, String orderByField, String orderBy, ActivityManager activity)throws ServiceException ;

	/**
	 * 新增编辑活动
	 * @param activity
	 * @return
	 * @throws ServiceException
	 * @author luozi
	 */
	public ServiceCode saveOrUpdate(ActivityManager activity) throws ServiceException;
	
	/*****
	 * 修改发布状态
	 * @param activity
	 * @return
	 * @throws ServiceException
	 */
	public int updateActivityStatus(ActivityManager activity)throws ServiceException;

	/**
	* 查询已发布、未推荐的抢购活动
	* @param pageObj
	* @param activity
	* @throws ServiceException     
	* @return List<ActivityManager>    返回类型
	 */
	public List<ActivityManager> findAppRecommendList(Page pageObj, ActivityManager activity)throws ServiceException ;
	
	/*****
	* 验证选中app推荐的抢购活动
	* @param ids
	* @throws ServiceException     
	* @return List<ActivityManager>    返回类型
	 */
	public List<ActivityManager> findPanicBuyActivityList(String activityType, List<String> ids) throws ServiceException;
	
	/*****
	* 修改抢购活动为app推荐
	* @param appRecommend
	* @param ids
	* @return int    返回类型
	 */
	public int updateAppRecommend(String appRecommend, List<String> ids) throws ServiceException;

	/**
	* 验证抢购活动商品是否存在重叠时间
	* @param activity     
	* @return void    返回类型
	 */
	public void isActicityUpdateDate(ActivityManager activity) throws ServiceException;
	
}
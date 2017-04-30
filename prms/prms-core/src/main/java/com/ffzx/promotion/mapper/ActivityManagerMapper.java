package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.promotion.api.dto.ActivityManager;

/**
 * activity_manager数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-10 15:56:22
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */


@SuppressWarnings("deprecation")
@MyBatisDao
public interface ActivityManagerMapper extends CrudMapper {

	public List<ActivityManager> getActivityDetailList(@Param("params") Map<String, Object> params);
	
	/**
	* 大麦场列表
	* @Title: selectDMCByPage 
	* @param page
	* @param params
	* @return List<ActivityManager>    返回类型
	 */
	public List<ActivityManager> selectDMCByPage(@Param("page") Page page, @Param("params") Map<String, Object> params);
	
	/*****
	* 获取app推荐列表
	* @param page
	* @param params
	* @return List<ActivityManager>    返回类型
	 */
	public List<ActivityManager> selectAppRecommendList(@Param("page") Page page, @Param("params") Map<String, Object> params);
	
	/*****
	* 验证选中app推荐的抢购活动
	* @param params
	* @throws ServiceException     
	* @return List<ActivityManager>    返回类型
	 */
	public List<ActivityManager> findPanicBuyActivityList(@Param("params") Map<String, Object> params);
	
	/*****
	* 修改抢购活动为app推荐
	* @param params
	* @return int    返回类型
	 */
	public int updateAppRecommend(@Param("params") Map<String, Object> params);
	
	 /**
	 * @Description: 获取app抢购类目列表
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @date 2016年8月22日 上午11:09:23
	 * @version V1.0 
	 *
	 */
	public List<ActivityManager> findAppSaleCategory(@Param("params") Map<String, Object> param);
	
	/**
	* 验证抢购活动是否存在重叠时间
	* @param param
	* @return List<ActivityManager>    返回类型
	 */
	public List<ActivityManager> selectActivityByDate(@Param("params") Map<String, Object> params);
	
	/**
	* 获取活动
	* @param ids
	* @return List<String>    返回类型
	 */
	public List<String> findActivityNameList(List<String> ids);
}
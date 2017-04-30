package com.ffzx.promotion.api.service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * coupon_grant数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月4日 下午5:27:16
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface CouponGrantApiService extends BaseCrudService {


	/**
	 * 时间到点  自动发放优惠券,把发放状态改为已发放
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @date 2016年4月28日 上午9:32:18
	 * @version V1.0 
	 * @throws ServiceException
	 */
	public ResultDto updateGrant() throws ServiceException;
	

	/**
	 * 时间到点  自动发放优惠券,仅仅限制新用户专享
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @date 2016年4月28日 上午9:32:18
	 * @version V1.0 
	 * @throws ServiceException
	 */
	public ResultDto updateNewUserGrant() throws ServiceException;
	
	/**
	*  获取推荐有奖信息
	* @throws ServiceException     
	* @return ResultDto    返回类型
	 */
	public ResultDto findAppRecommendAwardsInfo() throws ServiceException;

	
	/**
	* 优惠券领取列表（用户领取+所有用户）--已发放的优惠券
	* @author sheng.shan
	* @email  sheng.shan@ffzxnet.com
	* @date 2016年9月27日 上午10:28:18 
	* @param uid 会员id
	* @param grantId 优惠券发放id
	* @throws ServiceException
	* @return ResultDto    返回类型
	 */
	public ResultDto getCouponGrantList(String uid,String grantId) throws ServiceException;



	/**
	 * 根据发放时间发放指定优惠券   自动发放优惠券,仅仅限制新用户专享
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @date 2016年4月28日 上午9:32:18
	 * @version V1.0 
	 * @throws ServiceException
	 */
	public ResultDto updateNewUserGrantDate() throws ServiceException;
	
	/**
	 * 优惠券发放消息
	 */
	public ResultDto coupongrantMessage() throws ServiceException ;

}
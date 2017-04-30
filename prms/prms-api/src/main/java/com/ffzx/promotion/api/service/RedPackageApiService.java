package com.ffzx.promotion.api.service;

import java.util.Map;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.api.dto.RedpackageReceive;

public interface RedPackageApiService extends BaseCrudService {

	/***
	 * 红包和优惠券推送方式给用户发红包或者优惠券
	 * @param memberId
	 * @return
	 */
	public ResultDto saveRedPackageMessage(String memberId);
	
	
	/***
	 * 天降红包方式给用户发红包或者优惠券
	 * @param memberId
	 * @return
	 */
	public ResultDto saveRedPackageOrCoupon(String memberId);
	
	/**
	 * 拆红包
	 * @param redPackage
	 * @return
	 */
	public ResultDto receiveRedPackage(RedpackageReceive redPackage);
	
	/***
	 * 优惠券领取
	 * @param couponReceive
	 * @return
	 */
	public ResultDto receiveCoupon(CouponReceive couponReceive);
	
	/***
	 * 我的现金红包
	 * @param params
	 * @return
	 */
	public ResultDto getRedPackageList(Map<String, Object> params);
	
	
	/***
	 * 可用红包列表和最优方案
	 * @param params
	 * @return
	 */
	public ResultDto getOptimumRedPackage(Map<String, Object> params);
	
	/***
	 * 根据发放ID获取抽奖活动详情
	 * @param String grantId,String type,String memberId
	 * @return
	 */
	public ResultDto getRedpackageDetail(Map<String, Object> params);
	
	/**
	 * 根据发放ID获取发放数量
	 * @param grantId
	 * @param type
	 * @return
	 */
	public ResultDto getReceiveCount(String grantId,String type);
	
	/**
	 * 
	 * @param params
	 * @return key :redpackageUseIds 红包使用id 多个 以;号隔开
	 * 						 type 类型（0 下单成功 1 支付成功 2 取消订单）
	 * 						 orderPrice 订单付款金额
	 */
	public ResultDto updateReceiveStatus(Map<String, Object> params);
}

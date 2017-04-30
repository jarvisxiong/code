package com.ffzx.promotion.api.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * coupon_receive数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016-05-20 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface CouponReceiveApiService extends BaseCrudService {

	/**
	 * 查询出用户优惠券基本信息
	 * @param coureceiveid  优惠领取id
	 * @return
	 */
	public ResultDto getCoupReceive(String coureceiveid);
	
	/**
	 * 判断领取优惠券是否符合指定类目和商品
	 * @param type
	 * @param id
	 * @return
	 */
	public ResultDto isCommodityCategory(String type,String id);
	
	/**
	 * 优惠券领取更新状态
	 * @param id
	 * @param state 使用状态(0未使用，1使用).
	 * @param memberId 为null字符就是ios，过渡期一个星期
	 * @return
	 */
	public ResultDto updateReceiveState(String id,String state,String memberId);
	
	/**
	 * 查询出用户优惠券基本信息
	 * @param map = uid,amount
	 * @return
	 */
	public ResultDto getCouponReceiveList(Map<String, Object> map);
	/***
	 * 获取我的优惠券可用总数
	 * @param uid 用户ID
	 * @param amount 付费总计
	 * @return
	 */
	public ResultDto findCouponCount(String uid,String amount);
	
	/***
	 * 根据用户id，优惠券ID获取领取的优惠券
	 * @param uid
	 * @param couponId
	 * @return
	 */
	public ResultDto findCoupon(String uid,String couponId);
	/**
	 * 根据code获取优惠券，判断是否兑换成功
	 * @param uid
	 * @param vcode
	 * @return code=0兑换成功，其他兑换失败
	 */
	public ResultDto getVcode(String uid,String vcode);
	
	/*****
	*  根据商品ids，获取商品名称
	* @param goodsIds
	* @return ResultDto    返回类型
	 */
	public ResultDto getCommodityName(List<String> goodsIds, String couponAdminId);
	
	/**
	 * 查询出用户优惠券列表信息
	 * @param map = uid,status
	 * @return
	 */
	public ResultDto getMyCouponList(Map<String, Object> map);
	
	/***
	 * 根据用户id，优惠券ID获取领取的优惠券、并且在管理和发放中间表设置已领取数
	 * @param uid 会员id
	 * @param adminId 优惠券id
	 * @param grantId 优惠券发放id
	 * @param grantVcode 优惠券发放编码
	 * @return
	 */
	public ResultDto getCoupon(String uid, String adminId, String grantId);
	
	/**
	* 定时提醒--优惠券已快过期（提前三天提醒）
	* @return ResultDto    返回类型
	 */
	public ResultDto remindCouponOverDate();
	

	public Map<String, Object> findCouponForOrder(String uid, List<Map<String, Object>> lMaps);
	/**
	 * @desc tang.jun看没人用，建议注释掉
	 * 获取我的优惠券
	 * @param uid    会员id
	 * @return 
	 */
//	public Map<String, Object> findCouponList(String uid, String couponStatus,List<Map<String, Object>> lMaps,String path);
}
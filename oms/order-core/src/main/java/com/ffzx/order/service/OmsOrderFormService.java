 package com.ffzx.order.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.api.dto.GoodsVo;
import com.ffzx.order.api.dto.OmsOrder;

/**
 * 订单成单业务
 * 
 * @author ffzx
 * @date 2016-05-10 14:01:15
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface OmsOrderFormService extends BaseCrudService {
	/***
	 * 对外商品购买 接口服务
	 * @param sysType 0:android,1:ios,2:pc
	 * @param memberId 购买会员ID
	 * @param couponId 使用优惠券ID
	 * @param addressId 收货地址ID
	 * @param isInvoice 是否需要发票
	 * @param desc 订单备注
	 * @param cityId 所在城市地区ID
	 * @param goodsList 所购买商品集合对象
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016年5月9日 上午9:32:18
	 * @version V1.0
	 * @return 
	 * data{
	 * 	 id:订单ID
	 * 	 orderNo:订单编号
	 *   buyTime:购买下单时间
	 *   overTime:订单超时自动取消时间
	 *   totalPrice:订单需支付总金额	
	 * }
	 * @throws ServiceException
	 */
	public OmsOrder placeAnOrder(String sysType, String uId, String couponId, String addressId,
			String isInvoice, String goodsListStr)throws ServiceException;

	public OmsOrder importBuildOrder(String uId,String addressId,List<GoodsVo> goodsList
		 ) throws ServiceException;
}
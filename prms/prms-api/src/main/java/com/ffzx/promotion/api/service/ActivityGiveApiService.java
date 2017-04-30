package com.ffzx.promotion.api.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.ActivityGiveOrder;
import com.ffzx.promotion.api.dto.GiveOrderCoupon;

/***
 * 买赠活动api
 * @author lushi.guo
 *
 */
public interface ActivityGiveApiService extends BaseCrudService{

	/***
	 * 获取买赠活动主商品信息
	 * map.put("commodityId","xxxx")
	 * @param params
	 * @return
	 */
	public ResultDto getActivityGive(Map<String, Object> params);
	
	/**
	 * 获取赠品集合
	 * map.put("commodityId","xxxx")
	 * @param parmas
	 * @return
	 */
	public ResultDto getActivityGifts(Map<String, Object>params);
	
	/***
	 * 确认下单页面根据主商品获取赠品是否显示
	 * @param params
	 * @return
	 */
	public ResultDto getActivityGiftsByCommodity(List<ActivityGiveOrder>params);
	
	/***
	 * 确认下单验证参加买赠的商品是否可以下单
	 * map.put("order",order);
	 * @param params
	 * @return
	 */
	public ResultDto checkGiveOmsOrder(Map<String, Object> params);
	
	/***
	 * 根据赠品表ID获取赠品SKU
	 * @param params4
	 * skuMap.put("giftCommodityId", "xxxx");
	 * @return
	 */
	public ResultDto getGiftCommoditySku(Map<String, Object> params);
	
	/***
	 * 买赠活动交易完成的订单发放优惠券
	 * 
	 * @param giveOrder
	 * @return
	 */
	public ResultDto saveGiveOrderCoupon(GiveOrderCoupon giveOrder);
	
	/**
	 * 定时发放买赠活动优惠券
	 * @return
	 */
	public ResultDto sendGiveOrderCoupon();
	
	
	/***
	 * 获取主商品已经购买数量
	 * @param commodityNo
	 * @param giftCommodityItemId
	 * @return
	 */
	public ResultDto getCommodityBuyNum(String commodityId);
}

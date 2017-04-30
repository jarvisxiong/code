package com.ffzx.order.service;


import java.util.Date;

import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * @className PriceSettlementDetailService
 *
 * @author liujunjun
 * @date 2017-03-23 16:37:40
 * @version 1.0.0
 */
public interface PriceSettlementDetailService extends BaseCrudService{
	/**
	 * 系统自动统计支付超15天的合格订单，
	 * 根据商品批发价、零售价核算出应补给服务站合伙人的总金额，
	 * 按合人伙服务站维度生成待补助结算单数据，大麦场定期与服务站结算。
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月24日 上午9:41:07
	 * @version V1.0
	 * @return
	 */
	public void autoPush2PriceSettlementDetail(Date date,String serialCode);
	
	/**
	 * 系统自动统计支付超15天的合格订单，
	 * 根据商品批发价、零售价核算出应补给服务站合伙人的总金额，
	 * 按合人伙服务站维度生成待补助结算单数据，大麦场定期与服务站结算。
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月24日 上午9:41:07
	 * @version V1.0
	 * @return
	 */
	public void autoPush2PriceSettlement(Date date,String serialCode);
	
	
	public void deleteDetail(String id);
}

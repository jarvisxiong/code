/**
 * 
 */
package com.ffzx.order.api.service;

import com.ffzx.order.api.dto.OmsOrder;

/**
 * @Description: TODO
 * @author zi.luo
 * @email  zi.luo@ffzxnet.com
 * @date 2016年6月7日 上午10:13:11
 * @version V1.0 
 *
 */
public interface OutboundSalesDeliveryMqApiService {

	/**
	 * 1.1	发货订单数据推送
	 * @param order
	 */
	public void outboundSalesDelivery(OmsOrder order);
}

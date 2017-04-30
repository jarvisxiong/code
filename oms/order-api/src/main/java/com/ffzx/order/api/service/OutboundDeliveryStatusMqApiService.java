/**
 * 
 */
package com.ffzx.order.api.service;


 /**
 * @Description: TODO
 * @author zi.luo
 * @email  zi.luo@ffzxnet.com
 * @date 2016年6月7日 下午7:20:03
 * @version V1.0 
 *
 */
public interface OutboundDeliveryStatusMqApiService {

	/**
	 * 1.3	订单取消状态推送
	 * @param salesOrderNo   销售订单号
	 * @param status  状态
	 */
	public void outboundDeliveryStatus(String salesOrderNo,Integer status);
}

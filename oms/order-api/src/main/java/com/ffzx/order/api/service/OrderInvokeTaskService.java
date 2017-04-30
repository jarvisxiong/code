package com.ffzx.order.api.service;

/***
 * 订单定时任务执行定义service
 * @author ying.cai
 * @date 2016年6月6日 下午2:29:58
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public interface OrderInvokeTaskService{
	
	/***
	 * 定时自动取消订单
	 * @date 2016年6月6日 下午2:32:10
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public void autoCancelOrder();
}

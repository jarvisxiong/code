package com.ffzx.order.service.job;

import com.ffzx.commerce.framework.exception.ServiceException;

public interface OmsJobService {
	/**
	 * 销量统计定时任务
	 * @return
	 * @throws ServiceException
	 */
	public void salesTimingTask() throws ServiceException;
	
	/***
	 * 定时自动确认收货（相当于合伙人点击确认送达）
	 * @date 2016年6月21日 下午5:25:00
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public void autoConfirmOrder();
	
	/***
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月22日 上午10:57:43
	 * @version V1.0
	 * @return 
	 */
	public void autoRepush2wms();
}

package com.ffzx.order.api.service;

import com.ffzx.commerce.framework.exception.ServiceException;

public interface OmsJobApiService {
	
	/**
	 * 销量统计定时任务
	 * @return
	 * @throws ServiceException
	 */
	public void salesTimingTask() throws ServiceException;
	
	/***
	 * 定时自动确认收货（相当于合伙人点击确认送达）
	 * 
	 * @date 2016年6月21日 下午12:01:35
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public void autoConfirmOrder();
	/**
	 * 定时任务  自动补偿未推送的销售订单信息到wms
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月22日 上午10:52:02
	 * @version V1.0
	 * @return
	 */
	public void autoRepush2wms();
	/**
	 * 定时任务统计会员月消费数据
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月25日 上午11:38:46
	 * @version V1.0
	 * @return
	 */
	public void autoMemberSummary();
	/**
	 * 定时推送每月会员消费数据
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月25日 下午2:28:49
	 * @version V1.0
	 * @return
	 */
	public void autoPushMs2Mq();
	
	/**
	 *需求说明：

    		系统自动统计支付超15天的合格订单，根据商品批发价、零售价核算出应补给服务站合伙人的总金额，按合人伙服务站维度生成待补助结算单数据，
    		大麦场定期与服务站结算。
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月25日 下午2:28:49
	 * @version V1.0
	 * @return
	 */
	public void autoPush2PriceSettlement();
	
}

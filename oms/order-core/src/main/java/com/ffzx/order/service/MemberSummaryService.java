package com.ffzx.order.service;

import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * @className MemberSummaryService
 *
 * @author liujunjun
 * @date 2016-10-24 18:24:32
 * @version 1.0.0
 */
public interface MemberSummaryService extends BaseCrudService{
	/**
	 * 定时任务  从订单统计单个会员销售数量
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月24日 下午6:49:16
	 * @version V1.0
	 * @return
	 */
	public void taskSummaryFromOrder();
	
	/**
	 * 定时推送每月会员消费数据
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月25日 下午2:52:46
	 * @version V1.0
	 * @return
	 */
	public void taskPushMs2Mq();
}

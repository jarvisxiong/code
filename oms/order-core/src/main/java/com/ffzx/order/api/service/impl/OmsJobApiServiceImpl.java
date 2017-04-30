package com.ffzx.order.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.utils.RedisLock;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.order.api.service.OmsJobApiService;
import com.ffzx.order.service.MemberSummaryService;
import com.ffzx.order.service.PriceSettlementDetailService;
import com.ffzx.order.service.job.OmsJobService;

 /**
 * @Description: oms系统 job dubbo接口
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年6月7日 下午2:30:35
 * @version V1.0 
 *
 */
@Service
public class OmsJobApiServiceImpl implements OmsJobApiService{
	@Autowired
	private OmsJobService omsJobService;
	
	@Autowired
	private MemberSummaryService memberSummaryService;
	@Autowired
	private PriceSettlementDetailService priceSettlementDetailService;
	@Autowired
	private RedisUtil redis;
	
	@Override
	public void salesTimingTask() throws ServiceException {
		RedisLock lock= new RedisLock(redis, "OMS_ORDER_SALES_TIMING_TASK_LOCK",5000,60000,10);
		try {
			if(lock.acquire()){
				this.omsJobService.salesTimingTask();
				lock.release();
			}
		} catch (Exception e) {
			if(null != lock && lock.isLocked()){
				lock.release();
			}
		}
	}
	
	@Override
	public void autoConfirmOrder() {
		omsJobService.autoConfirmOrder();
	}

	/***
	 * 订单补偿推送wms
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月22日 上午10:57:43
	 * @version V1.0
	 * @return 
	 */
	@Override
	public void autoRepush2wms() {
		omsJobService.autoRepush2wms();
	}
	/**
	 * *
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月25日 下午2:44:41
	 * @version V1.0
	 * @return
	 */
	@Override
	public void autoMemberSummary(){
		memberSummaryService.taskSummaryFromOrder();
	}

	/***
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月25日 下午2:11:52
	 * @version V1.0
	 * @return 
	 */
	@Override
	public void autoPushMs2Mq() {
		// TODO Auto-generated method stub
		memberSummaryService.taskPushMs2Mq();
	}

	/***
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月24日 下午5:23:01
	 * @version V1.0
	 * @return 
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void autoPush2PriceSettlement() {
		Date now = new Date();
		String serialCode = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
		priceSettlementDetailService.autoPush2PriceSettlementDetail(now,serialCode);
		priceSettlementDetailService.autoPush2PriceSettlement(now,serialCode);
	}
}

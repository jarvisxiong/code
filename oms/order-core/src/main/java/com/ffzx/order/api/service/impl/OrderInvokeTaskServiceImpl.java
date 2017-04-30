package com.ffzx.order.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.enums.OrderOperationRecordEnum;
import com.ffzx.order.api.service.OrderInvokeTaskService;
import com.ffzx.order.mapper.OmsOrderMapper;
import com.ffzx.order.service.OmsOrderService;

@Service("orderInvokeTaskService")
public class OrderInvokeTaskServiceImpl implements OrderInvokeTaskService {
	private Logger logger = LoggerFactory.getLogger(OrderInvokeTaskServiceImpl.class);
	@Autowired
	private OmsOrderService omsOrderService;
	@Autowired
	private OmsOrderMapper omsOrderMapper;
	
	@Override
	public void autoCancelOrder() {
		logger.info("OrderInvokeTaskServiceImpl.autoCancelOrder 自动取消订单 task start ===>>>");
		
		List<OmsOrder> orderList = omsOrderService.findOrderToBatchCancel();
		int count = 0 ; //记录更新成功的数据条数
		StringBuilder successOrderNos = new StringBuilder(); //记录更新成功的订单编码集合
		//开始跑取消订单任务
		for (OmsOrder omsOrder : orderList) {
			try {
				logger.info("自动取消订单：【"+omsOrder.getOrderNo()+"】OrderInvokeTaskServiceImpl.autoCancelOrder");
//				omsOrderService.cancelOrder(omsOrder,OrderOperationRecordEnum.AUTO_CANCEL);
				omsOrderService.operateOrderByType(omsOrder.getOrderNo(), OrderOperationRecordEnum.AUTO_CANCEL.getValue());
				successOrderNos.append(omsOrder.getOrderNo()).append(",");
				count ++;
			} catch (Exception e) {
				logger.error("OrderInvokeTaskServiceImpl.autoCancelOrder error,orderNo = " + omsOrder.getOrderNo(),e);
			}
		}
		logger.info("invoke result === success count:" + count+ ",orderNos = {" +successOrderNos.toString() +"}");
		logger.info("OrderInvokeTaskServiceImpl.autoCancelOrder 自动取消订单 task end ===>>>");
	}
}

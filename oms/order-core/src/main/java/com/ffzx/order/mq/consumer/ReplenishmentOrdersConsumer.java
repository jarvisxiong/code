/**   
 * @Title: ReplenishmentOrders.java
 * @Package com.ffzx.order.mq.consumer
 * @Description: 订单补货
 * @author 雷  
 * @date 2016年9月20日 
 * @version V1.0   
 */
package com.ffzx.order.mq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ffzx.commerce.framework.mq.AbstractMessageConsumer;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.order.api.dto.ReplenishmentOrderVo;
import com.ffzx.order.service.OmsOrderService;

/**
 * @ClassName: ReplenishmentOrders
 * @Description: 订单补货的消息
 * @author 雷
 * @date 2016年9月20日
 * 
 */
@Component
public class ReplenishmentOrdersConsumer extends AbstractMessageConsumer<ReplenishmentOrderVo> {
	/**
	 * Logger for this class
	 */
	protected final Logger logger = LoggerFactory.getLogger(ReplenishmentOrdersConsumer.class);

	@Autowired
	private OmsOrderService omsOrderService;

	/**
	 * 雷-----2016年9月20日 (非 Javadoc)
	 * <p>
	 * Title: onMessage
	 * </p>
	 * <p>
	 * Description:补货修改订单信息
	 * </p>
	 * 
	 * @param message
	 * @see com.ffzx.commerce.framework.mq.MessageConsumer#onMessage(java.lang.Object)
	 */
	@SuppressWarnings("unused")
	@Override
	public void onMessage(ReplenishmentOrderVo message) {
		try {
			logger.info("订单补货消费消息>>>>>>"+JsonMapper.toJsonString(message));
			int i = this.omsOrderService.replenishmentOrders(message);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("订单补货消费消息", e);
		}
	}

}

/**
 * 
 */
package com.ffzx.order.mq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.order.api.dto.OutboundDeliveryStatusApiVo;
import com.ffzx.order.api.service.OutboundDeliveryStatusMqApiService;

/**
 * @Description: TODO
 * @author zi.luo
 * @email  zi.luo@ffzxnet.com
 * @date 2016年6月7日 下午7:23:16
 * @version V1.0 
 *
 */
@Service("outboundDeliveryStatusMqApiService")
public class OutboundDeliveryStatusMqApiServiceImpl implements OutboundDeliveryStatusMqApiService {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(OutboundDeliveryStatusMqApiServiceImpl.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public void outboundDeliveryStatus(String salesOrderNo, Integer status) {
		try {
			OutboundDeliveryStatusApiVo outboundDeliveryStatusApiVo = new OutboundDeliveryStatusApiVo();
			outboundDeliveryStatusApiVo.setSalesOrderNo(salesOrderNo);
			outboundDeliveryStatusApiVo.setStatus(status);
			logger.info("开始推送订单取消推送到wms ========================>>"+JsonMapper.toJsonString(outboundDeliveryStatusApiVo));
			rabbitTemplate.convertAndSend("QUEUE_WMS_OUTBOUND_DELIVERY_CANCEL", outboundDeliveryStatusApiVo);
			logger.info("结束推送订单取消消息===============");
		} catch (Exception e) {
			logger.error("开始推送订单取消消息 : invoke OutboundDeliveryStatusMqApiServiceImpl.outboundDeliveryStatus error ===>>>",e);
			throw new ServiceException(e);
		}
	}

}

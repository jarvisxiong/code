package com.ffzx.order.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.order.api.dto.InboundAftersaleReqApiVo;
import com.ffzx.order.api.service.AftersaleApplyInstorageMqApiService;

@Service("aftersaleApplyInstorageMqApiService")
public class AftersaleApplyInstorageMqApiServiceImpl implements AftersaleApplyInstorageMqApiService {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Override
	public void saveAftersaleApplyInstorageMq(InboundAftersaleReqApiVo aftersaleReqApiVo) {
		
		rabbitTemplate.convertAndSend("QUEUE_INBOUND_AFTERSALE_REQ", aftersaleReqApiVo);
	}

}

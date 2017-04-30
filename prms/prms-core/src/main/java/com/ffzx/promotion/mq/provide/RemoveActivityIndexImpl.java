package com.ffzx.promotion.mq.provide;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.promotion.api.service.RemoveActivityIndex;
import com.ffzx.promotion.constant.MqConstant;

/***
 * 移除活动索引
 * 当预售，抢购，普通活动的数据被移除时，都需要调用此类方法推送消息至mq
 * @author ying.cai
 * @date 2016年9月12日 上午10:22:38
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
@Service
public class RemoveActivityIndexImpl implements RemoveActivityIndex {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	private final static Logger logger = LoggerFactory.getLogger(RemoveActivityIndexImpl.class);
	
	@Override
	public void sendMqByRemoveActivity(String goodsId) {
		logger.info("活动移除mq推送START===>>>{goodsId:" +goodsId+ "}");
		try {
			rabbitTemplate.convertAndSend(MqConstant.PRMS_ACTIVITY_REMOVE,"", goodsId);
		} catch (Exception e) {
			logger.error("活动移除mq推送 ERROR===>>>", e);
			throw new RuntimeException("活动信息修改mq推送 ERROR",e);
		}
		logger.info("活动移除mq推送END===>>>{goodsId:" +goodsId+ "}");
	}

	@Override
	public void sendMqByRemoveActivity(List<String> idList) {
		for (String id : idList) {
			sendMqByRemoveActivity(id);
		}
	}

}

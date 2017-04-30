package com.ffzx.order.mq.producer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.order.api.dto.AftersalePickup;
import com.ffzx.order.api.dto.AftersaleRefund;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.enums.OrderStatusEnum;
import com.ffzx.order.api.vo.ChangeSaleBroadcastVo;
import com.ffzx.order.api.vo.RefundBroadcastVo;
import com.ffzx.order.api.vo.ReturnGoodsBroadcastVo;
import com.ffzx.order.service.AftersaleApplyService;

/**
 * 
 * @ClassName: AftersaleToMemberOtherMq
 * @Description: 售后广播出去的消息
 * @author 雷
 * @date 2016年10月24日
 * 
 */
@Service("aftersaleToMemberOtherMq")
public class AftersaleToMemberOtherMq {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(AftersaleToMemberOtherMq.class);
	@Autowired
	private AftersaleApplyService aftersaleApplyService;
	/**
	 * @param sysUser
	 * 
	 *            雷------2016年10月24日
	 * 
	 * @Title: sendRefundMember
	 * @Description: 退款广播会员的消息
	 * @param @param refund
	 * @param @param order 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void sendRefundMember(AftersaleRefund refund, OmsOrder order, SysUser user) {
		try {
			RefundBroadcastVo broadcastVo = new RefundBroadcastVo();

			broadcastVo.setRefund(refund);
			/**
			 * 区分退款(TRANSACTION_CLOSED:交易关闭),还是（TRANSACTION_COMPLETION：交易完成）退货
			 */
			if (OrderStatusEnum.TRANSACTION_CLOSED.getValue().equals(order.getStatus()))
				broadcastVo.setType("1");
			else if (OrderStatusEnum.TRANSACTION_COMPLETION.getValue().equals(order.getStatus()))
				broadcastVo.setType("2");
			order = this.aftersaleApplyService.getSourceOrder(refund.getApplyNo());
			broadcastVo.setOrder(order);
			broadcastVo.setUser(user);
			rabbitTemplate.convertAndSend("EXACHANGE_REFUND_TO_OTHER_USE", "", broadcastVo);
		} catch (AmqpException e) {
			logger.error("退款的广播异常", e);
		}
	}

	/**
	 * 
	 * 雷------2016年10月24日
	 * 
	 * @Title: changeSaleBroadcast
	 * @Description: 换货的广播
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void changeSaleBroadcast(AftersalePickup pickup, OmsOrder order, List<OmsOrderdetail> orderDetails, SysUser user) {
		try {
			order.setDetailList(orderDetails);
			ChangeSaleBroadcastVo broadcastVo = new ChangeSaleBroadcastVo();
			broadcastVo.setOrder(order);
			broadcastVo.setPickup(pickup);
			broadcastVo.setUser(user);
			rabbitTemplate.convertAndSend("EXACHANGE_CHANGESALE_TO_OTHER_USE", "", broadcastVo);
		} catch (AmqpException e) {
			logger.error("换货的广播异常", e);
		}
	}

	/**
	 * @param user
	 * 
	 *            雷------2016年11月2日
	 * 
	 * @Title: returnGoods
	 * @Description: 退货广播
	 * @param @param order
	 * @param @param pickup 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void returnGoods(OmsOrder order, AftersalePickup pickup, SysUser user) {
		try {
			ReturnGoodsBroadcastVo broadcastVo = new ReturnGoodsBroadcastVo();
			broadcastVo.setOrder(order);
			broadcastVo.setPickup(pickup);
			broadcastVo.setUser(user);
			rabbitTemplate.convertAndSend("EXACHANGE_RETURNGOODS_TO_OTHER_USE", "", broadcastVo);
		} catch (AmqpException e) {
			e.printStackTrace();
			logger.error("退货的广播异常", e);
		}
	}
}

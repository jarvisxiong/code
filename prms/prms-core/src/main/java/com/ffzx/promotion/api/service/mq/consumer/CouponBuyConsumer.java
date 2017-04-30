package com.ffzx.promotion.api.service.mq.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ffzx.commerce.framework.mq.AbstractMessageConsumer;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.member.api.dto.Member;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.promotion.service.CouponGrantService;

/**
 * 采购入库申请
 * @author liangdong.yao
 *
 */
@Component
public class CouponBuyConsumer extends AbstractMessageConsumer<OmsOrder>{

	@Autowired
	private CouponGrantService couponGrantService;
	
	
	/** ====================判断非区域性商品 后期删除==================== */
	@Override
	public void onMessage(OmsOrder omsOrder) {
		try {
			logger.info("购买优惠券，发放start");
			if(omsOrder!=null && !StringUtil.isEmpty(omsOrder.getOrderNo()) && !StringUtil.isEmpty(omsOrder.getMemberPhone() )
					&& !StringUtil.isEmpty(omsOrder.getMemberId()) ){
				logger.info(omsOrder+"购买优惠券，发放中...."+omsOrder==null?"null":omsOrder.getOrderNo()+"memberid"+omsOrder.getMemberId()+"buycount"+omsOrder.getBuyCount());				
				couponGrantService.updateBuyCouopon(omsOrder);
				logger.info("购买优惠券，发放end"+omsOrder.getOrderNo());
			}else{
				logger.info(omsOrder+"无效购买发放购买优惠券，发放start"+omsOrder==null?"null":omsOrder.getOrderNo()+"memberid"+omsOrder.getMemberId()
				+"phone"+omsOrder.getMemberPhone());
			}
			
		} catch (Exception e) {
			logger.error("CouponGrantConsumer 发放出错:"+e.getMessage(),e);
		}
	}
	

}

















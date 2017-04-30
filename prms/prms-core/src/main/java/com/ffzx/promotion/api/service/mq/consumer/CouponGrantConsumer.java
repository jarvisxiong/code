package com.ffzx.promotion.api.service.mq.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ffzx.commerce.framework.mq.AbstractMessageConsumer;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.member.api.dto.Member;
import com.ffzx.promotion.service.CouponGrantService;

/**
 * 采购入库申请
 * @author liangdong.yao
 *
 */
@Component
public class CouponGrantConsumer extends AbstractMessageConsumer<Member>{

	@Autowired
	private CouponGrantService couponGrantService;
	
	
	/** ====================判断非区域性商品 后期删除==================== */
	@Override
	public void onMessage(Member member) {
		try {
			logger.info("注册发放优惠券");
			if(member!=null && !StringUtil.isEmpty(member.getId())){
				logger.info(member.getId());
				couponGrantService.updateNewUserCouopon(member);
				logger.info(member.getId()+","+member.getPhone());
			}
			
		} catch (Exception e) {
			logger.error("CouponGrantConsumer 发放出错:"+e.getMessage(),e);
		}
	}
	

}

















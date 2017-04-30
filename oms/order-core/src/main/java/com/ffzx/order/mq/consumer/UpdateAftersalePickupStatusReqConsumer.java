package com.ffzx.order.mq.consumer;


import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.mq.AbstractMessageConsumer;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.dto.AftersalePickup;
import com.ffzx.order.constant.OrderDetailStatusConstant;
import com.ffzx.order.service.AftersalePickupService;
import com.ffzx.order.service.inbuond.InboundAftersaleQualityService;
import com.ffzx.wms.api.dto.InboundAftersaleQualityApiVo;
import com.ffzx.wms.api.dto.InboundAftersaleQualityItemsApiVo;

/**
 * @author liangdong.yao
 *
 */
@Component
public class UpdateAftersalePickupStatusReqConsumer extends AbstractMessageConsumer<InboundAftersaleQualityApiVo>{
	@Autowired
	private InboundAftersaleQualityService inboundAftersaleQualityService;
	@Autowired
	private AftersalePickupService aftersalePickupService;
	
	@Override
	public void onMessage(InboundAftersaleQualityApiVo inboundAftersaleQualityApiVo) {
		logger.info("thunder-质检审核进入消息————————"+inboundAftersaleQualityApiVo.getReqNo());
		try {
			String no=inboundAftersaleQualityApiVo.getReqNo();//取货单号
			SysUser user=inboundAftersaleQualityApiVo.getUser();
			List<InboundAftersaleQualityItemsApiVo> itemsList= inboundAftersaleQualityApiVo.getItemsList();
			if(StringUtil.isNotNull(itemsList)){
				int qty=0;
				for(InboundAftersaleQualityItemsApiVo vo:itemsList){
					qty+=vo.getQty();					
				}
				if(qty>0){
					List<AftersalePickup> pickups=aftersalePickupService.getAftersalPickupByPickNo(no);
					//取货单未审核执行后续操作
					if(null!=pickups && pickups.size()>0 && OrderDetailStatusConstant.APPROVE_NO.equals(pickups.get(0).getPickupStatus())){
						//质检数量大于0 的时候 说明已经取到货了 ，更新取货单状态为已审核
						logger.info("thunder-质检审核进入消息2————————"+inboundAftersaleQualityApiVo.getReqNo());
						this.inboundAftersaleQualityService.updatePickupStatus(user,pickups);
					}

				}
			}
		} catch (Exception e) {
			logger.error("InboundPurchaseReqConsumer :" + ToStringBuilder.reflectionToString(inboundAftersaleQualityApiVo), e);
			throw new ServiceException(e);
		}
	}

}

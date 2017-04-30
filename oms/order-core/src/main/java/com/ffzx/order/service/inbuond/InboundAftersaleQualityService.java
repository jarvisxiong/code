package com.ffzx.order.service.inbuond;

import java.util.List;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.order.api.dto.AftersalePickup;

public interface InboundAftersaleQualityService {
	/***
	 * 质检单修改状态
	 * @param pickup
	 * @throws ServiceException
	 */
	public void updatePickupStatus(SysUser user,List<AftersalePickup> pickups) throws ServiceException;
}

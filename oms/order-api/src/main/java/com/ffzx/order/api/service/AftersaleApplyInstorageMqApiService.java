package com.ffzx.order.api.service;

import com.ffzx.order.api.dto.InboundAftersaleReqApiVo;

public interface AftersaleApplyInstorageMqApiService {

	/***
	 * 售后入库申请
	 * @param aftersaleReqApiVo
	 */
	public void saveAftersaleApplyInstorageMq(InboundAftersaleReqApiVo aftersaleReqApiVo);
}

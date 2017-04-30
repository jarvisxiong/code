package com.ffzx.promotion.api.service;

import java.util.Map;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.page.Page;

public interface MemberCouponApiService {

	/******
	 * 根据会员获取已领取优惠券信息
	 * @param page
	 * @param params
	 * @return ResultDto
	 * 返回ResultDto对象,ResultDto.data 
	 * 数据类型为Page page, Map<String, Object> params
	 */
	public ResultDto getMemberCouponList(Page page, String memberId);
}

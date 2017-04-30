package com.ffzx.promotion.api.service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.service.BaseCrudService;

public interface ActivityCategoryApiService extends BaseCrudService{

	/****
	 * 根据优惠券ID获取指定的商品或者类目
	 * @param couponId
	 * @return
	 */
	public ResultDto selectActivityCategory(String couponId);
}

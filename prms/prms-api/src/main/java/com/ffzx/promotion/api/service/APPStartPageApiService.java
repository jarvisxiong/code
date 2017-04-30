package com.ffzx.promotion.api.service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.service.BaseCrudService;

public interface APPStartPageApiService extends BaseCrudService {

	/**
	*  获取启动页数据
	* @param termainl
	* @param imgSize
	* @return ResultDto    返回类型
	 */
	public ResultDto findAPPStartPageList(String termainl, String imgSize);
}

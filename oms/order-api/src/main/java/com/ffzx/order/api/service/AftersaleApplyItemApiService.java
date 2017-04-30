package com.ffzx.order.api.service;

import java.util.List;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.order.api.dto.AftersaleApplyItem;

public interface AftersaleApplyItemApiService {

	
	/******
	 * 新增售后申请单详情
	 * @param apply
	 * @return
	 */
	public ResultDto saveAftersaleApply(List<AftersaleApplyItem> item) throws ServiceException;
	
	public ResultDto findAftersaleApplyItemList(Page pageObj,String orderByField, String orderBy,AftersaleApplyItem aftersaleApplyItem)throws ServiceException;
	
}

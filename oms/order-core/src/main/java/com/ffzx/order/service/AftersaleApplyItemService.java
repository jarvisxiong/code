package com.ffzx.order.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.api.dto.AftersaleApplyItem;

/**
 * aftersale_apply_item数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-11 11:50:59
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface AftersaleApplyItemService extends BaseCrudService {

	/******
	 * 获取售后申请单详情集合
	 * @param pageObj
	 * @param orderByField
	 * @param orderBy
	 * @param aftersaleApplyItem
	 * @return
	 * @throws ServiceException
	 */
	public List<AftersaleApplyItem> findAftersaleApplyItemList(Page pageObj,String orderByField, String orderBy,AftersaleApplyItem aftersaleApplyItem)throws ServiceException;
	/********
	 * 新增售后申请单详情
	 * @param aftersaleApplyItem
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode saveAftersaleApplyItem(AftersaleApplyItem aftersaleApplyItem)throws ServiceException;
	
	/****
	 * 根据售后申请单ID 查询对应的售后商品（只针对交易已完成，申请单品）
	 * @param applyId
	 * @return
	 * @throws ServiceException
	 */
	public List<AftersaleApplyItem> findByApplyId(String applyId)throws ServiceException;
	
	
	public int getHadRreturnCount(Map<String,Object> params);

}
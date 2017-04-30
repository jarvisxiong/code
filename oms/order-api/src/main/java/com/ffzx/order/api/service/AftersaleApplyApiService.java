package com.ffzx.order.api.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.order.api.dto.AftersaleApply;
import com.ffzx.order.api.dto.AftersaleApplyItem;
import com.ffzx.order.api.dto.AftersalePickup;
import com.ffzx.order.api.dto.AftersaleRefund;

public interface AftersaleApplyApiService {

	
	/******
	 * 新增售后申请单
	 * @param apply
	 * @return
	 */
	public ResultDto saveAftersaleApply(Map<String, Object> params)throws ServiceException;
	
	/*******
	 * 获取售后申请单列表
	 * @param apply
	 * @return
	 */
	public ResultDto findAftersaleApply(AftersaleApply apply)throws ServiceException;
	
	/*****
	 * 获取售后申请单详情
	 * @return
	 * @throws ServiceException
	 * @param apply
	 */
	public ResultDto findAftersaleApplyDetail(AftersaleApply apply)throws ServiceException;
	
	public ResultDto getAftersaleApply(String orderNo)throws ServiceException;
	
	/******
	 * 根据ID 查询售后申请单
	 * @return
	 * @throws ServiceException
	 */
	public AftersaleApply findAftersaleApplyById(String id)throws ServiceException;
	
	public List<AftersalePickup> getAftersalPickupByPickNo(String pickuNo);
	/**
	 * 
	 * 雷------2016年9月20日
	 * @Title: findAftersalePickupByParams
	 * @Description: params.pickupNo取单号，params.id取货单ID，可任意传一个参数
	 * @param @param params
	 * @param @return
	 * @param @throws ServiceException    设定文件
	 * @return AftersalePickup    返回类型
	 * @throws
	 */
	public AftersalePickup findAftersalePickupByParams(Map<String, Object> params) throws ServiceException;
	/**
	 * 
	 * 雷------2016年9月20日
	 * @Title: findRefundInfo
	 * @Description: 查询退款单信息，可任意传一个参数
	 * @param @param id
	 * @param @param refundNo
	 * @param @return
	 * @param @throws ServiceException    设定文件
	 * @return AftersaleRefund    返回类型
	 * @throws
	 */
	public AftersaleRefund findRefundInfo(String id,String refundNo) throws ServiceException;
	/**
	 * 
	 * 雷------2016年9月20日
	 * @Title: findAftersaleApplyItemList
	 * @Description: 获取售后申请单详情集合
	 * @param @param aftersaleApplyItem
	 * @param @return
	 * @param @throws ServiceException    设定文件
	 * @return List<AftersaleApplyItem>    返回类型
	 * @throws
	 */
	public List<AftersaleApplyItem> findAftersaleApplyItemList(AftersaleApplyItem aftersaleApplyItem)throws ServiceException;
}

package com.ffzx.order.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.api.dto.AftersaleApply;
import com.ffzx.order.api.dto.AftersalePickup;


/**
 * aftersale_pickup数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-11 11:50:59
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface AftersalePickupService extends BaseCrudService {

	/******
	 * 获取取货单集合
	 * @param pageObj
	 * @param orderByField
	 * @param orderBy
	 * @param aftersalePickup
	 * @return
	 * @throws ServiceException
	 */
	public List<AftersalePickup> findAftersalePickupList(Page pageObj,String orderByField, String orderBy,AftersaleApply aftersaleApply,AftersalePickup pickup)throws ServiceException;
	
	/**
	 * 雷------2016年8月10日
	 * @Title: findAftersalePickupList_Like
	 * @Description: 获取取货单集合(8-5新需求)
	 * @param @param pageObj
	 * @param @param orderByFieldCreate
	 * @param @param orderBy
	 * @param @param aftresal
	 * @param @param pickup
	 * @param @return    设定文件
	 * @return List<AftersalePickup>    返回类型
	 * @throws
	 */
	public List<AftersalePickup> findAftersalePickupList_Like(Page pageObj, String orderByFieldCreate, String orderBy, AftersaleApply aftresal, AftersalePickup pickup);
	
	/******
	 * 新增售后取货单
	 * @param aftersalePickup
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode saveAftersalePickup(AftersalePickup aftersalePickup)throws ServiceException;
	
	/****
	 * 根据售后取货单号查询取货单信息
	 * @param pickupNo
	 * @return
	 * @throws ServiceException
	 */
	public AftersalePickup getAftersalPickupByNo(String pickupNo)throws ServiceException;
	
	/*******
	 * 根据取货单号修改状态
	 * @param params
	 * @return
	 */
	public int updatePickStatus(Map<String, Object> params)throws ServiceException;
	
	/******
	 * 根据ID 获取取货单信息和关联的申请单信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public AftersalePickup getAftersalePickupById(String id)throws ServiceException;
	
	/*****
	 * 多个参数获取取货单
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public AftersalePickup findAftersalePickupByParams(Map<String, Object> params)throws ServiceException;
	
	/****
	 * 根据取货单号删除取货单
	 * @param pickupNo
	 * @return
	 * @throws ServiceException
	 */
	public int deletePickupByPickupNo(String pickupNo)throws ServiceException;

	/**
	 * 雷------2016年8月16日
	 * @Title: cancelPick
	 * @Description: 删除（取消）取货单
	 * @param @param params
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int cancelPick(Map<String, Object> params);

	/**
	 * 雷------2016年9月7日
	 * @Title: getAftersalPickupByPickNo
	 * @Description: 线上bug，（救火处理）
	 * @param @param no
	 * @param @return    设定文件
	 * @return List<AftersalePickup>    返回类型
	 * @throws
	 */
	public List<AftersalePickup> getAftersalPickupByPickNo(String no);

	/**
	 * 雷------2016年9月8日
	 * @Title: getPickupStateByPickNo
	 * @Description: 售后取消审核，根据取货单查询状态
	 * @param @param pickupNo
	 * @param @return    设定文件
	 * @return AftersalePickup    返回类型
	 * @throws
	 */
	public AftersalePickup getPickupStateByPickNo(String pickupNo);


	
}
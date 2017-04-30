package com.ffzx.order.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.api.dto.AftersaleApply;
import com.ffzx.order.api.dto.OmsOrder;

/**
 * aftersale_apply数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-11 11:50:59
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface AftersaleApplyService extends BaseCrudService {
	
	/******
	 * 获取售后申请单列表
	 * @param pageObj
	 * @param orderByField
	 * @param orderBy
	 * @param apply
	 * @return
	 * @throws ServiceException
	 */
	public List<AftersaleApply> findList(Page pageObj, String orderByField, String orderBy,AftersaleApply apply)throws ServiceException;
	
	/**
	 * 
	 * 雷------2016年8月10日
	 * @Title: findList_Like
	 * @Description: 获取售后申请单列表(8-5的新需求)
	 * @param @param pageObj
	 * @param @param orderByField
	 * @param @param orderBy
	 * @param @param apply
	 * @param @return
	 * @param @throws ServiceException    设定文件
	 * @return List<AftersaleApply>    返回类型
	 * @throws
	 */
	public List<AftersaleApply> findList_Like(Page pageObj, String orderByField, String orderBy,AftersaleApply apply)throws ServiceException;
	
	
	/******
	 * 审核售后申请单
	 * @param apply
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode approve(AftersaleApply apply)throws ServiceException;
	
	/******
	 * 根据ID 查询售后申请单详情
	 * @return
	 * @throws ServiceException
	 */
	public AftersaleApply findAftersaleApplyById(String id)throws ServiceException;
	
	/******
	 * 根据ID 更新取货单号，或者退款单号
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int updatePickupNoOrRefundNo(Map<String, Object> params)throws ServiceException;
	
	/****
	 * 根据申请用户ID 获取售后单
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<AftersaleApply> findPageByParams(Page page,String orderByField,String orderBy,Map<String, Object> params)throws ServiceException;
	
	/***
	 * 新增售后申请单
	 * @param reason 
	 * @param desc 
	 * @param memberId 
	 * @param apply
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> saveAftersaleApply(Map<String, Object> map, String memberId, String desc, String reason)throws ServiceException;
	/**
	 * 雷------2016年7月28日
	 * @Title: cancelAudit
	 * @Description: 售后申请单，取消审核
	 * @param @param pickupNo
	 * @param @param orderNo
	 * @param @return    设定文件
	 * @return JSONObject    返回类型
	 * @throws
	 */
	public JSONObject cancelAudit(String pickupNo, String orderNo,String applyID)throws ServiceException;

	/**
	 * 雷------2016年8月10日
	 * @Title: beforeImportApplyList
	 * @Description: 售后管理：导出数据之前的检查
	 * @param @param params
	 * @param @return    设定文件
	 * @return JSONObject    返回类型
	 * @throws
	 */
	public JSONObject beforeImportApplyList(Map<String, Object> params);

	/**
	 * 雷------2016年8月12日
	 * @Title: importApplyList
	 * @Description: 售后管理：导出数据 
	 * @param @param params
	 * @param @return    设定文件
	 * @return Map<String,String>    返回类型
	 * @throws
	 */
	public List<Map> importApplyList(Map<String, Object> params);

	/**
	 * 雷------2016年9月18日
	 * @Title: calculationGifts
	 * @Description: 计算赠品的申请售后时信息
	 * @param @param mainCommodityId
	 * @param @param returnMainNum
	 * @param @return    设定文件
	 * @return JSONObject    返回类型
	 * @throws
	 */
	public JSONObject calculationGifts(String mainCommodityId, Integer returnMainNum);

	/**
	 * 雷------2016年11月3日
	 * @Title: getSourceOrder
	 * @Description: 根据售后申请单号，查询源订单售后信息
	 * @param @param applyNo
	 * @param @return    设定文件
	 * @return OmsOrder    返回类型
	 * @throws
	 */
	public OmsOrder getSourceOrder(String applyNo);

	/**
	 * 雷------2016年12月5日
	 * @Title: importRefundList
	 * @Description: 财务批量导出数据
	 * @param @param params
	 * @param @return    设定文件
	 * @return List<Map>    返回类型
	 * @throws
	 */
	public List<Map> importRefundList(Map<String, Object> params);
}
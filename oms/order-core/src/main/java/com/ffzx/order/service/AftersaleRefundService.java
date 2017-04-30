package com.ffzx.order.service;

import java.math.BigDecimal;
import java.util.List;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.order.api.dto.AftersaleRefund;

/**
 * aftersale_refund数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-11 11:50:59
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface AftersaleRefundService extends BaseCrudService {
	/********
	 * 获取退款单集合
	 * 
	 * @param pageObj
	 * @param orderByField
	 * @param orderBy
	 * @param aftersaleRefund
	 * @return
	 * @throws ServiceException
	 */
	public List<AftersaleRefund> findList(Page pageObj, String orderByField, String orderBy, AftersaleRefund aftersaleRefund) throws ServiceException;

	/**
	 * 
	 * 雷------2016年8月10日
	 * 
	 * @Title: findList_Like
	 * @Description: 获取退款单集合(8-5新需求)
	 * @param @param pageObj
	 * @param @param orderByField
	 * @param @param orderBy
	 * @param @param aftersaleRefund
	 * @param @return
	 * @param @throws ServiceException 设定文件
	 * @return List<AftersaleRefund> 返回类型
	 * @throws
	 */
	public List<AftersaleRefund> findList_Like(Page pageObj, String orderByField, String orderBy, AftersaleRefund aftersaleRefund) throws ServiceException;

	/**
	 * 获取退款单详情
	 * 
	 * @Title: findRefundInfo
	 * @param id
	 *            退款单id
	 * @param refundNo
	 *            退款单编码
	 * @throws ServiceException
	 * @return AftersaleRefund 返回类型
	 */
	public AftersaleRefund findRefundInfo(String id, String refundNo) throws ServiceException;

	/******
	 * 审核退款单
	 * 
	 * @param remarks
	 * @param orderNo
	 * @param orderId
	 * @param refundId
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode approveAftersaleRefund(String refundId, String orderId, String orderNo, String remarks) throws ServiceException;

	/******
	 * 确认付款
	 * 
	 * @param orderNo
	 * @param orderId
	 * @Title: confirmPayment
	 * @param aftersaleRefund
	 *            退款单
	 * @param record
	 *            订单操作记录
	 * @param refundId
	 * @throws ServiceException
	 * @return ServiceCode 返回类型
	 */
	public ServiceCode confirmPayment(String orderId, String orderNo, BigDecimal confirmPayment, String refundId) throws ServiceException;

	/*****
	 * 根据退款单号删除退款单
	 * 
	 * @param refundNo
	 * @return
	 * @throws ServiceException
	 */
	public int deleteRefund(String refundNo) throws ServiceException;

	/**
	 * 
	 * 雷------2016年11月7日
	 * @Title: refundTFT
	 * @Description: 腾付通退款回写
	 * @param @param orderNo
	 * @param @param payPrice
	 * @param @param refundNo
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean refundTFT(String orderNo, BigDecimal payPrice, String refundNo,SysUser user);

}
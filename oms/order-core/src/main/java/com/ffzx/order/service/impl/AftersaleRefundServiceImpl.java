package com.ffzx.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.order.api.dto.AftersaleApplyItem;
import com.ffzx.order.api.dto.AftersaleRefund;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderRecord;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.enums.OrderStatusEnum;
import com.ffzx.order.api.enums.OrderTypeEnum;
import com.ffzx.order.api.enums.PayTypeEnum;
import com.ffzx.order.common.AftersaleAuxiliary;
import com.ffzx.order.constant.OrderDetailStatusConstant;
import com.ffzx.order.mapper.AftersaleApplyMapper;
import com.ffzx.order.mapper.AftersaleRefundMapper;
import com.ffzx.order.mapper.OmsOrderRecordMapper;
import com.ffzx.order.mq.producer.AftersaleToMemberOtherMq;
import com.ffzx.order.service.AftersaleApplyItemService;
import com.ffzx.order.service.AftersaleRefundService;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.order.service.OmsOrderdetailService;
import com.ffzx.order.utils.pingxx.Pay;
import com.ffzx.pay.api.service.TFTPayApiService;

/**
 * 
 * @author ffzx
 * @date 2016-05-11 11:50:59
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("aftersaleRefundService")
public class AftersaleRefundServiceImpl extends BaseCrudServiceImpl implements AftersaleRefundService {

	@Resource
	private AftersaleRefundMapper aftersaleRefundMapper;

	@Override
	public CrudMapper init() {
		return aftersaleRefundMapper;
	}

	@Resource
	private OmsOrderRecordMapper omsOrderRecordMapper;
	@Resource
	private AftersaleApplyMapper aftersaleApplyMapper;
	@Autowired
	private OmsOrderService omsOrderService;
	@Autowired
	private OmsOrderdetailService omsOrderDetailService;
	@Autowired
	private AftersaleApplyItemService aftersaleApplyItemService;

	@Autowired
	private TFTPayApiService tFTPayApiService;

	@Autowired
	private AftersaleToMemberOtherMq aftersaleToMemberOtherMq;

	@Override
	public List<AftersaleRefund> findList(Page pageObj, String orderByField, String orderBy, AftersaleRefund refund) throws ServiceException {
		// 设置过滤条件
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtil.isNotNull(refund)) {
			// 申请人手机号
			if (StringUtil.isNotNull(refund.getApplyPersonPhone())) {
				params.put("applyPersonPhone", refund.getApplyPersonPhone());
			}
			// 状态
			if (StringUtil.isNotNull(refund.getRefundStatus())) {
				params.put("refundStatus", refund.getRefundStatus());
			}

			try {
				if (StringUtil.isNotNull(refund.getApplyStartDate())) {
					params.put("applyStartDate", refund.getApplyStartDate());
				}
				if (StringUtil.isNotNull(refund.getApplyEndDate())) {
					params.put("applyEndDate", refund.getApplyEndDate());
				}
				// 仓储审核时间(开始、结束)
				if (StringUtil.isNotNull(refund.getStorageApproveEndDate())) {
					params.put("storageApproveEndDate", DateUtil.parseTime(refund.getStorageApproveEndDate()));
				}
				if (StringUtil.isNotNull(refund.getStorageApproveStartDate())) {
					params.put("storageApproveStartDate", DateUtil.parseTime(refund.getStorageApproveStartDate()));
				}
				// 客服审核时间(开始、结束)
				if (StringUtil.isNotNull(refund.getServiceApproveEndDate())) {
					params.put("serviceApproveEndDate", DateUtil.parseTime(refund.getServiceApproveEndDate()));
				}
				if (StringUtil.isNotNull(refund.getServiceApproveStartDate())) {
					params.put("serviceApproveStartDate", DateUtil.parseTime(refund.getServiceApproveStartDate()));
				}
			} catch (Exception e) {
				logger.error("AftersaleRefundServiceImpl.findList() Exception=》", e);
				throw new ServiceException(e);
			}
			// 订单编号
			if (StringUtil.isNotNull(refund.getOrderNo())) {
				params.put("orderNo", refund.getOrderNo());
			}
			// 售后服务类型
			if (StringUtil.isNotNull(refund.getApplyType())) {
				params.put("applyType", refund.getApplyType());
			}
			// 申请人
			if (StringUtil.isNotNull(refund.getApplyPersonName())) {
				params.put("applyPersonName", refund.getApplyPersonName());
			}
			if (StringUtil.isNotNull(refund.getApplyNo())) {
				params.put("applyNo", refund.getApplyNo());
			}
			if (StringUtil.isNotNull(refund.getPickupNo())) {
				params.put("pickupNo", refund.getPickupNo());
			}
			if (StringUtil.isNotNull(refund.getRefundNo())) {
				params.put("refundNo", refund.getRefundNo());
			}
			params.put("delFlag", OrderDetailStatusConstant.NO);
		}

		return this.aftersaleRefundMapper.selectByPage(pageObj, orderByField, orderBy, params);
	}

	/**
	 * 
	 * 雷-----2016年8月10日 (非 Javadoc)
	 * <p>
	 * Title: findList_Like
	 * </p>
	 * <p>
	 * Description:获取退款单集合(8-5新需求)
	 * </p>
	 * 
	 * @param pageObj
	 * @param orderByField
	 * @param orderBy
	 * @param refund
	 * @return
	 * @throws ServiceException
	 * @see com.ffzx.order.service.AftersaleRefundService#findList_Like(com.ffzx.commerce.framework.page.Page,
	 *      java.lang.String, java.lang.String,
	 *      com.ffzx.order.api.dto.AftersaleRefund)
	 */
	@Override
	public List<AftersaleRefund> findList_Like(Page pageObj, String orderByField, String orderBy, AftersaleRefund refund) throws ServiceException {
		// 设置过滤条件
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtil.isNotNull(refund)) {
			// 申请人手机号
			if (StringUtil.isNotNull(refund.getApplyPersonPhone())) {
				params.put("applyPersonPhone_like", refund.getApplyPersonPhone());
			}
			// 状态
			if (StringUtil.isNotNull(refund.getRefundStatus())) {
				params.put("refundStatus", refund.getRefundStatus());
			}

			try {
				if (StringUtil.isNotNull(refund.getApplyStartDate())) {
					params.put("applyStartDate", refund.getApplyStartDate());
				}
				if (StringUtil.isNotNull(refund.getApplyEndDate())) {
					params.put("applyEndDate", refund.getApplyEndDate());
				}
				// 仓储审核时间(开始、结束)
				if (StringUtil.isNotNull(refund.getStorageApproveEndDate())) {
					params.put("storageApproveEndDate", DateUtil.parseTime(refund.getStorageApproveEndDate()));
				}
				if (StringUtil.isNotNull(refund.getStorageApproveStartDate())) {
					params.put("storageApproveStartDate", DateUtil.parseTime(refund.getStorageApproveStartDate()));
				}
				// 客服审核时间(开始、结束)
				if (StringUtil.isNotNull(refund.getServiceApproveEndDate())) {
					params.put("serviceApproveEndDate", DateUtil.parseTime(refund.getServiceApproveEndDate()));
				}
				if (StringUtil.isNotNull(refund.getServiceApproveStartDate())) {
					params.put("serviceApproveStartDate", DateUtil.parseTime(refund.getServiceApproveStartDate()));
				}
			} catch (Exception e) {
				logger.error("AftersaleRefundServiceImpl.findList() Exception=》", e);
				throw new ServiceException(e);
			}
			// 订单编号
			if (StringUtil.isNotNull(refund.getOrderNo())) {
				params.put("orderNo_like", refund.getOrderNo());
			}
			// 售后服务类型
			if (StringUtil.isNotNull(refund.getApplyType())) {
				params.put("applyType", refund.getApplyType());
			}
			// 申请人
			if (StringUtil.isNotNull(refund.getApplyPersonName())) {
				params.put("applyPersonName", refund.getApplyPersonName());
			}
			if (StringUtil.isNotNull(refund.getApplyNo())) {
				params.put("applyNo_like", refund.getApplyNo());
			}
			if (StringUtil.isNotNull(refund.getPickupNo())) {
				params.put("pickupNo_like", refund.getPickupNo());
			}
			if (StringUtil.isNotNull(refund.getRefundNo())) {
				params.put("refundNo_like", refund.getRefundNo());
			}
			if(null!=refund.getAftersaleApply() && StringUtils.isNotBlank(refund.getAftersaleApply().getCommodityBarcode())){
				params.put("commodityBarcode", refund.getAftersaleApply().getCommodityBarcode());
			}
			params.put("delFlag", OrderDetailStatusConstant.NO);
		}

		return this.aftersaleRefundMapper.selectByPage(pageObj, orderByField, orderBy, params);
	}

	/**
	 * 
	 * 雷-----2016年9月22日 (非 Javadoc)
	 * <p>
	 * Title: approveAftersaleRefund
	 * </p>
	 * <p>
	 * Description:退款单审核
	 * </p>
	 * 
	 * @param refundId
	 * @param orderId
	 * @param orderNo
	 * @param remarks
	 * @return
	 * @throws ServiceException
	 * @see com.ffzx.order.service.AftersaleRefundService#approveAftersaleRefund(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public ServiceCode approveAftersaleRefund(String refundId, String orderId, String orderNo, String remarks) throws ServiceException {
		int result = 0;
		/**
		 * 2016-09-22，雷--调优
		 */
		// 查询获取退款单对象
		AftersaleRefund refund = findById(refundId);
		if (OrderDetailStatusConstant.REFUND_APPROVE_NO.equals(refund.getRefundStatus())) {
			Date date = new Date();
			refund = AftersaleAuxiliary.setRefund(refund, date, OrderDetailStatusConstant.REFUND_APPROVE_SUC, RedisWebUtils.getLoginUser());
			// 修改退款单状态
			result = aftersaleRefundMapper.updateByPrimaryKeySelective(refund);
			if (result > 0) {
				result = omsOrderRecordMapper.insertSelective(AftersaleAuxiliary.setRecord(orderId, orderNo, "售后退款单：审核通过 " + remarks, date, RedisWebUtils.getLoginUser()));
			}
		}
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Override
	public AftersaleRefund findRefundInfo(String id, String refundNo) throws ServiceException {
		// 设置查询条件
		Map<String, Object> params = new HashMap<String, Object>();
		// 退款单id
		if (StringUtil.isNotNull(id)) {
			params.put("id", id);
		}
		// 退款单编码
		if (StringUtil.isNotNull(refundNo)) {
			params.put("refundNo", refundNo);
		}

		return this.aftersaleRefundMapper.findRefundInfo(params);
	}

	/**
	 * 
	 * 雷-----2016年9月21日 (非 Javadoc)
	 * <p>
	 * Title: confirmPayment
	 * </p>
	 * <p>
	 * Description: 退款的方法重构
	 * </p>
	 * 
	 * @param record
	 * @param payPrice
	 * @param refundId
	 * @return
	 * @throws ServiceException
	 * @see com.ffzx.order.service.AftersaleRefundService#confirmPayment(com.ffzx.order.api.dto.OmsOrderRecord,
	 *      java.math.BigDecimal, java.lang.String)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServiceCode confirmPayment(String orderId, String orderNo, BigDecimal payPrice, String refundId) throws ServiceException {
		int result = 0;
		// 时间
		Date date = new Date();
		try {
			// 查询获取退款单对象
			AftersaleRefund refund = findById(refundId);
			/**
			 * 2016-08-24、09-21雷，修复多次退款的问题
			 */
			if (!OrderDetailStatusConstant.REFUND_PAY.equals(refund.getRefundStatus())) {
				refund = AftersaleAuxiliary.setRefund(refund, date, OrderDetailStatusConstant.REFUND_PAY, RedisWebUtils.getLoginUser());
				OmsOrderRecord record = AftersaleAuxiliary.setRecord(orderId, orderNo, "退款单：退款成功", date, RedisWebUtils.getLoginUser());
				// 修改退款单状态
				result = aftersaleRefundMapper.updateByPrimaryKeySelective(refund);
				// 获取售后申请详情的商品列表
				AftersaleApplyItem item = new AftersaleApplyItem();
				item.setApplyId(refund.getAftersaleApply().getId());
				List<AftersaleApplyItem> aftersaleApplyItemList = aftersaleApplyItemService.findAftersaleApplyItemList(null, null, null, item);
				// 更新订单状态
				OmsOrder order = null;
				if (result > 0) {
					result = 0;
					order = omsOrderService.findById(record.getOrderId());
					if (StringUtil.isNotNull(order)) {
						// 获取订单详情
						Map<String, Object> params = new HashMap<>();
						params.put("orderId", order.getId());
						List<OmsOrderdetail> orderdetailList = omsOrderDetailService.findByBiz(params);
						logger.info("订单编号：【" + record.getOrderNo() + "】=》更新订单状态");
						// 如果订单状态是：交易关闭，则订单是(退货已收到货)只修改订单商品状态；否则(退款代收货)，要修改订单和订单的商品状态
						/**
						 * 当前订单状态为交易完成，刚说明是：退货
						 */
						if (OrderStatusEnum.TRANSACTION_COMPLETION.getValue().equals(order.getStatus())) {
							// 遍历，更新状态
							for (AftersaleApplyItem applyItem : aftersaleApplyItemList) {
								for (OmsOrderdetail orderdetail : orderdetailList) {
									// 判断存在条形码，则改商品状态
									if (applyItem.getSkuId().equals(orderdetail.getSkuId())) {
										orderdetail.setOrderDetailStatus(OrderDetailStatusConstant.STATUS_REFSUC);
										orderdetail.setLastUpdateDate(new Date()); // 最后更新时间
										orderdetail.setLastUpdateBy(RedisWebUtils.getLoginUser()); // 最后更新人
										result += omsOrderDetailService.modifyById(orderdetail);
									}
								}
							}
						} else {
							order.setStatus(OrderStatusEnum.TRANSACTION_CLOSED.getValue());
							order.setLastUpdateDate(new Date()); // 最后更新时间
							order.setLastUpdateBy(RedisWebUtils.getLoginUser()); // 最后更新人
							omsOrderService.updateStatus(order);
							for (OmsOrderdetail orderdetail : orderdetailList) {
								orderdetail.setOrderDetailStatus(OrderDetailStatusConstant.STATUS_REFSUC);
								orderdetail.setLastUpdateDate(new Date()); // 最后更新时间
								orderdetail.setLastUpdateBy(RedisWebUtils.getLoginUser()); // 最后更新人
								result += omsOrderDetailService.modifyById(orderdetail);
							}
						}
					} else {
						throw new Exception();
					}
				}
				// 更新售后申请单详情状态
				if (result > 0) {
					result = 0;
					logger.info("订单编号：【" + record.getOrderNo() + "】=》更新售后申请单详情状态");
					for (AftersaleApplyItem applyItem : aftersaleApplyItemList) {
						applyItem.setAftersaleStatus(OrderDetailStatusConstant.STATUS_REFSUC);
						result += aftersaleApplyItemService.modifyById(applyItem);
					}
				}
				// 新增订单记录
				if (result > 0) {
					logger.info("订单编号：【" + record.getOrderNo() + "】=》新增订单操作记录");
					record.setId(UUIDGenerator.getUUID()); // 生成id
					result = omsOrderRecordMapper.insertSelective(record);
				}
				// 设置退款金额
				order.setActualPrice(payPrice);
				// 调用付款接口
				if (order.getPayType().getValue().equals(PayTypeEnum.WXPAY.getValue()) && (!OrderTypeEnum.EXCHANGE_ORDER.equals(order.getOrderType()))) {
					logger.info("订单编号：【" + record.getOrderNo() + "】=》订单退款，调用支付接口");
					Pay.refundApply(order);
				} else if (order.getPayType().getValue().equals(PayTypeEnum.TFTFASTPAY.getValue()) && (!OrderTypeEnum.EXCHANGE_ORDER.equals(order.getOrderType()))) {
					/**
					 * 腾付通退款接口
					 */
					if (!this.refundTFT(order, refund)) {
						throw new ServiceException("腾付通退款不成功！");
					}
				}

				/**
				 * 发送给退款、退货的广播
				 */
				aftersaleToMemberOtherMq.sendRefundMember(refund, order, RedisWebUtils.getLoginUser());
			}
		} catch (Exception e) {
			result = 0;
			logger.error("订单编号：【" + orderNo + "】=》订单系统-售后管理-退款(确认付款)", e);
			throw new ServiceException(e);
		}

		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	/**
	 * 
	 * 雷-----2016年11月7日 (非 Javadoc)
	 * <p>
	 * Title: refundTFT
	 * </p>
	 * <p>
	 * Description:腾付通退款回写
	 * </p>
	 * 
	 * @param orderNo
	 * @param payPrice
	 * @param refundNo
	 * @param user
	 * @return
	 * @throws ServiceException
	 * @see com.ffzx.order.service.AftersaleRefundService#refundTFT(java.lang.String,
	 *      java.math.BigDecimal, java.lang.String,
	 *      com.ffzx.commerce.framework.system.entity.SysUser)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean refundTFT(String orderNo, BigDecimal payPrice, String refundNo, SysUser user) throws ServiceException {
		int result = 0;
		// 时间
		Date date = new Date();
		try {
			// 查询获取退款单对象
			AftersaleRefund refund = findRefundInfo(null, refundNo);
			/**
			 * 2016-08-24、09-21雷，修复多次退款的问题
			 */
			if (!OrderDetailStatusConstant.REFUND_PAY.equals(refund.getRefundStatus())) {
				OmsOrder order = omsOrderService.findOrderInfo(orderNo);
				refund = AftersaleAuxiliary.setRefund(refund, date, OrderDetailStatusConstant.REFUND_PAY, user);
				OmsOrderRecord record = AftersaleAuxiliary.setRecord(order.getId(), orderNo, "退款单：退款成功", date, user);
				// 修改退款单状态
				result = aftersaleRefundMapper.updateByPrimaryKeySelective(refund);
				// 获取售后申请详情的商品列表
				AftersaleApplyItem item = new AftersaleApplyItem();
				item.setApplyId(refund.getAftersaleApply().getId());
				List<AftersaleApplyItem> aftersaleApplyItemList = aftersaleApplyItemService.findAftersaleApplyItemList(null, null, null, item);
				if (result > 0) {
					result = 0;
					if (StringUtil.isNotNull(order)) {
						// 获取订单详情
						Map<String, Object> params = new HashMap<>();
						params.put("orderId", order.getId());
						List<OmsOrderdetail> orderdetailList = omsOrderDetailService.findByBiz(params);
						logger.info("订单编号：【" + record.getOrderNo() + "】=》更新订单状态");
						// 如果订单状态是：交易关闭，则订单是(退货已收到货)只修改订单商品状态；否则(退款代收货)，要修改订单和订单的商品状态
						/**
						 * 当前订单状态为交易完成，刚说明是：退货
						 */
						if (OrderStatusEnum.TRANSACTION_COMPLETION.getValue().equals(order.getStatus())) {
							// 遍历，更新状态
							for (AftersaleApplyItem applyItem : aftersaleApplyItemList) {
								for (OmsOrderdetail orderdetail : orderdetailList) {
									// 判断存在条形码，则改商品状态
									if (applyItem.getSkuId().equals(orderdetail.getSkuId())) {
										orderdetail.setOrderDetailStatus(OrderDetailStatusConstant.STATUS_REFSUC);
										orderdetail.setLastUpdateDate(new Date()); // 最后更新时间
										orderdetail.setLastUpdateBy(user); // 最后更新人
										result += omsOrderDetailService.modifyById(orderdetail);
									}
								}
							}
						} else {
							order.setStatus(OrderStatusEnum.TRANSACTION_CLOSED.getValue());
							order.setLastUpdateDate(new Date()); // 最后更新时间
							order.setLastUpdateBy(user); // 最后更新人
							omsOrderService.updateStatus(order);
							for (OmsOrderdetail orderdetail : orderdetailList) {
								orderdetail.setOrderDetailStatus(OrderDetailStatusConstant.STATUS_REFSUC);
								orderdetail.setLastUpdateDate(new Date()); // 最后更新时间
								orderdetail.setLastUpdateBy(user); // 最后更新人
								result += omsOrderDetailService.modifyById(orderdetail);
							}
						}
					} else {
						throw new Exception();
					}
				}
				// 更新售后申请单详情状态
				if (result > 0) {
					result = 0;
					logger.info("订单编号：【" + record.getOrderNo() + "】=》更新售后申请单详情状态,腾付通退款回写");
					for (AftersaleApplyItem applyItem : aftersaleApplyItemList) {
						applyItem.setAftersaleStatus(OrderDetailStatusConstant.STATUS_REFSUC);
						result += aftersaleApplyItemService.modifyById(applyItem);
					}
				}
				// 新增订单记录
				if (result > 0) {
					logger.info("订单编号：【" + record.getOrderNo() + "】=》新增订单操作记录,腾付通退款回写");
					record.setId(UUIDGenerator.getUUID()); // 生成id
					result = omsOrderRecordMapper.insertSelective(record);
				}
				// 设置退款金额
				order.setActualPrice(payPrice);
				/**
				 * 发送给退款、退货的广播
				 */
				aftersaleToMemberOtherMq.sendRefundMember(refund, order, user);
			} else {
				return true;
			}
		} catch (Exception e) {
			result = 0;
			logger.error("订单编号：【" + orderNo + "】=》腾付通退款回写", e);
			throw new ServiceException(e);
		}

		return result > 0 ? true : false;
	}

	/**
	 * 
	 * 雷------2016年10月24日
	 * 
	 * @Title: refundTFT
	 * @Description: 腾付通退款接口
	 * @param @param order
	 * @param @return
	 * @param @throws ServiceException 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean refundTFT(OmsOrder order, AftersaleRefund refund) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeUserId", order.getMemberId());
		params.put("tradeUserName", order.getMemberPhone());
		params.put("amout", order.getActualPrice());
		params.put("orderCode", order.getOrderNo());
		params.put("refundNo", refund.getRefundNo());
		return this.tFTPayApiService.refundOrder(params);
	}

	/**
	 * 
	 * 雷-----2016年10月24日 (非 Javadoc)
	 * <p>
	 * Title: deleteRefund
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param refundNo
	 * @return
	 * @throws ServiceException
	 * @see com.ffzx.order.service.AftersaleRefundService#deleteRefund(java.lang.String)
	 */
	@Override
	public int deleteRefund(String refundNo) throws ServiceException {

		return this.aftersaleRefundMapper.deleteRefundByNo(refundNo);
	}

}
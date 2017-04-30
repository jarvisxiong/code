package com.ffzx.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ffzx.basedata.api.dto.Address;
import com.ffzx.basedata.api.service.AddressApiService;
import com.ffzx.basedata.api.service.CodeRuleApiService;
import com.ffzx.bms.api.dto.OrderParam;
import com.ffzx.bms.api.service.OrderProcessManagerApiService;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.order.api.dto.AftersaleApply;
import com.ffzx.order.api.dto.AftersaleApplyItem;
import com.ffzx.order.api.dto.AftersalePickup;
import com.ffzx.order.api.dto.AftersaleRefund;
import com.ffzx.order.api.dto.InboundAftersaleReqApiVo;
import com.ffzx.order.api.dto.InboundAftersaleReqItemsApiVo;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderRecord;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.enums.AfterSaleStatusType;
import com.ffzx.order.api.enums.OrderStatusEnum;
import com.ffzx.order.api.service.AftersaleApplyInstorageMqApiService;
import com.ffzx.order.api.service.OutboundDeliveryStatusMqApiService;
import com.ffzx.order.common.AftersaleAuxiliary;
import com.ffzx.order.constant.OrderDetailStatusConstant;
import com.ffzx.order.constant.OrderStatusConstant;
import com.ffzx.order.mapper.AftersaleApplyMapper;
import com.ffzx.order.mapper.OmsOrderdetailMapper;
import com.ffzx.order.service.AftersaleApplyItemService;
import com.ffzx.order.service.AftersaleApplyService;
import com.ffzx.order.service.AftersalePickupService;
import com.ffzx.order.service.AftersaleRefundService;
import com.ffzx.order.service.OmsOrderRecordService;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.order.service.OmsOrderdetailService;
import com.ffzx.wms.api.dto.InboundAftersaleReqCancelApiVo;
import com.ffzx.wms.api.dto.Warehouse;
import com.ffzx.wms.api.service.InboundAftersaleReqCancelApiService;
import com.ffzx.wms.api.service.OutboundSalesDeliveryApiService;

/**
 * 
 * @author ffzx
 * @date 2016-05-11 11:50:59
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("aftersaleApplyService")
public class AftersaleApplyServiceImpl extends BaseCrudServiceImpl implements AftersaleApplyService {
	// 记录日志
	protected final Logger logger = LoggerFactory.getLogger(AftersaleApplyServiceImpl.class);
	@Resource
	private AftersaleApplyMapper aftersaleApplyMapper;
	@Autowired
	private CodeRuleApiService codeRuleApiService;
	@Autowired
	private AftersalePickupService aftersalePickupService;
	@Autowired
	private AftersaleRefundService aftersaleRefundService;
	@Autowired
	private AftersaleApplyItemService aftersaleApplyItemService;
	@Autowired
	private AftersaleApplyInstorageMqApiService aftersaleApplyInstorageMqApiService;
	@Autowired
	private OutboundSalesDeliveryApiService outboundSalesDeliveryApiService;
	@Autowired
	private OutboundDeliveryStatusMqApiService outboundDeliveryStatusMqApiService;
	@Autowired
	private InboundAftersaleReqCancelApiService inboundAftersaleReqCancelApiService;
	@Autowired
	private OmsOrderService omsOrderService;
	@Autowired
	private OmsOrderRecordService omsOrderRecordService;
	@Autowired
	private OmsOrderdetailService omsOrderdetailService;
	@Autowired
	private OmsOrderdetailMapper omsOrderdetailMapper;
	@Autowired
	private AddressApiService addressApiService;
	@Resource(name = "orderProcessManagerApiService")
	private OrderProcessManagerApiService orderProcessManagerApiService;

	@Override
	public CrudMapper init() {
		return aftersaleApplyMapper;
	}

	/**
	 * 雷-----2016年8月12日 (非 Javadoc)
	 * <p>
	 * Title: importApplyList
	 * </p>
	 * <p>
	 * Description:售后管理：导出数据
	 * </p>
	 * 
	 * @param params
	 * @return
	 * @see com.ffzx.order.service.AftersaleApplyService#importApplyList(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> importApplyList(Map<String, Object> params) {
		params = this.handleParams(params);
		List<Map> list = this.aftersaleApplyMapper.importApplyList(params);
		return list;
	}

	/**
	 * 雷-----2016年12月5日 (非 Javadoc)
	 * <p>
	 * Title: importRefundList
	 * </p>
	 * <p>
	 * Description:财务批量导出数据
	 * </p>
	 * 
	 * @param params
	 * @return
	 * @see com.ffzx.order.service.AftersaleApplyService#importRefundList(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> importRefundList(Map<String, Object> params) {
		params = this.handleParams(params);
		List<Map> list = this.aftersaleApplyMapper.importRefundList(params);
		return list;
	}

	/**
	 * 雷-----2016年8月10日 (非 Javadoc)
	 * <p>
	 * Title: beforeImportApplyList
	 * </p>
	 * <p>
	 * Description: 售后管理：导出数据之前的检查
	 * </p>
	 * 
	 * @param params
	 * @return
	 * @see com.ffzx.order.service.AftersaleApplyService#beforeImportApplyList(java.util.Map)
	 */
	@Override
	public JSONObject beforeImportApplyList(Map<String, Object> params) {
		JSONObject msgObgect = new JSONObject();
		params = this.handleParams(params);
		int total = this.aftersaleApplyMapper.beforeImportApplyList(params);
		if (total > 50000) {
			msgObgect.put("flag", false);
			msgObgect.put("msg", "不能导出数据大于50000");
		} else {
			msgObgect.put("flag", true);
			msgObgect.put("msg", "正常");
		}
		return msgObgect;
	}

	/**
	 * 雷------2016年8月10日
	 * 
	 * @Title: handleParams
	 * @Description: 参数处理
	 * @param @param params 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private Map<String, Object> handleParams(Map<String, Object> params) {
		System.out.println(params);
		if (params.containsKey("refundNo")) {
			params.put("refundNo_like", params.get("refundNo"));
			params.remove("refundNo");
		}
		if (params.containsKey("applyNo")) {
			params.put("applyNo_like", params.get("applyNo"));
			params.remove("applyNo");
		}
		if (params.containsKey("applyPersonPhone")) {
			params.put("applyPersonPhone_like", params.get("applyPersonPhone"));
			params.remove("applyPersonPhone");
		}
		if (params.containsKey("pickupNo")) {
			params.put("pickupNo_like", params.get("pickupNo"));
			params.remove("pickupNo");
		}
		if (params.containsKey("orderNo")) {
			params.put("orderNo_like", params.get("orderNo"));
			params.remove("orderNo");
		}
		if (params.containsKey("exchangeOrderNo")) {
			params.put("exchangeOrderNo_like", params.get("exchangeOrderNo"));
			params.remove("exchangeOrderNo");
		}
		if (params.containsKey("order.servicePoint")) {
			params.put("servicePoint", params.get("order.servicePoint"));
			params.remove("order.servicePoint");
		}
		params.put("delFlag", OrderDetailStatusConstant.NO);
		return params;
	}

	@Override
	public List<AftersaleApply> findList(Page pageObj, String orderByField, String orderBy, AftersaleApply apply) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		// 申请人手机号
		if (StringUtil.isNotNull(apply.getApplyPersonPhone())) {
			params.put("applyPersonPhone", apply.getApplyPersonPhone());
		}
		// 申请状态
		if (StringUtil.isNotNull(apply.getApplyStatus())) {
			params.put("applyStatus", apply.getApplyStatus());
		}

		try {
			if (StringUtil.isNotNull(apply.getApplyStartDate())) {
				params.put("applyStartDate", apply.getApplyStartDate());
			}
			if (StringUtil.isNotNull(apply.getApplyEndDate())) {
				params.put("applyEndDate", apply.getApplyEndDate());
			}
			if (StringUtil.isNotNull(apply.getStorageApproveEndDate())) {
				params.put("storageApproveEndDate", apply.getStorageApproveEndDate());
			}
			if (StringUtil.isNotNull(apply.getStorageApproveStartDate())) {
				params.put("storageApproveStartDate", apply.getStorageApproveStartDate());
			}
			if (StringUtil.isNotNull(apply.getServiceApproveEndDate())) {
				params.put("serviceApproveEndDate", apply.getServiceApproveEndDate());
			}
			if (StringUtil.isNotNull(apply.getServiceApproveStartDate())) {
				params.put("serviceApproveStartDate", apply.getServiceApproveStartDate());
			}
		} catch (Exception e) {
			logger.error("AftersaleApplyServiceImpl.findList() Exception=》订单系统-售后管理-获取售后申请单-时间转换", e);
			throw new ServiceException(e);
		}
		if (StringUtil.isNotNull(apply.getOrderNo())) {
			params.put("orderNo", apply.getOrderNo());
		}
		if (StringUtil.isNotNull(apply.getApplyType())) {
			params.put("applyType", apply.getApplyType());
		}
		if (StringUtil.isNotNull(apply.getApplyPersonName())) {
			params.put("applyPersonName", apply.getApplyPersonName());
		}
		if (StringUtil.isNotNull(apply.getApplyNo())) {
			params.put("applyNo", apply.getApplyNo());
		}
		if (StringUtil.isNotNull(apply.getPickupNo())) {
			params.put("pickupNo", apply.getPickupNo());
		}
		if (StringUtil.isNotNull(apply.getRefundNo())) {
			params.put("refundNo", apply.getRefundNo());
		}
		if (StringUtil.isNotNull(apply.getExchangeOrderNo())) {
			params.put("exchangeOrderNo", apply.getExchangeOrderNo());
		}
		params.put("delFlag", OrderDetailStatusConstant.NO);
		return this.aftersaleApplyMapper.selectByPage(pageObj, orderByField, orderBy, params);
	}

	/**
	 * 
	 * 雷-----2016年8月10日 (非 Javadoc)
	 * <p>
	 * Title: findList_Like
	 * </p>
	 * <p>
	 * Description:获取售后申请单列表(8-5的新需求)
	 * </p>
	 * 
	 * @param pageObj
	 * @param orderByField
	 * @param orderBy
	 * @param apply
	 * @return
	 * @throws ServiceException
	 * @see com.ffzx.order.service.AftersaleApplyService#findList_Like(com.ffzx.commerce.framework.page.Page,
	 *      java.lang.String, java.lang.String,
	 *      com.ffzx.order.api.dto.AftersaleApply)
	 */
	@Override
	public List<AftersaleApply> findList_Like(Page pageObj, String orderByField, String orderBy, AftersaleApply apply) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		// 申请人手机号
		if (StringUtil.isNotNull(apply.getApplyPersonPhone())) {
			params.put("applyPersonPhone_like", apply.getApplyPersonPhone());
		}
		// 申请状态
		if (StringUtil.isNotNull(apply.getApplyStatus())) {
			params.put("applyStatus", apply.getApplyStatus());
		}

		try {
			if (StringUtil.isNotNull(apply.getApplyStartDate())) {
				params.put("applyStartDate", apply.getApplyStartDate());
			}
			if (StringUtil.isNotNull(apply.getApplyEndDate())) {
				params.put("applyEndDate", apply.getApplyEndDate());
			}
			if (StringUtil.isNotNull(apply.getStorageApproveEndDate())) {
				params.put("storageApproveEndDate", apply.getStorageApproveEndDate());
			}
			if (StringUtil.isNotNull(apply.getStorageApproveStartDate())) {
				params.put("storageApproveStartDate", apply.getStorageApproveStartDate());
			}
			if (StringUtil.isNotNull(apply.getServiceApproveEndDate())) {
				params.put("serviceApproveEndDate", apply.getServiceApproveEndDate());
			}
			if (StringUtil.isNotNull(apply.getServiceApproveStartDate())) {
				params.put("serviceApproveStartDate", apply.getServiceApproveStartDate());
			}
		} catch (Exception e) {
			logger.error("AftersaleApplyServiceImpl.findList() Exception=》订单系统-售后管理-获取售后申请单-时间转换", e);
			throw new ServiceException(e);
		}
		if (StringUtil.isNotNull(apply.getOrderNo())) {
			params.put("orderNo_like", apply.getOrderNo());
		}
		if (StringUtil.isNotNull(apply.getApplyType())) {
			params.put("applyType", apply.getApplyType());
		}
		if (StringUtil.isNotNull(apply.getApplyPersonName())) {
			params.put("applyPersonName", apply.getApplyPersonName());
		}
		if (StringUtil.isNotNull(apply.getApplyNo())) {
			params.put("applyNo_like", apply.getApplyNo());
		}
		if (StringUtil.isNotNull(apply.getPickupNo())) {
			params.put("pickupNo_like", apply.getPickupNo());
		}
		if (StringUtil.isNotNull(apply.getRefundNo())) {
			params.put("refundNo_like", apply.getRefundNo());
		}
		if (StringUtil.isNotNull(apply.getExchangeOrderNo())) {
			params.put("exchangeOrderNo_like", apply.getExchangeOrderNo());
		}
		if (StringUtil.isNotNull(apply.getCommodityBarcode())) {
			params.put("commodityBarcode", apply.getCommodityBarcode());
		}
		if (apply.getOrder() != null && StringUtils.isNotBlank(apply.getOrder().getServicePoint()))
			params.put("servicePoint", apply.getOrder().getServicePoint());
		params.put("delFlag", OrderDetailStatusConstant.NO);
		return this.aftersaleApplyMapper.selectPageByParamsNew(pageObj, orderByField, orderBy, params);
	}

	/**
	 * 雷-----2016年7月28日 (非 Javadoc)
	 * <p>
	 * Title: cancelAudit
	 * </p>
	 * <p>
	 * Description:售后申请单，取消审核
	 * </p>
	 * 
	 * @param pickupNo
	 * @param orderNo
	 * @return
	 * @see com.ffzx.order.service.AftersalePickupService#cancelAudit(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject cancelAudit(String pickupNo, String orderNo, String applyID) throws ServiceException {
		JSONObject json = new JSONObject();
		json.put("flag", false);
		int result = 0;
		AftersalePickup pickup = this.aftersalePickupService.getPickupStateByPickNo(pickupNo);
		if (StringUtil.isNotNull(pickup)) {
			String pickupStatus = pickup.getPickupStatus();
			// 取货单已经通过 ，提示不能取消审核
			if (pickupStatus.equals(OrderDetailStatusConstant.APPROVE_SUC)) {
				json.put("msg", "取货单已经通过 ，不能取消审核！");
				return json;
			} else {
				Map<String, Object> params = new HashMap<String, Object>();
				SysUser currentUser = RedisWebUtils.getLoginUser();
				params.put("pickupNo", pickupNo);
				params.put("pickupStatus", OrderDetailStatusConstant.APPROVE_REJECT);// 取消审核
				params.put("lastUpdateBy.id", currentUser.getId());
				Date now = new Date();
				params.put("lastUpdateDate", now);
				params.put("orderNo", "C" + pickup.getOrderNo());
				params.put("applyNo", "C" + pickup.getApplyNo());
				params.put("applyId", "C" + pickup.getAftersaleApply().getId());
				// 修改取货申请单的状态为（取消无效）
				result = this.aftersalePickupService.cancelPick(params);
				if (result > 0) {
					AftersaleApply apply = new AftersaleApply();
					apply.setLastUpdateBy(currentUser);
					apply.setLastUpdateDate(now);
					apply.setApplyStatus(OrderDetailStatusConstant.APPROVE_NO);
					apply.setRemarks("售后申请单：取消审核！");
					apply.setId(applyID);
					apply.setPickupNo(null);
					// 修改申请售后的状态为：未审核后续可以重新审核
					result = this.aftersaleApplyMapper.updateByParams(apply);
					if (result > 0) {
						// 调用wms取消入库申请的接口
						ResultDto resultDto = null;
						try {
							InboundAftersaleReqCancelApiVo vo = new InboundAftersaleReqCancelApiVo();
							vo.setNo(pickupNo);
							resultDto = this.inboundAftersaleReqCancelApiService.cancel(vo);
						} catch (Exception e) {
							logger.error("调用wms的inboundAftersaleReqCancelApiService.cancel取消审核接口异常", e);
							throw new ServiceException(e);
						}
						if (ResultDto.OK_CODE.equals(resultDto.getCode())) {
							json.put("flag", true);
							json.put("msg", "操作成功！");
						} else {
							throw new ServiceException("wms返回的异常信息：" + resultDto.getMessage());
						}
					}
				}
				// 记录订单操作记录
				AftersaleApply apply = new AftersaleApply();
				apply.setOrderNo(orderNo);
				saveOmsRecord(apply, "售后申请单：取消审核！", currentUser);
			}
		}
		return json;
	}

	/******
	 * 审核售后申请单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServiceCode approve(AftersaleApply apply) throws ServiceException {
		int result = 0;
		SysUser user = RedisWebUtils.getLoginUser();
		String remarks = null;
		if (StringUtil.isNotNull(apply.getId())) {
			AftersaleApply applyv = this.aftersaleApplyMapper.findApplyStateById(apply.getId());
			if (null != applyv && OrderDetailStatusConstant.APPROVE_NO.equals(applyv.getApplyStatus())) {
				// 审核
				apply.setServiceApproveDate(new Date());// 客服审核时间
				if (apply.getApplyStatus().equals(OrderDetailStatusConstant.APPROVE_SUC)) {
					remarks = "售后申请单：审核通过！";
					result = this.aftersaleApplyMapper.updateByPrimaryKeySelective(apply);
					// 审核通过去生成取货单或者退款单
					apply = isSavePickupAndRefund(apply, result, user);
					// 产生订单操作记录
					saveOmsRecord(apply, remarks, user);
				} else if (apply.getApplyStatus().equals(OrderDetailStatusConstant.APPROVE_REJECT)) {
					remarks = "售后申请单：审核被驳回！";
					result = this.aftersaleApplyMapper.updateByPrimaryKeySelective(apply);
					if (result > 0 && apply.getApplyStatus().equals(OrderDetailStatusConstant.APPROVE_REJECT)) {
						apply = this.aftersaleApplyMapper.findApplyById(apply.getId());
						// 审核驳回
						if (StringUtil.isNotNull(apply.getOrder())) {
							// 退款申请中的订单修改状态为待收货和商品状态改为正常
							if (apply.getOrder().getStatus().equals(OrderStatusEnum.REFUND_APPLICATION.getValue())) {
								updateOrderStatus(apply, OrderStatusEnum.RECEIPT_OF_GOODS.getValue(), OrderDetailStatusConstant.STATUS_NOR);
							} else {
								// 交易完成的只修改订单详情商品状态为正常（不分是否换货和退款）
								updateOrderStatus(apply, null, OrderDetailStatusConstant.STATUS_NOR);
							}
						}
					}
					// 产生订单操作记录
					saveOmsRecord(apply, remarks, user);
				}
			}
		}
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	private void updateStatusByAftersale(AftersaleApply apply) {
		if (apply.getOrder().getStatus().equals(OrderStatusEnum.RECEIPT_OF_GOODS.getValue())) {
			updateOrderStatus(apply, OrderStatusEnum.REFUND_APPLICATION.getValue(), OrderDetailStatusConstant.STATUS_REFING);
		} else if (apply.getApplyStatus().equals(OrderDetailStatusConstant.CHANGE_GOODS)) {
			// 售后是换货单时只修改订单详情商品状态为换货处理中
			updateOrderStatus(apply, null, OrderDetailStatusConstant.STATUS_CHANGING);
		} else {
			// 交易完成的只修改订单详情商品状态为退款处理中
			updateOrderStatus(apply, null, OrderDetailStatusConstant.STATUS_REFING);
		}
	}

	/**
	 * 
	 * 雷------2016年7月29日
	 * 
	 * @Title: isSavePickupAndRefund
	 * @Description: 售后审核：详细步骤分析
	 * @param @param apply
	 * @param @param result
	 * @param @param user
	 * @param @return 设定文件
	 * @return AftersaleApply 返回类型
	 * @throws
	 */
	private AftersaleApply isSavePickupAndRefund(AftersaleApply apply, int result, SysUser user) {
		try {
			if (result > 0 && apply.getApplyStatus().equals(OrderDetailStatusConstant.APPROVE_SUC)) {
				apply = this.aftersaleApplyMapper.findApplyById(apply.getId());
				// 判断是否装箱
				if (StringUtil.isNotNull(apply.getOrder())) {
					// 待收货 退款申请中
					if (apply.getApplyType().equals(OrderDetailStatusConstant.REFUND_TOTAL)) {
						Map<String, Object> pa = new HashMap<String, Object>();
						pa.put("orderNo", apply.getOrderNo());
						List<OmsOrderdetail> orderdetailList = this.omsOrderdetailService.getOrderDetailList(pa);
						// 去wms获取该订单的状态（是否出库装箱）
						/**
						 * 订单状态： 0：未分配 1： 已分配 2： 部分分配 3：未拣货 4：拣货完成 5：装箱中 6：已装箱
						 * 7：已出库 -1：取消 -2：已取消
						 */
						ResultDto resDto = this.outboundSalesDeliveryApiService.getSalesDeliveryStatus(apply.getOrderNo());
						try {
							if (null == resDto || null == resDto.getData()) {
								throw new ServiceException("wms查询订单状态失败！");
							}
							Integer orderPackStatus = (Integer) resDto.getData();
							if (orderPackStatus >= 3) {
								// 未装箱，把订单的装箱复核修改为：已装箱
								apply.getOrder().setOrderPackStatus("1");
								// 已经装箱 生成取货单
								// 售后取货单号
								ResultDto pickCode = codeRuleApiService.getCodeRule("order", "order_aftersalepickup_code");
								String pickNo = pickCode.getData().toString();
								saveAftersalePickUp(apply, user, pickNo);
								// 生成入库申请单
								orderdetailList = this.omsOrderdetailService.getOrderDetailList(pa);
								saveInstorage(apply, pickNo, orderdetailList);
							} else {
								// 未装箱，把订单的装箱复核修改为：未装箱
								apply.getOrder().setOrderPackStatus("0");
								// 未装箱 生成退款单
								// 售后退款单号
								saveAftersaleRefund(apply, user);
								// 通知wms该订单取消
								this.outboundDeliveryStatusMqApiService.outboundDeliveryStatus(apply.getOrderNo(), Integer.valueOf(OrderStatusEnum.REFUND_APPLICATION.getValue()));
								// 把占用的库存减掉
								List<OrderParam> list = new ArrayList<OrderParam>();
								for (OmsOrderdetail detail : orderdetailList) {
									Warehouse warehouse = new Warehouse();
									warehouse.setId(detail.getWarehouseId());
									warehouse.setCode(detail.getWarehouseCode());
									warehouse.setName(detail.getWarehouseName());
									list.add(new OrderParam(detail.getSkuCode(), new Long(detail.getBuyNum()), warehouse));
								}
								/**
								 * 2016-08-23雷---对预占减库存的异常处理
								 */
								ResultDto resultDto = orderProcessManagerApiService.cancelOrder(list, apply.getOrder().getRegionId(), apply.getOrderNo());
								if (resultDto == null || !ResultDto.OK_CODE.equals(resultDto.getCode())) {
									logger.error("售后审核退款，预占减库存的异常处理：AftersaleApplyServiceImpl.isSavePickupAndRefund中返回结果异常");
									throw new ServiceException("售后审核退款，预占减库存的处理异常：AftersaleApplyServiceImpl.isSavePickupAndRefund中返回结果异常");
								}
							}
							/**
							 * 修改订单状态为退款申请中、订单是否装箱
							 */
							apply.getOrder().setStatus(OrderStatusEnum.REFUND_APPLICATION.getValue());
							this.omsOrderService.updateStatus(apply.getOrder());
						} catch (Exception e) {
							logger.error("", e);
							throw new ServiceException(e);
						}

					} else if (apply.getApplyType().equals(OrderDetailStatusConstant.REFUND_GOODS) || apply.getApplyType().equals(OrderDetailStatusConstant.CHANGE_GOODS)) {
						// 审核通过生成取货单 TODO
						ResultDto pickCode = codeRuleApiService.getCodeRule("order", "order_aftersalepickup_code");
						String pickNo = pickCode.getData().toString();
						/**
						 * 售后取货单号
						 */
						saveAftersalePickUp(apply, user, pickNo);
						List<OmsOrderdetail> orderdetailList = this.omsOrderdetailService.getOrderDetailListByApplyId(apply.getId());
						/**
						 * 生成入库申请单
						 */
						saveInstorage(apply, pickNo, orderdetailList);
					}
				}
			}
			return apply;
		} catch (Exception e) {
			logger.error("isSavePickupAndRefund error ===>>>", e);
			throw new ServiceException(e);
		}

	}

	/**
	 * 
	 * 雷------2016年8月5日
	 * 
	 * @Title: saveInstorage
	 * @Description: 通过MQ生成入库申请单
	 * @param @param apply
	 * @param @param pickNo
	 * @param @param orderDetailList
	 * @param @param num 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void saveInstorage(AftersaleApply apply, String pickNo, List<OmsOrderdetail> orderDetailList) {
		try {
			InboundAftersaleReqApiVo aftersaleReqApiVo = new InboundAftersaleReqApiVo();
			aftersaleReqApiVo.setServiceApproveDesc(apply.getServiceApproveDesc());
			// 重新定义为县仓
			if (StringUtils.isNotBlank(apply.getOrder().getCountyStoreCode())) {
				aftersaleReqApiVo.setWarehouseCode(apply.getOrder().getCountyStoreCode());
			} else {
				ResultDto result = addressApiService.getAddressParentAreaByIdNActFlag(apply.getOrder().getRegionId());
				Address address = null;
				if (result.getCode().equals(ResultDto.OK_CODE)) {
					address = (Address) result.getData();
					if (address != null && StringUtils.isNotBlank(address.getWarehouseCode())) {
						aftersaleReqApiVo.setWarehouseCode(address.getWarehouseCode());
					} else {
						aftersaleReqApiVo.setWarehouseCode(apply.getOrder().getWarehouseCode());
					}
				}
			}
			aftersaleReqApiVo.setNo(pickNo);
			aftersaleReqApiVo.setOrderId(apply.getOrder().getId());
			aftersaleReqApiVo.setOrderNo(apply.getOrderNo());
			aftersaleReqApiVo.setUserId(apply.getOrder().getMemberId());
			aftersaleReqApiVo.setUserName(apply.getOrder().getMemberName());
			aftersaleReqApiVo.setUserAddress(apply.getOrder().getAddressInfo());
			aftersaleReqApiVo.setUserPhone(apply.getOrder().getMemberPhone());
			aftersaleReqApiVo.setApplyType(apply.getApplyType());
			// 售后申请单商品详情
			List<InboundAftersaleReqItemsApiVo> itemList = new ArrayList<InboundAftersaleReqItemsApiVo>();
			if (StringUtil.isNotNull(orderDetailList)) {
				for (OmsOrderdetail orderdetail : orderDetailList) {
					InboundAftersaleReqItemsApiVo itemsVo = new InboundAftersaleReqItemsApiVo();
					itemsVo.setCommoditySkuId(orderdetail.getSkuId());
					itemsVo.setCommodityCode(orderdetail.getCommodityNo());
					itemsVo.setCommodityName(orderdetail.getCommodityTitle());
					itemsVo.setCommoditySkuBarcode(orderdetail.getCommodityBarcode());
					itemsVo.setCommoditySkuCode(orderdetail.getSkuCode());
					itemsVo.setSaleAttributeValues(orderdetail.getCommoditySpecifications());
					itemsVo.setUnit(orderdetail.getCommodityUnit());
					/**
					 * 判断退换货数量
					 */
					if (null != orderdetail.getReturn_num() && 0 < orderdetail.getReturn_num())
						itemsVo.setAftersaleCount(orderdetail.getReturn_num().toString());
					else
						itemsVo.setAftersaleCount(orderdetail.getBuyNum().toString());
					itemsVo.setCommodityAttributeValues(orderdetail.getCommodityAttributeValues());
					itemList.add(itemsVo);
				}
			}
			aftersaleReqApiVo.setItemsList(itemList);
			aftersaleApplyInstorageMqApiService.saveAftersaleApplyInstorageMq(aftersaleReqApiVo);
		} catch (Exception e) {
			logger.error("saveInstorage error ===>>>", e);
			throw new ServiceException(e);
		}
	}

	private void updateOrderStatus(AftersaleApply apply, String orderStatus, String orderDetailStauts) throws ServiceException {
		OmsOrder order = apply.getOrder();
		if (StringUtil.isNotNull(order)) {

			// 获取订单明细
			Map<String, Object> odParams = new HashMap<String, Object>();
			odParams.put("orderId", order.getId());
			// 订单明细
			List<OmsOrderdetail> odList = omsOrderdetailService.findByBiz(odParams);			
			// 恢复订单状态为待收货(退款未收到货)
			if (StringUtil.isNotNull(orderStatus)) {
				order.setStatus(OrderStatusEnum.RECEIPT_OF_GOODS.getValue());
				this.omsOrderService.updateStatus(order);
				// 更新订单明细商品状态为正常
				for (OmsOrderdetail detail : odList) {
					detail.setOrderDetailStatus(OrderDetailStatusConstant.STATUS_NOR);
					this.omsOrderdetailService.modifyById(detail);
				}
			}else{
				//交易完成的单 驳回 只修改 申请的商品售后状态
				List<AftersaleApplyItem> itemList=this.aftersaleApplyItemService.findByApplyId(apply.getId());
				if(itemList!=null && !itemList.isEmpty()){
					for(AftersaleApplyItem item:itemList){
						this.omsOrderdetailMapper.updateOrderDetailStatusByOrderNoAndSkuId(apply.getOrderNo(), item.getSkuId());
					}
				}
			}
		}
	}

	/**
	 * 生成订单记录
	 * 
	 * @param apply
	 * @param remarks
	 * @param user
	 */
	private void saveOmsRecord(AftersaleApply apply, String remarks, SysUser user) {
		OmsOrderRecord record = new OmsOrderRecord();
		record.setOrderNo(apply.getOrderNo());
		record.setOprId(user.getId());
		record.setOprName(user.getName());
		record.setDescription(remarks);
		record.setId(UUIDGenerator.getUUID());
		record.setRecordType(OrderDetailStatusConstant.NO);
		record.setCreateDate(new Date());
		try {
			this.omsOrderRecordService.add(record);
		} catch (ServiceException e) {
			logger.error("AftersaleApplyServiceImpl.findList() Exception=》订单系统-售后管理-获取售后申请单-时间转换", e);
			throw new ServiceException(e);
		}
	}

	/****
	 * 生成售后退款单
	 * 
	 * @param apply
	 * @param user
	 * @throws ServiceException
	 */
	private void saveAftersaleRefund(AftersaleApply apply, SysUser user) throws ServiceException {
		ResultDto refundCode = codeRuleApiService.getCodeRule("order", "order_aftersalerefund_code");
		String refundNo = refundCode.getData().toString();
		AftersaleRefund refund = new AftersaleRefund();
		refund.setApplyNo(apply.getApplyNo());
		refund.setOrderNo(apply.getOrderNo());
		refund.setCreateBy(user);
		refund.setCreateDate(new Date());
		refund.setLastUpdateBy(user);
		refund.setLastUpdateDate(new Date());
		refund.setRefundNo(refundNo);
		refund.setId(UUIDGenerator.getUUID());
		refund.setAftersaleApply(apply);
		refund.setRefundStatus(OrderDetailStatusConstant.REFUND_APPROVE_NO);
		int pickcode = this.aftersaleRefundService.add(refund);
		// 维护售后申请表取货单号
		if (pickcode > 0) {
			apply.setRefundNo(refundNo);
			this.aftersaleApplyMapper.updateByPrimaryKeySelective(apply);
		}
	}

	/*****
	 * 生成售后取货单
	 * 
	 * @param apply
	 * @param user
	 * @throws ServiceException
	 */
	@SuppressWarnings("static-access")
	private void saveAftersalePickUp(AftersaleApply apply, SysUser user, String pickNo) throws ServiceException {

		AftersalePickup pickup = new AftersalePickup();
		pickup.setPickupNo(pickNo);
		pickup.setAftersaleApply(apply);
		pickup.setApplyNo(apply.getApplyNo());
		pickup.setOrderNo(apply.getOrderNo());
		pickup.setCreateBy(user);
		pickup.setLastUpdateDate(new Date());
		pickup.setLastUpdateBy(user);
		pickup.setId(UUIDGenerator.getUUID());
		pickup.setPickupStatus(OrderDetailStatusConstant.APPROVE_NO);
		pickup.setCreateDate(new Date());
		ServiceCode pickcode = this.aftersalePickupService.saveAftersalePickup(pickup);
		// 维护售后申请表取货单号(非换货)
		if (pickcode.SUCCESS == 0) {
			apply.setPickupNo(pickNo);
			this.aftersaleApplyMapper.updateByPrimaryKeySelective(apply);
		}
	}

	/****
	 * 获取带订单对象的售后申请单
	 */
	@Override
	public AftersaleApply findAftersaleApplyById(String id) throws ServiceException {

		return this.aftersaleApplyMapper.findApplyById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updatePickupNoOrRefundNo(Map<String, Object> params) throws ServiceException {

		return this.aftersaleApplyMapper.updateNo(params);
	}

	public List<AftersaleApply> findPageByParams(Page page, String orderByField, String orderBy, Map<String, Object> params) throws ServiceException {
		return this.aftersaleApplyMapper.selectPageByParams(page, orderByField, orderBy, params);
	}

	/**
	 * 
	 * 雷-----2016年7月28日 (非 Javadoc)
	 * <p>
	 * Title: saveAftersaleApply
	 * </p>
	 * <p>
	 * Description:售后申请实现
	 * </p>
	 * 
	 * @param map
	 * @return
	 * @throws ServiceException
	 * @see com.ffzx.order.service.AftersaleApplyService#saveAftersaleApply(java.util.Map)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> saveAftersaleApply(Map<String, Object> map, String memberId, String desc, String reason) throws ServiceException {
		Map<String, Object> resultMap = null;

		try {
			String serviceType = null;
			String skuId = null;
			String orderId = null;
			if (map.get("serviceType") != null) {
				serviceType = map.get("serviceType").toString();
			}

			if (map.get("orderId") != null) {
				orderId = map.get("orderId").toString();
			}
			/**
			 * 2016-09-21，雷--主从可能不同步，多加一次查询判断是否申请售后
			 */
			OmsOrder order_main = omsOrderService.findById(orderId);
			if (OrderStatusEnum.REFUND_APPLICATION.getValue().equals(order_main.getStatus())) {
				resultMap = new HashMap<String, Object>();
				resultMap.put("STATUS", ServiceResultCode.FAIL);
				resultMap.put("MSG", "该订单已经申请过售后，请不要重复操作！");
				return resultMap;
			}
			ResultDto aftersaleCode = codeRuleApiService.getCodeRule("order", "order_aftersaleapply_code");
			AftersaleApply aftersaleapply = AftersaleAuxiliary.setAftersaleApplyValue(memberId, serviceType, desc, reason, new AftersaleApply(), order_main, aftersaleCode);
			if (AfterSaleStatusType.REFUND_TOTAL.getValue().equals(aftersaleapply.getApplyType())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("orderId", orderId);
				List<OmsOrderdetail> orderDetailList = omsOrderdetailService.findByBiz(params);
				if (StringUtil.isNotNull(orderDetailList)) {
					for (OmsOrderdetail detail : orderDetailList) {
						// 保存申请售后单详情
						AftersaleApplyItem item = AftersaleAuxiliary.setAftersaleItmeValue(detail.getBuyNum(), aftersaleapply, detail, new AftersaleApplyItem());
						
						aftersaleApplyItemService.saveAftersaleApplyItem(item);
						// 修改订单详情商品状态为:退款处理中
						this.omsOrderdetailService.updateOrderDetaiStatus(detail.getId(), OrderDetailStatusConstant.STATUS_REFING);
					}
				}
				// 修改订单状态为:退款申请中
				updateOrderStatus(orderId, OrderStatusConstant.STATUS_REFUND_APPLICATION);
			} else {
				if (map.get("skuId") != null) {
					skuId = map.get("skuId").toString();
				}
				String count = null;
				if (map.get("count") != null) {
					count = map.get("count").toString();
				}
				Map<String, Object> odParams = new HashMap<String, Object>();
				odParams.put("orderNo", order_main.getOrderNo());
				odParams.put("skuId", skuId);
				OmsOrderdetail detail = this.omsOrderdetailService.getOrderdetailByCode(odParams);
				if (!OrderDetailStatusConstant.STATUS_NOR.equals(detail.getOrderDetailStatus())) {
					resultMap = new HashMap<String, Object>();
					resultMap.put("STATUS", ServiceResultCode.FAIL);
					resultMap.put("MSG", "该商品已经申请过售后，请不要重复申请");
					return resultMap;
				}
				
				
				try {
				// 申请售后时退、换货，添加售后明细
				aftersaleAddGift(count, aftersaleapply, detail);
				} catch (ServiceException e) {
					resultMap.put("STATUS", ServiceResultCode.FAIL);
					resultMap.put("MSG", e.getMessage());
					return resultMap;
				}

			}
			this.aftersaleApplyMapper.insertSelective(aftersaleapply);
			resultMap = new HashMap<String, Object>();
			resultMap.put("STATUS", ServiceResultCode.SUCCESS);
			resultMap.put("MSG", "操作成功");
		} catch (Exception e) {
			resultMap = new HashMap<String, Object>();
			resultMap.put("STATUS", ServiceResultCode.FAIL);
			resultMap.put("MSG", "申请售后失败");
			throw new ServiceException(e);

		}
		return resultMap;
	}
	private void checkAfterAplyItem(AftersaleApply aftersaleapply,List<AftersaleApplyItem> applyItems){
		Map<String,Object> params = new HashMap<>();
		params.put("orderNo", aftersaleapply.getOrderNo());
		for (AftersaleApplyItem item : applyItems) {
			params.put("skuId", item.getSkuId());
			int hadReturnCount = aftersaleApplyItemService.getHadRreturnCount(params);
			if(item.getCommodityBuyNum()<(item.getReturnNum()+hadReturnCount)){
				throw  new ServiceException("商品【"+item.getCommodityName()+"】售后数量最多还能处理【"+(item.getCommodityBuyNum()-hadReturnCount)+"】个");
			}
		}
	}
	/**
	 * 
	 * 雷------2016年9月14日
	 * 
	 * @Title: aftersaleAddGift
	 * @Description: 申请售后时退、换货，添加售后明细，修改订单状态
	 * @param @param serviceType
	 * @param @param skuId
	 * @param @param count
	 * @param @param afterSaleApply
	 * @param @param aftersaleCode
	 * @param @param detail
	 * @param @param item
	 * @param @throws ServiceException 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@Transactional(rollbackFor = Exception.class)
	public void aftersaleAddGift(String count, AftersaleApply afterSaleApply, OmsOrderdetail detail) throws ServiceException {
		/**
		 * 申请的售后为：换货、退货将计算赠品数量
		 */
		if (!AfterSaleStatusType.REFUND_TOTAL.getValue().equals(afterSaleApply.getApplyType())) {
			List<AftersaleApplyItem> applyItems = AftersaleAuxiliary.getApplyItems(new ArrayList<AftersaleApplyItem>(), Integer.valueOf(count), afterSaleApply, detail, new AftersaleApplyItem());
			List<OmsOrderdetail> orderdetails = omsOrderdetailService.getdetailByIDList(detail.getId());
			/**
			 * 有赠品时，对赠品的处理
			 */
			if (null != orderdetails && orderdetails.size() > 0) {
				for (OmsOrderdetail omsOrderdetail : orderdetails) {
					/**
					 * 计算退换赠品数量
					 */
					int returnGift = AftersaleAuxiliary.getReturnGiftNum(omsOrderdetail.getSingleGiftNum(), omsOrderdetail.getTriggerNum(), omsOrderdetail.getBuyNum(), Integer.valueOf(count));
					applyItems = AftersaleAuxiliary.getApplyItems(applyItems, returnGift, afterSaleApply, omsOrderdetail, new AftersaleApplyItem());
				
				}

			}
			//检查退款
			checkAfterAplyItem(afterSaleApply,applyItems);
			/**
			 * 保存售后明细
			 */
			for (AftersaleApplyItem applyItem : applyItems) {
				aftersaleApplyItemService.saveAftersaleApplyItem(applyItem);
			}
			/**
			 * 修改订单状态
			 */
			if (null == orderdetails)
				orderdetails = new ArrayList<OmsOrderdetail>();
			orderdetails.add(detail);
			for (OmsOrderdetail omsOrderdetail : orderdetails) {
				if (AfterSaleStatusType.REFUND_GOODS.getValue().equals(afterSaleApply.getApplyType())) {
					this.omsOrderdetailService.updateOrderDetaiStatus(omsOrderdetail.getId(), OrderDetailStatusConstant.STATUS_REFING);
				} else if (AfterSaleStatusType.CHANGE_GOODS.getValue().equals(afterSaleApply.getApplyType())) {
					// 换货(已收到货)，则修改订单详情商品状态为：换货处理中
					this.omsOrderdetailService.updateOrderDetaiStatus(omsOrderdetail.getId(), OrderDetailStatusConstant.STATUS_CHANGING);
				}
			}

		}

	}

	private void updateOrderStatus(String orderId, String status) throws ServiceException {
		try {
			OmsOrder omsOrder = new OmsOrder();
			omsOrder.setId(orderId);
			omsOrder.setStatus(status);
			omsOrderService.updateStatus(omsOrder);
		} catch (Exception e) {
			logger.error("AftersaleApplyController.updateOrderStatus-Exception=》", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 雷-----2016年9月18日 (非 Javadoc)
	 * <p>
	 * Title: calculationGifts
	 * </p>
	 * <p>
	 * Description: 计算赠品的申请售后时信息
	 * </p>
	 * 
	 * @param mainCommodityId
	 * @param returnMainNum
	 * @return
	 * @see com.ffzx.order.service.AftersaleApplyService#calculationGifts(java.lang.String,
	 *      java.lang.Integer)
	 */
	@Override
	public JSONObject calculationGifts(String mainCommodityId, Integer returnMainNum) {
		JSONObject data = new JSONObject();
		/**
		 * 查出主商品对应的赠品
		 */
		List<OmsOrderdetail> orderdetails = this.omsOrderdetailService.getdetailByIDList(mainCommodityId);
		if (null != orderdetails && orderdetails.size() > 0) {
			for (int i = 0; i < orderdetails.size(); i++) {
				OmsOrderdetail orderdetail = orderdetails.get(i);
				orderdetails.get(i).setBuyNum(AftersaleAuxiliary.getReturnGiftNum(orderdetail.getSingleGiftNum(), orderdetail.getTriggerNum(), orderdetail.getBuyNum(), returnMainNum));
			}
		}
		data.put("data", orderdetails);
		return data;
	}

	/**
	 * 雷-----2016年11月3日 (非 Javadoc)
	 * <p>
	 * Title: getSourceOrder
	 * </p>
	 * <p>
	 * Description:根据售后申请单号，查询源订单售后信息
	 * </p>
	 * 
	 * @param applyNo
	 * @return
	 * @see com.ffzx.order.service.AftersaleApplyService#getSourceOrder(java.lang.String)
	 */
	@Override
	public OmsOrder getSourceOrder(String applyNo) {
		OmsOrder order = new OmsOrder();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("applyNo", applyNo);
		params.put("applyStatus", OrderDetailStatusConstant.APPROVE_SUC);
		AftersaleApply apply = this.aftersaleApplyMapper.findByApplyNo(params);
		if (null != apply) {
			/**
			 * 操作的单据为换货单
			 */
			if (StringUtils.contains(apply.getOrderNo(), "HH")) {
				params.clear();
				params.put("skuids", this.getSkuids(apply.getAftersaleApplyItems()));
				params.put("applyStatus", OrderDetailStatusConstant.APPROVE_SUC);
				params.put("exchangeOrderNo", apply.getOrderNo());
				/**
				 * 递归出源订单信息：XXX
				 */
				apply = this.aftersaleApplyMapper.findByApplyNo(params);
				return getSourceOrder(apply.getApplyNo());

			} else {
				return order = this.setApplytoOrder(this.omsOrderService.findOrderInfo(apply.getOrderNo()), apply);
			}
		}

		return order;
	}

	/**
	 * 
	 * 雷------2016年11月3日
	 * 
	 * @Title: setApplytoOrder
	 * @Description: 比较出申请售后的子订单
	 * @param @param omsOrder
	 * @param @param apply
	 * @param @return 设定文件
	 * @return OmsOrder 返回类型
	 * @throws
	 */
	public OmsOrder setApplytoOrder(OmsOrder omsOrder, AftersaleApply apply) {
		List<OmsOrderdetail> detail = new ArrayList<OmsOrderdetail>();
		List<AftersaleApplyItem> items = apply.getAftersaleApplyItems();
		List<OmsOrderdetail> details = omsOrder.getDetailList();
		if (null != items && null != omsOrder)
			for (AftersaleApplyItem aftersaleApplyItem : items) {
				for (OmsOrderdetail omsOrderdetail : details) {
					if (aftersaleApplyItem.getSkuId().equals(omsOrderdetail.getSkuId())) {
						/**
						 * 退货的数量设置
						 */
						omsOrderdetail.setBuyNum(aftersaleApplyItem.getReturnNum());
						detail.add(omsOrderdetail);
					}
				}
			}
		omsOrder.setDetailList(detail);
		return omsOrder;

	}

	/**
	 * 
	 * 雷------2016年11月4日
	 * 
	 * @Title: getSkuids
	 * @Description: skuid的组装
	 * @param @return 设定文件
	 * @return List<String> 返回类型
	 * @throws
	 */
	public List<String> getSkuids(List<AftersaleApplyItem> items) {
		List<String> skuids = new ArrayList<String>();
		if (null != items)
			for (AftersaleApplyItem item : items) {
				skuids.add(item.getSkuId());
			}
		return skuids;
	}

}
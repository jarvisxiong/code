package com.ffzx.order.controller;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.basedata.api.dto.SubSystemConfig;
import com.ffzx.basedata.api.service.SubSystemConfigApiService;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.order.api.dto.AftersaleApply;
import com.ffzx.order.api.dto.AftersaleApplyItem;
import com.ffzx.order.api.dto.AftersaleRefund;
import com.ffzx.order.api.dto.OmsOrderRecord;
import com.ffzx.order.common.AftersaleAuxiliary;
import com.ffzx.order.constant.OrderConstant;
import com.ffzx.order.service.AftersaleApplyItemService;
import com.ffzx.order.service.AftersaleApplyService;
import com.ffzx.order.service.AftersaleRefundService;
import com.ffzx.order.service.OmsOrderRecordService;

/**
 *售后管理
 * 
 * @className AftersaleApplyController.java
 * @author lushi.guo
 * @date 
 * @version 1.0
 */
@Controller
@RequestMapping("/aftersalerefund/*")
public class AftersaleRefundController extends BaseController {
	// 日志
	protected final   Logger logger = LoggerFactory.getLogger(AftersaleRefundController.class);
	
	@Autowired
	private  AftersaleRefundService aftersaleRefundService;
	@Autowired
	private  AftersaleApplyService aftersaleApplyService;
	@Autowired
	private AftersaleApplyItemService aftersaleApplyItemService;
	@Autowired
	private OmsOrderRecordService omsOrderRecordService;
	@Autowired
	private SubSystemConfigApiService subSystemConfigApiService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	/**
	* 退款单列表
	* @Title: afterList 
	* @param map
	* @param refund
	* @param tblshow
	* @return String    返回类型
	 */
	@RequestMapping(value="refundList")
	public String refundList(ModelMap map, AftersaleRefund refund,String tblshow){
		// 分页
		Page pageObj = this.getPageObj();
		// 集合
		List<AftersaleRefund> aftersaleRefundList = null;
		
		try {
			aftersaleRefundList =this.aftersaleRefundService.findList_Like(pageObj, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY, refund);
		} catch (ServiceException e) {
			logger.error("AftersaleRefundController.refundList() ServiceException=售后管理-退款单集合", e);
			throw e;
		} catch (Exception e) {
			logger.error("AftersaleRefundController.refundList() Exception=售后管理-退款单集合", e);
			throw new ServiceException(e);
		}
		// 页签
		if (tblshow == "" || tblshow == null) {
			map.put("tblshow", "0");
		} else {
			map.put("tblshow", tblshow);
		}
		map.put("aftersaleApplyList", aftersaleRefundList);
		map.put("aftresal", refund);
		map.put("refund", refund);
		map.put("pageObj", pageObj);
		return "aftersale/apply/apply_list";
	}
	
	
	/**
	* 获取退款单详情
	* @Title: findRefundInfo 
	* @param map
	* @return String    返回类型
	 */
	@RequestMapping(value = "findRefundInfo")
	public String findRefundInfo(ModelMap map,String pageType){
		// 获取退款单id
		String id = this.getString("id");
		// 获取退款单单号
		String refundNo = this.getString("refundNo");
		AftersaleRefund refund = null;
		AftersaleApply apply=null;
		List<String> imageList=null;
		List<AftersaleApplyItem> aftersaleApplyItemList=null;
		List<OmsOrderRecord> omsOrderRecordList=null;
		SubSystemConfig ucSystem = null;
		SubSystemConfig orderSystem = null;
		List<SubSystemConfig> systemConfigList = null;
			// 查询
			try {
				// 退款单信息
				refund = aftersaleRefundService.findRefundInfo(id, refundNo);
				//售后申请单信息
				apply=this.aftersaleApplyService.findAftersaleApplyById(refund.getAftersaleApply().getId());
				//售后申请单商品详情
				AftersaleApplyItem item=new AftersaleApplyItem();
				item.setApplyId(apply.getId());
				aftersaleApplyItemList=this.aftersaleApplyItemService.findAftersaleApplyItemList(null, null, null, item);
				//售后订单操作状态
				Map<String, Object> params=new HashMap<>();
				params.put("orderNo", refund.getOrderNo());
				omsOrderRecordList=this.omsOrderRecordService.findByPage(null, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY, params);
				//售后申请图片
				if(StringUtil.isNotNull(apply)){
					String images=apply.getApplyPicImg();
					imageList=new ArrayList<>();
					if(StringUtil.isNotNull(images)){
						for(String img:images.split(",")){
							imageList.add(img);
						}
					}
					
				}
				//获取子系统信息(域名和端口)
				ResultDto allResult = subSystemConfigApiService.getSubSystemConfigALl();
				if(allResult.getCode().equals(ResultDto.OK_CODE)){
					systemConfigList = (List<SubSystemConfig>)allResult.getData();
					for (SubSystemConfig subSystemConfig : systemConfigList) {
						if(subSystemConfig!= null && OrderConstant.UC_CODE.equals(subSystemConfig.getSubSystemCode())){
							ucSystem = subSystemConfig;
						}else if(subSystemConfig!= null && OrderConstant.OMS_CODE.equals(subSystemConfig.getSubSystemCode())){
							orderSystem = subSystemConfig;
						}
						if(ucSystem != null && orderSystem != null){
							break;
						}
					}
				}else{
					throw new ServiceException("SubSystemConfigApiConsumer-getSubSystem=>dubbo调用失败：" + allResult);
				}
			} catch (ServiceException e) {
				logger.error("AftersaleRefundController.findRefundInfo() ServiceException=售后管理-退款单信息", e);
				throw e;
			} catch (Exception e) {
				logger.error("AftersaleRefundController.findRefundInfo() Exception=售后管理-退款单信息", e);
				throw new ServiceException(e);
			}
//		String applyId = this.getString("applyId");
		map.put("pageType", pageType);
		map.put("applyID", id);
		map.put("refundNo", refundNo);
		map.put("applyId", apply.getId());
		map.put("refund", refund);
		map.put("apply", apply);
		map.put("imageList", imageList);
		map.put("aftersaleApplyItemList", aftersaleApplyItemList);
		map.put("omsOrderRecordList", omsOrderRecordList);
		// 金额显示格式
		DecimalFormat df=new DecimalFormat("0.00");
		Map<String, Object> priceMap=AftersaleAuxiliary.calculatePrice(aftersaleApplyItemList);
		BigDecimal actPayAmountSum = (priceMap.get("actPayAmountSum")==null?new BigDecimal(0):new BigDecimal(priceMap.get("actPayAmountSum").toString()));
		map.put("actPayAmountSum", actPayAmountSum);
		// 退款计算总金额（包括运费、优惠）
		BigDecimal sendCost = apply.getOrder().getSendCost()==null?new BigDecimal(0):apply.getOrder().getSendCost(); // 运费
		BigDecimal favorablePrice = (priceMap.get("favouredAmount")==null?new BigDecimal(0):new BigDecimal(priceMap.get("favouredAmount").toString())); // 优惠
		BigDecimal redPacketAmount= (priceMap.get("redPacketAmount")==null?new BigDecimal(0):new BigDecimal(priceMap.get("redPacketAmount").toString())); // 红包金额
		BigDecimal totalAmount = actPayAmountSum.add(sendCost).subtract(favorablePrice).subtract(redPacketAmount);
		map.put("sendCost", df.format(sendCost));
		map.put("favorablePrice", df.format(favorablePrice));
		map.put("redPacketAmount", df.format(redPacketAmount));
		map.put("totalAmount", df.format(totalAmount));
		map.put("image_path",System.getProperty("image.web.server"));
		map.put("ucDomain", ucSystem.getSubSystemDomain());
		map.put("ucPort", ucSystem.getSubSystemPort());
		map.put("orderDomain", orderSystem.getSubSystemDomain());
		map.put("orderPort", orderSystem.getSubSystemPort());
		
		return "aftersale/refund/refund_detail";
	}
	
	
	/**
	 * 审核退款单
	 * 
	 * @Title: approveRefund
	 * @return ResultVo 返回类型
	 */
	@RequestMapping(value = "approveRefund")
	@ResponseBody
	public ResultVo approveRefund() {
		// 返回结果对象
		ResultVo resultVo = new ResultVo();
		// 获取数据
		String orderId = this.getString("orderId"); // 订单id
		String orderNo = this.getString("orderNo"); // 订单编号
		String refundId = this.getString("refundId"); // 退款单id
		String remarks = this.getString("remarks") == null ? "" : "(" + this.getString("remarks") + ")"; // 记录描述
		ServiceCode serviceCode = null;
		String lockKey = refundId + "_" + orderId + "_aftersaleRefund";
		try {
			/**
			 * 2016-09-26，雷--调优
			 */
			// 审核退款单
			if (redisTemplate.opsForValue().setIfAbsent(lockKey, "1")) {
				serviceCode = aftersaleRefundService.approveAftersaleRefund(refundId, orderId, orderNo, remarks);
			} else {
				serviceCode = ServiceResultCode.FAIL;
			}
			resultVo.setResult(serviceCode, "aftersalerefund/refundList.do?tblshow=2");
		} catch (ServiceException e) {
			logger.error("AftersaleRefundController.approveRefund() ServiceException=售后管理-退款单审核", e);
			throw e;
		} catch (Exception e) {
			logger.error("AftersaleRefundController.approveRefund() Exception=售后管理-退款单审核", e);
			throw new ServiceException(e);
		} finally {
			// 释放锁
			redisTemplate.delete(lockKey);
		}

		return resultVo;
	}
	
	
	@RequestMapping(value = "findPaymentInfo")
	public String findPaymentInfo(ModelMap map){
		// 获取售后申请单id
		String applyId = this.getString("applyId");
		AftersaleApply apply=null;
		AftersaleRefund refund = null;
		List<AftersaleApplyItem> aftersaleApplyItemList=null;
		if (StringUtil.isNotNull(applyId)) {
			// 查询
			try {
				//售后申请单信息
				apply=this.aftersaleApplyService.findAftersaleApplyById(applyId);
				// 退款单信息
				refund = aftersaleRefundService.findRefundInfo(null, apply.getRefundNo());
				//售后申请单商品详情
				AftersaleApplyItem item=new AftersaleApplyItem();
				item.setApplyId(applyId);
				aftersaleApplyItemList=this.aftersaleApplyItemService.findAftersaleApplyItemList(null, null, null, item);
			} catch (ServiceException e) {
				logger.error("AftersaleRefundController.findPaymentInfo() ServiceException=售后管理-退款单-确认付款信息", e);
				throw e;
			} catch (Exception e) {
				logger.error("AftersaleRefundController.findPaymentInfo() Exception=售后管理-退款单-确认付款信息", e);
				throw new ServiceException(e);
			}
		}
		map.put("apply", apply);
		map.put("refund", refund);
		map.put("aftersaleApplyItemList", aftersaleApplyItemList);
		// 金额显示格式
		DecimalFormat df=new DecimalFormat("0.00");
		Map<String, Object> priceMap=AftersaleAuxiliary.calculatePrice(aftersaleApplyItemList);
		BigDecimal actPayAmountSum = (priceMap.get("actPayAmountSum")==null?new BigDecimal(0):new BigDecimal(priceMap.get("actPayAmountSum").toString()));
		map.put("actPayAmountSum", actPayAmountSum);
		// 退款计算总金额（包括运费、优惠）
		BigDecimal sendCost = apply.getOrder().getSendCost()==null?new BigDecimal(0):apply.getOrder().getSendCost(); // 运费
		BigDecimal favorablePrice = (priceMap.get("favouredAmount")==null?new BigDecimal(0):new BigDecimal(priceMap.get("favouredAmount").toString())); // 优惠
		BigDecimal redPacketAmount= (priceMap.get("redPacketAmount")==null?new BigDecimal(0):new BigDecimal(priceMap.get("redPacketAmount").toString())); // 红包金额
		BigDecimal totalAmount = actPayAmountSum.add(sendCost).subtract(favorablePrice).subtract(redPacketAmount);
		map.put("sendCost", df.format(sendCost));
		map.put("favorablePrice", df.format(favorablePrice));
		map.put("redPacketAmount", df.format(redPacketAmount));
		map.put("totalAmount", df.format(totalAmount));
		map.put("image_path",System.getProperty("image.web.server"));
		return "aftersale/refund/confirm_payment";
	}
	
	/**
	 * 确认付款
	 * 
	 * @Title: confirmPayment
	 * @return ResultVo 返回类型
	 */
	@RequestMapping(value = "confirmPayment")
	@ResponseBody
	public ResultVo confirmPayment() {
		// 返回结果对象
		ResultVo resultVo = new ResultVo();
		// 获取数据
		String orderId = this.getString("orderId"); // 订单id
		String orderNo = this.getString("orderNo"); // 订单编号
		String refundId = this.getString("refundId"); // 退款单id
		String lockKey = refundId + "_" + orderId + "_RefundconfirmPayment";
		ServiceCode serviceCode = null;
		try {
			/**
			 * 2016-09-21~27-雷修复 
			 */
			// 更新退款单、修改售后申请单详情状态、修改订单及订单详情状态、新增退款记录
			BigDecimal payPrice = new BigDecimal(this.getString("payPrice"));
			if (redisTemplate.opsForValue().setIfAbsent(lockKey, "1")) {
				serviceCode = aftersaleRefundService.confirmPayment(orderId, orderNo, payPrice, refundId);
			} else {
				serviceCode = ServiceResultCode.FAIL;
			}
			resultVo.setResult(serviceCode, "/aftersalerefund/findRefundInfo.do?id=" + refundId);
		} catch (ServiceException e) {
			logger.error("AftersaleRefundController.confirmPayment() ServiceException=售后管理-退款单(确认付款)", e);
			resultVo.setResult(ServiceResultCode.FAIL, "/aftersalerefund/findRefundInfo.do?id=" + refundId);
		} catch (Exception e) {
			logger.error("AftersaleRefundController.confirmPayment() Exception=售后管理-退款单(确认付款)", e);
			resultVo.setResult(ServiceResultCode.FAIL, "/aftersalerefund/findRefundInfo.do?id=" + refundId);
		} finally {
			// 释放锁
			redisTemplate.delete(lockKey);
		}

		return resultVo;
	}
	
	
	@RequestMapping(value="toPrinter")
	public String toPrinter(String id,ModelMap map){
		AftersaleApply apply=null;
		List<AftersaleApplyItem> aftersaleApplyItemList=null;
		if(StringUtil.isNotNull(id)){
			try {
				//售后申请单信息
				apply=this.aftersaleApplyService.findAftersaleApplyById(id);
				//售后申请单商品详情
				AftersaleApplyItem item=new AftersaleApplyItem();
				item.setApplyId(apply.getId());
				aftersaleApplyItemList=this.aftersaleApplyItemService.findAftersaleApplyItemList(null, null, null, item);
			} catch (ServiceException e) {
				logger.error("AftersaleRefundController.toPrinter() ServiceException=售后管理-售后退款单详情-打印", e);
				throw e;
			}
		}
		map.put("apply", apply);
		map.put("aftersaleApplyItemList", aftersaleApplyItemList);
		// 金额显示格式
		DecimalFormat df=new DecimalFormat("0.00");
		Map<String, Object> priceMap=AftersaleAuxiliary.calculatePrice(aftersaleApplyItemList);
		BigDecimal actPayAmountSum = (priceMap.get("actPayAmountSum")==null?new BigDecimal(0):new BigDecimal(priceMap.get("actPayAmountSum").toString()));
		map.put("actPayAmountSum", actPayAmountSum);
		return "aftersale/refund/refund_printer";
	}
}


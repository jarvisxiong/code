package com.ffzx.order.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ffzx.basedata.api.dto.SubSystemConfig;
import com.ffzx.basedata.api.service.CodeRuleApiService;
import com.ffzx.basedata.api.service.SubSystemConfigApiService;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.ExcelExportUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil.ExportModel;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.order.api.dto.AftersaleApply;
import com.ffzx.order.api.dto.AftersaleApplyItem;
import com.ffzx.order.api.dto.OmsOrderRecord;
import com.ffzx.order.common.AftersaleAuxiliary;
import com.ffzx.order.constant.OrderConstant;
import com.ffzx.order.constant.OrderDetailStatusConstant;
import com.ffzx.order.service.AftersaleApplyItemService;
import com.ffzx.order.service.AftersaleApplyService;
import com.ffzx.order.service.AftersalePickupService;
import com.ffzx.order.service.OmsOrderRecordService;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.order.service.OmsOrderdetailService;

/**
 * 售后管理
 * 
 * @className AftersaleApplyController.java
 * @author lushi.guo
 * @date
 * @version 1.0
 */
@Controller
@RequestMapping("/aftersaleapply")
public class AftersaleApplyController extends BaseController {
	// 日志
	protected final Logger logger = LoggerFactory.getLogger(AftersaleApplyController.class);

	@Autowired
	private AftersaleApplyService aftersaleApplyService;
	@Autowired
	private AftersaleApplyItemService aftersaleApplyItemService;
	@Autowired
	private OmsOrderRecordService omsOrderRecordService;
	@Autowired
	private OmsOrderService omsOrderService;
	@Autowired
	private CodeRuleApiService codeRuleApiService;
	@Autowired
	private OmsOrderdetailService omsOrderdetailService;
	@Autowired
	private SubSystemConfigApiService subSystemConfigApiService;
	@Autowired
	private AftersalePickupService aftersalePickupService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 
	 * 雷------2016年9月18日
	 * 
	 * @Title: calculationGifts
	 * @Description: 计算赠品的申请售后时信息
	 * @param @param mainCommodityId
	 * @param @return 设定文件
	 * @return JSONObject 返回类型
	 * @throws
	 */
	@RequestMapping(value = "calculationGifts")
	@ResponseBody
	public JSONObject calculationGifts(String mainCommodityId, Integer returnMainNum) {
		JSONObject data = new JSONObject();
		try {
			data = this.aftersaleApplyService.calculationGifts(mainCommodityId, returnMainNum);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AftersaleApplyController.calculationGifts", e);
		}
		return data;
	}

	/**
	 * 
	 * 雷------2016年8月17日
	 * 
	 * @Title: cancelAudit
	 * @Description: 售后申请单，取消审核
	 * @param @param pickupNo
	 * @param @param orderNo
	 * @param @param applyID
	 * @param @return 设定文件
	 * @return JSONObject 返回类型
	 * @throws
	 */
	@RequestMapping(value = "cancelAudit")
	@ResponseBody
	public JSONObject cancelAudit(String pickupNo, String orderNo, String applyID) {
		JSONObject msgObject = new JSONObject();
		try {
			msgObject = this.aftersaleApplyService.cancelAudit(pickupNo, orderNo, applyID);
		} catch (ServiceException e) {
			logger.error("订单号：" + orderNo + "申请售后单ID:" + applyID + "取货单号：" + pickupNo + "", e);
			msgObject.put("flag", false);
			msgObject.put("msg", "操作失败！");
		}
		return msgObject;
	}

	/**
	 * 
	 * 雷------2016年8月10日
	 * 
	 * @Title: importApplyList
	 * @Description: 售后管理：导出数据
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "importApplyList")
	public void importApplyList(HttpServletRequest request, HttpServletResponse response) {
		final String[] headers = new String[] { "支付时间", "申请时间", "售后申请单号", "售后服务类型", "关联订单", "关联售后取货单", "关联退款单", "关联换货订单", "售后商品名称", "销售属性", "条形码", "计量单位", "单价", "实付金额", "购买数量", "退货数量", "退款金额", "配送人", "配送服务点", "审核描述", "物流状态" };

		final String[] properties = new String[] { "payTime", "createDate", "applyNo", "applyType", "orderNo", "pickupNo", "refundNo", "exchangeOrderNo", "commodityTitle", "commodityAttributeValues", "commodityBarcode", "commodityUnit", "commodityPrice", "actPayAmount", "buyNum", "returnNum", "refundAmount", "sendPerson", "servicePoint", "description", "logisticsState" };

		final List<Map<String, String>> listMap = new ArrayList<>();
		Map<String, Object> params = this.getParaMap();
		final List<Map> list = this.aftersaleApplyService.importApplyList(params);
		try {
			final String fileName = "售后申请导出";
			ExcelExportUtil.exportXls(new ExportModel() {
				public String[] getProperties() {
					return properties;
				}

				public String[] getHeaders() {
					return headers;
				}

				public List getData() {
					return list;
				}

				public String getFileName() {
					return fileName;
				}
			}, response);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("AftersaleApplyController.importApplyList()售后管理：导出数据 ", e);
			throw new ServiceException(e);
		}

	}

	/**
	 * 
	 * 雷------2016年8月10日
	 * 
	 * @Title: importApplyList
	 * @Description: 售后管理：财务批量导出数据
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "importRefundList")
	public void importRefundList(HttpServletRequest request, HttpServletResponse response) {
		final String[] headers = new String[] { "支付时间", "申请时间", "售后申请单号", "售后服务类型", "关联订单", "下单人电话", "关联售后取货单", "关联退款单", "退款时间", "关联换货订单", "供应商", "供应商编码", "售后商品名称", "销售属性", "条形码", "计量单位", "单价", "实付金额", "购买数量", "退货数量", "退款金额", "优惠金额", "支付方式", "配送人", "配送服务点", "审核描述", "物流状态" };

		final String[] properties = new String[] { "payTime", "createDate", "applyNo", "applyType", "orderNo", "memberPhone", "pickupNo", "refundNo", "payDate", "exchangeOrderNo", "vendorName", "vendorCode", "commodityTitle", "commodityAttributeValues", "commodityBarcode", "commodityUnit", "commodityPrice", "actPayAmount", "buyNum", "returnNum", "refundAmount", "favouredAmount", "payType", "sendPerson", "servicePoint", "description", "logisticsState" };

		final List<Map<String, String>> listMap = new ArrayList<>();
		Map<String, Object> params = this.getParaMap();
		final List<Map> list = this.aftersaleApplyService.importRefundList(params);
		try {
			final String fileName = "财务批量导出";
			ExcelExportUtil.exportXls(new ExportModel() {
				public String[] getProperties() {
					return properties;
				}

				public String[] getHeaders() {
					return headers;
				}

				public List getData() {
					return list;
				}

				public String getFileName() {
					return fileName;
				}
			}, response);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("AftersaleApplyController.importRefundList()售后管理：财务批量导出数据 ", e);
			throw new ServiceException(e);
		}

	}

	/**
	 * 
	 * 雷------2016年8月10日
	 * 
	 * @Title: beforeImportApplyList
	 * @Description: 售后管理：导出数据之前的检查
	 * @param @return 设定文件
	 * @return JSONObject 返回类型
	 * @throws
	 */
	@RequestMapping(value = "beforeImportApplyList")
	@ResponseBody
	public JSONObject beforeImportApplyList() {
		JSONObject msgObject = new JSONObject();
		Map<String, Object> params = this.getParaMap();
		try {
			msgObject = this.aftersaleApplyService.beforeImportApplyList(params);
		} catch (ServiceException e) {
			logger.error("导出数据检查失败", e);
			msgObject.put("flag", false);
			msgObject.put("msg", "导出数据检查失败！");
			throw new ServiceException(e);
		}
		return msgObject;

	}

	@RequestMapping(value = "afterList")
	public String afterList(ModelMap map, AftersaleApply aftresal, String tblshow) {
		// 分页
		Page pageObj = this.getPageObj();
		// 集合
		List<AftersaleApply> aftersaleApplyList = null;
		try {
			aftersaleApplyList = this.aftersaleApplyService.findList_Like(pageObj, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY, aftresal);
		} catch (ServiceException e) {
			logger.error("AftersaleApplyController.afterList() ServiceException=售后管理-售后申请单集合", e);
			throw e;
		} catch (Exception e) {
			logger.error("AftersaleApplyController.afterList() Exception=售后管理-售后申请单集合", e);
			throw new ServiceException(e);
		}
		// 页签
		if (tblshow == "" || tblshow == null) {
			map.put("tblshow", "0");
		} else {
			map.put("tblshow", tblshow);
		}
		map.put("aftersaleApplyList", aftersaleApplyList);
		map.put("aftresal", aftresal);
		map.put("pageObj", pageObj);
		return "aftersale/apply/apply_list";
	}

	/******
	 * 跳转详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "toApprove")
	public String toApprove(String id, ModelMap map) {
		AftersaleApply apply = null;
		List<String> imageList = null;
		List<AftersaleApplyItem> aftersaleApplyItemList = null;
		List<OmsOrderRecord> omsOrderRecordList = null;
		SubSystemConfig ucSystem = null;
		SubSystemConfig orderSystem = null;
		List<SubSystemConfig> systemConfigList = null;
		if (StringUtil.isNotNull(id)) {
			try {
				// 售后申请单信息
				apply = this.aftersaleApplyService.findAftersaleApplyById(id);
				// 售后申请单商品详情
				AftersaleApplyItem item = new AftersaleApplyItem();
				item.setApplyId(id);
				aftersaleApplyItemList = this.aftersaleApplyItemService.findAftersaleApplyItemList(null, null, null, item);
				// 售后订单操作状态
				Map<String, Object> params = new HashMap<>();
				params.put("orderNo", apply.getOrderNo());
				// params.put("recordType", OrderDetailStatusConstant.NO);
				omsOrderRecordList = this.omsOrderRecordService.findByPage(null, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY, params);
				// 售后申请图片
				if (StringUtil.isNotNull(apply)) {
					String images = apply.getApplyPicImg();
					imageList = new ArrayList<>();
					if (StringUtil.isNotNull(images)) {
						for (String img : images.split(",")) {
							imageList.add(img);
						}
					}

				}
				// 获取子系统信息(域名和端口)
				ResultDto allResult = subSystemConfigApiService.getSubSystemConfigALl();
				if (allResult.getCode().equals(ResultDto.OK_CODE)) {
					systemConfigList = (List<SubSystemConfig>) allResult.getData();
					for (SubSystemConfig subSystemConfig : systemConfigList) {
						if (subSystemConfig != null && OrderConstant.UC_CODE.equals(subSystemConfig.getSubSystemCode())) {
							ucSystem = subSystemConfig;
						} else if (subSystemConfig != null && OrderConstant.OMS_CODE.equals(subSystemConfig.getSubSystemCode())) {
							orderSystem = subSystemConfig;
						}
						if (ucSystem != null && orderSystem != null) {
							break;
						}
					}
				} else {
					throw new ServiceException("SubSystemConfigApiConsumer-getSubSystem=>dubbo调用失败：" + allResult);
				}
			} catch (ServiceException e) {
				logger.error("AftersaleApplyController.toApprove() ServiceException=售后管理-售后申请单详情", e);
				throw e;
			} catch (Exception e) {
				logger.error("AftersaleApplyController.toApprove() ServiceException=售后管理-售后申请单详情", e);
				throw new ServiceException(e);
			}
		}
		map.put("applyID", id);
		map.put("apply", apply);
		map.put("imageList", imageList);
		map.put("aftersaleApplyItemList", aftersaleApplyItemList);
		// 金额显示格式
		DecimalFormat df = new DecimalFormat("0.00");
		Map<String, Object> priceMap = AftersaleAuxiliary.calculatePrice(aftersaleApplyItemList);
		BigDecimal actPayAmountSum = (priceMap.get("actPayAmountSum") == null ? new BigDecimal(0) : new BigDecimal(priceMap.get("actPayAmountSum").toString()));
		map.put("actPayAmountSum", actPayAmountSum);
		// 退款计算总金额（包括运费、优惠）
		BigDecimal sendCost = apply.getOrder().getSendCost() == null ? new BigDecimal(0) : apply.getOrder().getSendCost(); // 运费
		BigDecimal favorablePrice = (priceMap.get("favouredAmount") == null ? new BigDecimal(0) : new BigDecimal(priceMap.get("favouredAmount").toString())); // 优惠
		BigDecimal redPacketAmount = (priceMap.get("redPacketAmount") == null ? new BigDecimal(0) : new BigDecimal(priceMap.get("redPacketAmount").toString())); // 红包金额
		BigDecimal totalAmount = actPayAmountSum.add(sendCost).subtract(favorablePrice).subtract(redPacketAmount);
		map.put("sendCost", df.format(sendCost));
		map.put("favorablePrice", df.format(favorablePrice));
		map.put("redPacketAmount", df.format(redPacketAmount));
		map.put("totalAmount", df.format(totalAmount));
		map.put("omsOrderRecordList", omsOrderRecordList);
		map.put("image_path", System.getProperty("image.web.server"));
		map.put("ucDomain", ucSystem.getSubSystemDomain());
		map.put("ucPort", ucSystem.getSubSystemPort());
		map.put("orderDomain", orderSystem.getSubSystemDomain());
		map.put("orderPort", orderSystem.getSubSystemPort());
		return "aftersale/apply/apply_detail";
	}

	/*****
	 * 审核
	 * 
	 * @param apply
	 * @return
	 */
	@RequestMapping(value = "approveAftersaleapply")
	@ResponseBody
	public ResultVo approveAftersaleapply(AftersaleApply apply) {
		ResultVo resultVo = new ResultVo();
		// 执行操作
		try {
			ServiceCode serviceCode = this.aftersaleApplyService.approve(apply);
			if (serviceCode.getServiceResult().code() == 1) {
				resultVo.setInfoStr("售后取货单状态为已通过或者售后退款单状态为已通过时不能取消审核");
				resultVo.setStatus(Constant.RESULT_ERR);
				return resultVo;
			} else {
				resultVo.setResult(serviceCode, "/aftersaleapply/afterList.do");
			}
		} catch (Exception e) {
			logger.error("AftersaleApplyController.approveAftersaleapply() Exception=》售后管理-售后申请单审核", e);
			throw new ServiceException(e);
		}

		// 判断执行结课
		if (resultVo.isHasException()) {
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}

		return resultVo;
	}

	@RequestMapping(value = "getOmsOrderStatus")
	public void getOmsOrderStatus(HttpServletResponse response, String orderNo) {
		try {
			// 售后订单操作状态
			Map<String, Object> params = new HashMap<>();
			params.put("orderNo", orderNo);
			params.put("recordType", OrderDetailStatusConstant.NO);
			List<OmsOrderRecord> omsOrderRecordList = this.omsOrderRecordService.findByPage(null, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY, params);
			this.responseWrite(response, this.getJsonByObject(omsOrderRecordList));
		} catch (Exception e) {
			logger.error("AftersaleApplyController.getOmsOrderStatus() Exception=》售后状态-", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 
	 * @Title: applyAfterSale
	 * @Description: 申请售后
	 * @param orderId
	 * @param memberId
	 * @param serviceType
	 * @param skuId
	 * @param desc
	 * @param count
	 * @return ResultVo
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "applyAfterSale")
	public void applyAfterSale(String orderId, String memberId, String serviceType, String skuId, String desc, String count, String reason, HttpServletResponse response) {
		ResultVo resultVo = new ResultVo();
		/**
		 * 2016-08-29雷--售后申请同一用户、同订单不能同时提交
		 */
		String lockKey = memberId + "_" + orderId + "_addaftersale";
		try {
			if (redisTemplate.opsForValue().setIfAbsent(lockKey, "1")) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("orderId", orderId);
				params.put("serviceType", serviceType);
				params.put("skuId", skuId);
				params.put("count", count);
				Map<String, Object> resultMap = null;
				resultMap = this.aftersaleApplyService.saveAftersaleApply(params, memberId, desc, reason);
				getOutputMsg().put("STATUS", resultMap.get("STATUS"));
				getOutputMsg().put("MSG", resultMap.get("MSG"));
			}
		} catch (ServiceException e) {
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			getOutputMsg().put("MSG", e.getMessage());
			logger.error("AftersaleApplyController-applyAfterSale-ServiceException=》申请售后-保存", e);
		} catch (Exception e) {
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			getOutputMsg().put("MSG", e.getMessage());
			logger.error("AftersaleApplyController-applyAfterSale-Exception=》申请售后-保存", e);
		} finally {
			// 释放锁
			redisTemplate.delete(lockKey);
		}
		outPrint(response, this.getJsonByObject(this.getOutputMsg()));
	}

}

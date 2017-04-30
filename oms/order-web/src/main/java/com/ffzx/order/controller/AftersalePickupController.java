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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ffzx.basedata.api.dto.SubSystemConfig;
import com.ffzx.basedata.api.service.SubSystemConfigApiService;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.order.api.dto.AftersaleApply;
import com.ffzx.order.api.dto.AftersaleApplyItem;
import com.ffzx.order.api.dto.AftersalePickup;
import com.ffzx.order.api.dto.OmsOrderRecord;
import com.ffzx.order.common.AftersaleAuxiliary;
import com.ffzx.order.constant.OrderConstant;
import com.ffzx.order.service.AftersaleApplyItemService;
import com.ffzx.order.service.AftersaleApplyService;
import com.ffzx.order.service.AftersalePickupService;
import com.ffzx.order.service.OmsOrderRecordService;

/**
 * 售后管理
 * 
 * @className AftersaleApplyController.java
 * @author lushi.guo
 * @date
 * @version 1.0
 */
@Controller
@RequestMapping("/aftersaleapickup")
public class AftersalePickupController extends BaseController {
	// 日志
	protected final Logger logger = LoggerFactory.getLogger(AftersalePickupController.class);

	@Autowired
	private AftersaleApplyService aftersaleApplyService;
	@Autowired
	private AftersaleApplyItemService aftersaleApplyItemService;
	@Autowired
	private OmsOrderRecordService omsOrderRecordService;
	@Autowired
	private AftersalePickupService aftersalePickupService;
	@Autowired
	private SubSystemConfigApiService subSystemConfigApiService;

	

	@RequestMapping(value = "afterPickupList")
	public String afterPickupList(ModelMap map, AftersaleApply aftresal, AftersalePickup pickup, String tblshow) {
		// 分页
		Page pageObj = this.getPageObj();
		// 集后取货单
		List<AftersalePickup> aftersalePickupList = null;

		try {
			aftersalePickupList = this.aftersalePickupService.findAftersalePickupList_Like(pageObj, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY, aftresal, pickup);
		} catch (ServiceException e) {
			logger.error("AftersalePickupController.afterPickupList() ServiceException=售后管理-售后取货单集合", e);
			throw e;
		} catch (Exception e) {
			logger.error("AftersalePickupController.afterPickupList() Exception=售后管理-售后取货单集合", e);
			throw new ServiceException(e);
		}
		// 页签
		if (tblshow == "" || tblshow == null) {
			map.put("tblshow", "0");
		} else {
			map.put("tblshow", tblshow);
		}
		map.put("aftersaleApplyList", aftersalePickupList);
		map.put("aftresal", aftresal);
		map.put("pickup", pickup);
		map.put("pageObj", pageObj);
		return "aftersale/apply/apply_list";
	}

	/******
	 * 跳转详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "toApprove")
	public String toApprove(String id, ModelMap map, String pickupNo, String pageType) {
		AftersaleApply apply = null;
		AftersalePickup pickup = null;
		List<String> imageList = null;
		List<AftersaleApplyItem> aftersaleApplyItemList = null;
		List<OmsOrderRecord> omsOrderRecordList = null;
		Map<String, Object> params = new HashMap<>();
		SubSystemConfig ucSystem = null;
		SubSystemConfig orderSystem = null;
		List<SubSystemConfig> systemConfigList = null;
		try {

			params.put("id", id);
			params.put("pickupNo", pickupNo);
			// 售后申请单信息
			pickup = this.aftersalePickupService.findAftersalePickupByParams(params);
			apply = this.aftersaleApplyService.findAftersaleApplyById(pickup.getAftersaleApply().getId());
			/*
			 * String desc = new String(apply.getReasonExplain().getBytes("ISO8859-1"), "UTF-8"); apply.setReasonExplain(desc);
			 */
			// 售后申请单商品详情
			AftersaleApplyItem item = new AftersaleApplyItem();
			item.setApplyId(apply.getId());
			aftersaleApplyItemList = this.aftersaleApplyItemService.findAftersaleApplyItemList(null, null, null, item);
			// 售后订单操作状态
			params = new HashMap<>();
			params.put("orderNo", apply.getOrderNo());
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
			logger.error("AftersalePickupController.toApprove() ServiceException=售后管理-售后取货单详情", e);
			throw e;
		} catch (Exception e) {
			logger.error("AftersalePickupController.toApprove() Exception=售后管理-售后取货单详情", e);
			throw new ServiceException(e);
		}
		// 用于跳转
		map.put("pageType", pageType);
		map.put("applyID", id);
		map.put("pickupNo", pickupNo);
		map.put("apply", apply);
		map.put("pickup", pickup);
		map.put("imageList", imageList);
		map.put("aftersaleApplyItemList", aftersaleApplyItemList);
		map.put("omsOrderRecordList", omsOrderRecordList);
		// 金额显示格式
		DecimalFormat df = new DecimalFormat("0.00");
		Map<String, Object> priceMap = AftersaleAuxiliary.calculatePrice(aftersaleApplyItemList);
		BigDecimal actPayAmountSum = (priceMap.get("actPayAmountSum") == null ? new BigDecimal(0) : new BigDecimal(priceMap.get("actPayAmountSum").toString()));
		map.put("actPayAmountSum", actPayAmountSum);
		// 退款计算总金额（包括运费、优惠）
		BigDecimal sendCost = apply.getOrder().getSendCost() == null ? new BigDecimal(0) : apply.getOrder().getSendCost(); // 运费
		BigDecimal favorablePrice = (priceMap.get("favouredAmount") == null ? new BigDecimal(0) : new BigDecimal(priceMap.get("favouredAmount").toString())); // 优惠
		BigDecimal redPacketAmount= (priceMap.get("redPacketAmount")==null?new BigDecimal(0):new BigDecimal(priceMap.get("redPacketAmount").toString())); // 红包金额
		BigDecimal totalAmount = actPayAmountSum.add(sendCost).subtract(favorablePrice).subtract(redPacketAmount);
		map.put("sendCost", df.format(sendCost));
		map.put("favorablePrice", df.format(favorablePrice));
		map.put("redPacketAmount", df.format(redPacketAmount));
		map.put("totalAmount", df.format(totalAmount));
		map.put("image_path", System.getProperty("image.web.server"));
		map.put("ucDomain", ucSystem.getSubSystemDomain());
		map.put("ucPort", ucSystem.getSubSystemPort());
		map.put("orderDomain", orderSystem.getSubSystemDomain());
		map.put("orderPort", orderSystem.getSubSystemPort());
		return "aftersale/pickup/pick_detail";
	}


	/*****
	 * 审核
	 * 
	 * @param apply
	 * @return
	 */
	@RequestMapping(value = "approveAftersaleapply")
	public ResultVo approveAftersaleapply(AftersaleApply apply) {
		ResultVo resultVo = new ResultVo();
		// 执行操作
		try {
			ServiceCode serviceCode = this.aftersaleApplyService.approve(apply);
			resultVo.setResult(serviceCode, "/aftersaleapply/afterList.do?");
		} catch (Exception e) {
			logger.error("AftersalePickupController.approveAftersaleapply() Exception=》售后管理-售后取货单审核", e);
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

	@RequestMapping(value = "toPrinter")
	public String toPrinter(String id, ModelMap map) {
		AftersaleApply apply = null;
		List<AftersaleApplyItem> aftersaleApplyItemList = null;
		if (StringUtil.isNotNull(id)) {
			try {
				// 售后申请单信息
				apply = this.aftersaleApplyService.findAftersaleApplyById(id);
				// 售后申请单商品详情
				AftersaleApplyItem item = new AftersaleApplyItem();
				item.setApplyId(apply.getId());
				aftersaleApplyItemList = this.aftersaleApplyItemService.findAftersaleApplyItemList(null, null, null, item);
			} catch (ServiceException e) {
				logger.error("AftersalePickupController.toPrinter() ServiceException=售后管理-售后取货单详情-打印", e);
				throw e;
			}
		}
		map.put("apply", apply);
		map.put("aftersaleApplyItemList", aftersaleApplyItemList);
		return "aftersale/pickup/apply_printer";
	}

}

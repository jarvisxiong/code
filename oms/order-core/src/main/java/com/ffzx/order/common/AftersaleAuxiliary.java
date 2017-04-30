/**   
 * @Title: AftersaleAuxiliary.java
 * @Package com.ffzx.order.common
 * @Description: 售后业务辅助处理类
 * @author 雷  
 * @date 2016年9月14日 
 * @version V1.0   
 */
package com.ffzx.order.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ffzx.bms.api.dto.OrderParam;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.order.api.dto.AftersaleApply;
import com.ffzx.order.api.dto.AftersaleApplyItem;
import com.ffzx.order.api.dto.AftersaleRefund;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderRecord;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.enums.AfterSaleStatusType;
import com.ffzx.order.api.enums.OrderTypeEnum;
import com.ffzx.order.constant.OrderDetailStatusConstant;
import com.ffzx.order.constant.OrderStatusConstant;
import com.ffzx.wms.api.dto.Warehouse;

/**
 * @ClassName: AftersaleAuxiliary
 * @Description: 售后业务辅助处理类
 * @author 雷
 * @date 2016年9月14日
 * 
 */
public class AftersaleAuxiliary {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(AftersaleAuxiliary.class);
	/**
	 * 
	 * 雷------2016年9月23日
	 * 
	 * @Title: calculatePrice
	 * @Description: 计算商品总价
	 * @param @param aftersaleApplyItemList
	 * @param @return 设定文件
	 * @return Map<String,Object> 返回类型
	 * @throws
	 */
	public static Map<String, Object> calculatePrice(List<AftersaleApplyItem> aftersaleApplyItemList) {
		BigDecimal actPayAmountSum = new BigDecimal(0);
		BigDecimal favouredAmount = new BigDecimal(0);
		BigDecimal redPacketAmount = new BigDecimal(0);
		for (AftersaleApplyItem aftersaleApplyItem : aftersaleApplyItemList) {
			actPayAmountSum = actPayAmountSum.add(aftersaleApplyItem.getCommodityPrice().multiply(new BigDecimal(aftersaleApplyItem.getReturnNum())).setScale(2, BigDecimal.ROUND_HALF_UP));
			if (null != aftersaleApplyItem.getFavouredAmount() || null != aftersaleApplyItem.getRedPacketAmount()) {
				/**
				 * 2016-08-24--雷--优惠金额的修复
				 */
				if (aftersaleApplyItem.getCommodityBuyNum() != aftersaleApplyItem.getReturnNum()) {
					if (null != aftersaleApplyItem.getRedPacketAmount())
						redPacketAmount = redPacketAmount.add(new BigDecimal(aftersaleApplyItem.getRedPacketAmount().doubleValue() / aftersaleApplyItem.getCommodityBuyNum() * aftersaleApplyItem.getReturnNum()));
					if (null != aftersaleApplyItem.getFavouredAmount())
						favouredAmount = favouredAmount.add(new BigDecimal(aftersaleApplyItem.getFavouredAmount().doubleValue() / aftersaleApplyItem.getCommodityBuyNum() * aftersaleApplyItem.getReturnNum()));
				} else {
					if (null != aftersaleApplyItem.getRedPacketAmount())
						redPacketAmount = redPacketAmount.add(aftersaleApplyItem.getRedPacketAmount());
					if (null != aftersaleApplyItem.getFavouredAmount())
						favouredAmount = favouredAmount.add(aftersaleApplyItem.getFavouredAmount());
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("actPayAmountSum", actPayAmountSum);
		map.put("favouredAmount", favouredAmount);
		map.put("redPacketAmount", redPacketAmount);
		return map;
	}

	/**
	 * 
	 * 雷------2016年9月22日
	 * 
	 * @Title: setRecord
	 * @Description: 退款单，订单记录
	 * @param @param orderId
	 * @param @param orderNo
	 * @param @param remarks
	 * @param @param date
	 * @param @param user
	 * @param @return 设定文件
	 * @return OmsOrderRecord 返回类型
	 * @throws
	 */
	public static OmsOrderRecord setRecord(String orderId, String orderNo, String remarks, Date date,SysUser user) {
		// 订单记录表
		OmsOrderRecord record = new OmsOrderRecord();
		// 新增订单记录
		record.setId(UUIDGenerator.getUUID()); // 生成id
		// 订单记录赋值
		record.setOrderId(orderId);
		record.setOrderNo(orderNo);
		record.setOprId(user.getId());
		record.setOprName(user.getName());
		record.setCreateDate(date);
		record.setRecordType(OrderDetailStatusConstant.NO);
		record.setDescription(remarks);
		return record;
	}

	/**
	 * 
	 * 雷------2016年9月22日
	 * 
	 * @Title: setRefund
	 * @Description: 退款单赋值
	 * @param @param refund
	 * @param @param date
	 * @param @param state
	 * @param @param user
	 * @param @return 设定文件
	 * @return AftersaleRefund 返回类型
	 * @throws
	 */
	public static AftersaleRefund setRefund(AftersaleRefund refund, Date date, String state,SysUser user) {
		refund.setRefundStatus(state); // 付款单状态
		refund.setLastUpdateBy(user);
		refund.setLastUpdateDate(date);
		refund.setPayDate(date); // 退款支付时间
		/**
		 * 审核退款时，记录审核操作者信息
		 */
		if (OrderDetailStatusConstant.REFUND_APPROVE_SUC.equals(state)) {
			refund.setAuditingId(user.getId());
			refund.setAuditingName(user.getLoginName());
			refund.setAuditingTime(date);
		}
		return refund;
	}

	/**
	 * @param orderDetails 
	 * 
	 * 雷------2016年9月19日
	 * 
	 * @Title: combinationChangeOrder
	 * @Description: 换货重发订单信息的装配
	 * @param @param user
	 * @param @param order
	 * @param @param orderId
	 * @param @return 设定文件
	 * @return OmsOrder 返回类型
	 * @throws
	 */
	public static OmsOrder combinationChangeOrder(SysUser user, OmsOrder order, String orderId, List<OmsOrderdetail> orderDetails) {
		BigDecimal actualPrice = new BigDecimal(0);
		BigDecimal favorablePrice = new BigDecimal(0);
		BigDecimal totalPrice = new BigDecimal(0);
		BigDecimal totalRedAmount = new BigDecimal(0);
		order.setId(orderId);
		order.setStatus(OrderStatusConstant.STATUS_RECEIPT_OF_GOODS);
		order.setOrderType(OrderTypeEnum.EXCHANGE_ORDER);
		/**
		 * 计算主订单金额
		 */
		if (null != orderDetails && orderDetails.size() > 0) {
			for (OmsOrderdetail detail : orderDetails) {
				if (null != detail.getActPayAmount())
					actualPrice = actualPrice.add(detail.getActPayAmount());
				if (null != detail.getFavouredAmount())
					favorablePrice = favorablePrice.add(detail.getFavouredAmount());
				if (null != detail.getActSalePrice())
					totalPrice = totalPrice.add(detail.getActSalePrice().multiply(new BigDecimal(detail.getBuyNum())));
				if (null != detail.getRedPacketAmount())
					totalRedAmount = totalRedAmount.add(detail.getRedPacketAmount());

			}
		}
		order.setActualPrice(actualPrice);
		order.setFavorablePrice(favorablePrice);
		order.setTotalPrice(totalPrice);
		order.setTotalRedAmount(totalRedAmount);
		order.setPayTime(new Date());
		order.setCreateDate(new Date());
		order.setCreateBy(user);
		order.setCurLoisticsStatus(null);
		return order;

	}

	/**
	 * 
	 * 雷------2016年10月15日
	 * 
	 * @Title: combinationChangeOrder
	 * @Description: 换货重发订单明细的装配
	 * @param @param user
	 * @param @param order
	 * @param @param orderId
	 * @param @return 设定文件
	 * @return OmsOrder 返回类型
	 * @throws
	 */
	public static List<OmsOrderdetail> combinationChangeOrderDetail(OmsOrder order, String orderId, List<OmsOrderdetail> orderDetails) {
		List<OmsOrderdetail> detail = new ArrayList<OmsOrderdetail>();
		/**
		 * 赠品主id
		 */
		String mainOrderdetailId = UUIDGenerator.getUUID();
		for (OmsOrderdetail orderDetail : orderDetails) {
			orderDetail.setActPayAmount(calculateReturnPrice(orderDetail.getActPayAmount(), orderDetail.getBuyNum(), orderDetail.getReturn_num()));
			orderDetail.setFavouredAmount(calculateReturnPrice(orderDetail.getFavouredAmount(), orderDetail.getBuyNum(), orderDetail.getReturn_num()));
			orderDetail.setRedPacketAmount(calculateReturnPrice(orderDetail.getRedPacketAmount(), orderDetail.getBuyNum(), orderDetail.getReturn_num()));;
			orderDetail.setOrderDetailStatus(OrderDetailStatusConstant.STATUS_NOR);
			orderDetail.setOrderId(orderId);
			orderDetail.setOrderNo(order.getOrderNo());
			orderDetail.setBuyNum(orderDetail.getReturn_num());
			/**
			 * 对买赠的做不同的区别
			 */
			if (OrderDetailStatusConstant.BUYGIFTS_MAIN.equals(orderDetail.getBuyGifts())) {
				orderDetail.setId(mainOrderdetailId);
			} else if (OrderDetailStatusConstant.BUYGIFTS_SECONDARY.equals(orderDetail.getBuyGifts())) {
				orderDetail.setId(UUIDGenerator.getUUID());
				orderDetail.setOrderdetailId(mainOrderdetailId);
			} else {
				orderDetail.setId(UUIDGenerator.getUUID());
			}
			/**
			 * 组织推送给wms的信息
			 */
			detail.add(orderDetail);
		}
		return detail;

	}
	/**
	 * 
	 * 雷------2016年12月6日
	 * @Title: calculateReturnPrice
	 * @Description: 计算多次售后金额的准确性
	 * @param @return    设定文件
	 * @return BigDecimal    返回类型
	 * @throws
	 */
	public static BigDecimal calculateReturnPrice(BigDecimal price, Integer bugNum, Integer returnNum) {
		if (null != price)
			return new BigDecimal(price.doubleValue() / bugNum * returnNum).setScale(2, BigDecimal.ROUND_DOWN);
		else
			return new BigDecimal(0);
	}

	/**
	 * 
	 * 雷------2016年9月19日
	 * 
	 * @Title: preholdingStore
	 * @Description: 质检通过，换货重发订单计算预占
	 * @param @param order
	 * @param @param orderDetails
	 * @param @return 设定文件
	 * @return List<OrderParam> 返回类型
	 * @throws
	 */
	public static List<OrderParam> preholdingStore(OmsOrder order, List<OmsOrderdetail> orderDetails) {
		List<OrderParam> list = new ArrayList<OrderParam>();
		if (null != orderDetails && orderDetails.size() > 0)
			for (OmsOrderdetail orderDetail : orderDetails) {
				// 库存预占
				Warehouse warehouse = new Warehouse();
				warehouse.setId(orderDetail.getWarehouseId());
				warehouse.setCode(orderDetail.getWarehouseCode());
				warehouse.setName(orderDetail.getWarehouseName());
				list.add(new OrderParam(orderDetail.getSkuCode(), new Long(orderDetail.getReturn_num()), warehouse));
			}

		return list;
	}

	/**
	 * 
	 * 雷------2016年9月14日
	 * 
	 * @Title: getReturnGiftNum
	 * @Description: 计算退、换赠品的数量
	 * @param @param singleGiftNum
	 * @param @param triggerNum
	 * @param @param buyNum
	 * @param @param mainReturnNum
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public static int getReturnGiftNum(Integer singleGiftNum, Integer triggerNum, Integer buyNum, Integer mainReturnNum) throws ServiceException {
		int giftNum = (int) (Math.ceil((double) mainReturnNum / triggerNum) * singleGiftNum);
		if (buyNum < giftNum)
			return giftNum = buyNum;
		return giftNum;
	}

	/**
	 * 
	 * 雷------2016年9月14日
	 * 
	 * @Title: getApplyItems
	 * @Description: 申请售后时，将多商品明细统一转成售后明细处理
	 * @param @param applyItems
	 * @param @param skuId
	 * @param @param count
	 * @param @param afterSaleApply
	 * @param @param aftersaleCode
	 * @param @param detail
	 * @param @param item
	 * @param @return 设定文件
	 * @return List<AftersaleApplyItem> 返回类型
	 * @throws
	 */
	public static List<AftersaleApplyItem> getApplyItems(List<AftersaleApplyItem> applyItems, Integer count, AftersaleApply afterSaleApply, OmsOrderdetail detail, AftersaleApplyItem item) throws ServiceException {
		setAftersaleItmeValue(count, afterSaleApply, detail, item);
		applyItems.add(item);
		return applyItems;
	}

	/**
	 * 
	 * 雷------2016年9月14日
	 * 
	 * @Title: setAftersaleItmeValue
	 * @Description: 将订单明细转成售后明细
	 * @param @param skuId
	 * @param @param count
	 * @param @param afterSaleApply
	 * @param @param aftersaleCode
	 * @param @param detail
	 * @param @param item
	 * @param @return 设定文件
	 * @return AftersaleApplyItem 返回类型
	 * @throws
	 */
	public static AftersaleApplyItem setAftersaleItmeValue(Integer count, AftersaleApply afterSaleApply, OmsOrderdetail detail, AftersaleApplyItem item) throws ServiceException {
		item.setId(UUIDGenerator.getUUID());
		item.setCommodityPic(detail.getCommodityImage());
		item.setCommodityName(detail.getCommodityTitle());
		item.setCommoditySpecifications(detail.getCommoditySpecifications());
		item.setCommodityUnit(detail.getCommodityUnit());
		/**
		 * 如果是赠品，金额都为0
		 */
		if (OrderDetailStatusConstant.BUYGIFTS_SECONDARY.equals(detail.getBuyGifts())) {
			item.setCommodityPrice(new BigDecimal(0));
			item.setActPayAmount(new BigDecimal(0));
		} else {
			item.setCommodityPrice(detail.getActSalePrice());
			item.setActPayAmount(detail.getActPayAmount());
		}
		if (null != detail.getBuyType()) {
			item.setCommodityBuyType(detail.getBuyType().getValue());
		}
		item.setCommodityBuyNum(detail.getBuyNum());
		item.setCommodityBarcode(detail.getCommodityBarcode());
		item.setReturnNum(Integer.valueOf(count));
		if (AfterSaleStatusType.REFUND_GOODS.getValue().equals(afterSaleApply.getApplyType())) {
			item.setAftersaleStatus(OrderDetailStatusConstant.STATUS_REFING);
		} else if (AfterSaleStatusType.CHANGE_GOODS.getValue().equals(afterSaleApply.getApplyType())) {
			item.setAftersaleStatus(OrderDetailStatusConstant.STATUS_CHANGING);
		} else if (AfterSaleStatusType.REFUND_TOTAL.getValue().equals(afterSaleApply.getApplyType())) {
			item.setAftersaleStatus(OrderDetailStatusConstant.STATUS_REFING);
		}
		item.setApplyId(afterSaleApply.getId());
		item.setSkuId(detail.getSkuId());
		item.setFavouredAmount(detail.getFavouredAmount());
		item.setRedPacketAmount(detail.getRedPacketAmount());
		// 雷----2016-07-29做修改
		item.setApplyNo(afterSaleApply.getApplyNo());
		item.setCommodityAttributeValues(detail.getCommodityAttributeValues());
		/**
		 * 雷------2016-09-14 买赠
		 */
		item.setMainCommodityId(detail.getOrderdetailId());
		item.setBuyGifts(detail.getBuyGifts());
		return item;
	}

	/**
	 * 雷------2016年9月23日
	 * 
	 * @Title: setAftersaleApplyValue
	 * @Description: 后台售后申请单数据组装
	 * @param @param memberId
	 * @param @param serviceType
	 * @param @param desc
	 * @param @param reason
	 * @param @param afterSaleApply
	 * @param @param order
	 * @param @param aftersaleCode
	 * @param @return 设定文件
	 * @return AftersaleApply 返回类型
	 * @throws
	 */
	public static AftersaleApply setAftersaleApplyValue(String memberId, String serviceType, String desc, String reason, AftersaleApply afterSaleApply, OmsOrder order, ResultDto aftersaleCode) {
		afterSaleApply.setId(UUIDGenerator.getUUID());
		afterSaleApply.setApplyPersonAddress(order.getAddressInfo());
		afterSaleApply.setApplyNo(aftersaleCode.getData().toString());
		afterSaleApply.setApplyStatus(OrderDetailStatusConstant.APPROVE_NO);
		afterSaleApply.setApplyPersonId(memberId);
		afterSaleApply.setOrder(order);
		afterSaleApply.setOrderNo(order.getOrderNo());
		afterSaleApply.setApplyType(serviceType);
		afterSaleApply.setReasonExplain(desc);
		afterSaleApply.setCreateDate(new Date());
		afterSaleApply.setReasonSelect(reason);
		afterSaleApply.setApplyPersonName(order.getMemberName());
		afterSaleApply.setApplyPersonPhone(order.getMemberPhone());
		afterSaleApply.setLastUpdateDate(new Date());
		afterSaleApply.setApplyPersonAddress(order.getAddressInfo());
		return afterSaleApply;
	}

}

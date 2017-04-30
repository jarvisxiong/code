package com.ffzx.order.utils;

import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.enums.LogisticsInfo;

public class OmsConstant {
	/**
	 * redis 前缀
	 */
	public static final String REDIS_PREFIX = "ORDER_";

	/**
	 * redis 有效期 1小时 单位：s
	 */
	public static final Long REDIS_EXPIRETIME_1H = (long) 3600;

	/**
	 * redis 有效期 30分钟 单位：s
	 */
	public static final Long REDIS_EXPIRETIME_30MIN = (long) 1800;
	
	/**
	 * redis 有效期 15分钟 单位：s
	 */
	public static final Long REDIS_EXPIRETIME_15MIN = (long) 900;

	/**
	 * redis 有效期 1分钟 单位：s
	 */
	public static final Long REDIS_EXPIRETIME_1MIN = (long) 60;

	/**
	 * redis 订单推送采购订单 redis key
	 */
	public static final String OMS_PURCHASEORDERMQAPI_PURCHASEORDER = "OMS_PURCHASEORDERMQAPI_PURCHASEORDER";

	/**
	 * redis wms推送到订单物流状态 redis key
	 */
	public static final String QUEUE_WMS_OUTBOUND_ORDER_STATUS = "QUEUE_WMS_OUTBOUND_ORDER_STATUS";
	/**
	 * 
	 * 雷------2016年6月28日
	 * 
	 * @Title: LogisticsConvert
	 * @Description: 物流信息的转化
	 * @param @param state
	 * @param @param remark
	 * @param @param order
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String LogisticsConvert(int state, String remark, OmsOrder order) {
		String description = "";
		switch (state) {
		case 1:
			description = LogisticsInfo.getName("0") + order.getStorageName() + LogisticsInfo.getName("3");
			break;
		case 4:
			description = LogisticsInfo.getName("0") + order.getStorageName() + LogisticsInfo.getName("4");
			break;
		case 5:
			description = LogisticsInfo.getName("0") + order.getStorageName() + LogisticsInfo.getName("5");
			break;
		case 6:
			description = LogisticsInfo.getName("0") + order.getStorageName() + LogisticsInfo.getName("6");
			break;
		case 7:
			description = LogisticsInfo.getName("0") + order.getStorageName() + LogisticsInfo.getName("7") + order.getServicePoint() + LogisticsInfo.getName("10");
			break;
		default:
			description = remark;
			break;
		}
		return description;
	}
}

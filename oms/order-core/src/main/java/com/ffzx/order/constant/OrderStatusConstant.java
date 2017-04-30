package com.ffzx.order.constant;

public class OrderStatusConstant {
	/*****
	 *  0：待付款，6：支付处理中，1：待收货，2：退款申请中，3：交易关闭，4：交易完成，5：订单已取消
	 */
	public static final String STATUS_PENDING_PAYMENT="0";
	public static final String STATUS_RECEIPT_OF_GOODS="1";
	public static final String STATUS_REFUND_APPLICATION="2";
	public static final String STATUS_TRANSACTION_CLOSED="3";
	public static final String TRANSACTION_COMPLETION="4";
	public static final String ORDER_CANCELED="5";
	public static final String ORDER_PROCESSING="6";
}

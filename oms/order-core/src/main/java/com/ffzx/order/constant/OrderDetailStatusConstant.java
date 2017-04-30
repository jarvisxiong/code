package com.ffzx.order.constant;

/**
 * 公共常量类
 * 
 * @author lushi.guo
 * @date "2""0""1"6年5月5日 
 * @version "0"."1"."0" 
 */
public final class OrderDetailStatusConstant {

	/*****
	 *  0：正常
	 */
	public static final String STATUS_NOR="0";
	/**
	 * 1：退款处理中
	 */
	public static final String STATUS_REFING="1";
	/**
	 * 2：换货处理中
	 */
	public static final String STATUS_CHANGING="2";
	/**
	 * 3：退款成功
	 */
	public static final String STATUS_REFSUC="3";
	/**
	 * 4：换货成功
	 */
	public static final String STATUS_CHANGESUC="4";	

	/****
	 * 0 未审核
	 */
	public static final String APPROVE_NO="0";
	/**
	 * 1已通过
	 */
	public static final String APPROVE_SUC="1";
	/**
	 * 2已驳回( 取消)
	 */
	public static final String APPROVE_REJECT="2";
	
	/****
	 * 0 未审核，1审核通过，2付款成功
	 */
	public static final String REFUND_APPROVE_NO="0";
	public static final String REFUND_APPROVE_SUC="1";
	public static final String REFUND_PAY="2";
	
	/****
	 * 0 退款(没收到货)
	 */
	public static final String REFUND_TOTAL="0";
	/**
	 * ，1退货(已收到货)，
	 */
	public static final String REFUND_GOODS="1";
	/**
	 * 2换货(已收到货)
	 */
	public static final String CHANGE_GOODS="2";
	/**
	 *  3订单分配错误
	 */
	public static final String DIS_ERROR="3";
	/**
	 * 是
	 */
	public static final String YES = "1";
	/** 否 
	 * */
	public static final String NO = "0";
	
	public static final String RECOVERED_NOT = "0";
	public static final String RECOVERED_ALREADY = "1";
	/**
	 * 买赠主商品标识
	 */
	public static final String BUYGIFTS_MAIN="1";
	/**
	 * 买赠品商品标识
	 */
	public static final String BUYGIFTS_SECONDARY="2";
	
	
}

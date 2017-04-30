package com.ffzx.aftersale.constant;

public class ApiConstants {
	/**
	 * app	调用返回结果类型
	 */
	public static final int RESULT_TYPE_ = -1 ;   //接口调用失败，未找到对应编码接口
	
	public static final int RESULT_TYPE_0 = 0 ;   //接口调用异常\
	public static String DEFAULTIMG = "/asset/images/a1.jpg";//默认头像
	
	public static final int RESULT_TYPE_1 = 1 ;//调用成功
	
	public static final int RESULT_TYPE_2 = 2 ;//调用失败:参数等未传.调用方引起的调用失败
	
	/**
	 * app	调用返回状态
	 */
	public static final int  RESULT_STATUS_500 = 500; //网络异常,请稍后重试;
	
	public static final int  RESULT_STATUS_100 = 100; //正常结果反回状态
	
	public static final int  RESULT_STATUS_200 = 200; //结果状态异常(人为正常的错误类型 如:商品不能买,外在条件引起的异常)
	
	
	/**
	 * map  key定义
	 */
	
	public static final String ERROR_MSG = "errorMsg"; //错误消息
	
	public static final String RESULT_MSG = "resultMsg"; //正常结果消息  若正常返回数据. errorMsg则为空字符串
	
	public static final String RESULT_TYPE = "resultType"; //结果类型
	
	public static final String RESULT_STATUS = "resultStatus"; //结果状态
	
	public static final String RESULT_DATA = "resultData"; //结果对象
	

	//基础城市地址
	public static final String BASE_CITY = "河南省>郑州市>新郑市";
	//基础城市ID 
	public static final String BASE_CITY_ID = "71b1f606b817461b9b8768f12d888365";
	//获取地址redis数据
	public static final String GETADDRESS="getaddress";
	
	//获取地址行政级别缓存数据
	public static final String ADDRESSTYPE="address_type";
	public static final int IOS=2;//代表设备IOS
	public static final int ANDROID=1;//代表设备ANDROID
	public static final String USER="USER";
	
	/**
	 * map  value定义
	 */
	
	public static final String ERROR_MSG_VALUE = "未捕获的异常信息"; //错误消息
	
	public static final String RESULT_MSG_OK = "OK" ;
	
	
	public static final String RESULT_MSG_DECRYPT = "解密失败" ;

	
	/*****
	 *  0：待付款，1：待收货，2：退款申请中，3：交易关闭，4：交易完成，5：订单已取消
	 */
	public static final String STATUS_PENDING_PAYMENT="0";
	public static final String STATUS_RECEIPT_OF_GOODS="1";
	public static final String STATUS_REFUND_APPLICATION="2";
	public static final String STATUS_TRANSACTION_CLOSED="3";
	public static final String TRANSACTION_COMPLETION="4";
	public static final String ORDER_CANCELED="5";
	
	public static final String ORDERID_NULL="订单id不能为空！";
	public static final String SERVICE_TYPE="售后服务类型不能为空！";
	public static final String REASON_NULL="售后服务原因不能为空！";
	public static final String STATUS_NULL="订单状态不能为空！";
	public static final String UID_NULL="用户id不能为空！";
	public static final String NUMBER_NULL="订单编号不能为空！";
	public static final String PAGE_NULL="页数不能为空！";
	public static final String PAGESIEZE_NULL="每页显示数量不能为空！";
	public static final String DATE_NULL="没有查到数据！";
	public static final String SUBMITERROR="提交数据失败！";
	public static final String ORDER_NULL="没有查到订单信息！";
	public static final String MEMBER_NULL="没有查到申请人信息！";
	public static final String AFTERSALE_ERROR="该商品已经申请了售后，请耐心等待结果！";
	public static final String AFTERSALE_ORDERERROR="该订单已经申请了售后，请耐心等待结果！";
	public static final String AFTERSALE_DELAY="该订单或者商品已经过申请售后的有效时间，请联系客服！";
	
	/*****
	 *  0：正常，1：退款处理中，2：换货处理中，3：退款成功，4：换货成功
	 */
	public static final String STATUS_NOR="0";
	public static final String STATUS_REFING="1";
	public static final String STATUS_CHANGING="2";
	public static final String STATUS_REFSUC="3";
	public static final String STATUS_CHANGESUC="4";
	
	//已结束
	public static final String TWO="2";

	
	//系统设置中使用的常量
	public static final String DICT_APPSETTING_TYPE = "APPSETTING";//数据字典中“APP系统设置”的类型代码
	public static final String DICT_APPSETTING_UPDATEDATA_ANDROID = "APPSETTING_UPDATEDATA_ANDROID";//数据字典中“APP系统设置”的更新APP(ANDROID)的名称
	public static final String DICT_APPSETTING_UPDATEDATA_IOS = "APPSETTING_UPDATEDATA_IOS";//数据字典中“APP系统设置”的更新APP(IOS)的名称
	public static final String DICT_APPSETTING_HOTKEY = "APPSETTING_HOTKEY";//数据字典中“APP系统设置”的热词的名称
	public static final String DICT_APPSETTING_MENU = "APPSETTING_MENU";//数据字典中个人中心菜单的类别名称
	/**
	 * 精确到分的时间
	 */
	public static final String MINUTE_DATA_FORMAT = "yyyy-MM-dd HH:mm";
	
	/**
	 * 8.8好五县同庆各会唱广告区域编码
	 */
	public static final String FFCHD_1 ="FFCHD_1";
	public static final String FFCHD_2 ="FFCHD_2";
	public static final String FFCHD_3 ="FFCHD_3";
	public static final String FFCHD_4 ="FFCHD_4";
	public static final String FFCHD_5 ="FFCHD_5";
	public static final String FFCHD_6 ="FFCHD_6";
	public static final String ZHC ="ZHC";
	public static final String HAS_BQQD_ICON ="HAS_BQQD_ICON";
	public static final String BQQD_ICON_PATH ="BQQD_ICON_PATH";
	
	/**
	 * 抢购缓存时间
	 */
	public static final String PANIC_CACHE_TIME ="PANIC_CACHE_TIME";
	public static final String PANIC_LIST_CACHE_TIME ="PANIC_LIST_CACHE_TIME";
	
	
	/**
	 * 抢购活动状态
	 */
	public static final String PANIC_STATUS_1="即将开始";
	public static final String PANIC_STATUS_2="抢购中";
	public static final String PANIC_STATUS_3="已结束";
	
	
	public static final String YES = "1";
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
	 * 买赠中：交易完成，且没有申请退换货，X天后，系统自动发放上述优惠券
	 */
	public static final int EFFECTIVE_DAYS = 15;
}

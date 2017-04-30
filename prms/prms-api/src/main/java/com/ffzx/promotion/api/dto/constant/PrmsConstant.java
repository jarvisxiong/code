package com.ffzx.promotion.api.dto.constant;

import com.ffzx.commerce.framework.constant.RedisPrefix;

/**
 * @Description: prms公共常量类
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年8月22日 下午2:44:05
 * @version V1.0 
 *
 */
public final class PrmsConstant {


	/**
	 * 已结束，进行中，未开始  app专用
	 */
	
	public static final String sortdateweiJingxing="1";//未开始
	public static final String sortdateJingxing="2";//进行中
	public static final String sortdatejiesu="3";//结束
	
	public static final String messagecoupon="4";
	public static final String skycoupon="5";

	public static final String sortdateweiJingxingString="即将开始";//未开始
	public static final String sortdateJingxingString="抢购中";//进行中
	public static final String sortdatejiesuString="已结束";//结束

	public static final String MinSecond="HH:mm";//小时分

	public static final String NullString="";//分秒
	/**
	 * 精确到分的时间
	 */
	public static final String MINUTE_DATA_FORMAT = "yyyy-MM-dd HH:mm";
	//已结束
	public static final String TWO="2";
	
	public static final String RESULT_TYPE_2="2";

	//已结束
	public static final String ZERO="0";
	
	
	//items
	public static final String ITEMS="items";
	//总数
	public static final String RECORDCOUNT="recordCount";
	
	//排序asc
	public static final String ASC="ASC";

	//成功
	public static final String SUCCESS="success";
	//失败
	public static final String ERROR="error";
	
	public static final String EDIT="编辑";

	public static final String SEND="发布";
	public static final String LUCKNO="计算幸运号";
	public static final String SHAREORDER="设置晒单";
	public static final String CANCEL="撤销";
	public static final String GOLUCK="已经超过了可抽奖次数";
	
	/***
	 * 免费抽奖
	 */
	public static final String REWARD_MANAGER="reward_manager:";
	
	//中奖
	public static final String BONDED="1";
	public static final String NOBOND="0";//未中奖
	
	/***
	 * 参与人次
	 */
	public static final String DRAW_COUNT="draw_count:";
	
	public static final String LOCKKEY="lockKey:";
	
	public static final Integer defaultValue=10000000;
	public static final String EXCEPTIONLOCK="异常解锁";
	public static final String CHAOSHILOCK="超时解锁";
	public static final String ERRORM="生成幸运号码失败！";
	public static final String REWARDDATE="reward_date";
	public static final String ERRORMESSAGE="进入设置晒单失败，原因：当前还没有进行幸运号计算，请先进行计算。";
	
	public static final String SENDERROR="很遗憾，红包已经发完了！";
	
	public static final String REDPACKAGE="redpackage:";
	
	public static final String COUPONGRANT="couponGrant:";
	
	public static final String REDPACKAGENUM="redpackageNum:";
	
	public static final String COUPONNUM="couponNum:";
	public static final String MESSAGE="message:";
	public static final String SENDREDORCOU="send:";
	
	public static final String SENDEDERROR="-1000";
	
	public static final String SENDCOUNTERROR="-2000";
	/**
	 * 获取红包发放总量KEY
	 * @param redpackageId
	 * @return
	 */
	public static  String getRedpackageGrantNum(String redpackageId){
		return REDPACKAGENUM+redpackageId;
	}
	
	/**
	 * 获取优惠券发放总量key
	 * @param grantId
	 * @return
	 */
	public static String getCouponGrantNum(String grantId){
		return COUPONNUM+grantId;
	}
	
	/**
	 * 获取红包已经发放数量
	 * @param redpackageId
	 * @return
	 */
	public static String getRedpackSendedNum(String redpackageId){
		return REDPACKAGENUM+redpackageId+"_num";
	}
	
	/**
	 * 获取优惠券已经发放数量
	 * @param grantId
	 * @return
	 */
	public static String getCouponSendedNum(String grantId){
		return COUPONGRANT+grantId+"_num";
	}
	
	public static String getDrawCount(String rewardId){
		return REWARD_MANAGER+DRAW_COUNT+rewardId+"_count";
	}
	
	public static  String getLockKey(String rewardId){
		return LOCKKEY+rewardId+"_count" ;
	}
	
	/**
	 * 红包锁 key
	 * @param grantId
	 * @return
	 */
	public static String getRedpackLockKey(String grantId,String redpackageId){
		return LOCKKEY+REDPACKAGE+grantId+"_"+redpackageId;
	}
	
	/**
	 * 消息锁 key
	 * @param grantId
	 * @param memberId
	 * @return
	 */
	public static String getMessageLockKey(String grantId,String memberId){
		return LOCKKEY+MESSAGE+memberId+"_"+grantId;
	}
	
	/**
	 * 消息 key
	 * @param grantId
	 * @param memberId
	 * @return
	 */
	public static String getMessageRedis(String grantId,String memberId){
		return MESSAGE+memberId+"_"+grantId+"_send";
	}
	
	/**
	 * 用户发放 key
	 * @param grantId
	 * @param memberId
	 * @return
	 */
	public static String getRedisMemberIdSend(String grantId,String memberId){
		return SENDREDORCOU+memberId+"_"+grantId+"_sed";
	}
	
	/**
	 * 优惠券锁key
	 * @param grantId
	 * @return
	 */
	public static String getCouponLockKey(String grantId){
		return LOCKKEY+COUPONNUM+grantId;
	}
	
	/***app下单**/
	public static final String APP_PAY_BUYNUM="app_pay_buyNum:";
	
	/**
	 * 活动缓存
	 */
	public static final String ACTIVITY="activity:";
	
	/***
	 * 活动限购量
	 */
	public static final String ACTIVITY_LIMIT="activity_limit:";
	
	/****
	 * 用户限购量
	 */
	public static final String ACTIVITY_IDLIMIT="activity_idlimit:";
	
	/***
	 * 活动日期
	 */
	public static final String ACTIVITY_DEVLIDATE="activity_devlidate:";
	
	/***
	 * 活动价格
	 */
	public static final String ACTIVITY_PRICE="activity_price:";
	
	/***
	 * 活动对象
	 */
	public static final String ACTIVITY_OBJ="activity_obj:";
	
	/***
	 * 活动商品列表
	 */
	public static final String ACTIVITY_COMMODITY="activity_commodity:";
	
	/**
	 * 获取商品已购买数量key
	 * @param commodityNo
	 * @param activityCommodityId
	 * @return
	 */
	public static String getCommodityBuyNum(String commodityNo,String activityCommodityId){
		
		return APP_PAY_BUYNUM + activityCommodityId + "_" + commodityNo + "_buyNum";
	}
	
	/***
	 * 获取商品用户已购买量key
	 * @param commodityNo
	 * @param activityCommodityId
	 * @param userId
	 * @return
	 */
	public static String getCommodityUserBuyNum(String commodityNo,String activityCommodityId,String userId){
		
		return APP_PAY_BUYNUM + userId + "_" + activityCommodityId + "_" + commodityNo+ "_buyNum";
	}
	
	/**
	 * 获取商品SKU已购买量
	 * @param activityCommodityId
	 * @param skuCode
	 * @return
	 */
	public static String getCommoditySkyBuyNum(String activityCommodityId,String skuCode){
		
		return APP_PAY_BUYNUM + activityCommodityId + "_" + skuCode + "_buyNum";
	}
	
	/***
	 * 获取预售发货时间key
	 * @param activityCommodityId
	 * @param commodityNo
	 * @return
	 */
	public static String getPreSaleDevliveDateKey(String activityCommodityId,String commodityNo){
		
		return ACTIVITY + ACTIVITY_DEVLIDATE + activityCommodityId + "_" + commodityNo + "_deliverDate";
	}
	
	
	/***
	 * 获取活动商品限购量Key
	 * @param activityCommodityId
	 * @param commodityNo
	 * @return
	 */
	public static String getCommodityLimitKey(String activityCommodityId,String commodityNo){
		
		return ACTIVITY + ACTIVITY_LIMIT + activityCommodityId + "_" + commodityNo + "limit";
	}
	
	/***
	 * 获取活动商品用户限购量key
	 * @param activityCommodityId
	 * @param commodityNo
	 * @return
	 */
	public static String getCommodityIdLimitKey(String activityCommodityId,String commodityNo){
		
		return ACTIVITY + ACTIVITY_IDLIMIT + activityCommodityId + "_" + commodityNo + "idlimit";
	}
	
	/***
	 * 获取活动商品SKU限购量key
	 * @param activityCommodityId
	 * @param skuCode
	 * @return
	 */
	public static String getCommoditySkuLimitKey(String activityCommodityId,String skuCode){
		
		return ACTIVITY + ACTIVITY_LIMIT + activityCommodityId + "_" + skuCode + "limit";
	}
	
	/***
	 * 获取活动商品对象key
	 * @param activityCommodityId
	 * @param commodityNo
	 * @return
	 */
	public static String getActivityCommodityObj(String activityCommodityId,String commodityNo){
		
		return ACTIVITY + ACTIVITY_OBJ + activityCommodityId + "_" + commodityNo;
	}
	
	/***
	 * 获取活动商品对象key
	 * @param activityCommodityId
	 * @param commodityNo
	 * @return
	 */
	public static String getPriceKey(String activityCommodityId,String skuCode){
		return ACTIVITY + ACTIVITY_PRICE + activityCommodityId + "_"+ skuCode + "price";
	}
	
	/**
	 * 用户限购量提示
	 * @param title
	 * @param idLimit
	 * @return
	 */
	public static String isIdLimit(String title,Integer idLimit){
		return "【" + title + "】单个用户最多只允许购买" + idLimit + "件，请修改数量后重试！";
	}
	
	public static String isLimit(String title,Integer limit,String value){
		return "【" + title + value + "】最多只允许购买" + limit + "件，请修改数量后重试！";
	}
	
	public static String passIdLimit(String title){
		return "【" + title + "】已超过用户最大限购量，请抢购其他商品，修改后重试，我知道啦！";
	}
	
	public static String overSkuLimit(String title,String value){
		return "【" + title + value + "】已抢光啦，请修改后重试    我知道啦！";
	}
	
	public static String exception(String title,String value){
		return "【" + title + value + "】活动太火爆了，请稍后重试！";
	}
	public static final String isVartual="0";//是虚拟商品
	public  static final String notVartual="1";//不是虚拟商品
	

	

	
	//date_start char DEFAULT '1' COMMENT '(时间限制，是自领取后生效1，自定有效期0）',
	//领取有效
	public static final String RECEIVEEFFECTIVE="1";
	//自定义有效
	public static final String CUSTEMEFFECTIVE="0";

	public static final String UPDATE="修改";

	public static final String ADD="新增";
	
	public static final String Disable="禁用";
	public static final String Enable="启用";
	public static final String DELETE="删除";
	public static final String NUMBER="number";
	public static final String RedPackageMessage="非凡大麦场送您现金红包啦，限时限量，打开直接领取>>";
	public static final String COUPONMessage="非凡大麦场送您优惠券啦！速来领取>>";
	public static final String TWOMESSAGE="很遗憾，您已经拆过一次！";
	public static final String COUPONMESSAGE="很遗憾，您领取过一次！";
	public static final String RedPackagetitle ="红包提醒";
	public static final String Coupontitle ="优惠券提醒";
	public static final String 	messageType="message_type";
	public static final String STARTDATE="start_date";
	

	//消息
	public static final String RedpackageMessagegrantMode="1";
	//天降
	public static final String RedpackageFallinggrantMode ="2";
	
	public static final String NOGET="-100";
	public static final String OVER="-200";
	public static final String TWOGET="-300";
	
	public static final String PACKAGESUCC="100";
	public static final String COUPONSUCC="200";

	//未使用
	public static final String UseNo="0";
	//已使用
	public static final String UseYse="1";
	//过期
	public static final String Useoverdue="2";
	
	//1表示android，2表示ios',

	public static final String android="1";
	public static final String ios="2";
	public static final String CouponMessageCode="2";
	public static final String RedpacageMessageCode="1";

	public static final String CouponMessageMovement="4";//优惠券消息
	public static final String CouponFallingMovement="5";//优惠券天降
	public static final String CouponUserReceive="1";//优惠券天降

}

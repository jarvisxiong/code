package com.ffzx.promotion.api.dto.constant;

/**
 * 公共常量类
 * 
 * @author lushi.guo
 * @date "2""0""1"6年5月5日 
 * @version "0"."1"."0" 
 */
public final class Constant {

	/*****
	 * 优惠券状态（"0"未生效，"1"已生效，"2"已过期）
	 * 活动商品状态(0 即将开始 1 进行中 2 已结束)
	 */
	public static final String STATUS_UNEFFECT="0";
	public static final String STATUS_EFFECT="1";
	public static final String STATUS_OVERDUR="2";
	public static final String STATUS_EMPTY="3";//已抢完
		
	/*******
	 * 优惠券有效期（"1":指定有效期，"0":自定义有效期）
	 */
	public static final String DATE_FIXED="1";
	public static final String DATE_CUSTOM="0";
	
	/*****
	 * 优惠券选择商品（"0"全部商品，1,指定普通活动"2"指定商品，"3"指定商品类目）
	 */
	public static final String COMM_ALL="0";
	public static final String ActivityPT="1";
	public static final String COMM_FIXED="2";
	public static final String COMM_CATEGORY="3";
	
	/*****
	 * 优惠券发放方式（"0"系统推送,"1"用户领取,"2"指定用户）
	 */
	public static final String GRANT_SYS="0";
	public static final String GRANT_USERREC="1";
	public static final String GRANT_FIXEDUSER="2";
	/*****
	 * 优惠券发放类型（"0"商品，"1"注册，"2"分享）
	 */
	public static final String GRANT_COMM="0";
	public static final String GRANT_REG="1";
	public static final String GRANT_SHARE="2";
	/*****
	 * 优惠券领取状态（"0"未开始,"1"领取中,"2"领取完成,"3",已结束）
	 */
	public static final String REC_UNSTART="0";
	public static final String REC_ING="1";
	public static final String REC_SUCCESS="2";
	public static final String REC_END="3";
	/** 是;优惠券其他限制（"1"有限制）;
	 * 优惠券领取（"1"暂停领取）；
	 * 优惠券使用状态（"1"使用）
	 */
	public static final String YES = "1";
	/** 否 ;
	 * 优惠券其他限制（"0"没有限制）;
	 * 优惠券领取（"0"不暂停领取）；
	 * 优惠券使用状态（"0"未使用）*/
	public static final String NO = "0";
	
	public static final int putong = 0;
	public static final int main = 1;
	
	/***
	 * 添加商品各种提示
	 */
	public static final String ERROR_RE="该商品已经参加了其他活动，请先在其他活动删除该商品！";
	public static final String ERROR_EX="限定数量超过当前用户可购买最大数量，请修改后重试！";
	public static final String ERROR_TIME="设置的开始结束时间和另外的批次有重合！";
	public static final String ERROR_PC="一个批次不允许同一个商品多次存在！";
	public static final String GIVE_PC="该商品作为主商品参加了买赠活动，还未启用，请启用该商品后重试！";

	//商品和优惠券

	public static final String Goods = "0";
	public static final String Coupon = "1";
	public static final String GoodsAndCoupon = "2";
	public static final String ID = "id";
	public static final String COUPON="coupon";
	public static final String ORDERINTY="orderinty";
	

	/**
	 * 买赠说明
	 */
	public static final String commodityStrtop="购买满"; 
	public static final String commodityStrend="件,领超值赠品(赠完即止)"; 
	public static final String couponStrend="件,赠送优惠券（赠完即止）"; 
	public static final String textStr1="有赠品时，确认下单页面，可以看到赠品。";
	public static final String textTop="下单购买，每满";
	public static final String coupontextmid="件，交易完成，且没有申请退换货，15天后，系统自动发放上述优惠券。(赠完即止)";
	public static final String coupontextend="天后，系统自动发放上述优惠券。(赠完即止)";
	public static final String GIVETYPE="_give";
	public static final String GIFTYPE="_gift";
	public static final String EXCEPTIONLOCK="异常解锁";
	public static final String CHAOSHILOCK="超时解锁";
	
	/***
	 * 买赠活动
	 */
	public static final String ACTIVITY_GIVE="activity_give:";
	
	/***
	 * 主商品限定数量
	 */
	public static final String GIVE_LIMIT="give_limit:";
	
	/***
	 * 主商品id限购量
	 */
	public static final String GIVE_IDLIMIT="give_idlimt:";
	
	/***
	 * 主商品触发量
	 */
	public static final String GIVE_TRIGGERCOUNT="give_triggercount:";
	
	/***
	 * 赠品限定数量
	 */
	public static final String GIFT_LIMIT="gift_limit:";
	
	/***
	 * 主商品购买量
	 */
	public static final String GIVE_PAY_NUM="give_pay_num:";
	
	/**
	 * 赠品赠送数量
	 */
	public static final String GIFT_PAY_NUM="gift_pay_num:";
	
	/**
	 * 赠品单次赠送量
	 */
	public static final String GIFT_ONE_NUM="gift_one_num:";
	
	/**
	 * 主商品限定数量key值获取(为空 赋值为 -1)
	 * @param commodityCode（商品编码）
	 * @return
	 */
	public static String getLimitKey(String giveId,String commodityCode){
		return ACTIVITY_GIVE+GIVE_LIMIT+giveId+"_"+commodityCode+"_limit";
	}
	
	/**
	 * 主商品ID限购量key值获取(为空 赋值为 -1)
	 * @param commodityCode（商品编码）
	 * @return
	 */
	public static String getIdLimitKey(String giveId,String commodityCode){
		return ACTIVITY_GIVE+GIVE_IDLIMIT+giveId+"_"+commodityCode+"_idlimit";
	}
	
	/**
	 * 主商品触发量key值获取(为空 赋值为 -1)
	 * @param commodityCode（商品编码）
	 * @return
	 */
	public static String getTriggerKey(String giveId,String commodityCode){
		return ACTIVITY_GIVE+GIVE_TRIGGERCOUNT+giveId+"_"+commodityCode+"_trigger";
	}
	
	/**
	 * 赠品限定数量(为空 赋值为 -1)
	 * @param giveId（主品表ID）
	 * @param skuCode（赠品SKU编码）
	 * @return
	 */
	public static String getGiftLimit(String giveId,String skuCode){
		return ACTIVITY_GIVE+GIFT_LIMIT+giveId+"_"+skuCode+"_limit";
	}
		
	/**
	 * 主商品购买量key 值获取
	 * @param commodityCode
	 * @return
	 */
	public static String getGivePayKey(String giveId,String commodityCode){
		return ACTIVITY_GIVE+GIVE_PAY_NUM+giveId+"_"+commodityCode+"_buyNum";
	}
	
	
	/**
	 * 赠品赠送数量
	 * @param giveId（主品表ID）
	 * @param skuCode (赠品SKU编码)
	 * @return
	 */
	public static String getGiftPayKey(String giveId,String skuCode){
		return ACTIVITY_GIVE+GIFT_PAY_NUM+giveId+"_"+skuCode+"_buyNum";
	}
	
	/**
	 * 赠品单次赠送量(为空 赋值为 -1)
	 * @param giftCommodityId（赠品表ID）
	 * @param skuCode (赠品SKU编码)
	 * @return
	 */
	public static String getOneGiftKey(String giftCommodityId,String skuCode){
		return ACTIVITY_GIVE+GIFT_ONE_NUM+giftCommodityId+"_"+skuCode+"_oneNum";
	}
	
	public static  String getLockKey(String giveId,String commodityNo,String type){
		return giveId+ "_" + commodityNo+type;
	}
	
	/**
	 * 主商品购买量key 值获取(用户)
	 * @param commodityCode
	 * @return
	 */
	public static String getMemberGivePayKey(String giveId,String commodityCode,String memeberId){
		return ACTIVITY_GIVE+GIVE_PAY_NUM+giveId+"_"+commodityCode+"_"+memeberId+"_buyNum";
	}
	
	
	public static String getGiveIdLimistStr(String value){
		return "【" + value + "】已超过用户最大限购量，请购买其他商品，修改后重试，我知道啦！";
	}
	
	public static String getGiveLimitStr(String value,Integer userBuyNum){
		return "【" + value + "】最多只允许购买" + userBuyNum + "件，请修改数量后重试！";
	}
	
	public static String getGiveEndLimitStr(String value){
		return "【" + value + "】已售罄，请修改后重试    我知道啦！";
	}
	
	public static String getStr(String value){
		return "【" + value + "】活动太火爆了，请稍后重试！";
	}
	
	public static String getGiftLimitStr(String value){
		return "亲，提交订单失败啦，原因：【" + value + "】当前数量不足，请返回后，重新提交订单。";
	}
	
	public static String getRedpackageStr(String value){
		return "【" + value + "】领取的人太多了，请稍后重试！";
	}

	
}

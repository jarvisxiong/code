package com.ffzx.promotion.constant;

/***
 * mq相关约定字段
 * @author ying.cai
 * @date 2016年9月12日 上午10:41:36
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 */
public class MqConstant {
	//活动信息新增/编辑mq exchange name
	public static final String PRMS_ACTIVITY_CHANGE = "PRMS_ACTIVITY_CHANGE";
	//活动信息新增/编辑redis look key
	public static final String QUEUE_ELASTICSEARCH_ACTIVITY_UPDATE_LOOK = "QUEUE_ELASTICSEARCH_ACTIVITY_UPDATE_LOOK";
	
	//活动信息移除mq exchange name
	public static final String PRMS_ACTIVITY_REMOVE = "PRMS_ACTIVITY_REMOVE";
	//活动信息移除 redis look key
	public static final String QUEUE_ELASTICSEARCH_ACTIVITY_REMOVE_LOOK = "QUEUE_ELASTICSEARCH_ACTIVITY_REMOVE_LOOK";
	
	
}

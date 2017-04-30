package com.ffzx.promotion.api.service;

import java.util.List;

/***
 * 移除活动索引
 * 当预售，抢购，普通活动的数据被移除时，都需要调用此类方法推送消息至mq
 * @author ying.cai
 * @date 2016年9月12日 上午10:22:38
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public interface RemoveActivityIndex {
	
	/***
	 * 移除活动或者移除活动商品时触发
	 * @param goodsId 商品ID
	 * @date 2016年9月12日 上午10:25:34
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	void sendMqByRemoveActivity(String goodsId);
	
	/***
	 * 批量移除时触发
	 * @param idList
	 * @date 2016年9月14日 上午11:18:30
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	void sendMqByRemoveActivity(List<String> idList);
}

package com.ffzx.promotion.api.dto;

import java.io.Serializable;

/***
 * exchange:amq.activity.topic
 * queue:QUEUE_ELASTICSEARCH_ACTIVITY_REMOVE
 * 搜索引擎dto (移除活动信息)
 * @author ying.cai
 * @date 2016年9月9日 下午2:59:50
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public class ActivityEsDelVo implements Serializable{
	private static final long serialVersionUID = -2719329673632843834L;
	
	/**商品ID*/
	private String goodsId;

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	
}

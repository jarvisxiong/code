package com.ffzx.promotion.api.service;

import java.util.Date;

import com.ffzx.promotion.api.dto.ActivityEsVo;

/***
 * 更新活动索引
 * 当预售，抢购，普通活动的数据有新增或任何修改时，都需要调用此类方法推送消息至mq
 * @author ying.cai
 * @date 2016年9月12日 上午10:21:36
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public interface UpdateActivityIndex {
	
	/***
	 * 活动数据更改时触发
	 * @param goodsId 商品ID
	 * @param title1 活动标题
	 * @param imgPath 图片路径
	 * @param discountPrice 优惠券
	 * @param type 售卖类型
	 * @param activityId 活动id
	 * @param startDate 活动开始时间
	 * @param endDate 活动结束时间
	 * @date 2016年9月12日 下午2:11:10
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	void sendMqByUpdateActivity(String goodsId,String title1,String imgPath,String discountPrice,
			String type,String activityId,Date startDate,Date endDate);
	
	/***
	 * 重载方法，活动数据更改时触发
	 * @param activityEsVo
	 * @date 2016年9月12日 上午10:26:27
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	void sendMqByUpdateActivity(ActivityEsVo activityEsVo);
	
	/***
	 * 快捷处理中心
	 * @param activityManagerId 活动管理id
	 * @param operateType 操作类型 0撤销  1发布
	 * @date 2016年9月14日 上午10:46:03
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	void activityResolved(String activityManagerId,String operateType);
	
	/***
	 * 当在商品设置中新增或者修改商品信息时，需要看此商品对应的活动管理是否已启用，如果已启用，则需要推送mq 
	 * @param activityCommodityId
	 * @date 2016年10月10日 下午5:06:46
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	void updateCommodity(String activityCommodityId);
	
	/***
	 * 当一个商品变成批发或者新用户专享时，调用此方法
	 * 当商品在批发或者新用户专享活动中被移除时，调用此方法
	 * @param goodsId
	 * @param type
	 * @date 2016年11月18日 下午3:54:20
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	void removeSpecialActivity(String goodsId,String type);
}

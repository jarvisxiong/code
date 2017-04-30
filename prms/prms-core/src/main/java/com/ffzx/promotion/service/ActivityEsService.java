package com.ffzx.promotion.service;

import java.util.List;

import com.ffzx.promotion.api.dto.ActivityEsVo;

/***
 * 
 * @author ying.cai
 * @date 2016年9月12日 下午4:31:31
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public interface ActivityEsService {
	
	/***
	 * 根据商品id获取与索引相关活动信息的接口 	
	 * @param goodsId
	 * @date 2016年9月12日 下午4:24:19
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	List<ActivityEsVo> findActivityForESByCommodityId(String goodsId); 
}

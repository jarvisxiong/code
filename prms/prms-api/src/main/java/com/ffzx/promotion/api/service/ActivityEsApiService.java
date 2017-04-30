package com.ffzx.promotion.api.service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.promotion.api.dto.ActivityEsVo;

/***
 * 搜索引擎相关api提供
 * @author ying.cai
 * @date 2016年9月12日 下午4:24:32
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public interface ActivityEsApiService {
	
	/***
	 * 根据商品id获取与索引相关活动信息的接口 	
	 * @param goodsId
	 * @date 2016年9月12日 下午4:24:19
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	ResultDto findActivityForESByCommodityId(String goodsId);
}

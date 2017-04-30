package com.ffzx.promotion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.promotion.api.dto.ActivityEsVo;
import com.ffzx.promotion.mapper.ActivityCommodityMapper;
import com.ffzx.promotion.service.ActivityEsService;

/***
 * 
 * @author ying.cai
 * @date 2016年9月12日 下午4:32:27
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
@Service
public class ActivityEsServiceImpl implements ActivityEsService{
	
	@Autowired
	private ActivityCommodityMapper activityCommodityMapper;
	@Override
	public List<ActivityEsVo> findActivityForESByCommodityId(String goodsId) {
		List<ActivityEsVo> activityEsVoList = activityCommodityMapper.findActivityForESByCommodityId(goodsId);
		return activityEsVoList;
	}
	
	public void fillDefaultValue(ActivityEsVo activityEsVo){
		if(null!=activityEsVo){
			activityEsVo.setIsVip(1);
		}
	}

}

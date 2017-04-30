package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.api.dto.GiftCommodity;
import com.ffzx.promotion.mapper.GiftCommodityMapper;
import com.ffzx.promotion.service.GiftCommodityService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-09-12 11:25:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("giftCommodityService")
public class GiftCommodityServiceImpl extends BaseCrudServiceImpl implements GiftCommodityService {

	@Resource
	private GiftCommodityMapper giftCommodityMapper;

	@Override
	public CrudMapper init() {
		return giftCommodityMapper;
	}

	@Override
	public Map<String, Object> getMapValue() {
		// 排除掉参加了买赠的商品
		Map<String, Object> giftMap = new HashMap<String, Object>();
		giftMap.put("delFlag", Constant.DELTE_FLAG_NO);
		List<GiftCommodity> giftList = this.giftCommodityMapper.selectByParams(giftMap);
		List<String> commodityIdList = new ArrayList<String>();
		if (giftList != null && giftList.size() != 0) {
			for (GiftCommodity gift : giftList) {
				commodityIdList.add(gift.getCommodityId());
			}
		}
		Map<String, Object> params =null;
		if (commodityIdList != null && commodityIdList.size() != 0) {
			params =new HashMap<String, Object>();
			params.put("goodsId", commodityIdList);
		}
		return params;
	}
}
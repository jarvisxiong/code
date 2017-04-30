package com.ffzx.promotion.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.mapper.AppRecommendAwardsMapper;
import com.ffzx.promotion.service.AppRecommendAwardsService;

/**
 * @className AppRecommendAwardsServiceImpl
 *
 * @author liujunjun
 * @date 2016-10-08 16:06:27
 * @version 1.0.0
 */
@Service("appRecommendAwardsService")
public class AppRecommendAwardsServiceImpl extends BaseCrudServiceImpl implements AppRecommendAwardsService {

	@Resource
	private AppRecommendAwardsMapper appRecommendAwardsMapper;
	
	@Override
	public CrudMapper init() {
		return appRecommendAwardsMapper;
	}	
}
package com.ffzx.promotion.api.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.promotion.api.dto.ActivityEsVo;
import com.ffzx.promotion.api.service.ActivityEsApiService;
import com.ffzx.promotion.service.ActivityEsService;

@Service
public class ActivityEsApiServiceImpl implements ActivityEsApiService{
	
	private Logger logger = LoggerFactory.getLogger(ActivityEsApiServiceImpl.class);
	@Autowired
	private ActivityEsService activityEsService;
	
	@Override
	public ResultDto findActivityForESByCommodityId(String goodsId) {
		logger.info("根据商品id获取活动信息 START ===>>>");
		ResultDto result = new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
		try {
			List<ActivityEsVo> activityEsVoList = activityEsService.findActivityForESByCommodityId(goodsId);
			//add by ying.cai 2016-11-17 ,为脏数据赋值价格
			for(ActivityEsVo activityEsVo:activityEsVoList){
				if(activityEsVo.getMinPrice()==null || activityEsVo.getMinPrice().doubleValue()<=0){
					logger.info("minprice价格为0");
					String discountPrice = activityEsVo.getDiscountPrice();
					if(discountPrice.indexOf("-")>=0){
						String[] maxmain=discountPrice.split("-");
						activityEsVo.setMinPrice(new BigDecimal(maxmain[0].trim()));
						activityEsVo.setMaxPrice(new BigDecimal(maxmain[1].trim()));
					}else{
						activityEsVo.setMinPrice(new BigDecimal(discountPrice.trim()));
						activityEsVo.setMaxPrice(new BigDecimal(discountPrice.trim()));
					}
						
				}
			}
			result.setData(activityEsVoList);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
		return result;
	}

}

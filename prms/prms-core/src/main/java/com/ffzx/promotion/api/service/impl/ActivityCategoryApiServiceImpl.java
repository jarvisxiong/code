package com.ffzx.promotion.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.api.dto.CouponAdminCategory;
import com.ffzx.promotion.api.service.ActivityCategoryApiService;
import com.ffzx.promotion.mapper.CouponAdminCategoryMapper;

@Service("activityCategoryApiService")
public class ActivityCategoryApiServiceImpl extends BaseCrudServiceImpl implements ActivityCategoryApiService {
    @Resource
    private CouponAdminCategoryMapper couponAdminCategoryMapper;

    @Override
    public CrudMapper init() {
        return couponAdminCategoryMapper;
    }
	@Override
	public ResultDto selectActivityCategory(String couponId) {
		ResultDto resDto=null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("couponId", couponId);
			List<CouponAdminCategory> list=this.couponAdminCategoryMapper.selectByParams(params);
			resDto.setData(list);
		} catch (Exception e) {
			logger.error("ActivityCategoryApiServiceImpl-selectActivityCategory-Exception=》获取优惠券对应的商品或者类目列表", e);
//			resDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return resDto;
	}

}

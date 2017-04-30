package com.ffzx.promotion.api.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.CouponAdmin;

/**
 * 优惠券数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月11日 下午4:25:32
 */
public interface CouponApiService extends BaseCrudService {
		
	/**
	 * orderByField 可以null
	 * orderBy 可以null
	 * @param couponAdmin   
	 * set  effectiveDateStr-优惠券开始过期的时间     ;   `delFlag`-'启用标记（0：未启用；1：启用）',
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto findCoupon(Page page,String orderByField,String orderBy,CouponAdmin couponAdmin) throws ServiceException;
}
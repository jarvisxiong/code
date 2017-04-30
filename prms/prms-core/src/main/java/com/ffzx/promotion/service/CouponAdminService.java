package com.ffzx.promotion.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.CouponAdmin;

/**
 * coupon_admin数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016-05-03 17:57:39
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface CouponAdminService extends BaseCrudService {
	
	/*****
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<CouponAdmin> getCouponList(Page page,Map<String, Object> params)throws ServiceException;
	
	/**
	 * 保存
	 * @param coupon
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode save(CouponAdmin coupon) throws ServiceException;
	
	/*******
	 * 启用
	 * @param coupon
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode runCoupon(CouponAdmin coupon)throws ServiceException;
	

	/*****
	 * 分页查询优惠券-优惠券发放弹出列表
	 * @param map
	 * @return
	 */
	public List<CouponAdmin> getCouponAdminList(Page page, String orderByField, String orderBy,Map<String, Object> map)throws ServiceException;

}
package com.ffzx.promotion.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.CouponAdminCategory;

/**
 * coupon_admin_category数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016-05-03 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface CouponAdminCategoryService extends BaseCrudService {

	public void save(Map<String, Object>map)throws ServiceException;
	
	public List<CouponAdminCategory> findList(Page page,Map<String, Object> params)throws  ServiceException;

	public List<CouponAdminCategory> findCommodity(Map<String, Object> param);
}
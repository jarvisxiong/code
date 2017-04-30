package com.ffzx.promotion.api.service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * coupon_grant数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月4日 下午5:27:16
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface RedpackageGrantApiService extends BaseCrudService {

	/**
	 * 统计优惠券使用
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @date 2016年11月10日 上午9:32:18
	 * @version V1.0 
	 * @throws ServiceException
	 */
	public ResultDto redpackagegrant() throws ServiceException;
	

}
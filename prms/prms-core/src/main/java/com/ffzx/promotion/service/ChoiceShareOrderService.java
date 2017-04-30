package com.ffzx.promotion.service;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.ChoiceShareOrder;

/**
 * choice_share_order数据库操作接口
 * 
 * @author ffzx
 * @date 2016-10-24 11:24:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface ChoiceShareOrderService extends BaseCrudService {
	
	public ServiceCode saveChoiceOrder(ChoiceShareOrder choice)throws ServiceException;

}
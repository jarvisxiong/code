package com.ffzx.promotion.service;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.Advert;

/**
 * @className AdvertService
 *
 * @author hyl
 * @date 2016-05-03 17:18:53
 * @version 1.0.0
 */
public interface AdvertService extends BaseCrudService{
	public  int autoUpdateStatus() throws  ServiceException;
	
	/**
	 * 删除广告、修改替补广告为原始广告
	* @param advert
	* @param alternate
	* @throws ServiceException     
	* @return void    返回类型
	 */
	public void updateAdvertDate(Advert advert, Advert alternate) throws ServiceException;
}

package com.ffzx.promotion.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.ActivityPreSaleTag;
import com.ffzx.promotion.model.PreSaleTag;

/**
 * activity_pre_sale_tag数据库操作接口
 * 
 * @author ffzx
 * @date 2016-08-17 17:55:51
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface ActivityPreSaleTagService extends BaseCrudService {

	/***
	 * 获取预售标签集合
	 * @param pageObj
	 * @param orderByField
	 * @param orderBy
	 * @return
	 * @throws ServiceException
	 */
	public List<ActivityPreSaleTag> findList(Page pageObj, String orderByField, String orderBy,Map<String, Object> params)throws ServiceException;
	
	/**
	 * 新增或者修改预售标签
	 * @param tag
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode saveAndUpdatePreSaleTag(ActivityPreSaleTag tag)throws ServiceException;
	
	/***
	 * 删除预售标签
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode deletePreSaleTag(String id)throws ServiceException;
	
	/**
	 * 根据标签名称或者排序号查询标签
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<ActivityPreSaleTag> findPresaleTagByNumberOrName(PreSaleTag tag)throws ServiceException;
}
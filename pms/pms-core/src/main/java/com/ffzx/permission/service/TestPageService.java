package com.ffzx.permission.service;

import java.util.List;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.permission.model.TestPage;

/**
 * test_page数据库操作接口
 * 
 * @author Generator
 * @date 2016-02-22 11:39:07
 * @version 1.0.0
 * @copyright facegarden.com
 */
public interface TestPageService extends BaseCrudService {
	
	/**
	 * 更新
	 * @param testPage
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode save(TestPage testPage) throws ServiceException;
	
	/**
	 * 根据参数查询列表
	 * 
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<TestPage> findList(Page page, String orderByField, String orderBy, TestPage testPage) throws ServiceException;

}
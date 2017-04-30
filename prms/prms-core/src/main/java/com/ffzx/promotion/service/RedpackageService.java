package com.ffzx.promotion.service;

import java.util.List;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.commodity.api.dto.SaleTemplate;
import com.ffzx.promotion.api.dto.Redpackage;

/**
 * redpackage数据库操作接口
 * 
 * @author ffzx
 * @date 2016-11-07 09:41:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface RedpackageService extends BaseCrudService {

	public List<Redpackage> findList(Page pageObj,Redpackage redpackage);
	
	/**
	 * 新增或修改红包
	 * @param dict
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode saveOrUpdate(Redpackage redpackage) throws ServiceException;
	/**
	 * 获取详细信息
	 * @param id
	 * @return
	 */
	public Redpackage findBydetail(String id);
	/**
	 * 更改状态，写操作日志，判断是否加入红包
	 * @param id
	 * @return
	 */
	public ServiceCode updateState(Redpackage redpackage);
	
	/**
	 * 删除数据，判断是否加入红包，写操作日志
	 */
	public ServiceCode deleteRedpackage(Redpackage redpackage);
}
package com.ffzx.promotion.service;

import java.util.List;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.Redpackage;
import com.ffzx.promotion.api.dto.RedpackageGrant;

/**
 * redpackage_grant数据库操作接口
 * 
 * @author ffzx
 * @date 2016-11-07 09:41:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface RedpackageGrantService extends BaseCrudService {
	
	public List<RedpackageGrant> findList(Page pageObj,RedpackageGrant redpackageGrant);
	
	/**
	 * 新增或修改红包
	 * @param dict
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode saveOrUpdate(RedpackageGrant redpackageGrant) throws ServiceException;
	/**
	 * 获取详细信息
	 * @param id
	 * @param isHandleInfo 是否显示处理信息，编辑为false，详情为true
	 * @return
	 */
	public RedpackageGrant findBydetail(String id,boolean isHandleInfo);
	/**
	 * 更改状态，写操作日志，判断是否加入红包
	 * @param id
	 * @return
	 */
	public ServiceCode updateState(RedpackageGrant redpackageGrant);
	/**
	 * 更改发放，加事务
	 * @param id
	 * @return
	 */
	public ServiceCode updateRepackageGrant(RedpackageGrant redpackageGrant);
	
	/**
	 * 删除数据，判断是否加入红包，写操作日志
	 */
	public ServiceCode deleteRedpackage(RedpackageGrant redpackageGrant);
}
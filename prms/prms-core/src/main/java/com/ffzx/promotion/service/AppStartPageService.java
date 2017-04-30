package com.ffzx.promotion.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.AppStartPage;

/**
 * @className AppStartPageService
 *
 * @author liujunjun
 * @date 2016-09-09 14:43:37
 * @version 1.0.0
 */
public interface AppStartPageService extends BaseCrudService{
	
	/**
	* 查询APP启动页列表
	* @param pageObj 分页数据
	* @param appStartPage 过滤条件
	* @throws ServiceException     
	* @return List<AppStartPage>    返回类型
	 */
	public List<AppStartPage> findList (Page pageObj, AppStartPage appStartPage) throws ServiceException;
	
	/**
	* 验证是否存在重叠时间数据
	* @param params
	* @throws ServiceException     
	* @return List<AppStartPage>    返回类型
	 */
	public List<AppStartPage> findListByDate(Map<String, Object> params) throws ServiceException;
	
	/**
	* TODO(修改启动页广告)
	* @param appStartPage
	* @throws ServiceException     
	* @return int    返回类型
	 */
	public int updateBySelective(AppStartPage appStartPage) throws ServiceException;
}

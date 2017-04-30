package com.ffzx.promotion.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.ActivityGive;
import com.ffzx.promotion.vo.GiveExportVo;

/**
 * activity_give数据库操作接口
 * 
 * @author ffzx
 * @date 2016-09-12 11:25:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface ActivityGiveService extends BaseCrudService {

	public String saveGiveDate(ActivityGive activityGive) throws Exception;
	public List<ActivityGive> findList(Page page, String orderByField, String orderBy, ActivityGive activityGive) throws ServiceException;
	
	public Map<String, Object> getActivityGiveDate(String id);
	
	/***
	 * 数据导出查询
	 * @param params 
	 * @return
	 * @date 2016年9月19日 上午11:42:09
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public List<GiveExportVo> findListToExport(Map<String,Object> params);
	
	public void deleteAndhuifu(ActivityGive activityGive) throws  RuntimeException;
}
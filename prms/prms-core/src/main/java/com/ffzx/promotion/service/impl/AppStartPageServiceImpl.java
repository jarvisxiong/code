package com.ffzx.promotion.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.promotion.api.dto.AppStartPage;
import com.ffzx.promotion.mapper.AppStartPageMapper;
import com.ffzx.promotion.service.AppStartPageService;

/**
 * @className AppStartPageServiceImpl
 *
 * @author liujunjun
 * @date 2016-09-09 14:43:37
 * @version 1.0.0
 */
@Service("appStartPageService")
public class AppStartPageServiceImpl extends BaseCrudServiceImpl implements AppStartPageService {

	@Resource
	private AppStartPageMapper appStartPageMapper;
	
	@Override
	public CrudMapper init() {
		return appStartPageMapper;
	}

	@Override
	public List<AppStartPage> findList(Page pageObj, AppStartPage appStartPage) throws ServiceException {
		// 过滤条件
		Map<String, Object> params = new HashMap<String, Object>();
		if (appStartPage != null) {
			//筛选添加终端
			if(StringUtil.isNotNull(appStartPage.getTerminal())){
				params.put("terminal", appStartPage.getTerminal());
			}
			//筛选添加状态
			if(StringUtil.isNotNull(appStartPage.getStatus())){
				params.put("status", appStartPage.getStatus());
				params.put("nowDate", new Date());
			}
			//筛选添加生效开始时间
			if(StringUtil.isNotNull(appStartPage.getEffectiveStartDate())){
				params.put("effectiveStartDate", appStartPage.getEffectiveStartDate());
			}
			//筛选添加生效结束时间
			if(StringUtil.isNotNull(appStartPage.getEffectiveEndDate())){
				params.put("effectiveEndDate", appStartPage.getEffectiveEndDate());
			}
			//筛选添加过期开始时间
			if(StringUtil.isNotNull(appStartPage.getExpiredStartDate())){
				params.put("expiredStartDate", appStartPage.getExpiredStartDate());
			}
			//筛选添加过期结束时间
			if(StringUtil.isNotNull(appStartPage.getExpiredEndDate())){
				params.put("expiredEndDate", appStartPage.getExpiredEndDate());
			}
			//列表只查询出删除标志为正常的数据
			params.put("delFlag",Constant.DELTE_FLAG_NO); 
			
		}
		
		return appStartPageMapper.selectByPage(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, params);
	}

	@Override
	public List<AppStartPage> findListByDate(Map<String, Object> params) throws ServiceException {
		
		return appStartPageMapper.selectByDate(params);
	}

	@Override
	public int updateBySelective(AppStartPage appStartPage) throws ServiceException {
		// TODO Auto-generated method stub
		return appStartPageMapper.updateBySelective(appStartPage);
	}	
}
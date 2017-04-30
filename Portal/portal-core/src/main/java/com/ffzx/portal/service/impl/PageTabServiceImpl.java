package com.ffzx.portal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.portal.mapper.PageTabMapper;
import com.ffzx.portal.model.PageTab;
import com.ffzx.portal.service.PageTabService;

/**
 * @className PageTabServiceImpl
 *
 * @author liujunjun
 * @date 2017-04-12 11:38:46
 * @version 1.0.0
 */
@Service("pageTabService")
public class PageTabServiceImpl extends BaseCrudServiceImpl implements PageTabService {

	@Resource
	private PageTabMapper pageTabMapper;
	
	@Override
	public CrudMapper init() {
		return pageTabMapper;
	}

	/* (non-Javadoc)
	 * @see com.ffzx.portal.service.PageTabService#getPageTabJson()
	 */
	@Override
	public JSONArray getPageTabJson() {
		List<PageTab> list = pageTabMapper.selectList();
		JSONArray jsonArray = JSONArray.parseArray(JSONObject.toJSONString(list));
		return jsonArray;
	}	
}
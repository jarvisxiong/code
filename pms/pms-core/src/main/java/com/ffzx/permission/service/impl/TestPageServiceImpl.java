package com.ffzx.permission.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.FastJsonUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.permission.mapper.TestPageMapper;
import com.ffzx.permission.model.TestPage;
import com.ffzx.permission.service.TestPageService;

/**
 * 
 * @author Generator
 * @date 2016-02-22 11:39:07
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("testPageService")
public class TestPageServiceImpl extends BaseCrudServiceImpl implements TestPageService {

    @Resource
    private TestPageMapper testPageMapper;
    
    @Override
    public CrudMapper init() {
        return testPageMapper;
    }

    @Transactional
	public ServiceCode save(TestPage testPage) throws ServiceException {
		int resultInt = 0;
		if(StringUtil.isEmpty(testPage.getId())){
			Date createDate = new Date();
			testPage.setCreateDate(createDate);
			testPage.setLastUpdateDate(createDate);
			testPage.setId(UUIDGenerator.getUUID());
			resultInt = testPageMapper.insertSelective(testPage);
		}else{
			testPage.setLastUpdateDate(new Date());
			resultInt = testPageMapper.updateByPrimaryKeySelective(testPage);
		}
		return resultInt > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	public List<TestPage> findList(Page page, String orderByField, String orderBy, TestPage testPage) throws ServiceException {

	    //添加查询徐条件
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
	    params.put("name", testPage.getName());
		params.put("beginAge", testPage.getBeginAge());
		params.put("endAge", testPage.getEndAge());
		params.put("email", testPage.getEmail());
		if(null != testPage.getCreateBy() && StringUtil.isNotNull(testPage.getCreateBy().getName())){
			params.put("createBy", testPage.getCreateBy());
		}
		if(null != testPage.getLastUpdateBy() && StringUtil.isNotNull(testPage.getLastUpdateBy().getName())){
			params.put("lastUpdateBy", testPage.getLastUpdateBy());
		}
		
		params.put("lastUpdateDateBegin", testPage.getBeginLastUpdateDateStr());
		if(StringUtil.isNotNull(testPage.getEndLastUpdateDateStr())){
			params.put("lastUpdateDateEnd", testPage.getEndLastUpdateDateStr() + " 23:59:59");
		}

		params.put("createDateBegin", testPage.getBeginCreateDateStr());
		if(StringUtil.isNotNull(testPage.getEndCreateDateStr())){
			params.put("createDateEnd", testPage.getEndCreateDateStr() + " 23:59:59");
		}
		
		//数据范围过虑
		params.put("dsf", dataScopeFilter(RedisWebUtils.getLoginUser(), "of", "uc"));
		
		return this.findByPage(page, orderByField, orderBy, params);
	}
    
    
}
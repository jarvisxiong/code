package com.ffzx.permission.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.permission.mapper.SysCityMapper;
import com.ffzx.permission.model.SysCity;
import com.ffzx.permission.service.SysCityService;

/**
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("sysCityService")
public class SysCityServiceImpl extends BaseCrudServiceImpl implements SysCityService {

	@Autowired
    private SysCityMapper sysCityMapper;

    public CrudMapper init() {
        return sysCityMapper;
    }
    
    public List<SysCity> selectAllCityByUserId(String userId) throws ServiceException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		return sysCityMapper.selectAllCityByUserId(params);
	}

}
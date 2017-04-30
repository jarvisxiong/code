package com.ffzx.permission.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.permission.mapper.SysUserCityMapper;
import com.ffzx.permission.model.SysUserCity;
import com.ffzx.permission.service.SysUserCityService;

/**
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("sysUserCityService")
public class SysUserCityServiceImpl extends BaseCrudServiceImpl implements SysUserCityService {

	@Autowired
    private SysUserCityMapper sysUserCityMapper;

    public CrudMapper init() {
        return sysUserCityMapper;
    }

    @Transactional(rollbackFor=Exception.class)
	public void authorizationUserCity(String userId, List<String> cityIds) throws ServiceException {
		sysUserCityMapper.deleteUserAllControlCityList(userId);
		
		if (cityIds != null && cityIds.size() > 0) {
			List<SysUserCity> userCityList = new ArrayList<SysUserCity>();
			for (int i = 0; i < cityIds.size(); i++) {  
				SysUserCity userCity = new SysUserCity();
				userCity.setId(UUIDGenerator.getUUID());
				userCity.setCityId(cityIds.get(i));
				userCity.setUserId(userId);
				userCityList.add(userCity);
			}
			sysUserCityMapper.insertUserControlCity(userCityList);
		}
	}
}
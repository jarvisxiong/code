package com.ffzx.permission.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.permission.mapper.SysUserRoleMapper;
import com.ffzx.permission.model.SysUserRole;
import com.ffzx.permission.service.SysUserRoleService;

/**
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends BaseCrudServiceImpl implements SysUserRoleService {

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	public CrudMapper init() {
		return sysUserRoleMapper;
	}

	@Transactional(rollbackFor=Exception.class)
	public void authorizationUserRole(String userId, String roleIdStr) throws ServiceException {

		String[] roleIdsArr = new String[0];
		if(StringUtil.isNotNull(roleIdStr)) {
			roleIdsArr = roleIdStr.split(",");
			List<String> roleIds = Arrays.asList(roleIdsArr);
			sysUserRoleMapper.deleteUserAllControlRoleList(userId);
			
			if(roleIds != null && roleIds.size() > 0) {
				List<SysUserRole> userRoleList = new ArrayList<SysUserRole>();
				for (int i = 0; i < roleIds.size(); i++) {  
					SysUserRole userRole = new SysUserRole();
					userRole.setId(UUIDGenerator.getUUID());
					userRole.setRoleId(roleIds.get(i));
					userRole.setUserId(userId);
					userRoleList.add(userRole);
				}
				sysUserRoleMapper.insertUserControlRole(userRoleList);
			}
		}else{
			sysUserRoleMapper.deleteUserAllControlRoleList(userId);
			
		}
	}
	
}
package com.ffzx.permission.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.permission.mapper.SysUserGroupRoleMapper;
import com.ffzx.permission.model.SysUserGroupRole;
import com.ffzx.permission.service.SysUserGroupRoleService;

/**
 * 
 * @author Generator
 * @date 2016-03-05 10:09:55
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("sysUserGroupRoleService")
public class SysUserGroupRoleServiceImpl extends BaseCrudServiceImpl implements SysUserGroupRoleService {

    @Resource
    private SysUserGroupRoleMapper sysUserGroupRoleMapper;

    @Override
    public CrudMapper init() {
        return sysUserGroupRoleMapper;
    }

    @Transactional(rollbackFor=Exception.class)
	public void authorizationUserGroupRole(String id, String roleIdStr) throws ServiceException {
		String[] roleIdsArr = new String[0];
		if(StringUtil.isNotNull(roleIdStr)) {
			roleIdsArr = roleIdStr.split(",");
		
			List<String> roleIds = Arrays.asList(roleIdsArr);
			
			sysUserGroupRoleMapper.deleteUserGroupAllControlRoleList(id);
			
			if(roleIds != null && roleIds.size() > 0) {
				List<SysUserGroupRole> userRoleList = new ArrayList<SysUserGroupRole>();
				for (int i = 0; i < roleIds.size(); i++) {  
					SysUserGroupRole userRole = new SysUserGroupRole();
					userRole.setId(UUIDGenerator.getUUID());
					userRole.setRoleId(roleIds.get(i));
					userRole.setUserGroupId(id);
					userRoleList.add(userRole);
				}
				sysUserGroupRoleMapper.insertUserGroupControlRole(userRoleList);
			}	
		}else{
			sysUserGroupRoleMapper.deleteUserGroupAllControlRoleList(id);
		}
	}
}
package com.ffzx.permission.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.permission.mapper.SysUserGroupUserMapper;
import com.ffzx.permission.model.SysUserGroupUser;
import com.ffzx.permission.service.SysUserGroupUserService;

/**
 * 
 * @author Generator
 * @date 2016-03-05 10:09:55
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("sysUserGroupUserService")
public class SysUserGroupUserServiceImpl extends BaseCrudServiceImpl implements SysUserGroupUserService {

    @Resource
    private SysUserGroupUserMapper sysUserGroupUserMapper;

    @Override
    public CrudMapper init() {
        return sysUserGroupUserMapper;
    }

    @Transactional(rollbackFor=Exception.class)
	public void authorizationUserGroupUser(String id, String roleIdStr) throws ServiceException {
		String[] userIdsArr = new String[0];
		if(StringUtil.isNotNull(roleIdStr)) {
			userIdsArr = roleIdStr.split(",");
		
			List<String> roleIds = Arrays.asList(userIdsArr);
			sysUserGroupUserMapper.deleteUserGroupAllControlUserList(id);
			
			if(roleIds != null && roleIds.size() > 0) {
				List<SysUserGroupUser> userGroupUserList = new ArrayList<SysUserGroupUser>();
				for (int i = 0; i < roleIds.size(); i++) {  
					SysUserGroupUser UserGroupUser = new SysUserGroupUser();
					UserGroupUser.setId(UUIDGenerator.getUUID());
					UserGroupUser.setUserId(roleIds.get(i));
					UserGroupUser.setUserGroupId(id);
					userGroupUserList.add(UserGroupUser);
				}
				sysUserGroupUserMapper.insertUserGroupControlUser(userGroupUserList);
			}
		}else{
			sysUserGroupUserMapper.deleteUserGroupAllControlUserList(id);
		}
	}

    @Transactional(rollbackFor=Exception.class)
	public void authorizationUserGroupUserByUserId(String id, String roleIdStr) throws ServiceException {
		String[] userIdsArr = new String[0];
		if(StringUtil.isNotNull(roleIdStr)) {
			userIdsArr = roleIdStr.split(",");
		
			List<String> roleIds = Arrays.asList(userIdsArr);
			sysUserGroupUserMapper.deleteUserGroupAllControlUserListByUserId(id);
			
			if(roleIds != null && roleIds.size() > 0) {
				List<SysUserGroupUser> userGroupUserList = new ArrayList<SysUserGroupUser>();
				for (int i = 0; i < roleIds.size(); i++) {  
					SysUserGroupUser UserGroupUser = new SysUserGroupUser();
					UserGroupUser.setId(UUIDGenerator.getUUID());
					UserGroupUser.setUserId(id);
					UserGroupUser.setUserGroupId(roleIds.get(i));
					userGroupUserList.add(UserGroupUser);
				}
				sysUserGroupUserMapper.insertUserGroupControlUser(userGroupUserList);
			}
		}else{
			sysUserGroupUserMapper.deleteUserGroupAllControlUserListByUserId(id);
		}
	}

    @Transactional(rollbackFor=Exception.class)
	public void assignUserAdd(String id, String roleIdStr) throws ServiceException {
		String[] userIdsArr = new String[0];
		if(StringUtil.isNotNull(roleIdStr)) {
			userIdsArr = roleIdStr.split(",");		
			List<String> roleIds = Arrays.asList(userIdsArr);

			if(roleIds != null && roleIds.size() > 0) {
				for (int i = 0; i < roleIds.size(); i++) {  
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("userId", roleIds.get(i));
					params.put("userGroupId", id);
					List<SysUserGroupUser> lists = sysUserGroupUserMapper.selectByParams(params);
					if(!StringUtil.isNotNull(lists) || lists.size() <= 0){
						SysUserGroupUser UserGroupUser = new SysUserGroupUser();
						UserGroupUser.setId(UUIDGenerator.getUUID());
						UserGroupUser.setUserId(roleIds.get(i));
						UserGroupUser.setUserGroupId(id);
						sysUserGroupUserMapper.insert(UserGroupUser);
					}
				}
			}
		}
	}
    
    @Transactional(rollbackFor=Exception.class)
	public void assignUserRemove(String id, String roleIdStr) throws ServiceException {
		String[] userIdsArr = new String[0];
		if(StringUtil.isNotNull(roleIdStr)) {
			userIdsArr = roleIdStr.split(",");		
			List<String> roleIds = Arrays.asList(userIdsArr);
			if(roleIds != null && roleIds.size() > 0) {
				for (int i = 0; i < roleIds.size(); i++) {  
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("userId", roleIds.get(i));
					params.put("userGroupId", id);
					List<SysUserGroupUser> lists = sysUserGroupUserMapper.selectByParams(params);
					if(StringUtil.isNotNull(lists) && lists.size() > 0){
						sysUserGroupUserMapper.deleteByPrimarayKeyForModel(lists.get(0));
					}
				}
			}			
		}
	}
}
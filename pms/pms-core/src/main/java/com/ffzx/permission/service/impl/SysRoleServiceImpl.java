package com.ffzx.permission.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.RoleDataScope;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.system.entity.SysOffice;
import com.ffzx.commerce.framework.system.entity.SysRole;
import com.ffzx.commerce.framework.utils.BeanMapUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.permission.mapper.SysRoleMapper;
import com.ffzx.permission.model.SysRoleMenu;
import com.ffzx.permission.model.SysRoleOffice;
import com.ffzx.permission.model.SysUserGroupRole;
import com.ffzx.permission.model.SysUserRole;
import com.ffzx.permission.service.SysRoleService;

/**
 * 
 * @author Generator
 * @date 2016-03-03 18:00:10
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseCrudServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public CrudMapper init() {
        return sysRoleMapper;
    }

	public List<SysRole> findList(Page page, String orderByField, String orderBy, SysRole sysRole) throws ServiceException {
		
		//添加查询徐条件
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("name", sysRole.getName());
	    params.put("delFlag", SysRole.DEL_FLAG_NORMAL);
	    params.put("dataScope", sysRole.getDataScope());
	    params.put("useable", sysRole.getUseable());
	    
	    return this.findByPage(page, orderByField, orderBy, params);
	}

	@Transactional(rollbackFor=Exception.class)
	public ServiceCode save(SysRole sysRole, String roleMenuIds, String roleOfficeIds) throws ServiceException {
		int resultInt = 0;
		if(StringUtil.isEmpty(sysRole.getId())){
			Date createDate = new Date();
			sysRole.setId(UUIDGenerator.getUUID());
			sysRole.setCreateDate(createDate);
			sysRole.setLastUpdateDate(createDate);
			resultInt = sysRoleMapper.insertSelective(sysRole);
		}else{
			sysRole.setLastUpdateDate(new Date());
			resultInt = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
			
			//删除所有角色菜单（删除角色权限）
			sysRoleMapper.delAllRoleMenu(sysRole.getId());
			
			//删除对应的角色机构
			sysRoleMapper.delAllRoleOffice(sysRole.getId());
		}
		
		//添加角色菜单（角色授权）
		if(StringUtil.isNotNull(roleMenuIds)){
			List<SysRoleMenu> roleMenuList = new ArrayList<SysRoleMenu>();
			String[] ids = roleMenuIds.split("_");
			for (String menuId : ids) {
				roleMenuList.add(new SysRoleMenu(UUIDGenerator.getUUID(), sysRole.getId(), menuId));
			}
			sysRoleMapper.batchInsertRoleMenu(roleMenuList);
		}
		
		//添加角色机构
		if(StringUtil.isNotNull(roleOfficeIds) && RoleDataScope.DATA_SCOPE_CUSTOM.getValue().equals(sysRole.getDataScope())){
			List<SysRoleOffice> roleOfficeList = new ArrayList<SysRoleOffice>();
			String[] ids = roleOfficeIds.split("_");
			for (String officeId : ids) {
				roleOfficeList.add(new SysRoleOffice(UUIDGenerator.getUUID(), sysRole.getId(), officeId));
			}
			sysRoleMapper.batchInsertRoleOffice(roleOfficeList);
		}
		
		return resultInt > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Transactional
	public ServiceCode delete(SysRole sysRole) throws ServiceException {
		sysRole.setLastUpdateDate(new Date());
		sysRole.setDelFlag(SysRole.DEL_FLAG_DELETE);
		int resultInt = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
		return resultInt > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	public List<SysRole> selectAllRoleByUserId(String userId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return sysRoleMapper.selectAllRoleByUserId(params);
	}

	public List<SysRole> selectAllRoleByUserGroupId(String userGroupId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userGroupId", userGroupId);
		return sysRoleMapper.selectAllRoleByUserGroupId(params);
	}

	@Transactional
	public ServiceCode deleteUser(String roleId, String userId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("userId", userId);
		int resultInt = sysRoleMapper.deleteRoleUser(params);
		return resultInt > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Transactional(rollbackFor=Exception.class)
	public ServiceCode saveUser(String roleId, String[] userIds) throws ServiceException {
		
		if(null == userIds || userIds.length < 1){
			return ServiceResultCode.SUCCESS;
		}
		
		List<SysUserRole> userList = new ArrayList<SysUserRole>();
		for(String userId : userIds){
			userList.add(new SysUserRole(UUIDGenerator.getUUID(), userId, roleId));
		}
		
		sysRoleMapper.delAllRoleUser(roleId);
		int resultInt = sysRoleMapper.batchInsertRoleUser(userList);
		
		return resultInt > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Transactional
	public ServiceCode deleteUserGroup(String roleId, String userGroupId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("userGroupId", userGroupId);
		int resultInt = sysRoleMapper.deleteRoleUserGroup(params);
		return resultInt > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Transactional(rollbackFor=Exception.class)
	public ServiceCode saveUserGroup(String roleId, String userGroupIds) throws ServiceException {
		
		if(StringUtil.isNotNull(roleId)){
			sysRoleMapper.delAllRoleUserGroup(roleId);
		}else{
			return ServiceResultCode.FAIL;
		}
		
		String[] groupIdArray = null;
		if(StringUtil.isNotNull(userGroupIds)){
			groupIdArray = userGroupIds.split(",");
		}
		
		List<SysUserGroupRole> saveList = new ArrayList<SysUserGroupRole>();
		if(groupIdArray!=null){
			for(String groupId : groupIdArray){
				saveList.add(new SysUserGroupRole(UUIDGenerator.getUUID(), groupId, roleId));
			}
		}
		
		int resultInt = sysRoleMapper.batchInsertRoleUserGroup(saveList);
		return resultInt > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	public List<SysRole> selectAllRole(String userId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return sysRoleMapper.selectAllRole(params);
	}

	@Override
	public List<Object> selectAllOfficeByRoleId(String roleId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
		List<SysOffice> office = sysRoleMapper.selectAllOfficeByRoleId(params);
		List<Object> listResult = new ArrayList<Object>();
		listResult = sysOfficeToMap(office, listResult);
		return listResult;
	}
	
	/**
	 * list<SysOffice>格式转变成List<map>格式
	 * 
	 * @param subOffice
	 *            list
	 * @param listResult
	 * @return
	 */
	public List<Object> sysOfficeToMap(List<SysOffice> subOffice, List<Object> listResult) {
		for (SysOffice office : subOffice) {
			Map<String, Object> row = BeanMapUtils.toMap(office);
			row.put("pId", office.getParentId());
			row.put("open", true);
			row.put("type", office.getType());
			if (office.getArea() != null) {
				row.put("area", office.getArea().getName());
			}
			if (office.getRoleId() != null) {
				row.put("checked", true);
			}
			listResult.add(row);
			if (office.getSub().size() > 0) {
				sysOfficeToMap(office.getSub(), listResult);
			}
		}
		return listResult;
	}
}
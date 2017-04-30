package com.ffzx.permission.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.permission.common.PmsServiceResultCode;
import com.ffzx.permission.mapper.SysUserGroupMapper;
import com.ffzx.permission.model.SysUserGroup;
import com.ffzx.permission.service.SysUserGroupRoleService;
import com.ffzx.permission.service.SysUserGroupService;
import com.ffzx.permission.service.SysUserGroupUserService;

/**
 * 用户组管理Service实现类
 * 
 * @author liujunjun
 * @date 2016年3月5日 上午10:27:30
 * @version 1.0
 */
@Service("sysUserGroupService")
public class SysUserGroupServiceImpl extends BaseCrudServiceImpl implements SysUserGroupService {

    @Resource
    private SysUserGroupMapper sysUserGroupMapper;
    
    @Autowired
    private SysUserGroupRoleService sysUserGroupRoleService;
    @Autowired
    private SysUserGroupUserService sysUserGroupUserService;

    @Override
    public CrudMapper init() {
        return sysUserGroupMapper;
    }

	/**
	 * 保存用户组
	 * @param userGroup
	 * @param userId
	 * @param roleIds   分配的角色  允许为null
	 * @param userIds   分配的用户  允许为null
	 * @return
	 * @throws ServiceException
	 */
    @Transactional(rollbackFor=Exception.class)
	public ServiceCode save(SysUserGroup userGroup, String userId, String roleIds, String userIds) throws ServiceException {
		int result = 0;

		//参数唯一性验证
		ServiceCode serviceCode = validationParam(userGroup);
		if(StringUtil.isNotNull(serviceCode)){
			return serviceCode;
		}
		
		String id = userGroup.getId();
		//设置parentIds
		if(!StringUtil.isNotNull(userGroup.getParent().getId())){
			userGroup.getParent().setId("0");
		}
		SysUserGroup parentuserGroup = this.findById(userGroup.getParent().getId());
		String parentIds = "";
		if(StringUtil.isNotNull(parentuserGroup)){
			parentIds = parentuserGroup.getParentIds() + userGroup.getParent().getId() + ",";
		}else{
			parentIds = "0,";
		}
		userGroup.setParentIds(parentIds);
		
		// 有Id为修改
		if (StringUtil.isNotNull(userGroup.getId())) {
			SysUserGroup oldOffice = this.findById(userGroup.getId());// 旧的数据
			
			userGroup.setLastUpdateBy(new SysUser(userId));
			userGroup.setLastUpdateDate(new Date());
			result = sysUserGroupMapper.updateByPrimaryKeySelective(userGroup);
			// 维护所有子集的ids
			SysUserGroup m = new SysUserGroup();
			m.setParentIds("%," + userGroup.getId() + ",%");
			List<SysUserGroup> list = sysUserGroupMapper.getByParentIdsLike(m);
			if(StringUtil.isNotNull(list)){
				for(SysUserGroup ug : list){
					ug.setParentIds(ug.getParentIds().replace(oldOffice.getParentIds(), userGroup.getParentIds()));
					ug.setDelFlag(null);
					sysUserGroupMapper.updateByPrimaryKeySelective(ug);
				}
			}
		} else {
			id = UUIDGenerator.getUUID();
			userGroup.setId(id);
			userGroup.setCreateBy(new SysUser(userId));
			userGroup.setCreateDate(new Date());
			result = sysUserGroupMapper.insertSelective(userGroup);
		}
		
		//分配角色
		if(roleIds != null){
			sysUserGroupRoleService.authorizationUserGroupRole(id, roleIds);
		}
		//分配用户
		if(userIds != null){
			sysUserGroupUserService.authorizationUserGroupUser(id, userIds);
		}
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	/**
	 * 机构treetable返回
	 * 
	 * @param id
	 */
	public List<Object> getOfficeTreeTable(Map<String, Object> params, String id) throws ServiceException {
		List<SysUserGroup> tUserGroup = this.findByBiz(params);
		List<SysUserGroup> listTree = new ArrayList<SysUserGroup>();
		List<Object> treeTable = new ArrayList<Object>();
		if (id != null) {
			listTree = listToTree(tUserGroup);
			List<SysUserGroup> subUserGroup = new ArrayList<SysUserGroup>();
			subUserGroup = getSuboffice(listTree, id, subUserGroup);
			subUserGroup = modifyParentId(subUserGroup);
			treeTable = userGroupToMap(subUserGroup, treeTable);
		} else {
			listTree = listToTree(tUserGroup);
			treeTable = userGroupToMap(listTree, treeTable);
		}
		return treeTable;
	}

	/**
	 * 列表转树形
	 * 
	 * @param list
	 */
	public List<SysUserGroup> listToTree(List<SysUserGroup> list) {
		List<SysUserGroup> rootTrees = new ArrayList<SysUserGroup>();
		for (SysUserGroup object1 : list) {
			if (object1.getParentId().equals("0")) {
				rootTrees.add(object1);
			}
			for (SysUserGroup object2 : list) {
				if (object2.getParentId().equals(object1.getId())) {
					if (object1.getSub().size() == 0) {
						List<SysUserGroup> myChildrens = new ArrayList<SysUserGroup>();
						myChildrens.add(object2);
						object1.setSub(myChildrens);
					} else {
						object1.getSub().add(object2);
					}
				}
			}
		}
		return rootTrees;
	}

	/**
	 * 获取子树形 从属性中截取id下的所有子树形
	 * 
	 * @param list
	 * @param officeId
	 * @param subOffice
	 * @return
	 */
	public List<SysUserGroup> getSuboffice(List<SysUserGroup> list, String id, List<SysUserGroup> subUserGroup) {
		for (SysUserGroup userGroup : list) {
			if (userGroup.getId().equals(id)) {
				subUserGroup = userGroup.getSub();
				break;
			}
			if (subUserGroup.size() > 0) {
				break;
			}
			if (userGroup.getSub().size() > 0) {
				subUserGroup = getSuboffice(userGroup.getSub(), id, subUserGroup);
			}
		}
		return subUserGroup;
	}

	/**
	 * 顶级机构手动添加父节点为'0',前段treetable必须顶级机构从0开始
	 * 
	 * @param list
	 * @return
	 */
	public List<SysUserGroup> modifyParentId(List<SysUserGroup> list) {
		List<SysUserGroup> res = new ArrayList<SysUserGroup>();
		for (SysUserGroup menu : list) {
			menu.setParent(new SysUserGroup("0"));
			res.add(menu);
		}
		return res;
	}
	
	/**
	 * 获取所有机构
	 */
	public List<Object> getUserGroupList(Map<String, Object> params) throws ServiceException {
		List<SysUserGroup> tUserGroup = this.findByBiz(params);
		List<Object> listResult = new ArrayList<Object>();
		listResult = userGroupToMap(tUserGroup, listResult);
		return listResult;
	}
		
	/**
	 * list<SysOffice>格式转变成List<map>格式
	 * @param subUserGroup
	 *            list
	 * @param listResult
	 * @return
	 */
	public List<Object> userGroupToMap(List<SysUserGroup> subUserGroup, List<Object> listResult) {
		for (SysUserGroup userGroup : subUserGroup) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", userGroup.getId());
			row.put("name", userGroup.getName());
			row.put("pId", userGroup.getParentId());
			row.put("open", true);
			row.put("code", userGroup.getCode());
			row.put("type", userGroup.getType());
			listResult.add(row);
			if (StringUtil.isNotNull(userGroup.getSub()) && userGroup.getSub().size() > 0) {
				userGroupToMap(userGroup.getSub(), listResult);
			}
		}
		return listResult;
	}

	public ServiceCode delete(SysUserGroup userGroup, String userId) throws ServiceException {

		//判定是否有子节点
		Map<String, Object> params = new HashMap<String, Object>();
		List<Object> treeTable = this.getOfficeTreeTable(params,userGroup.getId());
		if(StringUtil.isNotNull(treeTable) && treeTable.size() > 0){
			return PmsServiceResultCode.PMS_USERGROUP_10011;
		}

		SysUserGroup delUserGroup = new SysUserGroup(userGroup.getId());
		delUserGroup.setDelFlag(Constant.DELTE_FLAG_YES);
		int result = sysUserGroupMapper.updateByPrimaryKeySelective(delUserGroup);
		
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
		//ServiceCode serviceCode = this.save(userGroup, userId, null,null);
		
		//return serviceCode;
	}

	public List<SysUserGroup> getUserGroupListByRole(String roleId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		List<SysUserGroup> userGroupList = sysUserGroupMapper.getUserGroupListByRole(params);
		return userGroupList;
	}

	/**
	 * 新增或修改的参数验证唯一性
	 * @param userGroup
	 * @return
	 */
	public ServiceCode validationParam(SysUserGroup userGroup){
		if(StringUtil.isNotNull(userGroup.getId())){   //修改参数验证
			SysUserGroup olduserGroup = sysUserGroupMapper.selectByPrimaryKey(userGroup.getId());
			if(userGroup.getName()!= null 
					&& !userGroup.getName().equals(olduserGroup.getName())
					&& validationName(userGroup.getName()) > 0){
				return PmsServiceResultCode.PMS_USERGROUP_10013;
			}else if(userGroup.getCode()!= null 
					&& !userGroup.getCode().equals(olduserGroup.getCode())
					&& validationCode(userGroup.getCode()) > 0){
				return PmsServiceResultCode.PMS_USERGROUP_10014;
			}
		}else{		
			if(userGroup.getName()!= null 
					&& validationName(userGroup.getName()) > 0){
				return PmsServiceResultCode.PMS_USERGROUP_10013;
			}else if(userGroup.getCode()!= null 
					&& validationCode(userGroup.getCode()) > 0){
				return PmsServiceResultCode.PMS_USERGROUP_10014;
			}
		}
		return null;
	}
	
	/**
	 * 名称的唯一性验证
	 * @param name
	 * @return int > 0已存在 
	 */
	public int validationName(String name){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);		
		List<SysUser> userList = sysUserGroupMapper.selectByPage(new Page(), null, null, params);
		if(StringUtil.isNotNull(userList)){
			return userList.size();
		}
		return 0;
	}

	/**
	 * 编码的唯一性验证
	 * @param code
	 * @return int > 0已存在 
	 */
	public int validationCode(String code){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", code);		
		List<SysUser> userList = sysUserGroupMapper.selectByPage(new Page(), null, null, params);
		if(StringUtil.isNotNull(userList)){
			return userList.size();
		}
		return 0;
	}
	
	public List<SysUserGroup> selectAllUserByUserGroupId(String userId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return sysUserGroupMapper.selectAllUserGroupByUserId(params);
	}

	public List<Object> getAllUserGroupListByRole(String roleId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		List<SysUserGroup> userGroupList = sysUserGroupMapper.getAllUserGroupListByRole(params);
		
		List<Object> listResult = new ArrayList<Object>();
		if(null != userGroupList && userGroupList.size() > 0){
			for (SysUserGroup userGroup : userGroupList){
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("id", userGroup.getId());
				row.put("name", userGroup.getName());
				row.put("open", true);
				if(StringUtil.isNotNull(userGroup.getParent())){
					row.put("pId", userGroup.getParent().getId());
				}
				if(null != roleId && roleId.equals(userGroup.getRoleId())){
					row.put("checked", true);
				}
				listResult.add(row);
			}
		}
		
		return listResult;
	}
}
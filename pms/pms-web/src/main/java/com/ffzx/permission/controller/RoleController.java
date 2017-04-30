package com.ffzx.permission.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.commerce.framework.annotation.Permission;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.enums.RoleDataScope;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.system.entity.SysOffice;
import com.ffzx.commerce.framework.system.entity.SysRole;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.CollectionsUtils;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.permission.api.service.consumer.OfficeApiConsumer;
import com.ffzx.permission.model.SysUserGroup;
import com.ffzx.permission.service.SysMenuService;
import com.ffzx.permission.service.SysRoleService;
import com.ffzx.permission.service.SysUserGroupService;
import com.ffzx.permission.service.SysUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 角色管理
 * 
 * @author liujunjun
 * @date 2016年1月13日 上午10:45:10
 * @version 1.0
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	public SysMenuService sysMenuService;
	@Autowired
	private OfficeApiConsumer officeApiConsumer;
	@Autowired
	public SysUserService sysUserService;
	@Autowired
	private SysUserGroupService sysUserGroupService;

	/**
	 * 角色列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	@Permission(privilege = {"pms:role:view","pms:role:edit","pms:role:delete","pms:role:assign"})
	public String roleList(ModelMap map) {
		
		map.put("dataScopes", RoleDataScope.values());
		
		return "role/role_list";
	}
	@RequestMapping(value = "/roleQueryData")
	@Permission(privilege = {"pms:role:view","pms:role:edit","pms:role:delete","pms:role:assign"})
	@ResponseBody
	public ResultVo roleQueryData(SysRole sysRole, ModelMap map) {
		
		Page pageObj = this.getPageObj();
		List<SysRole> roleList = null;
		
		//查询数据
		roleList = sysRoleService.findList(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, sysRole);
		
		//设置页面显示
	    ResultVo resultVo = new ResultVo();
	    resultVo.setRecordsTotal(pageObj.getTotalCount());
	    resultVo.setInfoData(roleList);
		
		return resultVo;
	}
	
	/**
	 * 角色编辑页面
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/form")
	@Permission(privilege = "pms:role:view")
	public String form(String id, ModelMap map){
		
		List<Object> menuTreeList = null;
		List<Object> officeTreeList = null;
		SysRole sysRole = null;
		
		//查询编辑数据
		try {
			if(StringUtil.isNotEmpty(id)){//编辑
				sysRole = sysRoleService.findById(id);
				
				menuTreeList = sysMenuService.selectAllMenuByRoleId(id);
				officeTreeList = sysRoleService.selectAllOfficeByRoleId(id);
			}else{//新增
				Map<String, Object> menuParams = new HashMap<String, Object>();
				menuParams.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
				menuTreeList = sysMenuService.getMenuList(menuParams);
				
				Map<String, Object> officeParams = new HashMap<String, Object>();
				officeParams.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
				officeTreeList = officeApiConsumer.getObjectList(null);
			}
		} catch (ServiceException e) {
			logger.error("RoleController-form-Exception=》角色管理-编辑页面-Exception", e);
		} catch (Exception e) {
			logger.error("RoleController-form-Exception=》角色管理-编辑页面-Exception", e);
		}
		
		//设置页面显示
		map.put("role", sysRole);
		map.put("dataScopes", RoleDataScope.values());
		map.put("menuTreeList", this.getJsonByObject(menuTreeList));
		map.put("officeTreeList", this.getJsonByObject(officeTreeList));
		
		return "role/role_form";
	}
	
	/**
	 * 编辑或新增角色
	 * @param sysRole
	 * @param result
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	@Permission(privilege = "pms:role:edit")
	public ResultVo save(@Valid SysRole sysRole, BindingResult result, String roleMenuIds, String roleOfficeIds) {
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		
		if(result.hasErrors()) {//验证错误  
			//验证失败统一说明
			resultVo.setStatus(Constant.RESULT_VALID_STR);
			resultVo.setInfoStr(Constant.RESULT_VALID_MSG);
			return resultVo;
        } 
		
		//验证是否已经存在相同的角色名
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
		params.put("name_equal", sysRole.getName());
		List<SysRole> roleList = sysRoleService.findByBiz(params);
		if(null != roleList){
			if((StringUtil.isEmpty(sysRole.getId()) && roleList.size() > 0) || roleList.size() > 1){
				//验证失败统一说明
				resultVo.setStatus(Constant.RESULT_VALID_STR);
				resultVo.setInfoStr("输入数据验证失败(已存在相同的角色名)！");
				return resultVo;
			}
		}
		
		//执行更新
		ServiceCode serviceCode = null;
		sysRole.setCreateBy(RedisWebUtils.getLoginUser());
		sysRole.setLastUpdateBy(RedisWebUtils.getLoginUser());
		try {
			serviceCode = sysRoleService.save(sysRole, roleMenuIds, roleOfficeIds);
		} catch (ServiceException e) {
			logger.error("RoleController-save-Exception=》角色管理-保存-Exception", e);
		} catch (Exception e) {
			logger.error("RoleController-save-Exception=》角色管理-保存-Exception", e);
		}

		resultVo.setResult(serviceCode, "/role/list.do");
		return resultVo;
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@Permission(privilege = "pms:role:delete")
	public ResultVo delete(String id) {
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		ServiceCode serviceCode = null;
		SysRole sysRole = new SysRole();
		sysRole.setId(id);
		sysRole.setCreateBy(RedisWebUtils.getLoginUser());
		
		//执行操作
		try {
			serviceCode = sysRoleService.delete(sysRole);
		} catch (ServiceException e) {
			logger.error("RoleController-delete-Exception=》角色管理-删除角色-Exception", e);
		} catch (Exception e) {
			logger.error("RoleController-delete-Exception=》角色管理-删除角色-Exception", e);
		}
		
		resultVo.setResult(serviceCode, "/role/list.do");
		return resultVo;
	}
	
	/**
	 * 删除角色用户
	 * @param roleId
	 * @param userId
	 * @return
	 */
	@RequestMapping("/deleteUser")
	@ResponseBody
	@Permission(privilege = "pms:role:delete")
	public ResultVo deleteUser(String roleId, String userId) {
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		ServiceCode serviceCode = null;
		
		//执行操作
		try {
			serviceCode = sysRoleService.deleteUser(roleId, userId);
		} catch (ServiceException e) {
			logger.error("RoleController-delete-Exception=》角色管理-删除角色用户-Exception", e);
		} catch (Exception e) {
			logger.error("RoleController-delete-Exception=》角色管理-删除角色用户-Exception", e);
		}
		
		//判断执行结课
		if(serviceCode.getServiceResult().code() == ServiceCode.SUCCESS) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
			resultVo.setUrl("/role/roleUserList.do?id=" + roleId);
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
		}
		
		return resultVo;
	}
	
	/**
	 * 角色用户列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/roleUserList")
	@Permission(privilege = "pms:role:assign")
	public String roleUserList(String id, ModelMap map) {
		
		SysRole sysRole = null;
		List<SysUser> userList = null;
		List<SysUserGroup> userGroupList = null;
		
		//查询数据
		try {
			sysRole = sysRoleService.findById(id);
			userList = sysUserService.getUserListByRole(id);
			userGroupList = sysUserGroupService.getUserGroupListByRole(id);
		} catch (ServiceException e) {
			logger.error("RoleController-roleUserList-Exception=》角色管理-角色用户列表-Exception", e);
		} catch (Exception e) {
			logger.error("RoleController-roleUserList-Exception=》角色管理-角色用户列表-Exception", e);
		}
		
		//设置页面显示
		map.put("role", sysRole);
		map.put("userList", userList);
		map.put("userGroupList", userGroupList);
		
		return "role/role_user_list";
	}
	
	/**
	 * 角色分配 -- 打开角色分配对话框
	 * @param role
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "assignUserToRole")
	@Permission(privilege = "pms:role:assign")
	public String assignUserToRole(SysRole sysRole, ModelMap map) {
		
		List<SysUser> userList = null;
		List<SysOffice> officeList = null;
		String selectIds = null;
		
		//查询数据
		try {
			//查询角色用户
			userList = sysUserService.getUserListByRole(sysRole.getId());
			selectIds = CollectionsUtils.extractToString(userList, "id", ",");
			
			//查询部门
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
			officeList = officeApiConsumer.getOfficeList(null);
		} catch (ServiceException e) {
			logger.error("RoleController-selectUserToRole-Exception=》角色管理-角色分配用户页面 -Exception", e);
		} catch (Exception e) {
			logger.error("RoleController-selectUserToRole-Exception=》角色管理-角色分配用户页面 -Exception", e);
		}
		
		//设置页面显示
		map.put("role", sysRole);
		map.put("userList", userList);
		map.put("selectIds", selectIds);
		map.put("officeList", officeList);
		
		return "role/role_user_form";
	}
	
	/**
	 * 根据部门查询用户
	 * @param officeId
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findUserByOffice")
	@Permission(privilege = "pms:role:assign")
	public List<Map<String, Object>> findUserByOffice(String officeId, HttpServletResponse response) {
		
		//根据部门查询用户
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
		params.put("officeId", officeId);
		List<SysUser> userList = null;
		try {
			userList = sysUserService.findByPage(null, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, params);
		} catch (ServiceException e) {
			logger.error("RoleController-selectUserToRole-Exception=》角色管理-根据部门查询用户 -Exception", e);
		} catch (Exception e) {
			logger.error("RoleController-selectUserToRole-Exception=》角色管理-根据部门查询用户 -Exception", e);
		}
		
		//数据组装
		List<Map<String, Object>> mapList = Lists.newArrayList();
		if(null != userList){
			for (SysUser e : userList) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", 0);
				map.put("name", e.getName());
				mapList.add(map);			
			}
		}
		return mapList;
	}
	
	/**
	 * 保存角色用户
	 */
	@RequestMapping("/saveUser")
	@ResponseBody
	@Permission(privilege = "pms:role:edit")
	public ResultVo saveUser(String roleId, String[] userIds) {
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		
		ServiceCode serviceCode = null;
		try {
			serviceCode = sysRoleService.saveUser(roleId, userIds);
		} catch (ServiceException e) {
			logger.error("RoleController-selectUserToRole-Exception=》角色管理-保存角色用户 -Exception", e);
		} catch (Exception e) {
			logger.error("RoleController-selectUserToRole-Exception=》角色管理-保存角色用户 -Exception", e);
		}
		
		//判断执行结课
		if(serviceCode.getServiceResult().code() == ServiceCode.SUCCESS) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
			resultVo.setUrl("/role/roleUserList.do?id=" + roleId);
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
		}
		
		return resultVo;
	}
	
	/**
	 * 删除角色用户组
	 * @param roleId
	 * @param userGroupId
	 * @return
	 */
	@RequestMapping("/deleteUserGroup")
	@ResponseBody
	@Permission(privilege = "pms:role:delete")
	public ResultVo deleteUserGroup(String roleId, String userGroupId) {
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		ServiceCode serviceCode = null;
		
		//执行操作
		try {
			serviceCode = sysRoleService.deleteUserGroup(roleId, userGroupId);
		} catch (ServiceException e) {
			logger.error("RoleController-delete-Exception=》角色管理-删除角色用户组-Exception", e);
		} catch (Exception e) {
			logger.error("RoleController-delete-Exception=》角色管理-删除角色用户组-Exception", e);
		}
		
		//判断执行结课
		if(serviceCode.getServiceResult().code() == ServiceCode.SUCCESS) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
			resultVo.setUrl("/role/roleUserList.do?id=" + roleId);
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
		}
		
		return resultVo;
	}
	
	/**
	 * 角色分配 -- 打开角色分配对话框
	 * @param role
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "assignUserGroupToRole")
	@Permission(privilege = "pms:role:assign")
	public void assignUserGroupToRole(HttpServletResponse response,SysRole sysRole, ModelMap map) {
		List<Object> treeList = null;
		
		try {
			treeList = sysUserGroupService.getAllUserGroupListByRole(sysRole.getId());
		} catch (ServiceException e) {
			logger.error("RoleController-selectUserToRole-Exception=》角色管理-角色分配用户组数据 -Exception", e);
		} catch (Exception e) {
			logger.error("RoleController-selectUserToRole-Exception=》角色管理-角色分配用户组数据 -Exception", e);
		}
		
		this.responseWrite(response, this.getJsonByObject(treeList));
	}
	
	/**
	 * 保存角色用户组
	 * @param sysRole
	 * @param result
	 * @return
	 */
	@RequestMapping("/saveUserGroup")
	@ResponseBody
	@Permission(privilege = "pms:role:edit")
	public ResultVo saveUserGroup(String roleId, String userGroupIds) {
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		
		ServiceCode serviceCode = null;
		try {
			serviceCode = sysRoleService.saveUserGroup(roleId, userGroupIds);
		} catch (ServiceException e) {
			logger.error("RoleController-selectUserToRole-Exception=》角色管理-保存角色用户组 -Exception", e);
		} catch (Exception e) {
			logger.error("RoleController-selectUserToRole-Exception=》角色管理-保存角色用户组 -Exception", e);
		}
		
		//判断执行结课
		if(serviceCode.getServiceResult().code() == ServiceCode.SUCCESS) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
			resultVo.setUrl("/role/roleUserList.do?id=" + roleId);
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
		}
		
		return resultVo;
	}
	
}

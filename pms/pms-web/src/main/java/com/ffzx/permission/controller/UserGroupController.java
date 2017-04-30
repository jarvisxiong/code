package com.ffzx.permission.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.commerce.framework.annotation.Permission;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.system.entity.SysRole;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.permission.api.service.consumer.DictApiConsumer;
import com.ffzx.permission.api.service.consumer.OfficeApiConsumer;
import com.ffzx.permission.common.PmsServiceResultCode;
import com.ffzx.permission.model.SysUserGroup;
import com.ffzx.permission.service.SysRoleService;
import com.ffzx.permission.service.SysUserGroupRoleService;
import com.ffzx.permission.service.SysUserGroupService;
import com.ffzx.permission.service.SysUserGroupUserService;
import com.ffzx.permission.service.SysUserService;

/**
 * 用户组管理控制层
 * 
 * @author liujunjun
 * @date 2016年3月5日 上午10:25:11
 * @version 1.0
 */
@Controller
@RequestMapping("/userGroup")
public class UserGroupController extends BaseController {

	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(UserGroupController.class);

	@Autowired
	private SysUserGroupService sysUserGroupService;
	
	@Autowired 
	private SysRoleService sysRoleService;
	
	@Autowired 
	private SysUserService sysUserService;
	
	@Autowired 
	private OfficeApiConsumer officeApiConsumer;

	@Autowired 
	private SysUserGroupRoleService sysUserGroupRoleService;

	@Autowired 
	private SysUserGroupUserService sysUserGroupUserService;
	
	@Autowired
	private DictApiConsumer dictApiConsumer;

	/**
	 * 列表
	 * @param id
	 * @param map
	 * @return
	 */
	@Permission(privilege = {"pms:userGroup:view","pms:userGroup:edit","pms:userGroup:assign","pms:userGroup:delete"})
	@RequestMapping(value = "list")
	public String list(ModelMap map) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String tree_data = "[]";
			// 掩藏的不显示
			List<Object> listResult = sysUserGroupService.getUserGroupList(params);
			tree_data = this.getJsonByObject(listResult);
			map.put("result", tree_data);
			
		} catch (ServiceException e) {
			logger.error("UserGroupController-list-ServiceException=》用户组管理-查看所有用户组-ServiceException", e);
		} catch (Exception e) {
			logger.error("UserGroupController-list-Exception=》用户组管理-查看所有用户组-Exception", e);
		}
		return "userGroup/user_group_list";
	}
	
	/**
	 * 右侧表格
	 * @param map
	 * @return
	 */
	@Permission(privilege = {"pms:userGroup:view","pms:userGroup:edit","pms:userGroup:assign","pms:userGroup:delete"})
	@RequestMapping(value = "tableList")
	public String tableList(ModelMap map, String id) {
		
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			
			// 显示所有
			List<Object> treeTable = sysUserGroupService.getOfficeTreeTable(params,id);
			map.put("treeTable", treeTable);
			map.addAttribute("userGroupTypes",dictApiConsumer.getDictByType("sys_userGroup_type"));
		} catch (ServiceException e) {
			logger.error("UserGroupController-list-ServiceException=》用户组管理-查看所有用户组-ServiceException", e);
		} catch (Exception e) {
			logger.error("UserGroupController-list-Exception=》用户组管理-查看所有用户组-Exception", e);
		}
		
		return "userGroup/user_group_table_list";
	}

	/**
	 * 查看所有用户组
	 * 
	 * @return
	 */
	@Permission(privilege = {"pms:userGroup:view","pms:userGroup:edit","pms:userGroup:assign","pms:userGroup:delete"})
	@RequestMapping(value = "ajaxList")
	@ResponseBody
	public void ajaxList(String parent,HttpServletResponse response) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
		
			if(StringUtil.isNotNull(parent)){
				params.put("parentId", parent);
			}			
			List<Object> listResult = sysUserGroupService.getUserGroupList(params);
			this.responseWrite(response, this.getJsonByObject(listResult));
		} catch (ServiceException e) {
			logger.error("UserGroupController-ajaxList-ServiceException=》用户组管理-查看所有用户组-ServiceException", e);
		} catch (Exception e) {
			logger.error("UserGroupController-ajaxList-Exception=》用户组管理-查看所有用户组-Exception", e);
		}
	}

	/**
	 * 加载编辑页面
	 * 
	 * @return
	 */
	@Permission(privilege = {"pms:userGroup:edit"})
	@RequestMapping(value = "form")
	public String form(String id, String parentId, ModelMap map) {
		try {
			SysUserGroup userGroup = new SysUserGroup();
			if (StringUtil.isNotNull(parentId)) {
				SysUserGroup parentOffice = new SysUserGroup();
				parentOffice.setId(parentId);
				userGroup.setParent(sysUserGroupService.findById(parentOffice));
			}
			if (StringUtil.isNotNull(id)) {
				userGroup.setId(id);
				userGroup = sysUserGroupService.findById(userGroup);
				if("0".equals(userGroup.getParent().getId())){
					userGroup.getParent().setName("顶级用户组");
				}
			}

			map.addAttribute("userGroupTypes",dictApiConsumer.getDictByType("sys_userGroup_type"));

			map.put("roleNodes", this.getRoleNodes(id));
//			map.put("userNodes", this.getUserNodes(id));			
			map.put("userGroup", userGroup);
		} catch (ServiceException e) {
			logger.error("UserGroupController-form-ServiceException=》用户组管理-加载编辑页面-ServiceException", e);
		} catch (Exception e) {
			logger.error("UserGroupController-form-ServiceException=》用户组管理-加载编辑页面-ServiceException", e);
		}
		return "userGroup/user_group_form";
	}

	/**
	 * 保存
	 */
	@Permission(privilege = {"pms:userGroup:edit"})
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo save(@Valid SysUserGroup userGroup, BindingResult result,String roleIds,String userIds, HttpSession session) {
		ResultVo resultVo = new ResultVo();
		if (result.hasErrors()) {
			resultVo.setStatus(Constant.RESULT_VALID);
			resultVo.setInfoMap(this.getValidErrors(result.getFieldErrors()));
			return resultVo;
		}
		String userId = (String) session.getAttribute(Constant.SYSTEM_LOGIN_USER_ID);
		try {

			if(!StringUtil.isNotNull(userGroup.getType())){		//验证用户类型是否选择
				resultVo.setStatus(Constant.RESULT_VALID);
				Map<String, String> errorMap = new LinkedHashMap<String, String>();
				errorMap.put("userType", PmsServiceResultCode.PMS_USERGROUP_10012.getServiceResult().msg());
				resultVo.setInfoMap(errorMap);
				return resultVo;
			}
			
			ServiceCode serviceCode = sysUserGroupService.save(userGroup, userId, roleIds,userIds);
			resultVo.setResult(serviceCode, "/userGroup/tableList.do");
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("UserGroupController-save-ServiceException=》用户组管理-保存用户组", e);
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("UserGroupController-save-Exception=》用户组管理-保存用户组", e);
		}
		if (resultVo.isHasException()) {
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}
	
	/**
	 * 删除--逻辑删除
	 */
	@Permission(privilege = {"pms:userGroup:delete"})
	@RequestMapping(value = "delete")
	@ResponseBody
	public ResultVo delete(String id, HttpSession session){
		logger.debug("UserGroupController-delete=》用户组管理-删除");
		ResultVo resultVo = new ResultVo();
		try {

			SysUserGroup userGroup = sysUserGroupService.findById(id);
			String userId = (String) session.getAttribute(Constant.SYSTEM_LOGIN_USER_ID);
			
			ServiceCode serviceCode = sysUserGroupService.delete(userGroup, userId);
			resultVo.setResult(serviceCode,"/userGroup/tableList.do");			
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("UserGroupController-delete-ServiceException=》用户组管理-删除-ServiceException", e);
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("UserGroupController-delete-Exception=》用户组管理-删除-Exception", e);
		}
		if(resultVo.isHasException()){
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}

	/**
	 * 加载分配角色页面
	 * @return
	 */
	@Permission(privilege = {"pms:userGroup:assign"})
	@RequestMapping("assignRoleToUserGroupForm")
	public void assignRoleToUserGroupForm(HttpServletResponse response, String userGroupId) {
		logger.debug("UserGroupController-assignRoleToUserGroupForm=》用户组管理-加载分配角色页面");
		try {
			this.responseWrite(response, this.getRoleNodes(userGroupId));
		} catch (ServiceException e) {
			logger.error("UserGroupController-assignRoleToUserGroupForm-ServiceException=》用户组管理-加载分配角色页面", e);
		} catch (Exception e) {
			logger.error("UserGroupController-assignRoleToUserGroupForm-Exception=》用户组管理-加载分配角色页面", e);
		}
	}
	
	/**
	 * 获取角色列表，标识已分配用户组
	 * @param userGroupId
	 * @return
	 * @throws ServiceException
	 */
	@Permission(privilege = {"pms:userGroup:assign"})
	public String getRoleNodes(String userGroupId) throws ServiceException{
		List<SysRole> list = sysRoleService.selectAllRoleByUserGroupId(userGroupId);
		List<Object> listResult = new ArrayList<Object>();
		if(list.size() > 0){
			for (SysRole role : list){
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("id", role.getId());
				row.put("name", role.getName());
				row.put("open", true);
				if (StringUtil.isNotNull(role.getUserRoleid())){
					row.put("checked", true);
				}
				listResult.add(row);
			}
		}
		return this.getJsonByObject(listResult);
	}

	/**
	 * 修改用户组的角色
	 * @param userId
	 * @param roleIds
	 * @param response
	 */
	@Permission(privilege = {"pms:userGroup:assign"})
	@RequestMapping(value = "/assignRoleToUserGroup", method = RequestMethod.POST)
	public void assignRoleToUserGroup(HttpSession session,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "roleIds", required = true) String roleIds, HttpServletResponse response) {
		logger.debug("UserGroupController.assignRoleToUserGroup()=》用户组管理- 修改用户组的角色");
		try {
			
			sysUserGroupRoleService.authorizationUserGroupRole(id, roleIds);
			
		} catch (ServiceException e) {
			logger.error("UserGroupController.assignRoleToUserGroup() ServiceException =》用户组管理- 修改用户组的角色" + e);
		} catch (Exception e) {
			logger.error("UserGroupController.assignRoleToUserGroup() Exception=》用户组管理-删除用户组", e);
		}
		this.responseWrite(response, this.buildResponse(PmsServiceResultCode.SUCCESS.getServiceResult()));
	}

	/**
	 * 加载分配用户页面
	 * @return
	 */
	@Permission(privilege = {"pms:userGroup:assign"})
	@RequestMapping("assignUserToUserGroupForm")
	public void assignUserToUserGroupForm(HttpServletResponse response, String userGroupId) {
		logger.debug("UserGroupController-assignUserToUserGroupForm=》用户组管理-加载分配用户");
		try {
			this.responseWrite(response, this.getUserNodes(userGroupId,null));
		} catch (ServiceException e) {
			logger.error("UserGroupController-assignUserToUserGroupForm-ServiceException=》用户组管理-加载分配用户", e);
		} catch (Exception e) {
			logger.error("UserGroupController-assignUserToUserGroupForm-Exception=》用户组管理-加载分配用户", e);
		}
	}

	
	/**
	 * 获取用户列表，标识已分配用户组
	 * @param userGroupId
	 * @return
	 * @throws ServiceException
	 */
	@Permission(privilege = {"pms:userGroup:assign"})
	public String getUserNodes(String userGroupId,String officeId) throws ServiceException{
		List<SysUser> list = sysUserService.selectAllUserByUserGroupId(userGroupId, officeId);
		List<Object> listResult = new ArrayList<Object>();
		if(list.size() > 0){
			for (SysUser role : list){
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("id", role.getId());
				row.put("name", role.getName());
				row.put("open", true);
				if (StringUtil.isNotNull(role.getLoginFlag())){
					row.put("checked", true);
				}
				listResult.add(row);
			}
		}
		return this.getJsonByObject(listResult);
	}
	
	/**
	 * 修改用户组分配的用户
	 * @param id
	 * @param roleIds
	 * @param response
	 */
	@Permission(privilege = {"pms:userGroup:assign"})
	@RequestMapping(value = "/assignUserToUserGroup", method = RequestMethod.POST)
	public void assignUserToUserGroup(HttpSession session,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "roleIds", required = true) String userIds, HttpServletResponse response) {
		logger.debug("UserGroupController-assignUserToUserGroup=》用户组管理- 分配的用户");
		try {
			
			sysUserGroupUserService.authorizationUserGroupUser(id, userIds);
			
		} catch (ServiceException e) {
			logger.error("UserGroupController-assignUserToUserGroup- ServiceException =》用户组管理- 分配的用户" + e);
		} catch (Exception e) {
			logger.error("UserGroupController-assignUserToUserGroup-Exception=》用户组管理-分配的用户", e);
		}
		this.responseWrite(response, this.buildResponse(PmsServiceResultCode.SUCCESS.getServiceResult()));
		
	}	

	/**
	 * 加载分配用户页面
	 * @return
	 */
	@Permission(privilege = {"pms:userGroup:assign"})
	@RequestMapping("assignUserForm")
	public void assignUserForm(HttpServletResponse response, String userGroupId, String officeId) {
		logger.debug("UserGroupController-assignUserToUserGroupForm=》用户组管理-加载分配用户");
		try {
			if(!StringUtil.isNotNull(officeId) || officeId.equals("null")){
				officeId = null;
			}
			//查询所有机构
			List<Object> listResult = officeApiConsumer.getObjectList(null);
			Map result = new HashMap<String, String>();
			result.put("office", this.getJsonByObject(listResult));
			result.put("user", this.getUserNodes(userGroupId,officeId));
			this.responseWrite(response,this.getJsonByObject(result));
			
		} catch (ServiceException e) {
			logger.error("UserGroupController-assignUserToUserGroupForm-ServiceException=》用户组管理-加载分配用户", e);
		} catch (Exception e) {
			logger.error("UserGroupController-assignUserToUserGroupForm-Exception=》用户组管理-加载分配用户", e);
		}
	}
	
	/**
	 * 通过userIdStr新增用户与用户组关系
	 * @return
	 */
	@Permission(privilege = {"pms:userGroup:assign"})
	@RequestMapping("assignUserAdd")
	public void assignUserAdd(HttpSession session, String id, String userIds, HttpServletResponse response) {
		logger.debug("UserGroupController-assignUserAdd=》用户组管理- 添加分配的用户");
		try {
			
			sysUserGroupUserService.assignUserAdd(id, userIds);
			
		} catch (ServiceException e) {
			logger.error("UserGroupController-assignUserAdd- ServiceException =》用户组管理- 添加分配的用户" + e);
		} catch (Exception e) {
			logger.error("UserGroupController-assignUserAdd-Exception=》用户组管理-添加分配的用户", e);
		}
		this.responseWrite(response, this.buildResponse(PmsServiceResultCode.SUCCESS.getServiceResult()));
		
	}

	
	/**
	 * 通过userIdStr删除用户与用户组关系
	 * @return
	 */
	@Permission(privilege = {"pms:userGroup:assign"})
	@RequestMapping("assignUserRemove")
	public void assignUserRemove(HttpSession session, String id, String userIds, HttpServletResponse response) {
		logger.debug("UserGroupController-assignUserRemove=》用户组管理- 删除分配的用户");
		try {
			
			sysUserGroupUserService.assignUserRemove(id, userIds);
			
		} catch (ServiceException e) {
			logger.error("UserGroupController-assignUserRemove- ServiceException =》用户组管理- 删除分配的用户" + e);
		} catch (Exception e) {
			logger.error("UserGroupController-assignUserRemove-Exception=》用户组管理-删除分配的用户", e);
		}
		this.responseWrite(response, this.buildResponse(PmsServiceResultCode.SUCCESS.getServiceResult()));
		
	}
}
package com.ffzx.permission.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
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

import com.ffzx.basedata.api.service.AddressApiService;
import com.ffzx.basedata.api.service.EmployeeApiService;
import com.ffzx.commerce.framework.annotation.Permission;
import com.ffzx.commerce.framework.common.persistence.BaseEntity;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.system.entity.SysOffice;
import com.ffzx.commerce.framework.system.entity.SysRole;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.Md5Utils;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.member.api.dto.Vendor;
import com.ffzx.member.api.service.VendorApiService;
import com.ffzx.permission.api.service.consumer.DictApiConsumer;
import com.ffzx.permission.api.service.consumer.OfficeApiConsumer;
import com.ffzx.permission.common.PmsServiceResultCode;
import com.ffzx.permission.model.SysUserAddress;
import com.ffzx.permission.model.SysUserGroup;
import com.ffzx.permission.model.SysUserOffice;
import com.ffzx.permission.model.SysUserWarehouse;
import com.ffzx.permission.service.SysMenuService;
import com.ffzx.permission.service.SysRoleService;
import com.ffzx.permission.service.SysUserGroupService;
import com.ffzx.permission.service.SysUserGroupUserService;
import com.ffzx.permission.service.SysUserRoleService;
import com.ffzx.permission.service.SysUserService;
import com.ffzx.wms.api.service.WarehouseApiService;

/**
 * 用户管理控制器层
 * 
 * @author liujunjun
 * @date 2016年3月2日 上午9:51:14
 * @version 1.0
 */
@Controller
@RequestMapping("/user/")
public class UserController extends BaseController {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public SysUserService sysUserService;

	@Autowired
	public SysUserRoleService sysUserRoleService;

	@Autowired
	public SysUserGroupService sysUserGroupService;

	@Autowired
	public SysUserGroupUserService sysUserGroupUserService;

	@Autowired
	public SysRoleService sysRoleService;

	@Autowired
	private OfficeApiConsumer officeApiConsumer;

	@Autowired
	private DictApiConsumer dictApiConsumer;

	@Autowired
	private EmployeeApiService empolyeeApi;
	
	@Autowired
	private VendorApiService vendorApiService;
	
	@Autowired
	private AddressApiService addressApiService;
	
	@Autowired
	private WarehouseApiService warehouseApiService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	/**
	 * 修改用户密码
	 * 
	 * @param srcPassWord
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "modifyPassword")
	public void modifyPassword(@RequestParam(value = "srcPassword", required = true) String srcPassword,@RequestParam(value = "newPassword", required = true) String newPassword,HttpServletResponse response) {
		SysUser currentUser = RedisWebUtils.getLoginUser();
		try {
			if(currentUser != null){
				if(!StringUtil.isEmpty(srcPassword)){
					/**
					 * 如果原密码合法则进入修改密码
					 */
					if(Md5Utils.encryption(srcPassword).equals(currentUser.getPassword())){
						  String newPasswordMd5 = Md5Utils.encryption(newPassword);
						  sysUserService.updateUserPassword(currentUser.getLoginName(),newPasswordMd5);
						  /**
						   * 将新密码写回redis
						   */
						  currentUser.setPassword(newPasswordMd5);
						  RedisWebUtils.setLoginUser(currentUser);
						  this.responseWrite(response, "flightHandler("+this.buildResponse(PmsServiceResultCode.SUCCESS.getServiceResult())+")");
					}else{
						  this.responseWrite(response, "flightHandler("+this.buildResponse(PmsServiceResultCode.PMS_USERGROUP_10015.getServiceResult())+")");
					}
				}
			}
		} catch (ServiceException e) {
			logger.error("UserController-modifyPassword-ServiceException=》修改用户密码-ServiceException", e);
		} catch (Exception e) {
			logger.error("UserController-modifyPassword-Exception=》修改用户密码--Exception", e);
		}
	   
	}
	
	/**
	 * 用户列表
	 * 
	 * @param user
	 * @param map
	 * @return
	 */
	@Permission(privilege = { "pms:user:view", "pms:user:edit", "pms:user:assign", "pms:user:delete" })
	@RequestMapping(value = "list")
	public String list(ModelMap map) {
		if (logger.isDebugEnabled()) {
			logger.debug("UserController-list=》用户管理-用户列表 ");
		}
		try {
			// 查询所有机构
			List<Object> listResult = officeApiConsumer.getObjectList(null);
			map.put("result", this.getJsonByObject(listResult));
		} catch (ServiceException e) {
			logger.error("UserController-list-ServiceException=》用户管理-用户列表-ServiceException", e);
		} catch (Exception e) {
			logger.error("UserController-list-Exception=》用户管理-用户列表--Exception", e);
		}
		return "user/user_list";
	}
	
	/**
	 * 右侧表格
	 * @param map
	 * @return
	 */
	@Permission(privilege = { "pms:user:view", "pms:user:edit", "pms:user:assign", "pms:user:delete" })
	@RequestMapping(value = "tableList")
	public String tableList(ModelMap map, String officeId) {
		map.put("officeId", officeId);
		return "user/user_table_list";
	}
	@Permission(privilege = { "pms:user:view", "pms:user:edit", "pms:user:assign", "pms:user:delete" })
	@RequestMapping(value = "tableQueryData")
	@ResponseBody
	public ResultVo tableQueryData(SysUser user, String officeId) {
		Page pageObj = this.getPageObj();
		Map<String, Object> params = new HashMap<String, Object>();

		// 筛选添加名称
		if (StringUtil.isNotNull(officeId)) {
			params.put("officeId", officeId);
		}

		// 筛选添加名称
		if (StringUtil.isNotNull(user.getLoginName())) {
			params.put("loginName", user.getLoginName());
		}
		// 筛选添加登录名
		if (StringUtil.isNotNull(user.getName())) {
			params.put("name", user.getName());
		}

		// 筛选条件用户类型
		if (StringUtil.isNotNull(user.getUserType())) {
			params.put("userType", user.getUserType());
		}

		// 筛选条件是否禁用
		if (StringUtil.isNotNull(user.getLoginFlag())) {
			params.put("loginFlag", user.getLoginFlag());
		}

		List<SysUser> userList = sysUserService.findByPage(pageObj, null, null, params);
		
		//设置页面显示
	    ResultVo resultVo = new ResultVo();
	    resultVo.setRecordsTotal(pageObj.getTotalCount());
	    resultVo.setInfoData(userList);
	    
		return resultVo;
	}

	/**
	 * 加载添加页面
	 */
	@Permission(privilege = { "pms:user:edit" })
	@RequestMapping(value = "form")
	public String form(String id, ModelMap map) {
		if (logger.isDebugEnabled()) {
			logger.debug("UserController-form=》用户管理-加载添加页面");
		}
		List<Object> menuTreeList = null;
		try {
			if (StringUtil.isNotNull(id)) {
				SysUser user = sysUserService.findById(id);
				
				if(null != user && StringUtil.isNotNull(user.getVendorId())){
					Vendor vendor = vendorApiService.getVendorById(user.getVendorId());
					map.addAttribute("vendor", vendor);
				}
				
				map.addAttribute("user", user);
				
				menuTreeList = sysMenuService.selectAllMenuByUserId(id);
			}else {
				//添加
				Map<String, Object> menuParams = new HashMap<String, Object>();
				menuParams.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
				menuTreeList = sysMenuService.getMenuList(menuParams);
			}
			
			map.addAttribute("userTypes", dictApiConsumer.getDictByType("sys_user_type"));
			map.addAttribute("roleNodes", this.getRoleNodes(id));
			map.addAttribute("userGroupNodes", this.getUserGroupNodes(id));
			map.addAttribute("OfficeNodes", this.getOfficeNodes(id));
			map.addAttribute("addressNodes", this.getAddressNodes(id));
			map.addAttribute("warehouseNodes", this.getWarehouseNodes(id));
			map.addAttribute("menuTreeList", this.getJsonByObject(menuTreeList));
		} catch (ServiceException e) {
			logger.error("UserController-form-ServiceException=》用户管理-加载添加页面-ServiceException", e);
		} catch (Exception e) {
			logger.error("UserController-form-Exception=》用户管理-加载添加页面-Exception", e);
		}
		return "user/user_form";
	}

	/**
	 * 员工姓名搜索弹窗
	 * 
	 * @return
	 */
	@RequestMapping(value = "/popDetails")
	@Permission(privilege = "pms:role:edit")
	public String popDetails() {
		return "user/user_pop_details";
	}
	@RequestMapping(value = "/popQueryData")
	@Permission(privilege = "pms:role:edit")
	@ResponseBody
	public ResultVo popQueryData(ModelMap modelMap, HttpServletRequest request, String keyword) {
		
		Page pageObj = this.getPageObj();
	    Map<String, Object> params = new HashMap<String, Object>();
		
		if(StringUtil.isNotNull(keyword)) {
			keyword = keyword.trim();
			params.put("name", keyword);
			modelMap.put("name", keyword);
		}
		
		pageObj = (Page) empolyeeApi.findByPage(pageObj, params).getData();
		
		//设置页面显示
	    ResultVo resultVo = new ResultVo();
	    resultVo.setRecordsTotal(pageObj.getTotalCount());
	    resultVo.setInfoData(pageObj.getRecords());
		
		return resultVo;
	}
	
	/**
	 * 供应商搜索弹窗
	 * 
	 * @return
	 */
	@RequestMapping(value = "/vendorDetails")
	public String vendorDetails() {
		return "user/user_vendor_details";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/vendorQueryData")
	@ResponseBody
	public ResultVo vendorQueryData(ModelMap modelMap, HttpServletRequest request, String keyword) {
		
		Page pageObj = this.getPageObj();
	    Map<String, Object> params = new HashMap<String, Object>();
		
		if(StringUtil.isNotNull(keyword)) {
			keyword = keyword.trim();
			params.put("name", keyword);
			modelMap.put("name", keyword);
		}
		
		Map<String, Object> resultMap =  (Map<String, Object>) vendorApiService.findList(pageObj, null, null, params).getData();
		pageObj = (Page) resultMap.get("page");
		
		//设置页面显示
	    ResultVo resultVo = new ResultVo();
	    resultVo.setRecordsTotal(pageObj.getTotalCount());
	    resultVo.setInfoData(resultMap.get("list"));
		
		return resultVo;
	}

	/*@SuppressWarnings("unchecked")
	@RequestMapping(value = "/popSearch")
	@Permission(privilege = "pms:role:edit")
	@ResponseBody
	public ResultVo popSearch(String keyword) {
		ResultVo resultVo = new ResultVo();

		if (StringUtils.isNotBlank(keyword)) {
			keyword = keyword.trim();
			if (keyword.length() >= 2) {// 大于两个字的搜索才认为合法，因为dubbo底层用的是like
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("name", keyword);
				List<Employee> employees = null;
				try {
					employees = (List<Employee>) empolyeeApi.getEmployeeList(params).getData();
				} catch (ServiceException e) {
					logger.error("UserController-popSearch-ServiceException=》用户管理-加载职员页面-ServiceException", e);
				}
				resultVo.setStatus(Constant.SUCCESS);
				resultVo.setInfoStr(this.getJsonByObject(employees));
			} else {
				resultVo.setStatus(Constant.RESULT_VALID);
			}
		} else {
			resultVo.setStatus(Constant.RESULT_VALID);
		}

		return resultVo;
	}*/

	/**
	 * 添加
	 */
	@Permission(privilege = { "pms:user:edit" })
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo save(@Valid SysUser user, BindingResult result, HttpSession session, String roleIds,
			String userGroupIds, String userOfficeIds, String userAddressIds, String userWarehouseIds, String userMenuIds) {
		if (logger.isDebugEnabled()) {
			logger.debug("UserController-save=》用户管理-添加");
		}
		ResultVo resultVo = new ResultVo();
		if (result.hasErrors()) { // 验证失败说明
			resultVo.setStatus(Constant.RESULT_VALID);
			resultVo.setInfoMap(this.getValidErrors(result.getFieldErrors()));
			return resultVo;
		}

		if (!StringUtil.isNotNull(user.getUserType())) { // 验证用户类型是否选择
			resultVo.setStatus(Constant.RESULT_VALID);
			Map<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put("userType", PmsServiceResultCode.PMS_USER_10024.getServiceResult().msg());
			resultVo.setInfoMap(errorMap);
			return resultVo;
		}
		try {
			if (StringUtil.isNotNull(user.getId())) {
				// 设置修改人信息
				SysUser lastUpdatedBy = new SysUser();
				lastUpdatedBy.setId((String) session.getAttribute(Constant.SYSTEM_LOGIN_USER_ID));
				user.setLastUpdateBy(lastUpdatedBy);
				user.setLastUpdateDate(new Date());

				// 修改设置密码
				if (StringUtil.isNotNull(user.getPassword())) {
					user.setPassword(Md5Utils.encryption(user.getPassword()));
				} else { // 未输入密码，则密码不修改
					user.setPassword(null);
				}

			} else { // 设置新增人信息
				SysUser createdBy = new SysUser();
				createdBy.setId((String) session.getAttribute(Constant.SYSTEM_LOGIN_USER_ID));
				user.setCreateBy(createdBy);
				user.setCreateDate(new Date());

				if (!StringUtil.isNotNull(user.getPassword())) { // 验证密码
					resultVo.setStatus(Constant.RESULT_VALID);
					Map<String, String> errorMap = new LinkedHashMap<String, String>();
					errorMap.put("password", "请输入密码");
					resultVo.setInfoMap(errorMap);
					return resultVo;
				} else {
					user.setPassword(Md5Utils.encryption(user.getPassword()));
				}
			}
			ServiceCode serviceCode = sysUserService.saveOrUpdateSysUser(user, roleIds, userGroupIds, userOfficeIds, userAddressIds, userWarehouseIds, userMenuIds);
			resultVo.setResult(serviceCode, "/user/tableList.do");
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("UserController-save-ServiceException=》用户管理-添加-ServiceException", e);
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("UserController-save-Exception=》用户管理-添加-Exception", e);
		}
		if (resultVo.isHasException()) { // 异常返还
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}

	/**
	 * 删除--逻辑删除
	 */
	@Permission(privilege = { "pms:user:delete" })
	@RequestMapping(value = "delete")
	@ResponseBody
	public ResultVo delete(String id, HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("UserController-delete=》用户管理-删除");
		}
		ResultVo resultVo = new ResultVo();
		try {
			SysUser user = new SysUser();
			user.setId(id);
			user.setDelFlag(BaseEntity.DEL_FLAG_DELETE);

			ServiceCode serviceCode = sysUserService.saveOrUpdateSysUser(user, null, null, null, null, null, null);
			resultVo.setResult(serviceCode, "/user/tableList.do");
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("UserController-delete-ServiceException=》用户管理-删除-ServiceException", e);
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("UserController-delete-Exception=》用户管理-删除-Exception", e);
		}
		if (resultVo.isHasException()) {
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}

	/**
	 * 修改是否登录
	 */
	@Permission(privilege = { "pms:user:edit" })
	@RequestMapping(value = "updateLoginFlag")
	@ResponseBody
	public ResultVo updateLoginFlag(String userId, String loginFlag, HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("UserController-updateLoginFlag=》用户管理-修改是否登录");
		}
		ResultVo resultVo = new ResultVo();
		try {
			SysUser user = new SysUser();
			user.setId(userId);
			user.setLoginFlag(loginFlag);

			ServiceCode serviceCode = sysUserService.saveOrUpdateSysUser(user, null, null, null, null, null, null);
			resultVo.setResult(serviceCode, "/user/list.do");
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("UserController-updateLoginFlag-ServiceException=》用户管理-修改是否登录-ServiceException", e);
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("UserController-updateLoginFlag-Exception=》用户管理-修改是否登录-Exception", e);
		}
		if (resultVo.isHasException()) {
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}

	/**
	 * 加载分配角色页面
	 * 
	 * @return
	 */
	@Permission(privilege = { "pms:user:assign" })
	@RequestMapping("assignRoleForm")
	public void assignRoleForm(HttpServletResponse response, String userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("UserController-assignRoleform=》用户管理-加载分配角色页面");
		}
		try {
			this.responseWrite(response, this.getRoleNodes(userId));
		} catch (ServiceException e) {
			logger.error("UserController-assignRoleform-ServiceException=》用户管理-加载分配角色页面", e);
		} catch (Exception e) {
			logger.error("UserController-assignRoleform-Exception=》用户管理-加载分配角色页面", e);
		}
	}

	/**
	 * 获取角色列表，标识已分配用户
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public String getRoleNodes(String userId) throws ServiceException {
		List<SysRole> list = sysRoleService.selectAllRoleByUserId(userId);
		List<Object> listResult = new ArrayList<Object>();
		if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
			for (SysRole role : list) {
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("id", role.getId());
				row.put("name", role.getName());
				row.put("pId", 0);
				row.put("open", true);
				if (StringUtil.isNotNull(role.getUserRoleid())) {
					row.put("checked", true);
				}
				listResult.add(row);
			}
		}
		return this.getJsonByObject(listResult);
	}
	
	/**
	 * 获取公司列表，标识已分配公司
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public String getOfficeNodes(String userId) throws ServiceException {
		//查询原有的关联公司
		List<SysUserOffice> officeList = null;
		if(StringUtil.isNotNull(userId)){
			officeList = sysUserService.getUserOfficeByUser(userId);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", "0");
		List<SysOffice> list = officeApiConsumer.getOfficeList(params);
		List<Object> listResult = new ArrayList<Object>();
		if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
			for (SysOffice office : list) {
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("id", office.getId());
				row.put("name", office.getName());
				row.put("pId", 0);
				row.put("open", true);
				
				//根据原有关联公司，默认选择这些节点
				if (CollectionUtils.isNotEmpty(officeList) && officeList.size() > 0) {
					for (SysUserOffice userOffice : officeList) {
						if(office.getId().equals(userOffice.getOfficeId())){
							row.put("checked", true);
						}
					}
				}
				listResult.add(row);
			}
		}
		return this.getJsonByObject(listResult);
	}
	
	/**
	 * 获取地址列表，标识已分配地址
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public String getAddressNodes(String userId) throws ServiceException {
		//查询原有的关联公司
		List<SysUserAddress> addressList = null;
		if(StringUtil.isNotNull(userId)){
			addressList = sysUserService.getUserAddressByUser(userId);
		}
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) addressApiService.getAddressListObject(null).getData();
		List<Object> listResult = new ArrayList<Object>();
		if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
			for (Map<String, Object> row : list) {
				if(row.get("type").equals("4") || row.get("type").equals("5") || row.get("type").equals("6") || row.get("type").equals("7")){
					continue ;
				}
				
				Map<String, Object> newRow = new HashMap<String, Object>();
				newRow.put("id", row.get("id"));
				newRow.put("name", row.get("name"));
				newRow.put("pId", row.get("pId"));
				newRow.put("open", true);
				newRow.put("addressCode", row.get("addressCode"));
				if(row.get("type").equals("3")){
					newRow.put("chkDisabled", false);
				}else{
					newRow.put("chkDisabled", true);
				}
				
				//根据原有关联地址，默认选择这些节点
				if (CollectionUtils.isNotEmpty(addressList) && addressList.size() > 0) {
					for (SysUserAddress userAddress : addressList) {
						if(row.get("id").equals(userAddress.getAddressId())){
							newRow.put("checked", true);
						}
					}
				}
				listResult.add(newRow);
			}
		}
		return this.getJsonByObject(listResult);
	}
	
	/**
	 * 获取仓库列表，标识已分配仓库
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public String getWarehouseNodes(String userId) throws ServiceException {
		//查询原有的关联公司
		List<SysUserWarehouse> warehouseList = null;
		if(StringUtil.isNotNull(userId)){
			warehouseList = sysUserService.getUserWarehouseByUser(userId);
		}
		
		List<Map<String, Object>> listResult = (List<Map<String, Object>>) warehouseApiService.getWarehouseDataTree().getData();
		if (CollectionUtils.isNotEmpty(listResult) && listResult.size() > 0) {
			for (Map<String, Object> row : listResult) {
				//根据原有关联仓库，默认选择这些节点
				if (CollectionUtils.isNotEmpty(warehouseList) && warehouseList.size() > 0) {
					for (SysUserWarehouse userWarehouse : warehouseList) {
						if(row.get("id").equals(userWarehouse.getWarehouseId())){
							row.put("checked", true);
						}
					}
				}
			}
		}
		return this.getJsonByObject(listResult);
	}

	/**
	 * 修改用户的角色
	 * 
	 * @param userId
	 * @param roleIds
	 * @param response
	 */
	@Permission(privilege = { "pms:user:assign" })
	@RequestMapping(value = "/assignRole", method = RequestMethod.POST)
	public void assignRole(HttpSession session, @RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "roleIds", required = true) String roleIds, HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug("UserController.assignRole=》用户管理- 修改用户的角色");
		}
		try {
			sysUserRoleService.authorizationUserRole(userId, roleIds);
		} catch (ServiceException e) {
			logger.error("UserController.assignRole ServiceException =》用户管理- 修改用户的角色" + e);
		} catch (Exception e) {
			logger.error("UserController.assignRole Exception=》用户管理-修改用户的角色", e);
		}

		this.responseWrite(response, this.buildResponse(PmsServiceResultCode.SUCCESS.getServiceResult()));
	}

	/**
	 * 加载分配用户组页面
	 * 
	 * @return
	 */
	@Permission(privilege = { "pms:user:assign" })
	@RequestMapping("assignUserGroupForm")
	public void assignUserGroupForm(HttpServletResponse response, String userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("UserController-assignUserGroupform=》用户管理-加载分配用户组页面");
		}
		try {
			this.responseWrite(response, this.getUserGroupNodes(userId));
		} catch (ServiceException e) {
			logger.error("UserController-assignUserGroupform-ServiceException=》用户管理-加载分配用户组页面", e);
		} catch (Exception e) {
			logger.error("UserController-assignUserGroupform-Exception=》用户管理-加载分配用户组页面", e);
		}
	}

	/**
	 * 获取角色列表，标识已分配用户
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public String getUserGroupNodes(String userId) throws ServiceException {
		List<SysUserGroup> list = sysUserGroupService.selectAllUserByUserGroupId(userId);
		List<Object> listResult = new ArrayList<Object>();
		if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
			for (SysUserGroup userGroup : list) {
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("id", userGroup.getId());
				row.put("name", userGroup.getName());
				row.put("pId", userGroup.getParent().getId());
				row.put("open", true);
				if (StringUtil.isNotNull(userGroup.getType())) {
					row.put("checked", true);
				}
				listResult.add(row);
			}
		}
		return this.getJsonByObject(listResult);
	}

	/**
	 * 修改用户的角色
	 * 
	 * @param userId
	 * @param roleIds
	 * @param response
	 */
	@Permission(privilege = { "pms:user:assign" })
	@RequestMapping(value = "/assignUserGroup", method = RequestMethod.POST)
	public void assignUserGroup(HttpSession session, @RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "userGroupIds", required = true) String userGroupIds, HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug("UserController.assignUserGroup=》用户管理- 修改用户的角色");
		}
		try {
			sysUserGroupUserService.authorizationUserGroupUserByUserId(userId, userGroupIds);
		} catch (ServiceException e) {
			logger.error("UserController.assignUserGroup ServiceException =》用户管理- 修改用户的角色" + e);
		} catch (Exception e) {
			logger.error("UserController.assignUserGroup Exception=》用户管理-修改用户的角色", e);
		}

		this.responseWrite(response, this.buildResponse(PmsServiceResultCode.SUCCESS.getServiceResult()));
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 加载分配用户菜单页面
	 * 
	 * @return
	 */
	@Permission(privilege = { "pms:user:assign" })
	@RequestMapping("assignUserMenuForm")
	public void assignUserMenuForm(HttpServletResponse response, String userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("UserController-assignUserMenuForm=》用户管理-加载分配用户菜单页面");
		}
		try {
			this.responseWrite(response, this.getUserMenuNodes(userId));
		} catch (ServiceException e) {
			logger.error("UserController-assignUserMenuForm-ServiceException=》用户管理-加载分配用户菜单页面", e);
		} catch (Exception e) {
			logger.error("UserController-assignUserMenuForm-Exception=》用户管理-加载分配用户菜单页面", e);
		}
	}

	/**
	 * 获取用户菜单列表，标识已分配菜单
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public String getUserMenuNodes(String userId) throws ServiceException {
		List<Object> menuTreeList = sysMenuService.selectAllMenuByUserId(userId);
		return this.getJsonByObject(menuTreeList);
	}

	/**
	 * 修改用户的菜单
	 * 
	 * @param userId
	 * @param roleIds
	 * @param response
	 */
	@Permission(privilege = { "pms:user:assign" })
	@RequestMapping(value = "/assignUserMenu", method = RequestMethod.POST)
	public void assignUserMenu(HttpSession session, @RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "userMenuIds", required = true) String userMenuIds, HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug("UserController.assignUserMenu=》用户管理- 修改用户的菜单");
		}
		try {
			sysUserService.authorizationUserMenu(userId, userMenuIds);
		} catch (ServiceException e) {
			logger.error("UserController.assignUserMenu ServiceException =》用户管理- 修改用户的菜单" + e);
		} catch (Exception e) {
			logger.error("UserController.assignUserMenu Exception=》用户管理-修改用户的菜单", e);
		}

		this.responseWrite(response, this.buildResponse(PmsServiceResultCode.SUCCESS.getServiceResult()));
	}
	
	
	
	
	
	

	/**
	 * 查看所有机构
	 * 
	 * @return
	 */
	@RequestMapping(value = "ajaxList")
	@ResponseBody
	public void ajaxList(SysOffice officeParams, String parent, HttpServletResponse response) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();

			if (StringUtil.isNotNull(officeParams.getType())) {
				params.put("type", officeParams.getType());
			}
			if (StringUtil.isNotNull(parent)) {
				params.put("parentId", parent);
			}
			List<Object> listResult = officeApiConsumer.getObjectList(params);
			this.responseWrite(response, this.getJsonByObject(listResult));
		} catch (ServiceException e) {
			logger.error("OfficeController-ajaxList-ServiceException=》机构管理-查看所有机构-ServiceException", e);
		} catch (Exception e) {
			logger.error("OfficeController-ajaxList-Exception=》机构管理-查看所有机构-Exception", e);
		}
	}
}

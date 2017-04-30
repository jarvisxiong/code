package com.ffzx.permission.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.ffzx.permission.mapper.SysUserMapper;
import com.ffzx.permission.model.SysUserAddress;
import com.ffzx.permission.model.SysUserMenu;
import com.ffzx.permission.model.SysUserOffice;
import com.ffzx.permission.model.SysUserWarehouse;
import com.ffzx.permission.service.SysUserGroupUserService;
import com.ffzx.permission.service.SysUserRoleService;
import com.ffzx.permission.service.SysUserService;

/**
 * 用户管理service实现类
 * 
 * @author liujunjun
 * @date 2016年3月1日 下午7:33:42
 * @version 1.0
 */
@Service("sysUserService")
public class SysUserServiceImpl extends BaseCrudServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserGroupUserService sysUserGroupUserService;
    
    @Autowired
    private SysUserRoleService sysUserRoleService;
    
    @Override
    public CrudMapper init() {
        return sysUserMapper;
    }

	public SysUser getSysUserByUserName(String loginName) throws ServiceException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName_", loginName);		
		List<SysUser> subSystemConfigList = sysUserMapper.selectByPage(null, null, null, params);
		if(StringUtil.isNotNull(subSystemConfigList) && subSystemConfigList.size() > 0){
			return subSystemConfigList.get(0);
		}
		return null;
	}

	/**
	 * 新增或编辑用户
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode saveOrUpdateSysUser(SysUser user,String roleIds,String userGroupIds, String userOfficeIds, String userAddressIds, String userWarehouseIds, String userMenuIds) throws ServiceException {
		int result = 0;
		String id = user.getId();
		//参数唯一性验证
		ServiceCode serviceCode = validationParam(user);
		if(StringUtil.isNotNull(serviceCode)){
			return serviceCode;
		}

		if(StringUtil.isNotNull(user.getId())){   //有ID则为修改
			result = sysUserMapper.updateByPrimaryKeySelective(user);
		}else{					//新增
			id = UUIDGenerator.getUUID();
			user.setId(id);
			result = sysUserMapper.insertSelective(user);
		}		
		if(result > 0){
			//分配角色
			if(roleIds != null){
				sysUserRoleService.authorizationUserRole(id, roleIds);
			}
			//分配用户
			if(userGroupIds != null){
				sysUserGroupUserService.authorizationUserGroupUserByUserId(id, userGroupIds);
			}
			//分配公司
			if(userOfficeIds != null){
				authorizationUserOffice(id, userOfficeIds);
			}
			//分配地址
			if(userAddressIds != null){
				authorizationUserAddress(id, userAddressIds);
			}
			//分配仓库
			if(userWarehouseIds != null){
				authorizationUserWarehouse(id, userWarehouseIds);
			}
			//分配权限
			if(userMenuIds != null){
				authorizationUserMenu(id, userMenuIds);
			}
		}
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}
	
	/**
	 * 保存用户公司
	 * @param userId
	 * @param officeIdStr
	 * @throws ServiceException
	 */
	private void authorizationUserOffice(String userId, String officeIdStr) throws ServiceException {

		String[] officeIdsArr = new String[0];
		if(StringUtil.isNotNull(officeIdStr)) {
			officeIdsArr = officeIdStr.split(",");
			List<String> officeIds = Arrays.asList(officeIdsArr);
			sysUserMapper.deleteUserAllOfficeList(userId);
			
			if(officeIds != null && officeIds.size() > 0) {
				List<SysUserOffice> userOfficeList = new ArrayList<SysUserOffice>();
				for (int i = 0; i < officeIds.size(); i++) {  
					SysUserOffice userOffice = new SysUserOffice();
					userOffice.setId(UUIDGenerator.getUUID());
					userOffice.setOfficeId(officeIds.get(i));
					userOffice.setUserId(userId);
					userOfficeList.add(userOffice);
				}
				sysUserMapper.insertUserOffice(userOfficeList);
			}
		}else{
			sysUserMapper.deleteUserAllOfficeList(userId);
			
		}
	}
	
	/**
	 * 保存用户地址
	 * @param userId
	 * @param addressIdStr
	 * @throws ServiceException
	 */
	private void authorizationUserAddress(String userId, String addressIdStr) throws ServiceException {

		String[] addressIdsArr = new String[0];
		if(StringUtil.isNotNull(addressIdStr)) {
			addressIdsArr = addressIdStr.split(",");
			List<String> addressIds = Arrays.asList(addressIdsArr);
			sysUserMapper.deleteUserAllAddressList(userId);
			
			if(addressIds != null && addressIds.size() > 0) {
				List<SysUserAddress> userAddressList = new ArrayList<SysUserAddress>();
				for (int i = 0; i < addressIds.size(); i++) {  
					String[] addressIdCode = addressIds.get(i).split("_");
					
					SysUserAddress userAddress = new SysUserAddress();
					userAddress.setId(UUIDGenerator.getUUID());
					userAddress.setAddressId(addressIdCode[0]);
					userAddress.setAddressCode(addressIdCode[1]);
					userAddress.setUserId(userId);
					userAddressList.add(userAddress);
				}
				sysUserMapper.insertUserAddress(userAddressList);
			}
		}else{
			sysUserMapper.deleteUserAllAddressList(userId);
			
		}
	}
	
	/**
	 * 保存用户仓库
	 * @param userId
	 * @param warehouseIdStr
	 * @throws ServiceException
	 */
	private void authorizationUserWarehouse(String userId, String warehouseIdStr) throws ServiceException {

		String[] warehouseIdsArr = new String[0];
		if(StringUtil.isNotNull(warehouseIdStr)) {
			warehouseIdsArr = warehouseIdStr.split(",");
			List<String> warehouseIds = Arrays.asList(warehouseIdsArr);
			sysUserMapper.deleteUserAllWarehouseList(userId);
			
			if(warehouseIds != null && warehouseIds.size() > 0) {
				List<SysUserWarehouse> userWarehuseList = new ArrayList<SysUserWarehouse>();
				for (int i = 0; i < warehouseIds.size(); i++) {  
					SysUserWarehouse userWarehouse = new SysUserWarehouse();
					userWarehouse.setId(UUIDGenerator.getUUID());
					userWarehouse.setWarehouseId(warehouseIds.get(i));
					userWarehouse.setUserId(userId);
					userWarehuseList.add(userWarehouse);
				}
				sysUserMapper.insertUserWarehouse(userWarehuseList);
			}
		}else{
			sysUserMapper.deleteUserAllWarehouseList(userId);
			
		}
	}
	
	/**
	 * 保存用户菜单
	 * @param userId
	 * @param menuIdStr
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public void authorizationUserMenu(String userId, String menuIdStr) throws ServiceException {

		String[] menuIdsArr = new String[0];
		if(StringUtil.isNotNull(menuIdStr)) {
			menuIdsArr = menuIdStr.split(",");
			List<String> menuIds = Arrays.asList(menuIdsArr);
			sysUserMapper.deleteUserAllMenuList(userId);
			
			if(menuIds != null && menuIds.size() > 0) {
				List<SysUserMenu> userMenuList = new ArrayList<SysUserMenu>();
				for (int i = 0; i < menuIds.size(); i++) {  
					SysUserMenu userMenu = new SysUserMenu();
					userMenu.setId(UUIDGenerator.getUUID());
					userMenu.setMenuId(menuIds.get(i));
					userMenu.setUserId(userId);
					userMenuList.add(userMenu);
				}
				sysUserMapper.insertUserMenu(userMenuList);
			}
		}else{
			sysUserMapper.deleteUserAllMenuList(userId);
			
		}
	}

	/**
	 * 新增或修改的参数验证唯一性
	 * @param ssc
	 * @return
	 */
	public ServiceCode validationParam(SysUser user){

		if(StringUtil.isNotNull(user.getId())){   //修改参数验证
			SysUser olduser = sysUserMapper.selectByPrimaryKey(user.getId());
			if(user.getName()!= null 
					&& !user.getName().equals(olduser.getName())
					&& validationName(user.getName()) > 0){
				return PmsServiceResultCode.PMS_USER_10022;
			}else if(user.getLoginName()!= null 
					&& !user.getLoginName().equals(olduser.getLoginName())
					&& validationLoginName(user.getLoginName()) > 0){
				return PmsServiceResultCode.PMS_USER_10021;
			}else if(user.getWorkNo()!= null 
					&& !user.getWorkNo().equals(olduser.getWorkNo())
					&& validationNo(user.getWorkNo()) > 0){
				return PmsServiceResultCode.PMS_USER_10023;
			}
		}else{		
			if(user.getLoginName()!= null 
					&& validationLoginName(user.getLoginName()) > 0){
				return PmsServiceResultCode.PMS_USER_10021;
			}else if(user.getWorkNo()!= null 
					&& validationNo(user.getWorkNo()) > 0){
				return PmsServiceResultCode.PMS_USER_10023;
			}
		}
		return null;
	}
	
	/**
	 * 登录名的唯一性验证
	 * @param loginName
	 * @return int > 0已存在 
	 */
	public int validationLoginName(String loginName){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);		
		List<SysUser> userList = sysUserMapper.selectByPage(new Page(), null, null, params);
		if(StringUtil.isNotNull(userList)){
			return userList.size();
		}
		return 0;
	}
	
	/**
	 * 工号的唯一性验证
	 * @param no
	 * @return int > 0已存在 
	 */
	public int validationNo(String workNo){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workNo", workNo);		
		List<SysUser> userList = sysUserMapper.selectByPage(new Page(), null, null, params);
		if(StringUtil.isNotNull(userList)){
			return userList.size();
		}
		return 0;
	}
	
	/**
	 * 姓名的唯一性验证
	 * @param name
	 * @return int > 0已存在 
	 */
	public int validationName(String name){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);		
		List<SysUser> userList = sysUserMapper.selectByPage(new Page(), null, null, params);
		if(StringUtil.isNotNull(userList)){
			return userList.size();
		}
		return 0;
	}	
		
	public List<SysUser> selectAllUserByUserGroupId(String userGroupId,String officeId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userGroupId", userGroupId);
		params.put("officeId", officeId);
		return sysUserMapper.selectAllUserByUserGroupId(params);
	}

	public List<SysUser> getUserListByRole(String roleId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		List<SysUser> list = sysUserMapper.getUserListByRole(params);
		return list;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode updateUserPassword(String loginName,String newPassword) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		params.put("newPassword", newPassword);
		int result  = sysUserMapper.updateUserPassword(params);
		
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Override
	public List<SysUserOffice> getUserOfficeByUser(String userId) throws ServiceException {
		return sysUserMapper.getUserOfficeByUser(userId);
	}

	@Override
	public List<SysUserAddress> getUserAddressByUser(String userId) throws ServiceException {
		return sysUserMapper.getUserAddressByUser(userId);
	}

	@Override
	public List<SysUserWarehouse> getUserWarehouseByUser(String userId) throws ServiceException {
		return sysUserMapper.getUserWarehouseByUser(userId);
	}
}
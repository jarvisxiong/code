package com.ffzx.permission.service;

import java.util.List;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.permission.model.SysUserAddress;
import com.ffzx.permission.model.SysUserOffice;
import com.ffzx.permission.model.SysUserWarehouse;

/**
 * 用户管理service
 * 
 * @author liujunjun
 * @date 2016年3月1日 下午7:33:18
 * @version 1.0
 */
public interface SysUserService extends BaseCrudService {
	/**
	 * 修改密码
	 * @param loginName ,newPassword
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode updateUserPassword(String loginName,String newPassword) throws ServiceException;

	/**
	 * 通过用户获取用户对象
	 * @param loginName
	 * @return
	 * @throws ServiceException
	 */
	public SysUser getSysUserByUserName(String loginName) throws ServiceException;
	
	/**
	 * 新增或编辑用户
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode saveOrUpdateSysUser(SysUser user, String roleIds, String userGroupIds, String userOfficeIds, String userAddressIds, String userWarehouseIds, String userMenuIds) throws ServiceException;
	
	/**
	 * 保存用户菜单
	 * @param userId
	 * @param menuIdStr
	 * @throws ServiceException
	 */
	public void authorizationUserMenu(String userId, String menuIdStr) throws ServiceException;
	
	/**
	 * 查询所有用户，并标识用户组关联
	 * @param userGroupId
	 * @return
	 * @throws ServiceException
	 */
	public List<SysUser> selectAllUserByUserGroupId(String userGroupId,String officeId) throws ServiceException;
	
	/**
	 * 根据角色查询用户
	 * 
	 * @param roleId
	 * @return
	 * @throws ServiceException
	 */
	public List<SysUser> getUserListByRole(String roleId) throws ServiceException;
	
	/**
	 * 根据用户ID，查询用户关联的公司
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public List<SysUserOffice> getUserOfficeByUser(String userId) throws ServiceException;
	
	/**
	 * 根据用户ID，查询用户关联的地址
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public List<SysUserAddress> getUserAddressByUser(String userId) throws ServiceException;
	
	/**
	 * 根据用户ID，查询用户关联的仓库
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public List<SysUserWarehouse> getUserWarehouseByUser(String userId) throws ServiceException;
}
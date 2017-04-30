package com.ffzx.permission.mapper;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.permission.api.dto.SysUserDto;
import com.ffzx.permission.model.SysUserAddress;
import com.ffzx.permission.model.SysUserMenu;
import com.ffzx.permission.model.SysUserOffice;
import com.ffzx.permission.model.SysUserWarehouse;

/**
 * 用户管理Mapper
 * 
 * @author liujunjun
 * @date 2016年3月1日 下午7:34:15
 * @version 1.0
 */
@MyBatisDao
public interface SysUserMapper extends CrudMapper {
	
	/**
	 * 获取所有用户，标识用户组关联
	 * 
	 * @param params
	 * @return
	 */
	public List<SysUser> selectAllUserByUserGroupId(Map<String, Object> params);
	
	/**
	 * 根据角色查询用户
	 * 
	 * @param map
	 * @return
	 */
	public List<SysUser> getUserListByRole(Map<String, Object> map);
	
	
	/**
	 *修改密码
	 * 
	 * @param String
	 * @return
	 */
	public int updateUserPassword(Map<String, Object> params);
	
	/**
	 * 根据工号激活或禁用用户
	 * @param sysUser
	 * @return
	 */
	public int updateUserStatusByWorkNo(SysUser sysUser);
	
	/**
	 * 根据工号更新用户信息(工号,姓名,手机等)  ==>> add by cmm 2016-08-29
	 * @param sysUserDto
	 * @return
	 */
	public int updateUserByWorkNo(SysUserDto sysUserDto);
	
	/**
	 * 删除用户关联的公司
	 * @param userId
	 */
	public void deleteUserAllOfficeList(String userId);

	/**
	 * 插入用户关联的公司
	 * @param userOfficeList
	 */
	public void insertUserOffice(List<SysUserOffice> userOfficeList);
	
	/**
	 * 删除用户关联的地址
	 * @param userId
	 */
	public void deleteUserAllAddressList(String userId);

	/**
	 * 插入用户关联的地址
	 * @param userAddressList
	 */
	public void insertUserAddress(List<SysUserAddress> userAddressList);
	
	/**
	 * 删除用户关联的仓库
	 * @param userId
	 */
	public void deleteUserAllWarehouseList(String userId);

	/**
	 * 插入用户关联的仓库
	 * @param userWarehouseList
	 */
	public void insertUserWarehouse(List<SysUserWarehouse> userWarehouseList);
	
	/**
	 * 删除用户关联的菜单
	 * @param userId
	 */
	public void deleteUserAllMenuList(String userId);

	/**
	 * 插入用户关联的菜单
	 * @param userMenuList
	 */
	public void insertUserMenu(List<SysUserMenu> userMenuList);
	
	/**
	 * 根据用户ID，查询用户关联的公司
	 * @param userId
	 * @return
	 */
	public List<SysUserOffice> getUserOfficeByUser(String userId);
	
	/**
	 * 根据用户ID，查询用户关联的地址
	 * @param userId
	 * @return
	 */
	public List<SysUserAddress> getUserAddressByUser(String userId);
	
	/**
	 * 根据用户ID，查询用户关联的仓库
	 * @param userId
	 * @return
	 */
	public List<SysUserWarehouse> getUserWarehouseByUser(String userId);

}
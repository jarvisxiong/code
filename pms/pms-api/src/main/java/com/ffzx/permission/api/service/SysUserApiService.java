package com.ffzx.permission.api.service;

import java.util.Map;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.permission.api.dto.SysUserDto;

/**
 * dubbo 对外用户接口
 * @author tangshifeng
 * @date 2011-1-29
 * @version 1.0.0
 */
public interface SysUserApiService {

	/**
	 * 用户名获取用户
	 * 返回ResultDto对象,ResultDto.data 数据类型为{@link SysUser}
	 *  @return {@link ResultDto}
	 */
	public ResultDto getUserByName(String name);
	
	/**
	 * 用户名和密码获取用户
	 * 返回ResultDto对象,ResultDto.data 数据类型为{@link SysUser}
	 *  @return {@link ResultDto}
	 */
	public ResultDto getUserForLogin(String name, String password);
	
	/**
	 * 根据用户id获取用户
	 * 返回ResultDto对象,ResultDto.data 数据类型为{@link SysUser}
	 * @param id
	 * @param isLoadRole true： 加载，false：不加载
	 * @return {@link ResultDto}
	 */
	public ResultDto getUserById(String id, boolean isLoadRole);
	
	/**
	 * 根据工号启用或禁用用户
	 * 返回ResultDto对象,ResultDto.OK_CODE:成功,ResultDto.ERROR_CODE:失败
	 * @param workNo
	 * @param loginFlag, '1':启用,'0':禁用
	 * @return {@link ResultDto}
	 */
	public ResultDto updateUserStatusByWorkNo(String workNo, String loginFlag);
	
	/**
	 * 根据工号更新用户信息(工号,姓名,手机等)  ==>> add by cmm 2016-08-29
	 * 返回ResultDto对象,ResultDto.OK_CODE:成功,ResultDto.ERROR_CODE:失败
	 * @param sysUserDto
	 * @return {@link ResultDto}
	 */
	public ResultDto updateUserByWorkNo(SysUserDto sysUserDto);
	
	/**
	 * 根据参数，分页查询用户
	 * 
	 * 返回ResultDto对象,ResultDto.OK_CODE:成功,ResultDto.ERROR_CODE:失败
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return {@link ResultDto}
	 */
	public ResultDto getUserByPage(Page page, String orderByField, String orderBy, Map<String, Object> params);
}

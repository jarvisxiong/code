package com.ffzx.permission.api.service;


import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.system.entity.SysMenu;

/**
 * dubbo 权限接口
 * @author tangshifeng
 * @date 2011-1-28
 * @version 1.0.0
 */
public interface SysPermissionApiService{

	/**
	 * 用户id获取菜单
	 * 返回ResultDto对象,ResultDto.data 数据类型为List<SysMenu>
	 * List<SysMenu> 菜单列表，SysMenu 为菜单对象{@link SysMenu}
	 * @param userId 用户id
	 * @return {@link ResultDto}
	 */
	public ResultDto getMenuPermissionList(String userId) throws ServiceException;
	
	/**
	 * 用户id获取功能权限
	 * 返回ResultDto对象,ResultDto.data 数据类型为List<String> 
	 * List<String> 表示权限列，String格式如：pms:menu:edit
	 * @param userId 用户id
	 * @return {@link ResultDto}
	 */
	public ResultDto getFunctionPermissionList(String userId) throws ServiceException;

}
package com.ffzx.permission.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.vo.SysMenuVo;
import com.ffzx.permission.api.service.SysPermissionApiService;
import com.ffzx.permission.service.SysMenuService;

/**
 * dubbo 权限API
 * 
 * @author shifeng.tang
 * @date 2016-1-28
 * @version 1.0.0
 */

@Service("sysPermissionApiService")
public class SysPermissionApiServiceImpl implements SysPermissionApiService {

	@Autowired
	public SysMenuService sysMenuService;

	/**
	 * 用户id获取菜单 返回ResultDto对象,ResultDto.data 格式为List<SysMenu>转化后的json字符串
	 * 
	 * @param userId
	 * @return {@link ResultDto}
	 */
	public ResultDto getMenuPermissionList(String userId) throws ServiceException {
		ResultDto rsDto = new ResultDto(ResultDto.OK_CODE, "success");
		List<SysMenuVo> list = sysMenuService.getMenuByUserId(userId);
		rsDto.setData(JsonMapper.getInstance().toJson(list));
		return rsDto;
	}

	/**
	 * 用户id获取权限 返回ResultDto对象,ResultDto.data 格式为List<String>
	 * 
	 * @param userId
	 * @return {@link ResultDto}
	 */
	public ResultDto getFunctionPermissionList(String userId) throws ServiceException {
		List<String> list = sysMenuService.getPermissionByUserId(userId);
		ResultDto rsDto = new ResultDto(ResultDto.OK_CODE, "success");
		rsDto.setData(list);
		return rsDto;
	}

}

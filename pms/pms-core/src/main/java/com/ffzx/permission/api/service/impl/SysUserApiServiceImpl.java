package com.ffzx.permission.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.basedata.api.dto.Partner;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.system.entity.SysRole;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.Global;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.Md5Utils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.permission.api.dto.SysUserDto;
import com.ffzx.permission.api.service.SysUserApiService;
import com.ffzx.permission.mapper.SysUserMapper;
import com.ffzx.permission.model.SysUserAddress;
import com.ffzx.permission.model.SysUserOffice;
import com.ffzx.permission.model.SysUserWarehouse;
import com.ffzx.permission.service.SysRoleService;
import com.ffzx.permission.service.SysUserService;

/**
 * dubbo 对外用户接口实现
 * 
 * @author tangshifeng
 * @date 2011-1-29
 * @version 1.0.0
 */
@Service("sysUserApiService")
public class SysUserApiServiceImpl implements SysUserApiService {

	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(SysUserApiServiceImpl.class);

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;
	
	@Resource
    private SysUserMapper sysUserMapper;

	/**
	 * 用户名获取用户
	 * 
	 * @param userName
	 *            用户名
	 */
	public ResultDto getUserByName(String userName) {
		logger.debug("SysUserApiServiceImpl-getUserByName=》用户接口-用户名获取用户");
		ResultDto rsDto = null;
		try {
			SysUser user = sysUserService.getSysUserByUserName(userName);
			
			bindUserData(user);
			
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(JsonMapper.getInstance().toJson(user));
		} catch (Exception e) {
			logger.error("SysUserApiServiceImpl-getUserByName-Exception=》用户接口-Exception", e);
			rsDto = new ResultDto(ResultDto.ERROR_CODE, "fail:" + e);
		}
		return rsDto;
	}
	
	@Override
	public ResultDto getUserForLogin(String name, String password) {
		logger.debug("SysUserApiServiceImpl-getUserForLogin=》用户接口-用户名和密码获取用户");
		ResultDto rsDto = null;
		try {
			SysUser user = sysUserService.getSysUserByUserName(name);
			if(!StringUtil.isNotNull(user)){
				return new ResultDto(ResultDto.ERROR_CODE, "找不到用户");
			}
			if(!Global.YES.equals(user.getLoginFlag())){
				return new ResultDto(ResultDto.ERROR_CODE, "用户不能登录");
			}
			if(!Md5Utils.encryption(password).equals(user.getPassword())){
				return new ResultDto(ResultDto.ERROR_CODE, "用户密码错误");
			}
			
			bindUserData(user);
			
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(JsonMapper.getInstance().toJson(user));
		} catch (Exception e) {
			logger.error("SysUserApiServiceImpl-getUserForLogin-Exception=》用户接口-Exception", e);
			rsDto = new ResultDto(ResultDto.ERROR_CODE, "fail:" + e);
		}
		return rsDto;
	}
	
	/**
	 * 给登录用户绑定关联信息
	 * @param user
	 */
	private void bindUserData(SysUser user){
		if (StringUtil.isNotNull(user)) {
			List<SysRole> sysRoleList = sysRoleService.selectAllRole(user.getId());
			user.setRoleList(sysRoleList);
			
			//获取关联公司
			List<SysUserOffice> userOfficeList = sysUserMapper.getUserOfficeByUser(user.getId());
			if(StringUtil.isNotNull(userOfficeList)){
				for(SysUserOffice userOffice : userOfficeList){
					user.getOfficeList().add(userOffice.getOfficeId());
				}
			}
			
			//获取关联地址
			List<SysUserAddress> userAddressList = sysUserMapper.getUserAddressByUser(user.getId());
			if(StringUtil.isNotNull(userAddressList)){
				for(SysUserAddress userAddress : userAddressList){
					user.getAddressIdList().add(userAddress.getAddressId());
					user.getAddressCodeList().add(userAddress.getAddressCode());
				}
			}
			
			//获取关联仓库
			List<SysUserWarehouse> userWarehouseList = sysUserMapper.getUserWarehouseByUser(user.getId());
			if(StringUtil.isNotNull(userWarehouseList)){
				for(SysUserWarehouse userWarehouse : userWarehouseList){
					user.getWarehouseList().add(userWarehouse.getWarehouseId());
				}
			}
		}
	}

	@Override
	public ResultDto getUserById(String id, boolean isLoadRole) {
		logger.debug("SysUserApiServiceImpl-getUserById=》用户接口-根据用户id获取用户");
		ResultDto rsDto = null;
		try {
			SysUser user = sysUserService.findById(id);
			if (isLoadRole && StringUtil.isNotNull(user)) {
				List<SysRole> sysRoleList = sysRoleService.selectAllRole(user.getId());
				user.setRoleList(sysRoleList);
			}
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(JsonMapper.getInstance().toJson(user));
		} catch (Exception e) {
			logger.error("SysUserApiServiceImpl-getUserByName-Exception=》用户接口-Exception", e);
			rsDto = new ResultDto(ResultDto.ERROR_CODE, "fail:" + e);
		}
		return rsDto;
	}

	@Override
	@Transactional
	public ResultDto updateUserStatusByWorkNo(String workNo, String loginFlag) {
		if (logger.isDebugEnabled()) {
			logger.debug("SysUserApiServiceImpl-updateUserStatusByWorkNo=》用户接口-根据用户工号启用或禁用用户");
		}
		ResultDto rsDto = new ResultDto(ResultDto.OK_CODE, "success");;
		try {
			SysUser sysUser = new SysUser();
			sysUser.setWorkNo(workNo);
			sysUser.setLoginFlag(loginFlag);
			sysUserMapper.updateUserStatusByWorkNo(sysUser);
		} catch (Exception e) {
			logger.error("SysUserApiServiceImpl-updateUserStatusByWorkNo-Exception=》用户接口-Exception", e);
			rsDto = new ResultDto(ResultDto.ERROR_CODE, "fail:" + e);
		}
		return rsDto;
	}

	@Override
	@Transactional
	public ResultDto updateUserByWorkNo(SysUserDto sysUserDto) {
		if (logger.isDebugEnabled()) {
			logger.debug("SysUserApiServiceImpl-updateUserByWorkNo=》用户接口-根据工号更新用户信息");
		}
		
		ResultDto rsDto = new ResultDto(ResultDto.OK_CODE, "success");;
		try {
			sysUserMapper.updateUserByWorkNo(sysUserDto);
		} catch (Exception e) {
			logger.error("SysUserApiServiceImpl-updateUserByWorkNo-Exception=》用户接口-Exception", e);
			rsDto = new ResultDto(ResultDto.ERROR_CODE, "fail:" + e);
		}
		return rsDto;
	}

	@Override
	public ResultDto getUserByPage(Page page, String orderByField, String orderBy, Map<String, Object> params) {
		if (logger.isDebugEnabled()) {
			logger.debug("SysUserApiServiceImpl-getUserByPage=》用户接口-根据参数分页查询用户");
		}
		
		ResultDto rsDto = new ResultDto(ResultDto.OK_CODE, "success");;
		try {
			List<SysUser> list = sysUserService.findByPage(page, orderByField, orderBy, params);
			page.setRecords(list);
			rsDto.setData(page);
		} catch (Exception e) {
			logger.error("SysUserApiServiceImpl-getUserByPage-Exception=》用户接口-Exception", e);
			rsDto = new ResultDto(ResultDto.ERROR_CODE, "fail:" + e);
		}
		return rsDto;
	}
}

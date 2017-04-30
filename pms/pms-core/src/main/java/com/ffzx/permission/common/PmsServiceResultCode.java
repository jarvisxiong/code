package com.ffzx.permission.common;

import com.ffzx.commerce.framework.annotation.ServiceResult;
import com.ffzx.commerce.framework.enums.ServiceCode;

/**
 * pms Service系统响应枚举
 * @date 2016年2月26日 上午
 * code:10011 按10:01:1拆分  11：表示子系统，01：表示子系统下的菜单，1：表示菜单下的响应有多个响应则1从开始无限制增长如：100112
 * PMS子系统为10开头
 * @version 1.0
 * 
 */
public enum PmsServiceResultCode implements ServiceCode {

	@ServiceResult(code = 0000, msg = "操作成功！") SUCCESS, 	
	
	//----用户组管理 ----01开头
	@ServiceResult(code = 10011, msg = "请先删除子级用户组") PMS_USERGROUP_10011,
	@ServiceResult(code = 10012, msg = "请选择类型") PMS_USERGROUP_10012,
	@ServiceResult(code = 10013, msg = "名称已存在") PMS_USERGROUP_10013,
	@ServiceResult(code = 10014, msg = "编码已存在") PMS_USERGROUP_10014,
	@ServiceResult(code = 10015, msg = "原密码错误") PMS_USERGROUP_10015,
	
	//----用户管理------02开头
	@ServiceResult(code = 10021, msg = "登录名已存在", type = 2)	PMS_USER_10021,
	@ServiceResult(code = 10022, msg = "名称已存在", type = 2)	PMS_USER_10022,
	@ServiceResult(code = 10023, msg = "工号已存在",type = 2)	PMS_USER_10023,
	@ServiceResult(code = 10024, msg = "请选择用户类型",type = 2)	PMS_USER_10024,
		
	//----菜单管理------05开头
	@ServiceResult(code = 10051, msg = "同等级存在相同的菜单",type = 2) PMS_MENU_10051,
	@ServiceResult(code = 10052, msg = "请先删除子菜单",type = 1) PMS_MENU_10052,
	
	//----机构管理------06开头
	@ServiceResult(code = 10061, msg = "请先删除子机构",type = 1) PMS_OFFICE_10061,
	@ServiceResult(code = 10062, msg="同等级存在相同机构",type=2) PMS_OFFICE_1102,
	
	//----区域管理------07开头
	@ServiceResult(code= 10071,msg="请先删除子区域",type=1) PMS_AREA_10071,
	@ServiceResult(code = 10072, msg="同等级存在相同区域",type=2) PMS_AREA_10072,
	;

	/**
	 * 返回错误码
	 */
	public ServiceResult getServiceResult() {
		ServiceResult serviceResult;
		try {
			serviceResult = this.getClass().getField(this.name()).getAnnotation(ServiceResult.class);
		} catch (Exception e) {
			return null;
		}
		return serviceResult;
	}
}

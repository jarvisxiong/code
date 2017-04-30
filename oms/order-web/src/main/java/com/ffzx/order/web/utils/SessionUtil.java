package com.ffzx.order.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ffzx.commerce.framework.system.entity.SysUser;

public class SessionUtil {

	/**
	 * 系统登录用户
	 */
	public static final String SessionSystemLoginUserName = "SessionSystemLoginUserName";

	/**
	 * 清空session
	 */
	public static final void clearSession(HttpServletRequest request) {
		HttpSession session = request.getSession();

		session.removeAttribute(SessionUtil.SessionSystemLoginUserName);

		session.invalidate();// 非必须，单点登出接收到服务器消息时，会自动销毁session
	}

	/**
	 * 返回session中的用户对象
	 * 
	 * @param request
	 * @return
	 */
	public static final SysUser getSessionUser(HttpServletRequest request) {
		return (SysUser) request.getSession().getAttribute(
				SessionUtil.SessionSystemLoginUserName);
	}
}

package com.ffzx.promotion.common;

import com.ffzx.commerce.framework.annotation.ServiceResult;
import com.ffzx.commerce.framework.enums.ServiceCode;

/**
 * cims Service系统响应枚举
 * code:11011 按10:01:1拆分  11：表示子系统，01：表示子系统下的菜单，1：表示菜单下的响应有多个响应则1从开始无限制增长如：110112
 * Cims子系统为11开头 
 * @author liujunjun
 * @date 2016年3月17日 下午2:51:36
 * @version 1.0
 */
public enum PrmsServiceResultCode implements ServiceCode {

	@ServiceResult(code = 0000, msg = "操作成功！") SUCCESS, 
	
	// ----商品类别 ----1101开头
	@ServiceResult(code = 11011, msg = "优惠券名称已存在", type = 2) PRMS_COUPON_11011;// 11011

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

package com.ffzx.order.common;

import com.ffzx.commerce.framework.annotation.ServiceResult;
import com.ffzx.commerce.framework.enums.ServiceCode;

/**
 * oms Service系统响应枚举
 * 
 * @author liujunjun
 * @date 2016年3月14日 上午10:04:03
 * @version 1.0
 */
public enum OmsServiceResultCode implements ServiceCode {

	@ServiceResult(code = 0000, msg = "操作成功！") SUCCESS, 	
	@ServiceResult(code = 0001, msg = "该商品没有可用的sku", type = 1) NO_SKU,
	@ServiceResult(code = 0003, msg = "商品尚未出库不可切换,待出库之后才可以切换", type = 1) NO_OCCUPANCY,
	@ServiceResult(code = 0004, msg = "商品状态切换失败，原因：当前商品所有SKU在所有地区的[已占用数]不是0，请返回后重试！", type = 1) NO_OCCUPANCY_USED,
	@ServiceResult(code = 0005, msg = "商品状态切换失败，原因：当前商品状态已经不是“录入中、已录入、已下架”请返回后重试！", type = 1) NO_STAUTS,
	@ServiceResult(code = 3, msg = "去掉的地址中含有商品未出库", type = 1) REMOVE_ADDRESS,
	
	//----订单管理系统 ----3000开头----------
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

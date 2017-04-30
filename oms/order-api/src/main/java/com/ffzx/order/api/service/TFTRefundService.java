/**   
 * @Title: TFTRefundService.java
 * @Package com.ffzx.order.service.impl
 * @Description: TODO
 * @author 雷  
 * @date 2016年11月7日 
 * @version V1.0   
 */
package com.ffzx.order.api.service;

import java.math.BigDecimal;

import com.ffzx.commerce.framework.system.entity.SysUser;

/**
 * @ClassName: TFTRefundService
 * @Description: TODO
 * @author 雷
 * @date 2016年11月7日
 * 
 */
public interface TFTRefundService {
	/**
	 * 
	 * 雷------2016年11月7日
	 * 
	 * @Title: refundTFT
	 * @Description: 腾付通退款回写
	 * @param @param orderNo:订单号
	 * @param @param payPrice：退款金额
	 * @param @param refundNo：退款单号
	 * @param @param user：操作者
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean refundTFT(String orderNo, BigDecimal payPrice, String refundNo, SysUser user);

}

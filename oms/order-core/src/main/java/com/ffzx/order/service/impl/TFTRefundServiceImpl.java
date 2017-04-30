/**   
 * @Title: TFTRefundService.java
 * @Package com.ffzx.order.service.impl
 * @Description: TODO
 * @author 雷  
 * @date 2016年11月4日 
 * @version V1.0   
 */
package com.ffzx.order.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.order.api.service.TFTRefundService;
import com.ffzx.order.service.AftersaleRefundService;

/**
 * @ClassName: TFTRefundService
 * @Description: TODO
 * @author 雷
 * @date 2016年11月4日
 * 
 */
@Service("tFTRefundServiceImpl")
public class TFTRefundServiceImpl implements TFTRefundService {

	@Autowired
	private AftersaleRefundService aftersaleRefundService;

	/**
	 * 
	 * 雷-----2016年11月7日 (非 Javadoc)
	 * <p>
	 * Title: refundTFT
	 * </p>
	 * <p>
	 * Description:腾付通退款回写
	 * </p>
	 * 
	 * @param orderNo
	 * @param payPrice
	 * @param refundNo
	 * @param user
	 * @return
	 * @see com.ffzx.aftersale.service.TFTRefundService#refundTFT(java.lang.String,
	 *      java.math.BigDecimal, java.lang.String,
	 *      com.ffzx.commerce.framework.system.entity.SysUser)
	 */
	@Override
	public boolean refundTFT(String orderNo, BigDecimal payPrice, String refundNo, SysUser user) {
		/**
		 * 金额以分为单位处理为元为单位
		 */
		payPrice=payPrice.divide(new BigDecimal(100));
		return aftersaleRefundService.refundTFT(orderNo, payPrice, refundNo, user);
	}

}

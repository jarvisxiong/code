/**   
 * @Title: RefundBroadcastVo.java
 * @Package com.ffzx.order.api.vo
 * @Description: TODO
 * @author 雷  
 * @date 2016年10月24日 
 * @version V1.0   
 */
package com.ffzx.order.api.vo;

import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.order.api.dto.AftersaleRefund;
import com.ffzx.order.api.dto.OmsOrder;

/**
 * @ClassName: RefundBroadcastVo
 * @Description: 退款广播的数据
 * @author 雷
 * @date 2016年10月24日
 * 
 */
public class RefundBroadcastVo {
	/**
	 * 订单信息
	 */
	private OmsOrder order;
	/**
	 * 退款信息
	 */
	private AftersaleRefund refund;
	/**
	 * 广播的类型（1：退款，2：退货）
	 */
	private String type;
	private SysUser user;

	/**
	 * 雷----2016年10月24日
	 * 
	 * @return order
	 */
	public OmsOrder getOrder() {
		return order;
	}

	/**
	 * 雷-------2016年10月24日
	 * 
	 * @param order
	 *            要设置的 order
	 */
	public void setOrder(OmsOrder order) {
		this.order = order;
	}

	/**
	 * 雷----2016年10月24日
	 * 
	 * @return refund
	 */
	public AftersaleRefund getRefund() {
		return refund;
	}

	/**
	 * 雷-------2016年10月24日
	 * 
	 * @param refund
	 *            要设置的 refund
	 */
	public void setRefund(AftersaleRefund refund) {
		this.refund = refund;
	}

	/**
	 * 雷----2016年10月24日
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 雷-------2016年10月24日
	 * 
	 * @param type
	 *            要设置的 type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 雷----2016年11月25日
	 * 
	 * @return user
	 */
	public SysUser getUser() {
		return user;
	}

	/**
	 * 雷-------2016年11月25日
	 * @param user 要设置的 user
	 */
	public void setUser(SysUser user) {
		this.user = user;
	}

}

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
import com.ffzx.order.api.dto.AftersalePickup;
import com.ffzx.order.api.dto.OmsOrder;

/**
 * @ClassName: RefundBroadcastVo
 * @Description: 退货广播的数据
 * @author 雷
 * @date 2016年10月24日
 * 
 */
public class ReturnGoodsBroadcastVo {
	/**
	 * 订单信息
	 */
	private OmsOrder order;
	/**
	 * 换货信息
	 */
	private AftersalePickup pickup;

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
	 * @return pickup
	 */
	public AftersalePickup getPickup() {
		return pickup;
	}

	/**
	 * 雷-------2016年10月24日
	 * 
	 * @param pickup
	 *            要设置的 pickup
	 */
	public void setPickup(AftersalePickup pickup) {
		this.pickup = pickup;
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
	 * 
	 * @param user
	 *            要设置的 user
	 */
	public void setUser(SysUser user) {
		this.user = user;
	}

}

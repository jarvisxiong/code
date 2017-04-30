package com.ffzx.order.api.vo;

import com.ffzx.commerce.framework.common.persistence.BaseEntity;


/**
 * @Description: 查询订单信息对象
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年11月18日 下午3:50:19
 * @version V1.0 
 *
 */
public class OmsOrderParamVo extends BaseEntity<OmsOrderParamVo>{
  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4961480227173907685L;

	private String orderNo;//根据订单编号查询
	
	private String afterSaleNo;//根据售后申请单号查询

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAfterSaleNo() {
		return afterSaleNo;
	}

	public void setAfterSaleNo(String afterSaleNo) {
		this.afterSaleNo = afterSaleNo;
	}
	
	
}

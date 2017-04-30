/**
 * 
 */
package com.ffzx.order.api.dto;


 /**
 * @Description: 订单取消状态推送
 * @author zi.luo
 * @email  zi.luo@ffzxnet.com
 * @date 2016年6月7日 下午7:15:31
 * @version V1.0 
 *
 */
public class OutboundDeliveryStatusApiVo {
	private static final long serialVersionUID = 1L;
	
	private String salesOrderNo;
	
	private Integer status;

	public String getSalesOrderNo() {
		return salesOrderNo;
	}

	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}

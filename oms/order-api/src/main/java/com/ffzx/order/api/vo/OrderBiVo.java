package com.ffzx.order.api.vo;

import java.io.Serializable;

/***
 * 配送APP销量报表Vo
 * @author ying.cai
 * @date 2016年6月15日 下午4:56:06
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public class OrderBiVo implements Serializable{
	private String month;//统计月份
	
	private String salesTotal;//总销售金额
	
	private String refundTotal;//总退款金额

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getSalesTotal() {
		return salesTotal;
	}

	public void setSalesTotal(String salesTotal) {
		this.salesTotal = salesTotal;
	}

	public String getRefundTotal() {
		return refundTotal;
	}

	public void setRefundTotal(String refundTotal) {
		this.refundTotal = refundTotal;
	}
	
	
	
}

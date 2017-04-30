package com.ffzx.order.api.dto;

import java.math.BigDecimal;
/**
 * QUEUE_OMS_PURCHASE_ORDER 消息队列vo
 * @author ffzx
 * @date 2016-06-03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class PurchaseOrderDetailVo {
	
	private String code;// 商品sku编号
	private BigDecimal purchaseQuantity;//采购数量
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getPurchaseQuantity() {
		return purchaseQuantity;
	}
	public void setPurchaseQuantity(BigDecimal purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}
	
	
}

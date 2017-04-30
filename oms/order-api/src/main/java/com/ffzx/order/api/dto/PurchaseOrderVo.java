package com.ffzx.order.api.dto;

import java.util.List;

/**
 * QUEUE_OMS_PURCHASE_ORDER 消息队列vo
 * @author ffzx
 * @date 2016-06-03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class PurchaseOrderVo {
	private String supplierId;//供应商id
	private String warehouseCode;//仓库编码
	private List<String> orderNumbers;//订单列表
	private List<PurchaseOrderDetailVo> items;//sku采购集合
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public List<String> getOrderNumbers() {
		return orderNumbers;
	}
	public void setOrderNumbers(List<String> orderNumbers) {
		this.orderNumbers = orderNumbers;
	}
	public List<PurchaseOrderDetailVo> getItems() {
		return items;
	}
	public void setItems(List<PurchaseOrderDetailVo> items) {
		this.items = items;
	}
	
	
}

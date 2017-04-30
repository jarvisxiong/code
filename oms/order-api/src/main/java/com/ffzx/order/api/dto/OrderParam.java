/**
 * 
 */
package com.ffzx.order.api.dto;

import java.io.Serializable;

import com.ffzx.wms.api.dto.Warehouse;

/***
 * 
 * @description 订单管理传参
 * @author richeng
 * @date 2016-8-12 15:58:27
 * @version V1.0
 *
 */
public class OrderParam implements Serializable {

	private static final long serialVersionUID = -8836244169655607338L;
	
	//SKU编号
	private String skuCode;
	
	//商品对象
	private Commodity commodity;
	
	//数量
	private Long number;

	//仓库
	private Warehouse warehouseObject;
	
	//如果是买赠类型，不管是主品还是赠品都置为1
	private String giftType;
	
	//仓库类型 0：中央仓；1：地址绑定仓库
	private String warehouseType;
	
	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public OrderParam() {
		super();
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:加构造
	 * </p>
	 * 
	 * @param skuCode
	 * @param number
	 * @param warehouseCode
	 */
	public OrderParam(String skuCode, Long number, Warehouse warehouseObject) {
		super();
		this.skuCode = skuCode;
		this.number = number;
		this.warehouseObject = warehouseObject;
	}
	
	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Warehouse getWarehouseObject() {
		return warehouseObject;
	}

	public void setWarehouseObject(Warehouse warehouseObject) {
		this.warehouseObject = warehouseObject;
	}

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public String getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(String warehouseType) {
		this.warehouseType = warehouseType;
	}

}

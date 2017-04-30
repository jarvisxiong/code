package com.ffzx.order.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

public class StockWmsUsed extends DataEntity<StockCustom>{

	private static final long serialVersionUID = 1L;

	private Commodity commodity;//商品
	
	private CommoditySku commoditySku;//sku编码
	
	private String warehouseCode; //仓库编码
	
	private String addressCode;//地址编码
	
	private String stockUsedCount; // 已占用数
	
	private String userCanBuy; //用户可购买量
	
	private String actFlag; //禁用状态,0禁用1启用
	
	private StockWms stockWms; //库存自定义id
	
	private String currStockNum;//对应仓库当前库存
	
	public StockWmsUsed(){
	}

	public Commodity getCommodity() {
		if(commodity==null){
			commodity= new Commodity();
		}
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	public CommoditySku getCommoditySku() {
		if(commoditySku==null){
			commoditySku= new CommoditySku();
		}
		return commoditySku;
	}

	public void setCommoditySku(CommoditySku commoditySku) {
		this.commoditySku = commoditySku;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	public String getStockUsedCount() {
		return stockUsedCount;
	}

	public void setStockUsedCount(String stockUsedCount) {
		this.stockUsedCount = stockUsedCount;
	}

	public String getUserCanBuy() {
		return userCanBuy;
	}

	public void setUserCanBuy(String userCanBuy) {
		this.userCanBuy = userCanBuy;
	}

	public String getActFlag() {
		return actFlag;
	}

	public void setActFlag(String actFlag) {
		this.actFlag = actFlag;
	}

	public StockWms getStockWms() {
		return stockWms;
	}

	public void setStockWms(StockWms stockWms) {
		this.stockWms = stockWms;
	}

	public String getCurrStockNum() {
		return currStockNum;
	}

	public void setCurrStockNum(String currStockNum) {
		this.currStockNum = currStockNum;
	}
	
}

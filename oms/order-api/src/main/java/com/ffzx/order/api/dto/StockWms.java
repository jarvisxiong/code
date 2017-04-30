package com.ffzx.order.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

public class StockWms extends DataEntity<StockWms> {

	private static final long serialVersionUID = 1L;
	
	private Commodity commodity;//商品
	
	private CommoditySku commoditySku;//sku编码
	
	private String warehouseCode; //仓库编码
	
	private String warehouseName; //仓库名称
	
	private String addressCode;//地址编码
	
	private String addressName;// 地址名称
	
	private String addressNameOrWarehouseName; //购买区域（县级地址或仓库名）
	
	private String currStockNum;//对应仓库当前库存
	
    private String actFlag; //禁用状态,0启用1禁用
    
    private StockWmsUsed stockWmsUsed; // 库存自定义占用id
    
    public StockWms(){
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

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddressNameOrWarehouseName() {
		return addressNameOrWarehouseName;
	}

	public void setAddressNameOrWarehouseName(String addressNameOrWarehouseName) {
		this.addressNameOrWarehouseName = addressNameOrWarehouseName;
	}

	public String getCurrStockNum() {
		return currStockNum;
	}

	public void setCurrStockNum(String currStockNum) {
		this.currStockNum = currStockNum;
	}

	public String getActFlag() {
		return actFlag;
	}

	public void setActFlag(String actFlag) {
		this.actFlag = actFlag;
	}

	public StockWmsUsed getStockWmsUsed() {
		if(stockWmsUsed==null){
			stockWmsUsed= new StockWmsUsed();
		}
		return stockWmsUsed;
	}

	public void setStockWmsUsed(StockWmsUsed stockWmsUsed) {
		this.stockWmsUsed = stockWmsUsed;
	}
	
}

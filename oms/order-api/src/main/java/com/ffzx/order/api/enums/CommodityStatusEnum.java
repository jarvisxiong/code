package com.ffzx.order.api.enums;

public enum CommodityStatusEnum {
	//商品状态
	COMMODITY_STATUS_ENTRYING("录入中","COMMODITY_STATUS_ENTRYING"),
	COMMODITY_STATUS_ENTRYED("已录入","COMMODITY_STATUS_ENTRYED"),
	COMMODITY_STATUS_ADDED("上架","COMMODITY_STATUS_ADDED"),
	COMMODITY_STATUS_SHELVES("下架","COMMODITY_STATUS_SHELVES"),
	COMMODITY_STATUS_DEL("已删除","COMMODITY_STATUS_DEL");
	
   private String name;
	
	private String value;
	
	private CommodityStatusEnum(String name,String value){
		this.name=name;
		this.value=value;
	}
	
	
	public static CommodityStatusEnum getCommodityStatusByValue(String value){
		for(CommodityStatusEnum commodityStatus:CommodityStatusEnum.values()){
			if(commodityStatus.value.equals(value))
				return commodityStatus;
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

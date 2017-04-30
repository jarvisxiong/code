package com.ffzx.order.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ffzx.commerce.framework.common.persistence.DataEntity;

public class CommoditySwitchLog extends DataEntity<CommoditySwitchLog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Commodity commodity; //商品编码
	
	private String stockLimitBefore; //之前可销售数量限制
	
	private String areaCategoryBefore; //之前的购买区域
	
	private String stockLimitAfter; //之后可销售数量限制
	
	private String areaCategoryAfter;//之后的购买区域
	
	private String handleMsg; //处理信息
	
	private String opUserId;//操作人id
	
	private String opUser;//操作人
	
	
    @JsonIgnore
	public Commodity getCommodity() {
		if(commodity==null){
			commodity=new Commodity();
		}
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	public String getStockLimitBefore() {
		return stockLimitBefore;
	}

	public void setStockLimitBefore(String stockLimitBefore) {
		this.stockLimitBefore = stockLimitBefore;
	}

	public String getAreaCategoryBefore() {
		return areaCategoryBefore;
	}

	public void setAreaCategoryBefore(String areaCategoryBefore) {
		this.areaCategoryBefore = areaCategoryBefore;
	}

	public String getStockLimitAfter() {
		return stockLimitAfter;
	}

	public void setStockLimitAfter(String stockLimitAfter) {
		this.stockLimitAfter = stockLimitAfter;
	}

	public String getAreaCategoryAfter() {
		return areaCategoryAfter;
	}

	public void setAreaCategoryAfter(String areaCategoryAfter) {
		this.areaCategoryAfter = areaCategoryAfter;
	}

	public String getHandleMsg() {
		return handleMsg;
	}

	public void setHandleMsg(String handleMsg) {
		this.handleMsg = handleMsg;
	}

	public String getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(String opUserId) {
		this.opUserId = opUserId;
	}

	public String getOpUser() {
		return opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}
	
}

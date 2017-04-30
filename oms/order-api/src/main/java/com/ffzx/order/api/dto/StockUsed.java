package com.ffzx.order.api.dto;

import java.io.Serializable;

public class StockUsed implements Serializable{
	private static final long serialVersionUID = 7700401789683123958L;

	private String stockUsedCount; // 已占用数
	
	private String userCanBuy; //用户可购买量
	
	private String currStockNum;//对应仓库当前库存

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

	public String getCurrStockNum() {
		return currStockNum;
	}

	public void setCurrStockNum(String currStockNum) {
		this.currStockNum = currStockNum;
	}
	
	
}

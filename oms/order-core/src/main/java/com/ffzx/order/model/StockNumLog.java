package com.ffzx.order.model;

import java.util.Date;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

public class StockNumLog extends DataEntity<StockNumLog>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date handleDate; // 处理时间
	
	private String handleMsg; //处理信息
	
	private String opUserId;//操作人id
	
	private String opUser;//操作人
	
	private String stockCustomId;//sku编码

	public Date getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
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

	public String getStockCustomId() {
		return stockCustomId;
	}

	public void setStockCustomId(String stockCustomId) {
		this.stockCustomId = stockCustomId;
	}
	
}

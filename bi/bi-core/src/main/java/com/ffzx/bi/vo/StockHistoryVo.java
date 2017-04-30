package com.ffzx.bi.vo;

import java.util.List;

import com.ffzx.bi.model.StockHistory;

/**
 * 
 * @author ffzx
 * @date 2016-08-12 11:32:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class StockHistoryVo {
	
	private StockHistory stockHistory;
	
	private StockHistoryCustom stockHistoryCustom;
	
	private List<String> vendorCodeList;
	
	private List<String> skuBarcodeList;
	
	private List<String> categoryIdList;
	
	private List<String> dateList;

	public StockHistory getStockHistory() {
		return stockHistory;
	}

	public void setStockHistory(StockHistory stockHistory) {
		this.stockHistory = stockHistory;
	}

	public StockHistoryCustom getStockHistoryCustom() {
		return stockHistoryCustom;
	}

	public void setStockHistoryCustom(StockHistoryCustom stockHistoryCustom) {
		this.stockHistoryCustom = stockHistoryCustom;
	}

	public List<String> getVendorCodeList() {
		return vendorCodeList;
	}

	public void setVendorCodeList(List<String> vendorCodeList) {
		this.vendorCodeList = vendorCodeList;
	}

	public List<String> getSkuBarcodeList() {
		return skuBarcodeList;
	}

	public void setSkuBarcodeList(List<String> skuBarcodeList) {
		this.skuBarcodeList = skuBarcodeList;
	}

	public List<String> getDateList() {
		return dateList;
	}

	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}

	public List<String> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(List<String> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}
}
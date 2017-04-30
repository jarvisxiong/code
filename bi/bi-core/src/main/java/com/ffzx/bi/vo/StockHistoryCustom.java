package com.ffzx.bi.vo;

import com.ffzx.bi.model.StockHistory;

/**
 * 历史库存扩展类
 */
public class StockHistoryCustom extends StockHistory {
	
	private String totleStockNum;
	/*
	 * 搜索时间(开始时间)
	 */
	private String searchMinDate;
	/*
	 * 搜索时间(结束时间)
	 */
	private String searchMaxDate;
	/*
	 * 是否按供应商分组
	 */
	private String groupByVendor;
	/*
	 * 是否按商品分类分组
	 */
	private String groupByCategory;
	/*
	 * 是否选中条形码
	 */
	private String isCheckedBarcode;
	/*
	 * 多个供应商拼接的字符串
	 */
	private String vendorCodeArrStr;
	/**
	 * 日期
	 */
	private String date;
	/**
	 * 分类级别 
	 */
	private String categoryLevel;
	
	public String getSearchMinDate() {
		return searchMinDate;
	}

	public void setSearchMinDate(String searchMinDate) {
		this.searchMinDate = searchMinDate;
	}

	public String getSearchMaxDate() {
		return searchMaxDate;
	}

	public void setSearchMaxDate(String searchMaxDate) {
		this.searchMaxDate = searchMaxDate;
	}

	public String getGroupByVendor() {
		return groupByVendor;
	}

	public void setGroupByVendor(String groupByVendor) {
		this.groupByVendor = groupByVendor;
	}

	public String getGroupByCategory() {
		return groupByCategory;
	}

	public void setGroupByCategory(String groupByCategory) {
		this.groupByCategory = groupByCategory;
	}

	public String getIsCheckedBarcode() {
		return isCheckedBarcode;
	}

	public void setIsCheckedBarcode(String isCheckedBarcode) {
		this.isCheckedBarcode = isCheckedBarcode;
	}

	public String getVendorCodeArrStr() {
		return vendorCodeArrStr;
	}

	public void setVendorCodeArrStr(String vendorCodeArrStr) {
		this.vendorCodeArrStr = vendorCodeArrStr;
	}

	public String getTotleStockNum() {
		return totleStockNum;
	}

	public void setTotleStockNum(String totleStockNum) {
		this.totleStockNum = totleStockNum;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(String categoryLevel) {
		this.categoryLevel = categoryLevel;
	}
}
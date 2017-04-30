package com.ffzx.bi.vo;

import java.math.BigDecimal;

import com.ffzx.bi.model.GoodsArrival;

/**
 * 
 * @author ffzx
 * @date 2016-09-01 14:45:34
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class GoodsArrivalCustom extends GoodsArrival {
	/*
	 * 退货率
	 */
	private String refundRate;
	/*
	 * 到货率
	 */
	private String arrivalRate;
	/*
	 * 良品率
	 */
	private String goodProductsRate;
	/*
	 * 搜索时间(开始时间)
	 */
	private String searchMinDate;
	/*
	 * 搜索时间(结束时间)
	 */
	private String searchMaxDate;
	/*
	 * 是否选中条形码
	 */
	private String isCheckedBarcode;
	/*
	 * 是否选中供应商
	 */
	private String isCheckedVendor;
	/**
	 * 总的采购数量
	 */
	private BigDecimal totalPurchaseQuantity;
	/**
	 * 总的到货数量
	 */
	private BigDecimal totalReceivingQuantity;
	/**
	 * 总的质检良品数
	 */
	private BigDecimal totalcStorageQuantity;
	/**
	 * 总的退货数
	 */
	private BigDecimal totalcRejectedQuantity;
	
	public String getRefundRate() {
		return refundRate == null ? "0%" : refundRate;
	}

	public void setRefundRate(String refundRate) {
		this.refundRate = refundRate;
	}

	public String getArrivalRate() {
		return arrivalRate == null ? "0%" : arrivalRate;
	}

	public void setArrivalRate(String arrivalRate) {
		this.arrivalRate = arrivalRate;
	}

	public String getGoodProductsRate() {
		return goodProductsRate == null ? "0%" : goodProductsRate;
	}

	public void setGoodProductsRate(String goodProductsRate) {
		this.goodProductsRate = goodProductsRate;
	}

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

	public String getIsCheckedBarcode() {
		return isCheckedBarcode;
	}

	public void setIsCheckedBarcode(String isCheckedBarcode) {
		this.isCheckedBarcode = isCheckedBarcode;
	}

	public String getIsCheckedVendor() {
		return isCheckedVendor;
	}

	public void setIsCheckedVendor(String isCheckedVendor) {
		this.isCheckedVendor = isCheckedVendor;
	}

	public BigDecimal getTotalPurchaseQuantity() {
		return totalPurchaseQuantity;
	}

	public void setTotalPurchaseQuantity(BigDecimal totalPurchaseQuantity) {
		this.totalPurchaseQuantity = totalPurchaseQuantity;
	}

	public BigDecimal getTotalReceivingQuantity() {
		return totalReceivingQuantity == null ? BigDecimal.ZERO : totalReceivingQuantity;
	}

	public void setTotalReceivingQuantity(BigDecimal totalReceivingQuantity) {
		this.totalReceivingQuantity = totalReceivingQuantity;
	}

	public BigDecimal getTotalcStorageQuantity() {
		return totalcStorageQuantity == null ? BigDecimal.ZERO : totalcStorageQuantity;
	}

	public void setTotalcStorageQuantity(BigDecimal totalcStorageQuantity) {
		this.totalcStorageQuantity = totalcStorageQuantity;
	}

	public BigDecimal getTotalcRejectedQuantity() {
		return totalcRejectedQuantity == null ? BigDecimal.ZERO : totalcRejectedQuantity;
	}

	public void setTotalcRejectedQuantity(BigDecimal totalcRejectedQuantity) {
		this.totalcRejectedQuantity = totalcRejectedQuantity;
	}
}
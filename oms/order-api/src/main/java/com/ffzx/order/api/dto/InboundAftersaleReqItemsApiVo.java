package com.ffzx.order.api.dto;

import java.util.Date;
import java.util.List;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

public class InboundAftersaleReqItemsApiVo extends DataEntity<InboundAftersaleReqItemsApiVo> {
	private static final long serialVersionUID = 1L;

	private String commoditySkuId;// 商品ID
	private String commodityName;// 商品名称
	private String commodityCode;// 商品编码
	private String commoditySkuBarcode;// SKU条码
	private String commoditySkuCode;// SKU编码
	private String saleAttributeValues;// 规格
	private String unit;// 计量单位
	private String aftersaleCount;// 退货数量
	/**
	 * 雷------2016年10月15日
	 * 
	 * @Title: commodityAttributeValues
	 * @Description: 商品属性值
	 */
	private String commodityAttributeValues;

	public String getCommoditySkuId() {
		return commoditySkuId;
	}

	public void setCommoditySkuId(String commoditySkuId) {
		this.commoditySkuId = commoditySkuId;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public String getCommoditySkuBarcode() {
		return commoditySkuBarcode;
	}

	public void setCommoditySkuBarcode(String commoditySkuBarcode) {
		this.commoditySkuBarcode = commoditySkuBarcode;
	}

	public String getCommoditySkuCode() {
		return commoditySkuCode;
	}

	public void setCommoditySkuCode(String commoditySkuCode) {
		this.commoditySkuCode = commoditySkuCode;
	}

	public String getSaleAttributeValues() {
		return saleAttributeValues;
	}

	public void setSaleAttributeValues(String saleAttributeValues) {
		this.saleAttributeValues = saleAttributeValues;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getAftersaleCount() {
		return aftersaleCount;
	}

	public void setAftersaleCount(String aftersaleCount) {
		this.aftersaleCount = aftersaleCount;
	}

	/**
	 * 雷----2016年10月15日
	 * 
	 * @return commodityAttributeValues
	 */
	public String getCommodityAttributeValues() {
		return commodityAttributeValues;
	}

	/**
	 * 雷-------2016年10月15日
	 * 
	 * @param commodityAttributeValues
	 *            要设置的 commodityAttributeValues
	 */
	public void setCommodityAttributeValues(String commodityAttributeValues) {
		this.commodityAttributeValues = commodityAttributeValues;
	}

}

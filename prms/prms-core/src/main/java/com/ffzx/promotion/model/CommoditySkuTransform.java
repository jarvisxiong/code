/**
 * 
 */
package com.ffzx.promotion.model;

import java.math.BigDecimal;

/**
 * @Description: 商品系统查询出sku转换类
 * @author zi.luo
 * @email  zi.luo@ffzxnet.com
 * @date 2016年6月13日 下午3:49:31
 * @version V1.0 
 *
 */
public class CommoditySkuTransform {

	private String commodityAttributeValues;   //商品辅助属性
	
	private String barcode;     //商品条码
	
	private BigDecimal favourablePrice;     //优惠价
	 
	private String id;             //商品skuId
	
	private String skuCode;      
	
	private String userCanBuy; //用户可购买量
	
	private BigDecimal activityPrice;  //活动优惠价
	
	private Integer limitCount;   //限定数量
	
	private String commoditySkuTitle;   //商品sku标题

	public String getCommodityAttributeValues() {
		return commodityAttributeValues;
	}

	public void setCommodityAttributeValues(String commodityAttributeValues) {
		this.commodityAttributeValues = commodityAttributeValues;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public BigDecimal getFavourablePrice() {
		return favourablePrice;
	}

	public void setFavourablePrice(BigDecimal favourablePrice) {
		this.favourablePrice = favourablePrice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getUserCanBuy() {
		return userCanBuy;
	}

	public void setUserCanBuy(String userCanBuy) {
		this.userCanBuy = userCanBuy;
	}

	public BigDecimal getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(BigDecimal activityPrice) {
		this.activityPrice = activityPrice;
	}

	public Integer getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}

	public String getCommoditySkuTitle() {
		return commoditySkuTitle;
	}

	public void setCommoditySkuTitle(String commoditySkuTitle) {
		this.commoditySkuTitle = commoditySkuTitle;
	}
	
	
}

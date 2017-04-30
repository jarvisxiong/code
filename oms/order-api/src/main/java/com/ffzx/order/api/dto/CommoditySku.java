package com.ffzx.order.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.math.BigDecimal;

/**
 * 
 * @author 商品sku
 * @date 2016-05-13 09:34:39
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class CommoditySku extends DataEntity<CommoditySku> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品编码.
     */
    private String commodityCode;

    /**
     * sku code.
     */
    private String skuCode;

    /**
     * 条形码.
     */
    private String barcode;

    /**
     * 是否显示，1:显示,0:不显示.
     */
    private String isShow;

    /**
     * sku价格.
     */
    private BigDecimal price;
    
    /**
     * 批发价格.
     */
    private BigDecimal tradePriceSku;

    /**
     * 优惠价.
     */
    private BigDecimal favourablePrice;

    /**
     * 排序.
     */
    private Integer sort;

    /**
     * 状态，预售:pre_sale,正常销售:nomal_sale.
     */
    private String status;

    /**
     * 禁用状态,0启用1禁用.
     */
    private String actFlag;

    /**
     * 销售属性值id组合,用逗号隔开.
     */
    private String commodityAttributeValueIds;

    /**
     * 销售属性值组合.
     */
    private String commodityAttributeValues;

    /**
     * 销售属性id组合.
     */
    private String commodityAttributeIds;

    /**
     * 销售属性组合.
     */
    private String commodityAttributes;

    /**
     * 扣点方案id.
     */
    private byte[] discountPlanId;
     
    /**
     * 冗余属性  该字段不做持久化操作
     */
    private StockUsed stockUsed;
    /**
     * 冗余属性  该字段不做持久化操作
     */
    private Commodity commodity;
    
    /**
     * sku商品展示图.
     */
    private String smallImage;
    /**
     * 
     * {@linkplain #commodityCode}
     *
     * @return the value of commodity_sku.commodity_code
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * {@linkplain #commodityCode}
     * @param commodityCode the value for commodity_sku.commodity_code
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode == null ? null : commodityCode.trim();
    }

    /**
     * 
     * {@linkplain #skuCode}
     *
     * @return the value of commodity_sku.sku_code
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * {@linkplain #skuCode}
     * @param skuCode the value for commodity_sku.sku_code
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    /**
     * 
     * {@linkplain #barcode}
     *
     * @return the value of commodity_sku.barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * {@linkplain #barcode}
     * @param barcode the value for commodity_sku.barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    /**
     * 
     * {@linkplain #isShow}
     *
     * @return the value of commodity_sku.is_show
     */
    public String getIsShow() {
        return isShow;
    }

    /**
     * {@linkplain #isShow}
     * @param isShow the value for commodity_sku.is_show
     */
    public void setIsShow(String isShow) {
        this.isShow = isShow == null ? null : isShow.trim();
    }

    /**
     * 
     * {@linkplain #price}
     *
     * @return the value of commodity_sku.price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * {@linkplain #price}
     * @param price the value for commodity_sku.price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 
     * {@linkplain #favourablePrice}
     *
     * @return the value of commodity_sku.favourable_price
     */
    public BigDecimal getFavourablePrice() {
        return favourablePrice;
    }

    /**
     * {@linkplain #favourablePrice}
     * @param favourablePrice the value for commodity_sku.favourable_price
     */
    public void setFavourablePrice(BigDecimal favourablePrice) {
        this.favourablePrice = favourablePrice;
    }

    /**
     * 
     * {@linkplain #sort}
     *
     * @return the value of commodity_sku.sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * {@linkplain #sort}
     * @param sort the value for commodity_sku.sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of commodity_sku.status
     */
    public String getStatus() {
        return status;
    }

    /**
     * {@linkplain #status}
     * @param status the value for commodity_sku.status
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 
     * {@linkplain #actFlag}
     *
     * @return the value of commodity_sku.act_flag
     */
    public String getActFlag() {
        return actFlag;
    }

    /**
     * {@linkplain #actFlag}
     * @param actFlag the value for commodity_sku.act_flag
     */
    public void setActFlag(String actFlag) {
        this.actFlag = actFlag == null ? null : actFlag.trim();
    }

    /**
     * 
     * {@linkplain #commodityAttributeValueIds}
     *
     * @return the value of commodity_sku.commodity_attribute_value_ids
     */
    public String getCommodityAttributeValueIds() {
        return commodityAttributeValueIds;
    }

    /**
     * {@linkplain #commodityAttributeValueIds}
     * @param commodityAttributeValueIds the value for commodity_sku.commodity_attribute_value_ids
     */
    public void setCommodityAttributeValueIds(String commodityAttributeValueIds) {
        this.commodityAttributeValueIds = commodityAttributeValueIds == null ? null : commodityAttributeValueIds.trim();
    }

    /**
     * 
     * {@linkplain #commodityAttributeValues}
     *
     * @return the value of commodity_sku.commodity_attribute_values
     */
    public String getCommodityAttributeValues() {
        return commodityAttributeValues;
    }

    /**
     * {@linkplain #commodityAttributeValues}
     * @param commodityAttributeValues the value for commodity_sku.commodity_attribute_values
     */
    public void setCommodityAttributeValues(String commodityAttributeValues) {
        this.commodityAttributeValues = commodityAttributeValues == null ? null : commodityAttributeValues.trim();
    }

    /**
     * 
     * {@linkplain #commodityAttributeIds}
     *
     * @return the value of commodity_sku.commodity_attribute_ids
     */
    public String getCommodityAttributeIds() {
        return commodityAttributeIds;
    }

    /**
     * {@linkplain #commodityAttributeIds}
     * @param commodityAttributeIds the value for commodity_sku.commodity_attribute_ids
     */
    public void setCommodityAttributeIds(String commodityAttributeIds) {
        this.commodityAttributeIds = commodityAttributeIds == null ? null : commodityAttributeIds.trim();
    }

    /**
     * 
     * {@linkplain #commodityAttributes}
     *
     * @return the value of commodity_sku.commodity_attributes
     */
    public String getCommodityAttributes() {
        return commodityAttributes;
    }

    /**
     * {@linkplain #commodityAttributes}
     * @param commodityAttributes the value for commodity_sku.commodity_attributes
     */
    public void setCommodityAttributes(String commodityAttributes) {
        this.commodityAttributes = commodityAttributes == null ? null : commodityAttributes.trim();
    }

    /**
     * 
     * {@linkplain #discountPlanId}
     *
     * @return the value of commodity_sku.discount_plan_id
     */
    public byte[] getDiscountPlanId() {
        return discountPlanId;
    }

    /**
     * {@linkplain #discountPlanId}
     * @param discountPlanId the value for commodity_sku.discount_plan_id
     */
    public void setDiscountPlanId(byte[] discountPlanId) {
        this.discountPlanId = discountPlanId;
    }

	public StockUsed getStockUsed() {
		return stockUsed;
	}

	public void setStockUsed(StockUsed stockUsed) {
		this.stockUsed = stockUsed;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	/**
	 * @return the tradePriceSku
	 */
	public BigDecimal getTradePriceSku() {
		return tradePriceSku;
	}

	/**
	 * @param tradePriceSku the tradePriceSku to set
	 */
	public void setTradePriceSku(BigDecimal tradePriceSku) {
		this.tradePriceSku = tradePriceSku;
	}
	
}
package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author ffzx
 * @date 2016-05-18 10:12:54
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class Commodity extends DataEntity<Commodity> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品编码.
     */
    private String code;

    /**
     * 商品名称.
     */
    private String name;

    /**
     * 商品别名.
     */
    private String aliasName;

    /**
     * 商品标题.
     */
    private String title;

    /**
     * 商品副标题.
     */
    private String subTitle;

    /**
     * 商品类别id.
     */
    private String categoryId;

    /**
     * 商品属性.
     */
    private String commodityAttributeParameter;

    /**
     * 品牌id.
     */
    private String brandId;

    /**
     * 品牌名称.
     */
    private String brandName;

    /**
     * 供应商id.
     */
    private String vendorId;

    /**
     * 供应商名称.
     */
    private String vendorName;

    /**
     * 零售价.
     */
    private BigDecimal retailPrice;

    /**
     * 优惠价.
     */
    private String preferentialPrice;

    /**
     * 商品计量单位id.
     */
    private String unitId;

    /**
     * 是否区域性商品（0:是，1：否）.
     */
    private String areaCategory;

    /**
     * 0：WMS决定商品可销售数量；1：自定义商品可销售数量.
     */
    private String stockLimit;

    /**
     * 是否拆单（0：是；1：否）.
     */
    private String splitSingle;

    /**
     * 是否在供应商仓库（0：是;1:否）.
     */
    private String warehouse;

    /**
     * 常见的状态有,draft:草稿、un_publish:未发布、publish:发布等.
     */
    private String status;

    /**
     * 商品描述.
     */
    private String description;

    /**
     * 商品图标.
     */
    private String thumbnail;

    /**
     * 商品展示图.
     */
    private String smallImage;

    /**
     * 商品详情图.
     */
    private String bigImage;

    /**
     * 搜索关键词.
     */
    private String keyword;

    /**
     * 是否热销,1:热销,0:不热销.
     */
    private String isHotSale;

    /**
     * 是否新品,1:新品,0:非新品.
     */
    private String isNew;

    /**
     * 发布时间.
     */
    private Date publishTime;

    /**
     * 实物是否入库(0:是，1：否).
     */
    private String entityStorage;

    /**
     * 购买类型.
     */
    private String buyType;

    /**
     * 商品计量单位名称.
     */
    private String unitName;

    /**
     * 商品计量单位组Id.
     */
    private String unitGroupId;

    /**
     * sku编码.
     */
    private String skuCode;

    /**
     * 条形码.
     */
    private String barCode;

    /**
     * 商品规格.
     */
    private String specification;

    /**
     * 商品类别名称.
     */
    private String categoryAttributeName;
    /**
     * 商品类别名称.
     */
    private String categoryattributeName;

    /**
     * 销售属性名称组合,用逗号隔开.
     */
    private String saleAttributeName;

    /**
     * 销售属性组合.
     */
    private String saleAttributeIds;
    
    /**
     * 买赠类型  0:未参加任何买赠活动 ， 1:主商品 ， 2:赠品
     */
    private String giveType;
    //////冗余字段////////////
    private CommoditySku commoditySku;
    

    public CommoditySku getCommoditySku() {
		return commoditySku;
	}

	public void setCommoditySku(CommoditySku commoditySku) {
		this.commoditySku = commoditySku;
	}

	/**
     * 
     * {@linkplain #code}
     *
     * @return the value of commodity.code
     */
    public String getCode() {
        return code;
    }

    /**
     * {@linkplain #code}
     * @param code the value for commodity.code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCategoryAttributeName() {
		return categoryAttributeName;
	}

	public void setCategoryAttributeName(String categoryAttributeName) {
		this.categoryAttributeName = categoryAttributeName;
	}

	/**
     * 
     * {@linkplain #name}
     *
     * @return the value of commodity.name
     */
    public String getName() {
        return name;
    }

    /**
     * {@linkplain #name}
     * @param name the value for commodity.name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * {@linkplain #aliasName}
     *
     * @return the value of commodity.alias_name
     */
    public String getAliasName() {
        return aliasName;
    }

    /**
     * {@linkplain #aliasName}
     * @param aliasName the value for commodity.alias_name
     */
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName == null ? null : aliasName.trim();
    }

    /**
     * 
     * {@linkplain #title}
     *
     * @return the value of commodity.title
     */
    public String getTitle() {
        return title;
    }

    /**
     * {@linkplain #title}
     * @param title the value for commodity.title
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 
     * {@linkplain #subTitle}
     *
     * @return the value of commodity.sub_title
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     * {@linkplain #subTitle}
     * @param subTitle the value for commodity.sub_title
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    /**
     * 
     * {@linkplain #categoryId}
     *
     * @return the value of commodity.category_id
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * {@linkplain #categoryId}
     * @param categoryId the value for commodity.category_id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    /**
     * 
     * {@linkplain #commodityAttributeParameter}
     *
     * @return the value of commodity.commodity_attribute_parameter
     */
    public String getCommodityAttributeParameter() {
        return commodityAttributeParameter;
    }

    /**
     * {@linkplain #commodityAttributeParameter}
     * @param commodityAttributeParameter the value for commodity.commodity_attribute_parameter
     */
    public void setCommodityAttributeParameter(String commodityAttributeParameter) {
        this.commodityAttributeParameter = commodityAttributeParameter == null ? null : commodityAttributeParameter.trim();
    }

    /**
     * 
     * {@linkplain #brandId}
     *
     * @return the value of commodity.brand_id
     */
    public String getBrandId() {
        return brandId;
    }

    /**
     * {@linkplain #brandId}
     * @param brandId the value for commodity.brand_id
     */
    public void setBrandId(String brandId) {
        this.brandId = brandId == null ? null : brandId.trim();
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of commodity.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * {@linkplain #brandName}
     * @param brandName the value for commodity.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    /**
     * 
     * {@linkplain #vendorId}
     *
     * @return the value of commodity.vendor_id
     */
    public String getVendorId() {
        return vendorId;
    }

    /**
     * {@linkplain #vendorId}
     * @param vendorId the value for commodity.vendor_id
     */
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId == null ? null : vendorId.trim();
    }

    /**
     * 
     * {@linkplain #vendorName}
     *
     * @return the value of commodity.vendor_name
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * {@linkplain #vendorName}
     * @param vendorName the value for commodity.vendor_name
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName == null ? null : vendorName.trim();
    }

    /**
     * 
     * {@linkplain #retailPrice}
     *
     * @return the value of commodity.retail_price
     */
    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    /**
     * {@linkplain #retailPrice}
     * @param retailPrice the value for commodity.retail_price
     */
    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    /**
     * 
     * {@linkplain #preferentialPrice}
     *
     * @return the value of commodity.preferential_price
     */


    /**
     * 
     * {@linkplain #unitId}
     *
     * @return the value of commodity.unit_id
     */
    public String getUnitId() {
        return unitId;
    }

    public String getPreferentialPrice() {
		return preferentialPrice;
	}

	public void setPreferentialPrice(String preferentialPrice) {
		this.preferentialPrice = preferentialPrice;
	}

	/**
     * {@linkplain #unitId}
     * @param unitId the value for commodity.unit_id
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 
     * {@linkplain #areaCategory}
     *
     * @return the value of commodity.area_category
     */
    public String getAreaCategory() {
        return areaCategory;
    }

    /**
     * {@linkplain #areaCategory}
     * @param areaCategory the value for commodity.area_category
     */
    public void setAreaCategory(String areaCategory) {
        this.areaCategory = areaCategory == null ? null : areaCategory.trim();
    }

    /**
     * 
     * {@linkplain #stockLimit}
     *
     * @return the value of commodity.stock_limit
     */
    public String getStockLimit() {
        return stockLimit;
    }

    /**
     * {@linkplain #stockLimit}
     * @param stockLimit the value for commodity.stock_limit
     */
    public void setStockLimit(String stockLimit) {
        this.stockLimit = stockLimit == null ? null : stockLimit.trim();
    }

    /**
     * 
     * {@linkplain #splitSingle}
     *
     * @return the value of commodity.split_single
     */
    public String getSplitSingle() {
        return splitSingle;
    }

    /**
     * {@linkplain #splitSingle}
     * @param splitSingle the value for commodity.split_single
     */
    public void setSplitSingle(String splitSingle) {
        this.splitSingle = splitSingle == null ? null : splitSingle.trim();
    }

    /**
     * 
     * {@linkplain #warehouse}
     *
     * @return the value of commodity.warehouse
     */
    public String getWarehouse() {
        return warehouse;
    }

    /**
     * {@linkplain #warehouse}
     * @param warehouse the value for commodity.warehouse
     */
    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse == null ? null : warehouse.trim();
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of commodity.status
     */
    public String getStatus() {
        return status;
    }

    /**
     * {@linkplain #status}
     * @param status the value for commodity.status
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 
     * {@linkplain #description}
     *
     * @return the value of commodity.description
     */
    public String getDescription() {
        return description;
    }

    /**
     * {@linkplain #description}
     * @param description the value for commodity.description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 
     * {@linkplain #thumbnail}
     *
     * @return the value of commodity.thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * {@linkplain #thumbnail}
     * @param thumbnail the value for commodity.thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail == null ? null : thumbnail.trim();
    }

    /**
     * 
     * {@linkplain #smallImage}
     *
     * @return the value of commodity.small_image
     */
    public String getSmallImage() {
        return smallImage;
    }

    /**
     * {@linkplain #smallImage}
     * @param smallImage the value for commodity.small_image
     */
    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage == null ? null : smallImage.trim();
    }

    /**
     * 
     * {@linkplain #bigImage}
     *
     * @return the value of commodity.big_image
     */
    public String getBigImage() {
        return bigImage;
    }

    /**
     * {@linkplain #bigImage}
     * @param bigImage the value for commodity.big_image
     */
    public void setBigImage(String bigImage) {
        this.bigImage = bigImage == null ? null : bigImage.trim();
    }

    /**
     * 
     * {@linkplain #keyword}
     *
     * @return the value of commodity.keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * {@linkplain #keyword}
     * @param keyword the value for commodity.keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    /**
     * 
     * {@linkplain #isHotSale}
     *
     * @return the value of commodity.is_hot_sale
     */
    public String getIsHotSale() {
        return isHotSale;
    }

    /**
     * {@linkplain #isHotSale}
     * @param isHotSale the value for commodity.is_hot_sale
     */
    public void setIsHotSale(String isHotSale) {
        this.isHotSale = isHotSale == null ? null : isHotSale.trim();
    }

    /**
     * 
     * {@linkplain #isNew}
     *
     * @return the value of commodity.is_new
     */
    public String getIsNew() {
        return isNew;
    }

    /**
     * {@linkplain #isNew}
     * @param isNew the value for commodity.is_new
     */
    public void setIsNew(String isNew) {
        this.isNew = isNew == null ? null : isNew.trim();
    }

    /**
     * 
     * {@linkplain #publishTime}
     *
     * @return the value of commodity.publish_time
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * {@linkplain #publishTime}
     * @param publishTime the value for commodity.publish_time
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 
     * {@linkplain #entityStorage}
     *
     * @return the value of commodity.entity_storage
     */
    public String getEntityStorage() {
        return entityStorage;
    }

    /**
     * {@linkplain #entityStorage}
     * @param entityStorage the value for commodity.entity_storage
     */
    public void setEntityStorage(String entityStorage) {
        this.entityStorage = entityStorage == null ? null : entityStorage.trim();
    }

    /**
     * 
     * {@linkplain #buyType}
     *
     * @return the value of commodity.buy_type
     */
    public String getBuyType() {
        return buyType;
    }

    /**
     * {@linkplain #buyType}
     * @param buyType the value for commodity.buy_type
     */
    public void setBuyType(String buyType) {
        this.buyType = buyType == null ? null : buyType.trim();
    }

    /**
     * 
     * {@linkplain #unitName}
     *
     * @return the value of commodity.unit_name
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * {@linkplain #unitName}
     * @param unitName the value for commodity.unit_name
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    /**
     * 
     * {@linkplain #unitGroupId}
     *
     * @return the value of commodity.unit_group_id
     */
    public String getUnitGroupId() {
        return unitGroupId;
    }

    /**
     * {@linkplain #unitGroupId}
     * @param unitGroupId the value for commodity.unit_group_id
     */
    public void setUnitGroupId(String unitGroupId) {
        this.unitGroupId = unitGroupId == null ? null : unitGroupId.trim();
    }

    /**
     * 
     * {@linkplain #skuCode}
     *
     * @return the value of commodity.sku_code
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * {@linkplain #skuCode}
     * @param skuCode the value for commodity.sku_code
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    /**
     * 
     * {@linkplain #barCode}
     *
     * @return the value of commodity.bar_code
     */
    public String getBarCode() {
        return barCode;
    }

    /**
     * {@linkplain #barCode}
     * @param barCode the value for commodity.bar_code
     */
    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    /**
     * 
     * {@linkplain #specification}
     *
     * @return the value of commodity.specification
     */
    public String getSpecification() {
        return specification;
    }

    /**
     * {@linkplain #specification}
     * @param specification the value for commodity.specification
     */
    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    /**
     * 
     * {@linkplain #categoryattributeName}
     *
     * @return the value of commodity.categoryAttribute_name
     */
    public String getCategoryattributeName() {
        return categoryattributeName;
    }

    /**
     * {@linkplain #categoryattributeName}
     * @param categoryattributeName the value for commodity.categoryAttribute_name
     */
    public void setCategoryattributeName(String categoryattributeName) {
        this.categoryattributeName = categoryattributeName == null ? null : categoryattributeName.trim();
    }

    /**
     * 
     * {@linkplain #saleAttributeName}
     *
     * @return the value of commodity.sale_attribute_name
     */
    public String getSaleAttributeName() {
        return saleAttributeName;
    }

    /**
     * {@linkplain #saleAttributeName}
     * @param saleAttributeName the value for commodity.sale_attribute_name
     */
    public void setSaleAttributeName(String saleAttributeName) {
        this.saleAttributeName = saleAttributeName == null ? null : saleAttributeName.trim();
    }

    /**
     * 
     * {@linkplain #saleAttributeIds}
     *
     * @return the value of commodity.sale_attribute_ids
     */
    public String getSaleAttributeIds() {
        return saleAttributeIds;
    }

    /**
     * {@linkplain #saleAttributeIds}
     * @param saleAttributeIds the value for commodity.sale_attribute_ids
     */
    public void setSaleAttributeIds(String saleAttributeIds) {
        this.saleAttributeIds = saleAttributeIds == null ? null : saleAttributeIds.trim();
    }

	public String getGiveType() {
		return giveType;
	}

	public void setGiveType(String giveType) {
		this.giveType = giveType;
	}
    
    
}
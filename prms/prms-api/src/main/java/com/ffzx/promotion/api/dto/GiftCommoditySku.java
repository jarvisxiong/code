package com.ffzx.promotion.api.dto;

import java.math.BigDecimal;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * 
 * @author ffzx
 * @date 2016-09-18 12:07:18
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class GiftCommoditySku extends DataEntity<GiftCommoditySku> {

    private static final long serialVersionUID = 1L;

    /**
     * 赠品SKU条码.
     */
    private String giftCommoditySkuBarcode;

    /**
     * 赠品限定数量.
     */
    private Integer giftLimtCount;

    /**
     * 买赠 赠品商品skuid
     */
    private String giftCommoditySkuid;
    /**
     * 单次赠送数量.
     */
    private Integer giftCount;

    /**
     * 赠品活动价格.
     */
    private BigDecimal price;

    /**
     * sku编码.
     */
    private String giftCommoditySkuCode;

    /**
     * 外键ID 赠品商品管理ID.
     */
    private String giftCommodityId;
    
    /*
     * 冗余
     */
    private String commodityAttributeValues;//sku属性值
    
    private GiftCommodity giftCommodity;
    
    private String  title;//商品标题
    
    private String thumbnail;//logo
    
    private ActivityGive activityGive;
    
    private String label;////买赠关系标示	若果是买赠关系，若是主品设置为主品标示，若是赠品则设置为关联的主品标示
    
    private String userCanBuy;//可购买量
    
    private Integer lastCount;//剩余限定数量
    
    


	public Integer getLastCount() {
		return lastCount;
	}

	public void setLastCount(Integer lastCount) {
		this.lastCount = lastCount;
	}

	public String getUserCanBuy() {
		return userCanBuy;
	}

	public void setUserCanBuy(String userCanBuy) {
		this.userCanBuy = userCanBuy;
	}

	public String getCommodityAttributeValues() {
		return commodityAttributeValues;
	}

	public void setCommodityAttributeValues(String commodityAttributeValues) {
		this.commodityAttributeValues = commodityAttributeValues;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ActivityGive getActivityGive() {
		return activityGive;
	}

	public void setActivityGive(ActivityGive activityGive) {
		this.activityGive = activityGive;
	}


	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}



	public GiftCommodity getGiftCommodity() {
		return giftCommodity;
	}

	public void setGiftCommodity(GiftCommodity giftCommodity) {
		this.giftCommodity = giftCommodity;
	}

	public String getGiftCommoditySkuid() {
		return giftCommoditySkuid;
	}

	public void setGiftCommoditySkuid(String giftCommoditySkuid) {
		this.giftCommoditySkuid = giftCommoditySkuid;
	}

	/**
     * 
     * {@linkplain #giftCommoditySkuBarcode}
     *
     * @return the value of gift_commodity_sku.gift_commodity_sku_barcode
     */
    public String getGiftCommoditySkuBarcode() {
        return giftCommoditySkuBarcode;
    }

    /**
     * {@linkplain #giftCommoditySkuBarcode}
     * @param giftCommoditySkuBarcode the value for gift_commodity_sku.gift_commodity_sku_barcode
     */
    public void setGiftCommoditySkuBarcode(String giftCommoditySkuBarcode) {
        this.giftCommoditySkuBarcode = giftCommoditySkuBarcode == null ? null : giftCommoditySkuBarcode.trim();
    }

    /**
     * 
     * {@linkplain #giftLimtCount}
     *
     * @return the value of gift_commodity_sku.gift_limt_count
     */
    public Integer getGiftLimtCount() {
        return giftLimtCount;
    }

    /**
     * {@linkplain #giftLimtCount}
     * @param giftLimtCount the value for gift_commodity_sku.gift_limt_count
     */
    public void setGiftLimtCount(Integer giftLimtCount) {
        this.giftLimtCount = giftLimtCount;
    }

    /**
     * 
     * {@linkplain #giftCount}
     *
     * @return the value of gift_commodity_sku.gift_count
     */
    public Integer getGiftCount() {
        return giftCount;
    }

    /**
     * {@linkplain #giftCount}
     * @param giftCount the value for gift_commodity_sku.gift_count
     */
    public void setGiftCount(Integer giftCount) {
        this.giftCount = giftCount;
    }

    /**
     * 
     * {@linkplain #price}
     *
     * @return the value of gift_commodity_sku.price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * {@linkplain #price}
     * @param price the value for gift_commodity_sku.price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 
     * {@linkplain #giftCommoditySkuCode}
     *
     * @return the value of gift_commodity_sku.gift_commodity_sku_code
     */
    public String getGiftCommoditySkuCode() {
        return giftCommoditySkuCode;
    }

    /**
     * {@linkplain #giftCommoditySkuCode}
     * @param giftCommoditySkuCode the value for gift_commodity_sku.gift_commodity_sku_code
     */
    public void setGiftCommoditySkuCode(String giftCommoditySkuCode) {
        this.giftCommoditySkuCode = giftCommoditySkuCode == null ? null : giftCommoditySkuCode.trim();
    }

    /**
     * 
     * {@linkplain #giftCommodityId}
     *
     * @return the value of gift_commodity_sku.gift_commodity_id
     */
    public String getGiftCommodityId() {
        return giftCommodityId;
    }

    /**
     * {@linkplain #giftCommodityId}
     * @param giftCommodityId the value for gift_commodity_sku.gift_commodity_id
     */
    public void setGiftCommodityId(String giftCommodityId) {
        this.giftCommodityId = giftCommodityId == null ? null : giftCommodityId.trim();
    }
}
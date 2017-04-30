package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * 
 * @author ffzx
 * @date 2016-09-12 11:25:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class GiftCommodity extends DataEntity<GiftCommodity> {

    private static final long serialVersionUID = 1L;

    /**
     * 赠品商品ID.
     */
    private String commodityId;

    /**
     * commodity_barcode.
     */
    private String commodityBarcode;

    /**
     * 买赠管理ID.
     */
    private String giveId;

    /**
     * 买赠主商品ID.
     */
    private String giveCommodityId;

    /**
     * 赠品商品编码.
     */
    private String commodityNo;
    
    /*
     * 冗余
     */
    private String title;//赠品商品标题
    private String thumbnail;//赠品商品logo
    
    

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
     * 
     * {@linkplain #commodityId}
     *
     * @return the value of gift_commodity.commodity_id
     */
    public String getCommodityId() {
        return commodityId;
    }

    /**
     * {@linkplain #commodityId}
     * @param commodityId the value for gift_commodity.commodity_id
     */
    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId == null ? null : commodityId.trim();
    }

    /**
     * 
     * {@linkplain #commodityBarcode}
     *
     * @return the value of gift_commodity.commodity_barcode
     */
    public String getCommodityBarcode() {
        return commodityBarcode;
    }

    /**
     * {@linkplain #commodityBarcode}
     * @param commodityBarcode the value for gift_commodity.commodity_barcode
     */
    public void setCommodityBarcode(String commodityBarcode) {
        this.commodityBarcode = commodityBarcode == null ? null : commodityBarcode.trim();
    }

    /**
     * 
     * {@linkplain #giveId}
     *
     * @return the value of gift_commodity.give_id
     */
    public String getGiveId() {
        return giveId;
    }

    /**
     * {@linkplain #giveId}
     * @param giveId the value for gift_commodity.give_id
     */
    public void setGiveId(String giveId) {
        this.giveId = giveId == null ? null : giveId.trim();
    }

    /**
     * 
     * {@linkplain #giveCommodityId}
     *
     * @return the value of gift_commodity.give_commodity_id
     */
    public String getGiveCommodityId() {
        return giveCommodityId;
    }

    /**
     * {@linkplain #giveCommodityId}
     * @param giveCommodityId the value for gift_commodity.give_commodity_id
     */
    public void setGiveCommodityId(String giveCommodityId) {
        this.giveCommodityId = giveCommodityId == null ? null : giveCommodityId.trim();
    }

    /**
     * 
     * {@linkplain #commodityNo}
     *
     * @return the value of gift_commodity.commodity_no
     */
    public String getCommodityNo() {
        return commodityNo;
    }

    /**
     * {@linkplain #commodityNo}
     * @param commodityNo the value for gift_commodity.commodity_no
     */
    public void setCommodityNo(String commodityNo) {
        this.commodityNo = commodityNo == null ? null : commodityNo.trim();
    }
}
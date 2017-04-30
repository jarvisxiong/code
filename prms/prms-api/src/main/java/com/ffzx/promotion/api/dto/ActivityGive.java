package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * 
 * @author ffzx
 * @date 2016-09-12 11:25:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class ActivityGive extends DataEntity<ActivityGive> {

    private static final long serialVersionUID = 1L;

    /**
     * 买赠编码.
     */
    private String giveNo;

    /**
     * 买赠活动标题.
     */
    private String giveTitle;

    /**
     * id限购量
     */
    private Integer idLimit;
    /**
     * 活动备注.
     */
    private String remark;

    /**
     * 赠品类型 0 商品 1 优惠券 2 商品和优惠券.
     */
    private String giftType;

    /**
     * 主商品id.
     */
    private String commodityId;

    /**
     * 主商品条码.
     */
    private String commodityBarcode;

    /**
     * 主商品编码.
     */
    private String commodityCode;

    /**
     * 限定数量.
     */
    private Integer limitCount;

    /**
     * 是否禁用 0 禁用 1 启用.
     */
    private String actFlag;

    /**
     * 创建人名字.
     */
    private String createName;

    /**
     * 触发数量.
     */
    private Integer triggerCount;

    /**
     * 仓库类型 0 中央仓 1地址匹配仓.
     */
    private String storageType;

    /**
     * 冗余字段,赠品sku商品id，以逗号隔开
     */
    private String commodityCommoditySku;
    /**
     * 冗余字段，优惠券id，以逗号隔开
     */
    private String couponAdminList;
    /**
     * 冗余字段，赠品skuid和giftLimtCount，giftCount，限定数量和单次输了逗号隔开  ,;
     * @return
     */
    private String commodityCommoditySkuNum;
    /**
     * 冗余字段，主商品优惠价
     * @return
     */
    private String commodityprice;
    
    /*
     * 冗余主商品已购买数量 默认0件
     */
    private Integer buyCount=0;
    
    private Integer newMemberCount;
    
    
    
    
    public Integer getNewMemberCount() {
		return newMemberCount;
	}

	public void setNewMemberCount(Integer newMemberCount) {
		this.newMemberCount = newMemberCount;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public String getCommodityprice() {
		return commodityprice;
	}

	public void setCommodityprice(String commodityprice) {
		this.commodityprice = commodityprice;
	}

	public String getCommodityCommoditySkuNum() {
		return commodityCommoditySkuNum;
	}

	public void setCommodityCommoditySkuNum(String commodityCommoditySkuNum) {
		this.commodityCommoditySkuNum = commodityCommoditySkuNum;
	}

	public String getCommodityCommoditySku() {
		return commodityCommoditySku;
	}

	public void setCommodityCommoditySku(String commodityCommoditySku) {
		this.commodityCommoditySku = commodityCommoditySku;
	}


	public String getCouponAdminList() {
		return couponAdminList;
	}

	public void setCouponAdminList(String couponAdminList) {
		this.couponAdminList = couponAdminList;
	}

	/**
     * 
     * {@linkplain #giveNo}
     *
     * @return the value of activity_give.give_no
     */
    public String getGiveNo() {
        return giveNo;
    }

    /**
     * {@linkplain #giveNo}
     * @param giveNo the value for activity_give.give_no
     */
    public void setGiveNo(String giveNo) {
        this.giveNo = giveNo == null ? null : giveNo.trim();
    }

    /**
     * 
     * {@linkplain #giveTitle}
     *
     * @return the value of activity_give.give_title
     */
    public String getGiveTitle() {
        return giveTitle;
    }

    /**
     * {@linkplain #giveTitle}
     * @param giveTitle the value for activity_give.give_title
     */
    public void setGiveTitle(String giveTitle) {
        this.giveTitle = giveTitle == null ? null : giveTitle.trim();
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of activity_give.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * {@linkplain #remark}
     * @param remark the value for activity_give.remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 
     * {@linkplain #giftType}
     *
     * @return the value of activity_give.gift_type
     */
    public String getGiftType() {
        return giftType;
    }

    /**
     * {@linkplain #giftType}
     * @param giftType the value for activity_give.gift_type
     */
    public void setGiftType(String giftType) {
        this.giftType = giftType == null ? null : giftType.trim();
    }

    /**
     * 
     * {@linkplain #commodityId}
     *
     * @return the value of activity_give.commodity_id
     */
    public String getCommodityId() {
        return commodityId;
    }

    /**
     * {@linkplain #commodityId}
     * @param commodityId the value for activity_give.commodity_id
     */
    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId == null ? null : commodityId.trim();
    }

    /**
     * 
     * {@linkplain #commodityBarcode}
     *
     * @return the value of activity_give.commodity_barcode
     */
    public String getCommodityBarcode() {
        return commodityBarcode;
    }

    /**
     * {@linkplain #commodityBarcode}
     * @param commodityBarcode the value for activity_give.commodity_barcode
     */
    public void setCommodityBarcode(String commodityBarcode) {
        this.commodityBarcode = commodityBarcode == null ? null : commodityBarcode.trim();
    }

    /**
     * 
     * {@linkplain #commodityCode}
     *
     * @return the value of activity_give.commodity_code
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * {@linkplain #commodityCode}
     * @param commodityCode the value for activity_give.commodity_code
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode == null ? null : commodityCode.trim();
    }

    /**
     * 
     * {@linkplain #limitCount}
     *
     * @return the value of activity_give.limit_count
     */
    public Integer getLimitCount() {
        return limitCount;
    }

    /**
     * {@linkplain #limitCount}
     * @param limitCount the value for activity_give.limit_count
     */
    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    /**
     * 
     * {@linkplain #actFlag}
     *
     * @return the value of activity_give.act_flag
     */
    public String getActFlag() {
        return actFlag;
    }

    /**
     * {@linkplain #actFlag}
     * @param actFlag the value for activity_give.act_flag
     */
    public void setActFlag(String actFlag) {
        this.actFlag = actFlag == null ? null : actFlag.trim();
    }

    /**
     * 
     * {@linkplain #createName}
     *
     * @return the value of activity_give.create_name
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * {@linkplain #createName}
     * @param createName the value for activity_give.create_name
     */
    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    /**
     * 
     * {@linkplain #triggerCount}
     *
     * @return the value of activity_give.trigger_count
     */
    public Integer getTriggerCount() {
        return triggerCount;
    }

    /**
     * {@linkplain #triggerCount}
     * @param triggerCount the value for activity_give.trigger_count
     */
    public void setTriggerCount(Integer triggerCount) {
        this.triggerCount = triggerCount;
    }

    /**
     * 
     * {@linkplain #storageType}
     *
     * @return the value of activity_give.storage_type
     */
    public String getStorageType() {
        return storageType;
    }

    /**
     * {@linkplain #storageType}
     * @param storageType the value for activity_give.storage_type
     */
    public void setStorageType(String storageType) {
        this.storageType = storageType == null ? null : storageType.trim();
    }

	public Integer getIdLimit() {
		return idLimit;
	}

	public void setIdLimit(Integer idLimit) {
		this.idLimit = idLimit;
	}
}
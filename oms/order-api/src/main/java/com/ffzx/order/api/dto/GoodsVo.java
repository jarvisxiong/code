package com.ffzx.order.api.dto;

import java.math.BigDecimal;

import com.ffzx.order.api.enums.BuyTypeEnum;

/***
 * 
 * @description 下单商品购买购买值对象
 * @author ying.cai
 * @email ying.cai@ffzxnet.com
 * @date 2016-5-9 上午11:58:27
 * @version V1.0
 *
 */
public class GoodsVo {
	/***
	 * 商品skuID
	 */
	private String id ;
	
	/***
	 * 购买数量
	 */
	private int count;
	
	/***
	 * 购物车ID
	 */
	private String carId;
	
	/***
	 * 活动数据值 ;比如批发活动ID,活动ID,新用户专享活动ID
	 */
	private String value ; 
	
	/***
	 * 购买类型
	 */
	private BuyTypeEnum buyType ; 
	
	/***
	 * sku 的父商品编码
	 */
	private  String sku_commodityCode;
	/***
	 * 同一批发商品下sku购买总量
	 * 当购买类型为批发【 WHOLESALE_MANAGER】时，由客户端传此参数获取某商品购买sku数量的总和
	 */
	private  int wholeSaleCount;
	/**
	 * 是否指定仓库，0：收货地址对应的中央仓，1：收货地址对应的县仓,后台在判断库存之前使用
	 */
	private String warehouseAppoint;
	/**
	 * 【买赠】明细标示，1：【买赠】主商品明细，2：【买赠】赠品明细，空标示普通明细
	 */
	private String buyGifts;
	/**
	 * 标示：若果是买赠关系，若是主品设置为主品标示，若是赠品则设置为关联的主品标示
	 */
	private String label;
	/**
     * 买赠商品详情id
     */
    private String giftCommodityItemId;
    
    /**
     * 导入成单使用
     */
    private BigDecimal salePrice;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public BuyTypeEnum getBuyType() {
		return buyType;
	}
	public void setBuyType(BuyTypeEnum buyType) {
		this.buyType = buyType;
	}
	public String getSku_commodityCode() {
		return sku_commodityCode;
	}
	public void setSku_commodityCode(String sku_commodityCode) {
		this.sku_commodityCode = sku_commodityCode;
	}
	public int getWholeSaleCount() {
		return wholeSaleCount;
	}
	public void setWholeSaleCount(int wholeSaleCount) {
		this.wholeSaleCount = wholeSaleCount;
	}
	/**
	 * @return the warehouseAppoint
	 */
	public String getWarehouseAppoint() {
		return warehouseAppoint;
	}
	/**
	 * @param warehouseAppoint the warehouseAppoint to set
	 */
	public void setWarehouseAppoint(String warehouseAppoint) {
		this.warehouseAppoint = warehouseAppoint;
	}
	/**
	 * @return the buyGifts
	 */
	public String getBuyGifts() {
		return buyGifts;
	}
	/**
	 * @param buyGifts the buyGifts to set
	 */
	public void setBuyGifts(String buyGifts) {
		this.buyGifts = buyGifts;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the giftCommodityItemId
	 */
	public String getGiftCommodityItemId() {
		return giftCommodityItemId;
	}
	/**
	 * @param giftCommodityItemId the giftCommodityItemId to set
	 */
	public void setGiftCommodityItemId(String giftCommodityItemId) {
		this.giftCommodityItemId = giftCommodityItemId;
	}
	/**
	 * @return the salePrice
	 */
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	/**
	 * @param salePrice the salePrice to set
	 */
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

}

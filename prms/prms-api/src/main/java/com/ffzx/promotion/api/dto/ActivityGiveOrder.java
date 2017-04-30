package com.ffzx.promotion.api.dto;

import java.math.BigDecimal;
import java.util.List;

import com.ffzx.commerce.framework.common.persistence.DataEntity;


 /**
 * @Description: TODO
 * @author zi.luo
 * @email  zi.luo@ffzxnet.com
 * @date 2016年9月22日 下午4:05:04
 * @version V1.0 
 *
 */
public class ActivityGiveOrder extends DataEntity<ActivityGiveOrder> {

	/**
	 * app接口专用 确认下单页面参数定义
	 */
	private static final long serialVersionUID = 976112678373036555L;
	
	private String skuId;//skuId
	private String value;//当为活动商品下单时，请用该字段传递activityId参数以确保能获到改sku商品所对应的活动价格
	private int buycount;//购买数量
	private String buyType;//	购买类型
	private String cartId;//	购物车id
	private int wholeSaleCount;//	同一个批发商品所有sku购买量总和
	private String giveType;//	买赠标示【买赠】明细标示，1：【买赠】主商品明细，2：【买赠】赠品明细，空标示普通明细
	private String imgPath;//	商品logo
	private String title;//	商品标题
	private BigDecimal discountPrice;//商品价格
	private String goodsAttr;//	商品属性组合
	private String label;//买赠关系标示	若果是买赠关系，若是主品设置为主品标示，若是赠品则设置为关联的主品标示
	private List<GiftCommoditySku> giftCommodityList;//赠品集合
	private String goodsId;//商品ID
	private String regionId;//地址ID
	private String warehouseAppoint;  //指定仓库，1：收货地址对应的中央仓，2：收货地址对应的县仓
	private String giveId;
	
	public String getGiveId() {
		return giveId;
	}
	public void setGiveId(String giveId) {
		this.giveId = giveId;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getValue() {
		return value;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public int getBuycount() {
		return buycount;
	}
	public void setBuycount(int buycount) {
		this.buycount = buycount;
	}
	public String getBuyType() {
		return buyType;
	}
	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public int getWholeSaleCount() {
		return wholeSaleCount;
	}
	public void setWholeSaleCount(int wholeSaleCount) {
		this.wholeSaleCount = wholeSaleCount;
	}
	
	public String getGiveType() {
		return giveType;
	}
	public void setGiveType(String giveType) {
		this.giveType = giveType;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	public String getGoodsAttr() {
		return goodsAttr;
	}
	public void setGoodsAttr(String goodsAttr) {
		this.goodsAttr = goodsAttr;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<GiftCommoditySku> getGiftCommodityList() {
		return giftCommodityList;
	}
	public void setGiftCommodityList(List<GiftCommoditySku> giftCommodityList) {
		this.giftCommodityList = giftCommodityList;
	}
	public String getWarehouseAppoint() {
		return warehouseAppoint;
	}
	public void setWarehouseAppoint(String warehouseAppoint) {
		this.warehouseAppoint = warehouseAppoint;
	}
	
	
	
}

package com.ffzx.order.model;

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
	 * sku 的父商品id
	 */
	private  String sku_commodityCode;
	/***
	 * sku 的父商品id
	 */
	private  int wholeSaleCount;
	
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


	
}

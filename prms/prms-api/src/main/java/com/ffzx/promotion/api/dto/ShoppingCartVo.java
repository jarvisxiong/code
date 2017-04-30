package com.ffzx.promotion.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ffzx.commerce.framework.common.persistence.BaseEntity;

/**
 * @Description: 购物车 返回对象
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年6月2日 下午5:23:02
 * @version V1.0 
 *
 */
public class ShoppingCartVo extends BaseEntity<ShoppingCartVo> implements Comparable<ShoppingCartVo>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4383109661414921775L;
	
	private String memberId;//会员ID
	
	private String skuId;//商品sku id
	
	private String skuCode;//商品sku code
	
	private Integer cartNum;//商品数量
	
	private String status;// 标示是否失效(0:正常,1:失效)
	
	private String commodityId;//商品ID
	
	private String activityCommodityId;//活动商品设置ID
	
	private String commodityCode;//商品编码
	
	private String skuAttrs;//属性组合
	
	private String buyType;//商品购买类型
	
	private String activityType;//活动类型
	
	private String commodityTitle;//商品标题
	
	private String commoditySkuPic;//商品图片
	
	private BigDecimal price;//商品售价
	
	private BigDecimal favourablePrice;//优惠价
	
	private int isExistToPutong;//是否上下架 0:下架,1:正常
	
	private Integer batchCount;//批发 起批数量
	
	private Date addDate;//加入购物车时间
	
	private Integer storageNum;//剩余库存数
	
	private List<ShoppingCartVo> voList;//封装list对象
	
	private List<WholeSaleVo> wholeSaleList;//批发vo
	
	private int giveType;//买赠类型
	
	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuAttrs() {
		return skuAttrs;
	}

	public void setSkuAttrs(String skuAttrs) {
		this.skuAttrs = skuAttrs;
	}

	public String getActivityCommodityId() {
		return activityCommodityId;
	}

	public void setActivityCommodityId(String activityCommodityId) {
		this.activityCommodityId = activityCommodityId;
	}

	public List<ShoppingCartVo> getVoList() {
		return voList;
	}

	public void setVoList(List<ShoppingCartVo> voList) {
		this.voList = voList;
	}

	public Integer getStorageNum() {
		return storageNum;
	}

	public void setStorageNum(Integer storageNum) {
		this.storageNum = storageNum;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Integer getCartNum() {
		return cartNum;
	}

	public void setCartNum(Integer cartNum) {
		this.cartNum = cartNum;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Integer getBatchCount() {
		return batchCount;
	}

	public void setBatchCount(Integer batchCount) {
		this.batchCount = batchCount;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public List<WholeSaleVo> getWholeSaleList() {
		return wholeSaleList;
	}

	public void setWholeSaleList(List<WholeSaleVo> wholeSaleList) {
		this.wholeSaleList = wholeSaleList;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public String getBuyType() {
		return buyType;
	}

	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}

	public String getCommodityTitle() {
		return commodityTitle;
	}

	public void setCommodityTitle(String commodityTitle) {
		this.commodityTitle = commodityTitle;
	}

	public String getCommoditySkuPic() {
		return commoditySkuPic;
	}

	public void setCommoditySkuPic(String commoditySkuPic) {
		this.commoditySkuPic = commoditySkuPic;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getFavourablePrice() {
		return favourablePrice;
	}

	public void setFavourablePrice(BigDecimal favourablePrice) {
		this.favourablePrice = favourablePrice;
	}

	public int getIsExistToPutong() {
		return isExistToPutong;
	}

	public void setIsExistToPutong(int isExistToPutong) {
		this.isExistToPutong = isExistToPutong;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}



 
	//add by 柯典佑    按加入时间排序
	@Override
	public int compareTo(ShoppingCartVo o) {
		if(this.getAddDate()!=null && o.getAddDate()!=null){
			long beginTime = this.getAddDate().getTime();
			long endTime = o.getAddDate().getTime();
		        return beginTime<endTime ? 1 : beginTime==endTime?0:-1;
		}
		return 0;
	}

	public int getGiveType() {
		return giveType;
	}

	public void setGiveType(int giveType) {
		this.giveType = giveType;
	}

 

}

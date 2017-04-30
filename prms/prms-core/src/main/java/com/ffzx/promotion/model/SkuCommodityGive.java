package com.ffzx.promotion.model;

import com.ffzx.commerce.framework.common.persistence.DataEntity;



 /**
 * @Description: TODO
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年9月12日 下午3:46:12
 * @version V1.0 
 *
 */
public class SkuCommodityGive extends MainCommodityGive  {

    private static final long serialVersionUID = 1L;

    /**
     *  属性
     */
    protected String commodityAttributeValues;


    /**
     *条形码
     */
    protected String skubarcode;
    /**
     *skuId
     */
    protected String skuid;

    /**
     * 限定数量
     */
    protected String limitCount;

    /**
     * 优惠价/元
     */
    private String preferentialPrice;
    /**
     * 活动优惠价
     */
    private String activityPrice;
    /**
     * 单次赠送
     */
    private String giftCount;
	public String getSkuid() {
		return skuid;
	}
	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}
	public String getCommodityAttributeValues() {
		return commodityAttributeValues;
	}
	public void setCommodityAttributeValues(String commodityAttributeValues) {
		this.commodityAttributeValues = commodityAttributeValues;
	}
	public String getSkubarcode() {
		return skubarcode;
	}
	public void setSkubarcode(String skubarcode) {
		this.skubarcode = skubarcode;
	}
	public String getLimitCount() {
		return limitCount;
	}
	public void setLimitCount(String limitCount) {
		this.limitCount = limitCount;
	}
	public String getPreferentialPrice() {
		return preferentialPrice;
	}
	public void setPreferentialPrice(String preferentialPrice) {
		this.preferentialPrice = preferentialPrice;
	}
	public String getActivityPrice() {
		return activityPrice;
	}
	public void setActivityPrice(String activityPrice) {
		this.activityPrice = activityPrice;
	}
	public String getGiftCount() {
		return giftCount;
	}
	public void setGiftCount(String giftCount) {
		this.giftCount = giftCount;
	}
	
}
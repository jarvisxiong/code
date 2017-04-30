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
public class MainCommodityGive  {

    private static final long serialVersionUID = 1L;

    /**
     *  商品条形码
     */
    protected String barCode;

    /**
     *商品名称
     */
    protected String name;

    /**
     * 限定数量
     */
    protected String limitCount;

    /**
     * 优惠价/元
     */
    private String preferentialPrice;
    /**
     * 商品id
     */
    private String commodityId;
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

}
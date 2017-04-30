/**
 * 
 */
package com.ffzx.order.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * @Description: TODO
 * @author zi.luo
 * @email  zi.luo@ffzxnet.com
 * @date 2016年6月7日 上午10:24:24
 * @version V1.0 
 *
 */
public class OutboundSalesDeliveryItemsApiVo extends DataEntity<OutboundSalesDeliveryItemsApiVo> {
	  private static final long serialVersionUID = 1L;

	  private String commoditySkuCode;
	  
	  private Integer Qty;

	public String getCommoditySkuCode() {
		return commoditySkuCode;
	}

	public void setCommoditySkuCode(String commoditySkuCode) {
		this.commoditySkuCode = commoditySkuCode;
	}

	public Integer getQty() {
		return Qty;
	}

	public void setQty(Integer qty) {
		Qty = qty;
	}
	  
	  
}

package com.ffzx.order.api.dto;

import java.io.Serializable;

/**
 * 
 * @author Generator
 * @date 2016-05-13 10:09:55
 * @version 1.0.0
 * @copyright facegarden.com
 */
public class CommodityAddress implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
     * 唯一序列号.
     */
    private String id;
    
    private String commodityCode;
    
    private String addressCode;
    
    private String addressName;
    
    
    public CommodityAddress(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
}

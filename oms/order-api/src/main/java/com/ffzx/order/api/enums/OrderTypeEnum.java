package com.ffzx.order.api.enums;

/**
 * @Description: 订单类型 枚举
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年5月9日 下午3:44:34
 * @version V1.0 
 *
 */
public enum OrderTypeEnum {
    COMMON_ORDER("普通订单","COMMON_ORDER"),
    EXCHANGE_ORDER("换货订单","EXCHANGE_ORDER");
	private String name;
	private String value;
	
	private OrderTypeEnum(String name,String value){
		this.name=name;
		this.value=value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}

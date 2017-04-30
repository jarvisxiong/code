package com.ffzx.promotion.api.enums;


 /**
 * @Description: 活动管理类型
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年5月5日 上午11:23:06
 * @version V1.0 
 *
 */
public enum ActivityTypeEnum {
	PRE_SALE("预售","PRE_SALE"),
	PANIC_BUY("抢购","PANIC_BUY"),
	NEWUSER_VIP("新手专享","NEWUSER_VIP"),
	WHOLESALE_MANAGER("批发","WHOLESALE_MANAGER"),
	ORDINARY_ACTIVITY("普通活动","ORDINARY_ACTIVITY");
	private String name;
	private String value;

	
	private ActivityTypeEnum(String name,String value){
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

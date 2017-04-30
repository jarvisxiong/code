package com.ffzx.order.api.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 订单购买类型
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年5月6日 上午9:16:12
 * @version V1.0 
 *
 */
public enum BuyTypeEnum {
	COMMON_BUY("普通","COMMON_BUY"),
	PRE_SALE("预售","PRE_SALE"),
	PANIC_BUY("抢购","PANIC_BUY"),
	NEWUSER_VIP("新用户专享","NEWUSER_VIP"),
	WHOLESALE_MANAGER("批发","WHOLESALE_MANAGER"),
	ORDINARY_ACTIVITY("活动","ORDINARY_ACTIVITY")
	;
	private String name;
	
	private String value;
	
	private BuyTypeEnum(String name,String value){
		this.name=name;
		this.value=value;
	}
	
	public static BuyTypeEnum getBuyTypeEnumByValue(String value){
		for(BuyTypeEnum buyType:BuyTypeEnum.values()){
			if(buyType.value.equals(value))
				return buyType;
		}
		return null;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList()
	{
		BuyTypeEnum[] ary = BuyTypeEnum.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value", ary[i].getValue());
			map.put("name", ary[i].getName());
			list.add(map);
		}
		return list;
	}
}

package com.ffzx.order.api.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 订单售后状态枚举
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年4月22日 下午2:13:57
 * @version V1.0 
 *
 */
public enum AfterSaleStatusEnum {
	REFUND_PROCESSING("退款处理中","REFUND_PROCESSING"),
	REFUND_SUCCESS("退款成功","REFUND_SUCCESS"),
	EXCHANGE_PROCESSING("换货处理中","EXCHANGE_PROCESSING"),
	EXCHANGE_SUCCESS("换货成功","EXCHANGE_SUCCESS")
	;
	private String name;
	
	private String value;
	
	private AfterSaleStatusEnum(String name,String value){
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList()
	{
		AfterSaleStatusEnum[] ary = AfterSaleStatusEnum.values();
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

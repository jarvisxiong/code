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
public enum AfterSaleStatusType {
	
	/****
	 * 0 退款(没收到货)
	 */
	REFUND_TOTAL("退款(没收到货)","0"),
	/**
	 * 1退货(已收到货)
	 */
	REFUND_GOODS("退货(已收到货)","1"),
	/**
	 * 2换货(已收到货) 
	 */
	CHANGE_GOODS("换货(已收到货) ","2");
	private String name;
	
	private String value;
	
	private AfterSaleStatusType(String name,String value){
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
		AfterSaleStatusType[] ary = AfterSaleStatusType.values();
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

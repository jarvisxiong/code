package com.ffzx.order.api.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 售后退款(没收到或)原因
 * @author lushi.guo
 * @version V1.0 
 *
 */
public enum AfterSaleRefundReasons {

	NOREC("长时间没收到货","0"),
	MESSAGEERROR("信息填写错误，没收到货","1"),
	NONEED("没收到货，不想要了 ","2"),
	OTHERSUP("其他原因","3");
	
	private String name;
	
	private String value;
	
	private AfterSaleRefundReasons(String name,String value){
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
		AfterSaleRefundReasons[] ary = AfterSaleRefundReasons.values();
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

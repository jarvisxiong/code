package com.ffzx.order.api.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 换货（已收到货）原因
 * @author lushi.guo
 * @version V1.0 
 *
 */
public enum AfterSaleChangeReasons {

	
	DAMA("收到商品破损","31"),
	MISS("商品错发/漏发","32"),
	QUALITY("商品质量问题","33"),
	OTHERSUP("其他原因","30");
	
	private String name;
	
	private String value;
	
	private AfterSaleChangeReasons(String name,String value){
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
		AfterSaleChangeReasons[] ary = AfterSaleChangeReasons.values();
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

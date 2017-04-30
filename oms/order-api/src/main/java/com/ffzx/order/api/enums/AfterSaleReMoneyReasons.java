package com.ffzx.order.api.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 退货（已收到货）原因
 * @author lushi.guo
 * @version V1.0 
 *
 */
public enum AfterSaleReMoneyReasons {
	DAMA("收到商品破损","14"),
	MISS("商品错发/漏发","15"),
	CONFORM("收到商品不符","16"),
	QUALITY("商品质量问题","17"),
	PREC("商品比周边商贵 ","18");
	
	private String name;
	
	private String value;
	
	private AfterSaleReMoneyReasons(String name,String value){
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
		AfterSaleReMoneyReasons[] ary = AfterSaleReMoneyReasons.values();
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

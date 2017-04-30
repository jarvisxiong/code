package com.ffzx.promotion.api.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AdvertStatus {

	SOONSTART("即将开始","SOONSTART"),
	STARTTING("进行中","STARTTING"),
	END("已结束","END")
	;
	private String name;
	
	private String value;
	
	private AdvertStatus(String name,String value)
	{
		this.name = name;
		this.value = value;
				
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
		AdvertStatus[] ary = AdvertStatus.values();
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

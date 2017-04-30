package com.ffzx.portal.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 栏目排序类型
 * 
 * @author ying.cai
 * @date 2017年4月12日上午11:19:21
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 */
public enum CatelogSortType {
	DATE("日期正序","DATE"),
	DATEDESC("日期逆序","DATEDESC");
	//自定义
	private String label;
	private String value;
	private CatelogSortType(String label,String value){
		this.setLabel(label);
		this.setValue(value);
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList(){
		CatelogSortType[] ary = CatelogSortType.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value", ary[i].getValue());
			map.put("name", ary[i].getLabel());
			list.add(map);
		}
		return list;
	}
	
}

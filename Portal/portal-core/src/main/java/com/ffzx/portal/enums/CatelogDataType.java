package com.ffzx.portal.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 *  栏目数据类型
 * 
 * @author ying.cai
 * @date 2017年4月12日上午11:19:16
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 */
public enum CatelogDataType {
	SINGLE("单条","SINGLE"),
	BONUS("多条","BONUS");
	//元数据，引用，分类数据
	private String label;
	private String value;
	private CatelogDataType(String label,String value){
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
		CatelogDataType[] ary = CatelogDataType.values();
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

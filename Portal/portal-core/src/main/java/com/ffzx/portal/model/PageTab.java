package com.ffzx.portal.model;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.portal.enums.PageTabType;

/***
 * 页签
 * 
 * @author ying.cai
 * @date 2017年4月12日上午11:20:24
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 */
public class PageTab extends DataEntity{
	
	private static final long serialVersionUID = -7446583196347851941L;
	public static final String MAPPER ="com.ffzx.website.dao.PageTabDao";
		
	//页签id
	private String id;
	
	//页签名字
	private String name;
	
	//页签编号
	private String number;
	
	//页签类型
	private PageTabType pageTabType ; 
	
	//序号
	private int sort;
	
	//关键字
	private String key ;
	
	//meta描述
	private String meta ; 
	
	//页面路径
	private String path ; 
	
	//静态化路径
	private String staticPath;
	
	//城市分站ID
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public PageTabType getPageTabType() {
		return pageTabType;
	}
	public void setPageTabType(PageTabType pageTabType) {
		this.pageTabType = pageTabType;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getStaticPath() {
		return staticPath;
	}
	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}
	
	public String getPageTabTypeName(){
		if(this.pageTabType==null){
			return null;
		}
		return this.pageTabType.getLabel();
	}
	
	
}

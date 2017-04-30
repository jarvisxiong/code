package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

public class ActivityPreSaleTag extends DataEntity<ActivityPreSaleTag> {
	private static final long serialVersionUID = 1L;

	
	private int number;// 标签序号
	
	private String name;// 标签名字
	
	/*
	 * 冗余
	 */
	private String oldName;//编辑之前的名字
	private String oldNumber;//编辑之前的排序号
	
	
	
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}


	public String getOldNumber() {
		return oldNumber;
	}
	public void setOldNumber(String oldNumber) {
		this.oldNumber = oldNumber;
	}

	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

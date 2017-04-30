package com.ffzx.promotion.model;

import java.util.List;

import com.ffzx.promotion.api.dto.RedpackageReceive;

public class ResultBestRedpackValue {

	private String flag; //0 否 1该组合有满足条件的红包
	private List<RedpackageReceive> list;

	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List<RedpackageReceive> getList() {
		return list;
	}
	public void setList(List<RedpackageReceive> list) {
		this.list = list;
	}
	
	
}

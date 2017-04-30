package com.ffzx.promotion.model;

import java.math.BigDecimal;
import java.util.List;

import com.ffzx.promotion.api.dto.RedpackageReceive;

public class MathRedpackagePrice {

	private List<RedpackageReceive> list;
	private BigDecimal price;
	public List<RedpackageReceive> getList() {
		return list;
	}
	public void setList(List<RedpackageReceive> list) {
		this.list = list;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
}

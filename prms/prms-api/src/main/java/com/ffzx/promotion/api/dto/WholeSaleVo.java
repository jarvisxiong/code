package com.ffzx.promotion.api.dto;

import java.math.BigDecimal;

import com.ffzx.commerce.framework.common.persistence.BaseEntity;

 /**
 * @Description: 购物车批发vo
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年6月6日 下午2:00:53
 * @version V1.0 
 *
 */
public class WholeSaleVo extends BaseEntity<WholeSaleVo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6324987393575071239L;
	
	private Integer startNumber;//开始区间数
	
	private Integer endNumber;//结束区间数
	
	private BigDecimal price;//价格

	public Integer getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(Integer startNumber) {
		this.startNumber = startNumber;
	}

	public Integer getEndNumber() {
		return endNumber;
	}

	public void setEndNumber(Integer endNumber) {
		this.endNumber = endNumber;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}

package com.ffzx.bi.vo;

import com.ffzx.bi.model.GoodsArrival;

/**
 * 
 * @author ffzx
 * @date 2016-09-01 14:45:34
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class GoodsArrivalVo {
	
	private GoodsArrival goodsArrival;
	
	private GoodsArrivalCustom goodsArrivalCustom;

	public GoodsArrival getGoodsArrival() {
		return goodsArrival;
	}

	public void setGoodsArrival(GoodsArrival goodsArrival) {
		this.goodsArrival = goodsArrival;
	}

	public GoodsArrivalCustom getGoodsArrivalCustom() {
		return goodsArrivalCustom;
	}

	public void setGoodsArrivalCustom(GoodsArrivalCustom goodsArrivalCustom) {
		this.goodsArrivalCustom = goodsArrivalCustom;
	} 
	
}
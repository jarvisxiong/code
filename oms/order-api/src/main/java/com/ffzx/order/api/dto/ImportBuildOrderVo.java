package com.ffzx.order.api.dto;

import java.util.List;

/***
 * 
 * @description 下单商品购买购买值对象
 * @author ying.cai
 * @email ying.cai@ffzxnet.com
 * @date 2016-5-9 上午11:58:27
 * @version V1.0
 *
 */
public class ImportBuildOrderVo {

	private String errorCode;
	private String msg;
	private String memberId;
	private String memberAddressId;
	private List<GoodsVo> goodsVoList;
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}
	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	/**
	 * @return the memberAddressId
	 */
	public String getMemberAddressId() {
		return memberAddressId;
	}
	/**
	 * @param memberAddressId the memberAddressId to set
	 */
	public void setMemberAddressId(String memberAddressId) {
		this.memberAddressId = memberAddressId;
	}
	/**
	 * @return the goodsVoList
	 */
	public List<GoodsVo> getGoodsVoList() {
		return goodsVoList;
	}
	/**
	 * @param goodsVoList the goodsVoList to set
	 */
	public void setGoodsVoList(List<GoodsVo> goodsVoList) {
		this.goodsVoList = goodsVoList;
	}
	
	
}

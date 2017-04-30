package com.ffzx.order.api.dto;


/***
 * 
 * @description 下单商品购买购买值对象
 * @author ying.cai
 * @email ying.cai@ffzxnet.com
 * @date 2016-5-9 上午11:58:27
 * @version V1.0
 *
 */
public class AftersaleVo {
	/***
	 * 订单id
	 */
	private String orderId ;
	
	/***
	 * 商品数量
	 */
	private String maxEditNum;
	
	/***
	 * 图片路径
	 */
	private String imgPaths;
	
	/***
	 * 商品skuID
	 */
	private String skuId ; 
		
	/***
	 * 订单状态
	 */
	private  String status;
	/***
	 * 订单编号
	 */
	private String orderNumber;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getMaxEditNum() {
		return maxEditNum;
	}
	public void setMaxEditNum(String maxEditNum) {
		this.maxEditNum = maxEditNum;
	}
	public String getImgPaths() {
		return imgPaths;
	}
	public void setImgPaths(String imgPaths) {
		this.imgPaths = imgPaths;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	

}

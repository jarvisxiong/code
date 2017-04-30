/**   
 * @Title: ReplenishmentOrderVo.java
 * @Package com.ffzx.order.api.dto
 * @Description: TODO
 * @author 雷  
 * @date 2016年9月20日 
 * @version V1.0   
 */
package com.ffzx.order.api.dto;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: ReplenishmentOrderVo
 * @Description: 补货订单消息vo
 * @author 雷
 * @date 2016年9月20日
 * 
 */
public class ReplenishmentOrderVo {
	/**
	 * 商品sku
	 */
	String skuCode;
	/**
	 * 订单号集合
	 */
	List<String> orderNo;
	/**
	 * 补货日期
	 */
	Date receiptDate;
	/**
	 * 是否补货：0：不补货，1：补货
	 */
	String receiptType;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public ReplenishmentOrderVo() {
		super();
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param skuCode
	 * @param orderNo
	 * @param receiptDate
	 * @param receiptType
	 */
	public ReplenishmentOrderVo(String skuCode, List<String> orderNo, Date receiptDate, String receiptType) {
		super();
		this.skuCode = skuCode;
		this.orderNo = orderNo;
		this.receiptDate = receiptDate;
		this.receiptType = receiptType;
	}

	/**
	 * 雷----2016年9月20日
	 * 
	 * @return skuCode
	 */
	public String getSkuCode() {
		return skuCode;
	}

	/**
	 * 雷-------2016年9月20日
	 * 
	 * @param skuCode
	 *            要设置的 skuCode
	 */
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	/**
	 * 雷----2016年9月20日
	 * 
	 * @return orderNo
	 */
	public List<String> getOrderNo() {
		return orderNo;
	}

	/**
	 * 雷-------2016年9月20日
	 * 
	 * @param orderNo
	 *            要设置的 orderNo
	 */
	public void setOrderNo(List<String> orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 雷----2016年9月20日
	 * 
	 * @return receiptDate
	 */
	public Date getReceiptDate() {
		return receiptDate;
	}

	/**
	 * 雷-------2016年9月20日
	 * 
	 * @param receiptDate
	 *            要设置的 receiptDate
	 */
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	/**
	 * 雷----2016年9月20日
	 * 
	 * @return receiptType
	 */
	public String getReceiptType() {
		return receiptType;
	}

	/**
	 * 雷-------2016年9月20日
	 * 
	 * @param receiptType
	 *            要设置的 receiptType
	 */
	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

}

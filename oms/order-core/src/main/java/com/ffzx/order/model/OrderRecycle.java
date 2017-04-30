package com.ffzx.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * 
 * @author ffzx
 * @date 2016-08-04 17:19:04
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class OrderRecycle extends DataEntity<OrderRecycle> {

    private static final long serialVersionUID = 1L;

    /**
     * 客户签收单号.
     */
    private String orderNoSigned;

    /**
     * 客户签收单条形码.
     */
    private String barCodeSigned;

    /**
     * 合伙人.
     */
    private String partner;

    /**
     * 合伙人工号.
     */
    private String partnerNo;

    /**
     * 合伙人地址.
     */
    private String partnerAddress;

    /**
     * 合伙人电话.
     */
    private String partnerPhone;

    /**
     * 销售订单号.
     */
    private String saleNo;
    
    /**
     * 销售发货订单号
     */
    private String saleNoDelivery;

    /**
     * 商品名称.
     */
    private String commodityName;

    /**
     * 商品条形码.
     */
    private String commodityBarCode;

    /**
     * 商品数量.
     */
    private Integer commodityNum;

    /**
     * 商品单价.
     */
    private BigDecimal commodityPrice;

    /**
     * 交易金额.
     */
    private BigDecimal commodityMoney;

    /**
     * 物流回收状态:0未回收,1已回收
     */
    private String logisticsRecyleStatus;

    /**
     * 财务回收状态:0未回收,1已回收
     */
    private String financeRecyleStatus;

    /**
     * 回收确认时间.
     */
    private Date recyleDate;

    /**
     * 调出仓库.
     */
    private String warehouseCalled;

    /**
     * 出库时间.
     */
    private Date deliveryDate;
    
	/**
     * 
     * {@linkplain #orderNoSigned}
     *
     * @return the value of order_recycle.order_no_signed
     */
    public String getOrderNoSigned() {
        return orderNoSigned;
    }

    /**
     * {@linkplain #orderNoSigned}
     * @param orderNoSigned the value for order_recycle.order_no_signed
     */
    public void setOrderNoSigned(String orderNoSigned) {
        this.orderNoSigned = orderNoSigned == null ? null : orderNoSigned.trim();
    }

    /**
     * 
     * {@linkplain #barCodeSigned}
     *
     * @return the value of order_recycle.bar_code_signed
     */
    public String getBarCodeSigned() {
        return barCodeSigned;
    }

    /**
     * {@linkplain #barCodeSigned}
     * @param barCodeSigned the value for order_recycle.bar_code_signed
     */
    public void setBarCodeSigned(String barCodeSigned) {
        this.barCodeSigned = barCodeSigned == null ? null : barCodeSigned.trim();
    }

    /**
     * 
     * {@linkplain #partner}
     *
     * @return the value of order_recycle.partner
     */
    public String getPartner() {
        return partner;
    }

    /**
     * {@linkplain #partner}
     * @param partner the value for order_recycle.partner
     */
    public void setPartner(String partner) {
        this.partner = partner == null ? null : partner.trim();
    }

    /**
     * 
     * {@linkplain #partnerNo}
     *
     * @return the value of order_recycle.partner_no
     */
    public String getPartnerNo() {
        return partnerNo;
    }

    /**
     * {@linkplain #partnerNo}
     * @param partnerNo the value for order_recycle.partner_no
     */
    public void setPartnerNo(String partnerNo) {
        this.partnerNo = partnerNo == null ? null : partnerNo.trim();
    }

    /**
     * 
     * {@linkplain #partnerAddress}
     *
     * @return the value of order_recycle.partner_address
     */
    public String getPartnerAddress() {
        return partnerAddress;
    }

    /**
     * {@linkplain #partnerAddress}
     * @param partnerAddress the value for order_recycle.partner_address
     */
    public void setPartnerAddress(String partnerAddress) {
        this.partnerAddress = partnerAddress == null ? null : partnerAddress.trim();
    }

    /**
     * 
     * {@linkplain #partnerPhone}
     *
     * @return the value of order_recycle.partner_phone
     */
    public String getPartnerPhone() {
        return partnerPhone;
    }

    /**
     * {@linkplain #partnerPhone}
     * @param partnerPhone the value for order_recycle.partner_phone
     */
    public void setPartnerPhone(String partnerPhone) {
        this.partnerPhone = partnerPhone == null ? null : partnerPhone.trim();
    }

    /**
     * 
     * {@linkplain #saleNo}
     *
     * @return the value of order_recycle.sale_no
     */
    public String getSaleNo() {
        return saleNo;
    }

    /**
     * {@linkplain #saleNo}
     * @param saleNo the value for order_recycle.sale_no
     */
    public void setSaleNo(String saleNo) {
        this.saleNo = saleNo == null ? null : saleNo.trim();
    }

    /**
     * 
     * {@linkplain #commodityName}
     *
     * @return the value of order_recycle.commodity_name
     */
    public String getCommodityName() {
        return commodityName;
    }

    /**
     * {@linkplain #commodityName}
     * @param commodityName the value for order_recycle.commodity_name
     */
    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName == null ? null : commodityName.trim();
    }

    /**
     * 
     * {@linkplain #commodityBarCode}
     *
     * @return the value of order_recycle.commodity_bar_code
     */
    public String getCommodityBarCode() {
        return commodityBarCode;
    }

    /**
     * {@linkplain #commodityBarCode}
     * @param commodityBarCode the value for order_recycle.commodity_bar_code
     */
    public void setCommodityBarCode(String commodityBarCode) {
        this.commodityBarCode = commodityBarCode == null ? null : commodityBarCode.trim();
    }

    /**
     * 
     * {@linkplain #commodityNum}
     *
     * @return the value of order_recycle.commodity_num
     */
    public Integer getCommodityNum() {
        return commodityNum;
    }

    /**
     * {@linkplain #commodityNum}
     * @param commodityNum the value for order_recycle.commodity_num
     */
    public void setCommodityNum(Integer commodityNum) {
        this.commodityNum = commodityNum;
    }

    public String getSaleNoDelivery() {
		return saleNoDelivery;
	}

	public void setSaleNoDelivery(String saleNoDelivery) {
		this.saleNoDelivery = saleNoDelivery;
	}

	/**
     * 
     * {@linkplain #commodityPrice}
     *
     * @return the value of order_recycle.commodity_price
     */
    public BigDecimal getCommodityPrice() {
        return commodityPrice;
    }

    /**
     * {@linkplain #commodityPrice}
     * @param commodityPrice the value for order_recycle.commodity_price
     */
    public void setCommodityPrice(BigDecimal commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    /**
     * 
     * {@linkplain #commodityMoney}
     *
     * @return the value of order_recycle.commodity_money
     */
    public BigDecimal getCommodityMoney() {
        return commodityMoney;
    }

    /**
     * {@linkplain #commodityMoney}
     * @param commodityMoney the value for order_recycle.commodity_money
     */
    public void setCommodityMoney(BigDecimal commodityMoney) {
        this.commodityMoney = commodityMoney;
    }

    /**
     * 
     * {@linkplain #logisticsRecyleStatus}
     *
     * @return the value of order_recycle.logistics_recyle_status
     */
    public String getLogisticsRecyleStatus() {
    	if(null == logisticsRecyleStatus){
    		return null;
    	}
        return ("0".equals(logisticsRecyleStatus)) ? "未回收" : "已回收";
    }

    /**
     * {@linkplain #logisticsRecyleStatus}
     * @param logisticsRecyleStatus the value for order_recycle.logistics_recyle_status
     */
    public void setLogisticsRecyleStatus(String logisticsRecyleStatus) {
        this.logisticsRecyleStatus = logisticsRecyleStatus == null ? null : logisticsRecyleStatus.trim();
    }

    /**
     * 
     * {@linkplain #financeRecyleStatus}
     *
     * @return the value of order_recycle.finance_recyle_status
     */
    public String getFinanceRecyleStatus() {
    	if(null == financeRecyleStatus){
    		return null;
    	}
        return ("0".equals(financeRecyleStatus)) ? "未回收" : "已回收";
    }

    /**
     * {@linkplain #financeRecyleStatus}
     * @param financeRecyleStatus the value for order_recycle.finance_recyle_status
     */
    public void setFinanceRecyleStatus(String financeRecyleStatus) {
        this.financeRecyleStatus = financeRecyleStatus == null ? null : financeRecyleStatus.trim();
    }

    /**
     * 
     * {@linkplain #recyleDate}
     *
     * @return the value of order_recycle.recyle_date
     */
    public Date getRecyleDate() {
        return recyleDate;
    }

    /**
     * {@linkplain #recyleDate}
     * @param recyleDate the value for order_recycle.recyle_date
     */
    public void setRecyleDate(Date recyleDate) {
        this.recyleDate = recyleDate;
    }

    /**
     * 
     * {@linkplain #warehouseCalled}
     *
     * @return the value of order_recycle.warehouse_called
     */
    public String getWarehouseCalled() {
        return warehouseCalled;
    }

    /**
     * {@linkplain #warehouseCalled}
     * @param warehouseCalled the value for order_recycle.warehouse_called
     */
    public void setWarehouseCalled(String warehouseCalled) {
        this.warehouseCalled = warehouseCalled == null ? null : warehouseCalled.trim();
    }

    /**
     * 
     * {@linkplain #deliveryDate}
     *
     * @return the value of order_recycle.delivery_date
     */
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * {@linkplain #deliveryDate}
     * @param deliveryDate the value for order_recycle.delivery_date
     */
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
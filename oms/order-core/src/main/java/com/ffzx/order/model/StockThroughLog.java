package com.ffzx.order.model;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.util.Date;

/**
 * 
 * @author ffzx
 * @date 2016-08-19 12:55:24
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class StockThroughLog extends DataEntity<StockThroughLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 阶段.
     */
    private String throughLevel;

    /**
     * 1：增加；2：减少.
     */
    private String throughDatatype;

    /**
     * 数据.
     */
    private String throughData;

    /**
     * 是否成功.
     */
    private String throughResult;

    /**
     * .
     */
    private Date throughDate;

    /**
     * sku code.
     */
    private String skuCode;

    /**
     * 仓库编码.
     */
    private String warehouseCode;

    /**
     * 地址编码.
     */
    private String addressCode;

    /**
     * 仓库名字.
     */
    private String warehouseName;

    /**
     * 地址名称.
     */
    private String addressName;

    /**
     * .
     */
    private String orderNo;

    /**
     * 中央仓Code.
     */
    private String centerWarehouseCode;

    /**
     * 中央仓名称.
     */
    private String centerWarehouseName;

    /**
     * 中央仓改变数.
     */
    private String centerChangedata;

    /**
     * 当前中央仓库存.
     */
    private String centerStockNum;

    /**
     * 当前中央仓已占用数.
     */
    private String centerUsedCount;

    /**
     * 当前wms改变数.
     */
    private String wmsChangedata;

    /**
     * 当前wms库存.
     */
    private String wmsStockNum;

    /**
     * 当前wms已占用数.
     */
    private String wmsUsedCount;

    /**
     * 当前改变数.
     */
    private String addressChangedata;

    /**
     * 当前库存.
     */
    private String addressStockNum;

    /**
     * 当前已占用数.
     */
    private String addressUsedCount;

    /**
     * 
     * {@linkplain #throughLevel}
     *
     * @return the value of stock_through_log.through_level
     */
    public String getThroughLevel() {
        return throughLevel;
    }

    /**
     * {@linkplain #throughLevel}
     * @param throughLevel the value for stock_through_log.through_level
     */
    public void setThroughLevel(String throughLevel) {
        this.throughLevel = throughLevel == null ? null : throughLevel.trim();
    }

    /**
     * 
     * {@linkplain #throughDatatype}
     *
     * @return the value of stock_through_log.through_dataType
     */
    public String getThroughDatatype() {
        return throughDatatype;
    }

    /**
     * {@linkplain #throughDatatype}
     * @param throughDatatype the value for stock_through_log.through_dataType
     */
    public void setThroughDatatype(String throughDatatype) {
        this.throughDatatype = throughDatatype == null ? null : throughDatatype.trim();
    }

    /**
     * 
     * {@linkplain #throughData}
     *
     * @return the value of stock_through_log.through_data
     */
    public String getThroughData() {
        return throughData;
    }

    /**
     * {@linkplain #throughData}
     * @param throughData the value for stock_through_log.through_data
     */
    public void setThroughData(String throughData) {
        this.throughData = throughData == null ? null : throughData.trim();
    }

    /**
     * 
     * {@linkplain #throughResult}
     *
     * @return the value of stock_through_log.through_result
     */
    public String getThroughResult() {
        return throughResult;
    }

    /**
     * {@linkplain #throughResult}
     * @param throughResult the value for stock_through_log.through_result
     */
    public void setThroughResult(String throughResult) {
        this.throughResult = throughResult == null ? null : throughResult.trim();
    }

    /**
     * 
     * {@linkplain #throughDate}
     *
     * @return the value of stock_through_log.through_date
     */
    public Date getThroughDate() {
        return throughDate;
    }

    /**
     * {@linkplain #throughDate}
     * @param throughDate the value for stock_through_log.through_date
     */
    public void setThroughDate(Date throughDate) {
        this.throughDate = throughDate;
    }

    /**
     * 
     * {@linkplain #skuCode}
     *
     * @return the value of stock_through_log.sku_code
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * {@linkplain #skuCode}
     * @param skuCode the value for stock_through_log.sku_code
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    /**
     * 
     * {@linkplain #warehouseCode}
     *
     * @return the value of stock_through_log.warehouse_code
     */
    public String getWarehouseCode() {
        return warehouseCode;
    }

    /**
     * {@linkplain #warehouseCode}
     * @param warehouseCode the value for stock_through_log.warehouse_code
     */
    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    /**
     * 
     * {@linkplain #addressCode}
     *
     * @return the value of stock_through_log.address_code
     */
    public String getAddressCode() {
        return addressCode;
    }

    /**
     * {@linkplain #addressCode}
     * @param addressCode the value for stock_through_log.address_code
     */
    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode == null ? null : addressCode.trim();
    }

    /**
     * 
     * {@linkplain #warehouseName}
     *
     * @return the value of stock_through_log.warehouse_name
     */
    public String getWarehouseName() {
        return warehouseName;
    }

    /**
     * {@linkplain #warehouseName}
     * @param warehouseName the value for stock_through_log.warehouse_name
     */
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    /**
     * 
     * {@linkplain #addressName}
     *
     * @return the value of stock_through_log.address_name
     */
    public String getAddressName() {
        return addressName;
    }

    /**
     * {@linkplain #addressName}
     * @param addressName the value for stock_through_log.address_name
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName == null ? null : addressName.trim();
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of stock_through_log.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * {@linkplain #orderNo}
     * @param orderNo the value for stock_through_log.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * 
     * {@linkplain #centerWarehouseCode}
     *
     * @return the value of stock_through_log.center_warehouse_Code
     */
    public String getCenterWarehouseCode() {
        return centerWarehouseCode;
    }

    /**
     * {@linkplain #centerWarehouseCode}
     * @param centerWarehouseCode the value for stock_through_log.center_warehouse_Code
     */
    public void setCenterWarehouseCode(String centerWarehouseCode) {
        this.centerWarehouseCode = centerWarehouseCode == null ? null : centerWarehouseCode.trim();
    }

    /**
     * 
     * {@linkplain #centerWarehouseName}
     *
     * @return the value of stock_through_log.center_warehouse_Name
     */
    public String getCenterWarehouseName() {
        return centerWarehouseName;
    }

    /**
     * {@linkplain #centerWarehouseName}
     * @param centerWarehouseName the value for stock_through_log.center_warehouse_Name
     */
    public void setCenterWarehouseName(String centerWarehouseName) {
        this.centerWarehouseName = centerWarehouseName == null ? null : centerWarehouseName.trim();
    }

    /**
     * 
     * {@linkplain #centerChangedata}
     *
     * @return the value of stock_through_log.center_changeData
     */
    public String getCenterChangedata() {
        return centerChangedata;
    }

    /**
     * {@linkplain #centerChangedata}
     * @param centerChangedata the value for stock_through_log.center_changeData
     */
    public void setCenterChangedata(String centerChangedata) {
        this.centerChangedata = centerChangedata == null ? null : centerChangedata.trim();
    }

    /**
     * 
     * {@linkplain #centerStockNum}
     *
     * @return the value of stock_through_log.center_stock_num
     */
    public String getCenterStockNum() {
        return centerStockNum;
    }

    /**
     * {@linkplain #centerStockNum}
     * @param centerStockNum the value for stock_through_log.center_stock_num
     */
    public void setCenterStockNum(String centerStockNum) {
        this.centerStockNum = centerStockNum == null ? null : centerStockNum.trim();
    }

    /**
     * 
     * {@linkplain #centerUsedCount}
     *
     * @return the value of stock_through_log.center_used_count
     */
    public String getCenterUsedCount() {
        return centerUsedCount;
    }

    /**
     * {@linkplain #centerUsedCount}
     * @param centerUsedCount the value for stock_through_log.center_used_count
     */
    public void setCenterUsedCount(String centerUsedCount) {
        this.centerUsedCount = centerUsedCount == null ? null : centerUsedCount.trim();
    }

    /**
     * 
     * {@linkplain #wmsChangedata}
     *
     * @return the value of stock_through_log.wms_changeData
     */
    public String getWmsChangedata() {
        return wmsChangedata;
    }

    /**
     * {@linkplain #wmsChangedata}
     * @param wmsChangedata the value for stock_through_log.wms_changeData
     */
    public void setWmsChangedata(String wmsChangedata) {
        this.wmsChangedata = wmsChangedata == null ? null : wmsChangedata.trim();
    }

    /**
     * 
     * {@linkplain #wmsStockNum}
     *
     * @return the value of stock_through_log.wms_stock_num
     */
    public String getWmsStockNum() {
        return wmsStockNum;
    }

    /**
     * {@linkplain #wmsStockNum}
     * @param wmsStockNum the value for stock_through_log.wms_stock_num
     */
    public void setWmsStockNum(String wmsStockNum) {
        this.wmsStockNum = wmsStockNum == null ? null : wmsStockNum.trim();
    }

    /**
     * 
     * {@linkplain #wmsUsedCount}
     *
     * @return the value of stock_through_log.wms_used_count
     */
    public String getWmsUsedCount() {
        return wmsUsedCount;
    }

    /**
     * {@linkplain #wmsUsedCount}
     * @param wmsUsedCount the value for stock_through_log.wms_used_count
     */
    public void setWmsUsedCount(String wmsUsedCount) {
        this.wmsUsedCount = wmsUsedCount == null ? null : wmsUsedCount.trim();
    }

    /**
     * 
     * {@linkplain #addressChangedata}
     *
     * @return the value of stock_through_log.address_changeData
     */
    public String getAddressChangedata() {
        return addressChangedata;
    }

    /**
     * {@linkplain #addressChangedata}
     * @param addressChangedata the value for stock_through_log.address_changeData
     */
    public void setAddressChangedata(String addressChangedata) {
        this.addressChangedata = addressChangedata == null ? null : addressChangedata.trim();
    }

    /**
     * 
     * {@linkplain #addressStockNum}
     *
     * @return the value of stock_through_log.address_stock_num
     */
    public String getAddressStockNum() {
        return addressStockNum;
    }

    /**
     * {@linkplain #addressStockNum}
     * @param addressStockNum the value for stock_through_log.address_stock_num
     */
    public void setAddressStockNum(String addressStockNum) {
        this.addressStockNum = addressStockNum == null ? null : addressStockNum.trim();
    }

    /**
     * 
     * {@linkplain #addressUsedCount}
     *
     * @return the value of stock_through_log.address_used_count
     */
    public String getAddressUsedCount() {
        return addressUsedCount;
    }

    /**
     * {@linkplain #addressUsedCount}
     * @param addressUsedCount the value for stock_through_log.address_used_count
     */
    public void setAddressUsedCount(String addressUsedCount) {
        this.addressUsedCount = addressUsedCount == null ? null : addressUsedCount.trim();
    }
}
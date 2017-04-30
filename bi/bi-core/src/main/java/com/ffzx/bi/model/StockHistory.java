package com.ffzx.bi.model;

import java.util.Date;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * 
 * @author ffzx
 * @date 2016-08-15 14:24:06
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class StockHistory extends DataEntity<StockHistory> {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商.
     */
    private String vendorId;

    /**
     * 供应商编码.
     */
    private String vendorCode;

    /**
     * 供应商名称.
     */
    private String vendorName;

    /**
     * 商品ID.
     */
    private String commodityId;

    /**
     * 商品编码.
     */
    private String commodityCode;

    /**
     * 商品条形码.
     */
    private String commodityBarcode;

    /**
     * 商品名称.
     */
    private String commodityName;

    /**
     * SKU编码.
     */
    private String skuCode;

    /**
     * sku条形码.
     */
    private String skuBarcode;

    /**
     * 一级类目ID.
     */
    private String categoryIdOneLevel;

    /**
     * 一级类目名称.
     */
    private String categoryNameOneLevel;

    /**
     * 二级类目ID.
     */
    private String categoryIdTwoLevel;

    /**
     * 二级类目名称.
     */
    private String categoryNameTwoLevel;

    /**
     * 三级类目ID.
     */
    private String categoryIdThreeLevel;

    /**
     * 三级类目名称.
     */
    private String categoryNameThreeLevel;

    /**
     * 库存数.
     */
    private String stocknum;

    /**
     * 库存占用数.
     */
    private String stockUsedCount;

    /**
     * 仓库编码.
     */
    private String warehouseCode;

    /**
     * 仓库名称.
     */
    private String warehouseName;

    /**
     * 地址编码.
     */
    private String addressCode;

    /**
     * 地址名称.
     */
    private String addressName;

    /**
     * 年.
     */
    private String year;

    /**
     * 季度.
     */
    private String quarter;

    /**
     * 月.
     */
    private String month;

    /**
     * 周范围.
     */
    private String week;

    /**
     * 周.
     */
    private String weekNum;

    /**
     * 日.
     */
    private String day;

    /**
     * 迁移时间.
     */
    private Date transferTime;

    /**
     * 
     * {@linkplain #vendorId}
     *
     * @return the value of stock_repository.vendor_id
     */
    public String getVendorId() {
        return vendorId;
    }

    /**
     * {@linkplain #vendorId}
     * @param vendorId the value for stock_repository.vendor_id
     */
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId == null ? null : vendorId.trim();
    }

    /**
     * 
     * {@linkplain #vendorCode}
     *
     * @return the value of stock_repository.vendor_code
     */
    public String getVendorCode() {
        return vendorCode;
    }

    /**
     * {@linkplain #vendorCode}
     * @param vendorCode the value for stock_repository.vendor_code
     */
    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode == null ? null : vendorCode.trim();
    }

    /**
     * 
     * {@linkplain #vendorName}
     *
     * @return the value of stock_repository.vendor_name
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * {@linkplain #vendorName}
     * @param vendorName the value for stock_repository.vendor_name
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName == null ? null : vendorName.trim();
    }

    /**
     * 
     * {@linkplain #commodityId}
     *
     * @return the value of stock_repository.commodity_id
     */
    public String getCommodityId() {
        return commodityId;
    }

    /**
     * {@linkplain #commodityId}
     * @param commodityId the value for stock_repository.commodity_id
     */
    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId == null ? null : commodityId.trim();
    }

    /**
     * 
     * {@linkplain #commodityCode}
     *
     * @return the value of stock_repository.commodity_code
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * {@linkplain #commodityCode}
     * @param commodityCode the value for stock_repository.commodity_code
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode == null ? null : commodityCode.trim();
    }

    /**
     * 
     * {@linkplain #commodityBarcode}
     *
     * @return the value of stock_repository.commodity_barcode
     */
    public String getCommodityBarcode() {
        return commodityBarcode;
    }

    /**
     * {@linkplain #commodityBarcode}
     * @param commodityBarcode the value for stock_repository.commodity_barcode
     */
    public void setCommodityBarcode(String commodityBarcode) {
        this.commodityBarcode = commodityBarcode == null ? null : commodityBarcode.trim();
    }

    /**
     * 
     * {@linkplain #commodityName}
     *
     * @return the value of stock_repository.commodity_name
     */
    public String getCommodityName() {
        return commodityName;
    }

    /**
     * {@linkplain #commodityName}
     * @param commodityName the value for stock_repository.commodity_name
     */
    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName == null ? null : commodityName.trim();
    }

    /**
     * 
     * {@linkplain #skuCode}
     *
     * @return the value of stock_repository.sku_code
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * {@linkplain #skuCode}
     * @param skuCode the value for stock_repository.sku_code
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    /**
     * 
     * {@linkplain #skuBarcode}
     *
     * @return the value of stock_repository.sku_barcode
     */
    public String getSkuBarcode() {
        return skuBarcode;
    }

    /**
     * {@linkplain #skuBarcode}
     * @param skuBarcode the value for stock_repository.sku_barcode
     */
    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode == null ? null : skuBarcode.trim();
    }

    /**
     * 
     * {@linkplain #categoryIdOneLevel}
     *
     * @return the value of stock_repository.category_id_one_level
     */
    public String getCategoryIdOneLevel() {
        return categoryIdOneLevel;
    }

    /**
     * {@linkplain #categoryIdOneLevel}
     * @param categoryIdOneLevel the value for stock_repository.category_id_one_level
     */
    public void setCategoryIdOneLevel(String categoryIdOneLevel) {
        this.categoryIdOneLevel = categoryIdOneLevel == null ? null : categoryIdOneLevel.trim();
    }

    /**
     * 
     * {@linkplain #categoryNameOneLevel}
     *
     * @return the value of stock_repository.category_name_one_level
     */
    public String getCategoryNameOneLevel() {
        return categoryNameOneLevel;
    }

    /**
     * {@linkplain #categoryNameOneLevel}
     * @param categoryNameOneLevel the value for stock_repository.category_name_one_level
     */
    public void setCategoryNameOneLevel(String categoryNameOneLevel) {
        this.categoryNameOneLevel = categoryNameOneLevel == null ? null : categoryNameOneLevel.trim();
    }

    /**
     * 
     * {@linkplain #categoryIdTwoLevel}
     *
     * @return the value of stock_repository.category_id_two_level
     */
    public String getCategoryIdTwoLevel() {
        return categoryIdTwoLevel;
    }

    /**
     * {@linkplain #categoryIdTwoLevel}
     * @param categoryIdTwoLevel the value for stock_repository.category_id_two_level
     */
    public void setCategoryIdTwoLevel(String categoryIdTwoLevel) {
        this.categoryIdTwoLevel = categoryIdTwoLevel == null ? null : categoryIdTwoLevel.trim();
    }

    /**
     * 
     * {@linkplain #categoryNameTwoLevel}
     *
     * @return the value of stock_repository.category_name_two_level
     */
    public String getCategoryNameTwoLevel() {
        return categoryNameTwoLevel;
    }

    /**
     * {@linkplain #categoryNameTwoLevel}
     * @param categoryNameTwoLevel the value for stock_repository.category_name_two_level
     */
    public void setCategoryNameTwoLevel(String categoryNameTwoLevel) {
        this.categoryNameTwoLevel = categoryNameTwoLevel == null ? null : categoryNameTwoLevel.trim();
    }

    /**
     * 
     * {@linkplain #categoryIdThreeLevel}
     *
     * @return the value of stock_repository.category_id_three_level
     */
    public String getCategoryIdThreeLevel() {
        return categoryIdThreeLevel;
    }

    /**
     * {@linkplain #categoryIdThreeLevel}
     * @param categoryIdThreeLevel the value for stock_repository.category_id_three_level
     */
    public void setCategoryIdThreeLevel(String categoryIdThreeLevel) {
        this.categoryIdThreeLevel = categoryIdThreeLevel == null ? null : categoryIdThreeLevel.trim();
    }

    /**
     * 
     * {@linkplain #categoryNameThreeLevel}
     *
     * @return the value of stock_repository.category_name_three_level
     */
    public String getCategoryNameThreeLevel() {
        return categoryNameThreeLevel;
    }

    /**
     * {@linkplain #categoryNameThreeLevel}
     * @param categoryNameThreeLevel the value for stock_repository.category_name_three_level
     */
    public void setCategoryNameThreeLevel(String categoryNameThreeLevel) {
        this.categoryNameThreeLevel = categoryNameThreeLevel == null ? null : categoryNameThreeLevel.trim();
    }

    /**
     * 
     * {@linkplain #stocknum}
     *
     * @return the value of stock_repository.stocknum
     */
    public String getStocknum() {
        return stocknum;
    }

    /**
     * {@linkplain #stocknum}
     * @param stocknum the value for stock_repository.stocknum
     */
    public void setStocknum(String stocknum) {
        this.stocknum = stocknum == null ? null : stocknum.trim();
    }

    /**
     * 
     * {@linkplain #stockUsedCount}
     *
     * @return the value of stock_repository.stock_used_count
     */
    public String getStockUsedCount() {
        return stockUsedCount;
    }

    /**
     * {@linkplain #stockUsedCount}
     * @param stockUsedCount the value for stock_repository.stock_used_count
     */
    public void setStockUsedCount(String stockUsedCount) {
        this.stockUsedCount = stockUsedCount == null ? null : stockUsedCount.trim();
    }

    /**
     * 
     * {@linkplain #warehouseCode}
     *
     * @return the value of stock_repository.warehouse_code
     */
    public String getWarehouseCode() {
        return warehouseCode;
    }

    /**
     * {@linkplain #warehouseCode}
     * @param warehouseCode the value for stock_repository.warehouse_code
     */
    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    /**
     * 
     * {@linkplain #warehouseName}
     *
     * @return the value of stock_repository.warehouse_name
     */
    public String getWarehouseName() {
        return warehouseName;
    }

    /**
     * {@linkplain #warehouseName}
     * @param warehouseName the value for stock_repository.warehouse_name
     */
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    /**
     * 
     * {@linkplain #addressCode}
     *
     * @return the value of stock_repository.address_code
     */
    public String getAddressCode() {
        return addressCode;
    }

    /**
     * {@linkplain #addressCode}
     * @param addressCode the value for stock_repository.address_code
     */
    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode == null ? null : addressCode.trim();
    }

    /**
     * 
     * {@linkplain #addressName}
     *
     * @return the value of stock_repository.address_name
     */
    public String getAddressName() {
        return addressName;
    }

    /**
     * {@linkplain #addressName}
     * @param addressName the value for stock_repository.address_name
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName == null ? null : addressName.trim();
    }

    /**
     * 
     * {@linkplain #year}
     *
     * @return the value of stock_repository.year
     */
    public String getYear() {
        return year;
    }

    /**
     * {@linkplain #year}
     * @param year the value for stock_repository.year
     */
    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    /**
     * 
     * {@linkplain #quarter}
     *
     * @return the value of stock_repository.quarter
     */
    public String getQuarter() {
        return quarter;
    }

    /**
     * {@linkplain #quarter}
     * @param quarter the value for stock_repository.quarter
     */
    public void setQuarter(String quarter) {
        this.quarter = quarter == null ? null : quarter.trim();
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of stock_repository.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * {@linkplain #month}
     * @param month the value for stock_repository.month
     */
    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    /**
     * 
     * {@linkplain #week}
     *
     * @return the value of stock_repository.week
     */
    public String getWeek() {
        return week;
    }

    /**
     * {@linkplain #week}
     * @param week the value for stock_repository.week
     */
    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
    }

    /**
     * 
     * {@linkplain #weekNum}
     *
     * @return the value of stock_repository.week_num
     */
    public String getWeekNum() {
        return weekNum;
    }

    /**
     * {@linkplain #weekNum}
     * @param weekNum the value for stock_repository.week_num
     */
    public void setWeekNum(String weekNum) {
        this.weekNum = weekNum == null ? null : weekNum.trim();
    }

    /**
     * 
     * {@linkplain #day}
     *
     * @return the value of stock_repository.day
     */
    public String getDay() {
        return day;
    }

    /**
     * {@linkplain #day}
     * @param day the value for stock_repository.day
     */
    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    /**
     * 
     * {@linkplain #transferTime}
     *
     * @return the value of stock_repository.transfer_time
     */
    public Date getTransferTime() {
        return transferTime;
    }

    /**
     * {@linkplain #transferTime}
     * @param transferTime the value for stock_repository.transfer_time
     */
    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }
}
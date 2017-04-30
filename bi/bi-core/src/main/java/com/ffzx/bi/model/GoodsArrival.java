package com.ffzx.bi.model;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author ffzx
 * @date 2016-09-01 18:19:43
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class GoodsArrival extends DataEntity<GoodsArrival> {

    private static final long serialVersionUID = 1L;

    /**
     * 采购订单编号.
     */
    private String purchaseOrderNumber;

    /**
     * 采购模式.
     */
    private String pattern;

    /**
     * 部门id.
     */
    private String officeId;

    /**
     * 部门名称.
     */
    private String officeName;

    /**
     * 采购员id.
     */
    private String employeeId;

    /**
     * 采购员名称.
     */
    private String employeeName;

    /**
     * 采购日期.
     */
    private Date purchaseDate;

    /**
     * 收货仓库ID.
     */
    private String warehouseId;

    /**
     * 仓库名称.
     */
    private String warehouseName;

    /**
     * 仓库地址.
     */
    private String warehouseAddress;

    /**
     * 订单来源.
     */
    private String source;

    /**
     * 供应商ID.
     */
    private String supplierId;

    /**
     * 供应商名称.
     */
    private String supplierName;

    /**
     * 单据状态.
     */
    private String purchaseState;

    /**
     * 关闭状态.
     */
    private String purchaseOrderCloseState;

    /**
     * 采购订单明细ID.
     */
    private String purchaseOrderDetailId;

    /**
     * 商品名称.
     */
    private String commodityName;

    /**
     * sku编码.
     */
    private String skuCode;

    /**
     * sku条形码.
     */
    private String skuBarcode;

    /**
     * 计量单位ID.
     */
    private String unitId;

    /**
     * 计量单位名称.
     */
    private String unitName;

    /**
     * 商品规格.
     */
    private String specification;

    /**
     * 采购数量.
     */
    private BigDecimal purchaseQuantity;

    /**
     * 到货数量.
     */
    private BigDecimal receivingQuantity;

    /**
     * 质检良品数.
     */
    private BigDecimal cStorageQuantity;

    /**
     * 退货数.
     */
    private BigDecimal cRejectedQuantity;

    /**
     * 单价.
     */
    private BigDecimal unitPrice;

    /**
     * 金额.
     */
    private BigDecimal amount;

    /**
     * 税率.
     */
    private BigDecimal taxRate;

    /**
     * 税额.
     */
    private BigDecimal taxRateAmount;

    /**
     * 价税合计.
     */
    private BigDecimal leviedTotal;

    /**
     * 交货日期.
     */
    private Date deliveryDate;

    /**
     * 交货数量.
     */
    private BigDecimal deliveryQuantity;

    /**
     * 预计到货日期.
     */
    private Date expectedDate;

    /**
     * 业务关闭状态.
     */
    private String closeState;

    /**
     * 商品编码.
     */
    private String commodityCode;

    /**
     * 商品条形码.
     */
    private String commodityBarcode;

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
     * 周.
     */
    private String week;

    /**
     * 周范围.
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
     * 销售属性值id组合.
     */
    private String commodityAttributeValueIds;

    /**
     * 销售属性值组合.
     */
    private String commodityAttributeValues;

    /**
     * 销售属性id组合.
     */
    private String commodityAttributeIds;

    /**
     * 销售属性组合.
     */
    private String commodityAttributes;

    /**
     * 
     * {@linkplain #purchaseOrderNumber}
     *
     * @return the value of goods_arrival_repository.purchase_order_number
     */
    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    /**
     * {@linkplain #purchaseOrderNumber}
     * @param purchaseOrderNumber the value for goods_arrival_repository.purchase_order_number
     */
    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber == null ? null : purchaseOrderNumber.trim();
    }

    /**
     * 
     * {@linkplain #pattern}
     *
     * @return the value of goods_arrival_repository.pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * {@linkplain #pattern}
     * @param pattern the value for goods_arrival_repository.pattern
     */
    public void setPattern(String pattern) {
        this.pattern = pattern == null ? null : pattern.trim();
    }

    /**
     * 
     * {@linkplain #officeId}
     *
     * @return the value of goods_arrival_repository.office_id
     */
    public String getOfficeId() {
        return officeId;
    }

    /**
     * {@linkplain #officeId}
     * @param officeId the value for goods_arrival_repository.office_id
     */
    public void setOfficeId(String officeId) {
        this.officeId = officeId == null ? null : officeId.trim();
    }

    /**
     * 
     * {@linkplain #officeName}
     *
     * @return the value of goods_arrival_repository.office_name
     */
    public String getOfficeName() {
        return officeName;
    }

    /**
     * {@linkplain #officeName}
     * @param officeName the value for goods_arrival_repository.office_name
     */
    public void setOfficeName(String officeName) {
        this.officeName = officeName == null ? null : officeName.trim();
    }

    /**
     * 
     * {@linkplain #employeeId}
     *
     * @return the value of goods_arrival_repository.employee_id
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * {@linkplain #employeeId}
     * @param employeeId the value for goods_arrival_repository.employee_id
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId == null ? null : employeeId.trim();
    }

    /**
     * 
     * {@linkplain #employeeName}
     *
     * @return the value of goods_arrival_repository.employee_name
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * {@linkplain #employeeName}
     * @param employeeName the value for goods_arrival_repository.employee_name
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    /**
     * 
     * {@linkplain #purchaseDate}
     *
     * @return the value of goods_arrival_repository.purchase_date
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * {@linkplain #purchaseDate}
     * @param purchaseDate the value for goods_arrival_repository.purchase_date
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * 
     * {@linkplain #warehouseId}
     *
     * @return the value of goods_arrival_repository.warehouse_id
     */
    public String getWarehouseId() {
        return warehouseId;
    }

    /**
     * {@linkplain #warehouseId}
     * @param warehouseId the value for goods_arrival_repository.warehouse_id
     */
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId == null ? null : warehouseId.trim();
    }

    /**
     * 
     * {@linkplain #warehouseName}
     *
     * @return the value of goods_arrival_repository.warehouse_name
     */
    public String getWarehouseName() {
        return warehouseName;
    }

    /**
     * {@linkplain #warehouseName}
     * @param warehouseName the value for goods_arrival_repository.warehouse_name
     */
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    /**
     * 
     * {@linkplain #warehouseAddress}
     *
     * @return the value of goods_arrival_repository.warehouse_address
     */
    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    /**
     * {@linkplain #warehouseAddress}
     * @param warehouseAddress the value for goods_arrival_repository.warehouse_address
     */
    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress == null ? null : warehouseAddress.trim();
    }

    /**
     * 
     * {@linkplain #source}
     *
     * @return the value of goods_arrival_repository.source
     */
    public String getSource() {
        return source;
    }

    /**
     * {@linkplain #source}
     * @param source the value for goods_arrival_repository.source
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * 
     * {@linkplain #supplierId}
     *
     * @return the value of goods_arrival_repository.supplier_id
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * {@linkplain #supplierId}
     * @param supplierId the value for goods_arrival_repository.supplier_id
     */
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    /**
     * 
     * {@linkplain #supplierName}
     *
     * @return the value of goods_arrival_repository.supplier_name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * {@linkplain #supplierName}
     * @param supplierName the value for goods_arrival_repository.supplier_name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    /**
     * 
     * {@linkplain #purchaseState}
     *
     * @return the value of goods_arrival_repository.purchase_state
     */
    public String getPurchaseState() {
        return purchaseState;
    }

    /**
     * {@linkplain #purchaseState}
     * @param purchaseState the value for goods_arrival_repository.purchase_state
     */
    public void setPurchaseState(String purchaseState) {
        this.purchaseState = purchaseState == null ? null : purchaseState.trim();
    }

    /**
     * 
     * {@linkplain #purchaseOrderCloseState}
     *
     * @return the value of goods_arrival_repository.purchase_order_close_state
     */
    public String getPurchaseOrderCloseState() {
        return purchaseOrderCloseState;
    }

    /**
     * {@linkplain #purchaseOrderCloseState}
     * @param purchaseOrderCloseState the value for goods_arrival_repository.purchase_order_close_state
     */
    public void setPurchaseOrderCloseState(String purchaseOrderCloseState) {
        this.purchaseOrderCloseState = purchaseOrderCloseState == null ? null : purchaseOrderCloseState.trim();
    }

    /**
     * 
     * {@linkplain #purchaseOrderDetailId}
     *
     * @return the value of goods_arrival_repository.purchase_order_detail_id
     */
    public String getPurchaseOrderDetailId() {
        return purchaseOrderDetailId;
    }

    /**
     * {@linkplain #purchaseOrderDetailId}
     * @param purchaseOrderDetailId the value for goods_arrival_repository.purchase_order_detail_id
     */
    public void setPurchaseOrderDetailId(String purchaseOrderDetailId) {
        this.purchaseOrderDetailId = purchaseOrderDetailId == null ? null : purchaseOrderDetailId.trim();
    }

    /**
     * 
     * {@linkplain #commodityName}
     *
     * @return the value of goods_arrival_repository.commodity_name
     */
    public String getCommodityName() {
        return commodityName;
    }

    /**
     * {@linkplain #commodityName}
     * @param commodityName the value for goods_arrival_repository.commodity_name
     */
    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName == null ? null : commodityName.trim();
    }

    /**
     * 
     * {@linkplain #skuCode}
     *
     * @return the value of goods_arrival_repository.sku_code
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * {@linkplain #skuCode}
     * @param skuCode the value for goods_arrival_repository.sku_code
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    /**
     * 
     * {@linkplain #skuBarcode}
     *
     * @return the value of goods_arrival_repository.sku_barcode
     */
    public String getSkuBarcode() {
        return skuBarcode;
    }

    /**
     * {@linkplain #skuBarcode}
     * @param skuBarcode the value for goods_arrival_repository.sku_barcode
     */
    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode == null ? null : skuBarcode.trim();
    }

    /**
     * 
     * {@linkplain #unitId}
     *
     * @return the value of goods_arrival_repository.unit_id
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * {@linkplain #unitId}
     * @param unitId the value for goods_arrival_repository.unit_id
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 
     * {@linkplain #unitName}
     *
     * @return the value of goods_arrival_repository.unit_name
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * {@linkplain #unitName}
     * @param unitName the value for goods_arrival_repository.unit_name
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    /**
     * 
     * {@linkplain #specification}
     *
     * @return the value of goods_arrival_repository.specification
     */
    public String getSpecification() {
        return specification;
    }

    /**
     * {@linkplain #specification}
     * @param specification the value for goods_arrival_repository.specification
     */
    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    /**
     * 
     * {@linkplain #purchaseQuantity}
     *
     * @return the value of goods_arrival_repository.purchase_quantity
     */
    public BigDecimal getPurchaseQuantity() {
        return purchaseQuantity == null ? BigDecimal.ZERO : purchaseQuantity;
    }

    /**
     * {@linkplain #purchaseQuantity}
     * @param purchaseQuantity the value for goods_arrival_repository.purchase_quantity
     */
    public void setPurchaseQuantity(BigDecimal purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    /**
     * 
     * {@linkplain #receivingQuantity}
     *
     * @return the value of goods_arrival_repository.receiving_quantity
     */
    public BigDecimal getReceivingQuantity() {
        return receivingQuantity == null ? BigDecimal.ZERO : receivingQuantity;
    }

    /**
     * {@linkplain #receivingQuantity}
     * @param receivingQuantity the value for goods_arrival_repository.receiving_quantity
     */
    public void setReceivingQuantity(BigDecimal receivingQuantity) {
        this.receivingQuantity = receivingQuantity;
    }

    /**
     * 
     * {@linkplain #cStorageQuantity}
     *
     * @return the value of goods_arrival_repository.c_storage_quantity
     */
    public BigDecimal getcStorageQuantity() {
        return cStorageQuantity == null ? BigDecimal.ZERO : cStorageQuantity;
    }

    /**
     * {@linkplain #cStorageQuantity}
     * @param cStorageQuantity the value for goods_arrival_repository.c_storage_quantity
     */
    public void setcStorageQuantity(BigDecimal cStorageQuantity) {
        this.cStorageQuantity = cStorageQuantity;
    }

    /**
     * 
     * {@linkplain #cRejectedQuantity}
     *
     * @return the value of goods_arrival_repository.c_rejected_quantity
     */
    public BigDecimal getcRejectedQuantity() {
        return cRejectedQuantity == null ? BigDecimal.ZERO : cRejectedQuantity;
    }

    /**
     * {@linkplain #cRejectedQuantity}
     * @param cRejectedQuantity the value for goods_arrival_repository.c_rejected_quantity
     */
    public void setcRejectedQuantity(BigDecimal cRejectedQuantity) {
        this.cRejectedQuantity = cRejectedQuantity;
    }

    /**
     * 
     * {@linkplain #unitPrice}
     *
     * @return the value of goods_arrival_repository.unit_price
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * {@linkplain #unitPrice}
     * @param unitPrice the value for goods_arrival_repository.unit_price
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of goods_arrival_repository.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * {@linkplain #amount}
     * @param amount the value for goods_arrival_repository.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #taxRate}
     *
     * @return the value of goods_arrival_repository.tax_rate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * {@linkplain #taxRate}
     * @param taxRate the value for goods_arrival_repository.tax_rate
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * 
     * {@linkplain #taxRateAmount}
     *
     * @return the value of goods_arrival_repository.tax_rate_amount
     */
    public BigDecimal getTaxRateAmount() {
        return taxRateAmount;
    }

    /**
     * {@linkplain #taxRateAmount}
     * @param taxRateAmount the value for goods_arrival_repository.tax_rate_amount
     */
    public void setTaxRateAmount(BigDecimal taxRateAmount) {
        this.taxRateAmount = taxRateAmount;
    }

    /**
     * 
     * {@linkplain #leviedTotal}
     *
     * @return the value of goods_arrival_repository.levied_total
     */
    public BigDecimal getLeviedTotal() {
        return leviedTotal;
    }

    /**
     * {@linkplain #leviedTotal}
     * @param leviedTotal the value for goods_arrival_repository.levied_total
     */
    public void setLeviedTotal(BigDecimal leviedTotal) {
        this.leviedTotal = leviedTotal;
    }

    /**
     * 
     * {@linkplain #deliveryDate}
     *
     * @return the value of goods_arrival_repository.delivery_date
     */
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * {@linkplain #deliveryDate}
     * @param deliveryDate the value for goods_arrival_repository.delivery_date
     */
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * 
     * {@linkplain #deliveryQuantity}
     *
     * @return the value of goods_arrival_repository.delivery_quantity
     */
    public BigDecimal getDeliveryQuantity() {
        return deliveryQuantity;
    }

    /**
     * {@linkplain #deliveryQuantity}
     * @param deliveryQuantity the value for goods_arrival_repository.delivery_quantity
     */
    public void setDeliveryQuantity(BigDecimal deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

    /**
     * 
     * {@linkplain #expectedDate}
     *
     * @return the value of goods_arrival_repository.expected_date
     */
    public Date getExpectedDate() {
        return expectedDate;
    }

    /**
     * {@linkplain #expectedDate}
     * @param expectedDate the value for goods_arrival_repository.expected_date
     */
    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    /**
     * 
     * {@linkplain #closeState}
     *
     * @return the value of goods_arrival_repository.close_state
     */
    public String getCloseState() {
        return closeState;
    }

    /**
     * {@linkplain #closeState}
     * @param closeState the value for goods_arrival_repository.close_state
     */
    public void setCloseState(String closeState) {
        this.closeState = closeState == null ? null : closeState.trim();
    }

    /**
     * 
     * {@linkplain #commodityCode}
     *
     * @return the value of goods_arrival_repository.commodity_code
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * {@linkplain #commodityCode}
     * @param commodityCode the value for goods_arrival_repository.commodity_code
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode == null ? null : commodityCode.trim();
    }

    /**
     * 
     * {@linkplain #commodityBarcode}
     *
     * @return the value of goods_arrival_repository.commodity_barcode
     */
    public String getCommodityBarcode() {
        return commodityBarcode;
    }

    /**
     * {@linkplain #commodityBarcode}
     * @param commodityBarcode the value for goods_arrival_repository.commodity_barcode
     */
    public void setCommodityBarcode(String commodityBarcode) {
        this.commodityBarcode = commodityBarcode == null ? null : commodityBarcode.trim();
    }

    /**
     * 
     * {@linkplain #categoryIdOneLevel}
     *
     * @return the value of goods_arrival_repository.category_id_one_level
     */
    public String getCategoryIdOneLevel() {
        return categoryIdOneLevel;
    }

    /**
     * {@linkplain #categoryIdOneLevel}
     * @param categoryIdOneLevel the value for goods_arrival_repository.category_id_one_level
     */
    public void setCategoryIdOneLevel(String categoryIdOneLevel) {
        this.categoryIdOneLevel = categoryIdOneLevel == null ? null : categoryIdOneLevel.trim();
    }

    /**
     * 
     * {@linkplain #categoryNameOneLevel}
     *
     * @return the value of goods_arrival_repository.category_name_one_level
     */
    public String getCategoryNameOneLevel() {
        return categoryNameOneLevel;
    }

    /**
     * {@linkplain #categoryNameOneLevel}
     * @param categoryNameOneLevel the value for goods_arrival_repository.category_name_one_level
     */
    public void setCategoryNameOneLevel(String categoryNameOneLevel) {
        this.categoryNameOneLevel = categoryNameOneLevel == null ? null : categoryNameOneLevel.trim();
    }

    /**
     * 
     * {@linkplain #categoryIdTwoLevel}
     *
     * @return the value of goods_arrival_repository.category_id_two_level
     */
    public String getCategoryIdTwoLevel() {
        return categoryIdTwoLevel;
    }

    /**
     * {@linkplain #categoryIdTwoLevel}
     * @param categoryIdTwoLevel the value for goods_arrival_repository.category_id_two_level
     */
    public void setCategoryIdTwoLevel(String categoryIdTwoLevel) {
        this.categoryIdTwoLevel = categoryIdTwoLevel == null ? null : categoryIdTwoLevel.trim();
    }

    /**
     * 
     * {@linkplain #categoryNameTwoLevel}
     *
     * @return the value of goods_arrival_repository.category_name_two_level
     */
    public String getCategoryNameTwoLevel() {
        return categoryNameTwoLevel;
    }

    /**
     * {@linkplain #categoryNameTwoLevel}
     * @param categoryNameTwoLevel the value for goods_arrival_repository.category_name_two_level
     */
    public void setCategoryNameTwoLevel(String categoryNameTwoLevel) {
        this.categoryNameTwoLevel = categoryNameTwoLevel == null ? null : categoryNameTwoLevel.trim();
    }

    /**
     * 
     * {@linkplain #categoryIdThreeLevel}
     *
     * @return the value of goods_arrival_repository.category_id_three_level
     */
    public String getCategoryIdThreeLevel() {
        return categoryIdThreeLevel;
    }

    /**
     * {@linkplain #categoryIdThreeLevel}
     * @param categoryIdThreeLevel the value for goods_arrival_repository.category_id_three_level
     */
    public void setCategoryIdThreeLevel(String categoryIdThreeLevel) {
        this.categoryIdThreeLevel = categoryIdThreeLevel == null ? null : categoryIdThreeLevel.trim();
    }

    /**
     * 
     * {@linkplain #categoryNameThreeLevel}
     *
     * @return the value of goods_arrival_repository.category_name_three_level
     */
    public String getCategoryNameThreeLevel() {
        return categoryNameThreeLevel;
    }

    /**
     * {@linkplain #categoryNameThreeLevel}
     * @param categoryNameThreeLevel the value for goods_arrival_repository.category_name_three_level
     */
    public void setCategoryNameThreeLevel(String categoryNameThreeLevel) {
        this.categoryNameThreeLevel = categoryNameThreeLevel == null ? null : categoryNameThreeLevel.trim();
    }

    /**
     * 
     * {@linkplain #year}
     *
     * @return the value of goods_arrival_repository.year
     */
    public String getYear() {
        return year;
    }

    /**
     * {@linkplain #year}
     * @param year the value for goods_arrival_repository.year
     */
    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    /**
     * 
     * {@linkplain #quarter}
     *
     * @return the value of goods_arrival_repository.quarter
     */
    public String getQuarter() {
        return quarter;
    }

    /**
     * {@linkplain #quarter}
     * @param quarter the value for goods_arrival_repository.quarter
     */
    public void setQuarter(String quarter) {
        this.quarter = quarter == null ? null : quarter.trim();
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of goods_arrival_repository.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * {@linkplain #month}
     * @param month the value for goods_arrival_repository.month
     */
    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    /**
     * 
     * {@linkplain #week}
     *
     * @return the value of goods_arrival_repository.week
     */
    public String getWeek() {
        return week;
    }

    /**
     * {@linkplain #week}
     * @param week the value for goods_arrival_repository.week
     */
    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
    }

    /**
     * 
     * {@linkplain #weekNum}
     *
     * @return the value of goods_arrival_repository.week_num
     */
    public String getWeekNum() {
        return weekNum;
    }

    /**
     * {@linkplain #weekNum}
     * @param weekNum the value for goods_arrival_repository.week_num
     */
    public void setWeekNum(String weekNum) {
        this.weekNum = weekNum == null ? null : weekNum.trim();
    }

    /**
     * 
     * {@linkplain #day}
     *
     * @return the value of goods_arrival_repository.day
     */
    public String getDay() {
        return day;
    }

    /**
     * {@linkplain #day}
     * @param day the value for goods_arrival_repository.day
     */
    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    /**
     * 
     * {@linkplain #transferTime}
     *
     * @return the value of goods_arrival_repository.transfer_time
     */
    public Date getTransferTime() {
        return transferTime;
    }

    /**
     * {@linkplain #transferTime}
     * @param transferTime the value for goods_arrival_repository.transfer_time
     */
    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    /**
     * 
     * {@linkplain #commodityAttributeValueIds}
     *
     * @return the value of goods_arrival_repository.commodity_attribute_value_ids
     */
    public String getCommodityAttributeValueIds() {
        return commodityAttributeValueIds;
    }

    /**
     * {@linkplain #commodityAttributeValueIds}
     * @param commodityAttributeValueIds the value for goods_arrival_repository.commodity_attribute_value_ids
     */
    public void setCommodityAttributeValueIds(String commodityAttributeValueIds) {
        this.commodityAttributeValueIds = commodityAttributeValueIds == null ? null : commodityAttributeValueIds.trim();
    }

    /**
     * 
     * {@linkplain #commodityAttributeValues}
     *
     * @return the value of goods_arrival_repository.commodity_attribute_values
     */
    public String getCommodityAttributeValues() {
        return commodityAttributeValues;
    }

    /**
     * {@linkplain #commodityAttributeValues}
     * @param commodityAttributeValues the value for goods_arrival_repository.commodity_attribute_values
     */
    public void setCommodityAttributeValues(String commodityAttributeValues) {
        this.commodityAttributeValues = commodityAttributeValues == null ? null : commodityAttributeValues.trim();
    }

    /**
     * 
     * {@linkplain #commodityAttributeIds}
     *
     * @return the value of goods_arrival_repository.commodity_attribute_ids
     */
    public String getCommodityAttributeIds() {
        return commodityAttributeIds;
    }

    /**
     * {@linkplain #commodityAttributeIds}
     * @param commodityAttributeIds the value for goods_arrival_repository.commodity_attribute_ids
     */
    public void setCommodityAttributeIds(String commodityAttributeIds) {
        this.commodityAttributeIds = commodityAttributeIds == null ? null : commodityAttributeIds.trim();
    }

    /**
     * 
     * {@linkplain #commodityAttributes}
     *
     * @return the value of goods_arrival_repository.commodity_attributes
     */
    public String getCommodityAttributes() {
        return commodityAttributes;
    }

    /**
     * {@linkplain #commodityAttributes}
     * @param commodityAttributes the value for goods_arrival_repository.commodity_attributes
     */
    public void setCommodityAttributes(String commodityAttributes) {
        this.commodityAttributes = commodityAttributes == null ? null : commodityAttributes.trim();
    }
}
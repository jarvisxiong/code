package com.ffzx.order.model;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.util.Date;

/**
 * 
 * @author ffzx
 * @date 2016-06-03 14:33:38
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class SummarySales extends DataEntity<SummarySales> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品编号.
     */
    private String commodityCode;

    /**
     * 销售数量.
     */
    private Integer saleNum;

    /**
     * 更新时间.
     */
    private Date updateDate;

    /**
     * 
     * {@linkplain #commodityCode}
     *
     * @return the value of summary_sales.commodity_code
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * {@linkplain #commodityCode}
     * @param commodityCode the value for summary_sales.commodity_code
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode == null ? null : commodityCode.trim();
    }

    /**
     * 
     * {@linkplain #saleNum}
     *
     * @return the value of summary_sales.sale_num
     */
    public Integer getSaleNum() {
        return saleNum;
    }

    /**
     * {@linkplain #saleNum}
     * @param saleNum the value for summary_sales.sale_num
     */
    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    /**
     * 
     * {@linkplain #updateDate}
     *
     * @return the value of summary_sales.update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * {@linkplain #updateDate}
     * @param updateDate the value for summary_sales.update_date
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
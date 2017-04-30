package com.ffzx.order.model;

import java.util.Date;

import com.ffzx.commerce.framework.common.persistence.BaseEntity;

/**
 * 
 * @author ffzx
 * @date 2016-06-07 14:21:51
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class DailySkuSales extends BaseEntity<DailySkuSales> {

    private static final long serialVersionUID = 1L;

    /**
     * sku编码.
     */
    private String skuCode;

    /**
     * 商品编码.
     */
    private String commodityCode;

    /**
     * 销售数量.
     */
    private Integer saleNum;

    /**
     * 统计日期.
     */
    private Date statisticsDate;
    
    //更新参数 比对时间
    private Date startDate;
    
    private Date endDate;
    
    public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
     * 
     * {@linkplain #skuCode}
     *
     * @return the value of daily_sku_sales.sku_code
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * {@linkplain #skuCode}
     * @param skuCode the value for daily_sku_sales.sku_code
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    /**
     * 
     * {@linkplain #commodityCode}
     *
     * @return the value of daily_sku_sales.commodity_code
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * {@linkplain #commodityCode}
     * @param commodityCode the value for daily_sku_sales.commodity_code
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode == null ? null : commodityCode.trim();
    }

    /**
     * 
     * {@linkplain #saleNum}
     *
     * @return the value of daily_sku_sales.sale_num
     */
    public Integer getSaleNum() {
        return saleNum;
    }

    /**
     * {@linkplain #saleNum}
     * @param saleNum the value for daily_sku_sales.sale_num
     */
    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    /**
     * 
     * {@linkplain #statisticsDate}
     *
     * @return the value of daily_sku_sales.statistics_date
     */
    public Date getStatisticsDate() {
        return statisticsDate;
    }

    /**
     * {@linkplain #statisticsDate}
     * @param statisticsDate the value for daily_sku_sales.statistics_date
     */
    public void setStatisticsDate(Date statisticsDate) {
        this.statisticsDate = statisticsDate;
    }
}
package com.ffzx.order.model;

import java.util.Date;

import com.ffzx.commerce.framework.common.persistence.BaseEntity;

/**
 * 
 * @author ffzx
 * @date 2016-06-03 14:33:38
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class DailySales extends BaseEntity<DailySales> {

    private static final long serialVersionUID = 1L;

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
    
    
    //更新用参数
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
     * {@linkplain #commodityCode}
     *
     * @return the value of daily_sales.commodity_code
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * {@linkplain #commodityCode}
     * @param commodityCode the value for daily_sales.commodity_code
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode == null ? null : commodityCode.trim();
    }

    /**
     * 
     * {@linkplain #saleNum}
     *
     * @return the value of daily_sales.sale_num
     */
    public Integer getSaleNum() {
        return saleNum;
    }

    /**
     * {@linkplain #saleNum}
     * @param saleNum the value for daily_sales.sale_num
     */
    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    /**
     * 
     * {@linkplain #statisticsDate}
     *
     * @return the value of daily_sales.statistics_date
     */
    public Date getStatisticsDate() {
        return statisticsDate;
    }

    /**
     * {@linkplain #statisticsDate}
     * @param statisticsDate the value for daily_sales.statistics_date
     */
    public void setStatisticsDate(Date statisticsDate) {
        this.statisticsDate = statisticsDate;
    }
}
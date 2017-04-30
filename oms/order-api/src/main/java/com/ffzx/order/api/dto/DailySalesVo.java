package com.ffzx.order.api.dto;

import java.util.Date;
import java.util.List;

import com.ffzx.commerce.framework.common.persistence.BaseEntity;

 /**
 * @Description: 销量统计对外接口对象
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年6月8日 上午9:45:28
 * @version V1.0 
 *
 */
public class DailySalesVo extends BaseEntity<DailySalesVo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8959432679085048484L;
	
	/**
	 * 查询参数
	 */
	private List<String> skuCodeList;
	
	private List<String> commodityCodeList;
	
	private String dataType;//查询类型 0:根据skuCode,查询 1:根据commodityCode查询
	
	private String summaryType;//汇总类型  0:查询日销量  1:查询汇总销量
	
	private Date startDate;//查询时间开始区间
	
	private Date endDate;//查询区间结束区间
	
	/**
	 * 返回参数
	 */
	private Integer saleNums;//销量
	
	private String skuCode;
	
	private String commodityCode;
	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getSummaryType() {
		return summaryType;
	}

	public void setSummaryType(String summaryType) {
		this.summaryType = summaryType;
	}

	public Integer getSaleNums() {
		return saleNums;
	}

	public void setSaleNums(Integer saleNums) {
		this.saleNums = saleNums;
	}

	public List<String> getSkuCodeList() {
		return skuCodeList;
	}

	public void setSkuCodeList(List<String> skuCodeList) {
		this.skuCodeList = skuCodeList;
	}

	public List<String> getCommodityCodeList() {
		return commodityCodeList;
	}

	public void setCommodityCodeList(List<String> commodityCodeList) {
		this.commodityCodeList = commodityCodeList;
	}

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

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}
	
}

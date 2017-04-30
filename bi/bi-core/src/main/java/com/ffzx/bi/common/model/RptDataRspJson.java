/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 * 
 */
package com.ffzx.bi.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 */
public class RptDataRspJson
{
	private List<RptDimDataJson> chart_data = new ArrayList<RptDimDataJson>();  //图标数据
	private Map<String,List<?>> table_data = new HashMap<String,List<?>>();     //列表数据
	/**
	 * @return the chart_data
	 */
	public List<RptDimDataJson> getChart_data()
	{
		return chart_data;
	}
	/**
	 * @param chart_data the chart_data to set
	 */
	public void setChart_data(List<RptDimDataJson> chart_data)
	{
		this.chart_data = chart_data;
	}
	/**
	 * @return the table_data
	 */
	public Map<String, List<?>> getTable_data()
	{
		return table_data;
	}
	/**
	 * @param table_data the table_data to set
	 */
	public void setTable_data(Map<String, List<?>> table_data)
	{
		this.table_data = table_data;
	}
	
	

}

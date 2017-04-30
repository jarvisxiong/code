/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 * 
 */
package com.ffzx.bi.common.model;

import java.util.List;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 */
public class RptDimDataJson
{
	private String dim;       //维度ID
	private String indicator; //指标ID
	private List<?> x_data;   //x轴数据
	private String x_name;    //x轴名称
	private List<?> y_data;   //y轴数据
	private String y_name;    //y轴名称
	/**
	 * @return the dim
	 */
	public String getDim()
	{
		return dim;
	}
	/**
	 * @param dim the dim to set
	 */
	public void setDim(String dim)
	{
		this.dim = dim;
	}
	/**
	 * @return the x_data
	 */
	public List<?> getX_data()
	{
		return x_data;
	}
	/**
	 * @param x_data the x_data to set
	 */
	public void setX_data(List<?> x_data)
	{
		this.x_data = x_data;
	}
	/**
	 * @return the y_data
	 */
	public List<?> getY_data()
	{
		return y_data;
	}
	/**
	 * @param y_data the y_data to set
	 */
	public void setY_data(List<?> y_data)
	{
		this.y_data = y_data;
	}
	/**
	 * @return the indicator
	 */
	public String getIndicator()
	{
		return indicator;
	}
	/**
	 * @param indicator the indicator to set
	 */
	public void setIndicator(String indicator)
	{
		this.indicator = indicator;
	}
	public String getX_name() {
		return x_name;
	}
	public void setX_name(String x_name) {
		this.x_name = x_name;
	}
	public String getY_name() {
		return y_name;
	}
	public void setY_name(String y_name) {
		this.y_name = y_name;
	}

	
	

}

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 * 
 */
package com.ffzx.bi.common.model;

import com.ff.common.dao.annotation.FFColumn;
import com.ff.common.dao.model.FFBaseEntity;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 */
public class RptTimeBaseEntity extends FFBaseEntity
{

	
	@FFColumn(isKey=true)
	protected String id;
 	protected String year;
 	
 	
	protected String quarter;
	protected String month;
	protected String week;
	protected String day;
 	
	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	/**
	 * @return the year
	 */
	public String getYear()
	{
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year)
	{
		this.year = year;
	}
	/**
	 * @return the quarter
	 */
	public String getQuarter()
	{
		return quarter;
	}
	/**
	 * @param quarter the quarter to set
	 */
	public void setQuarter(String quarter)
	{
		this.quarter = quarter;
	}
	/**
	 * @return the month
	 */
	public String getMonth()
	{
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(String month)
	{
		this.month = month;
	}
	/**
	 * @return the week
	 */
	public String getWeek()
	{
		return week;
	}
	/**
	 * @param week the week to set
	 */
	public void setWeek(String week)
	{
		this.week = week;
	}
	/**
	 * @return the day
	 */
	public String getDay()
	{
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(String day)
	{
		this.day = day;
	}
 
	
	
}

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 * 
 */
package com.ffzx.bi.common.constant;

import java.util.Calendar;
import java.util.Date;

import com.ffzx.commerce.framework.utils.DateUtil;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 */
public enum RptTimeDimEnum
{
	DAY("day","day","yyyy/MM/dd"),
	WEEK("week","week","yyyy/MM/dd-&yyyy/MM/dd"),
	MONTH("month","month","yyyy年MM月"),
	QUARTER("quarter","quarter","yyyy年&季"),
	YEAR("year","year","yyyy年");
	
	
	/**
	 * @param value
	 * @param serviceName
	 */
	private RptTimeDimEnum(String dim,String field_name,String format)
	{
		this.dim = dim;
		this.field_name = field_name;
		this.datasource = "rptAxisDataSourceTimeImpl";
		this.format = format;
		this.name = "时间";
	}
	
	private final String dim;    
	
	private final String name;

	private final String field_name;    
    private final String datasource;
    private final String format;
 
 
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}



	/**
	 * @return the name
	 */
	public String getDim()
	{
		return dim;
	}

 

 	/**
	 * @return the field_name
	 */
	public String getField_name()
	{
		return field_name;
	}

  

	/**
	 * @return the datasource
	 */
	public String getDatasource()
	{
		return datasource;
	}
 
	/**
	 * @return the format
	 */
	public String getFormat()
	{
		return format;
	}
 
	public static RptTimeDimEnum getEnumByName(String dim)
	{
		for (RptTimeDimEnum c : RptTimeDimEnum.values())
		{
			if (c.dim.equals(dim))
			{
				return c;
			}
		}
		return null;
	}
 
	public  String format(Date startDate)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		String result = DateUtil.format(calendar.getTime(),this.getFormat());
		switch (this)
		{
		case QUARTER:
 			int month = (calendar.get(Calendar.MONTH) + 1);
			int quater = month/4;
			quater++;
			result = result.replace("&", ""+quater);
 			break;
		case WEEK:
			if(this.getFormat().contains("&"))
			{
	 			String[] f = this.getFormat().split("&");
	 			//calendar.add(Calendar.DAY_OF_YEAR, 1);
	 			Date firstTime = calendar.getTime();
 	 			calendar.add(Calendar.DAY_OF_YEAR, 6);
	 			Date nextTime = calendar.getTime();
	 			 
	 			result =   DateUtil.format(firstTime,f[0]) + DateUtil.format(nextTime,f[1]);
			}
 			
 			break;
		default:
 			break;
		}
		return result;
	}

	
	public Date nextTime(Date date)
	{
		return calcTime(date,true);
	}
	public Date lastTime(Date date)
	{
		return calcTime(date,false);
	}

	private Date calcTime(Date date,boolean isNext)
	{
		Calendar calendar = Calendar.getInstance();
 		calendar.setTime(date);
 		
 		int oldYear = calendar.get(Calendar.YEAR);

		int addNum = 1;

		int addType = 1;
		switch (this)
		{
		case YEAR:
			addType = Calendar.YEAR;
			break;
		case QUARTER:
 			addType = Calendar.MONTH;
 			addNum=3;
			break;
		case MONTH:
			addType = Calendar.MONTH;
			break;
		case WEEK:
			addType = Calendar.DAY_OF_YEAR;
  			addNum=7;
			break;
		case DAY:
			addType = Calendar.DAY_OF_YEAR;

 			break;
		default:
			break;
		}
		
		if(!isNext)
		{
			addNum = -addNum;
		}
		calendar.add(addType, addNum);
		
		if(this == RptTimeDimEnum.WEEK)
		{
			//handle the week between two years,
			int old = oldYear;
			int now = calendar.get(Calendar.YEAR);
			if(old != now)
			{
				calendar.set(Calendar.DAY_OF_YEAR, 1);
			}
		}
		 
		
		
		return calendar.getTime();
	}
}

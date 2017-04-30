package com.ff.common.util.format;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;

import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.validate.ValidatorUtil;

 

public class DateUtil
{
	private static final Logger log = FFLogFactory.getLog(DateUtil.class);

	public static final String yyyyMMddHHmmss ="yyyyMMddHHmmss";
	public static final String defaultFormat ="yyyyMMddHHmmss"; 
	public static long getDateTime(Date date)
	{
		if(null != date)
		{
			return date.getTime();
		}
		return 0 ;
	}
	public static String DateToString(Date date)
	{
		return DateToString(date, defaultFormat);
	}

	public static String DateToString(Date date,String format)
	{
		String str = null;
		//DateFormat d = DateFormat.getDateInstance(DateFormat.MEDIUM);
		SimpleDateFormat d = new SimpleDateFormat(format);

		if (null == date)
		{
			return "";
		}
		try
		{
			str = d.format(date);
		}
		catch (Exception e)
		{
			str = "";
			log.warn("the date formart is wrong." + date);
		}
		return str;

	}

	public static Date stringToDate(String date)
	{
		return stringToDate(date,defaultFormat);
	}
	
	public static Date stringToDate(String date,String format)
	{
    	Date da;
    	SimpleDateFormat sformat = new SimpleDateFormat(format);
    	if(ValidatorUtil.isEmpty(date))
    	{
    		return null;
    	}
    	try 
    	{
    	    da = sformat.parse(date);
    	   
    	} 		
    	catch (Exception e)
 		{
    		int year = 0;
    		int month = 0;
    		int day = 1;
    		int hour = 0;
    		int min = 0;
    		int sec = 0;
    		int millsec = 0;
    		int num = 0;
			String temp = "";

 			for(int i=0;i<date.length();i++)
 			{
 				char a = date.charAt(i);
 				if(ValidatorUtil.isInt(a+""))
 				{
 					num++;
 					temp += a;
 					if(4 == num)
 					{
 						year = Integer.valueOf(temp);
 						temp = "";
 					}
 					else if(6 == num)
 					{
 						month = Integer.valueOf(temp);
 						//month--;
 						temp = "";
  					}
 					else if(8 == num)
 					{
 						day = Integer.valueOf(temp);
 						temp = "";
  					}
 					else if(10 == num)
 					{
 						hour = Integer.valueOf(temp);
 						temp = "";
  					}
 					else if(12 == num)
 					{
 						min = Integer.valueOf(temp);
 						temp = "";
  					}
 					else if(14 == num)
 					{
 						sec = Integer.valueOf(temp);
 						temp = "";
  					}
 					else if(17 == num)
 					{
 						millsec = Integer.valueOf(temp);
 						temp = "";
  					}
 					
 				}
 					
 			}
 			Calendar cal = Calendar.getInstance();
 			month--;
 			cal.set(year, month, day, hour, min, sec);
 			cal.setTimeInMillis(cal.getTimeInMillis()+millsec);
    		//log.error("Err: Date Str is:" + date);
 			//throw new RuntimeException(e);
 			da = cal.getTime();

 		}
		return da;

	}
  
 

	/**
	 * get the first date of current month
	 * 
	 * @return
	 */
	public static String getCurMonthFirstDate()
	{
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		Date date = cal_1.getTime();
		return DateToString(date);
	}

	/**
	 * get the last date of current month
	 * 
	 * @return
	 */
	public static String getCurMonthLastDate()
	{
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date date = ca.getTime();

		return DateToString(date);
	}

	public static Date getCurrentDate()
	{

		return new Date(System.currentTimeMillis());
	}

	public static Timestamp getCurrentDateTime()
	{

		return new Timestamp(System.currentTimeMillis());
	}

	public static String getCurrentDateTimeStr()
	{
		Date time = new Timestamp(System.currentTimeMillis());

		return time.toString();
	}

	public static int countDayNum(Date startDate, Date endDate)
	{
		if (null == startDate || null == endDate)
		{
			return 0;
		}
		long milSec = startDate.getTime() - endDate.getTime();
		int dayNum = (int) (milSec / 1000 / 3600 / 24);
		return dayNum;

	}
	public static Date dayCalculate(Date date,int dateNum)
	{
		Calendar convertDate=Calendar.getInstance();
		convertDate.setTime(date); 
		convertDate.add(Calendar.DATE, dateNum);
		Date result=convertDate.getTime();
		return result;
		
	}
	public static Date minCalculate(Date date,int minNum)
	{
		Calendar convertDate=Calendar.getInstance();
		convertDate.setTime(date); 
		convertDate.add(Calendar.MINUTE, minNum);
		Date result=convertDate.getTime();
		return result;
		
	}
 
 

}

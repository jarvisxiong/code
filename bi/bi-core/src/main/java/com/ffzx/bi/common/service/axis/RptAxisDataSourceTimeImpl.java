/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 * 
 */
package com.ffzx.bi.common.service.axis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.ff.common.dao.model.FFCondition;
import com.ff.common.service.BaseService;
import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.meta.ReflectionUtil;
import com.ffzx.bi.common.constant.RptTimeDimEnum;
import com.ffzx.bi.common.model.RptDim;
import com.ffzx.bi.common.model.RptTimeBaseEntity;
import com.ffzx.commerce.framework.utils.DateUtil;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 */
@Service
public class RptAxisDataSourceTimeImpl extends RptAbstractAxisDataSource
{
	private Logger log = FFLogFactory.getLog(getClass());

	/* (non-Javadoc)
	 * @see com.ffzx.bi.common.service.RptAxisDataSource#getXAxisData(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<?> getXAxisData(RptDim dim,List<FFCondition> conditionList,BaseService service)
	{
		// TODO Auto-generated method stub
		Object sVal = this.getXStartValue(dim,  conditionList,service);
		Object eVal = this.getXEndValue(dim,  conditionList,service);
		List<?> data = new ArrayList<>();
		if(null == sVal || null == eVal)
		{
			log.error("can not get start or end value ,so can not get x axis data. start value is  " + sVal + ", end value is " + eVal);
		}
		else
		{
			data = getTimeDimData(dim,sVal,eVal);
		}
		
		return data;
	}
	


	/* (non-Javadoc)
	 * @see com.ffzx.bi.common.service.axis.RptAbstractAxisDataSource#getDimMapField(com.ffzx.bi.common.model.RptDim)
	 */
	@Override
	public String getDimMapField(RptDim dim)
	{
		// TODO Auto-generated method stub
		return "create_date";
	}



	private List<?> getTimeDimData(RptDim dim,Object startTime,Object endTime)
	{
	    Date startDate = (Date) ReflectionUtil.convertToType(startTime, Date.class);
	    Date endDate = (Date) ReflectionUtil.convertToType(endTime, Date.class);

		List data = new ArrayList();
		Date nowDate = startDate;
		

		RptTimeDimEnum timeDim = RptTimeDimEnum.getEnumByName(dim.getDim());
		if(timeDim == RptTimeDimEnum.WEEK)
		{
			Calendar calendar = Calendar.getInstance();
	 		calendar.setTime(nowDate);
	 		int days = calendar.get(Calendar.DAY_OF_YEAR)/7*7 +1;
	 		//log.info("days"+days);
	 		calendar.set(Calendar.DAY_OF_YEAR, days);
			nowDate = calendar.getTime();
		}
		nowDate = trimDate(nowDate,timeDim);
		while(nowDate.getTime()<=endDate.getTime())
		{
  			data.add(timeDim.format(nowDate));
  			nowDate = timeDim.nextTime(nowDate);
 		}
		
		return data;
	}
	
	private Date trimDate(Date date,RptTimeDimEnum dim)
	{
		Calendar calendar = Calendar.getInstance();
 		calendar.setTime(date);
 		
 
		switch (dim)
		{
		case YEAR:
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			break;
		case QUARTER:
		case MONTH:
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			break;
		case WEEK:
		case DAY:
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
 			break;
		default:
			break;
		}
				
		
		return calendar.getTime();
	}
	private List<RptTimeBaseEntity> getTimeDimData(RptTimeDimEnum timeDim,Object startTime,Object endTime)
	{
	    Date startDate = (Date) ReflectionUtil.convertToType(startTime, Date.class);
	    Date endDate = (Date) ReflectionUtil.convertToType(endTime, Date.class);

		List<RptTimeBaseEntity> data = new ArrayList<RptTimeBaseEntity>();
		Date nowDate = startDate;
		while(nowDate.getTime()<endDate.getTime())
		{
 			RptTimeBaseEntity timeEn = new RptTimeBaseEntity();
 			
 			for (RptTimeDimEnum c : RptTimeDimEnum.values())
 			{
 				ReflectionUtil.setObjectField(timeEn, c.getField_name(), c.format(nowDate));
 			}
 			nowDate = timeDim.nextTime(nowDate);
 			data.add(timeEn);
 		}
		
		return data;
	}
	



 
}

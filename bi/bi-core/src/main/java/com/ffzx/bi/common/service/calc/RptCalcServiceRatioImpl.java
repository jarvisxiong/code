/**
 * @Description 
 * @author tangjun
 * @date 2016年8月14日
 * 
 */
package com.ffzx.bi.common.service.calc;


import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.meta.ReflectionUtil;
import com.ffzx.bi.common.constant.RptTimeDimEnum;
import com.ffzx.bi.common.model.RptCalc;
import com.ffzx.bi.common.model.RptDim;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月14日
 */
@Service
public class RptCalcServiceRatioImpl extends RptCalcServiceMathImpl
{
	private Logger log = FFLogFactory.getLog(getClass());



	/* (non-Javadoc)
	 * @see com.ffzx.bi.common.service.calc.RptCalcService#getDimCmpValue(java.lang.Object, com.ffzx.bi.common.model.RptCalc)
	 */
	@Override
	public Object getDimCmpValue(Object nowValue,RptDim dim,RptCalc calc)
	{
		Object result = null;
		try
		{
			
			
			Date nowDate = (Date) ReflectionUtil.convertToType(nowValue, Date.class);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nowDate);
			switch (calc.getCalc())
			{
			case yoy:
				calendar.add(Calendar.YEAR, -1);
				result = calendar.getTime();
				break;
			case mom:
				//calendar.add(Calendar.MONTH, -1);
				RptTimeDimEnum dimEnum = RptTimeDimEnum.getEnumByName(dim.getDim());
				//result = calendar.getTime();

				if(null != dimEnum)
				{
					result = dimEnum.lastTime(nowDate);
				}
				break;
			default:
				break;
			} 

		}
		catch(Exception e)
		{
			log.error("can not get dim compare value when calc ratio indicator",e);
		}

		return result;
	}

}

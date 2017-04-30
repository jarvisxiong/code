/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 * 
 */
package com.ffzx.bi.common.service.calc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.ff.common.util.calc.CalcUtil;
import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.meta.ReflectionUtil;
import com.ff.common.util.validate.ValidatorUtil;
import com.ffzx.bi.common.model.RptCalc;
import com.ffzx.bi.common.model.RptDim;
import com.ffzx.bi.common.model.RptIndicator;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 */
@Service
public class RptCalcServiceMathImpl implements RptCalcService
{
	private static final String OTHER_FLAG = "other.";

	private Logger log = FFLogFactory.getLog(getClass());
	/* (non-Javadoc)
	 * @see com.ffzx.bi.common.service.calc.RptCalcService#calc(java.lang.Object, java.lang.Object, com.ffzx.bi.common.model.RptCalc, com.ffzx.bi.common.model.RptIndicator)
	 */
	@Override
	public Object calc(Object nowValue, Object compareValue, RptCalc calc, RptIndicator indicator)
	{
		CalcUtil calcUtil = new CalcUtil();
		Set<String> variableSet = calcUtil.getVariable(calc.getFormula());
		
		Map<String,Object> variableValueMap = new HashMap<String,Object>();
		for(String key : variableSet)
		{
			Object value = "";
			
			String field_name = key;
			if(key.contains(OTHER_FLAG))
			{
				field_name = field_name.replace(OTHER_FLAG, "");
				value = ReflectionUtil.getValueByFieldName(compareValue, field_name);
			}
			else
			{
				value = ReflectionUtil.getValueByFieldName(nowValue, field_name);
			}
			
			variableValueMap.put(key, value);
			
			
		}
		double value = calcUtil.calc(calc.getFormula(), variableValueMap);
		java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");
		return df.format(value);
	}

	/* (non-Javadoc)
	 * @see com.ffzx.bi.common.service.calc.RptCalcService#getDimCmpValue(java.lang.Object, com.ffzx.bi.common.model.RptCalc)
	 */
	@Override
	public Object getDimCmpValue(Object nowValue,RptDim dim,RptCalc calc)
	{
		// TODO Auto-generated method stub
		return null;
	}

}

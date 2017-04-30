/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 * 
 */
package com.ffzx.bi.common.service.calc;

import com.ffzx.bi.common.model.RptCalc;
import com.ffzx.bi.common.model.RptDim;
import com.ffzx.bi.common.model.RptIndicator;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 */
public interface RptCalcService
{
 	
	public Object calc(Object nowValue, Object compareValue,RptCalc calc, RptIndicator indicator);
	
	public Object getDimCmpValue(Object nowValue,RptDim dim,RptCalc calc);

}

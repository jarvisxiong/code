/**
 * @Description 
 * @author tangjun
 * @date 2016年8月14日
 * 
 */
package com.ffzx.bi.common.model;

import com.ffzx.bi.common.constant.RptCalcEnum;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月14日
 */
public class RptCalc
{
	private RptCalcEnum calc;
    private String formula;
	/**
	 * @return the calc
	 */
	public RptCalcEnum getCalc()
	{
		return calc;
	}
	/**
	 * @param calc the calc to set
	 */
	public void setCalc(RptCalcEnum calc)
	{
		this.calc = calc;
	}
	/**
	 * @return the formula
	 */
	public String getFormula()
	{
		return formula;
	}
	/**
	 * @param formula the formula to set
	 */
	public void setFormula(String formula)
	{
		this.formula = formula;
	}
  
    
}

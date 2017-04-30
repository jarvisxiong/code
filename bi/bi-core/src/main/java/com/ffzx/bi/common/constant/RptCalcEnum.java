/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 * 
 */
package com.ffzx.bi.common.constant;

import com.ff.common.dao.model.ConditionTypeEnum;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 */
public enum RptCalcEnum
{
	sum(ConditionTypeEnum.SUM.name,RptCalcEnum.calc_database),
	avg(ConditionTypeEnum.AVG.name,RptCalcEnum.calc_database),
	max(ConditionTypeEnum.MAX.name,RptCalcEnum.calc_database),
	min(ConditionTypeEnum.MIN.name,RptCalcEnum.calc_database),
	count(ConditionTypeEnum.COUNT.name,RptCalcEnum.calc_database),
	complex(ConditionTypeEnum.COMPLEX_CALC.name,RptCalcEnum.calc_database),

 	math("math",RptCalcEnum.calc_code,"math","rptCalcServiceMathImpl"),
	yoy("yoy",RptCalcEnum.calc_ratio,"yoy","rptCalcServiceRatioImpl"),
	mom("mom",RptCalcEnum.calc_ratio,"mon","rptCalcServiceRatioImpl");
 
	private final String name;    
    private final int calc_type;
    
    public final static int calc_database = 1;
    public final static int calc_code = 2;
    public final static int calc_ratio = 3;

    private final String formula;
    private final String service;

    RptCalcEnum(String name,int calc_type,String formula,String service)
    {
    	this.name = name;
    	this.calc_type = calc_type;
    	this.formula = formula;
    	this.service = service;
    }
    RptCalcEnum(String name,int calc_type)
    {
    	this.name = name;
    	this.calc_type = calc_type;
    	this.formula = "";
    	this.service = "";
   }
    RptCalcEnum(String name,int calc_type,String formula)
    {
    	this.name = name;
    	this.calc_type = calc_type;
    	this.formula = formula;
    	this.service = "";
     }
    public boolean isCodeCalc()
    {
    	if(this.calc_type == calc_code)
    	{
    		return true;
    	}
    	return false;
    }
    public boolean isDatabaseCalc()
    {
    	if(this.calc_type == calc_database)
    	{
    		return true;
    	}
    	return false;
    }
    public boolean isRatioCalc()
    {
    	if(this.calc_type == calc_ratio)
    	{
    		return true;
    	}
    	return false;
    }
	public static RptCalcEnum getEnumByName(String name)
	{
		for (RptCalcEnum c : RptCalcEnum.values())
		{
			if (c.name.equals(name))
			{
				return c;
			}
		}
		return null;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the calc_type
	 */
	public int getCalc_type()
	{
		return calc_type;
	}

	/**
	 * @return the formula
	 */
	public String getFormula()
	{
		return formula;
	}
	/**
	 * @return the service
	 */
	public String getService()
	{
		return service;
	}
	
	
	
}

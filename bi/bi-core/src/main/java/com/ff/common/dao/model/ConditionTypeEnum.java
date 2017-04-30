/**   
* @Title: QueryCondition.java 
* @Package cn.fuego.misp.contanst 
* @Description: TODO
* @author Tang Jun   
* @date 2014-9-24 下午04:11:10 
* @version V1.0   
*/ 
package com.ff.common.dao.model;


/**
 * 
 * @Description 
 * @author tangjun
 * @date 2016年7月5日
 */
public enum  ConditionTypeEnum
{

	
	LIKE("like","like"),
	NOT_LIKE("not_like","not like"), 
	EQUAL("eq","="), 
	NOT_EQUAL("not_eq","!="), 
	GT("gt",">"), 
	GT_EQ("gt_eq",">="),
	LT("lt","<"), 
	LT_EQ("lt_eq","<="),  
	IN("in","in"),
	LIKE_LEFT("like_left","like"),
	LIKE_RIGHT("like_right","like"),
	FALSE("false","1=2"),
	DESC_ORDER("desc","desc",ConditionTypeEnum.KIND_ORDER),
	ASC_ORDER("asc","asc",ConditionTypeEnum.KIND_ORDER),
	GROUP_BY("group by","group by",ConditionTypeEnum.KIND_GROUP_BY),
	SUM("sum","sum",ConditionTypeEnum.KIND_CALC),
	AVG("avg","avg",ConditionTypeEnum.KIND_CALC),
	MAX("max","max",ConditionTypeEnum.KIND_CALC),
	MIN("min","min",ConditionTypeEnum.KIND_CALC),
	COUNT("count","count",ConditionTypeEnum.KIND_CALC),
	COMPLEX_CALC("complex_calc","",ConditionTypeEnum.KIND_CALC),
	ORIGIN("origin","",ConditionTypeEnum.KIND_CALC);
 
    // 成员变量  
    public final String name;  
    private int kind;
    private String myBatisFlag;
    
    public static String ALL ="ALL";
    
	public static final int KIND_WHERE = 1;
	public static final int KIND_ORDER = 2;
	public static final int KIND_GROUP_BY = 3;
	public static final int KIND_CALC = 4;
    private ConditionTypeEnum(String name)
    {
    	this.name = name;
    }
    
    private ConditionTypeEnum(String name,String myBatisFlag)
    {
    	this.name = name;
     	this.myBatisFlag = myBatisFlag;
    	this.kind = ConditionTypeEnum.KIND_WHERE;

    }
    
    private ConditionTypeEnum(String name,String myBatisFlag,int kind)
    {
    	this.name = name;
     	this.myBatisFlag = myBatisFlag;
    	this.kind = kind;

    }
 
 
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	public int getKind()
	{
		return kind;
	}

	public static ConditionTypeEnum getEnumByStr(String strValue)
	{
		for (ConditionTypeEnum c : ConditionTypeEnum.values())
		{
			if (c.name.equals(strValue))
			{
				return c;
			}
		}
		return null;
	}
	/**
	 * @return the myBatisFlag
	 */
	public String getMyBatisFlag()
	{
		return myBatisFlag;
	}
	

	
	public boolean isFilterByType(int type)
	{
		if(type == KIND_WHERE)
		{
			return isWhereCondition();
		}
		if(type == KIND_ORDER)
		{
			return isOrder();
		}
		if(type == KIND_GROUP_BY)
		{
			return isGroupBy();
		}
		if(type == KIND_CALC)
		{
			return isCalc();
		}
		return false;
	}
	
	public boolean isOrder()
	{
		if(this.kind == KIND_ORDER)
		{
			return true;
		}
		return false;
	}
	
	public boolean isCalc()
	{
		if(this.kind == KIND_CALC)
		{
			return true;
		}
		return false;
	}
	
	public boolean isGroupBy()
	{
		if(this.kind == KIND_GROUP_BY)
		{
			return true;
		}
		return false;
	}
	
	public boolean isWhereCondition()
	{
		if(this.kind == KIND_WHERE)
		{
			return true;
		}
		return false;
	}
 
 
}

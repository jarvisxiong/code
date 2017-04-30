/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 * 
 */
package com.ffzx.bi.common.model;

import java.util.ArrayList;
import java.util.List;

import com.ff.common.cache.FFCacheAnno;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 */
public class RptIndicator
{
	@FFCacheAnno(isKey=true)
	private String indicator;   //指标ID
	
	private String name;        //指标名称
	
	private String table_name;  //后期扩展，表名
	private String field_name;  //对应实体的属性名称
	private List<RptCalc> calcList = new ArrayList<RptCalc>();   //计算公式
	
	public RptIndicator()
	{
		
	}
	
	public RptIndicator(String indicator,String field_name)
	{
		this.indicator = indicator;
		this.field_name = field_name;
	}
	
	//now  just support one db calc,  wating optimize
	public RptCalc getDbCalc()
	{
		List<RptCalc> calcs = new ArrayList<RptCalc>();
		for(RptCalc calc :this.calcList)
		{
			if(calc.getCalc().isDatabaseCalc())
			{
				return calc;
			}
		}
		return null;
	}
	
	public boolean hasCodeCalc()
	{
 		for(RptCalc calc :this.calcList)
		{
			if(calc.getCalc().isCodeCalc())
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean hasRatioCalc()
	{
 		for(RptCalc calc :this.calcList)
		{
			if(calc.getCalc().isRatioCalc())
			{
				return true;
			}
		}
		return false;
	}
	
	public List<RptCalc> getRatioCalc()
	{
		List<RptCalc> calcs = new ArrayList<RptCalc>();
		for(RptCalc calc :this.calcList)
		{
			if(calc.getCalc().isRatioCalc())
			{
				calcs.add(calc);
			}
		}
		return calcs;
	}
	public List<RptCalc> getCodeCalc()
	{
		List<RptCalc> calcs = new ArrayList<RptCalc>();
		for(RptCalc calc :this.calcList)
		{
			if(calc.getCalc().isCodeCalc())
			{
				calcs.add(calc);
			}
		}
		return calcs;
	}
	
	/**
	 * @return the indicator
	 */
	public String getIndicator()
	{
		return indicator;
	}
	/**
	 * @param indicator the indicator to set
	 */
	public void setIndicator(String indicator)
	{
		this.indicator = indicator;
	}
	/**
	 * @return the field_name
	 */
	public String getField_name()
	{
		return field_name;
	}
	/**
	 * @param field_name the field_name to set
	 */
	public void setField_name(String field_name)
	{
		this.field_name = field_name;
	}
 

 

	/**
	 * @return the calcList
	 */
	public List<RptCalc> getCalcList()
	{
		return calcList;
	}

	/**
	 * @param calcList the calcList to set
	 */
	public void setCalcList(List<RptCalc> calcList)
	{
		this.calcList = calcList;
	}

	/**
	 * @return the table_name
	 */
	public String getTable_name()
	{
		return table_name;
	}

	/**
	 * @param table_name the table_name to set
	 */
	public void setTable_name(String table_name)
	{
		this.table_name = table_name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((indicator == null) ? 0 : indicator.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RptIndicator other = (RptIndicator) obj;
		if (indicator == null)
		{
			if (other.indicator != null)
				return false;
		} else if (!indicator.equals(other.indicator))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "RptIndicator [indicator=" + indicator + ", table_name=" + table_name + ", field_name=" + field_name
				+ ", calcList=" + calcList + "]";
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

 
	
	

}

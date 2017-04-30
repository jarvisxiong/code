/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 * 
 */
package com.ffzx.bi.common.model;

import com.ff.common.cache.FFCacheAnno;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 */
public class RptDim
{	
	
	@FFCacheAnno(isKey=true)
	private String dim;                  //维度ID
	private String name;                 //维度名称
	private String field_name;           //对应的实体属性名
	private boolean supplement = false;  //是否要补充
    private String datasource;           //数据源
 
    
    
	/**
	 * 
	 */
	public RptDim()
	{
		super();
	}
	/**
	 * @param dim
	 */
	public RptDim(String dim)
	{
		super();
		this.dim = dim;
	}
	/**
	 * @return the name
	 */
	public String getDim()
	{
		return dim;
	}
	/**
	 * @param name the name to set
	 */
	public void setDim(String name)
	{
		this.dim = name;
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
	 * @return the supplement
	 */
	public boolean isSupplement()
	{
		return supplement;
	}
	/**
	 * @param supplement the supplement to set
	 */
	public void setSupplement(boolean supplement)
	{
		this.supplement = supplement;
	}
	/**
	 * @return the datasource
	 */
	public String getDatasource()
	{
		return datasource;
	}
	/**
	 * @param datasource the datasource to set
	 */
	public void setDatasource(String datasource)
	{
		this.datasource = datasource;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dim == null) ? 0 : dim.hashCode());
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
		RptDim other = (RptDim) obj;
		if (dim == null)
		{
			if (other.dim != null)
				return false;
		} else if (!dim.equals(other.dim))
			return false;
		return true;
	}
 
 
	
    
}

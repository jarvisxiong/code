/**   
* @Title: QueryCondition.java 
* @Package cn.fuego.misp.domain 
* @Description: TODO
* @author Tang Jun   
* @date 2014-9-24 下午04:31:29 
* @version V1.0   
*/ 
package com.ff.common.dao.model;

/**
 * 
 * @Description 
 * @author tangjun
 * @date 2016年7月5日
 */

public class FFCondition
{
	private ConditionTypeEnum conditionType;
	private String filterType;  //用于json转换，跟conditionType 保持一致
	private String myBatisFlag;  //用于mybatis 操作,跟conditionType 保持一致

	
	private String attrName;   //实体类属性名
	private Object attrValue;  //属性值
	
	
	private String columnName;  //数据库列名
	private String jdbcType;    //数据库类型
	
  
	public FFCondition()
	{
		super();
   	}
	public FFCondition(ConditionTypeEnum conditionType)
	{
		super();
		this.conditionType = conditionType;
		this.filterType = this.conditionType.getName();
		this.myBatisFlag = this.conditionType.getMyBatisFlag();

  	}
	public FFCondition(ConditionTypeEnum conditionType, String attrName)
	{
		super();
		this.conditionType = conditionType;
		this.filterType = this.conditionType.getName();
		this.myBatisFlag = this.conditionType.getMyBatisFlag();

		this.attrName = attrName;
 	}
	
	public FFCondition(ConditionTypeEnum conditionType, String attrName, Object attrValue)
	{
		super();
		this.conditionType = conditionType;
		this.filterType = this.conditionType.getName();
		this.myBatisFlag = this.conditionType.getMyBatisFlag();
		this.attrName = attrName;
		this.attrValue = attrValue;
	}
  	 
 
	/**
	 * @return the conditionType
	 */
	public ConditionTypeEnum getConditionType()
	{
		return conditionType;
	}
 
	/**
	 * @return the myBatisFlag
	 */
	public String getMyBatisFlag()
	{
		return myBatisFlag;
	}
	public String getAttrName()
	{
		return attrName;
	}
	public void setAttrName(String attrName)
	{
		this.attrName = attrName;
	}
 
	/**
	 * @return the attrValue
	 */
	public Object getAttrValue()
	{
		return attrValue;
	}
	/**
	 * @param attrValue the attrValue to set
	 */
	public void setAttrValue(Object attrValue)
	{
		this.attrValue = attrValue;
	}
	public String getFilterType()
	{
		if(null != this.conditionType)
		{
			filterType = this.conditionType.getName();

		}
		return filterType;
	}
	public void setFilterType(String filterType)
	{
		this.filterType = filterType;
		this.conditionType = ConditionTypeEnum.getEnumByStr(filterType);
		this.myBatisFlag = this.conditionType.getMyBatisFlag();
	}
 
	public String getColumnName()
	{
		return columnName;
	}
	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}
	public String getJdbcType()
	{
		return jdbcType;
	}
	public void setJdbcType(String jdbcType)
	{
		this.jdbcType = jdbcType;
	}
	/**
	 * @param conditionType the conditionType to set
	 */
	public void setConditionType(ConditionTypeEnum conditionType)
	{
		this.conditionType = conditionType;
	}
	/**
	 * @param myBatisFlag the myBatisFlag to set
	 */
	public void setMyBatisFlag(String myBatisFlag)
	{
		this.myBatisFlag = myBatisFlag;
	}
 
 

}

/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 * 
 */
package com.ff.common.dao.model;

import java.io.Serializable;
import java.util.Date;

import com.ff.common.dao.annotation.FFTable;

/**
 * @Description 
 * @author tangjun
 * @date 2016年6月28日
 */
@FFTable
public class FFBaseEntity implements Serializable
{
	public FFBaseEntity()
	{
		
	}
	
	protected Date create_date;

	public Date getCreate_date()
	{
		return create_date;
	}

	public void setCreate_date(Date create_date)
	{
		this.create_date = create_date;
	}
	

}

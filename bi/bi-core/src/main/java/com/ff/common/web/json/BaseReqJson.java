/**
 * @Description 
 * @author tangjun
 * @date 2016年8月4日
 * 
 */
package com.ff.common.web.json;

import java.io.Serializable;
import java.util.List;

import com.ff.common.dao.model.FFCondition;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月4日
 */
public class BaseReqJson<E> implements Serializable
{
	private String token;
	private PageJson page;
	private List<FFCondition> condition;
	
	private E obj;

	/**
	 * @return the obj
	 */
	public E getObj()
	{
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setObj(E obj)
	{
		this.obj = obj;
	}

	/**
	 * @return the page
	 */
	public PageJson getPage()
	{
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(PageJson page)
	{
		this.page = page;
	}

	/**
	 * @return the condition
	 */
	public List<FFCondition> getCondition()
	{
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(List<FFCondition> condition)
	{
		this.condition = condition;
	}

	/**
	 * @return the token
	 */
	public String getToken()
	{
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token)
	{
		this.token = token;
	}
	
	

}

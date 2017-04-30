/**
 * @Description 
 * @author tangjun
 * @date 2016年8月4日
 * 
 */
package com.ff.common.web.json;

import java.io.Serializable;

import com.ff.common.constant.FFErrorCode;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月4日
 */
public class BaseRspJson<E> implements Serializable
{
	private int errorCode = FFErrorCode.SUCCESS;
	private String message;
	private E obj;
	private Object obj2;
	/**
	 * @return the errorCode
	 */
	public int getErrorCode()
	{
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode)
	{
		this.errorCode = errorCode;
	}
	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
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
	 * @return the obj2
	 */
	public Object getObj2()
	{
		return obj2;
	}
	/**
	 * @param obj2 the obj2 to set
	 */
	public void setObj2(Object obj2)
	{
		this.obj2 = obj2;
	}
	
	
	
}

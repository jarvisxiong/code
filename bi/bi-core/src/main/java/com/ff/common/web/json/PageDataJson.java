/**
 * @Description 
 * @author tangjun
 * @date 2016年8月8日
 * 
 */
package com.ff.common.web.json;

import java.util.List;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月8日
 */
public class PageDataJson<E>
{
	private PageJson page;
	private List<E> dataList;
	public PageJson getPage()
	{
		return page;
	}
	public void setPage(PageJson page)
	{
		this.page = page;
	}
	public List<E> getDataList()
	{
		return dataList;
	}
	public void setDataList(List<E> dataList)
	{
		this.dataList = dataList;
	}
	
	
}

package com.ff.common.web.json;

public class PageJson  
{
	private int pageSize;
	private int currentPage;
	private long total;
	public int getPageSize()
	{
		return pageSize;
	}
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}
	public int getCurrentPage()
	{
		return currentPage;
	}
	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}
	public long getTotal()
	{
		return total;
	}
	public void setTotal(long total)
	{
		this.total = total;
	}
	
}

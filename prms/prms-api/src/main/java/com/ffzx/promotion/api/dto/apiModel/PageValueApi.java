package com.ffzx.promotion.api.dto.apiModel;

import java.util.Date;
import java.util.List;

import com.ffzx.commerce.framework.common.persistence.BaseEntity;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.promotion.api.dto.ActivityCommodity;

public class PageValueApi extends BaseEntity<ActivityCommodity> {
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * 返回的数据 分页
	 */
	private Page page;
	
	/*
	 * 返回的数据 集合
	 */
	private List<?> list;
	
	private Date endDate;
	
	



	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	
	
}

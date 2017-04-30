package com.ffzx.order.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.dto.AftersaleApplyItem;
import com.ffzx.order.api.service.AftersaleApplyItemApiService;
import com.ffzx.order.service.AftersaleApplyItemService;
@Service("afersaleApplyItemApiService")
public class AftersaleApplyItemApiServiceImpl implements AftersaleApplyItemApiService {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(AftersaleApplyItemApiServiceImpl.class);
	@Autowired
	private AftersaleApplyItemService aftersaleApplyItemService;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto saveAftersaleApply(List<AftersaleApplyItem> item) throws ServiceException {
		ResultDto  rsDto= null;
		try {
			if(!StringUtil.isNotNull(item)){
				rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + "参数有误！");
			}
			int result=0;
			for(AftersaleApplyItem i:item){
				result=this.aftersaleApplyItemService.add(i);
			}			
			if(result>0){
				rsDto = new ResultDto(ResultDto.OK_CODE,Constant.SUCCESS);
			}else{
				rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + "新增售后申请单失败！");
			}
		} catch (Exception e) {
			logger.error("AftersaleApplyItemApiServiceImpl-saveAftersaleApplyItem-Exception=》dubbo调用-saveAftersaleApplyItem", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto findAftersaleApplyItemList(Page pageObj, String orderByField, String orderBy,
			AftersaleApplyItem aftersaleApplyItem) throws ServiceException {
		ResultDto  rsDto= null;
		try {
			List<AftersaleApplyItem> aftersaleApplyItemList=(List<AftersaleApplyItem>) this.aftersaleApplyItemService.findAftersaleApplyItemList(pageObj, orderByField, orderBy, aftersaleApplyItem);
			if(StringUtil.isNotNull(aftersaleApplyItemList)){
				rsDto = new ResultDto(ResultDto.OK_CODE, "success");
				rsDto.setData(aftersaleApplyItemList);
			}
		} catch (Exception e) {
			logger.error("AftersaleApplyItemApiServiceImpl-findAftersaleApplyItemList-Exception=》dubbo调用-findAftersaleApplyItemList", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		
		return rsDto;
	}


}

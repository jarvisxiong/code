package com.ffzx.order.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.model.AftersaleRecord;

/**
 * aftersale_record数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-18 19:48:18
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface AftersaleRecordService extends BaseCrudService {

	/******
	 * 查询售后单操作记录
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<AftersaleRecord> findAftersaleRecordList(Map<String, Object> params)throws ServiceException;
	
	/******
	 * 生成售后单操作记录
	 * @param aftersaleRecord
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode saveAftersaleRecord(AftersaleRecord aftersaleRecord)throws ServiceException;
}
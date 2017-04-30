package com.ffzx.order.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.mapper.AftersaleRecordMapper;
import com.ffzx.order.model.AftersaleRecord;
import com.ffzx.order.service.AftersaleRecordService;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-05-18 19:48:18
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("aftersaleRecordService")
public class AftersaleRecordServiceImpl extends BaseCrudServiceImpl implements AftersaleRecordService {

	@Resource
	private AftersaleRecordMapper aftersaleRecordMapper;

	@Override
	public CrudMapper init() {
		return aftersaleRecordMapper;
	}

	@Override
	public List<AftersaleRecord> findAftersaleRecordList(Map<String, Object> params) throws ServiceException {

		return this.aftersaleRecordMapper.selectByParams(params);
	}

	@Override
	public ServiceCode saveAftersaleRecord(AftersaleRecord aftersaleRecord) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
}
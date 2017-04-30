package com.ffzx.order.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.mapper.CollectionRecordMapper;
import com.ffzx.order.service.CollectionRecordService;

/**
 * @className CollectionRecordServiceImpl
 *
 * @author liujunjun
 * @date 2017-01-04 10:40:01
 * @version 1.0.0
 */
@Service("collectionRecordService")
public class CollectionRecordServiceImpl extends BaseCrudServiceImpl implements CollectionRecordService {

	@Resource
	private CollectionRecordMapper collectionRecordMapper;
	
	@Override
	public CrudMapper init() {
		return collectionRecordMapper;
	}	
}
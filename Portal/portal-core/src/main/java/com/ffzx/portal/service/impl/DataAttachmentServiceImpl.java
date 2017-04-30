package com.ffzx.portal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.portal.mapper.DataAttachmentMapper;
import com.ffzx.portal.service.DataAttachmentService;

/**
 * @className DataAttachmentServiceImpl
 *
 * @author liujunjun
 * @date 2017-04-12 11:38:05
 * @version 1.0.0
 */
@Service("dataAttachmentService")
public class DataAttachmentServiceImpl extends BaseCrudServiceImpl implements DataAttachmentService {

	@Resource
	private DataAttachmentMapper dataAttachmentMapper;
	
	@Override
	public CrudMapper init() {
		return dataAttachmentMapper;
	}	
	@Override
	@Transactional
	public <T> int deleteByObjId(String objId) throws ServiceException {
		try {
			return dataAttachmentMapper.deleteByObjId(objId);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
}
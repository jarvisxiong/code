package com.ffzx.portal.service;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * @className DataAttachmentService
 *
 * @author liujunjun
 * @date 2017-04-12 11:38:05
 * @version 1.0.0
 */
public interface DataAttachmentService extends BaseCrudService{
	public <T> int deleteByObjId(String objId) throws ServiceException;
	
}

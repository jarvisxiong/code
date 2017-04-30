package com.ffzx.portal.mapper;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;

/**
 * @className DataAttachmentMapper
 *
 * @author liujunjun
 * @date 2017-04-12
 * @version 1.0.0
 */
@MyBatisDao
public interface DataAttachmentMapper extends CrudMapper {
	public int deleteByObjId(String id);

}

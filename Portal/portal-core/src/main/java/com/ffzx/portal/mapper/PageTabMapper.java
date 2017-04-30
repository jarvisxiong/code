package com.ffzx.portal.mapper;

import java.util.List;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.portal.model.PageTab;

/**
 * @className PageTabMapper
 *
 * @author liujunjun
 * @date 2017-04-12
 * @version 1.0.0
 */
@MyBatisDao
public interface PageTabMapper extends CrudMapper {

	List<PageTab> selectList();
}

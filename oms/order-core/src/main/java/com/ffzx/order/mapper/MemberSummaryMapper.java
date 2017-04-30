package com.ffzx.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;

/**
 * @className MemberSummaryMapper
 *
 * @author liujunjun
 * @date 2016-10-25
 * @version 1.0.0
 */
@MyBatisDao
public interface MemberSummaryMapper extends CrudMapper {
	public <T> List<T> selectByPageFromOrder(@Param("page") Page page,
			@Param("params") Map<String, Object> params);
}

package com.ffzx.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;

/**
 * @className PriceSettlementMapper
 *
 * @author liujunjun
 * @date 2017-03-23
 * @version 1.0.0
 */
@MyBatisDao
public interface PriceSettlementMapper extends CrudMapper {
	public <T> List<T> selectUninoDeatail(@Param("params") Map<String, Object> params);
}

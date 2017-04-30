package com.ffzx.order.mapper;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;

/**
 * @className CollectionRecordMapper
 *
 * @author liujunjun
 * @date 2017-01-04
 * @version 1.0.0
 */
@MyBatisDao
public interface CollectionRecordMapper extends CrudMapper {
	public BigDecimal selectTotalActualFines(@Param("params") Map<String, Object> params);
}

package com.ffzx.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;

/**
 * @className PriceSettlementDetailMapper
 *
 * @author liujunjun
 * @date 2017-03-23
 * @version 1.0.0
 */
@MyBatisDao
public interface PriceSettlementDetailMapper extends CrudMapper {
	public <T> List<T> selectByCountDetail(@Param("params") Map<String, Object> params);
	public <T> int updatepsNo(@Param("params") Map<String, Object> params);
	public <T> T selectByCountByPsNo(@Param("params") Map<String, Object> params);
	
}

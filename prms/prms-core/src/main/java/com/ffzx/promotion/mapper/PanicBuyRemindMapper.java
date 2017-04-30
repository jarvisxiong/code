package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;

/**
 * @className PanicBuyRemindMapper
 *
 * @author liujunjun
 * @date 2016-06-02
 * @version 1.0.0
 */
@MyBatisDao
public interface PanicBuyRemindMapper extends CrudMapper {

	public int updateScheduleIdByParams(@Param("params") Map<String, Object> params);
	
	public int countRemindByMember(@Param("params") Map<String, Object> params);
	
	public List<String> getActivityList(@Param("params") Map<String, Object> params);
}

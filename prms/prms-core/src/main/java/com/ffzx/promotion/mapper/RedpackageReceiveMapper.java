package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.promotion.api.dto.RedpackageCount;
import com.ffzx.promotion.api.dto.RedpackageReceive;

/**
 * redpackage_receive数据库操作接口
 * 
 * @author ffzx
 * @date 2016-11-07 09:41:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface RedpackageReceiveMapper extends CrudMapper {

	/**
	 * 统计订单金额
	 * @param params
	 * @return
	 */
	public RedpackageCount selectSumRedpackage(@Param("params") Map<String, Object> params);
	
	public int insertManyValue(@Param("list")List<RedpackageReceive> list);
}
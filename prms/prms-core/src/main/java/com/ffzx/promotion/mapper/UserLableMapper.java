package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;

/**
 * user_lable数据库操作接口
 * 
 * @author ffzx
 * @date 2016-11-07 09:42:57
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface UserLableMapper extends CrudMapper {

	public  List<String> selectByParamStrings(@Param("params") Map<String, Object> params);
	
	
}
package com.ffzx.promotion.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.promotion.api.dto.ActivityPreSaleTag;
import com.ffzx.promotion.model.PreSaleTag;

/**
 * activity_pre_sale_tag数据库操作接口
 * 
 * @author ffzx
 * @date 2016-08-17 17:55:51
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface ActivityPreSaleTagMapper extends CrudMapper {

	
	public <T> List<T> selectByNameOrId(@Param("params") PreSaleTag params);
	
	public ActivityPreSaleTag getTagByNumber(@Param("number") String number);
	
	public int deleteById(@Param("id")String id);
}
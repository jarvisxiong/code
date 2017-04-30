package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.promotion.api.dto.RewardManager;

/**
 * reward_manager数据库操作接口
 * 
 * @author ffzx
 * @date 2016-10-24 17:25:15
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface RewardManagerMapper extends CrudMapper {

	public List<RewardManager> selectPageByOrderBy(@Param("params") Map<String, Object> params);
	
	public Integer selectLuckNoCount();
}
package com.ffzx.promotion.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.promotion.api.dto.RewardLuckNo;

/**
 * reward_luck_no数据库操作接口
 * 
 * @author ffzx
 * @date 2016-10-24 11:24:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface RewardLuckNoMapper extends CrudMapper {

	public RewardLuckNo selectluckNoByid(@Param("id")String id);
}
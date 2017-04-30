package com.ffzx.promotion.mapper;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.promotion.api.dto.ShareOrder;

/**
 * share_order数据库操作接口
 * 
 * @author ffzx
 * @date 2016-10-24 11:24:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface ShareOrderMapper extends CrudMapper {

	public ShareOrder selectByRewardId(@Param("id")String id);
}
package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.promotion.api.dto.GiftCommodity;

/**
 * gift_commodity数据库操作接口
 * 
 * @author ffzx
 * @date 2016-09-12 11:25:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface GiftCommodityMapper extends CrudMapper {

	/**
	 * 获取赠品集合
	 * @param params
	 * @return
	 */
	public List<GiftCommodity> selectCommodityByParams(@Param("params") Map<String, Object>params);
}
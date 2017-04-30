package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.promotion.api.dto.GiftCommoditySku;

/**
 * gift_commodity_sku数据库操作接口
 * 
 * @author ffzx
 * @date 2016-09-18 12:07:18
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface GiftCommoditySkuMapper extends CrudMapper {
	
	/***
	 * 根据主商品ID 获取赠品集合
	 * @param commodityId
	 * @return
	 */
	public List<GiftCommoditySku> selectByOrder(@Param("params") Map<String, Object>params);

}
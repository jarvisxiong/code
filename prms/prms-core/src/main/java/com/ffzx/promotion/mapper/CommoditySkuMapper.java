package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;

/**
 * commodity_sku数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-18 10:12:54
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface CommoditySkuMapper extends CrudMapper {
   
	/**
	 * 获取商品详情
	 * @param params
	 * @return
	 */
	public  List<com.ffzx.promotion.api.dto.Commodity> getCommodityInfoBySku(@Param("params") Map<String, Object> params);
}
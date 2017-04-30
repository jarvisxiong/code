package com.ffzx.bi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ffzx.bi.vo.GoodsArrivalCustom;
import com.ffzx.bi.vo.GoodsArrivalVo;
import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;

/**
 * goods_arrival_repository数据库操作接口
 * 
 * @author ffzx
 * @date 2016-09-01 18:19:43
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface GoodsArrivalMapper extends CrudMapper {


	List<GoodsArrivalCustom> selectByList(@Param("page") Page page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("goodsArrivalVo") GoodsArrivalVo goodsArrivalVo);

}
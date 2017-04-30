package com.ffzx.bi.service;

import java.util.List;

import com.ffzx.bi.vo.GoodsArrivalCustom;
import com.ffzx.bi.vo.GoodsArrivalVo;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * goods_arrival_repository数据库操作接口
 * 
 * @author ffzx
 * @date 2016-09-01 18:19:43
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface GoodsArrivalService extends BaseCrudService {

	public List<GoodsArrivalCustom> findByList(Page page, String orderByField, String orderBy, 
			GoodsArrivalVo goodsArrivalVo);
	
}
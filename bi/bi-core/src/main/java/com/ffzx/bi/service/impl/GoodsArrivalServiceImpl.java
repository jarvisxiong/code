package com.ffzx.bi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.bi.mapper.GoodsArrivalMapper;
import com.ffzx.bi.service.GoodsArrivalService;
import com.ffzx.bi.vo.GoodsArrivalCustom;
import com.ffzx.bi.vo.GoodsArrivalVo;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;

/**
 * 
 * @author ffzx
 * @date 2016-09-01 18:19:43
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("goodsArrivalServiceImpl")
public class GoodsArrivalServiceImpl extends BaseCrudServiceImpl implements GoodsArrivalService {

    @Resource
    private GoodsArrivalMapper goodsArrivalMapper;

    @Override
    public CrudMapper init() {
        return goodsArrivalMapper;
    }

	@Override
	public List<GoodsArrivalCustom> findByList(Page page, String orderByField, String orderBy, GoodsArrivalVo goodsArrivalVo) {
		
		return goodsArrivalMapper.selectByList(page, orderByField, orderBy, goodsArrivalVo);
	}
}
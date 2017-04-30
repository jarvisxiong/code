package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.mapper.CommoditySkuMapper;
import com.ffzx.promotion.service.CommoditySkuService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-05-18 10:12:54
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("commoditySkuService")
public class CommoditySkuServiceImpl extends BaseCrudServiceImpl implements CommoditySkuService {

    @Resource
    private CommoditySkuMapper commoditySkuMapper;

    @Override
    public CrudMapper init() {
        return commoditySkuMapper;
    }
}
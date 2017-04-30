package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.mapper.GiftCommoditySkuMapper;
import com.ffzx.promotion.service.GiftCommoditySkuService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-09-18 12:07:18
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("giftCommoditySkuService")
public class GiftCommoditySkuServiceImpl extends BaseCrudServiceImpl implements GiftCommoditySkuService {

    @Resource
    private GiftCommoditySkuMapper giftCommoditySkuMapper;

    @Override
    public CrudMapper init() {
        return giftCommoditySkuMapper;
    }
}
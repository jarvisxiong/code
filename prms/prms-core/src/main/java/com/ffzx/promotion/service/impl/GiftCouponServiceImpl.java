package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.mapper.GiftCouponMapper;
import com.ffzx.promotion.service.GiftCouponService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-09-12 11:25:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("giftCouponService")
public class GiftCouponServiceImpl extends BaseCrudServiceImpl implements GiftCouponService {

    @Resource
    private GiftCouponMapper giftCouponMapper;

    @Override
    public CrudMapper init() {
        return giftCouponMapper;
    }
}
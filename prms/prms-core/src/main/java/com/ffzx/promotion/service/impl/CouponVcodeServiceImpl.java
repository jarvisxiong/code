package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.mapper.CouponVcodeMapper;
import com.ffzx.promotion.service.CouponVcodeService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-07-26 18:11:15
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("couponVcodeService")
public class CouponVcodeServiceImpl extends BaseCrudServiceImpl implements CouponVcodeService {

    @Resource
    private CouponVcodeMapper couponVcodeMapper;

    @Override
    public CrudMapper init() {
        return couponVcodeMapper;
    }
}
package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.mapper.CommodityMapper;
import com.ffzx.promotion.service.CommodityService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-05-18 10:12:54
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("commodityService")
public class CommodityServiceImpl extends BaseCrudServiceImpl implements CommodityService {

    @Resource
    private CommodityMapper commodityMapper;

    @Override
    public CrudMapper init() {
        return commodityMapper;
    }
}
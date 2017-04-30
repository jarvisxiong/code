package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.mapper.RedpackageCountMapper;
import com.ffzx.promotion.service.RedpackageCountService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-11-07 09:41:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("redpackageCountService")
public class RedpackageCountServiceImpl extends BaseCrudServiceImpl implements RedpackageCountService {

    @Resource
    private RedpackageCountMapper redpackageCountMapper;

    @Override
    public CrudMapper init() {
        return redpackageCountMapper;
    }
}
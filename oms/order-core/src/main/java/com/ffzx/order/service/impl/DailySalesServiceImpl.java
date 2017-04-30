package com.ffzx.order.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.mapper.DailySalesMapper;
import com.ffzx.order.service.DailySalesService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-06-03 14:33:38
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("dailySalesService")
public class DailySalesServiceImpl extends BaseCrudServiceImpl implements DailySalesService {

    @Resource
    private DailySalesMapper dailySalesMapper;

    @Override
    public CrudMapper init() {
        return dailySalesMapper;
    }
}
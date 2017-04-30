package com.ffzx.order.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.mapper.SummarySalesMapper;
import com.ffzx.order.service.SummarySalesService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-06-03 14:33:38
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("summarySalesService")
public class SummarySalesServiceImpl extends BaseCrudServiceImpl implements SummarySalesService {

    @Resource
    private SummarySalesMapper summarySalesMapper;

    @Override
    public CrudMapper init() {
        return summarySalesMapper;
    }
}
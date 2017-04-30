package com.ffzx.order.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.mapper.SummarySkuSalesMapper;
import com.ffzx.order.service.SummarySkuSalesService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-06-07 14:21:51
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("summarySkuSalesService")
public class SummarySkuSalesServiceImpl extends BaseCrudServiceImpl implements SummarySkuSalesService {

    @Resource
    private SummarySkuSalesMapper summarySkuSalesMapper;

    @Override
    public CrudMapper init() {
        return summarySkuSalesMapper;
    }
}
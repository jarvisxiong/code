package com.ffzx.order.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.mapper.PaymentRecordMapper;
import com.ffzx.order.service.PaymentRecordService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-05-10 14:04:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("paymentRecordService")
public class PaymentRecordServiceImpl extends BaseCrudServiceImpl implements PaymentRecordService {

    @Resource
    private PaymentRecordMapper paymentRecordMapper;

    @Override
    public CrudMapper init() {
        return paymentRecordMapper;
    }
}
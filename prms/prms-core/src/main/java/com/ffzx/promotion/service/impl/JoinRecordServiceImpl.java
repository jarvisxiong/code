package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.mapper.JoinRecordMapper;
import com.ffzx.promotion.service.JoinRecordService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-10-25 17:37:18
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("joinRecordService")
public class JoinRecordServiceImpl extends BaseCrudServiceImpl implements JoinRecordService {

    @Resource
    private JoinRecordMapper joinRecordMapper;

    @Override
    public CrudMapper init() {
        return joinRecordMapper;
    }
}
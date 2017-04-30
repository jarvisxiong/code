package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.mapper.OperateRecordMapper;
import com.ffzx.promotion.service.OperateRecordService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-11-01 17:16:02
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("operateRecordService")
public class OperateRecordServiceImpl extends BaseCrudServiceImpl implements OperateRecordService {

    @Resource
    private OperateRecordMapper operateRecordMapper;

    @Override
    public CrudMapper init() {
        return operateRecordMapper;
    }
}
package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.mapper.ShareDrawMapper;
import com.ffzx.promotion.service.ShareDrawService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-10-25 10:24:34
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("shareDrawService")
public class ShareDrawServiceImpl extends BaseCrudServiceImpl implements ShareDrawService {

    @Resource
    private ShareDrawMapper shareDrawMapper;

    @Override
    public CrudMapper init() {
        return shareDrawMapper;
    }
}
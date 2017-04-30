package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.api.dto.Redpackage;
import com.ffzx.promotion.api.dto.RedpackageReceive;
import com.ffzx.promotion.mapper.RedpackageReceiveMapper;
import com.ffzx.promotion.service.RedpackageReceiveService;
import com.ffzx.promotion.util.AppMapUtils;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-11-07 09:41:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("redpackageReceiveService")
public class RedpackageReceiveServiceImpl extends BaseCrudServiceImpl implements RedpackageReceiveService {

    @Resource
    private RedpackageReceiveMapper redpackageReceiveMapper;

    @Override
    public CrudMapper init() {
        return redpackageReceiveMapper;
    }

	@Override
	public List<Redpackage> findList(Page pageObj, RedpackageReceive redpackageReceive) {
		// TODO Auto-generated method stub
	    Map<String, Object> params = AppMapUtils.toMapObjcet(redpackageReceive);
		return redpackageReceiveMapper.selectByPage(pageObj, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY, params);
	}
}
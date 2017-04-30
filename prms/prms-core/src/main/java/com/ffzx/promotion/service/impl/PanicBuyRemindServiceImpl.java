package com.ffzx.promotion.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.mapper.PanicBuyRemindMapper;
import com.ffzx.promotion.service.PanicBuyRemindService;

/**
 * @className PanicBuyRemindServiceImpl
 *
 * @author shansheng
 * @date 2016-06-02 15:51:56
 * @version 1.0.0
 */
@Service("panicBuyRemindService")
public class PanicBuyRemindServiceImpl extends BaseCrudServiceImpl implements PanicBuyRemindService {

	@Resource
	private PanicBuyRemindMapper panicBuyRemindMapper;
	
	@Override
	public CrudMapper init() {
		return panicBuyRemindMapper;
	}	
}
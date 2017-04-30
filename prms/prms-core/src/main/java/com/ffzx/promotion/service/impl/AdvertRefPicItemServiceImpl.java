package com.ffzx.promotion.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.mapper.AdvertRefPicItemMapper;
import com.ffzx.promotion.service.AdvertRefPicItemService;

/**
 * @className AdvertRefPicItemServiceImpl
 *
 * @author hyl
 * @date 2016-05-03 17:36:25
 * @version 1.0.0
 */
@Service("advertRefPicItemService")
public class AdvertRefPicItemServiceImpl extends BaseCrudServiceImpl implements AdvertRefPicItemService {

	@Resource
	private AdvertRefPicItemMapper advertRefPicItemMapper;
	
	@Override
	public CrudMapper init() {
		return advertRefPicItemMapper;
	}	
}
package com.ffzx.promotion.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.promotion.mapper.AdvertPicItemMapper;
import com.ffzx.promotion.service.AdvertPicItemService;

/**
 * @className AdvertPicItemServiceImpl
 *
 * @author hyl
 * @date 2016-05-03 17:20:53
 * @version 1.0.0
 */
@Service("advertPicItemService")
public class AdvertPicItemServiceImpl extends BaseCrudServiceImpl implements AdvertPicItemService {

	@Resource
	private AdvertPicItemMapper advertPicItemMapper;
	
	@Override
	public CrudMapper init() {
		return advertPicItemMapper;
	}	
}
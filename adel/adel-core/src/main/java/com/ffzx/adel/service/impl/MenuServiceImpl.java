package com.ffzx.adel.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.adel.mapper.MenuMapper;
import com.ffzx.adel.model.Menu;
import com.ffzx.adel.service.MenuService;
import com.ffzx.common.service.impl.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

/**
 * Created by Administrator on 2017/1/17.
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu,String> implements MenuService {

	
	@Resource
    private MenuMapper mapper;
	/***
	 * 
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年4月25日 上午9:49:30
	 * @version V1.0
	 * @return 
	 */
	@Override
	public Mapper<Menu> getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}

}

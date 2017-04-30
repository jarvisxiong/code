package com.ffzx.adel.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.adel.mapper.RoominfoMapper;
import com.ffzx.adel.model.Roominfo;
import com.ffzx.adel.service.RoomInfoService;
import com.ffzx.common.service.impl.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

/**
 * Created by Administrator on 2017/1/17.
 */
@Service
public class RoomInfoServiceImpl extends BaseServiceImpl<Roominfo,String> implements RoomInfoService {
	

	@Resource
    private RoominfoMapper mapper;
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
	public Mapper<Roominfo> getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}

}

package com.ffzx.adel.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.adel.mapper.FileRepoMapper;
import com.ffzx.adel.model.FileRepo;
import com.ffzx.adel.service.FileRepoService;
import com.ffzx.common.service.impl.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

/**
 * Created by Administrator on 2017/1/17.
 */
@Service
public class FileRepoServiceImpl extends BaseServiceImpl<FileRepo,String> implements FileRepoService {

	@Resource
    private FileRepoMapper mapper;
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
	public Mapper<FileRepo> getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}

}

package com.ffzx.permission.api.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.IpUtil;
import com.ffzx.permission.api.service.TestJobApiService;
import com.ffzx.permission.model.TestPage;
import com.ffzx.permission.service.TestPageService;

/**
 * TestJobApiServiceImpl
 * 
 * @author CMM
 *
 * 2016年6月1日 上午10:55:30
 */
@Service("testJobApiService")
public class TestJobApiServiceImpl implements TestJobApiService {

	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(TestJobApiServiceImpl.class);
	
	@Autowired
	public TestPageService testPageService;
	
	@Override
	public ResultDto exeJob() {
		logger.error("TestJobApiServiceImpl ==>> 开始执行测试job");
		ResultDto rsDto = null;
		try {
			
			TestPage testPage = new TestPage();
			testPage.setCreateBy(new SysUser("1"));
			testPage.setLastUpdateBy(new SysUser("1"));
			testPage.setName("TestJobApiServiceImpl");
			testPage.setAge(18);
			testPage.setEmail(IpUtil.getRealIp() + "@163.com");
			testPage.setRemarks(DateUtil.formatDateTime(new Date()));
			testPageService.save(testPage);
			
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData("Add testPage is success!");
		} catch (Exception e) {
			logger.error("TestJobApiServiceImpl ==>> 执行测试job异常", e);
			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
		}
		logger.error("TestJobApiServiceImpl ==>> 完成执行测试job");
		return rsDto;
	}

}

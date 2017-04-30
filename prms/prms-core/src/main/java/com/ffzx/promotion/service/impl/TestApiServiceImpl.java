package com.ffzx.promotion.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.promotion.api.dto.TestVo;
import com.ffzx.promotion.api.service.TestApiService;

/**
 * @author chenjia
 *
 */
@Service
public class TestApiServiceImpl implements TestApiService{
	
	// 记录日志
	protected final  Logger logger = LoggerFactory.getLogger(getClass());
		
	@Value("${cas.server.url}")
	private String coreValue;
	
	/**
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public TestVo testDemo(String testStr) throws ServiceException{
		TestVo testVo = new TestVo();
		testVo.setTestResult(testStr);
		System.out.println(coreValue);
		logger.info("the name is {}", "ok");
		return testVo;
	}
	

}
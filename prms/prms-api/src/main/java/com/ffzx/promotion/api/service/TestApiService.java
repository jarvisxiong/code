package com.ffzx.promotion.api.service;


import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.promotion.api.dto.TestVo;

/**
 * @author chenjia
 *
 */
public interface TestApiService{

	/**
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public TestVo testDemo(String testStr) throws ServiceException;
	

}
package com.ffzx.promotion.service;


import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.promotion.api.dto.TestVo;

/**
 * @author chenjia
 *
 */
public interface TestInnerService{

	/**
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public TestVo testDemo(String testStr) throws ServiceException;
	

}
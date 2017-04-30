package com.ffzx.wms.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.promotion.api.service.TestApiService;
import com.ffzx.wms.AbstractDubboServiceTestCase;


/**
 * @author chenjia
 *
 */
public class DubboServiceTest extends AbstractDubboServiceTestCase
{
    @Autowired
    private TestApiService apiService;
    
    @Test
    public void test111() throws ServiceException{
    	
    	System.out.println(apiService.testDemo("i'm the test!!!!!!").getTestResult());
    }

    
}

package com.ffzx.permission;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.permission.model.TestPage;
import com.ffzx.permission.service.TestPageService;

/**
 * ValidTest
 * 
 * @author CMM
 *
 * 2016年5月4日 下午6:31:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-core.xml"})
public class SpringTest {
	
	@Autowired
    ApplicationContext ctx;
	
	@Autowired
	TestPageService testPageService;
	
	@Test
    public void testInsert() throws Exception{
		
		TestPageService testPageMapper = (TestPageService) ctx.getBean("testPageService");
		
		TestPage testPage = new TestPage();
		
		try {
			testPageMapper.save(testPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testSave() {
		TestPage testPage = new TestPage();
		
		//testPage.setId(UUIDGenerator.getUUID());
		testPage.setName("zhctest");
		SysUser createBy = new SysUser();
		createBy.setId("zhc001");
		testPage.setCreateBy(createBy);
		testPage.setCreateDate(new Date());
		
		SysUser lastUpdateBy = new SysUser();
		lastUpdateBy.setId("zhc002");
		testPage.setLastUpdateBy(lastUpdateBy);
		testPage.setLastUpdateDate(new Date());
		
		try {
			testPageService.save(testPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSel() {
		TestPage testPage = testPageService.findById("9e2dc90ce89c4a8dad504ff5df384bf5");
		System.out.println(":::::" + testPage);
	}
}

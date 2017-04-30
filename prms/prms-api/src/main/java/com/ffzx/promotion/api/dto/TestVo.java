package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * @author chenjia
 *
 */
public class TestVo extends DataEntity<TestVo>{

	private static final long serialVersionUID = -876962231088933845L;
	private String testResult ;
	
	public String getTestResult() {
		return "dubbo server: " + testResult;
	}
	
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

}

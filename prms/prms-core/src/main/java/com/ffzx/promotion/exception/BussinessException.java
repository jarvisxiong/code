package com.ffzx.promotion.exception;

/**
 * 
 *
 * BussinessException  业务异常类
 * @author zhang zhi yong
 * 2016年5月5日 下午7:21:22
 * @version 1.0.0
 *
 */
public class BussinessException extends RuntimeException {

	private static final long serialVersionUID = 1569920922697251378L;
	
	private String errCode ;  //异常对应的返回码 -1异常，1成功
	
	private String errorMsg;  //异常对应的描述信息
	
	public BussinessException() {
		super();
	}
	/**
	 * 
	 * 创建一个新的实例 BussinessException.
	 *
	 * @param message
	 */
	public BussinessException(String message) {
		super(message);
		errorMsg = message;
	}
	/**
	 * 
	 * 创建一个新的实例 BussinessException.
	 *
	 * @param errCode
	 * @param errorMsg
	 */
	public BussinessException(String errCode, String errorMsg) {
		super();
		this.errCode = errCode;
		this.errorMsg = errorMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}

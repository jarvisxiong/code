package com.ffzx.aftersale.api.service.exception;

/**
 * 
 *
 * BussinessException  接口调用异常类
 * @author zhang zhi yong
 * 2016年5月5日 下午7:21:22
 * @version 1.0.0
 *
 */
public class CallInterfaceExceptionApi extends RuntimeException {

	private static final long serialVersionUID = 1569920922697251378L;
	
	private int errCode ;  //异常对应的返回码
	
	private String errorMsg;  //异常对应的描述信息
	
	public CallInterfaceExceptionApi() {
		super();
	}
	/**
	 * 
	 * 创建一个新的实例 BussinessException.
	 *
	 * @param message
	 */
	public CallInterfaceExceptionApi(String message) {
		super(message);
		errorMsg = message;
	}
	/**
	 * 
	 * 创建一个新的实例 CallInterfaceException.
	 *
	 * @param errCode
	 * @param errorMsg
	 */
	public CallInterfaceExceptionApi(int errCode, String errorMsg) {
		super();
		this.errCode = errCode;
		this.errorMsg = errorMsg;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}

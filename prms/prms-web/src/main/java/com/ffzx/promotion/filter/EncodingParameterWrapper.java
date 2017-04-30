package com.ffzx.promotion.filter;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ffzx.commerce.framework.utils.Encodes;

/***
 * 参数转换器，如果是Liunx,如果get请求发送中文，则需要进行编码转换
 * @author ying.cai
 * @date 2016年11月15日 上午11:34:47
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public class EncodingParameterWrapper extends HttpServletRequestWrapper{
	
	/***默认原始编码*/
	private static String originCharacter = "ISO-8859-1";
	/***转换编码*/
	private static String character = "UTF-8";
	
	private Logger logger = LoggerFactory.getLogger(EncodingParameterWrapper.class);
	
	public EncodingParameterWrapper(HttpServletRequest request) {
		super(request);
	}
	
	public EncodingParameterWrapper(HttpServletRequest request,String originCharacter,String character) {
		this(request);
		this.originCharacter = StringUtils.isNotBlank(originCharacter)?originCharacter:this.originCharacter;
		this.character = StringUtils.isNotBlank(character)?character:this.character;
	}
	
	/***
	 * 重写获取提交参数值方法
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (value != null) {  
            value = convertCharacter(value);// 转换字符  
        }  
        return value;
	}
	
//	/***
//	 * 重写获取参数map方法
//	 */
//	@SuppressWarnings("all")
//	@Override
//	public Map getParameterMap() {
//		Map<String, String[]> map = new HashMap(super.getParameterMap());  
//        if (map != null) {  
//            for (String[] values : map.values()) {  
//                for (String value : values) {  
//                    value = convertCharacter(value);  
//                }  
//            }  
//        }  
//        return map;  
//	}
	
	/***
	 * 重写获取参数值集合方法
	 */
	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);  
        if (values != null) {  
            for (int i = 0 ; i<values.length ; i++) {  
            	values[i] =  convertCharacter( values[i] );  
            }  
        }  
        return values;  
	}
	
	/***
	 * 参数编码转换
	 * @param value
	 * @return
	 * @date 2016年11月15日 上午11:47:46
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
    private String convertCharacter(String value) {  
    	String finalValue = "";
		if(System.getProperties().getProperty("os.name").contains("Windows")){
			return Encodes.urlDecode(value);
		}
		
		try {
			finalValue =  new String(value.getBytes(originCharacter),character);
		} catch (UnsupportedEncodingException e) {
			logger.error("decode error",e);
		}
		return finalValue;
    } 
}

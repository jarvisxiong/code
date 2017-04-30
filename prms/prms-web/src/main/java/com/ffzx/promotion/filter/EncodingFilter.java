package com.ffzx.promotion.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/***
 * 解决Get请求导出文件数据乱码的问题
 * @author ying.cai
 * @date 2016年11月15日 上午10:59:19
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public class EncodingFilter implements Filter{
	
	/***原始字符编码*/
	String originCharacter = null;
	
	/***转换后字符编码*/
	String character = null;
	
	/***需要排除的路径（留作备用）*/
	private String excludePath;
	
	@Override
	public void destroy() {
		this.originCharacter = null;
		this.character = null;
		this.excludePath = null;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request=(HttpServletRequest) req;  
        String url = request.getServletPath().trim();  
        String method = request.getMethod();
        if( validPath(url) || !"GET".equals(method) ){
        	chain.doFilter(request, resp); 
        }else{
        	EncodingParameterWrapper requestWrapper = new EncodingParameterWrapper(request,originCharacter,character);  
            chain.doFilter(requestWrapper, resp);  
        }
        
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.originCharacter = filterConfig.getInitParameter("originCharacter");
		this.character = filterConfig.getInitParameter("character");
		this.excludePath = filterConfig.getInitParameter("excludePath");
	}
	
	
	/***
	 * 排除不需要的路径
	 * @param accessPath
	 * @return
	 * @date 2016年11月15日 上午11:28:54
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
    private boolean validPath(String accessPath){
    	if(StringUtils.isBlank(excludePath)){
    		return false;
    	}
    	for(String ep : excludePath.split(",")){
			if(ep.contains(".")){//匹配全路径
				if(ep.equals(accessPath))return true;
			}else{
				if(accessPath.startsWith(ep) || accessPath.equals("/"))return true;
			}
		}
    	return false;
    }

}

package com.cao.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * 公共过滤器
 */
@WebFilter(urlPatterns = {"*.html","*.jpg","*.do","*.s","*.jsp"})
public class CommonFilter implements Filter {


	public void destroy() {
		
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//设置页面内容字符集,一定要在页面之前，因为要是获取流，那么字符集就是指定的字符编码
		response.setContentType("text/html; charset=utf-8");
		//设置响应字符集
		response.setCharacterEncoding("utf-8");
		//设置请求字符集
		request.setCharacterEncoding("utf-8");
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}

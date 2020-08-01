package com.nao.thread.d0730;

public class CookieServlet  extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) {
		/**
		 * 向浏览器发送cookie 值 name=wusong
		 */
		Cookie cookie=new Cookie("name", "daniu");
		cookie.setMaxAge(60 * 60 * 24);
		response.addCookie(cookie);
		cookie = new Cookie("sex","0"); 
		cookie.setMaxAge(60 * 60 * 24); 
		response.addCookie(cookie); 

		response.getWriter().append("cookie");
		
		/**
		 * 请为cookie对象设置过期时间maxAge
		 */
	}
}

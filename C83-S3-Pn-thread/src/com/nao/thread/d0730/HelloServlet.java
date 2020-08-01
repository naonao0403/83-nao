package com.nao.thread.d0730;

import java.io.PrintWriter;

public class HelloServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) {
		
		
		PrintWriter out=response.getWriter();
		//输出页面
		out.print("<h1>hello niuniu</h1>");
		
		System.out.println("hello daniu!");
	}
}

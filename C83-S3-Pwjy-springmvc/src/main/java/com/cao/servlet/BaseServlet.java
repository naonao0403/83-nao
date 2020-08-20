package com.cao.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 一个Servlet实现所有的servlet访问，解决Servlet访问爆炸
 * 所有业务的父类  BaseActionServlet 
 * 		该类不能被直接创建成对象，如何从语法上确保
 */
//@WebServlet("/BaseServlet")
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   /**
    * ProductServlet 商品操作的servlet，产品查询、修改、删除
    * "/product.do ? op=query" 查询
    * "/product.do ? op=add" 新增
    * "/product.do ? op=del" 删除
    */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op=request.getParameter("op");
		/**
		 * java 黑科技==》反射技术
		 * 通过op获取方法对象
		 * 
		 */
		try {
			Method method=this.getClass().getDeclaredMethod(op, HttpServletRequest.class,HttpServletResponse.class);
			//设置方法可以被访问
			method.setAccessible(true);
			method.invoke(this, request,response);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().append("系统错误！");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	/**
	 * 输出页面内容
	 * @param response
	 * @param obj
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void print( HttpServletResponse response,Object obj) throws ServletException, IOException {
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json=gson.toJson(obj);
		response.getWriter().append(json);
		//response.getWriter().append(new Gson().toJson(obj));
	}
}

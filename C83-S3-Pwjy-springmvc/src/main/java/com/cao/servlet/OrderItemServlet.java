package com.cao.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.cao.dao.CartDAO;
import com.cao.dao.OrderItemDAO;
import com.cao.dao.OrdersDAO;

import com.cao.util.DBHelper;
import com.google.gson.Gson;
import com.yc.damai.bean.Result;

/**
 *订单详情表  OrderItemServlet
 */
@WebServlet("/orderitem.do")
public class OrderItemServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private OrderItemDAO oidao=new OrderItemDAO();

	/**
	 *查询这个订单下的商品是否已经评论，pstate=0表示没有评论
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected void selectByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String pid=request.getParameter("pid");
		String oid=(request.getParameter("oid"));
		if(pid!=null && oid!=null) {
			int list=	oidao.selectByPid(pid,oid);
	    	if(list==0) {
	    		print(response, new Result(0,"该商品没有评论"));
	 
	    	}else{
	    		print(response, new Result(1,"该商品已经评论!"));
	    	}
		}else {
			print(response, new Result(0,"该商品没有评论"));
		}
	}

}

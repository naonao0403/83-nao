package com.cao.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cao.dao.OrdersDAO;

/**
 * 接收支付页面返回的信息  ReturnServlet
 */
@WebServlet("/ReturnUrl")
public class ReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrdersDAO odao=new OrdersDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			System.out.println("name:"+name);
			String[] values = (String[]) requestParams.get(name);
			System.out.println("values:"+values);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
				System.out.println("valueStr:"+valueStr);
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		//获取交易的订单号
		String oid=params.get("out_trade_no");
		//1表示支付成功
		int i=odao.UpdateState("1", oid);
		
		if(i>0) {
			//跳转到首页
			response.sendRedirect("/damai/index.html");
		}
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

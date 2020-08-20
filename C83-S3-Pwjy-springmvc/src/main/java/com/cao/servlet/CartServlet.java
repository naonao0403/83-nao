package com.cao.servlet;

import java.io.IOException;
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

import com.cao.dao.CartDAO;
import com.cao.po.Result;
import com.cao.util.DBHelper;
import com.google.gson.Gson;

/**
 *购物车  CartServlet
 */
@WebServlet("/cart.do")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CartDAO cdao=new CartDAO();

	/**
	 * 添加购物车   cart.do?op=add&pid=???
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//从请求获取所有的cookies
		Cookie[] cookies=request.getCookies();
		if(cookies!=null) {
			for(Cookie c:cookies) {
				//判断cookie的名
				if(c.getName().equals("uname")) {
					//用户id从登陆用户的loginedUser获取，获取会话对象
					@SuppressWarnings("unchecked")
					Map<String , Object> user=(Map<String, Object>) request.getSession().getAttribute("LoginedUser");
					String uid=user.get("id").toString();
					String pid=request.getParameter("pid");
					if( cdao.update(uid, pid)==0 ) {
						//结果为0，该用户没有添加过该商品
						cdao.insert(uid, pid);
					}
					//response.getWriter().append("{\"msg\":\"购物车添加成功！\"}");
					print(response, new Result(1, "添加购物车成功！"));
				}
			}
		}else {
			//response.getWriter().append("{\"msg\":\"您还未登录！\"}");
			print(response, new Result(0, "您还未登录！"));
		}
	}

	/**
	 * 查询该用户下的购物车
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryByUid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//从请求获取所有的cookies
		Cookie[] cookies=request.getCookies();
		if(cookies!=null) {
			for(Cookie c:cookies) {
			//判断cookie的名
			if(c.getName().equals("uname")) {
				//用户id从登陆用户的loginedUser获取，获取会话对象
				@SuppressWarnings("unchecked")
				Map<String , Object> user=(Map<String, Object>) request.getSession().getAttribute("LoginedUser");
				if(user!=null && user.isEmpty()==false) {
					String uid=user.get("id").toString();
					System.out.println(uid);
					List<?> list=cdao.queryByUid(uid);
					List<?> cnt=cdao.cntTotal(uid);
					Map<String, Object> data=new HashMap<String, Object>();
					data.put("list", list);
					data.put("cnt", cnt);
					print(response, data);
				}
			}
			}
		}else {
			print(response, null);
		}

	}

	/**
	 * 删除单条记录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid=request.getParameter("pid");
		if(cdao.del(pid)>0) {
			response.getWriter().append("{\"msg\":\"删除成功！\"}");
		}else {
			response.getWriter().append("{\"msg\":\"删除失败！\"}");
		}
	}

	/**
	 * 统计购物车总金额
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	//	protected void cntTotal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//		//用户id从登陆用户的loginedUser获取，获取会话对象
	//		@SuppressWarnings("unchecked")
	//		Map<String , Object> user=(Map<String, Object>) request.getSession().getAttribute("LoginedUser");
	//		if(user!=null && user.isEmpty()==false) {
	//			String uid=user.get("id").toString();
	//			System.out.println(uid);
	//			List<?> list=cdao.cntTotal(uid);
	//			System.out.println(list);
	//			print(response, list);
	//		}else {
	//			print(response, null);
	//		}
	//	}


	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void clean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//用户id从登陆用户的loginedUser获取，获取会话对象
		@SuppressWarnings("unchecked")
		Map<String , Object> user=(Map<String, Object>) request.getSession().getAttribute("LoginedUser");
		String uid=user.get("id").toString();
		System.out.println(uid);
		if(cdao.clean(uid)>0) {
			response.getWriter().append("{\"msg\":\"清除成功！\"}");
		}else {
			response.getWriter().append("{\"msg\":\"清除失败！\"}");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package com.cao.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.cao.dao.EvaluateDAO;
import com.cao.dao.OrdersDAO;
import com.cao.po.Result;


/**
 *平均  EvaluateServlet
 */
@WebServlet("/evaluate.do")
public class EvaluateServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private EvaluateDAO edao=new EvaluateDAO();
	private OrdersDAO odao=new OrdersDAO();
	

	/**
	 * 保存商品
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		int pid=Integer.parseInt(request.getParameter("id"));
		String oid=request.getParameter("oid");
		String msg=request.getParameter("msg");
		String image=request.getParameter("image");
		int uid=0;
		//从请求获取所有的cookies
		Cookie[] cookies=request.getCookies();
		if(cookies!=null && cookies.length>0) {
			for(Cookie c:cookies) {
				//判断cookie的名
				if(c.getName().equals("uname")) {
					//获取会话对象
					@SuppressWarnings("unchecked")
					Map<String , Object> user=(Map<String, Object>) request.getSession().getAttribute("LoginedUser");
					uid=Integer.parseInt(user.get("id").toString());
					if(uid!=0 && pid!=0 && msg!=null && msg.isEmpty()==false) {
						edao.insert(pid, uid, msg, image);
						odao.UpdateState("4", oid);//更新状态订单状态为4，已评价
						print(response, new Result(1, "评论成功！"));
					}
				}else {
					print(response, new Result(0, "您未登录！"));
				}
			}
		}else {
			print(response, new Result(0, "评论失败，请联系卖家！"));
		}
	}

	/**
	 * 查询数据库是否有这个用户对这个条商品的评价数据
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected void queryByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		int pid=Integer.parseInt(request.getParameter("pid"));
		List<Map<String, Object>> list=	edao.selectByPid(pid);
		if(list!=null && list.size()>0) {
			print(response, list);
		}else {
			print(response, null);
		}
	}

}

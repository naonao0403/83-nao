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

import com.cao.dao.GreatDAO;
import com.cao.po.Result;
import com.cao.util.DBHelper;
import com.google.gson.Gson;

/**
 *点赞表  GreatServlet
 */
@WebServlet("/great.do")
public class GreatServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private GreatDAO gdao=new GreatDAO();

	/**
	 * 查看数据库是否有该用户点赞的数据
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryZan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//从请求获取所有的cookies
		Cookie[] cookies=request.getCookies();
		String pid=request.getParameter("pid");
		int cntZan=gdao.cntZan(pid);
		//用于传数据到前端
		Map<String, Object> data=new HashMap<String, Object>();
		if(cookies!=null) {
			for(Cookie c:cookies) {
				//判断cookie的名
				if(c.getName().equals("uname")) {
					//用户id从登陆用户的loginedUser获取，获取会话对象
					@SuppressWarnings("unchecked")
					Map<String , Object> user=(Map<String, Object>) request.getSession().getAttribute("LoginedUser");
					String uid=user.get("id").toString();				
					//查询是否有该用户对该文章的点赞记录
					List<Map<String, Object>> list=gdao.queryByUid(pid,uid);
					if (list!=null && list.size()>0){
						//返回一条记录
						//1表示该用户已经点赞
						data.put("msg", 1);
						//System.out.println("消息为1");
						//System.out.println("登陆点赞");
						data.put("list", list);

					}
				}
			}
			data.put("cntZan", cntZan);
			print(response,data);

		}else {
			//System.out.println("未登录");
			data.put("cntZan", cntZan);
			data.put("msg",-1);
			print(response,data);
		}
	}

	/**
	 * 点赞
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void addZan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//从请求获取所有的cookies
		Cookie[] cookies=request.getCookies();

		//用于传数据到前端
		Map<String, Object> data=new HashMap<String, Object>();
		if(cookies!=null) {
			for(Cookie c:cookies) {
				//判断cookie的名
				if(c.getName().equals("uname")) {
					//用户id从登陆用户的loginedUser获取，获取会话对象
					@SuppressWarnings("unchecked")
					Map<String , Object> user=(Map<String, Object>) request.getSession().getAttribute("LoginedUser");
					String uid=user.get("id").toString();
					String pid=request.getParameter("pid");
					//查询是否有该用户对该文章的点赞记录
					List<Map<String, Object>> list=gdao.queryByUid(pid,uid);
					if (list!=null && list.size()>0){//判断是否已经点赞，如果点过赞就不可以在点赞
						data.put("msg",0);
						print(response,data);
					}else {
						//返回一条记录
						//增加点赞数据
						int add=gdao.insert(uid, pid);
						data.put("msg", 1);
						print(response,data);
					}
				}
			}
		}else {
			data.put("msg",-1);//表示未登录
			print(response,data);
		}
	}

	/**
	 * 取消赞
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void cancelZan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//从请求获取所有的cookies
		Cookie[] cookies=request.getCookies();
		//用于传数据到前端
		Map<String, Object> data=new HashMap<String, Object>();
		if(cookies!=null) {
			for(Cookie c:cookies) {
				//判断cookie的名
				if(c.getName().equals("uname")) {
					//用户id从登陆用户的loginedUser获取，获取会话对象
					@SuppressWarnings("unchecked")
					Map<String , Object> user=(Map<String, Object>) request.getSession().getAttribute("LoginedUser");
					String uid=user.get("id").toString();
					String pid=request.getParameter("pid");
					//取消赞，删除一条数据
					int list=gdao.del( pid,uid);
					if (list>0){
						//返回一条记录
						data.put("msg", 1);
						print(response,data);
					}
				}
			}
		}else {
			data.put("msg",-1);//表示未登录
			print(response,data);
		}
	}

}

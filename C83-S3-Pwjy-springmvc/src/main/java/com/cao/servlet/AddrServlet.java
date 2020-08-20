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
import javax.servlet.http.HttpSession;

import com.cao.dao.AddrDAO;
import com.cao.dao.UserDAO;
import com.cao.po.Result;
import com.cao.util.DBHelper;
import com.google.gson.Gson;

/**
 *用户地址  AddrServlet
 */
@WebServlet("/addr.do")
public class AddrServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private AddrDAO adao=new AddrDAO();

	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {;
	//获取请求参数
	String name=request.getParameter("name");
	String addr=request.getParameter("addr");
	String phone=request.getParameter("phone");
	String dft=request.getParameter("dft");
	System.out.println(dft);
	//从请求获取所有的cookies
	Cookie[] cookies=request.getCookies();
	if(cookies!=null) {
		for(Cookie c:cookies) {
			//判断cookie的名
			if(c.getName().equals("uname")) {
				//获取会话对象
				@SuppressWarnings("unchecked")
				Map<String , Object> user=(Map<String, Object>) request.getSession().getAttribute("LoginedUser");
				String uid=user.get("id").toString();
				if(dft.equals("1")) {
					adao.setDft(uid);//先将数据库所有的地址设置为0，然后在添加新的默认地址
				}
				adao.insert(uid,addr,phone,name,dft);
				print(response, new Result(1, "添加成功！"));
			}
		}
	}else {
		print(response, new Result(0, "添加失败！"));
	}

	}

	/**
	 *获取用户的信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getAddr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//从请求获取所有的cookies
		Cookie[] cookies=request.getCookies();
		if(cookies!=null) {
			for(Cookie c:cookies) {
				//判断cookie的名
				if(c.getName().equals("uname")) {
					//获取会话对象
					@SuppressWarnings("unchecked")
					Map<String , Object> user=(Map<String, Object>) request.getSession().getAttribute("LoginedUser");
					String uid=user.get("id").toString();
					List<Map<String, Object>> list=adao.selectByUid(uid);
					if(list.size()>0 &&list!=null) {
						print(response, list);
					}else {
						print(response, null);
					}

				}
			}
		}

	}


	/**
	 *根据编号进行查询该地址
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void selectByAid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		List<Map<String, Object>> list= adao.selectById(id);
		if(list.size()>0 &&list!=null) {
			//使用Gson格式转成换成json格式返回给页面
			Gson gson=new Gson();
			String json=gson.toJson(list);
			response.getWriter().append(json);
		}else {
			response.getWriter().append(null);
		}

	}

	/**
	 * 保存
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		String addr=request.getParameter("addr");
		String phone=request.getParameter("phone");

		if(id!=null) {
			adao.updateAddr(id,name,addr,phone);
			print(response, new Result(1, "修改成功！"));
		}else {
			print(response, new Result(0, "修改失败"));
		}

	}

	/**
	 * 删除
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");

		if(id!=null) {
			adao.delAddr(id);
			print(response, new Result(1, "删除成功！"));
		}else {
			print(response, new Result(0, "删除失败"));
		}

	}

	/**
	 * 修改默认地址
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void updateDft(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");

		//从请求获取所有的cookies
		Cookie[] cookies=request.getCookies();
		if(cookies!=null) {
			for(Cookie c:cookies) {
				//判断cookie的名
				if(c.getName().equals("uname")) {
					//获取会话对象
					@SuppressWarnings("unchecked")
					Map<String , Object> user=(Map<String, Object>) request.getSession().getAttribute("LoginedUser");
					String uid=user.get("id").toString();
					if(id!=null) {
						adao.setDft(uid);//先将地址设置为0，然后进行修改
						adao.updateDft(id);
						print(response, new Result(1, "设置成功！"));
					}
				}
			}
		}else {
			print(response, new Result(0, "设置失败！"));
		}

	}


}

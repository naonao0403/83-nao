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

import com.cao.dao.AdminUserDAO;
import com.cao.dao.UserDAO;
import com.cao.po.Result;
import com.cao.util.DBHelper;
import com.google.gson.Gson;

/**
 *管理员   AdminUserServlet
 */
@WebServlet("/adminuser.do")
public class AdminUserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private AdminUserDAO admindao=new AdminUserDAO();
    
    /**
     * 检查账号是否已经被注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void checkName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String uname=request.getParameter("uname");
    	//System.out.println(ename);
    	if(uname!=null && uname.trim().isEmpty()==false) {
    		int list=admindao.selectByEname(uname);
        	if(list>0) {
        		print(response, new Result(0,"该用户名已经被注册了!"));//1表示可以注册，0表示不可以注册
        	}else{
        		print(response, new Result(1,"该用户名可以注册!"));
        	}
    	}else {
    		print(response, new Result(0,"用户名不能为空"));
    	}
		
		
	}
    
    /**
     * 用户注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void reg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String upass=request.getParameter("upass");
		String reupass=request.getParameter("reupass");
		String uname=request.getParameter("uname");
		String vcode=request.getParameter("vcode");
		String scode=(String) request.getSession().getAttribute("vcode");
		
		if(uname==null || uname.trim().isEmpty()) {
			print(response, new Result(2, "用户名不能为空"));
		}else if(upass==null || upass.trim().isEmpty() || reupass==null || reupass.trim().isEmpty()) {
			print(response, new Result(3, "密码不能为空"));
		}else if(vcode.equalsIgnoreCase(scode)) {//忽略大小写
			print(response, new Result(7, "验证码错误"));
		}else if(!upass.equalsIgnoreCase(reupass)) {
			print(response, new Result(8, "两次输入的密码不一致"));
		}else if( upass!=null &&  uname!=null) {
			admindao.insert(uname, upass);
			print(response, new Result(1, "注册成功！"));
		}else {
			print(response, new Result(0, "注册失败！"));
		}
		
		
		
	}
    
	
    /**
     * 登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//创建会话对象
		HttpSession session=request.getSession();
		//获取登录的用户名
		String uname=request.getParameter("uname");
		//获取密码
		String upass=request.getParameter("upass");
		//获取用户输入的验证码
		String vcode=request.getParameter("vcode");
		//获取用户是否按下记住密码按键
		String check=request.getParameter("check");
		//获取会话中的验证码
		String scode=(String) request.getSession().getAttribute("vcode");
		//获取登录的用户信息
		Map<String ,Object> user=admindao.selectByLogin(uname, upass);
		//保存登录的用户名
		Map<String ,Object> username=new HashMap<String, Object>();
		username.put("cname", uname);
		
		if(user!=null && vcode!=null && vcode.trim().isEmpty()==false) {
			if(vcode.equalsIgnoreCase(scode)) {//忽略大小写
				if(check.equals("1")) {
					//记住密码
					user.put("check", "1");
					//登录成功之后将用户信息保存到会话对象中
					session.setAttribute("LoginedAdmin",user);
					//设置会话时长为1周
					session.setMaxInactiveInterval(60*60*24*7);
					System.out.println("sessionId："+session.getId());
				}else {
					//登录成功之后将用户名保存到会话对象中
					//session.setAttribute("LoginedUser",username);
					//没有记住密码
					user.put("check", "0");
					session.setAttribute("LoginedAdmin",user);
					//设置会话时长为1周
					session.setMaxInactiveInterval(60*60*24*7);
				}
				//创建cookie对象
				Cookie cookie=new Cookie("aname", uname);
				//将cookie数据添加到响应对象中，发送浏览器
				response.addCookie(cookie);
				response.getWriter().append("登录成功!");
			}else {
				response.getWriter().append("0");//您输入的信息有误!
			}
			
		}else {
			response.getWriter().append("0");//您输入的信息有误!
		}
	}
    
    /**
     * 获取登录对象：用于登录记住用户名
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取会话对象
		@SuppressWarnings("unchecked")
		Map<String , Object> user=(Map<String, Object>) request.getSession().getAttribute("LoginedAdmin");
		//使用Gson格式转成换成json格式返回给页面
		Gson gson=new Gson();
		String json=gson.toJson(user);
		response.getWriter().append(json);
	}
    
    /**
     * 获取登录名：用于在页头显示
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getUname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//从请求获取所有的cookies
		Cookie[] cookies=request.getCookies();
		if(cookies!=null && cookies.length>0) {
			for(Cookie c:cookies) {
				//判断cookie的名
				if(c.getName().equals("aname")) {
					response.getWriter().append(c.getValue());
				}
			}
		}else {
			response.getWriter().append(null);
		}
		
	}


    /**
     * 退出：删除cookie
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void delLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 Cookie[] cookies = request.getCookies();
    	 for(Cookie c:cookies) {
 			//判断cookie的名
 			if(c.getName().equals("aname")) {
 				 //设置cookie存活时间为0
                c.setMaxAge(0);
                //将cookie响应到前台
                response.addCookie(c);
                response.getWriter().append("退出成功！");
 			}
 		}
	}

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

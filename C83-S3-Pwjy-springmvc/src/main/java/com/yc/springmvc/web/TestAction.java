package com.yc.springmvc.web;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel.MapMode;
import java.util.Date;

import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;

import org.hamcrest.Condition.Step;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.HttpSessionMutexListener;

import com.yc.damai.bean.DmUser;

@RestController
@RequestMapping("user")
/**
 * @SessionAttrubutes
 * names 	用于监听数据模型中出现指定名字的对象
 * types	用于监听数据模型中出现指定类型的对象
 */
//@SessionAttributes(names="loginUser",types = Data.class)  与 @RestController有冲突
public class TestAction {
	
	@RequestMapping("?/add")
	// user/a/add  user/b/add
	public String add() {
		return "add";
	}
	
	@RequestMapping("*/del")
	// user/a/del  user/abc/del
	public String del() {
		return "del";
	}
	
	@RequestMapping("**/mod")
	// user/a/mod  user/a/b/c/mod user/a/b/mod
	public String mod() {
		return "mod";
	}
	
	@RequestMapping("**/head")
	public String head(@RequestHeader() String accept,
			@RequestHeader(value="Connection",required = true) String conn) {
		return accept+"<br>"+conn;
	}
	
	@RequestMapping("**/cookie")
	/**
	 * document.cookie="user=zhangsan" 浏览器添加cookie
	 * @param user
	 * @param age
	 * @param cookie
	 * @return
	 */
	public String cookie(@CookieValue String user,@CookieValue int age,
			@RequestHeader String cookie) {
		return user+"<br>"+age+"<br>"+cookie;
	}
	
	/**
	 * 请求对象 响应 会话
	 * 添加servlet 依赖
	 * 应用上下文对象不能注入
	 */
	@RequestMapping("**/servlet")
	public String servlet(HttpServletRequest request,
			HttpServletResponse response,
			HttpSessionMutexListener session,
			InputStream in,
			OutputStream out) {
		return ""+request+"<br>"+response+"<br>"+session+
				"<br>"+in+"<br>"+out;
	}
	
	/**
	 * 地址参数:get请求的参数格式
	 * 标准URL中参数格式 login?user=root&pwd=123
	 * 地址参数 user/root//123/login
	 */
	@RequestMapping("user/{user}/{pwd}/login")
	public String login(@PathVariable("user") String user,@PathVariable String pwd) {
		return user+"<br>"+pwd;
	}
	
	/**
	 * 会话对象的注入
	 * SpringMVC 数据模型(Map) Model 请求过程中用于临时保存数据的对象
	 * @SessionAttrubutes  将指定对象设置到会话中 加在类上
	 */
	@RequestMapping("{user}/{pwd}/login.do")
	public String logindo(@PathVariable("user") String user,
			@PathVariable String pwd,
			Model model,
			HttpSession session) {
		DmUser du=new DmUser();
		du.setEname(user);
		du.setPassword(pwd);
		//将用户对象添加到数据模型中
		session.setAttribute("loginedUser",du);
		session.setAttribute("now",new Date());
		//model.addAttribute("age",100);
		return du.toString();
	}
	
	/**
	 * 验证会话中值
	 */
	@RequestMapping("testLogin")
	public String testLogin(@SessionAttribute("loginedUser") DmUser du,
			@SessionAttribute("now")Date date,
			@SessionAttribute(value = "age",required = false)Integer age) {
		return du+"<br>"+date+"<br>"+age;
	}
}

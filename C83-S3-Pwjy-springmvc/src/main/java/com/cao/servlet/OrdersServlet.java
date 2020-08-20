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

import com.cao.dao.AddrDAO;
import com.cao.dao.CartDAO;
import com.cao.dao.OrderItemDAO;
import com.cao.dao.OrdersDAO;
import com.cao.po.DmProduct;
import com.cao.po.Result;
import com.cao.util.DBHelper;
import com.google.gson.Gson;

/**
 *购物车  CartServlet
 */
@WebServlet("/orders.do")
public class OrdersServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private OrdersDAO odao=new OrdersDAO();
	private OrderItemDAO oidao=new OrderItemDAO();
	private CartDAO cdao=new CartDAO();
	private AddrDAO adao=new AddrDAO();

	/**
	 * 提交订单  orders.do?op=add
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
					
					int i=adao.selectByDft(uid);
					if(i>0) {
						//生成订单
						odao.insert(uid);
						//生成订单详情表
						oidao.insert(uid);	
						//清空购物车
						cdao.clean(uid);
						//response.getWriter().append("{\"code\":\"1\"}");//成功是表示1
						print(response, new Result(1, "订单生成成功！"));
					}else {
						print(response, new Result(2, "请设置默认收货地址！"));
					}

				}
			}
		}
	}


	/**
	 * 查询该用户下的新增的订单
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryNewOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
					Map<String, Object> ret=new HashMap<>();
					Map<String, Object> orders=odao.queryNewOrders(uid);
					List<?> ordersitem=oidao.queryByOid(""+orders.get("id"));
					if(ordersitem.size()>0) {
						ret.put("orders",orders);
						ret.put("orderitem",ordersitem);
						print(response,ret);
					}else {
						print(response,null);
					}
				}
			}
		}
	}


	/**
	 * 
	 * 查询已经生成的订单，用于在olist.html显示
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryOldOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid=request.getParameter("oid");
		//从请求获取所有的cookies
		Cookie[] cookies=request.getCookies();
		if(cookies!=null) {
			for(Cookie c:cookies) {
				//判断cookie的名
				if(c.getName().equals("uname")) {
					Map<String, Object> ret=new HashMap<>();
					Map<String, Object> orders=odao.queryOldOrders(oid);
					List<?> ordersitem=oidao.queryByOid(oid);
					if(ordersitem.size()>0) {
						ret.put("orders",orders);
						ret.put("orderitem",ordersitem);
						print(response,ret);
					}else {
						print(response,null);
					}
				}
			}
		}
	}
	

	/**
	 * 查询这个用户的订单
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//从请求获取所有的cookies
		Cookie[] cookies=request.getCookies();
		if(cookies!=null) {
			for(Cookie c:cookies) {
				//判断cookie的名
				if(c.getName().equals("uname")) {
					@SuppressWarnings("unchecked")
					Map<String , Object> user=(Map<String, Object>) request.getSession().getAttribute("LoginedUser");
					String uid=user.get("id").toString();
					System.out.println(uid);
					List<Map<String, Object>> olist=odao.queryOrder(uid);
					List<Map<String, Object>> oplist=odao.queryOrders(uid);
					HashMap<String,Object> data=new HashMap<>();
					data.put("olist", olist);
					data.put("oplist", oplist);
					print(response, data);
				}
			}
		}
	}
	
	/**
	 * 用户确认收货
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void shouHuo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid=request.getParameter("oid");
		System.out.println(oid);
		try {
			if(oid!=null && !oid.equals("")) {
				odao.UpdateState("3",oid);
				response.getWriter().print("收货成功");
			}else {
				response.getWriter().append("收货失败,请联系客服！");
			}
		} catch (Exception e) {
			response.getWriter().append("收货失败,请联系客服！");
		}
		
	}

	
	/**
	 * 后台使用
	 * 查询需要发货的订单，就是用户已付款等待发货
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	protected void queryPayOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");	
		/**
		 * 用于获取多个参数的工具
		 * BeanUtils.populate(bean, properties);
		 * bean: 要装载的实体对象
		 * properties: 存放属性值的map集合
		 */
		DmProduct dp=new DmProduct();
		BeanUtils.populate(dp,request.getParameterMap());
		
		List<?> list=odao.queryPayOrders(dp,page,rows);	
		int total=odao.count(dp);
		HashMap<String, Object> data=new HashMap<String, Object>();
		data.put("rows", list);
		data.put("total", total);
		print(response, data);	
	}
	
	/**
	 * 后台使用
	 * 查询需要发货的订单，就是用户已付款等待发货
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	protected void queryPayOrdersItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");	
		/**
		 * 用于获取多个参数的工具
		 * BeanUtils.populate(bean, properties);
		 * bean: 要装载的实体对象
		 * properties: 存放属性值的map集合
		 */
		DmProduct dp=new DmProduct();
		BeanUtils.populate(dp,request.getParameterMap());
		
		List<?> list=odao.queryPayOrdersItem(dp,page,rows);	
		int total=odao.count(dp);
		HashMap<String, Object> data=new HashMap<String, Object>();
		data.put("rows", list);
		data.put("total", total);
		print(response, data);	
	}
	
	
	/**
	 * 后台
	 * 发货
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void faHuo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid=request.getParameter("oid");
		System.out.println(oid);
		try {
			if(oid!=null && !oid.equals("")) {
				odao.UpdateState("2",oid);
				response.getWriter().print("发货成功");
			}else {
				response.getWriter().append("发货失败,请联系管理员！");
			}
		} catch (Exception e) {
			response.getWriter().append("发货失败,请联系管理员！");
		}
		
	}

}

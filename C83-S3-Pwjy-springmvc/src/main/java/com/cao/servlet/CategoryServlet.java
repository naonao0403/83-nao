package com.cao.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.cao.dao.CategoryDAO;
import com.cao.po.DmCategory;
import com.cao.po.Result;

/**
 *商品分类  CategoryServlet
 */
@WebServlet("/category.do")
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO catedao=new CategoryDAO();


	/*
	 * 后台服务
	 * 查询所有商品分类
	 */
	protected void queryCategorys(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows"); 

		DmCategory dc=new DmCategory();
		BeanUtils.populate(dc, request.getParameterMap());

		
		List<Map<String, Object>> list = catedao.queryCategory(dc,page,rows);
		//统计总行数
		int total = catedao.countPages(dc);

		HashMap<String,Object> data = new HashMap<>();
		data.put("rows", list);
		data.put("total", total);
		print( response, data);

	}

	/**
	 * 后台服务
	 * 保存商品管理信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		DmCategory dc=new DmCategory();
		BeanUtils.populate(dc, request.getParameterMap());
		if(dc.getCname()==null || dc.getCname().trim().isEmpty()) {
			print(response,new Result(0,"名称不能为空"));
			return ;
		}
		if(dc.getPid()==null || dc.getPid()<=0) {
			print(response,new Result(0,"编号不能小于0或者为空"));
			return ;
		}
		if(dc.getId()==0) {
			catedao.insert(dc);
		}else {
			catedao.update(dc);
		}

		print(response,new Result(1,"商品类保存成功"));
	}

	/**
	 * 删除商品
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void delCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		System.out.println(id);
		try {
			if(id!=null && !id.equals("")) {
				catedao.delete(id);
				response.getWriter().print("删除成功");
			}else {
				response.getWriter().append("删除失败");
			}
		} catch (Exception e) {
			response.getWriter().append("删除失败");
		}
		
	}
	

	/**
	 * 不分页的查询
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		List<?> list=catedao.query();	
		print(response, list);	
	}

	/**
	 * 显示菜单栏
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		List<Map<String, Object>>list=catedao.queryMenu();
//		HashMap<String,Object>page=new HashMap<>();
//		page.put("list", list);
//		print(response,page);
		print(response,list);
	}

	/**
	 * 分页查询
	 * 查询分类
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		List<Map<String, Object>> list=catedao.queryCategory();
		HashMap<String,Object>page=new HashMap<>();
		page.put("list", list);
		print(response,page);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

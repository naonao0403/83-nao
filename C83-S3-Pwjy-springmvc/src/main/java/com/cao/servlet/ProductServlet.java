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

import com.cao.dao.ProductDAO;
import com.cao.po.DmProduct;
import com.cao.po.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *产品  ProductServlet
 */
@WebServlet("/product.do")
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private ProductDAO pdao=new ProductDAO();
      
	/**
	 * 前台页面
	 * 分页查询及商品展示
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	protected void queryPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {	
		//获取分页参数，第几页
		String page=request.getParameter("page");
		//获取cid
		String cid=request.getParameter("cid");
		
		int iPage=1;
		if(page!=null && page.trim().isEmpty()==false) {
			iPage=Integer.valueOf(page);
		}
		//分页查询
		List<Map<String, Object>> list=pdao.queryPage(cid,iPage,12);
		//统计总页数
		int pages=pdao.countPages(cid,10);
		
		//使用对象保存list和pages 使用Map可以实现
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("plist", list);
		data.put("pages", pages);
		print(response, data);	
	}
	
   /**
    * 查询热销商品
    * @param request
    * @param response
    * @throws ServletException
    * @throws IOException
    */
	protected void queryHot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		List<Map<String, Object>> list=pdao.queryHot();
		HashMap<String, Object> page=new HashMap<String, Object>();
		page.put("list", list);
		print(response, page);		
	}

	/**
	 * 查询最新商品
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryNew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Map<String,Object>> list =pdao.queryNew();
		HashMap<String,Object> page=new HashMap<String, Object>();
		page.put("list",list);
		print(response,page);
	}
	
	/**
	 * 查询某件商品
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		List<?> list=pdao.queryById(id);
		print(response, list.get(0));		
	}
	
	/**
	 * 后台页面
	 * 给后台使用，查询全部商品
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {	
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
		
		//System.out.println(rows);
		List<?> list=pdao.query(dp,page,rows);	
		int total=pdao.count(dp);
		HashMap<String, Object> data=new HashMap<String, Object>();
		data.put("rows", list);
		data.put("total", total);
		print(response, data);	
	}

	/**
	 * 保存商品
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		/**
		 * 用于获取多个参数的工具
		 * BeanUtils.populate(bean, properties);
		 * bean: 要装载的实体对象
		 * properties: 存放属性值的map集合
		 */
		DmProduct dp=new DmProduct();
		//装载方法
		BeanUtils.populate(dp,request.getParameterMap());
		//商品名称  空值的验证，长度判断 
		if(dp.getPname()==null || dp.getPname().trim().isEmpty()) {
			Result r=new Result(0, "商品名称不能为空!");
			print(response,r);
			return ;				
		}	
		if(dp.getShopPrice()==null || dp.getShopPrice()<=0) {
			print(response,new Result(0, "商品价格必须大于0!"));
			return ;				
		}
		
		//System.out.println(dp.getId());
		//判断id的值是否是0，如果为0表示新增数据，否则就表示更新
		if(dp.getId()==0) {
			pdao.insert(dp);
		}else{
			pdao.update(dp);
		}
		
		print(response, new Result(1, "商品保存成功！"));
	}
	
	/**
	 * 删除商品
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void delProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		System.out.println(id);
		try {
			if(id!=null && !id.equals("")) {
				pdao.delete(id);
				response.getWriter().print("删除成功");
			}else {
				response.getWriter().append("删除失败");
			}
		} catch (Exception e) {
			response.getWriter().append("删除失败");
		}
		
	}

}

package com.cao.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cao.po.DmCategory;
import com.cao.util.DBHelper;

public class CategoryDAO {

	DBHelper db=new DBHelper();
	
	/*
	 * 插入新商品类
	 */
	public void insert(DmCategory dc) {
		String sql = "insert into dm_category values(null,?,?)";
		new DBHelper().update(sql
				,dc.getCname()
				,dc.getPid());
	}
	/*
	 * 更改商品类
	 */
	public void update(DmCategory dc) {
		String sql = "update  dm_category set cname=?,pid=? where id=?";
		new DBHelper().update(sql
				,dc.getCname()
				,dc.getPid()
				,dc.getId());
		
	}
	
	
	/**
	 * 统计总记录数，就是总行数
	 * @param dc
	 * @return
	 */
	public int countPages(DmCategory dc) {
		String where="";
		List<Object>params =new ArrayList<>();
	
		if(dc.getCname()!=null&&dc.getCname().trim().isEmpty()==false) {
			where+=" and a.cname like ?";
			params.add("%"+dc.getCname()+"%");
		}
		if(dc.getPid()!=null) {
			if(dc.getPid()!=0) {
				where+=" and a.pid=?";
				params.add(dc.getPid());
			}
		}
		String sql="select a.*,b.cname cpname from dm_category a join (select * from dm_category where pid is null)b on a.pid=b.id where 1=1 "+where;
		return new DBHelper().count(sql,params.toArray());	
	}
	
	
	/**
	 * 组合条件查询所有商品分类
	 * @param dc
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Map<String,Object>> queryCategory(DmCategory dc, String page,String rows) {
		String where="";
		List<Object>params =new ArrayList<>();
	
		if(dc.getCname()!=null && dc.getCname().trim().isEmpty()==false) {
			where+=" and a.cname like ?";
			params.add("%"+dc.getCname()+"%");
		}
		if(dc.getPid()!=null) {
			if(dc.getPid()!=0) {
				where+=" and a.pid=?";
				params.add(dc.getPid());
			}
		}
		int ipage = Integer.parseInt(page);
		int irows = Integer.parseInt(rows);
		ipage = (ipage - 1) * 10;
		String sql="select a.*,b.cname cpname from dm_category a join (select * from dm_category where pid is null)b on a.pid=b.id"
				+" where 1=1"
				+ where
				+" limit ?,?";
		params.add(ipage);
		params.add(irows);
		
		return new DBHelper().query(sql,params.toArray());		
	}
	
	/**
	 * 查询分类表
	 * @return
	 */
	public List<?> query() {
		String sql="select * from dm_category";
		return db.query(sql);
	}
	
	/**
	 * 查询菜单栏
	 * @return 
	 */
	public List<Map<String, Object>> queryMenu() {
		String sql="select * from dm_category  where pid is null";
		return db.query(sql);
	}
	
	/**
	 * 查询侧栏菜单分类
	 * @return 
	 */
	public List<Map<String, Object>> queryCategory() {
		//String sql="select * from  dm_category where pid>0";//这条sql语句也可
		String sql="select a.id,a.cname pname,b.cname,b.pid from dm_category a join dm_category b on a.id=b.pid order by a.id ";
		return db.query(sql);
	}
	
	/**
	 * 删除子分类
	 * @param id
	 */
	public void delete(String id) {
		String sql="delete from dm_category where id=?";
		db.update(sql, id);
		
	}
	
}

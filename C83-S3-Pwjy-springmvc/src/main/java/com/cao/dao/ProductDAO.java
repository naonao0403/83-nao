package com.cao.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cao.po.DmProduct;
import com.cao.util.DBHelper;

public class ProductDAO {
	
	DBHelper db=new DBHelper();
	
	/**
	 * 后台
	 * 查询所有商品
	 * @return
	 */
	public List<?> query(DmProduct dp,String page,String rows){
		String where="";
		List<Object> params=new ArrayList<>();
		if(dp.getPname()!=null && dp.getPname().trim().isEmpty()==false) {
			where+= "and pname like ?";
			params.add("%"+dp.getPname()+"%");
		}
		if(dp.getCid()!=null) {
			if(dp.getCid()!=0) {
				where+= " and cid=? ";
				params.add(dp.getCid());
			}
		}
		
		if(dp.getIsHot()!=null) {
			System.out.println(dp.getIsHot());
			where+= " and is_hot=? ";
			params.add(dp.getIsHot());
		}
		
		int ipage=Integer.parseInt(page);
		int irows=Integer.parseInt(rows);
		ipage = (ipage - 1) * 10;
		String sql = "select a.*,b.cname from dm_product a join dm_category b on a.cid=b.id"
				+ " where 1=1 "
				+ where
				+ " limit ?,? ";
		params.add(ipage);
		params.add(irows);
		return db.query(sql,params.toArray());
	}
	
	/**
	 * 后台
	 * 统计分页总记录数，就是总行数
	 * @param page
	 * @param rows
	 * @return
	 */
	public int count(DmProduct dp){
		String where="";
		List<Object> params=new ArrayList<>();
		if(dp.getPname()!=null && dp.getPname().trim().isEmpty()==false) {
			where+= " and pname like ? ";
			params.add("%"+dp.getPname()+"%");
		}
		if(dp.getCid()!=null) {
			if(dp.getCid()!=0) {
				where+= " and cid=? ";
				params.add(dp.getCid());
			}
		}
		
		if(dp.getIsHot()!=null) {
			where+= " and is_hot=? ";
			params.add(dp.getIsHot());
		}
		String sql = "select a.*,b.cname from dm_product a join dm_category b on a.cid=b.id where 1=1 "+where;
		return db.count(sql,params.toArray());
	}
	
	/**
	 * 查询热销商品
	 * @return
	 */
	public List<Map<String, Object>> queryHot(){
		String sql="select * from dm_product where is_hot=1 order by id desc limit 0,10 ";
		return db.query(sql);
	}
	
	/**
	 * 查询最新商品
	 * @return
	 */
	public List<Map<String, Object>> queryNew(){
		String sql="select * from dm_product order by id desc limit 0,10 ";
		return db.query(sql);
	}
	
	/**
	 * 查询某件商品
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> queryById(String id){
		String sql="select * from dm_product where id=?";
		return db.query(sql,id);
	}
	
	/**
	 * 新增数据
	 * @param dp
	 */
	public void insert(DmProduct dp) {
		String sql="insert into dm_product values(null,?,?,?,?,?,?,now(),?)";
		db.update(sql, 
				dp.getPname(),
				dp.getMarketPrice(),
				dp.getShopPrice(),
				dp.getImage(),
				dp.getPdesc(),
				dp.getIsHot(),
				dp.getCid());
	}

	/**
	 * 修改数据
	 * @param dp
	 */
	public void update(DmProduct dp) {
		String sql="update dm_product set"
				+ " pname=?,"
				+ " market_price=?,"
				+ " shop_price=?,"
				+ " image=?,"
				+ " pdesc=?,"
				+ " is_hot=?,"
				+ " cid=? "
				+ " where id =?";
		db.update(sql, 
				dp.getPname(),
				dp.getMarketPrice(),
				dp.getShopPrice(),
				dp.getImage(),
				dp.getPdesc(),
				dp.getIsHot(),
				dp.getCid(),
				dp.getId());
	}

	/**
	 * 分页查询商品
	 * @param cid
	 * @param iPage
	 * @param i
	 * @return
	 */
	public List<Map<String, Object>> queryPage(String cid,int page,int size) {
		int begin=(page-1)*size;
		String sql="select * from dm_product where cid=? limit ?,?";
		return db.query(sql, cid,begin,size);
	}
	
	/**
	 * 统计总页数
	 * @return
	 */
	public int countPages(String cid,int size) {
		String sql="select * from dm_product where cid="+cid;
		if(db.count(sql)%size==0) {
			return db.count(sql)/size;
		}
		return db.count(sql)/size+1;
	}

	/**
	 * 删除一条记录
	 * @param id
	 */
	public void delete(String id) {
		String sql="delete from dm_product where id=?";
		db.update(sql, id);		
	}
}

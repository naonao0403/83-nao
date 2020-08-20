package com.cao.dao;

import java.util.List;

import com.cao.util.DBHelper;

public class CartDAO {
	DBHelper db=new DBHelper();

	/**
	 * 添加新的商品进购物车
	 * @param uid
	 * @param pid
	 * @return 新增的记录数
	 */
	public int insert(String uid ,String pid) {
		String sql="insert into dm_cart values(null,?,?,1)";
		return db.update(sql, uid,pid);
	}
	
	/**
	 * 给某个用户的购物车商品数量+1
	 * @param uid
	 * @param pid
	 * @return 更新的记录数
	 */
	public int update(String uid ,String pid) {
		String sql="update dm_cart set count=count+1 where uid=? and pid=?";
		return db.update(sql, uid,pid);
	}
	
	/**
	 * 删除这条pid
	 * @param pid
	 * @return 更新的记录数
	 */
	public int del(String pid) {
		String sql="delete from dm_cart where pid=?";
		return db.update(sql,pid);
	}
	
	/**
	 * 根据用户编号查询购物车
	 * @param uid
	 * @return
	 */
	public List<?> queryByUid(String uid){
		String sql="select * from dm_cart a join dm_product b on a.pid=b.id where uid=?";
		return db.query(sql, uid);
	}
	
	/**
	 * 统计该用户下购物车总金额
	 * @param uid
	 * @return
	 */
	public List<?> cntTotal(String uid){
		String sql="select sum(b.shop_price*a.count)total from dm_cart a join dm_product b on a.pid=b.id where uid=?";
		return db.query(sql, uid);
	}
	
	/**
	 * 清空购物车
	 * @param uid
	 * @return
	 */
	public int clean(String uid) {
		String sql="delete from dm_cart where uid=?";
		return db.update(sql,uid);
	}
	
}

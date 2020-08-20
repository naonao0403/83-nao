package com.cao.dao;

import java.util.List;
import java.util.Map;

import com.cao.util.DBHelper;

public class GreatDAO {
	DBHelper db=new DBHelper();

	/**
	 * 点赞就在数据库添加一条数据
	 * @param uid
	 * @param pid
	 * @return 新增的记录数
	 */
	public int insert(String uid ,String pid) {
		String sql="insert into dm_great values(null,?,?)";
		return db.update(sql, pid,uid);
	}

	
	/**
	 * 删除该用户对这条商品的点赞
	 * @param pid
	 * @param uid
	 * @return 更新的记录数
	 */
	public int del(String pid,String uid) {
		String sql="delete from dm_great where pid=? and uid=?";
		return db.update(sql,pid,uid);
	}
	
	/**
	 * 根据用户编号查询该用户是否点赞该商品
	 * @param uid
	 * @return
	 */
	public List<Map<String, Object>> queryByUid(String pid,String uid){
		String sql="select * from dm_great where pid=? and uid=?";
		return db.query(sql,pid,uid);
	}
	
	/**
	 * 统计这个商品下的点赞数
	 * @param pid
	 * @return
	 */
	public int cntZan(String pid){
		String sql="select * from dm_great where pid=?";
		return db.count(sql, pid);
	}
}

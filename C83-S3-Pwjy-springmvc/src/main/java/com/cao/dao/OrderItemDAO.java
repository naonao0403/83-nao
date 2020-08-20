package com.cao.dao;

import java.util.List;
import java.util.Map;

import com.cao.util.DBHelper;

public class OrderItemDAO {
	
	DBHelper db=new DBHelper();
	
	
	/**
	 * 更新评论状态
	 * 0未评论,1已评论
	 * @param state
	 * @param oid
	 * @return
	 */
	public int UpdateState(String pstate,String id) {
		String sql="update dm_orderitem set pstate=? where id=?";
		return db.update(sql, pstate,id);
	}
	
	/**
	 * 向订单详情表添加数据
	 * @param uid
	 * @return
	 */
	public int insert(String uid) {
		String sql="INSERT INTO dm_orderitem\n" +
				"SELECT\n" +
				"	NULL,\n" +
				"	a.count,\n" +
				"	b.shop_price * a.count,\n" +
				"	a.pid,\n" +
				"	(select max(id) from dm_orders)\n" +
				"FROM\n" +
				"	dm_cart a\n" +
				"JOIN dm_product b ON a.pid = b.id\n" +
				"WHERE\n" +
				"	uid = ?";
		return db.update(sql, uid);
	}
	
	/**
	 * 查询该订单号下的订单明细
	 * @param uid
	 * @return
	 */
	public List<?> queryByOid(String oid){
		String sql="select * from dm_orderitem a join dm_product b on a.pid=b.id where oid=? ";
		return db.query(sql, oid);
	}
	
	
	/**
	 * 根据oid和pid查询该数据有没有存储数据
	 * @param pid
	 * @param uid
	 * @return
	 */
	public int selectByPid(String pid,String oid) {
		String sql="select * from dm_orderitem  where pstate=1 and oid=? and pid=? ";
		List<Map<String, Object>> list= db.query(sql,oid, pid);
		if(list.size()==0) {
			//表示数据库这条数据已经评论
			return 0;
		}else {
			return 1;
		}
	}
	
	
	public static void main(String[] args) {
		new OrderItemDAO().insert("2");
	}
	
}

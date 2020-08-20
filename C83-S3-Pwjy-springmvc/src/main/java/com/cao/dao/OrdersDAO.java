package com.cao.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cao.po.DmProduct;
import com.cao.util.DBHelper;

/**
 * 订单表DAO层
 * @author lenovo
 *
 */
public class OrdersDAO {
	
	DBHelper db=new DBHelper();
	
	/**
	 * 后台使用
	 * 查询已经支付的订单，
	 * 就是需要进行发货的订单
	 * @return
	 */
	public List<Map<String, Object>> queryPayOrders(DmProduct dp,String page,String rows){
		String where="";
		List<Object> params=new ArrayList<>();
		if(dp.getPname()!=null && dp.getPname().trim().isEmpty()==false) {
			where+= "and c.pname like ?";
			params.add("%"+dp.getPname()+"%");
		}
		if(dp.getId()!=null) {
			if(dp.getId()!=0) {
				where+= " and a.id=? ";
				params.add(dp.getId());
			}
		}	
		int ipage=Integer.parseInt(page);
		int irows=Integer.parseInt(rows);
		ipage = (ipage - 1) * 10;
		String sql="SELECT\n" +
				"	a.id,\n" +
				"	a.createtime,\n" +
				"	c.image,\n" +
				"	c.pname,\n" +
				"	b.total / b.count price,\n" +
				"	b.count,\n" +
				"	d.ename,\n" +
				"	a.total,\n" +
				"	a.state\n"+
				"FROM\n" +
				"	dm_orders a\n" +
				"JOIN dm_orderitem b ON a.id = b.oid\n" +
				"JOIN dm_product c ON b.pid = c.id\n" +
				"JOIN dm_user d ON a.uid = d.id\n" +
				"WHERE\n" +
				"	a.state = 1 "
				+ where
				+" GROUP BY a.id "
				+ " limit ?,? ";
		params.add(ipage);
		params.add(irows);
		return db.query(sql,params.toArray());
	}
	
	/**
	 * 后台使用
	 * 查询这个订单编号下的全部商品明细
	 * @param dp
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Map<String, Object>> queryPayOrdersItem(DmProduct dp,String page,String rows){
		String where="";
		List<Object> params=new ArrayList<>();
		if(dp.getId()!=null) {
			if(dp.getId()!=0) {
				where+= " and a.id=? ";
				params.add(dp.getId());
			}
		}	
		int ipage=Integer.parseInt(page);
		int irows=Integer.parseInt(rows);
		ipage = (ipage - 1) * 10;
		String sql="SELECT\n" +
				"	a.id,\n" +
				"	a.createtime,\n" +
				"	c.image,\n" +
				"	c.pname,\n" +
				"	b.total / b.count price,\n" +
				"	b.count,\n" +
				"	d.ename,\n" +
				"	b.total,\n" +
				"	a.state\n"+
				"FROM\n" +
				"	dm_orders a\n" +
				"JOIN dm_orderitem b ON a.id = b.oid\n" +
				"JOIN dm_product c ON b.pid = c.id\n" +
				"JOIN dm_user d ON a.uid = d.id\n" +
				"WHERE\n" +
				"	a.state = 1 "
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
			where+= " and c.pname like ? ";
			params.add("%"+dp.getPname()+"%");
		}
		if(dp.getId()!=null) {
			if(dp.getId()!=0) {
				where+= " and a.id=? ";
				params.add(dp.getId());
			}
		}	
		
		String sql = "SELECT\n" +
				"	a.id,\n" +
				"	a.createtime,\n" +
				"	c.image,\n" +
				"	c.pname,\n" +
				"	b.total / b.count price,\n" +
				"	b.count,\n" +
				"	d.ename,\n" +
				"	a.total,\n" +
				"	a.state\n"+
				"FROM\n" +
				"	dm_orders a\n" +
				"JOIN dm_orderitem b ON a.id = b.oid\n" +
				"JOIN dm_product c ON b.pid = c.id\n" +
				"JOIN dm_user d ON a.uid = d.id\n" +
				"WHERE\n" +
				"	a.state = 1 " +where+" GROUP BY a.id  ";
		return db.count(sql,params.toArray());
	}
	
	
	/**
	 * 查询该用户下所有的订单包括状态，查询订单表和订单详情表和商品表
	 * @return
	 */
	public List<Map<String, Object>> queryOrders(String uid){
		String sql="SELECT\n" +
				"	a.id oid,\n" +
				"	a.total,\n" +
				"	a.state,\n" +
				"	b.count,\n"+
				"	a.uid,\n" +
				"	c.*\n" +
				"FROM\n" +
				"	dm_orders a\n" +
				"LEFT JOIN dm_orderitem b ON a.id = b.oid\n" +
				"LEFT JOIN dm_product c ON b.pid = c.id\n" +
				"WHERE\n" +
				"	uid = ?";
		return db.query(sql, uid);
	}
	
	/**
	 * 查询该用户的订单表
	 * @param uid
	 * @return
	 */
	public List<Map<String, Object>> queryOrder(String uid){
		String sql="select * from dm_orders where uid=?";
		return db.query(sql, uid);
	}
	
	
	/**
	 * 更新支付状态
	 * 1已付款,2已发货
	 * @param state
	 * @param oid
	 * @return
	 */
	public int UpdateState(String state,String oid) {
		String sql="update dm_orders set createtime=now(),state=? where id=?";
		return db.update(sql, state,oid);
	}
	
	
	/**
	 * 插入订单进订单表
	 * @param uid
	 * @return 返回新增的记录数
	 */
	public int insert(String uid) {
		String sql="INSERT INTO dm_orders\n" +
				"	SELECT\n" +
				"		NULL,\n" +
				"		c.total,\n" +
				"		NOW(),\n" +
				"		0,\n" +
				"		a.id,\n" +
				"		b.id\n" +
				"	FROM\n" +
				"		dm_user a\n" +
				//获取默认地址
				"	JOIN dm_address b ON a.id = b.uid\n" +
				"	AND dft = 1\n" +
				"	JOIN (\n" +
				//求订单总金额
				"		SELECT\n" +
				"			sum(b.shop_price * a.count) total,\n" +
				"			a.uid\n" +
				"		FROM\n" +
				"			dm_cart a\n" +
				"		JOIN dm_product b ON a.pid = b.id\n" +
				"		WHERE\n" +
				"			uid = ?\n" +
				"		GROUP BY\n" +
				"			a.uid\n" +
				"	) c ON a.id = c.uid\n" +
				"	WHERE\n" +
				"		a.id = ? ";
		return db.update(sql, uid,uid);
	}
	
	/**
	 * 查询该用户下生成的订单表
	 * 新增的订单
	 * @param uid
	 * @return
	 */
	public Map<String, Object> queryNewOrders(String uid){
		String sql="select a.*,b.addr,b.phone,b.name from dm_orders a join dm_address b on a.aid=b.id where a.uid=? and state=0 order by id desc limit 0,1";
		List<Map<String, Object>> list= db.query(sql, uid);
		if(list.isEmpty()) {
			return null;
		}else {
			return list.get(0);
		}
	}
	
	/**
	 * 查询已经生成的订单
	 * @param uid
	 * @return
	 */
	public Map<String, Object> queryOldOrders(String oid){
		String sql="select a.*,b.addr,b.phone,b.name from dm_orders a join dm_address b on a.aid=b.id where a.id=? and state=0 order by id desc limit 0,1";
		List<Map<String, Object>> list= db.query(sql,oid);
		if(list.isEmpty()) {
			return null;
		}else {
			return list.get(0);
		}
	}
	
	/**
	 * 测试函数
	 * @param args
	 */
	public static void main(String[] args) {
		//这种写法有数据库事务的问题
		new OrdersDAO().insert("2");
		//出现异常，会导致订单被创建，而订单明细没有创建，购物车没有被清空
		new OrderItemDAO().insert("2");
		new CartDAO().clean("2");
	}
}

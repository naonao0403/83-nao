package com.cao.dao;

import java.util.List;
import java.util.Map;

import com.cao.util.DBHelper;


public class AddrDAO {
	DBHelper db=new DBHelper();
	
	
	/**
	 * 修改默认地址
	 * @param id
	 */
	public void updateDft(String id) {
		String sql =" update dm_address set dft=1 where id=? ";
		db.update(sql, id);
		
	}
	
	/**
	 * 修改该用户下所有的地址为0，就是不是默认地址
	 * @param uid
	 */
	public void setDft(String uid) {
		String sql =" update dm_address set dft=0 where uid=? ";
		db.update(sql, uid);
		
	}
	
	
	
	/**
	 *新增地址
	 * @param ename
	 * @param uname
	 * @param upass
	 * @param email
	 * @param phone
	 * @param sex
	 */
	public void insert(String uid,String addr,String phone,String name,String dft) {
		int idft=Integer.parseInt(dft);
		String sql="insert into dm_address values(null,?,?,?,?,?,now())";
		DBHelper dbh=new DBHelper();//ctrl+1错误解决方案提示
		dbh.update(sql, uid,addr,phone,name,idft);
	}
	
	
	/**
	 * 查询该用户下的所有收获地址
	 * @param ename
	 * @return
	 */
	public List<Map<String, Object>> selectByUid(String uid) {
		String sql="select * from dm_address where uid=? ";
		return db.query(sql,uid);
		
	}
	
	/**
	 * 查询该用户下的默认地址
	 * @param ename
	 * @return
	 */
	public int selectByDft(String uid) {
		String sql="select * from dm_address where dft=1 and uid=?  ";
		List<Map<String, Object>> list= db.query(sql,uid);
		if(list.size()>0&& list!=null) {
			return 1;
		}else {
			return 0;
		}
	}

	/**
	 * 根据编号进行查询该地址
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> selectById(String id) {
		String sql="select * from dm_address where id=? ";
		return db.query(sql,id);
	}

	/**
	 * 修改地址
	 * @param id
	 * @param name
	 * @param addr
	 * @param phone
	 */
	public void updateAddr(String id, String name, String addr, String phone) {
		String sql="update dm_address set addr=?,phone=?,name=? where id=?";
		db.update(sql, addr,phone,name,id);
		
	}

	/**
	 * 删除地址
	 * @param id
	 */
	public void delAddr(String id) {
		String sql="delete from dm_address where id=?";
		db.update(sql, id);
		
	}

	
}

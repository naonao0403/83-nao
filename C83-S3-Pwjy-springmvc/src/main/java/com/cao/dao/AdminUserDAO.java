package com.cao.dao;

import java.util.List;
import java.util.Map;

import com.cao.util.DBHelper;


public class AdminUserDAO {
	DBHelper db=new DBHelper();
	
	/**
	 *  将用户信息插入到数据库中
	 * @param ename
	 * @param uname
	 * @param upass
	 * @param email
	 * @param phone
	 * @param sex
	 */
	public void insert(String uname,String upass) {
		String sql="insert into dm_adminuser values(null,?,?)";
		DBHelper dbh=new DBHelper();//ctrl+1错误解决方案提示
		dbh.update(sql, uname,upass);
	}
	
	/**
	 * 用于判断用户是否登录
	 * 代码重构：如果正常登录，返回用户信息Map集合，否则返回null
	 * @param uname
	 * @param upass
	 * @return
	 */
	public Map<String, Object> selectByLogin(String uname,String upass) {
		String sql="select * from dm_adminuser where username=? and password=? ";
		List<Map<String, Object>> list=db.query(sql,uname,upass);
		if(list.size()==0) {
			return null;
		}else {
			 Map<String, Object> user=list.get(0);
			 return user;
		}
	}
	
	/**
	 * 检查账号是否已经注册
	 * @param ename
	 * @return
	 */
	public int selectByEname(String uname) {
		String sql="select * from dm_adminuser where username=? ";
		List<Map<String, Object>> list=db.query(sql,uname);
		if(list.size()==0) {
			//表示数据库没有该数据
			return 0;
		}else {
			return 1;
		}
	}
}

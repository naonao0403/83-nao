package com.cao.dao;

import java.util.List;
import java.util.Map;

import com.cao.util.DBHelper;


public class UserDAO {
	DBHelper db=new DBHelper();
	
	
	/**
	 * 修改密码
	 * @param id
	 * @param name
	 * @param press
	 * @param date
	 * @param author
	 * @param count
	 * @param image
	 */
	public void updateUpass(String upass,String ename) {
		String sql =" update dm_user set password=? where ename=? ";
		db.update(sql, upass,ename);
		
	}
	
	/**
	 * 保存用户个人信息
	 * 修改数据
	 * @param id
	 * @param name
	 * @param press
	 * @param date
	 * @param author
	 * @param count
	 * @param image
	 * @return 
	 */
	public int updateUser(String cname,String email,String phone,String sex,String image,String ename) {
		String sql =" update dm_user set cname=?,email=?,phone=?,sex=?,image=?  where ename=? ";
		 return db.update(sql, cname,email,phone,sex,image,ename);	
	}
	
	
	/**
	 *  将用户信息插入到数据库中
	 * @param ename
	 * @param uname
	 * @param upass
	 * @param email
	 * @param phone
	 * @param sex
	 */
	public void insert(String ename,String uname,String upass,String email,String phone,String sex) {
		String sql="insert into dm_user values(null,?,?,?,?,?,?,1,now(),'usersimage/head.png')";
		DBHelper dbh=new DBHelper();//ctrl+1错误解决方案提示
		dbh.update(sql, ename,uname,upass,email,phone,sex);
	}
	
	/**
	 * 用于判断用户是否登录
	 * 代码重构：如果正常登录，返回用户信息Map集合，否则返回null
	 * @param uname
	 * @param upass
	 * @return
	 */
	public Map<String, Object> selectByLogin(String ename,String upass) {
		String sql="select * from dm_user where ename=? and password=? ";
		List<Map<String, Object>> list=db.query(sql,ename,upass);
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
	public int selectByEname(String ename) {
		String sql="select * from dm_user where ename=? ";
		List<Map<String, Object>> list=db.query(sql,ename);
		if(list.size()==0) {
			//表示数据库没有该数据
			return 0;
		}else {
			return 1;
		}
	}
}

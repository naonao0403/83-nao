package com.cao.dao;

import java.util.List;
import java.util.Map;

import com.cao.po.DmEvaluate;
import com.cao.po.DmProduct;
import com.cao.util.DBHelper;

public class EvaluateDAO {
	
	DBHelper db=new DBHelper();
	
	/**
	 * 新增数据
	 * @param dp
	 */
	public void insert(int pid,int uid,String msg,String image) {
		String sql="insert into dm_evaluate values(null,?,?,?,?,now())";
		db.update(sql, pid,uid,msg,image);
	}

	/**
	 * 根据uid和pid查询该数据有没有存储数据
	 * @param pid
	 * @param uid
	 * @return
	 */
	public List<Map<String, Object>> selectByPid(int pid) {
		String sql="select a.*,b.image uimage,b.ename  from dm_evaluate a join dm_user b on a.uid=b.id where pid=? ";
		return db.query(sql, pid);
		
	}
	
}

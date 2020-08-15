package com.yc.spring.dao;

import com.yc.spring.bean.Person;

import org.apache.ibatis.annotations.Insert;

public interface UserDao {
	public int selectUserId(String name);

	@Insert("insert into person values(#{name},#{age})")
	public int insert(Person person);
}

package com.yc.spring.dao;

import org.springframework.stereotype.Repository;

import com.yc.spring.bean.Person;

@Repository("odao")
public class OracleUserDao implements UserDao{
	public int selectUserId(String name) {
		System.out.println("Oracle user dao");
		return 77;
	}

	@Override
	public int insert(Person person) {
		// TODO Auto-generated method stub
		return 0;
	}
}

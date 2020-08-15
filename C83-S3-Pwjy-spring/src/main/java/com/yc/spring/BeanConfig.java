package com.yc.spring;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.yc.spring.bean.Person;
import com.yc.spring.dao.MySQLUserDao;
import com.yc.spring.dao.OracleUserDao;

/**
 * 注解方式配置IOC容器
 * @author asus
 *
 */
@Configuration  //IOC容器配置类的注解 ==>beans.xml
public class BeanConfig {
	//xml中的每一个bean 对应java的一个方法  这个方法返回bean对象
	//方法名不限定 Bean
	@Bean(name="hello")
	public Hello getHello() {
		return new Hello();
	}
	
/*	@Bean(name="odao")
	public OracleUserDao getOracleUserDao() {
		return new OracleUserDao();
	}
	
	@Bean(name="mdao")
	public MySQLUserDao	getMySQLUserDao() {
		return new MySQLUserDao();
	}
	*/
	@Bean(name="p1")
	public Person getPerson1() {
		Person ret=new  Person();
		ret.setName("武松");
		ret.setAge(35);
		ret.setKilleds(new ArrayList<String>());
		ret.getKilleds().add("西门庆");
		ret.getKilleds().add("西门庆");
		ret.getKilleds().add("蒋门神");
		ret.getKilleds().add("西门庆");
		ret.getKilleds().add("西门庆");
		
		return ret;
	}
	
	@Bean(name="p2")
	public Person getPerson2() {
		Person ret=new  Person();
		ret.setName("吴用");
		ret.setAge(38);
		ret.setFriend(new Person());
		ret.getFriend().setName("华荣");		
		return ret;
	}
	
	@Bean(name="p5")
	public Person getPerson5() {
		Person p=Person.PersonFactory();
		p.setName("王英");
		return p;
	}
	
	@Bean(name="p6")
	public Person getPerson6() {
		Person p=Person.PersonFactory1();
		p.setName("扈三娘");
		return p;
	}
	
	/**
	 * 原型模式
	 * @return
	 */
	@Bean(name="hello1")
	@Scope(value = "prototype")//对应<bean scope="prototype">
	public Hello getHello1() {
		return new Hello();
	}
	
	/*
	 * 懒加载
	 */
	@Bean(name="hello2")
	@Lazy
	public Hello getHello2() {
		return new Hello();
	}
	
	/*
	 * 自动装载
	 */
	@Bean(name="p7")
	@Autowired
	public Person getPerson7() {
		Person ret=new  Person();
		ret.setFriend(new Person());
		ret.getFriend().getName();		
		return ret;
	}
}

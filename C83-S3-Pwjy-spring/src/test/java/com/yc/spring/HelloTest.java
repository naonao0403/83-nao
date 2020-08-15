package com.yc.spring;



import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yc.spring.bean.Person;
import com.yc.spring.dao.UserDao;



public class HelloTest {
	ClassPathXmlApplicationContext ctx=
			new ClassPathXmlApplicationContext("beans.xml");
	@Test
	public void test() {
		//Hello h=new Hello();
		
		/**
		 * Spring框架解决的问题
		 * Servlet
		 * 		UserBiz ubiz=new UserBiz();
		 * 1.  new -->创建对象-->内存中占用存储对象空间
		 * 		每次new都会创建一个新的对象-->导致内存消耗大
		 * 		解决方式:使用对象池
		 * 		对象池.get对象  获取对象
		 * 2.耦合性问题
		 * 		对象可以任意在运行期设置为指定的子类实现类
		 * 	控制反转
		 * 		对象创建由程序员决定
		 * 		对象的创建由容器决定
		 */
		//从spring框架 (容器) 中获取一个hello对象
		//创建Spring容器对象
		ClassPathXmlApplicationContext ctx=
				new ClassPathXmlApplicationContext("beans.xml");
		Hello h=(Hello)ctx.getBean("hello");
		Hello h1=(Hello)ctx.getBean("hello");
		Hello h2=(Hello)ctx.getBean("hello");
		
		System.out.println(h1==h2);
		//执行sayHello方法
		h.sayHello();
		ctx.close();
	}
	
	@Test
	public void test1() {
		ClassPathXmlApplicationContext ctx=
				new ClassPathXmlApplicationContext("beans.xml");
		
		UserDao udao1=(UserDao)ctx.getBean("mdao");
		UserDao udao2=(UserDao)ctx.getBean("odao");
		
		udao1.selectUserId("daniu");
		udao2.selectUserId("daniu");
		
		ctx.close();
	}
	
	@Test
	public void test2() {
		Person p1=(Person)ctx.getBean("p1");
		Assert.assertEquals("武松", p1.getName());
		Assert.assertEquals(35, p1.getAge());
		Assert.assertEquals(5, p1.getKilleds().size());
		Assert.assertEquals("蒋门神", p1.getKilleds().get(2));
	}
	
	@Test
	public void test3() {
		//java.lang.NoSuchMethodException:com.yc.spring.bean.Person.<init>()
		//未找到无参数的构造函数
		
		Person p1=ctx.getBean(Person.class);
		Assert.assertEquals("李逵", p1.getName());
		Assert.assertEquals(33, p1.getAge());
		Assert.assertEquals(null, p1.getKilleds());
	}
	
	@Test
	public void test4() {
		Person p1=(Person)ctx.getBean("p2");
		Assert.assertEquals("吴用", p1.getName());
		Assert.assertEquals(38, p1.getAge());
		Assert.assertEquals("华荣", p1.getFriend().getName());
	}
	
	@Test
	public void test5() {
		Person p1=(Person)ctx.getBean("p5");
		Assert.assertEquals("王英", p1.getName());
		Assert.assertEquals(40, p1.getAge());
	}
	
	@Test
	public void test6() {
		Person p1=(Person)ctx.getBean("p6");
		Assert.assertEquals("扈三娘", p1.getName());
		Assert.assertEquals(20, p1.getAge());
	}
	
	/**
	 * bean的作用域
	 * 默认情况下 bean的作用域是单例模式
	 */
	@Test
	public void test7() {
		System.out.println("======test7======");
		
		Hello h0=(Hello)ctx.getBean("hello");
		Hello h0_1=(Hello)ctx.getBean("hello");
		Hello h0_2=(Hello)ctx.getBean("hello");
		
		Hello h1=(Hello)ctx.getBean("hello1");
		Hello h1_1=(Hello)ctx.getBean("hello1");
		Hello h1_2=(Hello)ctx.getBean("hello1");
		
		System.out.println(h0==h0_1);
		System.out.println(h0_1==h0_2);
		System.out.println(h0_1==h1_2);
		System.out.println(h1==h1_1);
		System.out.println(h1==h1_2);
	}
	
	@Test
	public void test8() {
		System.out.println("======test8======");
		
		Hello h0=(Hello)ctx.getBean("hello2");
		h0.sayHello();
	}
		
	/**
	 * 生命周期方法
	 */
	@Test
	public void test9() {
		Hello h0=(Hello)ctx.getBean("hello3");
		h0.sayHello();
	}
	
	/**
	 * 自动装载
	 */
	@Test
	public void test10() {
		Person p7=(Person)ctx.getBean("p7");
		System.out.println(p7.getFriend().getName());
		
	}
}

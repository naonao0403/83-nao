package com.nao.thread.d0808;

import java.lang.reflect.Method;

@Test("测试")
public class Demo2 {
	
	@Test("测试方法")
	public void test() {
		
	}
	
	public static void main(String[] args) throws 
	ClassNotFoundException, //没有找到类
	NoSuchMethodException,//没有找到方法
	SecurityException {//安全异常(不允许获取)
		Demo2 d=new Demo2();
		//java 黑科技 ---反射
		//获取一个类的类对象(泛型对象)
		//方法一：类名+class 关键字
		Class<?> cls=Demo2.class;
		//方法二：对象名 +getClass()
		cls=d.getClass();
		//方法三：Class类+foeName方法 (加载时会执行静态块 完成初始化)
		cls= Class.forName("com.nao.thread.d0808.Demo2");
		
		
		//反射操作
		cls.getFields();//获取公有的属性数组
		cls.getMethods();//获取公有的方法数组
		cls.getConstructors();//获取公有的构造方法数组
		
		cls.getAnnotations();//获取类上的注解数组
		Test test=cls.getAnnotation(Test.class);//根据注解类的类对象来获取注解对象
		System.out.println(test.value());
		//获取方法对象
		Method m=cls.getMethod("test");
		
		Test test2=m.getAnnotation(Test.class);
		System.out.println(test2.value());
	}
}

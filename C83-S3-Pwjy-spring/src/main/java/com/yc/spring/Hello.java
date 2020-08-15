package com.yc.spring;

public class Hello {
	
	public Hello () {
		/*无参数的构造方法*/
		System.out.println("=======hello无参数的构造方法========");
	}
	
	public Hello (int a) {
		/*有参数的构造方法*/
		System.out.println("=======hello有参数的构造方法========");
	}
	public void sayHello() {
		System.out.println("你好大牛");
	}
	
	/**
	 * 生命周期方法(不能带参数)
	 */
	public void init() {
		System.out.println("==hello被创建===");
	}
	
	public void destroy() {
		System.out.println("==hello被销毁===");
	}
}

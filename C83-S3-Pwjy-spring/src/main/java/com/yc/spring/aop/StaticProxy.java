package com.yc.spring.aop;

public class StaticProxy {
	public static void main(String[] args) {
		ProxySubject ps=new ProxySubject();
		ps.say();
	}
}

//抽象主题
interface Subject{
	void say();
}

//真是主题：被告
class RealSubject implements Subject{
	public void say() {
		System.out.println("凶手是大牛!");
	}
}

//代理主题:律师
class ProxySubject implements Subject{
	//被代理对象
	RealSubject rs=new RealSubject();
	public void say() {
		System.out.println("结案吧,大牛都承认了!");
		rs.say();
		
	}
}
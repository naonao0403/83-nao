package com.yc.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy implements InvocationHandler {
	public static void main(String[] args) {
		JDKProxy jp=new JDKProxy();
		RealSubject rs=new RealSubject();
		//对应ProxySubject ps=new ProxySubject();
		Subject ps=(Subject)jp.createProxyInstance(rs);
		ps.say();
		Houseowner ho=new Houseowner();
		Broker bk=(Broker)jp.createProxyInstance(ho);
		bk.sale();
	}

	//被代理对象
	private Object realSubject;
	
	/**
	 * 
	 * @param proxy			目标对象的代理类实例
	 * @param method		对应于在代理实例上调用接口方法的method实例
	 * @param args			传入到代理实例上方法参数值的对象数组
	 * @return				方法的返回值 没有返回值时是null
	 * @throws Throwable
	 */
	public Object invoke(Object proxy,Method method,Object[] args) throws Throwable{
		//前置增强
		System.out.println("大牛是凶手");
		//执行目标方法
		Object retObject=method.invoke(realSubject, args);//ra.say();
		//后置增强
		System.out.println("大牛是凶手");
		return retObject;
	}
	
	/**
	 * 构建代理对象
	 * @param targerObject
	 * @return
	 */
	public Object createProxyInstance(Object targerObject) {
		this.realSubject=targerObject;
		/*
		 * 第一个参数设置代码使用的类加载器 一般采用跟目标类相同的类加载器
		 * 第二个参数设置代理实现的接口 跟目标类使用相同接口
		 * 第三个参数设置回调对象 当代理对象的方法被调用时 回调用该参数指定对象invoke方法
		 */
		return  Proxy.newProxyInstance(
				targerObject.getClass().getClassLoader(), 
				targerObject.getClass().getInterfaces(), 
				this);
	}
}

interface Broker{
	void sale();
}

class Houseowner implements Broker{
	public void sale() {
		System.out.println("买房子送大牛!");
	}
}
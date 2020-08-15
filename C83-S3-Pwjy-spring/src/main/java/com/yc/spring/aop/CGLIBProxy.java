package com.yc.spring.aop;

import java.lang.reflect.Method;import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CGLIBProxy implements MethodInterceptor {
	public static void main(String[] args) {
		RealSubject rs=new RealSubject();
		Houseowner ho=new Houseowner();
		CGLIBProxy cp=new CGLIBProxy();
		
		RealSubject proxyRs=(RealSubject)cp.proxy(rs);
		proxyRs.say();
		
		Houseowner proxyHo=(Houseowner) cp.proxy(ho);
		proxyHo.sale();
	}
	
	private Object realSubject;
	
	/**
	 * @param proxy			目标对象的代理类实例
	 * @param method		对应于在代理实例上调用接口方法的method实例
	 * @param args			传入到代理实例上方法参数值的对象数组
	 * @param methodProxy	使用它调用父类方法
	 * @return				方法的返回值 没有返回值时是null
	 * @throws Throwable
	 */
	public Object intercept(		
			Object obj,
			Method method,
			Object[] args,
			MethodProxy methodProxy) throws Throwable {
		System.out.println("代理类"+obj.getClass());
		System.out.println("方法名称"+method.getName());
		if(args!=null &&args.length>0) {//方法的参数
			for (int i = 0; i < args.length; i++) {
				System.out.println("方法参数"+args[i]);
			}
		}
		Object returnvalue=null;//方法的返回值 没有返回值时是null
		returnvalue=methodProxy.invoke(this.realSubject, args);
		System.out.println("方法的返回值"+returnvalue);
		return returnvalue;
	}
	
	/**
	 * 代理方法
	 * @param targetObject
	 * @return
	 */
	public Object proxy(Object targetObject) {
		this.realSubject=targetObject;
		Enhancer enhancer=new Enhancer();//该类用于生成代理对象
		enhancer.setSuperclass(targetObject.getClass());//设置父类
		enhancer.setCallback(this);//设置回调对象为本身
		return enhancer.create();//创建代理对象
	}
}

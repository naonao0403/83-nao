package com.nao.thread.d0808.junit;

import java.awt.font.NumericShaper.Range;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
	public static void main(String[] args) {
		DemoTest dt=new DemoTest();
		
		Class<?> cls=dt.getClass();
		
		Method beforeMethod=getMethodByMethod(cls,Before.class);
		Method afterMethod=getMethodByMethod(cls,After.class);
		//获取所有方法 并进行遍历
		for(Method m:cls.getMethods()) {
			if(m.getAnnotation(Test.class)!=null) {
				try {
					//动态执行方法 junit的测试方法不能定义参数
					if (beforeMethod!=null) {
						beforeMethod.invoke(dt);
					}
					m.invoke(dt);
					if (afterMethod!=null) {
						afterMethod.invoke(dt);
					}
				} catch (IllegalAccessException|IllegalArgumentException e) {
					e.printStackTrace();
				}catch (InvocationTargetException e) {
					//如果是 dt.m 方法出现业务异常
					//将会封装 该异常中
					System.out.println("测试异常:失败!");
					//Throwable t=e.getCause();//异常原因 空指针
					//System.out.println(t);
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 扩展1:定义befoe 和after
	 *    输出要求如下
	 *    		测试方法前执行的方法
	 *    		test1
	 *    		测试方法后执行的方法
	 *    		测试方法前执行的方法
	 *    		test2
	 *    		测试方法后执行的方法
	 * 扩展2:统计测试结果 :成功数量 失败数量
	 *    		
	 */
	
	/**
	 * 根据输入的注解类的类名 返回对应的方法
	 * @param testcls		要查找的被测试的类对象
	 * @param annocls		要查找方法标注的注解类对象
	 * @return  
	 */
	//扩展1
	@SuppressWarnings("unchecked")
	public static Method getMethodByMethod(Class<?> testcls,
			@SuppressWarnings("rawtypes")Class annocls) {
		for(Method m:testcls.getMethods()) {
			if(m.getAnnotation(annocls)!=null) {
				return m;
			}
		}
		return null;
	}
	
	
}

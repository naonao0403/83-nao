package com.nao.thread.d0725;

import java.util.Scanner;

public class Demo1 {
	public static void main(String[] args) {
		//a();
		//b();
		//使用多线程方式 让啊a() b()同时运行
		A a=new A("a方法的线程");
		B b=new B();
		Thread t=new Thread(b,"b方法的线程");
		
		a.start();
		t.start();
		
		
		//获取当前线程：主线程
		System.out.println("main getName:"+Thread.currentThread().getName());
		System.out.println("main getPriority:"+Thread.currentThread().getPriority());
		System.out.println("main getId:"+Thread.currentThread().getId());
		System.out.println("main getState:"+Thread.currentThread().getState());
	}
	
	public static void a() {
		Scanner sc=new Scanner(System.in);
		System.out.println("请输入：");
		String s=sc.nextLine();
		System.out.println("您输入的是："+s);
		sc.close();
	}
	
	public static void b() {
		System.out.println("这是b()方法");
	}
	
	//静态内部类实现线程类
	//继承 Thread 类实现多线程
	public static class A extends Thread{
		public A(String name) {
			super(name);
		}
		public void run() {
			a();
			System.out.println("a getName:"+Thread.currentThread().getName());
			System.out.println("a getPriority:"+Thread.currentThread().getPriority());
			System.out.println("a getId:"+Thread.currentThread().getId());
			System.out.println("a getState:"+Thread.currentThread().getState());
		}
	}
	
	//实现Runnable接口
	public static class B implements Runnable{
		public void run() {
			//让线程休眠十秒
			System.out.println("b()休眠10秒");
			try {
				Thread.sleep(10*1000);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			b();
			System.out.println("b getName:"+Thread.currentThread().getName());
			System.out.println("b getPriority:"+Thread.currentThread().getPriority());
			System.out.println("b getId:"+Thread.currentThread().getId());
			System.out.println("b getState:"+Thread.currentThread().getState());
		}
	}
}

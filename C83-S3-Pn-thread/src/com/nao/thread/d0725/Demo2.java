package com.nao.thread.d0725;
/**
 * join
 * @author asus
 *
 */
public class Demo2 {
	
	public static void main(String[] args) {
		//匿名内部类创建线程
		Thread t1=new Thread("线程1") {
			//类定义 匿名方式
			public void run() {
				for(int i=0;i<100;i++) {
					System.out.println(Thread.currentThread().getName()+":"+i);
				}
			}
		};
		
		Thread t2=new Thread("----线程2") {
			//类定义 匿名方式
			public void run() {
				for(int i=0;i<100;i++) {
					System.out.println(Thread.currentThread().getName()+":"+i);
					try {
						if(i==50) {
							t1.join();
						}
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t1.start();
		t2.start();
	}
}

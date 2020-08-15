package com.nao.thread.d0808.junit;



public class DemoTest {
	/**
	 * @Before 是用于标注测试方法执行前要执行的方法
	 */
	@Before()
	public void  before() {
	System.out.println("测试方法前执行的方法");

	}
	
	@After
	public  void after() {
		System.out.println("测试方法后执行的方法");

	}
	
	@Test
	public void test1() {
		System.out.println("test1");
	}
	
	@Test
	public void test2() {
		System.out.println("test2");
	}
	
	
	public void test3() {
		System.out.println("test3");
	}
	
	
}

package com.nao.thread.d0808;

import java.util.List;

@Test

public class Demo1 {
	@Test
	@Select(value= {"select * from a","select * from b"})
	public void test() {
		@Test  //由Test中elementType来决定
		int a;
	}
	
	@Select(value= "select * from a",age = 28)
	public void test1() {
		@Test  //由Test中elementType来决定
		int a;
	}
	
	@Select(value= "select * from a")
	public void test2() {
		@Test  //由Test中elementType来决定
		int a;
	}
	
//	@Select(value= "select * from a")
//	public void test3() {
//		@niu
//		int a;
//	}


	@Override
	public String toString() {
		@SuppressWarnings("rawtypes")
		List a=null;
		
		System.out.println(a);
		
		return super.toString();
	}


	
	
}

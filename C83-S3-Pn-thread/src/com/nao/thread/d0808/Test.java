package com.nao.thread.d0808;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//只能用注解的注解来限定该注解被标注的位置
@Target(value = {ElementType.TYPE,
		ElementType.METHOD,
		ElementType.LOCAL_VARIABLE})
//设置保持性Retention:这个元注释和java编译器处理注解的注解类型的方式有关 编译器有几种不同选择
//1.将注释保留在编译后的类文件中 并在第一次加载时读取它   --->源代码阶段
//2.将注释保留在编译后的类文件中 但在运行时忽略它             --->编译期阶段
//3.按照规定使用注解 但是并不将它保留到编译后的类文件中   --->运行期阶段
//这种选项用java.lang.anotation.RetentionPolicy枚举表示  如下所示
@Retention(RetentionPolicy.RUNTIME)// alt + /
public @interface Test {

	String value() default "";
}

@Target(value = {ElementType.METHOD})
@interface Select{
	//定义注解的属性： 定义语法类似于  接口方法（类属性+方法）
	//如果一个属性定义的数组类型
	//那么使用数组语法复制 但是如果值复制一个元素
	//可以使用单变量形式
	String[] value();
	
	//所有的注解属性都必须赋值  如果不赋值 那么要设置默认值
	int age() default 100;
	
	//如果某个属性的名称是value 那么这个属性就是默认属性
	//默认属性可以不写属性名就进行赋值 前期是只填写该属性 不填写其他属性
	
}

//@Target(value = {ElementType.LOCAL_VARIABLE})
//@interface niu{
//	
//}

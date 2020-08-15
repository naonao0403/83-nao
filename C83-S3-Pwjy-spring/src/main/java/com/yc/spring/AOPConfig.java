package com.yc.spring;

import javax.print.attribute.standard.PagesPerMinute;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.yc.spring.bean.Person;

@Configuration
@ComponentScan("com.yc.spring")
@EnableAspectJAutoProxy
public class AOPConfig {
	@Bean("person")
	public Person getPererson() {
		return new Person();
	}
	
	@Bean("hello")
	public Hello getHello() {
		return new Hello();
	}
}

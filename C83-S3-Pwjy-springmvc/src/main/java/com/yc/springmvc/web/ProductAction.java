package com.yc.springmvc.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductAction {
	
	@RequestMapping(path="product.do",params="op=query")
	public String query() {
		return "query!!";
	}
	
	@RequestMapping(path="product.do",params="op=add")
	public String add() {
		return "add!!";
	}
	
	@RequestMapping(path="product.do",params="op=mod",method = RequestMethod.POST)
	public String mod() {
		return "mod!!";
	}
	
	//@GetMapping=@RequestMapping(path="product.do",params="op=mod",method = RequestMethod.POST)
	@GetMapping(path="product.do",params="op=select")
	public String select() {
		return "select!!";
	}
	
	@GetMapping(path="product.do",params="op=mgr",headers= {"Connection=keep-alive"})
	public String mgr() {
		return "mgr!!";
	}
	

}

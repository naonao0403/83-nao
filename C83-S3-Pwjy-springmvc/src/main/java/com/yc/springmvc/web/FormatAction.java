package com.yc.springmvc.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yc.damai.bean.DmOrders;

@RestController
@RequestMapping("demo")
public class FormatAction {

	@RequestMapping("addOrder")
	public DmOrders addOrder(DmOrders dos) {
		System.out.println(dos.getDate());
		return dos;
	}
}

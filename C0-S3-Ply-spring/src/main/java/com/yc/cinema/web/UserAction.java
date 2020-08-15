package com.yc.cinema.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yc.cinema.biz.CommentsBiz;
import com.yc.cinema.biz.UserBiz;

@Controller
public class UserAction {
	
	@Autowired //根据类型容器中获取指定的bean
	private UserBiz ubiz;
	
	@Autowired
	private CommentsBiz cbiz;

	public UserBiz getUbiz() {
		return ubiz;
	}

	public void setUbiz(UserBiz ubiz) {
		this.ubiz = ubiz;
	}

	public CommentsBiz getCbiz() {
		return cbiz;
	}

	public void setCbiz(CommentsBiz cbiz) {
		this.cbiz = cbiz;
	}

}

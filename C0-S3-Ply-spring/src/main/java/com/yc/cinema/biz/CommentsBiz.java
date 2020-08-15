package com.yc.cinema.biz;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.cinema.dao.CommentsDao;
import com.yc.cinema.dao.MovieDao;

@Service
public class CommentsBiz {
	
	@Autowired //根据类型注入 Spring 提供
	private UserBiz ubiz;

	@Resource //根据类型名字(id)注入 id未找到再根据类型注入  java提供
	private CommentsDao cdao;

	@Resource
	private MovieDao mdao;

	public UserBiz getUbiz() {
		return ubiz;
	}

	public void setUbiz(UserBiz ubiz) {
		this.ubiz = ubiz;
	}

	public CommentsDao getCdao() {
		return cdao;
	}

	public void setCdao(CommentsDao cdao) {
		this.cdao = cdao;
	}

	public MovieDao getMdao() {
		return mdao;
	}

	public void setMdao(MovieDao mdao) {
		this.mdao = mdao;
	}

}

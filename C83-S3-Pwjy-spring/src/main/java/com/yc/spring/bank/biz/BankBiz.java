package com.yc.spring.bank.biz;

import java.sql.SQLException;

import javax.imageio.IIOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yc.spring.bank.bean.Oprecord;
import com.yc.spring.bank.dao.AccountDao;
import com.yc.spring.bank.dao.OprecordDao;

/**
 * 银行业务类
 * 开户:向Account表添加一条数据(新增)
 * 存取款:修改Account的余额(修改) 记录流水表(新增)
 * 转账:A账号减少(取款) B账号增加(存款)
 * 查询:根据账号查余额
 * @author asus
 *
 */
@Service
@Transactional(rollbackFor = {IIOException.class,SQLException.class})
public class BankBiz {
	@Autowired
	private AccountDao adao;
	
	@Autowired
	private OprecordDao odao;
	
	//开户
	public void register (int id,String pwd,double money) {
		/**
		 * 
		 */
		
		adao.insert(id, pwd, money);
		odao.insert(id, money);
	}
	
	//存取款
	@Transactional(rollbackFor = {BizException.class})
	public void save(int id,double money) throws BizException {
		//省略参数校验
		adao.update(id, money);
		if (money>999) {
			throw new BizException("存款金额不能大于999");
		}
		//int i=1/0;
		odao.insert(id, money);
	}
	
	//创发transfer 天辰
	public void transfer(int id1,int id2,double money) throws BizException {
		save(id1, -money);
		save(id2, money);
	}
}

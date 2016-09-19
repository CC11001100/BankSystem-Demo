package org.cc.bank.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.cc.bank.dao.BusinessDao;
import org.cc.bank.dao.CustomerDao;
import org.cc.bank.exception.MoneyNotEnoughException;
import org.cc.bank.exception.MoneyOverFlowException;
import org.cc.bank.exception.PasswdNotSafeException;
import org.cc.bank.po.Business;
import org.cc.bank.po.Customer;
import org.cc.bank.po.Business.Operation;
import org.cc.bank.service.CustomerService;
import org.cc.bank.settings.Settings;
import org.cc.bank.utils.BeanFactory;
import org.cc.bank.utils.DbUtils;

/**
 * 普通用户可以进行的操作
 * @author CC11001100
 *
 */
public class CustomerServiceImpl implements CustomerService {
	
	private DbUtils dbUtils=DbUtils.getInstance();
	private BeanFactory beanFactory=BeanFactory.getInstance();
	
	private CustomerDao dao=beanFactory.getBean(CustomerDao.class);
	private BusinessDao businessDao=beanFactory.getBean(BusinessDao.class);
	
	@Override
	public void saveMeney(Customer c,double howmuch) throws MoneyOverFlowException {
		if(c.getMoney()+howmuch>Settings.MAX_MONEY) throw new MoneyOverFlowException("钱溢出了...");
		
		//保存流水 先记录日志
		Business b=new Business(c.getAccountNumber(),Operation.SAVE,howmuch);
		businessDao.add(b);
		
		//再操作数据库
		c.setMoney(c.getMoney()+howmuch);
		dao.modify(c);
	}

	@Override
	public void drawMoney(Customer c,double howmuch) throws MoneyNotEnoughException {
		if(c.getMoney()-howmuch<0) throw new MoneyNotEnoughException("钱不够啦");
		
		//保存流水
		Business b=new Business(c.getAccountNumber(),Operation.DRAW,howmuch);
		businessDao.add(b);
		
		//修改数据库数据
		c.setMoney(c.getMoney()-howmuch);
		dao.modify(c);
	}

	@Override
	public Customer login(String username, String passwd) {
		return dao.login(username,passwd);
	}

	@Override
	public boolean logout(Customer t) {
		return false;
	}

	@Override
	public void changePasswd(Customer c, String newPasswd) throws PasswdNotSafeException {
		if(newPasswd==null || newPasswd.length()<6) throw new PasswdNotSafeException("密码不能为空或少于六位");
		c.setPasswd(newPasswd);
		dao.modify(c);
	}

	@Override
	public Customer load(String id) {
		return dao.load(id);
	}

	@Override
	public void transferMoney(String c1, String c2, double howmuch) throws MoneyNotEnoughException, MoneyOverFlowException, SQLException {
		Customer cs1=dao.load(c1);
		Customer cs2=dao.load(c2);
		
		if(cs1.getMoney()-howmuch<0) throw new MoneyNotEnoughException("人活着，钱没了");
		else if(cs2.getMoney()+howmuch>Settings.MAX_MONEY) throw new MoneyOverFlowException("钱溢出了");
		
		cs1.setMoney(cs1.getMoney()-howmuch);
		cs2.setMoney(cs2.getMoney()+howmuch);
		
		Connection conn=null;
		try {
			//先保存流水
			Business b=new Business(c1,Operation.TRANSFER,howmuch);
			businessDao.add(b);
			
			//再进行操作   这样子即使出了问题只要一看日志然后对比实际就可以明确了，否则的话没有日志实在是没法对证
			conn=dbUtils.getConnection();
			conn.setAutoCommit(false);
			
			//原子性操作
			dao.modify(conn,cs1);
			dao.modify(conn,cs2);
			
			conn.commit();
		} catch (SQLException e) {
//			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		}finally{
			dbUtils.close(conn);
		}
	}

	@Override
	public Customer loadByName(String name) {
		return dao.loadByName(name);
	}
	
	@Override
	public List<Customer> list() {
		return dao.list();
	}

}

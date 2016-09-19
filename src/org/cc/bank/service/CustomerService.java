package org.cc.bank.service;

import java.sql.SQLException;
import java.util.List;

import org.cc.bank.exception.MoneyNotEnoughException;
import org.cc.bank.exception.MoneyOverFlowException;
import org.cc.bank.exception.PasswdNotSafeException;
import org.cc.bank.po.Customer;

/**
 * 普通用户可以进行的操作
 * @author CC11001100
 *
 */
public interface CustomerService extends LoginService<Customer>  {

	/**
	 * 存钱
	 * @param c
	 */
	public void saveMeney(Customer c,double howmuch) throws MoneyOverFlowException;
	
	/**
	 * 取钱
	 * @param c
	 */
	public void drawMoney(Customer c,double howmuch) throws MoneyNotEnoughException;
	
	/**
	 * 根据卡号加载顾客
	 * @param id
	 * @return
	 */
	public Customer load(String id);
	
	/**
	 * 根据名字查询
	 * @param name
	 * @return
	 */
	public Customer loadByName(String name);
	
	/**
	 * 谁给谁打了多钱
	 * @param c1
	 * @param c2
	 * @param howmuch
	 */
	public void transferMoney(String c1,String c2,double howmuch) throws MoneyNotEnoughException, MoneyOverFlowException, SQLException ;
	
	/**
	 * 修改密码
	 * @param c
	 */
	public void changePasswd(Customer c,String newPasswd) throws PasswdNotSafeException;
	
	/**
	 * 列出所有的
	 * @return
	 */
	public List<Customer> list();
	
}

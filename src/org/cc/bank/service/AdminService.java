package org.cc.bank.service;

import java.util.List;

import org.cc.bank.po.Admin;
import org.cc.bank.po.Customer;

/**
 * 管理员可以进行的操作
 * @author CC11001100
 *
 */
public interface AdminService extends LoginService<Admin> {

	/**
	 * 添加普通客户
	 * @param t
	 */
	public void addCustomer(Customer t);
	
	/**
	 * 获得所有的钱
	 * @return
	 */
	public double getTotalMeney();
	
	/**
	 * 排行榜 
	 * @return
	 */
	public List<Customer> rankingList();
	
}

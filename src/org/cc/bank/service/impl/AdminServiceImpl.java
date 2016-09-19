package org.cc.bank.service.impl;

import java.util.Collections;
import java.util.List;

import org.cc.bank.dao.AdminDao;
import org.cc.bank.dao.CustomerDao;
import org.cc.bank.po.Admin;
import org.cc.bank.po.Customer;
import org.cc.bank.service.AdminService;
import org.cc.bank.utils.BeanFactory;

/**
 * 管理员进行的操作
 * @author CC11001100
 *
 */
public class AdminServiceImpl implements AdminService{

	private AdminDao adminDao;
	private CustomerDao customerDao;
	
	public AdminServiceImpl() {
		adminDao=BeanFactory.getInstance().getBean(AdminDao.class);
		customerDao=BeanFactory.getInstance().getBean(CustomerDao.class);
	}

	@Override
	public Admin login(String username, String passwd) {
		return adminDao.login(username,passwd);
	}

	@Override
	public boolean logout(Admin t) {
		return adminDao.logout(t);
	}

	@Override
	public void addCustomer(Customer t) {
		customerDao.add(t);
	}

	@Override
	public double getTotalMeney() {
		return adminDao.totalMoney();
	}

	@Override
	public List<Customer> rankingList() {
		List<Customer> list=customerDao.list();
		Collections.sort(list);
		return list;
	}

	
}

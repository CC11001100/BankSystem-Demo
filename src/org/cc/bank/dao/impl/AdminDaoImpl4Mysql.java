package org.cc.bank.dao.impl;

import java.util.List;

import org.cc.bank.dao.AdminDao;
import org.cc.bank.po.Admin;
import org.cc.bank.po.Customer;

/**
 * 管理员Dao实现类
 * @author CC11001100
 *
 */
public class AdminDaoImpl4Mysql extends BaseDao implements AdminDao {

	@Override
	public void add(Admin t) {
		//实现请打钱..
	}

	@Override
	public void del(String id) {
		//实现请打钱..
	}

	@Override
	public void modify(Admin t) {
		//实现请打钱..
	}

	@Override
	public Admin load(String id) {
		//实现请打钱..
		return null;
	}

	@Override
	public List<Admin> list() {
		//实现请打钱..
		return null;
	}

	@Override
	public Admin login(String username, String passwd) {
		String sql="SELECT adminNumber AS number, adminPwd AS passwd, adminName AS name FROM administrator WHERE adminName=? and adminPwd=?";
		List<Admin> list=dql(Admin.class,sql,username,passwd);
		if(list.isEmpty()) return null;
		return list.get(0);
	}
	
	@Override
	public double totalMoney() {
		String sql="SELECT SUM(custMoney) AS money FROM customer ";
		List<Customer> list=dql(Customer.class,sql);
		return list.get(0).getMoney();
	}

	@Override
	public boolean logout(Admin t) {
		//实现请打钱..
		return false;
	}
}

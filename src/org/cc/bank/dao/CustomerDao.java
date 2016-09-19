package org.cc.bank.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.cc.bank.po.Customer;

/**
 * 顾客Dao
 * @author CC11001100
 *
 */
public interface CustomerDao extends Dao<Customer>, LoginDao<Customer> {

	/**
	 * 根据用户名查询
	 * @param name
	 * @return
	 */
	public Customer loadByName(String name);
	
	/**
	 * 使用传入的Connection，事务
	 * @param conn
	 * @param c
	 */
	public void modify(Connection conn,Customer c) throws SQLException;
	
}

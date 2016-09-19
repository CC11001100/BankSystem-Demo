package org.cc.bank.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.cc.bank.dao.CustomerDao;
import org.cc.bank.po.Customer;
import org.cc.bank.utils.DbUtils;

/**
 * Customer Dao mysql实现
 * @author CC11001100
 *
 */
public class CustomerDaoImpl4Mysql extends BaseDao implements CustomerDao {

	private DbUtils dbUtil=DbUtils.getInstance();
	
	@Override
	public void add(Customer t) {
		StringBuilder sql=new StringBuilder();
		sql.append("INSERT INTO customer(custNumber,custName,custPwd,custIdCard,custMoney,custDate) ").
			append("VALUES ").
			append("(?,?,?,?,?,?)"); 
		
		dml(sql.toString(),t.getAccountNumber(),t.getName(),t.getPasswd(),t.getIdCard(),t.getMoney(),t.getCreateDate());
	}

	@Override
	public void del(String id) {
		//我勒个去竟然没有要求删除功能....好吧，项目经理说此功能等待客户打钱实现    ：)
	}

	@Override
	public void modify(Customer t) {
		String sql="UPDATE customer SET custName=?,custPwd=?,custIdCard=?,custMoney=?,custDate=? WHERE custNumber=? ";
		dml(sql,t.getName(),t.getPasswd(),t.getIdCard(),t.getMoney(),t.getCreateDate(),t.getAccountNumber());
	}

	@Override
	public void modify(Connection conn, Customer t) throws SQLException {
		String sql="UPDATE customer SET custName=?,custPwd=?,custIdCard=?,custMoney=?,custDate=? WHERE custNumber=? ";
		dml(conn,sql,t.getName(),t.getPasswd(),t.getIdCard(),t.getMoney(),t.getCreateDate(),t.getAccountNumber());
	}
	
	@Override
	public Customer load(String id) {
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT custNumber AS accountNumber, custName AS name, custPwd AS passwd, custIdCard AS idCard, custMoney AS money, custDate AS createDate ").
		append("FROM customer WHERE custNumber=? ");
		
		List<Customer> list=dql(Customer.class,sql.toString(),id);
		
		if(list.isEmpty()) return null;
		return list.get(0);
	}

	@Override
	public List<Customer> list() {
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT custNumber AS accountNumber, custName AS name, custPwd AS passwd, custIdCard AS idCard, custMoney AS money, custDate AS createDate ").
		append("FROM customer");
		
		List<Customer> list=dql(Customer.class,sql.toString());
		
		//项目经理说让客户给钱优化...
//		Thread.sleep(1000);
		
		return list;
	}

	@Override
	public Customer login(String username, String passwd) {
		String sql="SELECT custNumber AS accountNumber, custName AS name, custPwd AS passwd, custIdCard AS idCard, custMoney AS money, custDate AS createDate FROM customer WHERE custName=? and custPwd=?";
		List<Customer> list=dql(Customer.class,sql,username,passwd);
		if(list.isEmpty()) return null;
		return list.get(0);
	}

	@Override
	public boolean logout(Customer t) {
		//nothing  真实系统登出的时候可能有需要清理的资源之类的...我猜  - - 
		return true;
	}

	@Override
	public Customer loadByName(String name) {
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT custNumber AS accountNumber, custName AS name, custPwd AS passwd, custIdCard AS idCard, custMoney AS money, custDate AS createDate ").
		append("FROM customer WHERE custName=? ");
		
		List<Customer> list=dql(Customer.class,sql.toString(),name);
		
		if(list.isEmpty()) return null;
		return list.get(0);
	}

}

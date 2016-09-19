package org.cc.bank.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.cc.bank.dao.impl.CustomerDaoImpl4Mysql;
import org.cc.bank.po.Customer;
import org.junit.Ignore;
import org.junit.Test;

/**
 * CustomerDaoImpl单元测试
 * @author CC11001100
 *
 */
public class CustomerDaoImplTest {

	private static CustomerDaoImpl4Mysql dao=new CustomerDaoImpl4Mysql();
	
	@Ignore
	@Test
	public void testAdd() {
		Customer c=new Customer();
		c.setAccountNumber("1001");
		c.setCreateDate(new Date());
		c.setIdCard("372901199506160000");
		c.setMoney(1000);
		c.setName("CC");
		c.setPasswd("qwerty");
		dao.add(c);
	}

	@Ignore
	@Test
	public void testDel() {
		//nothing..
	}

	@Ignore
	@Test
	public void testModify() {
		Customer c=new Customer();
		c.setAccountNumber("1001");
		c.setCreateDate(new Date());
		c.setIdCard("372901199506160000");
		c.setMoney(10000);
		c.setName("CC");
		c.setPasswd("qwerty");
		dao.modify(c);
	}

	@Ignore
	@Test
	public void testLoad() {
		Customer c=dao.load("1001");
		assertNotNull("查询失败",c);
	}

	@Ignore
	@Test
	public void testList() {
		List<Customer> list=dao.list();
		for(Customer c:list){
			System.out.println(c);
		}
	}

}

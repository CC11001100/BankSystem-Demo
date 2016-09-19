package org.cc.bank.test;

import static org.junit.Assert.*;

import org.cc.bank.dao.impl.AdminDaoImpl4Mysql;
import org.cc.bank.po.Admin;
import org.junit.Test;

public class AdminDaoImplTest {

	@Test
	public void testLogin() {
		
		Admin admin=new AdminDaoImpl4Mysql().login("root","toor");
		assertNotNull(admin);
	}

}

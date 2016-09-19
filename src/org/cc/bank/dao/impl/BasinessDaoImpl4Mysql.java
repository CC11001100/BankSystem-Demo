package org.cc.bank.dao.impl;

import java.util.List;

import org.cc.bank.dao.BusinessDao;
import org.cc.bank.po.Business;

/**
 * 流水操作 Dao实现
 * @author CC11001100
 *
 */
public class BasinessDaoImpl4Mysql extends BaseDao implements BusinessDao {

	@Override
	public void add(Business t) {
		String sql="INSERT INTO business(custNumber, businessType, businessMoney) VALUES (?,?,?)";
		dml(sql,t.getCustomerAccountNumber(),t.getBusinessType().getValue(),t.getMoney());
	}

	@Override
	public void del(String id) {
		//实现请打钱..
	}

	@Override
	public void modify(Business t) {
		//实现请打钱..
	}

	@Override
	public Business load(String id) {
		//实现请打钱..
		return null;
	}

	@Override
	public List<Business> list() {
		//实现请打钱..
		return null;
	}

}

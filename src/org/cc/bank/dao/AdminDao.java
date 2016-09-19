package org.cc.bank.dao;

import org.cc.bank.po.Admin;

/**
 * 管理员Dao
 * @author CC11001100
 *
 */
public interface AdminDao extends Dao<Admin>, LoginDao<Admin>{

	/**
	 * 数钱.... 
	 * @return
	 */
	public double totalMoney() ;
	
}

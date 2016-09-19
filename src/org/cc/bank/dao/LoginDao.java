package org.cc.bank.dao;

/**
 * 负责的实体是可以登入登出的Dao都必须实现这个接口以提供登录功能
 * @author CC11001100
 *
 */
public interface LoginDao<T> {

	/**
	 * 登入
	 * @param username
	 * @param passwd
	 * @return
	 */
	public T login(String username,String passwd);
	
	/**
	 * 登出
	 * @param t
	 * @return
	 */
	public boolean logout(T t);
	
}

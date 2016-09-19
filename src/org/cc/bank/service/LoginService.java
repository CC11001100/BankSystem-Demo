package org.cc.bank.service;

/**
 * 通用操作抽取为父接口
 * @author CC11001100
 *
 */
public interface LoginService<T>{

	/**
	 * 登录操作
	 * @param username
	 * @param passwd
	 * @return
	 */
	public T login(String username,String passwd);
	
	/**
	 * 登出操作
	 * @param t
	 * @return
	 */
	public boolean logout(T t);
	
}

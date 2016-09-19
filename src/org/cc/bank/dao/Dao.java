package org.cc.bank.dao;

import java.util.List;

/**
 * dao规范，所有dao都应该实现的功能
 * @author CC11001100
 *
 */
public interface Dao<T> {
	
	/**
	 * 添加
	 * @param t
	 */
	public void add(T t);

	/**
	 * 删除
	 * @param t
	 */
	public void del(String id);
	
	/**
	 * 修改
	 * @param t
	 */
	public void modify(T t);
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public T load(String id);
	
	/**
	 * 列出所有
	 * @return
	 */
	public List<T> list();
	
}

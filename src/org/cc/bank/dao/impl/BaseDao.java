package org.cc.bank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cc.bank.utils.DbUtils;
import org.cc.bank.utils.ReflectionUtil;

/**
 * Dao基础类，提供了通用的实现
 * @author CC11001100
 *
 */
public class BaseDao {

	/**
	 * 执行sql dql语句
	 * @param clazz
	 * @param sql
	 * @param args
	 * @return
	 */
	public <T> List<T> dql(Class<T> clazz,String sql,Object ...args){
		
		List<T> list=new ArrayList<>();
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn=DbUtils.getInstance().getConnection();
			pstmt=conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				pstmt.setObject(i+1,args[i]);
			}
			rs=pstmt.executeQuery();
			while(rs.next()){
				Map<String,Object> map=new HashMap<>();
				ResultSetMetaData meta=rs.getMetaData();
				for(int i=1;i<=meta.getColumnCount();i++){
					map.put(meta.getColumnLabel(i),rs.getObject(i));
				}
				T o=clazz.newInstance();
				for(String key:map.keySet()){
					ReflectionUtil.setProperty(o,key,map.get(key));
				}
				list.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}finally{
			DbUtils.getInstance().close(conn,pstmt,rs);
		}
		return list;
	}
	
	/**
	 * 执行sql dml语句，因为可能有事务，所以连接是传进来的
	 * @param conn
	 * @param sql
	 * @param args
	 * @throws SQLException
	 */
	public void dml(Connection conn,String sql,Object ...args) throws SQLException{
		PreparedStatement pstmt=null;
		
		try {
			pstmt=conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				pstmt.setObject(i+1,args[i]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			DbUtils.getInstance().close(pstmt);
		}
	}
	
	/**
	 * 执行sql dml语句，不需要事务的话就用这个，不能为了20%而麻烦80%...
	 * 不需要事务，所以异常也不抛出了
	 * @param conn
	 * @param sql
	 * @param args
	 * @throws SQLException
	 */
	public void dml(String sql,Object ...args){
		Connection conn=null;
		try {
			conn=DbUtils.getInstance().getConnection();
			dml(conn,sql,args);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtils.getInstance().close(conn);
		}
	}
	
}

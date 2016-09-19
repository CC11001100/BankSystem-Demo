package org.cc.bank.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *  数据库工具类
 * @author CC11001100
 *
 */
public class DbUtils {
	
	//配置文件
	private Properties attr=null;
	//默认的数据库配置文件路径
	private String path="db.properties";
	
	private DbUtils() {
		try {
			attr=new Properties();
			//加载数据库配置文件
			InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			attr.load(is);
			Class.forName(attr.getProperty("driver"));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static DbUtils instance;
	
	//D-C-L
	public synchronized static DbUtils getInstance(){
		if (instance == null){
			synchronized (DbUtils.class) {
				if (instance == null) instance = new DbUtils();
			}
		}
		return instance;
	}
	
	/**
	 * 重新加载一个配置文件
	 * @param attr
	 */
	public void load(Properties attr){
		this.attr=attr;
	}
	
	/**
	 * 重新加载一个配置文件
	 * @param attr
	 */
	public void load(String configPath){
		try {
			attr=new Properties();
			attr.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(configPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到一个Connection
	 * @return
	 */
	public Connection getConnection(){
		try {
			return DriverManager.getConnection(attr.getProperty("url"),attr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 关闭Connection
	 * @param conn
	 */
	public void close(Connection conn){
		try {
			if(conn!=null && !conn.isClosed()) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭Statement
	 * @param st
	 */
	public void close(Statement st){
		try {
			if(st!=null && !st.isClosed()) st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭ResultSet
	 * @param rs
	 */
	public void close(ResultSet rs){
		try {
			if(rs!=null && !rs.isClosed()) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭资源
	 * @param conn
	 * @param pstmt
	 * @param rs
	 */
	public void close(Connection conn,PreparedStatement pstmt,ResultSet rs){
		close(rs);
		close(pstmt);
		close(conn);
	}
	
	/**
	 * 关闭资源
	 * @param conn
	 * @param pstmt
	 * @param rs
	 */
	public void close(PreparedStatement pstmt,ResultSet rs){
		close(rs);
		close(pstmt);
	}
		
}

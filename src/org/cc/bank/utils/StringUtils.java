package org.cc.bank.utils;

/**
 * 字符工具类，提供一些字符串的处理方法
 * @author CC11001100
 *
 */
public class StringUtils {
	
	/**
	 * 将字串的首字母大写返回
	 * @param s
	 * @return
	 */
	public static String initialUpperCase(String s){
		if(s.length()==0) return s;
		return Character.toUpperCase(s.charAt(0))+s.substring(1,s.length());
	}

}

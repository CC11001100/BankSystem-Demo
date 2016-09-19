package org.cc.bank.utils;

import java.util.Scanner;

/**
 * 从输入缓冲区读入数据的工具类（至少要抵御得住二逼用户的第一轮进攻...一大波二逼用户三十秒后到达战场......）
 * @author CC11001100
 *
 */
public class ReaderUtils {

	private static Scanner sc=new Scanner(System.in);
	
	/**
	 * 读入一个数字（死循环读，必须要读入）
	 * @param mesg
	 * @param errorMessage
	 * @return
	 */
	public static int readNumber(String mesg,String errorMesg){
		boolean first=true;
		while(true){
			log(false,first?mesg:errorMesg);
			first=false;
			String s=sc.nextLine().trim();
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
			}
		}
	}
	
	/**
	 * 读入一行
	 * @param leastLength
	 * @param mesg
	 * @param errMesg
	 * @return
	 */
	public static String readString(int leastLength,String mesg,String errMesg){
		boolean first=true;
		while(true){
			log(false,first?mesg:errMesg);
			first=false;
			String s=sc.nextLine().trim();
			if(s.length()==0) continue;
			if(s.length()>=leastLength) return s;
			else {
				log("长度不能少于"+leastLength);
			}
		}
	}
	
	/**
	 * 读入一个小数
	 * @param mesg
	 * @param errMesg
	 * @return
	 */
	public static double readDouble(String mesg,String errMesg){
		boolean first=true;
		while(true){
			log(false,first?mesg:errMesg);
			first=false;
			String s=sc.nextLine().trim();
			try {
				return Double.parseDouble(s);
			} catch (NumberFormatException e) {
			}
		}
	}
	
	/**
	 * 内部方法，封装一下sysout以方便打印
	 * @param args
	 */
	private static void log(Object ...args){
		log(true,args);
	}
	
	/**
	 *  选择打印完是否换行
	 * @param newLine
	 * @param args
	 */
	private static void log(Boolean newLine,Object ...args){
		for(Object o:args) System.out.print(o+" ");
		if(newLine) System.out.println();
	}
	
}

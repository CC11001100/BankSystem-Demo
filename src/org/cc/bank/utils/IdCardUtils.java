package org.cc.bank.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 身份证验证工具类
 * @author CC11001100
 *
 */
public class IdCardUtils {

	//权
	private static final int WEIGHT[]={7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
	//余数 映射
	private static final int MAPPING[]={1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	
	/**
	 * 传入身份证号，验证是否符合规则
	 * 符合规则并不一定代表一定存在这么一个人，够胆的话就去公安局人口数据库里比对....
	 * @param s
	 * @return
	 */
	public static boolean validate(String s){
		if(!s.matches("[0-9]{17}[0-9a-zA-Z]{1}")) return false;
		int sum=0;
		for(int i=0;i<WEIGHT.length;i++){
			sum+=(s.charAt(i)-'0')*WEIGHT[i]; 
		}
		int mod=MAPPING[sum%11];
		char last=s.charAt(s.length()-1);
		if((last=='X' || last=='x') && (mod==10) ) return true;
		else if(last-'0'==mod) return true;
		else return false;
	}
	
	
	
	
	
	
	
	/*---------------------- ignore below  -------------------------------*/
	
	/**
	 * 随机生成一个可用的身份证号码，因为后面测试要用得到...不然我满大街问别人身份证不太好....
	 * @return
	 */
	public static String getRandIdCard(){
		int year=new Random().nextInt(Calendar.getInstance().get(Calendar.YEAR)-1970)+1970; //从1970到今年
		int month=new Random().nextInt(2)+10; //随机月份
		int day=new Random().nextInt(18)+10; //简单考虑
		int order=new Random().nextInt(233)+100; //简单考虑，随机男女
		String s=new StringBuilder(17).append("372901").append(year).append(month).append(day).append(order).toString();
		int sum=0;
		for(int i=0;i<s.length();i++){
			sum+=(s.charAt(i)-'0')*WEIGHT[i];
		}
		int mod=sum%11;
		return s+(MAPPING[mod]==10?"X":MAPPING[mod]);
	}
	
}

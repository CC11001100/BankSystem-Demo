package org.cc.bank.utils;

/**
 * 银行卡校验工具类
 * @author CC11001100
 *
 */
public class BankCardUtils {

	/**
	 * 校验是否是有效的银行卡号(19位)  
	 * Luhn算法
	 * @param s
	 * @return
	 */
	public static boolean validate(String s){
		if(!s.matches("[1-9]{1}[0-9]{18}")) return false;
		
		char cs[]=new StringBuilder(s).reverse().toString().toCharArray();
		int sum=0;
		for(int i=0;i<cs.length;i++){
			int t=cs[i]-'0';
			sum+=(i==i/2*2?t:(t*2>=10?t*2-9:t*2));
		}
		return sum/10*10==sum;
	}
	
}

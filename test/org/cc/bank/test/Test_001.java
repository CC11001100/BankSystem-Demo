package org.cc.bank.test;

import org.cc.bank.po.Business.Operation;
import org.junit.Ignore;
import org.junit.Test;

public class Test_001 {

	@Ignore
	@Test
	public void test_001(){
		
		System.out.println(Operation.DRAW.getValue());
		
	}
	
	
	
	@Test
	public void test_002(){

		String matcher="[\u4E00-\u9FA5a-zA-Z0-9]{1,20}";
		
		System.out.println("哈你好".matches(matcher));
		
		
		
	}
	
	
}

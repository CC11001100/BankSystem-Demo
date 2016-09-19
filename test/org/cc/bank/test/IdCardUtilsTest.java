package org.cc.bank.test;

import static org.junit.Assert.*;

import org.cc.bank.utils.IdCardUtils;
import org.junit.Test;

public class IdCardUtilsTest {

	@Test
	public void testValidate() {
		
		for(int i=0;i<10;i++){
			String s=IdCardUtils.getRandIdCard();
			System.out.println(s);
			assertTrue(IdCardUtils.validate(s));
		}
		
	}

}

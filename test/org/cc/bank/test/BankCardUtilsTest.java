package org.cc.bank.test;

import static org.junit.Assert.*;

import org.cc.bank.utils.BankCardUtils;
import org.junit.Test;

public class BankCardUtilsTest {

	
	@Test
	public void testValidate() {
		
		assertTrue(BankCardUtils.validate("6228480402564890018"));
		
	}
	
	
}

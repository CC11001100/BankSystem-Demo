package org.cc.bank.exception;

/**
 * 钱溢出了...
 * @author CC11001100
 *
 */
public class MoneyOverFlowException extends Exception {

	public MoneyOverFlowException(String mesg) {
		super(mesg);
	}
	
}

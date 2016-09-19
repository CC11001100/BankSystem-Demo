package org.cc.bank.exception;

/**
 * 当取钱的时候钱不够了就抛出此异常。
 * @author CC11001100
 *
 */
public class MoneyNotEnoughException extends Exception {

	public MoneyNotEnoughException(String message) {
		super(message);
	}
	
}

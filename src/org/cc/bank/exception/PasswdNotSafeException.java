package org.cc.bank.exception;

/**
 * 密码不符合安全标准的时候抛出
 * @author CC11001100
 *
 */
public class PasswdNotSafeException extends Exception {

	public PasswdNotSafeException(String mesg) {
		super(mesg);
	}
	
}

package org.cc.bank.po;

/**
 * 只要是系统里面的人，这就是他爸爸....
 * @author CC11001100
 *
 */
public class Person {

	//若想登录进系统，则必须具有姓名和密码
	private String name;
	private String passwd;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
}

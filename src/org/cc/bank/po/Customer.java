package org.cc.bank.po;

import java.util.Date;

/**
 * 客户模型
 * @author CC11001100
 *
 */
public class Customer extends Person implements Comparable<Customer> {

	private String accountNumber;
	private String idCard;
	private double money;
	private Date createDate;
	
	public Customer() {
	}
	
	public Customer(String accountNumber,String name,String passwd, String idCard, double money, Date createDate) {
		this.accountNumber = accountNumber;
		setName(name);
		setPasswd(passwd);
		this.idCard = idCard;
		this.money = money;
		this.createDate = createDate;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Customer){
			Customer c=(Customer) obj;
			return getName().equals(c.getName());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return accountNumber.hashCode() ^ getName().hashCode();
	}
	
	@Override
	public String toString() {
		return "Customer [accountNumber=" + accountNumber + ", name=" + getName() + ", passwd=" + getPasswd() + ", idCard="
				+ idCard + ", money=" + money + ", createDate=" + createDate + "]";
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	//富豪榜排序要用
	@Override
	public int compareTo(Customer o) {
		if(!(Math.abs(o.getMoney()-this.getMoney())<1E-10)){
			return this.getMoney()-o.getMoney()>0?-1:1;
		}else {
			return 0;
		}
	}
}

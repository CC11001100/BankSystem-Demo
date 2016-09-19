package org.cc.bank.po;

/**
 * 流水模型，代表了一次流水操作
 * @author CC11001100
 *
 */
public class Business {

	private int orderId;
	//如果是转账的话，又该如何存储呢？
	private String customerAccountNumber ;
	private Operation businessType;
	private double money;
	
	/**
	 * @param customerAccountNumber 卡号
	 * @param businessType 业务类型
	 * @param money 涉案金额 - -
	 */
	public Business(String customerAccountNumber, Operation businessType, double money) {
		this.customerAccountNumber = customerAccountNumber;
		this.businessType = businessType;
		this.money = money;
	}
	
	/**
	 * 定义了流水操作
	 * @author CC11001100
	 *
	 */
	public enum Operation {
		SAVE("存"), //存钱
		DRAW("取"), //取钱
		TRANSFER("转"); //转账
		private final String value;
		private Operation(String v) {
			this.value=v;
		}
		public String getValue(){
			return this.value;
		}
	}
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getCustomerAccountNumber() {
		return customerAccountNumber;
	}
	public void setCustomerAccountNumber(String customerAccountNumber) {
		this.customerAccountNumber = customerAccountNumber;
	}
	public Operation getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Operation businessType) {
		this.businessType = businessType;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	
}

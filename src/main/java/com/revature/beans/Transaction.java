package com.revature.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.revature.helpers.BigDecimalHelper;

public class Transaction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1110170253158235115L;
	private User user;
	private int transactionID;
	private BigDecimal amount;
	private LocalDateTime time;
	
	public Transaction(User user, BigDecimal amount, LocalDateTime time) {
		super();
		this.amount = amount;
		this.time = time;
		this.user = user;
		transactionID = user.getNewTransactionID();
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getTransactionID() {
		return transactionID;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public String getAmountString() {
		return BigDecimalHelper.getMoneyString(amount);
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Transaction [user=" + user.toString() + ", transactionID=" + transactionID + ", amount=" + amount
				+ ", time=" + time + "]";
	}

}
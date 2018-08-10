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
	private String username;
	private int transactionID;
	private BigDecimal amount;
	private LocalDateTime time;
	private static int lastTransactionID = 0;
	
	public Transaction(BigDecimal amount, LocalDateTime time) {
		super();
		this.amount = amount;
		this.time = time;
		transactionID = lastTransactionID + 1;
		lastTransactionID++;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public static void setLastTransactionID(int transactionID) {
		lastTransactionID = transactionID;
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
		return "Transaction [username=" + username + ", transactionID=" + transactionID + ", amount=" + amount
				+ ", time=" + time + "]";
	}

}
package com.revature.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1233740070978649447L;
	private BigDecimal amount;
	private LocalDateTime time;
	public Transaction(BigDecimal amount, LocalDateTime time) {
		super();
		this.amount = amount;
		this.time = time;
	}
	public BigDecimal getAmount() {
		return amount;
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
		return "Transaction [amount=" + amount + ", time=" + time + "]";
	}
}
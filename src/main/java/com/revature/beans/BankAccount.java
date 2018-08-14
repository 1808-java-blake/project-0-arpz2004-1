package com.revature.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.TreeSet;

public class BankAccount implements Serializable {

	public enum AccountType {
		SAVINGS("Savings"), CHECKING("Checking");

		private final String value;

		private AccountType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3914513090847475291L;

	private BigDecimal balance;
	private String username;
	private AccountType accountType;
	private TreeSet<Integer> transactionHistory;

	public BankAccount() {
		super();
		this.balance = new BigDecimal(0);
		transactionHistory = new TreeSet<>();
	}

	public BankAccount(String username, AccountType accountType) {
		this();
		this.username = username;
		this.accountType = accountType;
		transactionHistory = new TreeSet<>();
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public String getAccountTypeString() {
		return accountType.getValue();
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String getBalanceString() {
		return "$" + this.balance.setScale(2);
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public void setBalance(int balance) {
		this.balance = new BigDecimal(balance);
	}

	public int getNewTransactionID() {
		boolean duplicate = true;
		int newID = 1;
		while (duplicate) {
			if (!transactionHistory.isEmpty()) {
				int lastID = transactionHistory.last();
				newID = lastID + 1;
			}
			duplicate = !transactionHistory.add(newID);
		}
		return newID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BankAccount [balance=" + balance + ", username=" + username + "]";
	}

}
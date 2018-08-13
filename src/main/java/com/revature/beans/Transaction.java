package com.revature.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.revature.helpers.BigDecimalHelper;

public class Transaction implements Serializable {

	public enum TransactionType {
		WITHDRAWAL("Withdrawal"), DEPOSIT("Deposit"), WIRE_TRANSFER("Wire Transfer");

		private final String value;

		private TransactionType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1110170253158235115L;
	private User user;
	private BankAccount ba;
	private int transactionID;
	private BigDecimal amount;
	private LocalDateTime time;
	private TransactionType transactionType;
	private BankAccount bankAccountTransferredTo;

	public Transaction(User user, BigDecimal amount, LocalDateTime time, BankAccount ba,
			TransactionType transactionType) {
		super();
		this.amount = amount;
		this.time = time;
		this.user = user;
		this.ba = ba;
		transactionID = user.getNewTransactionID();
		this.transactionType = transactionType;
	}

	public Transaction(User user, BigDecimal amount, LocalDateTime time, BankAccount ba,
			TransactionType transactionType, BankAccount bankAccountTransferredTo) {
		this(user, amount, time, ba, transactionType);
		this.bankAccountTransferredTo = bankAccountTransferredTo;
	}

	public BankAccount getBankAccount() {
		return ba;
	}

	public void setBankAccount(BankAccount ba) {
		this.ba = ba;
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

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public String getTransactionTypeString() {
		return transactionType.getValue();
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public BankAccount getBankAccountTransferredTo() {
		return bankAccountTransferredTo;
	}

	public void setBankAccountTransferredTo(BankAccount bankAccountTransferredTo) {
		this.bankAccountTransferredTo = bankAccountTransferredTo;
	}

	public String getUsernameAndAccountTypeTransferredTo() {
		String usernameAndAccountTypeTransferredTo = null;
		if (bankAccountTransferredTo != null) {
			usernameAndAccountTypeTransferredTo = bankAccountTransferredTo.getUsername() + " ("
					+ bankAccountTransferredTo.getAccountTypeString() + ")";
		}
		return usernameAndAccountTypeTransferredTo;
	}

	@Override
	public String toString() {
		return "Transaction [user=" + user.toString() + ", transactionID=" + transactionID + ", amount=" + amount
				+ ", time=" + time + "]";
	}

}
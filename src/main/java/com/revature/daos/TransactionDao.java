package com.revature.daos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.revature.beans.Transaction;

public interface TransactionDao {
	public static final TransactionDao currentTransactionDao = new TransactionSerializer();
	
	void createTransaction(Transaction t);
	Transaction findByDateAndAmount(LocalDateTime date, BigDecimal amount);
	void updateTransaction(Transaction t);
	void deleteTransaction(Transaction t);
	
}

package com.revature.daos;

import java.util.List;

import com.revature.beans.BankAccount.AccountType;
import com.revature.beans.Transaction;

public interface TransactionDao {
	public static final TransactionDao currentTransactionDao = new TransactionSerializer();
	
	void createTransaction(Transaction t);
	public List<Transaction> findByUsernameAndAccountType(String username, AccountType accountType);
	void updateTransaction(Transaction t);
	void deleteTransaction(Transaction t);
	
}

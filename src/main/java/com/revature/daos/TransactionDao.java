package com.revature.daos;

import com.revature.beans.Transaction;
import com.revature.beans.User;

public interface TransactionDao {
	public static final TransactionDao currentTransactionDao = new TransactionSerializer();
	
	void createTransaction(Transaction t);
	Transaction findByUserAndID(User u, int transactionID);
	void updateTransaction(Transaction t);
	void deleteTransaction(Transaction t);
	
}

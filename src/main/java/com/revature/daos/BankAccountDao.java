package com.revature.daos;

import com.revature.beans.BankAccount;

public interface BankAccountDao {
	public static final BankAccountDao currentBankAccountDao = new BankAccountSerializer();
	
	void createBankAccount(BankAccount ba);
	BankAccount findByUsername(String username);
	void updateBankAccount(BankAccount ba);
	void deleteBankAccount(BankAccount ba);
	
}

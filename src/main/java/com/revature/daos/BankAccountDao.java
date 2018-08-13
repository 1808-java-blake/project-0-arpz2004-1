package com.revature.daos;

import java.util.List;

import com.revature.beans.BankAccount;

public interface BankAccountDao {
	public static final BankAccountDao currentBankAccountDao = new BankAccountSerializer();
	
	void createBankAccount(BankAccount ba);
	List<BankAccount> findByUsername(String username);
	void updateBankAccount(BankAccount ba);
	void deleteBankAccount(BankAccount ba);
	
}

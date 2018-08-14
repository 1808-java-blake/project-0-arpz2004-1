package com.revature.daos;

import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.revature.beans.BankAccount;
import com.revature.beans.BankAccount.AccountType;

public interface BankAccountDao {
	public static final BankAccountDao currentBankAccountDao = new BankAccountSerializer();
	
	void createBankAccount(BankAccount ba);
	List<BankAccount> findByUsername(String username);
	public List<BankAccount> findByUsernameAndType(Set<Entry<String, AccountType>> usernamesAndAccountTypes);
	void updateBankAccount(BankAccount ba);
	void deleteBankAccount(BankAccount ba);
	
}

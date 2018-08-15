package com.revature.daos;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.revature.beans.BankAccount;
import com.revature.beans.BankAccount.AccountType;

public class BankAccountDatabase implements BankAccountDao {

	@Override
	public void createBankAccount(BankAccount ba) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BankAccount> findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BankAccount> findByUsernameAndType(Set<Entry<String, AccountType>> usernamesAndAccountTypes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateBankAccount(BankAccount ba) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBankAccount(BankAccount ba) {
		// TODO Auto-generated method stub
		
	}

}

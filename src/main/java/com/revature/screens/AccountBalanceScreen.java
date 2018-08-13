package com.revature.screens;

import java.util.List;
import java.util.Scanner;

import com.revature.beans.BankAccount;
import com.revature.beans.User;
import com.revature.daos.BankAccountDao;
import com.revature.helpers.StringHelper;

public class AccountBalanceScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private BankAccountDao bad = BankAccountDao.currentBankAccountDao;

	@Override
	public Screen start() {
		List<BankAccount> bankAccountList = bad.findByUsername(User.getCurrentUser().getUsername());
		int accountTypeLength = 15;
		System.out.println(StringHelper.padRight("Account Type", accountTypeLength) + "Balance");
		for (BankAccount bankAccount : bankAccountList) {
			System.out.println(StringHelper.padRight(bankAccount.getAccountTypeString(), accountTypeLength) + bankAccount.getBalanceString());
		}
		System.out.println("Press enter to return to home screen.");
		scan.nextLine();
		return new HomeScreen();
	}

}
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
		User currentUser = User.getCurrentUser();
		List<BankAccount> bankAccountList = bad.findByUsername(currentUser.getUsername());
		List<BankAccount> sharedBankAccountList = bad.findByUsernameAndType(currentUser.getSharedAccounts());
		int maxUsernameLength = 0;
		for (BankAccount sharedBankAccount : sharedBankAccountList) {
			int usernameLength = sharedBankAccount.getUsername().length();
			if (usernameLength > maxUsernameLength) {
				maxUsernameLength = usernameLength;
			}
		}
		int usernameLength = Math.max(12, maxUsernameLength + 3);
		bankAccountList.addAll(sharedBankAccountList);
		int accountTypeLength = 15;
		System.out.println(StringHelper.padRight("Account Type", accountTypeLength)
				+ StringHelper.padRight("Shared By", usernameLength) + "Balance");
		for (BankAccount bankAccount : bankAccountList) {
			String username = bankAccount.getUsername();
			if (username.equals(currentUser.getUsername())) {
				username = "";
			}
			System.out.println(StringHelper.padRight(bankAccount.getAccountTypeString(), accountTypeLength)
					+ StringHelper.padRight(username, usernameLength) + bankAccount.getBalanceString());
		}
		System.out.println("Press enter to return to home screen.");
		scan.nextLine();
		return new HomeScreen();
	}

}
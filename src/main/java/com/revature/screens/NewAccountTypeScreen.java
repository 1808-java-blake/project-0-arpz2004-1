package com.revature.screens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.revature.beans.BankAccount;
import com.revature.beans.BankAccount.AccountType;
import com.revature.beans.User;
import com.revature.daos.BankAccountDao;
import com.revature.helpers.StringHelper;

public class NewAccountTypeScreen implements Screen {
	private BankAccountDao bd = BankAccountDao.currentBankAccountDao;
	private Scanner scan = new Scanner(System.in);

	@Override
	public Screen start() {
		User user = User.getCurrentUser();
		AccountType[] accountTypes = AccountType.values();
		Map<Integer, AccountType> findBankAccountType = new HashMap<>();
		List<AccountType> accountTypeList = new ArrayList<>(Arrays.asList(accountTypes));
		List<BankAccount> bankAccountList = bd.findByUsername(user.getUsername());
		for (BankAccount bankAccount : bankAccountList) {
			accountTypeList.remove(bankAccount.getAccountType());
		}
		int bankAccountTypeCount = accountTypeList.size();
		if (bankAccountTypeCount == 0) {
			System.out.println(
					"Every type of bank account has already been created. Press enter to return to home screen.");
			scan.nextLine();
		} else {
			int maxCountLength = String.valueOf(bankAccountTypeCount).length() + 3;
			System.out.println(StringHelper.padRight("#", maxCountLength) + "Account Type");
			int accountTypeCount = 0;
			for (AccountType accountType : accountTypeList) {
				accountTypeCount++;
				findBankAccountType.put(accountTypeCount, accountType);
				System.out.println(StringHelper.padRight(String.valueOf(accountTypeCount), maxCountLength)
						+ accountType.getValue());
			}
			String selection;
			boolean validSelection = false;
			do {
				System.out.println(
						"Enter # of the type of bank account you want to create or press enter to return to home screen.");
				selection = scan.nextLine();
				if (selection.matches("^\\d+$")) {
					int bankAccountTypeNum = Integer.parseInt(selection);
					if (bankAccountTypeNum >= 1 && bankAccountTypeNum <= bankAccountTypeCount) {
						AccountType accountType = findBankAccountType.get(bankAccountTypeNum);
						BankAccount ba = new BankAccount(user.getUsername(), accountType);
						bd.createBankAccount(ba);
						System.out.println("New " + accountType.getValue()
								+ " account has been successfully created. Press enter to return to home screen.");
						scan.nextLine();
						return new HomeScreen();
					} else {
						System.out
								.println("Invalid number entered. Value must be between 1 and " + bankAccountTypeCount);
					}
				} else if (selection.isEmpty()) {
					validSelection = true;
				} else {
					System.out.println("Invalid value entered.");
				}
			} while (!validSelection);
		}
		return new HomeScreen();
	}
}

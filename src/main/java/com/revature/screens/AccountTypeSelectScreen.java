package com.revature.screens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.revature.beans.BankAccount;
import com.revature.beans.User;
import com.revature.daos.BankAccountDao;
import com.revature.helpers.StringHelper;

public class AccountTypeSelectScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private BankAccountDao bd = BankAccountDao.currentBankAccountDao;
	private User user;
	private String action;

	public AccountTypeSelectScreen(String action) {
		this.user = User.getCurrentUser();
		this.action = action;
	}

	public AccountTypeSelectScreen(User user, String action) {
		this.user = user;
		this.action = action;
	}

	public BankAccount getBankAccount() {
		Map<Integer, BankAccount> findBankAccount = new HashMap<>();
		List<BankAccount> bankAccountList = bd.findByUsername(user.getUsername());
		int bankAccountCount = bankAccountList.size();
		int maxCountLength = String.valueOf(bankAccountCount).length() + 3;
		System.out.println(StringHelper.padRight("#", maxCountLength) + "Account Type");
		int bankAccountNumber = 0;
		for (BankAccount bankAccount : bankAccountList) {
			bankAccountNumber++;
			findBankAccount.put(bankAccountNumber, bankAccount);
			System.out.println(StringHelper.padRight(String.valueOf(bankAccountNumber), maxCountLength)
					+ bankAccount.getAccountTypeString());
		}
		String selection;
		boolean validSelection = false;
		do {
			System.out.println("Enter bank account # to " + action + " or press enter to return to previous screen.");
			selection = scan.nextLine();
			if (selection.matches("^\\d+$")) {
				int bankAccountNum = Integer.parseInt(selection);
				if (bankAccountNum >= 1 && bankAccountNum <= bankAccountCount) {
					return findBankAccount.get(bankAccountNum);
				} else {
					System.out.println("Invalid number entered. Value must be between 1 and " + bankAccountCount);
				}
			} else if (selection.isEmpty()) {
				validSelection = true;
			} else {
				System.out.println("Invalid value entered.");
			}
		} while (!validSelection);
		return null;
	}

	@Override
	public Screen start() {
		return null;
	}
}

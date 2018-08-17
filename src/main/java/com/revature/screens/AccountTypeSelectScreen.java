package com.revature.screens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.revature.beans.BankAccount;
import com.revature.beans.User;
import com.revature.daos.BankAccountDao;
import com.revature.helpers.StringHelper;
import com.revature.util.AppState;

public class AccountTypeSelectScreen implements Screen {
	private AppState state = AppState.state;
	private Scanner scan = new Scanner(System.in);
	private BankAccountDao bd = BankAccountDao.currentBankAccountDao;
	private User user;
	private String action;

	public AccountTypeSelectScreen(String action) {
		this.user = state.getCurrentUser();
		this.action = action;
	}

	public AccountTypeSelectScreen(User user, String action) {
		this.user = user;
		this.action = action;
	}

	public BankAccount getBankAccount() {
		Map<Integer, BankAccount> findBankAccount = new HashMap<>();
		List<BankAccount> bankAccountList = bd.findByUsername(user.getUsername());
		List<BankAccount> sharedBankAccountList = bd.findByUsernameAndType(user.getSharedAccounts());
		bankAccountList.addAll(sharedBankAccountList);
		int bankAccountCount = bankAccountList.size();
		int maxCountLength = String.valueOf(bankAccountCount).length() + 3;
		int accountTypeLength = 15;
		System.out.println(StringHelper.padRight("#", maxCountLength)
				+ StringHelper.padRight("Account Type", accountTypeLength) + "Shared By");
		int bankAccountNumber = 0;
		for (BankAccount bankAccount : bankAccountList) {
			String username = bankAccount.getUsername();
			if (username.equals(user.getUsername())) {
				username = "";
			}
			bankAccountNumber++;
			findBankAccount.put(bankAccountNumber, bankAccount);
			System.out.println(StringHelper.padRight(String.valueOf(bankAccountNumber), maxCountLength)
					+ StringHelper.padRight(bankAccount.getAccountTypeString(), accountTypeLength) + username);
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

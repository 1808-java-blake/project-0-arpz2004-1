package com.revature.screens;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

import com.revature.beans.BankAccount;
import com.revature.beans.Transaction;
import com.revature.beans.User;
import com.revature.daos.BankAccountDao;

public class WithdrawMoneyScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private BankAccountDao bad = BankAccountDao.currentBankAccountDao;

	@Override
	public Screen start() {
		String selection;
		boolean validSelection = false;
		BankAccount ba = bad.findByUsername(User.getCurrentUser().getUsername());
		BigDecimal currentBalance = ba.getBalance();
		do {
			System.out.println("Your current bank account balance is $" + currentBalance + ".");
			System.out.println("Enter the amount you want to withdraw or press enter to return to home screen:");
			selection = scan.nextLine();
			if (selection.matches("^\\$?\\d+(.\\d\\d)?$")) {
				if (selection.charAt(0) == '$') {
					selection = selection.substring(1);
				}
				if (currentBalance.compareTo(new BigDecimal(selection)) >= 0) {
					validSelection = true;
					//Change these to use daos
					Transaction t = new Transaction(new BigDecimal(selection), LocalDateTime.now());
					User.getCurrentUser().addTransaction(t);
					ba.setBalance(currentBalance.subtract(new BigDecimal((selection))));
					bad.updateBankAccount(ba);
					System.out.println("Your new bank account balance is $" + ba.getBalance() + ".");
					System.out.println("Press enter to return to home screen.");
					scan.nextLine();
				} else {
					System.out.println("ERROR: Amount entered is greater than account balance.");
				}
			} else if(selection.isEmpty()) {
				validSelection = true;
			} else {
				System.out.println("Invalid value entered. Examples of correct values include $1, 1, 1.12, and $33.44");
			}
		} while (!validSelection);
		return new HomeScreen();
	}

}
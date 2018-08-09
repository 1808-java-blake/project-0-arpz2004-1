package com.revature.screens;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

import com.revature.beans.BankAccount;
import com.revature.beans.Transaction;
import com.revature.beans.User;
import com.revature.daos.BankAccountDao;
import com.revature.daos.TransactionDao;
import com.revature.daos.UserDao;

public class DepositMoneyScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private BankAccountDao bad = BankAccountDao.currentBankAccountDao;
	private TransactionDao td = TransactionDao.currentTransactionDao;
	private UserDao ud = UserDao.currentUserDao;

	@Override
	public Screen start() {
		String selection;
		boolean validSelection = false;
		User u = User.getCurrentUser();
		BankAccount ba = bad.findByUsername(u.getUsername());
		do {
			System.out.println("Your current bank account balance is $" + ba.getBalance() + ".");
			System.out.println("Enter the amount you want to deposit or press enter to return to home screen:");
			selection = scan.nextLine();
			if (selection.matches("^\\$?\\d+(.\\d\\d)?$")) {
				validSelection = true;
				if (selection.charAt(0) == '$') {
					selection = selection.substring(1);
				}
				Transaction t = new Transaction(new BigDecimal(selection), LocalDateTime.now());
				td.createTransaction(t);
				ba.setBalance(ba.getBalance().add(new BigDecimal(selection)));
				bad.updateBankAccount(ba);
				u.addTransaction(t.getTransactionID());
				ud.updateUser(u);
				System.out.println("Your new bank account balance is $" + ba.getBalance() + ".");
				System.out.println("Press enter to return to home screen.");
				scan.nextLine();
			} else if(selection.isEmpty()) {
				validSelection = true;
			} else {
				System.out.println("Invalid value entered. Examples of correct values include $1, 1, 1.12, and $33.44");
			}
		} while (!validSelection);
		return new HomeScreen();
	}

}
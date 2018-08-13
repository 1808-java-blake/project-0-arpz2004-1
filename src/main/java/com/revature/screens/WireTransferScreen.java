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

public class WireTransferScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.currentUserDao;
	private BankAccountDao bad = BankAccountDao.currentBankAccountDao;
	private TransactionDao td = TransactionDao.currentTransactionDao;

	@Override
	public Screen start() {
		User currentUser = User.getCurrentUser();
		User userTransferredTo = null;
		String selectedUsername;
		boolean validUsername = false;
		do {
			System.out.println("Enter username of user to transfer money to or press enter to return to home screen.");
			selectedUsername = scan.nextLine();
			if (selectedUsername.isEmpty()) {
				validUsername = true;
			} else {
				if (selectedUsername.equals(currentUser.getUsername())) {
					System.out.println("ERROR: You can't transfer money to yourself.");
				} else {
					userTransferredTo = ud.findByUsername(selectedUsername);
					if (userTransferredTo == null) {
						System.out.println("ERROR: User with that username does not exist.");
					} else {
						validUsername = true;
					}
				}
			}
		} while (!validUsername);
		if (userTransferredTo != null) {
			String amount;
			boolean validAmount = false;
			BankAccount ba = bad.findByUsername(currentUser.getUsername());
			BankAccount ba2 = bad.findByUsername(userTransferredTo.getUsername());
			do {
				System.out.println("Your current bank account balance is " + ba.getBalanceString() + ".");
				System.out.println("Enter the amount you want to transfer or press enter to return to home screen:");
				amount = scan.nextLine();
				if (amount.matches("^\\$?\\d+(.\\d\\d)?$")) {
					if (amount.charAt(0) == '$') {
						amount = amount.substring(1);
					}
					BigDecimal currentBalance = ba.getBalance();
					BigDecimal transferAmount = new BigDecimal(amount);
					if (currentBalance.compareTo(transferAmount) >= 0) {
						validAmount = true;
						LocalDateTime transactionTime = LocalDateTime.now();
						Transaction t = new Transaction(currentUser, transferAmount.negate(), transactionTime);
						Transaction t2 = new Transaction(userTransferredTo, transferAmount, transactionTime);
						td.createTransaction(t);
						td.createTransaction(t2);
						ba.setBalance(ba.getBalance().subtract(transferAmount));
						ba2.setBalance(ba2.getBalance().add(transferAmount));
						bad.updateBankAccount(ba);
						bad.updateBankAccount(ba2);
						ud.updateUser(currentUser);
						ud.updateUser(userTransferredTo);
						System.out.println(t.getAmountString().replace("-", "") + " successfully transferred to "
								+ userTransferredTo.getUsername());
						System.out.println("Your new bank account balance is " + ba.getBalanceString() + ".");
						System.out.println("Press enter to return to home screen.");
						scan.nextLine();
					} else {
						System.out.println("ERROR: Amount entered is greater than account balance.");
					}
				} else if (amount.isEmpty()) {
					validAmount = true;
				} else {
					System.out.println(
							"Invalid value entered. Examples of correct values include $1, 1, 1.12, and $33.44");
				}
			} while (!validAmount);
		}
		return new HomeScreen();
	}

}

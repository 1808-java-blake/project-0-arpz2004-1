package com.revature.screens;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

import com.revature.beans.BankAccount;
import com.revature.beans.Transaction;
import com.revature.beans.Transaction.TransactionType;
import com.revature.beans.User;
import com.revature.daos.BankAccountDao;
import com.revature.daos.TransactionDao;
import com.revature.daos.UserDao;

public class DepositMoneyScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private BankAccountDao bad = BankAccountDao.currentBankAccountDao;
	private TransactionDao td = TransactionDao.currentTransactionDao;
	private UserDao ud = UserDao.currentUserDao;
	private BankAccount ba;

	public DepositMoneyScreen() {
		super();
		AccountTypeSelectScreen accountTypeSelectScreen = new AccountTypeSelectScreen("deposit money to");
		ba = accountTypeSelectScreen.getBankAccount();
	}

	@Override
	public Screen start() {
		if (ba != null) {
			String selection;
			boolean validSelection = false;
			User u = User.getCurrentUser();
			do {
				System.out.println("Your current bank account balance is " + ba.getBalanceString() + ".");
				System.out.println("Enter the amount you want to deposit or press enter to return to home screen:");
				selection = scan.nextLine();
				if (selection.matches("^\\$?\\d+(\\.\\d\\d)?$")) {
					validSelection = true;
					if (selection.charAt(0) == '$') {
						selection = selection.substring(1);
					}
					BigDecimal depositAmount = new BigDecimal(selection);
					Transaction t = new Transaction(depositAmount, LocalDateTime.now(), ba, TransactionType.DEPOSIT);
					td.createTransaction(t);
					ba.setBalance(ba.getBalance().add(depositAmount));
					bad.updateBankAccount(ba);
					ud.updateUser(u);
					System.out.println("Your new bank account balance is " + ba.getBalanceString() + ".");
					System.out.println("Press enter to return to home screen.");
					scan.nextLine();
				} else if (selection.isEmpty()) {
					validSelection = true;
				} else {
					System.out.println(
							"Invalid value entered. Examples of correct values include $1, 1, 1.12, and $33.44");
				}
			} while (!validSelection);
		}
		return new HomeScreen();
	}

}
package com.revature.screens;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import com.revature.beans.Transaction;
import com.revature.beans.User;
import com.revature.daos.TransactionDao;
import com.revature.helpers.BigDecimalHelper;
import com.revature.helpers.StringHelper;

public class TransactionHistoryScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private TransactionDao td = TransactionDao.currentTransactionDao;
	private User user;
	private Screen previousScreen;

	public TransactionHistoryScreen(User user) {
		this.user = user;
		previousScreen = new HomeScreen();
	}

	public TransactionHistoryScreen(User user, Screen screen) {
		this.user = user;
		previousScreen = screen;
	}

	@Override
	public Screen start() {
		TreeSet<Integer> transactionHistory = user.getTransactionHistory();
		List<String> transactionAmounts = new ArrayList<>();
		List<String> transferredToUsers = new ArrayList<>();
		for (Integer transactionID : transactionHistory) {
			Transaction transaction = td.findByUserAndID(user, transactionID);
			transactionAmounts.add(BigDecimalHelper.getMoneyString(transaction.getAmount()));
			String bankAccountTransferredTo = transaction.getUsernameAndAccountTypeTransferredTo();
			if (bankAccountTransferredTo != null) {
				transferredToUsers.add(bankAccountTransferredTo);
			}
		}
		int lastTransactionID = 0;
		if (!transactionHistory.isEmpty()) {
			lastTransactionID = transactionHistory.last();
		}
		int maxIDLength = String.valueOf(lastTransactionID).length();
		int maxAmountLength = StringHelper.maxLengthString(transactionAmounts);
		int dateLength = 7;
		if (transactionHistory.size() > 0) {
			dateLength = 22;
		}
		int amountLength = Math.max(9, maxAmountLength + 3);
		int accountTypeLength = 15;
		int transactionTypeLength = 19;
		int maxTransferToLength = StringHelper.maxLengthString(transferredToUsers);
		int transferToLength = Math.max(22, maxTransferToLength + 3);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println(StringHelper.padRight("#", maxIDLength + 3)
				+ StringHelper.padRight("Account Type", accountTypeLength)
				+ StringHelper.padRight("Transaction Type", transactionTypeLength)
				+ StringHelper.padRight("Transferred To/From", transferToLength) + StringHelper.padRight("Time", dateLength)
				+ StringHelper.padRight("Amount", amountLength) + "Balance");
		BigDecimal balance = new BigDecimal(0);
		for (Integer transactionID : transactionHistory) {
			Transaction transaction = td.findByUserAndID(user, transactionID);
			balance = balance.add(transaction.getAmount());
			String transactionTime = transaction.getTime().format(formatter);
			String bankAccountTransferredTo = transaction.getUsernameAndAccountTypeTransferredTo();
			if(bankAccountTransferredTo == null) {
				bankAccountTransferredTo = "";
			}
			System.out.println(StringHelper.padRight(String.valueOf(transaction.getTransactionID()), maxIDLength + 3)
					+ StringHelper.padRight(transaction.getBankAccount().getAccountTypeString(), accountTypeLength)
					+ StringHelper.padRight(transaction.getTransactionTypeString(), transactionTypeLength)
					+ StringHelper.padRight(bankAccountTransferredTo, transferToLength)
					+ StringHelper.padRight(transactionTime, dateLength)
					+ StringHelper.padRight(transaction.getAmountString(), amountLength)
					+ BigDecimalHelper.getMoneyString(balance));
		}
		if (previousScreen instanceof AdminScreen) {
			System.out.println("Press enter to return to admin screen.");
		} else {
			System.out.println("Press enter to return to home screen.");
		}
		scan.nextLine();
		return previousScreen;
	}
}